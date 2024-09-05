package io.github.dockyardmc.implementations.commands

import io.github.dockyardmc.config.ConfigManager

class DockyardCommands {

    init {
        GamemodeCommand()
        VersionAndHelpCommand()
        WorldCommand()
        SoundCommand()
        GiveCommand()
        TeleportCommand()
        SchematicCommand()
        TimeCommand()
        if(ConfigManager.currentConfig.serverConfig.debug) {
            ViewerCommand()
            DebugCommands()
        }
    }
}