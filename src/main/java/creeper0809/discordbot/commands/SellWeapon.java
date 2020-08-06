package creeper0809.discordbot.commands;

import java.util.Arrays;
import java.util.List;

import creeper0809.discordbot.gameinfo.UserInfo;
import creeper0809.discordbot.gameinfo.WeaponInfo;
import creeper0809.discordbot.objects.Constants;
import creeper0809.discordbot.objects.ICommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SellWeapon implements ICommand {

	@Override
	public void handle(String args, MessageReceivedEvent e) {
		UserInfo userinfo = Constants.GAMEINFO.findUser(e.getAuthor().getName());
		WeaponInfo weaponInfo = userinfo.getWeapon(args);
		userinfo.setMoney(userinfo.getMoney() + 10000 * weaponInfo.getUpgraded());
		e.getChannel().sendMessage(weaponInfo.getProperName()+"가 팔려 소지하고 계신 돈이"+userinfo.getMoney()+"원이 되었습니다").queue();
		userinfo.removeItem(weaponInfo);
	}

	@Override
	public String getHelp() {
		return "무기를 판매 합니다";
	}

	@Override
	public String getInvoke() {
		return "판매";
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("판매");
	}

}
