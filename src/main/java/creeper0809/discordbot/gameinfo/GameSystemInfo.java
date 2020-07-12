package creeper0809.discordbot.gameinfo;

import java.util.ArrayList;

public class GameSystemInfo {
	private ArrayList<UserInfo> userInfo = new ArrayList<UserInfo>();
	private ArrayList<StageInfo> stageInfo = new ArrayList<StageInfo>();
	private ArrayList<WeaponInfo> weaponInfo = new ArrayList<WeaponInfo>();
	private ArrayList<EnemyInfo> enemyInfo = new ArrayList<EnemyInfo>();
	
	public GameSystemInfo() {
		weaponInfo.add(new WeaponInfo("Common", "기본단검", 0, 1000));
		weaponInfo.add(new WeaponInfo("Common", "기본장검", 0, 1000));
		weaponInfo.add(new WeaponInfo("Common", "기본활", 0, 1000));
		weaponInfo.add(new WeaponInfo("Common", "기본수류탄", 0, 1000));
		enemyInfo.add(new EnemyInfo(10, "쥐", 10, "정말 자그마한 쥐", 1));
		stageInfo.add(new StageInfo("평원"));
	}
	public EnemyInfo findEmemy(String name) {
		System.out.println(name);
		for(int i = 0;i<enemyInfo.size();i++) {
			System.out.println(enemyInfo.get(i).getName().equals(name));
			if(enemyInfo.get(i).getName().equals(name)) return enemyInfo.get(i);
		}
		return enemyInfo.get(0);
	}
	public StageInfo findStage(String name) {
		for(int i = 0;i<stageInfo.size();i++) {
			if(stageInfo.get(i).getStageName().equals(name)) return stageInfo.get(i);
		}
		return null;
	}
	public UserInfo findUser(String name) {
		for(int i = 0;i<userInfo.size();i++) {
			if(userInfo.get(i).getUserName().equals(name)) return userInfo.get(i);
		}
		return null;
	}
	public WeaponInfo findWeapon(String name) {
		for(int i = 0;i<weaponInfo.size();i++) {
			if(weaponInfo.get(i).getWeaponName().equals(name)) return weaponInfo.get(i);
		}
		return null;
	}
	public void addAccount(String name) {
		userInfo.add(new UserInfo(name));
	}
}
