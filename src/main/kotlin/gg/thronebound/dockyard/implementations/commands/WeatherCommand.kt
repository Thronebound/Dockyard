package gg.thronebound.dockyard.implementations.commands

import gg.thronebound.dockyard.commands.Commands
import gg.thronebound.dockyard.commands.EnumArgument
import gg.thronebound.dockyard.commands.WorldArgument
import gg.thronebound.dockyard.extentions.properStrictCase
import gg.thronebound.dockyard.world.Weather
import gg.thronebound.dockyard.world.World

class WeatherCommand {
    init {
        Commands.add("/weather") {
            withPermission("dockyard.commands.weather")
            withDescription("Sets a weather in specified world")

            addArgument("weather", EnumArgument(Weather::class))
            addOptionalArgument("world", WorldArgument())

            execute { ctx ->
                val weather = getEnumArgument<Weather>("weather")
                val world = getArgumentOrNull<World>("world") ?: ctx.getPlayerOrThrow().world

                world.weather.value = weather
                ctx.sendMessage("<gray>Set weather in world <white>${world.name} <gray>to <yellow>${weather.name.properStrictCase()}")
            }
        }
    }
}