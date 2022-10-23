package me.saehyeon.miner.event;

import me.saehyeon.miner.main.SaehyeonMiner;
import me.saehyeon.miner.manager.Miner;
import me.saehyeon.miner.region.MinerRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class onBlock implements Listener {
    @EventHandler
    void onBreak(BlockBreakEvent e) {
        Player p        = e.getPlayer();
        Location loc    = e.getBlock().getLocation();

        // 파괴한 블럭의 좌표가 속해있는 지역 얻기
        MinerRegion minerRegion = MinerRegion.getByLocation(loc);

        if(minerRegion != null) {

            // 리젠 시간 초
            float regenTime = minerRegion.getRegenTime();

            // 최종 리젠 시간 ticks
            long delay = (long)(regenTime == 0 ? 1 : regenTime*20);

            /* 리젠하기 */
            Bukkit.getScheduler().runTaskLater(SaehyeonMiner.instance, () -> {

                minerRegion.regen(loc);

            },delay);

        }
    }
}
