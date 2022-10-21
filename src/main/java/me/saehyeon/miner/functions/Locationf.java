package me.saehyeon.miner.functions;

import org.bukkit.Location;

public class Locationf {
    public static String toString(Location location) {
        return String.format("%.2f", location.getX())+", "+String.format("%.2f", location.getY())+", "+String.format("%.2f", location.getZ());
    }
}
