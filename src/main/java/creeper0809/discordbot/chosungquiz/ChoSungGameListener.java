package creeper0809.discordbot.chosungquiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ChoSungGameListener extends ListenerAdapter {

	ArrayList<String> choSungQuiz = new ArrayList<String>();

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {

		User user = e.getAuthor();
		Message msgs = e.getMessage();
		String msg = e.getMessage().getContentRaw();
		DicCrawl dicCrawl = new DicCrawl();
		ArrayList<String> forsubstring = new ArrayList<String>();

//		try {
		if (user.isBot())
			return;
		if (msg.charAt(0) == '>') {
			String[] args = msg.substring(1).split(" ");
			String explain = dicCrawl.isthere(args[0]);
			for (int i = 0; i < args[0].length(); i++) {
				forsubstring.add(args[0].substring(i, i + 1));
			}
			if (forsubstring.size() == 2) {
				if (!choSungQuiz.contains(args[0])) {
					for (int i = 0; i < forsubstring.size(); i++) {
						forsubstring.set(i, getInitialSound(forsubstring.get(i)));
					}
					String ab = (String) forsubstring.get(0) + (String) forsubstring.get(1);
					if (ab.equals(choSungQuiz.get(0))) {
						if (explain.equals("없음")) {
							msgs.delete().queue();
							sendNDeleteMessage(e.getChannel(), "없는 단어 입니다");
						} else {
							sendNDeleteMessage(e.getChannel(), "옳은 단어 입니다 단어의 뜻은\n" + explain);
							choSungQuiz.add(args[0]);
						}
					} else {
						sendNDeleteMessage(e.getChannel(), "옳지 못한 단어 입니다");
						deleteMessage(msgs);
					}
				} else {
					sendNDeleteMessage(e.getChannel(), "이미 한 단어입니다");
					deleteMessage(msgs);
				}
			} else {
				sendNDeleteMessage(e.getChannel(), "초성퀴즈 처음 하냐?");
				deleteMessage(msgs);
			}
		} else if (msg.charAt(0) == '-') {
			String[] args = msg.substring(1).split(" ");
			if (args.length <= 0)
				return;
			if (args[0].equalsIgnoreCase("초성퀴즈")) {
				if (args.length < 2)
					return;
				choSungQuiz.clear();
				choSungQuiz.add(args[1]);
				String q = (String) choSungQuiz.get(0) + "로 초성이 정해졌습니다";
				sendMessage(e.getChannel(), q);
			} else if (args[0].equalsIgnoreCase("게임종료")) {
				choSungQuiz.clear();
				MessageHistory mh = new MessageHistory(e.getChannel());
				List<Message> mgs = mh.retrievePast(100).complete();
				e.getTextChannel().deleteMessages(mgs).complete();
				sendNDeleteMessage(e.getChannel(), "게임이 종료 되었습니다.");
			} else if (args[0].equalsIgnoreCase("단어")) {
				String explain = dicCrawl.isthere(args[1]);
				if (args.length < 2)
					return;
				if (explain.equals("없음")) {
					sendMessage(e.getChannel(), "없는 단어 입니다.");
				} else {
					sendMessage(e.getChannel(), explain);
				}
			}
		}
//		} catch (Exception e1) {
//			System.out.println("오류");
//		}
	}

	private void sendNDeleteMessage(MessageChannel channel, String msg) {
		channel.sendMessage(msg).queue(message -> message.delete().queueAfter(3, TimeUnit.SECONDS));
	}

	private void sendMessage(MessageChannel channel, String msg) {
		channel.sendMessage(msg).queue();
	}

	private void deleteMessage(Message msg) {
		msg.delete().queue();
	}

	public static String getInitialSound(String text) {
		String[] chs = { "ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ",
				"ㅎ" };
		if (text.length() > 0) {
			char chName = text.charAt(0);
			if (chName >= 0xAC00) {
				int uniVal = chName - 0xAC00;
				int cho = ((uniVal - (uniVal % 28)) / 28) / 21;
				return chs[cho];
			}
		}
		return null;
	}

}
