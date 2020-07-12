package creeper0809.discordbot.gameinfo;

import java.awt.Color;
import java.util.ArrayList;

import lombok.*;

public class UserInfo {
	
	@Getter
	private String userName;
	@Getter @Setter
	private Color backgroundColor = Color.black;
	@Getter @Setter
	private int money;
	@Getter @Setter
	private int HP;
	@Getter @Setter
	private int damage;
	@Getter @Setter
	private int critical;
	@Getter @Setter
	private int criticalDamage;
	@Getter @Setter
	private int upgradeFailed;
	@Getter @Setter
	private WeaponInfo equipedWeapon;
	@Getter
	private ArrayList<WeaponInfo> inventory = new ArrayList<WeaponInfo>();
	@Getter @Setter
	private String stage;

	public UserInfo(String userName) {
		this.userName = userName;
		money = 5000;
		HP = 50;
		damage = 10;
		critical = 5;
		criticalDamage = 10;
		upgradeFailed = 0;
		equipedWeapon = null;
		giveItem("기본단검");
	}
	public WeaponInfo getWeapon(String weaponName) {
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).getProperName().equalsIgnoreCase(weaponName)) {
				return inventory.get(i);
			}
		}
		return null;
	}
	public void equipWeapon(WeaponInfo weaponinfo) {
		this.equipedWeapon = weaponinfo;
		this.HP +=weaponinfo.getHp();
		this.damage += weaponinfo.getDamage();
		this.critical += weaponinfo.getCriticalPer();
		this.criticalDamage +=weaponinfo.getCriticalDamage();
	}
	public void disarmWeapon(WeaponInfo weaponinfo) {
		this.equipedWeapon = null;
		this.HP -=weaponinfo.getHp();
		this.damage -= weaponinfo.getDamage();
		this.critical -= weaponinfo.getCriticalPer();
		this.criticalDamage -=weaponinfo.getCriticalDamage();
	}
	public void giveItem(String weaponName) {
		inventory.add(StaticFile.gameSystemInfo.findWeapon(weaponName));
	}

	public void removeItem(WeaponInfo weaponName) {
		inventory.remove(weaponName);
	}
}
