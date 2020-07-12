package creeper0809.discordbot.reinforcegame;

import java.util.ArrayList;
import java.util.Random;

import creeper0809.discordbot.gameinfo.StaticFile;
import creeper0809.discordbot.gameinfo.UserInfo;
import creeper0809.discordbot.gameinfo.WeaponInfo;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class ReinForceGame {

	UserInfo RFIF;
	User ReinforceName;
	
	public int[] sucessPercentage = {95, 90, 85, 85, 80, 75, 70, 65, 60, 55, 45, 35, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30,
			3, 2, 1 };
	public int[] destroyPercentage = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 2, 3, 3, 7, 7, 19, 29, 40 };



	public String upgradeWeapon(WeaponInfo weaponinfo) {
		RFIF.setMoney(RFIF.getMoney() - weaponinfo.getCost());
		if(RFIF.getUpgradeFailed() == 2) {
			weaponinfo.setUpgraded(weaponinfo.getUpgraded() + 1);
			weaponinfo.setProperName(weaponinfo.getWeaponName() + " +"+weaponinfo.getUpgraded());
			RFIF.setUpgradeFailed(0);
			return "연속 2번 강화 수치가 하락하여 100%성공 하였습니다";
		}
		Random r = new Random();
		int num = r.nextInt(100) + 1;
		if (num <= sucessPercentage[weaponinfo.getUpgraded()]) {
			weaponinfo.setUpgraded(weaponinfo.getUpgraded() + 1);
			weaponinfo.setProperName(weaponinfo.getWeaponName() + " +"+weaponinfo.getUpgraded());
			System.out.println(weaponinfo.getProperName());
			RFIF.setUpgradeFailed(0);
			return "성공!";
		} else {
			num = r.nextInt(100) + 1;
			if (num <= destroyPercentage[weaponinfo.getUpgraded()]) {
				RFIF.removeItem(weaponinfo);
				return "파괴...";
			} else {
				if(weaponinfo.getUpgraded()<11||weaponinfo.getUpgraded() == 15||weaponinfo.getUpgraded() == 20) {
					RFIF.setUpgradeFailed(0);
					return "강화 수치 유지";
				}
				weaponinfo.setUpgraded(weaponinfo.getUpgraded() - 1);
				RFIF.setUpgradeFailed(RFIF.getUpgradeFailed()+1);
				weaponinfo.setProperName(weaponinfo.getWeaponName() + " +"+weaponinfo.getUpgraded());
				return "강화 수치 하락...";
			}
		}
	}
	public User getReinforceName() {
		return ReinforceName;
	}

	public void setReinforceName(User reinforceName) {
		ReinforceName = reinforceName;
	}

	public void sellOrDestroy(WeaponInfo weapon) {
		RFIF.setMoney(RFIF.getMoney() + weapon.getUpgraded() * 100000);
		RFIF.getInventory().remove(weapon);
	}

	public UserInfo addInfo(String name) {
		RFIF = StaticFile.gameSystemInfo.findUser(name);
		return RFIF;
	}
}
