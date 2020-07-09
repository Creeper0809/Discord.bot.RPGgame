package creeper0809.discordbot.gameinfo;

public class WeaponInfo {
	
	private String weaponName;
	private String properName;
	private String quality;
	private int id;
	private int upgraded;
	private int cost;
	private int hp;
	private int damage;
	private int criticalPer;
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

	public String getProperName() {
		return properName;
	}

	public void setProperName(String properName) {
		this.properName = properName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getCriticalPer() {
		return criticalPer;
	}

	public void setCriticalPer(int criticalPer) {
		this.criticalPer = criticalPer;
	}

	public int getCriticalDamage() {
		return criticalDamage;
	}

	public void setCriticalDamage(int criticalDamage) {
		this.criticalDamage = criticalDamage;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getWeaponName() {
		return weaponName;
	}

	public void setWeaponName(String weaponName) {
		this.weaponName = weaponName;
	}

	public int getUpgraded() {
		return upgraded;
	}

	public void setUpgraded(int upgraded) {
		this.upgraded = upgraded;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

}
