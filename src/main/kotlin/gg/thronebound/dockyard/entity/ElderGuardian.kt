package gg.thronebound.dockyard.entity

import cz.lukynka.bindables.Bindable
import gg.thronebound.dockyard.location.Location
import gg.thronebound.dockyard.registry.EntityTypes
import gg.thronebound.dockyard.registry.registries.EntityType

class ElderGuardian(location: Location): Guardian(location) {

    override val health: Bindable<Float> = bindablePool.provideBindable(80f)
    override var type: EntityType = EntityTypes.ELDER_GUARDIAN
}