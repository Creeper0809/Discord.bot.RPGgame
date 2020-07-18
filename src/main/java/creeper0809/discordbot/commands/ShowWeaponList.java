package creeper0809.discordbot.commands;

import java.awt.Color;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import creeper0809.discordbot.gameinfo.UserInfo;
import creeper0809.discordbot.objects.Constants;
import net.dv8tion.jda.api.EmbedBuilder;

public class ShowWeaponList extends Command{
	public ShowWeaponList() {
		super.name = "무기목록";
		super.aliases = new String[]{"무기목록","내무기"};
		super.help = "내 무기 정보 보여주기";
	}
	@Override
	protected void execute(CommandEvent event) {
		if(Constants.GAMESYSTEMINFO.findUser(event.getAuthor().getName()) == null) {
			event.reply("게임 가입을 먼저 하십시오");
			return;
		}
		String[] msg = event.getMessage().getContentRaw().substring(1).split(" ");
		if(msg.length > 2) return;
		EmbedBuilder eb = new EmbedBuilder();
		UserInfo userinfo = Constants.GAMESYSTEMINFO.findUser(event.getAuthor().getName());
		StringBuilder description = new StringBuilder();
		for (int i = 1; i < userinfo.getInventory().size() + 1; i++) {
			if (userinfo.getInventory().get(i - 1).getUpgraded() == 0) {
				description.append(i + "." + userinfo.getInventory().get(i - 1).getWeaponName() + "\n");
			} else {
				description.append(i + "." + userinfo.getInventory().get(i - 1).getWeaponName() + " +"
						+ userinfo.getInventory().get(i - 1).getUpgraded() + "\n");
			}
		}
		eb.setColor(Color.black);
		eb.setTitle(userinfo.getUserName() + "님의 무기 목록", null);
		eb.setDescription("**" + description + "**");
		event.reply(eb.build());
	}

}
