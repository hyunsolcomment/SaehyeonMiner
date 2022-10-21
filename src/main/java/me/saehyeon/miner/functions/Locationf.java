package me.saehyeon.miner.functions;

import org.bukkit.Location;

import java.util.ArrayList;

public class Locationf {
    public static String toString(Location location) {
        return String.format("%.2f", location.getX())+", "+String.format("%.2f", location.getY())+", "+String.format("%.2f", location.getZ());
    }

    public static boolean isWithin(Location standard, Location pos1, Location pos2) {
        double minX = Math.min(pos1.getX(),pos2.getX());
        double minY = Math.min(pos1.getY(),pos2.getY());
        double minZ = Math.min(pos1.getZ(),pos2.getZ());

        double maxX = Math.max(pos1.getX(),pos2.getX());
        double maxY = Math.max(pos1.getY(),pos2.getY());
        double maxZ = Math.max(pos1.getZ(),pos2.getZ());

        if(minX <= standard.getX() && standard.getX() <= maxX) {

            if(minY <= standard.getY() && standard.getY() <= maxY) {

                if(minZ <= standard.getZ() && standard.getZ() <= maxZ) {

                    return true;

                }

            }

        }

        return false;
    }

    public static ArrayList<Location> getLocations(Location pos1, Location pos2) {

        ArrayList<Location> result = new ArrayList<>();

        double minX = Math.min(pos1.getX(),pos2.getX());
        double minY = Math.min(pos1.getY(),pos2.getY());
        double minZ = Math.min(pos1.getZ(),pos2.getZ());

        double maxX = Math.max(pos1.getX(),pos2.getX());
        double maxY = Math.max(pos1.getY(),pos2.getY());
        double maxZ = Math.max(pos1.getZ(),pos2.getZ());

        for(double x = minX; x <= maxX; x++) {
            for(double y = minY; y <= maxY; y++) {
                for(double z = minZ; z <= maxZ; z++) {

                    result.add(new Location(pos1.getWorld(),x,y,z));

                }
            }
        }

        return result;
    }
}
