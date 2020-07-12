package creeper0809.discordbot.gameinfo;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class StageInfo {
	@Getter
	ArrayList<String> hasEnemy = new ArrayList<String>();
	@Getter
	private String stageName;
	
	public StageInfo(String stageName) {
		this.stageName = stageName;
		hasEnemy.add("쥐");
	}
	
	public void addEnemy(String Name) {

	}
}
