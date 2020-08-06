package creeper0809.discordbot.commands;

import java.util.Arrays;
import java.util.List;

import creeper0809.discordbot.gameinfo.UserInfo;
import creeper0809.discordbot.gameinfo.WeaponInfo;
import creeper0809.discordbot.objects.Constants;
import creeper0809.discordbot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ShowWeaponInfo implements ICommand {

	@Override
	public void handle(String args, MessageReceivedEvent e) {
		if (args.isEmpty())
			return;
		UserInfo userinfo = Constants.GAMEINFO.findUser(e.getAuthor().getName());
		WeaponInfo weapon = userinfo.getWeapon(args);
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle(userinfo.getUserName() + "님의 " + weapon.getProperName());
		eb.setDescription("등급:" + weapon.getQuality());
		eb.setColor(userinfo.getBackgroundColor());
		e.getChannel().sendMessage(eb.build()).queue();
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "무기 정보를 알려준다";
	}

	@Override
	public String getInvoke() {
		// TODO Auto-generated method stub
		return "내무기";
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("내무기");
	}

}
