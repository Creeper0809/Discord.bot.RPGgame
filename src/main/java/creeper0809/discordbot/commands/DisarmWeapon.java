package creeper0809.discordbot.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import creeper0809.discordbot.gameinfo.UserInfo;
import creeper0809.discordbot.gameinfo.WeaponInfo;
import creeper0809.discordbot.objects.Constants;
import creeper0809.discordbot.objects.ICommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DisarmWeapon implements ICommand {

	@Override
	public void handle(String args, MessageReceivedEvent e) {
		UserInfo userinfo = Constants.GAMESYSTEMINFO.findUser(e.getAuthor().getName());
		if (userinfo.getEquipedWeapon() == null) {
			e.getChannel().sendMessage("장착중인 장비가 없습니다").queue();
			return;
		}
		WeaponInfo weaponInfo = userinfo.getWeapon(args);
		if(weaponInfo == null) {
			e.getChannel().sendMessage("없는 장비 입니다").queue();
			return;
		}
		userinfo.disarmWeapon(weaponInfo);
		e.getChannel().sendMessage(weaponInfo.getProperName() + "을(를) 해제하였습니다").queue();
	}

	@Override
	public String getHelp() {
		return "무기를 착용해제합니다.";
	}

	@Override
	public String getInvoke() {
		return "착용해제";
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("장착해제","착용해제");
	}

}
