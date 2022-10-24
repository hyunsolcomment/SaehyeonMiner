package me.saehyeon.miner.event;

import me.saehyeon.miner.manager.ErrorType;
import me.saehyeon.miner.manager.Manager;
import me.saehyeon.miner.manager.UtilType;
import me.saehyeon.miner.player.PlayerInfo;
import me.saehyeon.miner.region.MinerRegion;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
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

                    /* 블럭설정 종료 후, 모든 변경사항을 적용할지 물어보기 */
                    MinerRegion region = PlayerInfo.get(p).getSelectedRegion();

                    TextComponent regenAll = new TextComponent("§f§l[ 모든 블럭을 다시 리젠하기 ]");
                    TextComponent regenAir = new TextComponent("§f§l[ 공기블럭만 리젠하기 ]");

                    regenAll.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/광물리젠 리젠 "+selectedRegion.getName()+" 모든블럭"));
                    regenAll.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[] { new TextComponent("클릭하여 모든 블럭을 다시 리젠합니다.") } ));

                    regenAir.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/광물리젠 리젠 "+selectedRegion.getName()+" 공기만"));
                    regenAir.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[] { new TextComponent("클릭하여 지역 내 공기 블럭을 다시 리젠합니다.") } ));

                    p.sendMessage("\n"+region.getName()+" 지역에 대한 블럭설정이 변경되었어요.");

                    p.spigot().sendMessage(new TextComponent[] { regenAll, new TextComponent(" "), regenAir });
                    break;

            }

            // 작업 완료 후 플레이어의 현재 UtilType 없애기
            pi.setUtilType(null);
            pi.setSelectedRegion(null);
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
