package me.rime.babypotion

import org.bukkit.event.entity.PotionSplashEvent
import org.bukkit.plugin.java.JavaPlugin

class BabyPotion : JavaPlugin() {
	override fun onEnable() {
		INSTANCE = this
		server.pluginManager.registerEvents(PotionSplashListener(), this)
		getCommand("babypotion")?.setExecutor(BabyPotionCommand())
	}

	override fun onDisable() {
		PotionSplashEvent.getHandlerList().unregister(this)
	}

	companion object {
		lateinit var INSTANCE: BabyPotion
	}
}
