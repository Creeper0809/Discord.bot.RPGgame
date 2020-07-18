package creeper0809.discordbot.main;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import com.jagrosh.jdautilities.command.CommandClientBuilder;

import creeper0809.discordbot.chosungquiz.ChoSungGameListener;
import creeper0809.discordbot.commands.AddAccount;
import creeper0809.discordbot.commands.ShowAccountInfo;
import creeper0809.discordbot.commands.ShowHelp;
import creeper0809.discordbot.commands.ShowWeaponList;
import creeper0809.discordbot.commands.UpgradeWeapon;
import creeper0809.discordbot.gamesystem.ReactionListener;
import creeper0809.discordbot.objects.Constants;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;

public class Main {
	public static JDA jda;
	public static void main(String[] args) throws IOException {
		JDABuilder jb = new JDABuilder(AccountType.BOT);

		Listener listener = new Listener();
		CommandClientBuilder cb = new CommandClientBuilder();
		cb.setPrefix(Constants.PREFIX);
		cb.setHelpWord("도움을 입력하시면 도움말이 표시됩니다.");
		cb.setOwnerId("728850310834946158");
		cb.setStatus(OnlineStatus.ONLINE);
		cb.addCommand(new ShowHelp());
		cb.addCommand(new AddAccount());
		cb.addCommand(new ShowAccountInfo());
		cb.addCommand(new ShowWeaponList());
		jb.setAutoReconnect(true);
		jb.setToken(Constants.TOKEN);
		jb.addEventListeners(listener);
		jb.addEventListeners(cb.build());
		jb.addEventListeners(new ChoSungGameListener());
		jb.addEventListeners(new ReactionListener());
		try {
			jda = jb.build();
		} catch (LoginException e) {
			e.printStackTrace();
		}
	}
}
