package creeper0809.discordbot.main;

import java.io.FileInputStream;
import java.io.IOException;

import javax.security.auth.login.LoginException;

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
		jb.setAutoReconnect(true);
		jb.setStatus(OnlineStatus.ONLINE);
		jb.setToken(new String(readBuffer));
		jb.addEventListeners(new Listener());
		try {
			jda = jb.build();
		} catch (LoginException e) {
			e.printStackTrace();
		}
	}
}
