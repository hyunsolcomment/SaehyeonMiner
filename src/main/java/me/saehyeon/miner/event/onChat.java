package me.saehyeon.miner.event;

import me.saehyeon.miner.manager.ErrorType;
import me.saehyeon.miner.manager.Manager;
import me.saehyeon.miner.player.PlayerInfo;
import me.saehyeon.miner.player.TaskType;
import me.saehyeon.miner.region.MinerRegion;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class onChat implements Listener {
    @EventHandler
    void onChat(AsyncPlayerChatEvent e) {
        Player p        = e.getPlayer();
        PlayerInfo pi   = PlayerInfo.get(p);

        String message  = e.getMessage();

        MinerRegion minerRegion = pi.getSelectedRegion();

        /* 현재 진행 중인 TaskType에 대하여 작업 */
        TaskType taskType = pi.getTaskType();

        if(taskType != null) {

            switch (taskType) {

                // 채팅에 초를 입력하여 리젠시간(초)을 설정하는 작업
                case REGEN_TIME_SET:
                    e.setCancelled(true);

                    // 작업 취소
                    if(message.equals("exit")) {
                        pi.setTaskType(null);
                        p.sendMessage("작업을 취소했습니다.");
                        return;
                    }

                    try {

                        float time = Float.parseFloat(message);

                        /* 0 또는 자연수 인지 확인 */
                        if(time < 0) {
                            Manager.sendErrorMessage(p, ErrorType.INVALID_TIME);
                            return;
                        }

                        float oldTime = minerRegion.getRegenTime();

                        minerRegion.setRegenTime(time);

                        p.sendMessage("");
                        p.sendMessage(minerRegion.getName()+" 지역의 블럭리젠 시간이 §7"+time+"초§f로 변경됐어요. §7(변경되기 전에는 "+oldTime+"초였습니다.)");
                        pi.setTaskType(null);

                    } catch (Exception _e) {

                        Manager.sendErrorMessage(p, ErrorType.INVALID_TIME);

                    }

                    break;

            }
        }
    }

}
