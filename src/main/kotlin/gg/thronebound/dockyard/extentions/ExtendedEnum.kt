package gg.thronebound.dockyard.extentions

inline fun <reified T : Enum<T>> enumRandom(): T = enumValues<T>().random()