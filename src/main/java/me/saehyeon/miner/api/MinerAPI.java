package me.saehyeon.miner.api;

import me.saehyeon.miner.functions.Locationf;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.List;
import java.util.Random;

public class MinerAPI {
    public static void regenBlock(Location blockLocation, List<Block> blocks) {

        Block block = blocks.get(new Random().nextInt(blocks.size()));
        blockLocation.getBlock().setBlockData(block.getBlockData());

    }

    public static void regenBlocks(Location pos1, Location pos2, List<Block> blocks) {

        Block block = blocks.get( new Random().nextInt(blocks.size()) );

        Locationf.getLocations(pos1,pos2).forEach(l -> l.getBlock().setBlockData(block.getBlockData()) );

    }
}
