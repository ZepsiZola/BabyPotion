package me.rime.babypotion

import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class BabyPotionCommand : CommandExecutor {
	override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
		// Check if there are enough arguments (expecting 1 for the player name)
		if (args.size != 1) {
			sender.sendMessage("Usage: /babypotion <playerName>")
			return true
		}

		// Attempt to find the player by the provided name
		val targetPlayer = Bukkit.getPlayer(args[0])
		if (targetPlayer == null) {
			sender.sendMessage("Player not found.")
			return true
		}

		// If the sender is a player, check for permissions
		if (sender is Player && !sender.hasPermission("babypotion.babypotion")) {
			sender.sendMessage("You do not have permission to use this command.")
			return true
		}

		// Give the potion to the target player
		targetPlayer.inventory.addItem(generatePotion())
		sender.sendMessage("${targetPlayer.name} has been given a Baby Potion.")
		return true
	}

	private fun generatePotion() = ItemStack(Material.SPLASH_POTION).apply {
		editMeta {
			it as PotionMeta
			//it.persistentDataContainer[babyPotionKey, PersistentDataType.BOOLEAN] = true
			it.persistentDataContainer[PotionSplashListener.babyPotionKey, PersistentDataType.BOOLEAN] = true
			it.displayName(MiniMessage.miniMessage().deserialize("<gradient:#98DBCC:#F2C6DE:#98DBCC>Baby Potion</gradient>").decoration(TextDecoration.BOLD, false).decoration(TextDecoration.ITALIC, true))
			it.addCustomEffect(PotionEffect(PotionEffectType.LUCK, 0, 0, false, false, false), true)
			it.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
		}
	}
}