package me.casiebarie.caoguiaddon;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.casiebarie.casieattractionoperate.API;

public class EventListener implements Listener {
	private Functions f;
	private API caoAPI;
	private Menu menu;
	private static FileConfiguration CAOconfig;
	private static FileConfiguration config;
	public EventListener(Main plugin, Functions f, API caoAPI, Menu menu) {
		this.f = f;
		this.caoAPI = caoAPI;
		this.menu = menu;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		config = f.getCONFIG();
		CAOconfig = f.getCAOconfig();
		Player player = (Player) e.getWhoClicked();
		boolean closeOnClick = config.getBoolean(".MenuSettings.CloseOnClick");
		if(f.getINVopen().contains(player.getUniqueId().toString())) {
			for(final String attractionName : CAOconfig.getConfigurationSection(".Attractions").getKeys(false)) {
				String configTitle = f.Color(f.setPlaceholders((CommandSender)player, config.getString(".MenuSettings.Title").replaceAll("%attraction%", attractionName)));
				if(e.getView().getTitle().equals(configTitle)) {
					e.setCancelled(true);
					ItemStack item = e.getCurrentItem();
					if(item == null) {continue;}
					if(!item.hasItemMeta()) {continue;}
					ItemMeta itemMeta = item.getItemMeta();
					String[] localNameSplit = itemMeta.getLocalizedName().split("_");
					if(!localNameSplit[0].equals("CAOmenu")) {continue;}
					player = (EventListener.config.getBoolean(".MenuSettings.MessageOnClick") ? player : null);
					if(localNameSplit[1].equals("Restraints")) {caoAPI.restraints(player, attractionName);}
					if(localNameSplit[1].equals("Gates")) {caoAPI.gates(player, attractionName);}
					if(localNameSplit[1].equals("Release")) {caoAPI.release(player, attractionName);}
					if(localNameSplit[1].equals("Power")) {caoAPI.power(player, attractionName, "TOGGLE");}
					if(closeOnClick) {player.closeInventory();}
					else {menu.updateMenu(player, e.getClickedInventory(), attractionName);}
				}
			}
		}
	}
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		Player player = (Player) e.getPlayer();
		if(f.getINVopen().contains(player.getUniqueId().toString())) {f.setINVclosed(player.getUniqueId());}
	}
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player player = (Player) e.getPlayer();
		if(f.getINVopen().contains(player.getUniqueId().toString())) {f.setINVclosed(player.getUniqueId());}
	}
}
