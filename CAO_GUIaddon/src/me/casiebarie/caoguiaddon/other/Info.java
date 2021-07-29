package me.casiebarie.caoguiaddon.other;

import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

public class Info {
	@SuppressWarnings("deprecation")
	public static void infoMessage(CommandSender sender) {
		sender.spigot().sendMessage(new ComponentBuilder("------------------ ").color(ChatColor.GOLD).bold(false)
				.append("CAO - GUI ADDON").color(ChatColor.AQUA).bold(true).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("By: ").color(ChatColor.BLUE).append("CasieBarie").color(ChatColor.YELLOW).create())).append(" -----------------").color(ChatColor.GOLD).bold(false).event((HoverEvent)null)
				.append("\nThis plugin automatically finds your attractions in the CAO plugin. Just run '").color(ChatColor.DARK_GREEN)
				.append("/CAOmenu open ").color(ChatColor.YELLOW).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/CAO open <attraction")).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/CAOmenu open ").color(ChatColor.BLUE).append("<attraction>").color(ChatColor.LIGHT_PURPLE).create())).append("<attraction>").color(ChatColor.GOLD)
				.append("' to open the menu. The command has full TabCompletion to make your live easier. \nYou can customize the menu in the config. Run '").color(ChatColor.DARK_GREEN).event((ClickEvent)null).event((HoverEvent)null)
				.append("/CAOmenu ReloadConfig").color(ChatColor.YELLOW).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/CAOmenu ReloadConfig")).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/CAOmenu ReloadConfig").color(ChatColor.BLUE).create()))
				.append("' after you make changes to reload the config.").color(ChatColor.DARK_GREEN).event((ClickEvent)null).event((HoverEvent)null)
				.append("\n\nHAVE FUN!").color(ChatColor.GREEN)
				.append("----------------------------------------------------").color(ChatColor.GOLD)
				.create());
	}
}