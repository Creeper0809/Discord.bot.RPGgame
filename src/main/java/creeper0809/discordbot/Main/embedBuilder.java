package creeper0809.discordbot.Main;

import java.awt.Color;

import creeper0809.discordbot.GameInfo.ReinforceGameInfo;
import net.dv8tion.jda.api.EmbedBuilder;

public class embedBuilder {
	private EmbedBuilder eb = new EmbedBuilder();

	public EmbedBuilder myGameInfo(ReinforceGameInfo RFIF) {
		eb.setTitle(RFIF.getUserName() + "님의 정보", null);
		eb.setColor(Color.black);
		eb.setDescription("`체력:`" + RFIF.getHP() + "\n" + "`돈`:" + RFIF.getMoney() + "원\n" + "`공격력`:" + RFIF.getDamage()
				+ "\n" + "`크리티컬확률`:" + RFIF.getCritical() + "%\n" + "`크리티컬데미지`:" + RFIF.getCriticalDamage() + "%\n");
		return eb;
	}

	public EmbedBuilder showMyWeapon(ReinforceGameInfo RFIF) {
		StringBuilder description = new StringBuilder();
		for (int i = 1; i < RFIF.getInventory().size() + 1; i++) {
			if (RFIF.getInventory().get(i - 1).getUpgraded() == 0) {
				description.append(i + "." + RFIF.getInventory().get(i - 1).getWeaponName() + "\n");
			} else {
				description.append(i + "." + RFIF.getInventory().get(i - 1).getWeaponName() + " +"
						+ RFIF.getInventory().get(i - 1).getUpgraded() + "\n");
			}
		}
		eb.setTitle(RFIF.getUserName() + "님의 무기 목록", null);
		eb.setColor(Color.black);
		eb.setDescription("**" + description + "**");
		return eb;
	}

	public EmbedBuilder showHelp() {
		eb.setTitle("강화봇 도움말");
		eb.setColor(Color.black);
		eb.setDescription("]강화 도움 - 도움말표시\n" + "]강화 가입/탈퇴 - 게임 탈퇴/가입\n" + "]강화 도전 \"무기이름\" - 선택 무기 강화\n"
				+ "]강화 판매 \"무기이름\" - 선택 무기 판매\n" + "]강화 내정보 - 내 정보 표시\n"
				+ "]강화 내무기 \"무기이름\" - 선택 무기의 정보 표시\n" + "]강화 무기목록 - 무기 목록 표시\n"+
				  "]강화 획득 \"무기이름\" - 무기를 획득한다(디버그용)\n]강화 돈\"돈 수치\"- 돈을 획득한다(디버그용)");
		return eb;
		
	}
	public EmbedBuilder showQuestionBox(String Title,String description) {
		eb.setTitle(Title);
		eb.setColor(Color.black);
		eb.setDescription(description);
		return eb;
	}
}
