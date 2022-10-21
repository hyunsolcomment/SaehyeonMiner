package me.saehyeon.miner.event;

import me.saehyeon.miner.player.PlayerInfo;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onJoin implements Listener {
    @EventHandler
    void onJoin(PlayerJoinEvent e) {

        Player p        = e.getPlayer();
        PlayerInfo pi   = PlayerInfo.get(p);

        if(pi == null)
            PlayerInfo.set(p, new PlayerInfo());
    }
}
