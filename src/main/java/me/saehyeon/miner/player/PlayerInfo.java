package me.saehyeon.miner.player;

import me.saehyeon.miner.manager.UtilType;
import me.saehyeon.miner.region.MinerRegion;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

public class PlayerInfo {
    public static HashMap<UUID, PlayerInfo> playerInfoMap = new HashMap<>();

    MinerRegion selectedRegion;
    Inventory inventory;
    Location[] position = new Location[] { null, null };
    TaskType taskType;
    UtilType currentUtilType;

    public PlayerInfo() {
        selectedRegion = null;
        inventory = null;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setSelectedRegion(MinerRegion minerRegion) {
        selectedRegion = minerRegion;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
    public void setUtilType(UtilType currentUtilType) {
        this.currentUtilType = currentUtilType;
    }

    public UtilType getUtilType() {
        return currentUtilType;
    }

    public MinerRegion getSelectedRegion() {
        return selectedRegion;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Location[] getPosition() {
        return position;
    }

    public boolean isAllPositionSet() {
        return position[0] == null || position[1] == null;
    }

    public void setFirstPosition(Location pos1) {
        position[0] = pos1;
    }

    public void setSecondPosition(Location pos2) {
        position[1] = pos2;
    }

    public boolean isPositionSet() {
        return position[0] != null && position[1] != null;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public static PlayerInfo get(Player player) {
        return playerInfoMap.getOrDefault(player.getUniqueId(),null);
    }

    public static void set(Player player, PlayerInfo playerInfo) {
        playerInfoMap.put(player.getUniqueId(),playerInfo);
    }
}
