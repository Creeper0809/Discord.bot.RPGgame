package creeper0809.discordbot.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.map.HashedMap;

import creeper0809.discordbot.gameinfo.ReinforceGameInfo;
import creeper0809.discordbot.gameinfo.WeaponInfo;
import creeper0809.discordbot.reinforcegame.ReinForceGame;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter {

	User AuthorName;
	MessageChannel ch;
	ReinForceGame RFG = new ReinForceGame();
	WeaponInfo weapon;
	HashedMap<String, Integer> isDoing = new HashedMap<String, Integer>();
	ArrayList<String> emotionsName = new ArrayList<String>();
	List<Emote> emotions;

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		ch = e.getChannel();
		String msg = e.getMessage().getContentRaw();
		if (e.getAuthor().isBot())
			return;
		AuthorName = e.getAuthor();
		RFG.addInfo(AuthorName.getName());
		if (msg.charAt(0) == ']') {
			String[] args = msg.substring(1).split(" ");
			if (args.length <= 0)
				return;
			else if (args[0].equalsIgnoreCase("강화")) {
				if (args.length < 2)
					return;
				ReinforceGameInfo RFIF = RFG.getRFIF();
				if (args[1].equalsIgnoreCase("가입")) {
					if (RFIF == null) {
						sendMessage("강화게임에 참여 하셨습니다. 탈퇴를 원하시면 강화 탈퇴 를입력해주십시오.");
						RFG.addAccount(AuthorName.getName());
					} else {
						sendMessage("이미 가입되셨습니다.");
					}
					return;
				} else if (args[1].equalsIgnoreCase("도움")) {
					sendEmbed(new embedBuilder().showHelpBox());
					return;
				}
				if (RFIF == null) {// 이걸 기준으로 가입해야만 쓸수 있는 명령어들.
					sendMessage("게임 가입을 먼저 하십시오");
					return;
				}
				if (args[1].equalsIgnoreCase("내정보")) {
					sendEmbed(new embedBuilder().myGameInfo(RFIF, args[1]));
				} else if (args[1].equalsIgnoreCase("무기목록")) {
					sendEmbed(new embedBuilder().myGameInfo(RFIF, args[1]));
				} else if (args[1].equalsIgnoreCase("탈퇴")) {
					sendMessage("강화게임에 탈퇴 하셨습니다. 가입을 다시 원하시면 \"강화 가입\"를 입력해주십시오.");
					RFG.deleteAccount(AuthorName.getName());
				}
				if (args.length > 2) {// 무기 이름을 입력해야 하는 정보들
					StringBuilder weaponname = new StringBuilder();
					for (int i = 2; i < args.length; i++) {
						if (i == args.length - 1)
							weaponname.append(args[i]);
						else
							weaponname.append(args[i] + " ");
					}
					if (args[1].equalsIgnoreCase("획득")) {
						RFIF.giveItem("Common", weaponname.toString(), 0, 1000);
						return;
					} else if (args[1].equalsIgnoreCase("돈")) {
						try {
							RFIF.setMoney(RFIF.getMoney() + Integer.parseInt(weaponname.toString()));
						} catch (Exception es) {
							sendMessage("숫자를 입력해주십시오");
						}
					}
					weapon = RFIF.getWeapon(weaponname.toString());
					if (args[1].equalsIgnoreCase("도전")) {
						int does = IsDoing(e.getChannel().getName());
						if (does == 1) {
							sendMessage("누군가 강화를 시도 하고있습니다. 지켜봐주세요");
							return;
						}
						if (weapon == null) {
							sendMessage("뒤 강화수");
							return;
						}
						if (RFIF.getMoney() >= weapon.getCost()) {
							isDoing.replace(e.getChannel().getName(), 1);
							String description = "강화시 " + (weapon.getUpgraded() + 1) + "성이 됩니다.\n" + weapon.getCost()
									+ "원 소모\n" + "성공확률:" + RFG.sucessPercentage[weapon.getUpgraded()] + "%\n" + "실패확률:"
									+ (100 - (RFG.destroyPercentage[weapon.getUpgraded()]
											+ RFG.sucessPercentage[weapon.getUpgraded()]))
									+ "%\n" + "파괴확률:" + RFG.destroyPercentage[weapon.getUpgraded()] + "%";
							ch.sendMessage(new embedBuilder()
									.showQuestionBox(weapon.getUpgraded() + "성 입니다 강화 하시겠습니까?", description).build())
									.queue(message -> {
										message.addReaction("✔️").queue();
										message.addReaction("❌").queue();

										for (int i = 15; i > -1; i--) {
											message.editMessage(new embedBuilder()
													.showQuestionBox(weapon.getUpgraded() + "성 입니다 강화 하시겠습니까?", description + "\n" + i + "초 후 삭제")
													.build()).queueAfter(1, TimeUnit.SECONDS);
											try {
												TimeUnit.SECONDS.sleep(1);
											} catch (InterruptedException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
										}
										message.delete().queue();
										isDoing.replace(e.getChannel().getName(), 0);
									});
						} else {
							sendMessage("강화에 필요한 돈이 부족합니다.");
						}
					} else if (args[1].equalsIgnoreCase("판매")) {
						if (weapon.getUpgraded() == 0) {
							sendMessage("어딜 날로 먹을라고.");
							return;
						}
						RFG.sellOrDestroy(weapon);
						sendMessage(weapon.getUpgraded() * 100000 + "원입니다.\n현재 돈은 " + RFIF.getMoney() + "원 입니다.");
					} else if (args[1].equalsIgnoreCase("내무기")) {
						sendEmbed(new embedBuilder().showWeaponInfo(RFIF, weapon));
					} else if (args[1].equalsIgnoreCase("착용")) {
						if (weapon == null) {
							sendMessage("없는 장비 입니다. 뒤 강화수를 입력하셨는지 무기이름은 제대로 됐는지 확인해주세요.");
						} else if (weapon.equals(RFIF.getequipedWeapon())) {
							sendMessage("이미 착용중인 장비 입니다.");
						} else {
							RFIF.equipWeapon(weapon);
							sendMessage("장비 " + weapon.getProperName() + " 착용되었습니다.");
						}
					} else if (args[1].equalsIgnoreCase("착용해제")) {
						if (weapon == null) {
							sendMessage("없는 장비 입니다. 뒤 강화수를 입력하셨는지 무기이름은 제대로 됐는지 확인해주세요.");
						} else if (RFIF.getequipedWeapon() == null) {
							sendMessage("장착중인 장비가 없습니다.");
						} else {
							RFIF.disarmWeapon(weapon);
							;
							sendMessage("장비 " + weapon.getProperName() + " 착용해제되었습니다.");
						}
					}
				}
			}
		}

	}

	public int IsDoing(String msg) {
		for (Map.Entry<String, Integer> b : isDoing.entrySet()) {
			if (b.getKey().equals(msg))
				return b.getValue();
		}
		isDoing.put(msg, 0);
		return 0;
	}

	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent e) {
		if (!e.getChannel().retrieveMessageById(e.getMessageId()).complete().getAuthor().isBot()) {
			return;
		}
//		emotions = e.getChannel().retrieveMessageById(e.getMessageId()).complete().getEmotes();
//		for (int i = 0; i < emotions.size(); i++) {
//			emotionsName.add(emotions.get(i).getName());
//		}
//		if (emotionsName.contains("✔️") && emotionsName.contains("❌")) {
		if (e.getReactionEmote().getName().equals("✔️") && !e.getMember().getUser().equals(e.getJDA().getSelfUser())) {
			if (e.getMember().getUser().equals(AuthorName)) {
				sendMessage(RFG.upgradeWeapon(weapon));
				isDoing.replace(e.getChannel().getName(), 0);
				e.getChannel().retrieveMessageById(e.getMessageId()).complete().delete().queue();
			}
		} else if (e.getReactionEmote().getName().equals("❌")
				&& !e.getMember().getUser().equals(e.getJDA().getSelfUser())) {
			if (e.getMember().getUser().equals(AuthorName)) {
				isDoing.replace(e.getChannel().getName(), 0);
				sendMessage("강화를 취소 하셨습니다");
				e.getChannel().retrieveMessageById(e.getMessageId()).complete().delete().queue();
			}
		}
	}
//	}

	private void sendNDeleteMessage(String msg, int second) {
		ch.sendMessage(msg).queue(message -> message.delete().queueAfter(second, TimeUnit.SECONDS));
	}

	private void sendMessage(String msg) {
		ch.sendMessage(msg).queue();
	}

	private void sendEmbed(EmbedBuilder msg) {
		ch.sendMessage(msg.build()).queue();
	}

	private void deleteMessage(Message msg) {
		msg.delete().queue();
	}
}
