package gg.thronebound.dockyard.implementations.commands

import gg.thronebound.dockyard.implementations.DefaultImplementationModule
import gg.thronebound.dockyard.utils.InstrumentationUtils

class DefaultCommands : DefaultImplementationModule {

    override fun register() {
        GamemodeCommand()
        VersionAndHelpCommand()
        WorldCommand()
        SoundCommand()
        GiveCommand()
        TeleportCommand()
        TimeCommand()
        SchedulerCommand()
        ClearCommand()
        ListCommand()
        EffectCommand()
        WeatherCommand()
        SkinCommand()
        if(InstrumentationUtils.isDebuggerAttached()) DebugCommands.register()
    }
}