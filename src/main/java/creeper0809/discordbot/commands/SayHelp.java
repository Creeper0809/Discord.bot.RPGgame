package creeper0809.discordbot.commands;

import java.awt.Color;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class SayHelp extends Command {

	public SayHelp() {
		super.name = "도움말";
		super.aliases = new String[] { "도움", "도움말" };
		super.help = "도움말 표시";
	}

	@Override
	protected void execute(CommandEvent e) {

		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("강화봇 도움말");
		eb.setColor(Color.black);
		eb.setDescription("]강화 도움 - 도움말표시\n" + "]강화 가입/탈퇴 - 게임 탈퇴/가입\n" + "--무기 관련 명령어--\n"
				+ "무기 이름이 같은것이 있다면 인벤토리 기준 처음것이 선택 됩니다. 무기이름에 뒤에 강화수 까지 써주셔야 합니다.\n" + "]강화 도전 \"무기이름\" - 선택 무기 강화\n"
				+ "]강화 판매 \"무기이름\" - 선택 무기 판매\n" + "]강화 내정보 - 내 정보 표시\n" + "]강화 내무기 \"무기이름\" - 선택 무기의 정보 표시\n"
				+ "]강화 무기목록 - 무기 목록 표시\n" + "]강화 착용/착용해제 \"무기이름\" - 무기를 착용/해제 합니다.\n" + "--디버그 관련 명령어 --\n"
				+ "남용시 제재를 가할수있습니다.\n" + "]강화 획득 \"무기이름\" - 무기를 획득한다\n]강화 돈\"돈 수치\"- 돈을 획득한다");
		e.reply(e.getAuthor().getAsMention());
		e.reply(eb.build());
	}

}
