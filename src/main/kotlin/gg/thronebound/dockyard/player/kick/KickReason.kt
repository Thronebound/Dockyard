package gg.thronebound.dockyard.player.kick

fun getSystemKickMessage(kickReason: String): String {
    val message = buildString {
        appendLine("<red><b>Disconnected")
        appendLine()
        appendLine("<gray>$kickReason")
    }
    return message
}