package me.saehyeon.miner.manager;

import me.saehyeon.miner.player.PlayerInfo;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Manager {
    public static void sendErrorMessage(Player player, ErrorType errorType) {

        switch(errorType) {

            // 지역이 등록되어 있지 않음
            case REGION_NOT_EXIST:

                /* 일반 텍스트 */
                TextComponent message = new TextComponent("§c등록되지 않은 지역이에요. 지역 목록을 보려면 ");

                /* 클릭하여 지역 목록을 보는 컴포넌트 제작*/
                TextComponent listMessage = new TextComponent("§c§l§n여기를 클릭해주세요.");

                listMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/광물리젠 목록"));

                // 호버 시 텍스트
                TextComponent hoverMessage = new TextComponent("클릭하면 등록된 지역 목록을 볼 수 있어요.");
                listMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[] { hoverMessage } ));

                /* 채팅 전송 */
                player.spigot().sendMessage(ChatMessageType.CHAT, new TextComponent[] { message, listMessage } );
                break;

            // 첫번째, 두번째 지점이 지정되지 않음
            case INVALID_POSITION:

                PlayerInfo pi = PlayerInfo.get(player);

                Location pos1 = pi.getPosition()[0];
                Location pos2 = pi.getPosition()[1];

                if(pos1 == null || pos2 == null) {
                    player.sendMessage("§c첫번째 지점 또는 두번째 지점이 지정되지 않았습니다. \n지점은 이름표로 블럭을 좌클릭 또는 우클릭하여 지정할 수 있습니다.");
                    return;
                }

                break;

            // GUI - 아이템 선택은 블럭만 가능함
            case INVALID_ITEM:
                player.sendMessage("§c블럭만 리젠될 블럭에 등록할 수 있어요!");
                break;

            // Task - 시간이 올바르지 않음
            case INVALID_TIME:
                player.sendMessage("§c시간은 0 미만일 수 없어요.");
                break;

        }
    }
}
