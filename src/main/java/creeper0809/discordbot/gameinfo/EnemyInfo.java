package creeper0809.discordbot.gameinfo;

import lombok.Getter;

@Getter
public class EnemyInfo {
	private int HP;
	private String name;
	private int damage;
	private String describe;
	private int id;
	public EnemyInfo(int HP,String name,int damage,String describe,int id) {
		this.HP = HP;
		this.name = name;
		this.damage = damage;
		this.describe = describe;
		this.id= id;
	}
}
