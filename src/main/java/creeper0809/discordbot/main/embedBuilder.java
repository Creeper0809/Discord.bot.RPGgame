package creeper0809.discordbot.main;

import java.awt.Color;

import creeper0809.discordbot.gameinfo.UserInfo;
import creeper0809.discordbot.gameinfo.WeaponInfo;
import net.dv8tion.jda.api.EmbedBuilder;

public class embedBuilder {
	private EmbedBuilder eb = new EmbedBuilder();

	public EmbedBuilder showWeaponInfo(UserInfo RFIF, WeaponInfo weapon) {
		eb.setTitle(RFIF.getUserName() + "님의 " + weapon.getProperName());
		eb.setDescription("등급:" + weapon.getQuality());
		eb.setColor(RFIF.getBackgroundColor());
		return eb;
	}
}
