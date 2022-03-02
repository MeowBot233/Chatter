package yukisawanya.chatter

import net.milkbowl.vault.chat.Chat
import org.bukkit.plugin.java.JavaPlugin

class Chatter : JavaPlugin() {
    var chat: Chat? = null
    var configuration: Configuration? = null
    override fun onEnable() {
        // Plugin startup logic
        val rsp = server.servicesManager.getRegistration(Chat::class.java)
        chat = rsp?.provider
        configuration = Configuration(this)
        val handler = EventHandler(this)
        server.pluginManager.registerEvents(handler, this)
        server.onlinePlayers.forEach { handler.refreshName(it) }
//        getCommand("chatter_reload")?.run {
//
//        }
    }

    override fun onDisable() {
        // Plugin shutdown logic

    }
}