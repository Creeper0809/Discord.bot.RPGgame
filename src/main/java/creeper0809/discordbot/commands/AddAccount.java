package creeper0809.discordbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import creeper0809.discordbot.objects.Constants;

public class AddAccount extends Command{
	public AddAccount() {
		super.name = "가입";
		super.aliases = new String[] {"게임가입","가입"};
		super.help = "가입 시켜주는 명령어";
	}
	@Override
	protected void execute(CommandEvent e) {
		if(Constants.GAMEINFO.findUser(e.getAuthor().getName()) == null) {
			Constants.GAMEINFO.addAccount(e.getAuthor().getName());
			e.reply("가입되셨습니다");
		}
		else {
			e.reply("이미 가입하셨습니다.");
		}
	}
	
}
