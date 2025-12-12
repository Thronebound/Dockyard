package gg.thronebound.dockyard.entity

import cz.lukynka.bindables.Bindable
import cz.lukynka.bindables.BindableDispatcher
import gg.thronebound.dockyard.entity.metadata.EntityMetaValue
import gg.thronebound.dockyard.entity.metadata.EntityMetadata
import gg.thronebound.dockyard.entity.metadata.EntityMetadataType
import gg.thronebound.dockyard.events.*
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.player.Player
import gg.thronebound.dockyard.registry.EntityTypes
import gg.thronebound.dockyard.registry.registries.EntityType

class Interaction(location: Location): Entity(location) {

    override var type: EntityType = EntityTypes.INTERACTION
    override val health: Bindable<Float> = bindablePool.provideBindable(0f)
    override var inventorySize: Int = 0

    private val eventPool = EventPool(Events, "Interaction Listeners")

    val width: Bindable<Float> = bindablePool.provideBindable(1f)
    val height: Bindable<Float> = bindablePool.provideBindable(1f)
    val responsive: Bindable<Boolean> = bindablePool.provideBindable(true)

    val rightClickDispatcher: BindableDispatcher<Player> = bindablePool.provideBindableDispatcher()
    val leftClickDispatcher: BindableDispatcher<Player> = bindablePool.provideBindableDispatcher()
    val middleClickDispatcher: BindableDispatcher<Player> = bindablePool.provideBindableDispatcher()
    val generalInteractionDispatcher: BindableDispatcher<Player> = bindablePool.provideBindableDispatcher()

    init {
        eventPool.on<PlayerInteractWithEntityEvent> { event ->
            if(event.entity != this) return@on
            rightClickDispatcher.dispatch(event.player)
            generalInteractionDispatcher.dispatch(event.player)
        }

        eventPool.on<PlayerDamageEntityEvent> { event ->
            if(event.entity != this) return@on
            leftClickDispatcher.dispatch(event.player)
            generalInteractionDispatcher.dispatch(event.player)
        }

        eventPool.on<PlayerPickItemFromEntityEvent> { event ->
            if(event.entity != this) return@on
            middleClickDispatcher.dispatch(event.player)
            generalInteractionDispatcher.dispatch(event.player)
        }

        width.valueChanged { event ->
            metadata[EntityMetadataType.INTERACTION_WIDTH] = EntityMetadata(EntityMetadataType.INTERACTION_WIDTH, EntityMetaValue.FLOAT, event.newValue)
        }

        height.valueChanged { event ->
            metadata[EntityMetadataType.INTERACTION_HEIGHT] = EntityMetadata(EntityMetadataType.INTERACTION_HEIGHT, EntityMetaValue.FLOAT, event.newValue)
        }

        responsive.valueChanged { event ->
            metadata[EntityMetadataType.INTERACTION_RESPONSIVE] = EntityMetadata(EntityMetadataType.INTERACTION_RESPONSIVE, EntityMetaValue.BOOLEAN, event.newValue)
        }
    }

    override fun dispose() {
        eventPool.dispose()
        super.dispose()
    }
}