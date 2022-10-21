package me.saehyeon.miner.manager;

import me.saehyeon.miner.main.SaehyeonMiner;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MinerYML {
    public static YamlConfiguration yml = new YamlConfiguration();
    public static File file;

    File getFile() throws IOException{

        file = new File(SaehyeonMiner.instance.getDataFolder(), "regions.yml");
        file.createNewFile();

        return file;
    }

    public void save() {

        try {

            yml.save(getFile());

        } catch (Exception e) {

        }

    }

    public void load() {

        try {

            yml.load(getFile());

        } catch (Exception e) {

        }

    }
}
