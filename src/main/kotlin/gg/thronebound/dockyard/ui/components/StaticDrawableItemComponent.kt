package gg.thronebound.dockyard.ui.components

import gg.thronebound.dockyard.ui.CompositeDrawable
import gg.thronebound.dockyard.ui.DrawableItemStack

class StaticDrawableItemComponent(val item: DrawableItemStack) : CompositeDrawable() {

    override fun buildComponent() {
        withSlot(0, item)
    }

    override fun dispose() {}
}