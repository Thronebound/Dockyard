package gg.thronebound.dockyard.world

import gg.thronebound.dockyard.extentions.properStrictCase

enum class Difficulty {
    PEACEFUL,
    EASY,
    NORMAL,
    HARD;

    override fun toString(): String = this.name.properStrictCase()
}

