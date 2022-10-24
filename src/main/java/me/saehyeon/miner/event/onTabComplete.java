package me.saehyeon.miner.event;

import me.saehyeon.miner.main.SaehyeonMiner;
import me.saehyeon.miner.region.MinerRegion;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class onTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(alias.equals("광물리젠")) {

            if(args.length == 1) {
                return Arrays.asList("생성","삭제","목록","블럭설정","불러오기","리젠시간설정", "도움말", "리젠");
            }

            if(args.length == 2) {

                switch (args[0]) {
                    case "생성":
                    case "삭제":
                    case "블럭설정":
                    case "리젠시간설정":
                    case "리젠":
                        return MinerRegion.getAllRegionNames();

                    case "불러오기":
                        List<String> fileNames = new ArrayList<>();

                        File[] files = SaehyeonMiner.instance.getDataFolder().listFiles();
                        for(File file : files)
                            fileNames.add(file.getName());

                        return fileNames;

                }
                return null;
            }

            if(args.length == 3) {

                switch (args[0]) {
                    case "리젠":
                        return Arrays.asList("모든블럭","공기만");
                }
            }

        }

        return null;
    }
}
