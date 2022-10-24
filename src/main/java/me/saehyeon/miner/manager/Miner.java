package me.saehyeon.miner.manager;

import me.saehyeon.miner.main.SaehyeonMiner;
import me.saehyeon.miner.player.PlayerInfo;
import me.saehyeon.miner.region.MinerRegion;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Miner {

    public static void openGUI(Player player, UtilType utilType) {

        PlayerInfo pi                = PlayerInfo.get(player);
        MinerRegion selectedRegion   = pi.getSelectedRegion();

        player.closeInventory();
        pi.setUtilType(utilType);

        switch (utilType) {

            // 리젠해야하는 블럭들 보여주기
            case BLOCK_SETTING:

                pi.setInventory( Bukkit.createInventory(null, 54, selectedRegion.getName()+"에 랜덤으로 리젠될 블럭들") );
                player.openInventory( pi.getInventory() );

                // 리젠되어야 하는 블럭들을 로드하기
                ArrayList<ItemStack> blocks = selectedRegion.getBlocks();

                if(blocks != null && !blocks.isEmpty()) {

                    for (int i = 0; i < blocks.size(); i++)
                        player.getOpenInventory().setItem(i, new ItemStack(blocks.get(i).getType()));

                }

                break;
        }

    }

    public static void set(Player player, MinerRegion region) {

        // 지점이 지정되어 있는지 검사
        if( PlayerInfo.get(player).isAllPositionSet() ) {
            Manager.sendErrorMessage(player,ErrorType.INVALID_POSITION);
            return;
        }

        /* 지역 등록 */
        MinerRegion.MinerRegions.removeIf(e -> e.getName().equals(region.getName()));
        MinerRegion.MinerRegions.add(region);

        player.sendMessage("§7"+region.getName()+"§f(을)를 등록했습니다.");

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
