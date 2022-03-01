package yukisawanya.chatter

import java.io.File

class Configuration(val plugin: Chatter) {
    var chatFormat = ""
    var nameFormat = ""
    init {
        val configFile = File(plugin.dataFolder, "config.yml")
        if(!configFile.exists()) {
            configFile.parentFile.mkdirs()
            plugin.saveDefaultConfig()
        }
        load()
    }

    fun load() {
        chatFormat = plugin.config.getString("chatFormat", "<{player}> {message}")!!
        nameFormat = plugin.config.getString("nameFormat", "{prefix}{name}{suffix}")!!
    }
}