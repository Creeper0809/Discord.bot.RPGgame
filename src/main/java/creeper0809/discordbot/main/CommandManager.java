package creeper0809.discordbot.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import creeper0809.discordbot.commands.DisarmWeapon;
import creeper0809.discordbot.commands.EquipWeapon;
import creeper0809.discordbot.commands.SellWeapon;
import creeper0809.discordbot.commands.UpgradeWeapon;
import creeper0809.discordbot.objects.Constants;
import creeper0809.discordbot.objects.ICommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandManager {
	List<ICommand> commands = new ArrayList<ICommand>();
	public CommandManager() {
		addCommand(new EquipWeapon());
		addCommand(new DisarmWeapon());
		addCommand(new SellWeapon());
		addCommand(new UpgradeWeapon());
	}
	private void addCommand(ICommand command) {
		boolean hasCommand = this.commands.stream().anyMatch((it)->it.getInvoke().equalsIgnoreCase(command.getInvoke()));
		if(hasCommand) {
			System.out.println("이미 존재하는 커맨드입니다");
		}
		commands.add(command);
	}
	private ICommand getCommand(String commandName) {
		for(ICommand cmd : commands) {
			if(cmd.getInvoke().equals(commandName)||cmd.getAliases().contains(commandName)) {
				return cmd;
			}
		}
		return null;
	}
	public void handlecommand(MessageReceivedEvent e) {

		final String[] message = e.getMessage().getContentRaw().replaceFirst(Pattern.quote(Constants.PREFIX), "")
				.split("\\s");
		final String invoke = message[0].toLowerCase();
		ICommand cmd = this.getCommand(invoke);
		if(cmd != null) {
			List<String> args = Arrays.asList(message).subList(1, message.length);
			StringBuilder weaponName = new StringBuilder();
			for (int i = 0; i < args.size(); i++) {
				if (i == args.size() - 1)
					weaponName.append(args.get(i));
				else
					weaponName.append(args.get(i) + " ");
			}
			if(Constants.GAMESYSTEMINFO.findUser(e.getAuthor().getName()) == null) {
				e.getChannel().sendMessage("게임 가입부터 하십시오.").queue();
				return;
			}
			cmd.handle(weaponName.toString(), e);
		}
	}
}
