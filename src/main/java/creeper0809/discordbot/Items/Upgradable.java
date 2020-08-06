package creeper0809.discordbot.Items;

import java.util.Random;

import creeper0809.discordbot.gameinfo.UserInfo;
import creeper0809.discordbot.gameinfo.WeaponInfo;
import creeper0809.discordbot.objects.Constants;

public abstract class Upgradable extends Item {

	public String name = "";
	protected int hp = 0;
	protected int attackDamage = 0;
	protected int abilityPower = 0;
	protected int criticalPer = 0;
	protected int criticalDamage = 0;
	protected String potentialQuaity = "";
	public State state;
	protected String[] option = new String[2];

	public boolean sealed = true;

	public enum State {
		무기, 갑옷, 신발, 모자, 반지, 목걸이
	}
	@Override
	public String upgrade() {
		Random r = new Random();
		int num = r.nextInt(100) + 1;
		if (num <= Constants.sucessPercentage[upgrade]) {
			upgrade++;
			reName();
			return "성공";
		} else {
			num = r.nextInt(100) + 1;
			if (num <= Constants.destroyPercentage[upgrade]) {
				upgrade = 12;
				reName();
				return "파괴되어 원래 힘을 잃어버렸습니다(12성이 되었습니다)";
			} else {
				if (upgrade < 11 || upgrade == 15 || upgrade == 20) {
					return "강화 실패(강화 수치 유지)";
				}
				upgrade--;
				reName();
				return "강화 실패(강화 수치 하락)";
			}
		}

	}

	public String reName() {
		name = trueName + " +" + upgrade;
		return name;
	}

	public String doEquip(UserInfo user) {
		user.setHP(user.getHP() + this.hp);
		user.setCritical(user.getCritical() + this.criticalPer);
		user.setCriticalDamage(user.getCriticalDamage() + this.criticalDamage);
		user.setDamage(user.getDamage() + this.attackDamage);
		return "";
	}

	public String doUnequip(UserInfo user) {
		user.setHP(user.getHP() - this.hp);
		user.setCritical(user.getCritical() - this.criticalPer);
		user.setCriticalDamage(user.getCriticalDamage() - this.criticalDamage);
		user.setDamage(user.getDamage() - this.attackDamage);
		return "";

	}

	public abstract Item random();

	public void doIdentify() {
		sealed = false;
		randomOption();
	}

	public abstract void randomOption();
}
