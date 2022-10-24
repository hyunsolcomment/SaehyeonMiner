package me.saehyeon.miner.region;

import me.saehyeon.miner.functions.Locationf;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MinerRegion implements Serializable {
    public static ArrayList<MinerRegion> MinerRegions = new ArrayList<>();

    public String name;
    public ArrayList<ItemStack> blocks = new ArrayList<>();
    Location[] position;
    float regenSecond = 0;

    public MinerRegion(String name, Location pos1, Location pos2) {
        this.name       = name;
        this.position   = new Location[] { pos1, pos2 };

    }

    public MinerRegion(String name, Location pos1, Location pos2, ArrayList<ItemStack> blocks) {
        this.name       = name;
        this.position   = new Location[] { pos1, pos2 };
        this.blocks     = blocks;
    }

    public MinerRegion(String name, Location[] position, ArrayList<ItemStack> blocks) {
        this.name       = name;
        this.position   = position;
        this.blocks     = blocks;
    }

    public String getName() {
        return name;
    }

    public void setRegenTime(float second) {
        regenSecond = second;
    }

    public float getRegenTime() { return regenSecond; }

    public ArrayList<ItemStack> getBlocks() {
        return blocks;
    }

    public Location[] getPosition() {
        return position;
    }

    public void setPosition(Location[] position) {
        this.position = position;
    }

    public void setBlocks(ArrayList<ItemStack> blocks) {
        this.blocks = blocks;
    }

    public void regen(Location blockLocation) {
        Block block = blockLocation.getBlock();

        if(block.getType() == Material.AIR) {

            BlockData regenBlockData = blocks.get( new Random().nextInt(blocks.size()) ).getType().createBlockData();

            block.setBlockData(regenBlockData);
        }

    }

    public void regenAll(boolean regenOnlyAir) {

        // 만약 리젠할 블럭이 등록되어 있지 않다면 리젠 작업 취소
        if(blocks.isEmpty())
            return;

        /* 모든 블럭 리젠 */
        Locationf.getLocations(position[0],position[1]).forEach(l -> {

            Material blockType = blocks.get( new Random().nextInt(blocks.size()) ).getType();

            // 공기블럭만 대체한다고 했을때는 공기블럭만 다시 리젠
            if(regenOnlyAir && l.getBlock().getType() == Material.AIR) {
                l.getBlock().setType( blockType );

            }

            else if(!regenOnlyAir) {

                // 모든 블럭 리젠
                l.getBlock().setType(blockType);

            }
        });

    }

    public void delete() {
        MinerRegions.remove(this);
    }

    public static MinerRegion getByName(String regionName) {
        return MinerRegions.stream().filter(e -> e.name.equals(regionName)).findAny().orElse(null);
    }

    public static List<String> getAllRegionNames() {
        List<String> result = new ArrayList<>();

        MinerRegion.MinerRegions.forEach(r -> result.add(r.getName()));
        return result;
    }

    public static MinerRegion getByLocation(Location blockLocation) {

        for(MinerRegion r : MinerRegion.MinerRegions) {

            if(!r.getBlocks().isEmpty()) {

                Location pos1 = r.getPosition()[0];
                Location pos2 = r.getPosition()[1];

                if( pos1 != null && pos2 != null && Locationf.isWithin(blockLocation,pos1,pos2) )
                    return r;

            }

        }

        return null;
    }

    public static boolean contains(String regionName) {
        return getByName(regionName) != null;
    }

}
