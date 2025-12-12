package gg.thronebound.dockyard.entity

import cz.lukynka.bindables.Bindable
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.registry.EntityTypes
import gg.thronebound.dockyard.registry.registries.EntityType

class Squid(location: Location): Entity(location) {
    override var type: EntityType = EntityTypes.SQUID
    override val health: Bindable<Float> = bindablePool.provideBindable(10f)
    override var inventorySize: Int = 0
}