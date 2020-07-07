package creeper0809.discordbot.GameInfo;

import java.awt.Color;
import java.util.ArrayList;

import net.dv8tion.jda.api.EmbedBuilder;

public class ReinforceGameInfo {
	private String userName;
	private int money;
	private int HP;
	private int damage;
	private int critical;
	private int criticalDamage;
	private int upgradeFailed;

	public int getUpgradeFailed() {
		return upgradeFailed;
	}

	public void setUpgradeFailed(int upgradeFailed) {
		this.upgradeFailed = upgradeFailed;
	}

	private ArrayList<WeaponInfo> inventory = new ArrayList<WeaponInfo>();

	public ReinforceGameInfo(String userName) {
		this.userName = userName;
		money = 5000;
		HP = 50;
		damage = 10;
		critical = 5;
		criticalDamage = 10;
		upgradeFailed = 0;
		giveItem("Common", "기본단검", 0, 1000);
		giveItem("Common", "기본장검", 0, 1000);
		giveItem("Common", "기본활", 0, 1000);
		giveItem("Common", "기본수류탄", 0, 1000);
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		HP = hP;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getCritical() {
		return critical;
	}

	public void setCritical(int critical) {
		this.critical = critical;
	}

	public int getCriticalDamage() {
		return criticalDamage;
	}

	public void setCriticalDamage(int criticalDamage) {
		this.criticalDamage = criticalDamage;
	}

	public void setInventory(ArrayList<WeaponInfo> inventory) {
		this.inventory = inventory;
	}

	public void giveItem(String quality, String weaponName, int upgraded, int cost) {
		inventory.add(new WeaponInfo(quality, weaponName, upgraded, cost));
	}
	public void removeItem(WeaponInfo weaponName) {
		inventory.remove(weaponName);
	}
	public ArrayList<WeaponInfo> getInventory() {
		return inventory;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public WeaponInfo getWeapon(String weaponName) {
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).getWeaponName().equalsIgnoreCase(weaponName)) {
				return inventory.get(i);
			}
		}
		return null;
	}

}
