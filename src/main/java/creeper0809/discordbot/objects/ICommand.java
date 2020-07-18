package creeper0809.discordbot.objects;

import java.util.List;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface ICommand {
	void handle(String args,MessageReceivedEvent e);//명령어 들어오면 하는 동작
	String getHelp();//도움말 표시
	String getInvoke();//자신이 호출될 명령어
	List<String> getAliases();
}
