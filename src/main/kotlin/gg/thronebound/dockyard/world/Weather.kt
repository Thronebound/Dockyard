package gg.thronebound.dockyard.world

enum class Weather(val rain: Boolean, val thunder: Boolean) {
    CLEAR(false, false),
    RAIN(true, false),
    THUNDER(true, true)
}