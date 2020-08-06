package creeper0809.discordbot.Items;

import creeper0809.discordbot.gameinfo.UserInfo;
import lombok.Getter;

public abstract class Item implements Cloneable{

	public String trueName;
	public int id;
	public String quality;
	public int LV;
	public int quantity = 1;
	public boolean inherence = false;
	public boolean dropitem = false;
	public boolean stackable = false;
	public int upgrade = 0;
	
	public int price() {
		return 0;
	}
	@Override
	public String toString() {
		return trueName;
	}
	public String upgrade() {return "";}
	public abstract String help();
	@Override
	public Item clone() {
		Item item = null;
		try {
			 item = (Item)super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return item;
	}
}
