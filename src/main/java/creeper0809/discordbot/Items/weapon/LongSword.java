package creeper0809.discordbot.Items.weapon;

public class LongSword extends Weapon{
	
	{
		trueName = "롱소드";
		id = 1;
		name = trueName;
		LV = 10;
		quality = "레어";
		attackDamage = 10;
		criticalPer = 5;
		criticalDamage = 5;
	}
	
	@Override
	public int price() {
		int price = 100;
		if(sealed) {
			price = price/2;
		}
		return price;
	}
	@Override
	public String help() {
		String help = "";
		if(sealed) {
			help = "이름:"+name+",등급:"+quality+",AD:"+attackDamage+",렙제:"+LV+",잠재등급:???"+",잠재옵션:???"+",크뎀:"+criticalDamage+",크확:"+criticalPer
			+",가격:"+price();
		}
		else {
			help = "이름:"+name+",등급:"+quality+",AD:"+attackDamage+",렙제:"+LV+",잠재등급:"+potentialQuaity+",잠재옵션:"+option[0]+",크뎀:"+criticalDamage+",크확:"+criticalPer
					+",가격:"+price();
		}
		return help;
	}
}
