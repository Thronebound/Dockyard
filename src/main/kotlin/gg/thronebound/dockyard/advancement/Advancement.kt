package gg.thronebound.dockyard.advancement

import cz.lukynka.bindables.BindableList
import gg.thronebound.dockyard.events.EventPool
import gg.thronebound.dockyard.events.Events
import gg.thronebound.dockyard.events.PlayerLeaveEvent
import gg.thronebound.dockyard.extentions.writeString
import gg.thronebound.dockyard.extentions.writeStringArray
import gg.thronebound.dockyard.extentions.writeTextComponent
import gg.thronebound.dockyard.extentions.writeVarInt
import gg.thronebound.dockyard.item.ItemStack
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.protocol.NetworkWritable
import gg.thronebound.dockyard.protocol.writeOptional
import gg.thronebound.dockyard.registry.Items
import gg.thronebound.dockyard.utils.Disposable
import gg.thronebound.dockyard.utils.viewable.Viewable
import io.netty.buffer.ByteBuf
import kotlin.properties.Delegates

class Advancement(
    id: String,
    parent: Advancement?,

    title: String,
    description: String,
    icon: ItemStack,
    frame: AdvancementFrame,
    showToast: Boolean,
    isHidden: Boolean,
    background: String?,
    x: Float,
    y: Float,

    requirements: List<List<String>>,
) : NetworkWritable, Viewable(), Disposable {

    var id by Delegates.observable(id) { _, _, _ -> update() }
    var parent by Delegates.observable(parent) { _, _, _ -> update() }
    val requirements = BindableList(requirements)

    var title by Delegates.observable(title) { _, _, _ -> update() }
    var description by Delegates.observable(description) { _, _, _ -> update() }
    var icon by Delegates.observable(icon) { _, _, _ -> update() }
    var frame by Delegates.observable(frame) { _, _, _ -> update() }
    var showToast by Delegates.observable(showToast) { _, _, _ -> update() }
    var isHidden by Delegates.observable(isHidden) { _, _, _ -> update() }
    var background by Delegates.observable(background) { _, _, _ -> update() }
    var x by Delegates.observable(x) { _, _, _ -> update() }
    var y by Delegates.observable(y) { _, _, _ -> update() }

    private val innerChildren = mutableListOf<Advancement>()
    val children
        get() = synchronized(innerChildren) {
            innerChildren.toList()
        }

    override var autoViewable: Boolean = false

    private val eventPool = EventPool(Events, "Advancement Listeners")

    init {
        require(icon.material != Items.AIR) { "advancement icon can't be air" }

        parent?.innerChildren?.let {
            synchronized(it) {
                it.add(this)
            }
        }

        this.requirements.listUpdated { update() }

        eventPool.on<PlayerLeaveEvent> { event ->
            removeViewer(event.player)
        }
    }

    fun update() {
        viewers.forEach { player ->
            player.advancementTracker.onAdvancementRemoved(this)
            player.advancementTracker.onAdvancementAdded(this)
        }

        children.forEach(Advancement::update)
    }

    override fun write(buffer: ByteBuf) {
        buffer.writeOptional(parent?.id, ByteBuf::writeString)

        // advancement display is present
        buffer.writeBoolean(true)

        buffer.writeTextComponent(title)
        buffer.writeTextComponent(description)
        icon.write(buffer)
        buffer.writeVarInt(frame.ordinal)
        buffer.writeInt(getFlags())
        background?.let(buffer::writeString)
        buffer.writeFloat(x)
        buffer.writeFloat(y)

        buffer.writeVarInt(requirements.size)
        requirements.values.forEach(buffer::writeStringArray)

        buffer.writeBoolean(false) // that's 'Sends telemetry' field
    }

    /**
     * Adds the player as a viewer to this advancement
     * and all parents, all the way to root
     */
    override fun addViewer(player: Player): Boolean {
        if (!super.addViewer(player)) return false

        // parents first
        this.parent?.addViewer(player)

        player.advancementTracker.onAdvancementAdded(this)
        return true
    }

    /**
     * Adds the player as a viewer
     * to this advancement and ALL children
     * recursively
     */
    fun addAll(player: Player) {
        addViewer(player)

        children.forEach { child ->
            child.addAll(player)
        }
    }

    /**
     * Removes the player-viewer from this advancement
     * and all children
     */
    override fun removeViewer(player: Player) {
        if (!viewers.contains(player)) return

        // children first
        synchronized(this.innerChildren) {
            this.innerChildren.forEach { child ->
                child.removeViewer(player)
            }
        }

        player.advancementTracker.onAdvancementRemoved(this)
        super.removeViewer(player)
    }

    fun getFlags(): Int {
        var flags = 0x0
        if (background != null) {
            flags = flags or HAS_BACKGROUND_TEXTURE
        }
        if (showToast) {
            flags = flags or SHOW_TOAST
        }
        if (isHidden) {
            flags = flags or HIDDEN
        }
        return flags
    }

    companion object {
        const val HAS_BACKGROUND_TEXTURE = 0x01
        const val SHOW_TOAST = 0x02
        const val HIDDEN = 0x04
    }

    /**
     * Deletes this advancement and its children
     */
    override fun dispose() {
        this.parent?.innerChildren?.remove(this)

        while (viewers.isNotEmpty()) {
            removeViewer(viewers.first())
        }

        while (innerChildren.isNotEmpty()) {
            innerChildren.removeFirstOrNull()?.parent = null
        }

        this.parent = null
    }
}
