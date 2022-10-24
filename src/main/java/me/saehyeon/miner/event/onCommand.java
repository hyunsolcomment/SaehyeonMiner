package me.saehyeon.miner.event;

import me.saehyeon.miner.functions.Locationf;
import me.saehyeon.miner.manager.ErrorType;
import me.saehyeon.miner.manager.Manager;
import me.saehyeon.miner.manager.Miner;
import me.saehyeon.miner.manager.UtilType;
import me.saehyeon.miner.player.PlayerInfo;
import me.saehyeon.miner.player.TaskType;
import me.saehyeon.miner.region.MinerRegion;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class onCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player)sender;
        PlayerInfo pi = PlayerInfo.get(p);

        MinerRegion minerRegion;

        if(label.equals("광물리젠")) {

            switch(args[0]) {
                case "debug":
                    minerRegion = MinerRegion.getByName(args[1]);
                    p.sendMessage(minerRegion.getName()+" / "+minerRegion.getBlocks());

                    break;

                case "목록":

                    if(MinerRegion.MinerRegions.isEmpty()) {
                        p.sendMessage("아무 지역도 등록되어 있지 않아요!");
                        return false;
                    }

                    /* 목록 로드 */
                    p.sendMessage("");

                    MinerRegion.MinerRegions.forEach(r -> {

                        Location pos1 = r.getPosition()[0];
                        Location pos2 = r.getPosition()[1];

                        p.sendMessage(r.getName()+": \n → §f리젠블럭 갯수: §7"+r.getBlocks().size()+"개 \n §f→ 리젠시간: §7"+r.getRegenTime()+"초 \n §f→ 범위: §7"+ Locationf.toString(pos1)+" ~ "+ Locationf.toString(pos2));

                    });
                    break;

                case "생성":

                    /* 플레이어의 지점 가져오기 */
                    Location pos1 = pi.getPosition()[0];
                    Location pos2 = pi.getPosition()[1];

                    // 제대로 지점이 정해져 있는지 확인
                    if(!pi.isPositionSet()) {
                        p.sendMessage("§c첫번째 지점 또는 두번째 지점이 지정되지 않았어요. 지점은 이름표로 블럭을 우클릭 또는 좌클릭하여 지정할 수 있어요.");
                        return false;
                    }

                    // 등록하고자 하는 임시 지역 인스턴스
                    MinerRegion tempRegion = new MinerRegion(args[1], pos1, pos2);

                    // 이미 등록된 지역
                    if(MinerRegion.contains(args[1])) {

                        /* 지역 선택 */
                        pi.setSelectedRegion(tempRegion);

                        /* TaskType 변경 */
                        pi.setTaskType(TaskType.CONFIRM_SET_REGION);

                        /* 클릭 가능한 컴포넌트 제작 */
                        TextComponent message   = new TextComponent("§a§l§n여기를 클릭");

                        // 채팅 클릭
                        ClickEvent clickEvent   = new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/광물리젠 계속진행");

                        message.setClickEvent(clickEvent);

                        // 채팅 호버
                        TextComponent hoverText = new TextComponent("같은 이름을 가지고 있는 지역이 삭제되고 새롭게 지역이 설정될거에요.");
                        HoverEvent hoverEvent   = new HoverEvent( HoverEvent.Action.SHOW_TEXT, new TextComponent[] { hoverText } );

                        message.setHoverEvent(hoverEvent);

                        /* 채팅 전송 */
                        p.sendMessage("\n§7§l"+args[1]+"§f§l(은)는 이미 등록되어 있는 지역입니다.");
                        p.spigot().sendMessage(ChatMessageType.CHAT, new TextComponent[] { message, new TextComponent("§f하여 지역 생성을 계속합니다.") });
                        p.sendMessage("");

                        return false;
                    }

                    // 등록되지 않은 지역 (바로 지역 등록)
                    Miner.set(p, tempRegion);

                    break;

                case "삭제":

                    /* 지역이 등록되어 있지 않음 */
                    if(!MinerRegion.contains(args[1])) {

                        Manager.sendErrorMessage(p, ErrorType.REGION_NOT_EXIST);

                        return false;
                    }

                    // 등록되지 않은 지역임 (삭제)
                    minerRegion = MinerRegion.getByName(args[1]);
                    minerRegion.delete();

                    p.sendMessage("§7"+args[1]+" §f지역을 삭제했어요.");
                    break;

                case "블럭설정":

                    /* 지역이 등록되어 있지 않음 */
                    if(!MinerRegion.contains(args[1])) {

                        Manager.sendErrorMessage(p, ErrorType.REGION_NOT_EXIST);

                        return false;
                    }

                    /* 블럭설정 GUI 열기 */
                    minerRegion = MinerRegion.getByName(args[1]);

                    // 지역 선택
                    pi.setSelectedRegion( minerRegion );

                    // GUI 열기
                    Miner.openGUI(p, UtilType.BLOCK_SETTING);

                    break;

                case "리젠시간설정":

                    /* 지역이 등록되어 있지 않음 */
                    if(!MinerRegion.contains(args[1])) {

                        Manager.sendErrorMessage(p, ErrorType.REGION_NOT_EXIST);

                        return false;
                    }

                    minerRegion = MinerRegion.getByName(args[1]);

                    /* 딜레이 설정 작업 시작 */

                    /* 컴포넌트 만들기 */
                    TextComponent cancelChat = new TextComponent("§c§n여기를 클릭");
                    cancelChat.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[] { new TextComponent("클릭하여 작업을 §c취소합니다.") } ));
                    cancelChat.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "exit"));

                    // 메세지 전송
                    p.sendMessage("");
                    p.sendMessage("§e§l"+args[1]+"§f§l 지역의 블럭이 리젠될 초를 입력해주세요.");
                    p.spigot().sendMessage(new TextComponent[] { new TextComponent("작업을 취소하려면 "), cancelChat, new TextComponent("§f하거나 exit를 입력해주세요.") } );
                    p.sendMessage("§70은 리젠시간이 없는 것 입니다.");

                    //p.sendMessage("작업을 취소하려면 §c§n여기를 클릭§f하거나 exit를 입력해주세요.");

                    pi.setTaskType(TaskType.REGEN_TIME_SET);
                    pi.setSelectedRegion(minerRegion);

                    break;

                case "계속진행":

                    TaskType taskType = pi.getTaskType();

                    if(taskType == null) {
                        p.sendMessage("§c이 명령은 만료되어 사용할 수 없어요.");
                        return false;
                    }

                    switch(pi.getTaskType()) {

                        /* 같은 이름을 가진 기존의 지역을 삭제하고 새로운 지역을 만드는 것에 동의함 */
                        case CONFIRM_SET_REGION:

                            Miner.set(p, pi.getSelectedRegion());
                            break;

                    }

                    break;

                case "리젠":

                    MinerRegion region = MinerRegion.getByName(args[1]);

                    /* 등록된 지역이 아님 */
                    if(region == null) {
                        Manager.sendErrorMessage(p,ErrorType.REGION_NOT_EXIST);
                        return false;
                    }

                    /* 리젠할 블럭이 없음 */
                    if(region.blocks.isEmpty()) {

                        TextComponent clickHere = new TextComponent("§c§l§n여기를 클릭");

                        clickHere.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/광물리젠 블럭설정 "+args[1]));
                        clickHere.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[] { new TextComponent("클릭하여 §7"+args[1]+"§f 지역의 블럭을 설정합니다.") } ));

                        p.spigot().sendMessage(new TextComponent[] { new TextComponent("§c"+args[1]+" 지역에 블럭이 등록되어 있지 않아요. "), clickHere, new TextComponent("§c하여 블럭을 설정할 수 있어요.") });

                        return false;
                    }

                    if(args[2].equals("모든블럭")) {

                        region.regenAll(false);
                        p.sendMessage("§7"+args[1]+" §f지역에 있는 모든 블럭을 리젠했어요.");

                    }

                    else if(args[2].equals("공기만")) {

                        region.regenAll(true);
                        p.sendMessage("§7"+args[1]+" §f지역에 있는 공기를 리젠했어요.");

                    } else {

                        Manager.sendErrorMessage(p, ErrorType.INVALID_COMMAND);
                    }

                    break;

                case "도움말":
                    p.sendMessage("");
                    p.sendMessage("§b/광물리젠 생성 [지역이름]: §f[지역이름]라는 이름을 가진 지역을 생성합니다.");
                    p.sendMessage("§b/광물리젠 삭제 [지역이름]: §f[지역이름]라는 이름을 가진 지역을 삭제합니다.");
                    p.sendMessage("§b/광물리젠 리젠시간설정 [지역이름] [0 또는 자연수]: §f[지역이름]라는 지역의 블럭이 파괴된 후 다시 리젠되는 시간(초)를 설정합니다. §7(0이라면 리젠시간이 없습니다.)");
                    p.sendMessage("§b/광물리젠 블럭설정 [지역이름]: §f[지역이름]라는 지역의 리젠될 블럭을 설정합니다.");
                    p.sendMessage("§b/광물리젠 리젠 [지역이름] [모든블럭/공기만]: §f[지역이름]라는 지역의 모든 블럭 또는 공기만을 다시 리젠합니다.");
                    p.sendMessage("§b/광물리젠 목록: §f현재 등록된 지역의 목록을 로드합니다.");
                    break;

                default:
                    Manager.sendErrorMessage(p, ErrorType.INVALID_COMMAND);
                    break;
            }
        }

        return false;
    }
}
