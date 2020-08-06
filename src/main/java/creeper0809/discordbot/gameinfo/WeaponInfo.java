package creeper0809.discordbot.gameinfo;

import lombok.Getter;
import lombok.Setter;

public class WeaponInfo implements Cloneable{
	@Getter
	private String weaponName;
	@Getter
	@Setter
	private String properName;
	@Getter
	private String quality;
	@Getter
	@Setter
	private int upgraded;
	@Getter
	@Setter
	private int cost;
	@Getter
	@Setter
	private int hp;
	@Getter
	@Setter
	private int damage;
	@Getter
	@Setter
	private int criticalPer;
	@Getter
	@Setter
	private int criticalDamage;

	public WeaponInfo(String quality, String weaponname, int upgrade, int cost) {
		this.quality = quality;
		this.weaponName = weaponname;
		this.properName = weaponname;
		this.upgraded = upgrade;
		this.cost = cost;
		hp = 50;
		damage = 10;
		criticalPer = 40;
		criticalDamage = 30;
	}
	public WeaponInfo clone() {
		WeaponInfo info = null;
		try {
			info = (WeaponInfo) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}

}
