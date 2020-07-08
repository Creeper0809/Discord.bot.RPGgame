package creeper0809.discordbot.ReinForceGame;

import java.util.ArrayList;
import java.util.Random;

import org.omg.PortableInterceptor.SUCCESSFUL;

import creeper0809.discordbot.GameInfo.ReinforceGameInfo;
import creeper0809.discordbot.GameInfo.WeaponInfo;

public class ReinForceGame {

	ArrayList<ReinforceGameInfo> reinforceGame = new ArrayList<ReinforceGameInfo>();
	ReinforceGameInfo RFIF;
	public int[] sucessPercentage = {95, 90, 85, 85, 80, 75, 70, 65, 60, 55, 45, 35, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30,
			3, 2, 1 };
	public int[] destroyPercentage = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 2, 3, 3, 7, 7, 19, 29, 40 };

	public void addAccount(String name) {
		reinforceGame.add(new ReinforceGameInfo(name));
	}

	public void deleteAccount(String name) {
		reinforceGame.remove(findRFinfo(name));
	}

	public String upgradeWeapon(WeaponInfo weaponinfo) {
		RFIF.setMoney(RFIF.getMoney() - weaponinfo.getCost());
		if(RFIF.getUpgradeFailed() == 2) {
			weaponinfo.setUpgraded(weaponinfo.getUpgraded() + 1);
			weaponinfo.setProperName(weaponinfo.getProperName() + " +"+weaponinfo.getUpgraded());
			RFIF.setUpgradeFailed(0);
			return "연속 2번 강화 수치가 하락하여 100%성공 하였습니다";
		}
		Random r = new Random();
		int num = r.nextInt(100) + 1;
		if (num <= sucessPercentage[weaponinfo.getUpgraded()]) {
			weaponinfo.setUpgraded(weaponinfo.getUpgraded() + 1);
			weaponinfo.setProperName(weaponinfo.getProperName() + " +"+weaponinfo.getUpgraded());
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
				weaponinfo.setProperName(weaponinfo.getProperName() + " +"+weaponinfo.getUpgraded());
				return "강화 수치 하락...";
			}
		}
	}

	public void sellOrDestroy(WeaponInfo weapon) {
		RFIF.setMoney(RFIF.getMoney() + weapon.getUpgraded() * 100000);
		RFIF.getInventory().remove(weapon);
	}

	public ReinforceGameInfo getRFIF() {
		return RFIF;
	}

	public void addInfo(String name) {
		RFIF = findRFinfo(name);
	}

	public ReinforceGameInfo findRFinfo(String username) {
		for (int i = 0; i < reinforceGame.size(); i++) {
			if (reinforceGame.get(i).getUserName().equals(username))
				return reinforceGame.get(i);
		}
		return null;
	}
}
