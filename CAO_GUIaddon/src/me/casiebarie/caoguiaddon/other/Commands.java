package me.casiebarie.caoguiaddon.other;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.casiebarie.caoguiaddon.Functions;
import me.casiebarie.caoguiaddon.Main;
import me.casiebarie.caoguiaddon.Menu;
import me.casiebarie.casieattractionoperate.API;

public class Commands implements CommandExecutor {
	private Functions f;
	private API caoAPI;
	private Menu menu;
	private static FileConfiguration caoCONFIG;
	public Commands(Main plugin, Functions f, API caoAPI, Menu menu) {
		this.f = f;
		this.caoAPI = caoAPI;
		this.menu = menu;
		plugin.getCommand("CAOmenu").setExecutor(this);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		caoCONFIG = f.getCAOconfig();
		if(cmd.getName().equalsIgnoreCase("CAOMENU")) {
			if(args.length >= 2 && args.length <= 3 && args[0].toUpperCase().equals("OPEN")) {
				if(sender.hasPermission("CAO.menu.cmd")) {
					if(!args[1].equals("Sample")) {
						Player player = null;
						if(args.length == 2 && sender instanceof Player) {player = (Player) sender;}
						else {
							if(args.length != 3) {f.sendMessage(sender, "Usage", ""); return false;}
							for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
								if(!args[2].equals(onlinePlayer.getName())) {f.sendMessage(sender, "PlayerNotFound", ""); return false;}
								player = onlinePlayer;
							}	
						}
						File aFile1 = new File(Bukkit.getPluginManager().getPlugin("CasieAttractionOperate").getDataFolder() + File.separator + caoCONFIG.getString(".Attractions." + args[1]));
						if(aFile1.exists()) {menu.openMenu(player, args[1]);
						} else {caoAPI.sendMessage(sender, "NotInConfig", args[1], "");}
					} else {caoAPI.sendMessage(sender, "FeatureDisabled", "", "");}
				} else {caoAPI.sendMessage(sender, "NoPermission", "", "");}
			} else if(args.length == 1 && args[0].equalsIgnoreCase("ReloadConfig")) {
				if(sender.hasPermission("CAO.menu.admin")) {
					f.reloadConfig(sender);
					menu.restartRefresh();
				} else {caoAPI.sendMessage(sender, "NoPermission", "", "");}
			} else if(args.length == 1 && args[0].equalsIgnoreCase("Info")) {
				if(sender.hasPermission("CAO.menu.admin")) {Info.infoMessage(sender);}
				else {caoAPI.sendMessage(sender, "NoPermission", "", "");}
			} else {f.sendMessage(sender, "Usage", "");}
		}
		return false;
	}
}