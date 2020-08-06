package creeper0809.discordbot.Items.weapon;

import java.util.Random;

import creeper0809.discordbot.Items.Upgradable;
import creeper0809.discordbot.Items.Item;
import creeper0809.discordbot.gameinfo.UserInfo;
import creeper0809.discordbot.objects.Constants;

public abstract class Weapon extends Upgradable {
	{
		state = state.무기;
	}
	@Override
	public String doEquip(UserInfo user) {
		
		if (user.belonging.weapon != null) {
			return "이미 장착중인 장비가 있습니다.";
		}
		user.belonging.weapon = this;
		super.doEquip(user);
		return "장비가 장착되었습니다";
	}

	@Override
	public String doUnequip(UserInfo user) {
		if (user.belonging.weapon == null) {
			return "장착중인 장비가 없습니다.";
		}
		user.belonging.weapon = null;
		super.doUnequip(user);
		return "장착중인 장비가 해제되었습니다";
	}

	@Override
	public Weapon random() {
		Random r = new Random();
		int ran = r.nextInt(100) + 1;
		if (ran <= 70)
			potentialQuaity = "레어";
		else if (ran <= 99 && ran >= 71)
			potentialQuaity = "에픽";
		else
			potentialQuaity = "유니크";
		return this;
	}

	@Override
	public void randomOption() {
		if(sealed) {
			return;
		}
		if (potentialQuaity.equals("레어")) {
			for (int i = 0; i < option.length; i++) {
				option[i] = Constants.rareWeaponOption[new Random().nextInt(Constants.rareWeaponOption.length)];
			}
		}

	}
}
