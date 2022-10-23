package me.saehyeon.miner.main;

import me.saehyeon.miner.event.*;
import me.saehyeon.miner.manager.Miner;
import me.saehyeon.miner.player.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SaehyeonMiner extends JavaPlugin {

    public static SaehyeonMiner instance;

    @Override
    public void onEnable() {

        instance = this;
        getDataFolder().mkdir();

        Bukkit.getPluginCommand("광물리젠").setExecutor(new onCommand());

        Bukkit.getPluginManager().registerEvents(new onJoin(),this);
        Bukkit.getPluginManager().registerEvents(new onBlock(),this);
        Bukkit.getPluginManager().registerEvents(new onClick(),this);
        Bukkit.getPluginManager().registerEvents(new onInventory(),this);
        Bukkit.getPluginManager().registerEvents(new onChat(),this);

        new Miner().load();

        /* PlayerInfo 재등록 */
        Bukkit.getOnlinePlayers().forEach(p -> PlayerInfo.set(p, new PlayerInfo()) );
    }

    @Override
    public void onDisable() {
        new Miner().save();
    }
}
