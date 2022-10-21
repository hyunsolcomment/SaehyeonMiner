package me.saehyeon.miner.event;

import me.saehyeon.miner.functions.Locationf;
import me.saehyeon.miner.manager.Miner;
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

        if(label.equals("광물리젠")) {

            switch(args[0]) {
                case "debug":

                    MinerRegion region = MinerRegion.getByName(args[1]);
                    p.sendMessage(region.getName()+" / "+region.getBlocks());

                    break;

                case "불러오기":
                    new Miner().load();
                    p.sendMessage(".yml 파일들을 다시 불러왔어요.");
                    break;

                case "목록":

                    if(MinerRegion.MinerRegions.isEmpty()) {
                        p.sendMessage("아무 지역도 등록되어 있지 않아요!");
                        return false;
                    }

                    MinerRegion.MinerRegions.forEach(r -> {

                        Location pos1 = r.getPosition()[0];
                        Location pos2 = r.getPosition()[1];

                        p.sendMessage("§7"+r.getName()+"§f 지역에 §7"+r.getBlocks().size()+"개§f의 블럭이 등록되어 있습니다. ("+ Locationf.toString(pos1)+"~"+ Locationf.toString(pos2)+")");

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

                    if(!MinerRegion.contains(args[1])) {

                        /* 일반 텍스트 */
                        TextComponent message = new TextComponent("§c등록되지 않은 지역이에요. 지역 목록을 보려면 ");

                        /* 클릭하여 지역 목록을 보는 컴포넌트 제작*/
                        TextComponent listMessage = new TextComponent("§c§l§n여기를 클릭해주세요.");

                        listMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/광물리젠 목록"));

                        // 호버 시 텍스트
                        TextComponent hoverMessage = new TextComponent("클릭하면 등록된 지역 목록을 볼 수 있어요.");
                        listMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[] { hoverMessage } ));

                        /* 채팅 전송 */
                        p.spigot().sendMessage(ChatMessageType.CHAT, new TextComponent[] { message, listMessage } );

                        return false;
                    }

                    // 등록되지 않은 지역임 (삭제)
                    MinerRegion minerRegion = MinerRegion.getByName(args[1]);
                    minerRegion.delete();
                    p.sendMessage("§7"+args[1]+" §f지역을 삭제했어요.");
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

                default:
                    break;
            }
        }

        return false;
    }
}
