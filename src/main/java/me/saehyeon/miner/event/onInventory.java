package me.saehyeon.miner.event;

import me.saehyeon.miner.manager.ErrorType;
import me.saehyeon.miner.manager.Manager;
import me.saehyeon.miner.manager.UtilType;
import me.saehyeon.miner.player.PlayerInfo;
import me.saehyeon.miner.region.MinerRegion;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class onInventory implements Listener {

    @EventHandler
    void onInventoryClose(InventoryCloseEvent e) {
        Player p        = (Player)e.getPlayer();
        PlayerInfo pi   = PlayerInfo.get(p);

        UtilType utilType           = pi.getUtilType();
        MinerRegion selectedRegion  = pi.getSelectedRegion();

        /* GUI 관련 작업 */
        if(utilType != null) {

            switch(utilType) {

                /* 지역에 대한 리젠될 블럭들 저장 */
                case BLOCK_SETTING:

                    // 현재 GUI에 있는 블럭 아이템들을 Block형 배열에 저장
                    ArrayList<ItemStack> blocks = new ArrayList<>();
                    ItemStack[] contents = p.getOpenInventory().getTopInventory().getContents();

                    for(ItemStack item : contents) {

                        // 아이템이 블럭일 경우만 배열에 저장
                        if (item != null && item.getType().isBlock())
                            blocks.add( item );

                    }

                    selectedRegion.setBlocks(blocks);
                    break;

            }

            // 작업 완료 후 플레이어의 현재 UtilType 없애기
            pi.setUtilType(null);
        }

    }

    @EventHandler
    void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        PlayerInfo pi = PlayerInfo.get(p);
        ItemStack item = e.getCurrentItem();

        UtilType utilType = pi.getUtilType();

        if (utilType != null) {

            /* GUI 관련 작업 */
            switch (utilType) {

                // 블럭을 설정하고 있음
                case BLOCK_SETTING:

                    /* 블럭이 아닌 아이템의 클릭을 취소하기 */
                    if (item != null && !item.getType().isBlock()) {
                        e.setCancelled(true);
                        Manager.sendErrorMessage(p, ErrorType.INVALID_ITEM);
                    }

                    break;

            }

        }

    }

}
