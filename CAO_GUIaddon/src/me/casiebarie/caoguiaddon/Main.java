package me.casiebarie.caoguiaddon;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Consumer;

import me.casiebarie.caoguiaddon.other.Commands;
import me.casiebarie.caoguiaddon.other.Info;
import me.casiebarie.caoguiaddon.other.TabComplete;
import me.casiebarie.caoguiaddon.other.UpdateChecker;
import me.casiebarie.casieattractionoperate.API;

public class Main extends JavaPlugin {
	public me.casiebarie.casieattractionoperate.Main cao;
	public API caoAPI;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		caoAPI = getCAOapi();
		if(caoAPI != null) {
			updateChecker();
			Functions f = new Functions(this, caoAPI);
			Menu menu = new Menu(this, f, caoAPI);
			new EventListener(this, f, caoAPI, menu);
			new Commands(this, f, caoAPI, menu);
			new TabComplete(this, f);
			new Info();
		} else {
			//Disable plugin
			getLogger().warning("CasieAttractionOperate not found! Disabling plugin.");
			Bukkit.getPluginManager().disablePlugin(this);
		}
	}
	
	@Override
	public void onDisable() {saveDefaultConfig();reloadConfig();}
	public boolean papiPresent() {return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;}
	private API getCAOapi() {
		cao = (me.casiebarie.casieattractionoperate.Main) Bukkit.getPluginManager().getPlugin("CasieAttractionOperate");
		return cao.api;}
    public void updateChecker() {
        new UpdateChecker(this, 94778).getVersion((Consumer<String>)(version -> {
            if (getDescription().getVersion().equalsIgnoreCase(version)) {
                getLogger().info("You are using the most recent version. (v" + getDescription().getVersion() + ")");}
            else {getLogger().info("There is a new update available (v" + version + ")!   You are using: v" + getDescription().getVersion());}
        }));
    }
}
