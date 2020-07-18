package creeper0809.discordbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import creeper0809.discordbot.gameinfo.UserInfo;
import creeper0809.discordbot.objects.Constants;
import net.dv8tion.jda.api.EmbedBuilder;

public class ShowAccountInfo extends Command {
	public ShowAccountInfo() {
		super.name = "내정보";
		super.aliases = new String[] { "내정보" };
		super.help = "나의 정보 표시";
	}

	@Override
	protected void execute(CommandEvent event) {
		if (Constants.GAMESYSTEMINFO.findUser(event.getAuthor().getName()) == null) {
			event.reply("가입을 먼저 해주십시오");
			return;
		}
		UserInfo userinfo = Constants.GAMESYSTEMINFO.findUser(event.getAuthor().getName());
		EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(userinfo.getBackgroundColor());
		eb.setTitle(userinfo.getUserName() + "님의 정보", null);
		StringBuilder sb = new StringBuilder()
				.append("`체력:`" + userinfo.getHP() + "\n" + "`돈`:" + userinfo.getMoney() + "원\n" + "`공격력`:"
						+ userinfo.getDamage() + "\n" + "`크리티컬확률`:" + userinfo.getCritical() + "%\n" + "`크리티컬데미지`:"+userinfo.getCriticalDamage()+"\n");

		if (userinfo.getEquipedWeapon() == null) 
			sb.append("착용무기:없음");
		 else 
			sb.append("착용무기:" + userinfo.getEquipedWeapon().getProperName());
		eb.setDescription(sb.toString());
		event.reply(eb.build());
	}

}
