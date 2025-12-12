package gg.thronebound.dockyard.utils

import cz.lukynka.prettylog.CustomLogType
import cz.lukynka.prettylog.LogType
import cz.lukynka.prettylog.log
import gg.thronebound.dockyard.DockyardServer

fun debug(text: String, toChat: Boolean = false, logType: CustomLogType = LogType.DEBUG) {
    if (InstrumentationUtils.isDebuggerAttached() && toChat) {
        DockyardServer.sendMessage("<#5b6070>(⚑) <#9097ad>$text")
    }
    if (DockyardServer.debug) log(text, logType)
}