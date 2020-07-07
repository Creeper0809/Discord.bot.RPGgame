package creeper0809.discordbot.RPG;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import creeper0809.discordbot.GameInfo.ReinforceGameInfo;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RPGListener extends ListenerAdapter {
	ArrayList<ReinforceGameInfo> reinforceGame = new ArrayList<ReinforceGameInfo>();
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		
		
	}
	void sendPravateMessage(String mgs, User user) {
        user.openPrivateChannel().queue((channel) ->
        {
            channel.sendMessage(mgs).queue();
        });
	}
	public ReinforceGameInfo findRFinfo(String username) {
		for (int i = 0; i < reinforceGame.size(); i++) {
			if (reinforceGame.get(i).getUserName().equals(username))
				return reinforceGame.get(i);
		}
		return null;
	}
	private void sendNDeleteMessage(MessageChannel channel, String msg, int second) {
		channel.sendMessage(msg).queue(message -> message.delete().queueAfter(second, TimeUnit.SECONDS));
	}

	private void sendMessage(MessageChannel channel, String msg) {
		channel.sendMessage(msg).queue();
	}

	private void deleteMessage(Message msg) {
		msg.delete().queue();
	}
}
