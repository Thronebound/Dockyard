package gg.thronebound.dockyard.extentions

import gg.thronebound.dockyard.math.vectors.Vector3

fun IntArray.toVector3(): Vector3 {
    require(this.size == 3) { "IntArray must have exactly 3 elements" }
    return Vector3(this[0], this[1], this[2])
}