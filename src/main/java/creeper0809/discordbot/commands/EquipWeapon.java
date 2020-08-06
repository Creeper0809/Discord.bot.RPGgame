package creeper0809.discordbot.commands;

import java.util.Arrays;
import java.util.List;

import creeper0809.discordbot.gameinfo.UserInfo;
import creeper0809.discordbot.gameinfo.WeaponInfo;
import creeper0809.discordbot.objects.Constants;
import creeper0809.discordbot.objects.ICommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class EquipWeapon implements ICommand {

	@Override
	public void handle(String args, MessageReceivedEvent e) {
		UserInfo userinfo = Constants.GAMEINFO.findUser(e.getAuthor().getName());
		if (userinfo.getEquipedWeapon() != null) {
			e.getChannel().sendMessage("이미 착용중인 장비가 있습니다").queue();
			return;
		}
		WeaponInfo weaponInfo = userinfo.getWeapon(args);
		if(weaponInfo == null) {
			e.getChannel().sendMessage("없는 장비 입니다").queue();
			return;
		}
		userinfo.equipWeapon(weaponInfo);
		e.getChannel().sendMessage(weaponInfo.getProperName() + "을(를) 착용하였습니다").queue();
	}

	@Override
	public String getHelp() {
		return "무기를 착용합니다.";
	}

	@Override
	public String getInvoke() {
		return "착용";
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("착용","장착");
	}

}
