package me.zepsizola.babypotion

import org.bukkit.event.entity.PotionSplashEvent
import org.bukkit.plugin.java.JavaPlugin

class BabyPotion : JavaPlugin() {
	override fun onEnable() {
		INSTANCE = this
		server.pluginManager.registerEvents(PotionSplashListener(), this)
		getCommand("babypotion")?.setExecutor(BabyPotionCommand())
		logger.info("BabyPotion has enabled!")
	}

	override fun onDisable() {
		PotionSplashEvent.getHandlerList().unregister(this)
		logger.info("BabyPotion has disabled.")
	}

	companion object {
		lateinit var INSTANCE: BabyPotion
	}
}
