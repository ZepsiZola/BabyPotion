package me.zepsizola.babypotion

import me.zepsizola.babypotion.BabyPotion.Companion.INSTANCE
import org.bukkit.NamespacedKey
import org.bukkit.entity.Ageable
import org.bukkit.entity.Breedable
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.entity.Player
import org.bukkit.event.entity.PotionSplashEvent
import me.ryanhamshire.GriefPrevention.Claim
import me.ryanhamshire.GriefPrevention.ClaimPermission
import me.ryanhamshire.GriefPrevention.GriefPrevention
import me.ryanhamshire.GriefPrevention.TextMode
import me.ryanhamshire.GriefPrevention.Messages
import org.bukkit.ChatColor

class PotionSplashListener : Listener {

	@EventHandler
	fun onPotionSplash(event: PotionSplashEvent) {
		// Exit if the potion is not a baby potion
		if (!event.potion.item.itemMeta.persistentDataContainer.has(babyPotionKey)) return

		// Assuming the shooter is always a Player (might not be safe in all contexts)
		val shooter = event.potion.shooter
		if (shooter !is Player) return

		event.affectedEntities.filterIsInstance<Ageable>().forEach { entity ->
			val claim = GriefPrevention.instance.dataStore.getClaimAt(entity.location, false, null)

			// If no claim is found, or if the player has Build or Edit permissions, or is the owner
			if (claim == null || shooter.hasClaimPermission(claim)) {
				entity.setBaby()
				(entity as? Breedable)?.ageLock = true
			}else{
				GriefPrevention.sendMessage(shooter, ChatColor.RED, Messages.NoDamageClaimedEntity, claim.getOwnerName())
			}
		}
	}

	// Checks if a player has the permission to affect an entity in a claim
	private fun Player.hasClaimPermission(claim: Claim): Boolean {
		return this.uniqueId == claim.ownerID ||
				claim.getPermission(this.uniqueId.toString())?.let { permission ->
					permission == ClaimPermission.Build || permission == ClaimPermission.Edit
				} ?: false
	}

	companion object {
		val babyPotionKey: NamespacedKey = NamespacedKey(INSTANCE, "baby_potion")
	}
}