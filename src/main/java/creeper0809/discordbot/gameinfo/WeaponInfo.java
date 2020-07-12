package creeper0809.discordbot.gameinfo;

import lombok.Getter;
import lombok.Setter;

public class WeaponInfo {
	@Getter
	private String weaponName;
	@Getter
	@Setter
	private String properName;
	@Getter
	private String quality;
	@Getter
	private int id;
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

}
