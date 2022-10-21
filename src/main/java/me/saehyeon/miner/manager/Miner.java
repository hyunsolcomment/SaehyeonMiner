package me.saehyeon.miner.manager;

import me.saehyeon.miner.functions.Locationf;
import me.saehyeon.miner.main.SaehyeonMiner;
import me.saehyeon.miner.player.PlayerInfo;
import me.saehyeon.miner.region.MinerRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class Miner {

    public static void openGUI(Player player, UtilType utilType) {

        PlayerInfo pi                = PlayerInfo.get(player);
        MinerRegion selectedRegion   = pi.getSelectedRegion();

        player.closeInventory();

        switch (utilType) {

            // 리젠해야하는 블럭들 보여주기
            case SHOW_BLOCKS:

                pi.setInventory( Bukkit.createInventory(null, 54, selectedRegion+"에 랜덤으로 리젠될 블럭들") );

                // 리젠되어야 하는 블럭들을 로드하기
                ArrayList<Block> blocks = selectedRegion.getBlocks();

                if(blocks != null && !blocks.isEmpty()) {

                    for (int i = 0; i < blocks.size(); i++) {

                        player.getOpenInventory().setItem(i, new ItemStack(blocks.get(i).getType()));

                    }

                }

                break;


        }

    }

    public static void set(Player player, MinerRegion region) {
        PlayerInfo pi = PlayerInfo.get(player);

        Location pos1 = pi.getPosition()[0];
        Location pos2 = pi.getPosition()[1];

        if(pos1 == null || pos2 == null) {
            player.sendMessage("§c첫번째 지점 또는 두번째 지점이 지정되지 않았습니다. \n지점은 이름표로 블럭을 좌클릭 또는 우클릭하여 지정할 수 있습니다.");
            return;
        }

        /* 지역 등록 */
        MinerRegion.MinerRegions.removeIf(e -> e.getName().equals(region.getName()));
        MinerRegion.MinerRegions.add(region);

        player.sendMessage("§7"+region.getName()+"§f(을)를 등록했습니다.");

    }

    public static boolean setRegenBlocks(String regionName, ArrayList<Block> blocks) {

        if(!MinerRegion.contains(regionName)) return false;

        MinerRegion region = MinerRegion.getByName(regionName);
        region.setBlocks(blocks);

        return true;

    }

    public static boolean isHaveToRegen(Location blockLocation) {

        for(MinerRegion r : MinerRegion.MinerRegions) {

            if(!r.getBlocks().isEmpty()) {

                Location pos1 = r.getPosition()[0];
                Location pos2 = r.getPosition()[0];

                 if( pos1 != null && pos2 != null && Locationf.isWithin(blockLocation,pos1,pos2) )
                     return true;

            }

        }

        return false;
    }

    File getFile() throws IOException {

        File file = new File(SaehyeonMiner.instance.getDataFolder(), "regions.sae");
        file.createNewFile();

        return file;
    }

    public void save() {

        try {

            FileOutputStream fOutput = new FileOutputStream(getFile());
            BukkitObjectOutputStream objOutput = new BukkitObjectOutputStream(fOutput);
            objOutput.writeObject(MinerRegion.MinerRegions);

            objOutput.close();
            fOutput.close();

        } catch (Exception e) {
            System.out.println("§c[광물리젠: 오류] 파일을 저장하던 도중 오류가 발생했습니다. ("+e+")");
        }


    }

    public void load() {

        try {

            FileInputStream fInput = new FileInputStream(getFile());
            BukkitObjectInputStream objInput = new BukkitObjectInputStream(fInput);

            MinerRegion.MinerRegions = (ArrayList<MinerRegion>) objInput.readObject();

            objInput.close();
            fInput.close();

        } catch (Exception e) {

            System.out.println("§c[광물리젠: 오류] 파일을 불러오던 도중 오류가 발생했습니다. ("+e+")");

        }

    }

}
