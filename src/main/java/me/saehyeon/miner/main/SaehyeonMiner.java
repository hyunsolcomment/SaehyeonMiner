package me.saehyeon.miner.main;

import org.bukkit.plugin.java.JavaPlugin;

public final class SaehyeonMiner extends JavaPlugin {

    public static SaehyeonMiner instance;

    @Override
    public void onEnable() {

        instance = this;
        getDataFolder().mkdir();

    }

    @Override
    public void onDisable() {

    }
}
