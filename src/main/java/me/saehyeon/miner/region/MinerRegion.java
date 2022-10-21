package me.saehyeon.miner.region;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.io.Serializable;
import java.util.ArrayList;

public class MinerRegion implements Serializable {
    public static ArrayList<MinerRegion> MinerRegions = new ArrayList<>();

    public String name;
    public ArrayList<Block> blocks = new ArrayList<>();
    Location[] position;

    public MinerRegion(String name, Location pos1, Location pos2) {
        this.name       = name;
        this.position   = new Location[] { pos1, pos2 };

    }

    public MinerRegion(String name, Location pos1, Location pos2, ArrayList<Block> blocks) {
        this.name       = name;
        this.position   = new Location[] { pos1, pos2 };
        this.blocks     = blocks;
    }

    public MinerRegion(String name, Location[] position, ArrayList<Block> blocks) {
        this.name       = name;
        this.position   = position;
        this.blocks     = blocks;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public Location[] getPosition() {
        return position;
    }

    public void setPosition(Location[] position) {
        this.position = position;
    }

    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }

    public void delete() {
        MinerRegions.remove(this);
    }
    public static MinerRegion getByName(String regionName) {
        return MinerRegions.stream().filter(e -> e.name.equals(regionName)).findAny().orElse(null);
    }

    public static boolean contains(String regionName) {
        return getByName(regionName) != null;
    }

}
