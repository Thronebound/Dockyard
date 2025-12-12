package gg.thronebound.dockyard.utils

abstract class Freezable {
    var frozen: Boolean = false

    open fun freeze() {
        frozen = true
    }

    open fun unfreeze() {
        frozen = false
    }
}