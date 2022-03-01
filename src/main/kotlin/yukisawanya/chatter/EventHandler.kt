package yukisawanya.chatter

import io.papermc.paper.chat.ChatRenderer
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.flattener.ComponentFlattener
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerAdvancementDoneEvent
import org.bukkit.event.player.PlayerBedEnterEvent
import org.bukkit.event.player.PlayerJoinEvent

class EventHandler(private val plugin: Chatter): Listener {
    fun refreshDisplayName(player: Player) {
        val chat = plugin.chat!!
        val config = plugin.configuration!!
        val fullName = config.nameFormat
            .replace("{prefix}", chat.getPlayerPrefix(player))
            .replace("{name}", player.name)
            .replace("{suffix}", chat.getPlayerSuffix(player))
        player.displayName(Component.text(fullName))
    }
    @EventHandler
    fun onChat(event: AsyncChatEvent) {
        refreshDisplayName(event.player)
        event.renderer(ChatRenderer.viewerUnaware { _, sourceDisplayName, message ->
            val flattener = ComponentFlattener.basic()
            var component: Component? = null
            flattener.flatten(sourceDisplayName) { name ->
                flattener.flatten(message) { message ->
                    val format = plugin.configuration!!.chatFormat.replace("{player}", name).replace("{message}", message)
                    component = Component.text(format)
                }
            }
            component!!
        })
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) = refreshDisplayName(event.player)

    @EventHandler
    fun onPlayerAsleep(event: PlayerBedEnterEvent) = refreshDisplayName(event.player)

    @EventHandler
    fun onPlayerAdvancementDone(event: PlayerAdvancementDoneEvent) = refreshDisplayName(event.player)
}