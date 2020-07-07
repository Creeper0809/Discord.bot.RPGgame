package creeper0809.discordbot.GameInfo;

public class WeaponInfo {

	private String quality;
	private String weaponName;
	private int upgraded;
	private int cost;

	public WeaponInfo(String quality, String weaponName, int upgrade, int cost) {
		this.quality = quality;
		this.weaponName = weaponName;
		this.upgraded = upgrade;
		this.cost = cost;
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
