package creeper0809.discordbot.main;

import java.io.FileInputStream;
import java.io.IOException;

import javax.security.auth.login.LoginException;

import com.jagrosh.jdautilities.command.CommandClientBuilder;

import creeper0809.discordbot.chosungquiz.ChoSungGameListener;
import creeper0809.discordbot.commands.AddAccount;
import creeper0809.discordbot.commands.SayHelp;
import creeper0809.discordbot.gameinfo.GameSystemInfo;
import creeper0809.discordbot.gameinfo.StaticFile;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;

public class Main {
	public static JDA jda;
	public static void main(String[] args) throws IOException {
		StaticFile a = new StaticFile();
		JDABuilder jb = new JDABuilder(AccountType.BOT);

		FileInputStream file = new FileInputStream("C:\\Users\\js.lim\\Desktop\\resource\\token.txt");
		byte[] readBuffer = new byte[file.available()];
		while(file.read(readBuffer)!= -1) {}
		CommandClientBuilder cb = new CommandClientBuilder();
		cb.setPrefix("]강화 ");
		cb.setHelpWord("help me");
		cb.setOwnerId("728850310834946158");
		cb.setStatus(OnlineStatus.ONLINE);
		cb.addCommand(new SayHelp());
		cb.addCommand(new AddAccount());
		jb.setAutoReconnect(true);
		jb.setToken(new String(readBuffer));
		jb.addEventListeners(new Listener());
		jb.addEventListeners(cb.build());
		jb.addEventListeners(new ChoSungGameListener());
		try {
			jda = jb.build();
		} catch (LoginException e) {
			e.printStackTrace();
		}
	}
}
