package gg.thronebound.dockyard.data

import gg.thronebound.dockyard.protocol.DataComponentHashable
import gg.thronebound.dockyard.protocol.NetworkWritable

abstract class DataComponent(val isSingleField: Boolean = false) : NetworkWritable, DataComponentHashable {

    fun getId(): Int {
        return getIdOrNull() ?: throw NoSuchElementException("Data Component Registry does not have this component")
    }

    fun getIdOrNull(): Int? {
        return DataComponentRegistry.dataComponentsByIdReversed.getOrDefault(this::class, null)
    }
}