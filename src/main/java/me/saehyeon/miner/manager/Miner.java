package me.saehyeon.miner.manager;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.ArrayList;

public class Miner {

    public static boolean isExistRegion(String regionName) {
        return MinerYML.yml.contains(regionName);
    }

    public static void set(Location pos1, Location pos2, String regionName) {

        MinerYML.yml.set(regionName+".pos1",pos1);
        MinerYML.yml.set(regionName+".pos2",pos2);
        MinerYML.yml.set(regionName+".regenBlocks",new ArrayList<>());
    }

    public static boolean setRegenBlocks(String regionName, ArrayList<Block> blocks) {

        if(!isExistRegion(regionName)) return false;

        MinerYML.yml.set(regionName+".regenBlocks", blocks);

        return true;

    }

    public static ArrayList<Block> getRegenBlocks(String regionName) {

        if(!isExistRegion(regionName)) return null;

        return (ArrayList<Block>) MinerYML.yml.get(regionName+".regenBlocks");
    }

}
