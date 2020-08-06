package creeper0809.discordbot.commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import creeper0809.discordbot.gameinfo.UserInfo;
import creeper0809.discordbot.gameinfo.WeaponInfo;
import creeper0809.discordbot.objects.Constants;
import creeper0809.discordbot.objects.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;

public class UpgradeWeapon implements ICommand {
	@Override
	public void handle(String args, MessageReceivedEvent e) {
		UserInfo userinfo = Constants.GAMESYSTEMINFO.findUser(e.getAuthor().getName());
		if (userinfo.getUpgradingWeaponName() != null) {
			e.getChannel().sendMessage("이미 강화가 진행중입니다").queue();
			return;
		}
		userinfo.setUpgradingWeaponName(userinfo.getWeapon(args));
		if (userinfo.getMoney() >= userinfo.getUpgradingWeaponName().getCost()) {
			WeaponInfo weapon = userinfo.getUpgradingWeaponName();
			String description = "강화시 " + (weapon.getUpgraded() + 1) + "성이 됩니다.\n" + weapon.getCost() + "원 소모\n"
					+ "성공확률:" + Constants.sucessPercentage[weapon.getUpgraded()] + "%\n" + "실패확률:"
					+ (100 - (Constants.destroyPercentage[weapon.getUpgraded()] + Constants.sucessPercentage[weapon.getUpgraded()])) + "%\n"
					+ "파괴확률:" + Constants.destroyPercentage[weapon.getUpgraded()] + "%";
			EmbedBuilder eb = new EmbedBuilder().setTitle(weapon.getUpgraded() + "성 입니다 강화 하시겠습니까?")
					.setColor(Color.black).setDescription(description);

			e.getAuthor().openPrivateChannel().queue((channel) -> {
				channel.sendMessage(eb.build()).queue(message -> {
					message.addReaction("✔️").queue();
					message.addReaction("❌").queue();
					message.delete().submitAfter(10, TimeUnit.SECONDS);
				});
			});
		}

	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "강화를 한다";
	}

	@Override
	public String getInvoke() {
		// TODO Auto-generated method stub
		return "강화도전";
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("강화도전");
	}
}
