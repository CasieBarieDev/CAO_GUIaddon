package me.casiebarie.caoguiaddon;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.casiebarie.casieattractionoperate.API;

public class Menu {
	private Main plugin;
	private Functions f;
	private API caoAPI;
	private static FileConfiguration config;
	private static FileConfiguration caoCONFIG;
	private static ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	BukkitTask task;
	public Menu(Main plugin, Functions f, API caoAPI) {
		this.plugin = plugin;
		this.f = f;
		this.caoAPI = caoAPI;
		refreshMenu();
	}
	public void openMenu(Player player, String attractionName) {
		config = f.getCONFIG();
		caoCONFIG = f.getCAOconfig();
		String guiName = f.Color(config.getString(".MenuSettings.Title").replaceAll("%attraction%", attractionName));
		Inventory gui = Bukkit.createInventory(player, config.getInt(".MenuSettings.Size"), guiName);
		configureItems();
		updateMenu(player, gui, attractionName);
		f.setINVopen(player.getUniqueId());
		player.openInventory(gui);
	}
	public void updateMenu(final Player player, final Inventory gui, final String attractionName) {
		config = f.getCONFIG();
		caoCONFIG = f.getCAOconfig();
		configureItems();
		File aFile1 = new File(Bukkit.getPluginManager().getPlugin("CasieAttractionOperate").getDataFolder() + "/" + caoCONFIG.getString(".Attractions." + attractionName));
		FileConfiguration aFile2 = YamlConfiguration.loadConfiguration(aFile1);
		if(!aFile1.exists()) {return;}
		for(int i = 0; i < 6; ++i) {
			String _mode_ = null;
			String mode = null;
			int itemNumber = 0;
			mode = ((i >= 0 && i <= 2) ? "Restraints" : ((i >= 3 && i <= 5) ? "Gates" : null));
			if(aFile2.getBoolean(".Status.STATION") && aFile2.getString(".Status.POWER").equals("Enabled")) {
				if(aFile2.getBoolean(".Status." + mode.toUpperCase())) {
					_mode_ = "Open";
					itemNumber = ((mode == "Restraints") ? 0 : 3);
				} else {
					_mode_ = "Closed";
					itemNumber = ((mode == "Restraints") ? 1 : 4);}
			} else {
				_mode_ = "NotAllowed";
				itemNumber = ((mode == "Restraints") ? 2 : 5);}
			if(config.getBoolean(".MenuSettings.Items." + mode + ".ItemEnabled")) {
				ItemStack itemStack = Menu.items.get(itemNumber);
				ItemMeta itemMeta = itemStack.getItemMeta();
				String itemName = f.Color(config.getString(".MenuSettings.Items." + mode + "." + _mode_ + ".Name").replaceAll("%attraction", attractionName));
				itemMeta.setDisplayName(f.setPlaceholders(player, itemName));
				ArrayList<String> itemLoreList = new ArrayList<String>();
				for(String configItemLore : config.getStringList(".MenuSettings.Items." + mode + "." + _mode_ + ".Lore")) {
					String itemLore = f.Color(configItemLore.replaceAll("%attraction%", attractionName));
					itemLoreList.add(f.setPlaceholders(player, itemLore));
				}
				if (itemLoreList != null) {itemMeta.setLore(itemLoreList);}
				itemMeta.setLocalizedName("CAOmenu_" + mode);
				itemStack.setItemMeta(itemMeta);
				gui.setItem(config.getInt(".MenuSettings.Items." + mode + ".Slot"), itemStack);
			}
		}
		if(Menu.config.getBoolean(".MenuSettings.Items.Release.ItemEnabled")) {
			String _mode_2 = null;
			int itemNumber2 = 0;
			if(aFile2.getBoolean(".Status.RELEASE")) {
				_mode_2 = "Allowed";
				itemNumber2 = 6;
			} else {
				_mode_2 = "Disallowed";
				itemNumber2 = 7;
			}
			ItemStack itemStack2 = Menu.items.get(itemNumber2);
			ItemMeta itemMeta2 = itemStack2.getItemMeta();
			String itemName2 = f.Color(config.getString(".MenuSettings.Items.Release." + _mode_2 + ".Name").replaceAll("%attraction", attractionName));
			itemMeta2.setDisplayName(f.setPlaceholders(player, itemName2));
			ArrayList<String> itemLoreList2 = new ArrayList<String>();
			for(String configItemLore2 : config.getStringList(".MenuSettings.Items.Release." + _mode_2 + ".Lore")) {
				String itemLore2 = f.Color(configItemLore2.replaceAll("%attraction%", attractionName));
				itemLoreList2.add(f.setPlaceholders(player, itemLore2));
			}
			if(itemLoreList2 != null) {itemMeta2.setLore(itemLoreList2);}
			itemMeta2.setLocalizedName("CAOmenu_Release");
			itemStack2.setItemMeta(itemMeta2);
			gui.setItem(config.getInt(".MenuSettings.Items.Release.Slot"), itemStack2);
		}
		if(config.getBoolean(".MenuSettings.Items.Power.ItemEnabled")) {
			String _mode_2 = null;
			int itemNumber2 = 0;
			if(aFile2.getString(".Status.POWER").equals("Enabled")) {
				_mode_2 = "Enabled";
				itemNumber2 = 8;
			} else if(aFile2.getString(".Status.POWER").equals("Disabled")) {
				_mode_2 = "Disabled";
				itemNumber2 = 9;
			} else if(aFile2.getString(".Status.POWER").equals("Startup")) {
				_mode_2 = "Startup";
				itemNumber2 = 10;
			} else if(aFile2.getString(".Status.POWER").equals("Shutdown")) {
				_mode_2 = "Shutdown";
				itemNumber2 = 11;
			}
			ItemStack itemStack2 = items.get(itemNumber2);
			ItemMeta itemMeta2 = itemStack2.getItemMeta();
			String itemName2 = f.Color(config.getString(".MenuSettings.Items.Power." + _mode_2 + ".Name").replaceAll("%attraction", attractionName));
			itemMeta2.setDisplayName(f.setPlaceholders(player, itemName2));
			ArrayList<String> itemLoreList2 = new ArrayList<String>();
			for(String configItemLore2 : config.getStringList(".MenuSettings.Items.Power." + _mode_2 + ".Lore")) {
				String itemLore2 = f.Color(configItemLore2.replaceAll("%attraction", attractionName));
				itemLoreList2.add(f.setPlaceholders(player, itemLore2));
			}
			if(itemLoreList2 != null) {itemMeta2.setLore(itemLoreList2);}
			itemMeta2.setLocalizedName("CAOmenu_Power");
			itemStack2.setItemMeta(itemMeta2);
			gui.setItem(config.getInt(".MenuSettings.Items.Power.Slot"), itemStack2);
		}
	}
	public void restartRefresh() {
		task.cancel();
		ArrayList<String> uuidList = new ArrayList<String>();
		for(String uUIDname : f.getINVopen()) {uuidList.add(uUIDname);}
		for(String uuidname : uuidList) {
			Player player = Bukkit.getPlayer(UUID.fromString(uuidname));
			player.closeInventory();
			f.sendMessage(player, "ClosedMenu", "");
		}
		refreshMenu();
	}
	public void refreshMenu() {
		config = f.getCONFIG();
		caoCONFIG = f.getCAOconfig();
		long period = Menu.config.getLong(".MenuSettings.UpdateInterval");
		if(period == 0L) {return;}
		task = new BukkitRunnable() {
			public void run() {
				for(Player player : Bukkit.getOnlinePlayers()) {
					if(player.getOpenInventory() == null) {continue;}
					for(String attractionName : caoCONFIG.getConfigurationSection(".Attractions.").getKeys(false)) {
						String invName = f.Color(f.setPlaceholders(player, config.getString(".MenuSettings.Title").replaceAll("%attraction%", attractionName)));
						if (player.getOpenInventory().getTitle().equals(invName)) {updateMenu(player, player.getOpenInventory().getTopInventory(), attractionName);}
					}
				}
			};
		}.runTaskTimer(plugin, 0L, period);
	}
	@SuppressWarnings("deprecation")
	private void configureItems() {
		items = new ArrayList<ItemStack>();
		for(int i = 0; i < 12; ++i) {
			String _mode_ = null;
			String mode = null;
			_mode_ = ((i == 0 || i == 3) ? "Open" : ((i == 1 || i == 4) ? "Closed" : ((i == 2 || i == 5) ? "NotAllowed" : ((i == 6) ? "Allowed" : ((i == 7) ? "Disallowed" : ((i == 8) ? "Enabled" : ((i == 9) ? "Disabled" : ((i == 10) ? "Startup" : ((i == 11) ? "Shutdown" : null)))))))));
			mode = ((i >= 0 && i <= 2) ? "Restraints" : ((i >= 3 && i <= 5) ? "Gates" : ((i >= 6 && i <= 7) ? "Release" : ((i >= 8 && i <= 11) ? "Power" : null))));
			if (config.getBoolean(".MenuSettings.Items." + mode + ".ItemEnabled")) {
				if (caoAPI.isLegacy()) {
					String[] configItem = config.getString(".MenuSettings.Items." + mode + "." + _mode_ + ".Item").split(":");
					try {
						Material material = Material.matchMaterial(configItem[0].toUpperCase());
						byte data = 0;
						if(configItem.length == 2) {
							try {data = Byte.parseByte(configItem[1]);
							} catch (NumberFormatException e) {plugin.getLogger().warning("The data number of " + config.getString(".MenuSettings.Items." + mode + "." + _mode_ + ".Item") + " at " + mode + " " + _mode_ + " is wrong!");}
						}
						items.add(i, new ItemStack(material, 1, (short)0, Byte.valueOf(data)));
					} catch (Exception e) {plugin.getLogger().warning("The Item " + config.getString(".MenuSettings.Items." + mode + "." + _mode_ + ".Item") + " at " + mode + " " + _mode_ + " is not recognized!");}
				} else {items.add(i, new ItemStack(Material.matchMaterial(config.getString(".MenuSettings.Items." + mode + "." + _mode_ + ".Item"))));}
			} else {items.add(i, new ItemStack(Material.AIR));}
		}
	}
}