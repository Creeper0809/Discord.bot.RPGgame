package creeper0809.discordbot.gamesystem;

import java.util.Random;

import creeper0809.discordbot.gameinfo.UserInfo;
import creeper0809.discordbot.gameinfo.WeaponInfo;
import creeper0809.discordbot.objects.Constants;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReactionListener extends ListenerAdapter {

	public void onPrivateMessageReactionAdd(PrivateMessageReactionAddEvent e) {
		if (!e.getChannel().retrieveMessageById(e.getMessageId()).complete().getAuthor().isBot()) {
			return;
		}
		if (e.getReactionEmote().getName().equals("✔️") && !e.getUser().equals(e.getJDA().getSelfUser())) {
			e.getChannel().retrieveMessageById(e.getMessageId()).complete().delete().queue();
			upgradeWeapon(e);
			Constants.GAMEINFO.findUser(e.getUser().getName()).setUpgradingWeaponName(null);
		} else if (e.getReactionEmote().getName().equals("❌") && !e.getUser().equals(e.getJDA().getSelfUser())) {
			e.getChannel().retrieveMessageById(e.getMessageId()).complete().delete().queue();
			Constants.GAMEINFO.findUser(e.getUser().getName()).setUpgradingWeaponName(null);
		}
	}

	public void upgradeWeapon(PrivateMessageReactionAddEvent e) {
		UserInfo userinfo = Constants.GAMEINFO.findUser(e.getUser().getName());
		WeaponInfo weaponinfo = userinfo.getUpgradingWeaponName();
		userinfo.setMoney(userinfo.getMoney() - weaponinfo.getCost());
		if (userinfo.getUpgradeFailed() == 2) {
			weaponinfo.setUpgraded(weaponinfo.getUpgraded() + 1);
			weaponinfo.setProperName(weaponinfo.getWeaponName() + " +" + weaponinfo.getUpgraded());
			userinfo.setUpgradeFailed(0);
			e.getChannel().sendMessage("연속 2번 강화 수치가 하락하여 100%성공 하였습니다").queue();
		}
		Random r = new Random();
		int num = r.nextInt(100) + 1;
		if (num <= Constants.sucessPercentage[weaponinfo.getUpgraded()]) {
			weaponinfo.setUpgraded(weaponinfo.getUpgraded() + 1);
			weaponinfo.setProperName(weaponinfo.getWeaponName() + " +" + weaponinfo.getUpgraded());
			userinfo.setUpgradeFailed(0);
			e.getChannel().sendMessage("성공").queue();
		} else {
			num = r.nextInt(100) + 1;
			if (num <= Constants.destroyPercentage[weaponinfo.getUpgraded()]) {
				userinfo.removeItem(weaponinfo);
				e.getChannel().sendMessage("파괴").queue();
			} else {
				if (weaponinfo.getUpgraded() < 11 || weaponinfo.getUpgraded() == 15 || weaponinfo.getUpgraded() == 20) {
					userinfo.setUpgradeFailed(0);
					e.getChannel().sendMessage("강화 실패(강화 수치 유지)").queue();
				}
				weaponinfo.setUpgraded(weaponinfo.getUpgraded() - 1);
				userinfo.setUpgradeFailed(userinfo.getUpgradeFailed() + 1);
				weaponinfo.setProperName(weaponinfo.getWeaponName() + " +" + weaponinfo.getUpgraded());
				e.getChannel().sendMessage("강화 실패(강화 수치 하락)").queue();
			}
		}

	}
}
