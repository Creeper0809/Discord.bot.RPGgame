package creeper0809.discordbot;

import java.util.ArrayList;

import creeper0809.discordbot.Items.Item;
import creeper0809.discordbot.Items.Upgradable;
import creeper0809.discordbot.Items.Upgradable.State;
import creeper0809.discordbot.Items.weapon.LongSword;
import creeper0809.discordbot.Items.weapon.Weapon;
import creeper0809.discordbot.gameinfo.UserInfo;

public class Test {

	public static void main(String[] args) {
		Weapon a = new LongSword().random();
		Item b = a.clone();
		a.doIdentify();
		a.upgrade();
		a.upgrade();
		b.upgrade();
		System.out.println(b.help());
		System.out.println(a.help());
	}

}
