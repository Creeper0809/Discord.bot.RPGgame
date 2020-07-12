package creeper0809.discordbot.main;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import creeper0809.discordbot.gameinfo.StaticFile;
import creeper0809.discordbot.gameinfo.UserInfo;
import creeper0809.discordbot.gameinfo.WeaponInfo;
import creeper0809.discordbot.reinforcegame.ReinForceGame;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter {
	// .queue() 가장 기본적인 작업후 호출 방법 호출 할 대상이 없으면 오류 발생
	// .submit() 더 이상 필요하지 않은 경우 실행을 취소해야 하는 경우

	ReinForceGame RFG = new ReinForceGame();
	MessageChannel noticechannel;
	HashMap<String, Integer> isDoing = new HashMap<String, Integer>();
	HashMap<String, WeaponInfo> upgradeWeapon = new HashMap<String, WeaponInfo>();

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		String msg = e.getMessage().getContentRaw();
		if (e.getAuthor().isBot())
			return;
		if (msg.charAt(0) == ']') {
			String[] args = msg.substring(1).split(" ");
			if (args.length <= 0)
				return;
			else if (args[0].equalsIgnoreCase("강화")) {
				if (args.length < 2)
					return;
				User AuthorName = e.getAuthor();
				RFG.addInfo(e.getAuthor().getName());
				UserInfo RFIF = RFG.getRFIF();
				noticechannel = e.getChannel();
				if (args[1].equalsIgnoreCase("가입")) {
					if (RFIF == null) {
						sendMessage(e.getChannel(), "강화게임에 참여 하셨습니다. 탈퇴를 원하시면 강화 탈퇴 를입력해주십시오.");
						upgradeWeapon.put(e.getAuthor().getName(), null);
						StaticFile.gameSystemInfo.addAccount(AuthorName.getName());
					} else {
						sendMessage(e.getChannel(), "이미 가입되셨습니다.");
					}
					return;
				} else if (args[1].equalsIgnoreCase("도움") || args[1].equalsIgnoreCase("도움말")) {
					sendEmbed(e.getChannel(), new embedBuilder().showHelpBox());
					return;
				}
				if (RFIF == null) {// 이걸 기준으로 가입해야만 쓸수 있는 명령어들.
					sendMessage(e.getChannel(), "게임 가입을 먼저 하십시오");
					return;
				}
				System.out.println(RFIF.getUserName());
				if (args.length == 2) {
					// 명령어 길이가 2단어 인것들
					if (args[1].equalsIgnoreCase("내정보")) {
						sendEmbed(e.getChannel(), new embedBuilder().myGameInfo(RFIF, args[1]));
					} else if (args[1].equalsIgnoreCase("내무기")) {
						sendEmbed(e.getChannel(), new embedBuilder().myGameInfo(RFIF, args[1]));
					}
				}
				if (args.length > 2) {// 명령어 길이가 3단어 이상인것들
					StringBuilder name = new StringBuilder();
					for (int i = 2; i < args.length; i++) {
						if (i == args.length - 1)
							name.append(args[i]);
						else
							name.append(args[i] + " ");
					}
					if (args[1].equalsIgnoreCase("획득")) {
						if (StaticFile.gameSystemInfo.findWeapon(name.toString()) != null)
							RFIF.giveItem(name.toString());
						else
							sendMessage(e.getChannel(), "도감에 없는 장비 입니다.");
						return;
					} else if (args[1].equalsIgnoreCase("돈")) {
						try {
							RFIF.setMoney(RFIF.getMoney() + Integer.parseInt(name.toString()));
						} catch (Exception es) {
							sendMessage(e.getChannel(), "숫자를 입력해주십시오");
						}
					}
					try {
						RFG.addInfo(e.getAuthor().getName());
						upgradeWeapon.replace(e.getAuthor().getName(), RFIF.getWeapon(name.toString()));//현 이게 문제
					} catch (Exception e2) {
					}
					if (upgradeWeapon.get(e.getAuthor().getName()) == null) {
						sendMessage(e.getChannel(), "없는 장비 입니다");
						return;
					}
					if (args[1].equalsIgnoreCase("도전")) {
						if (isDoing(AuthorName.getName()) == 1) {
							sendMessage(e.getChannel(), "이미 시도중입니다");
							return;
						}
						if (RFIF.getMoney() >= upgradeWeapon.get(e.getAuthor().getName()).getCost()) {
							isDoing.replace(AuthorName.getName(), 1);
							RFG.setReinforceName(e.getAuthor());
							WeaponInfo weapon = upgradeWeapon.get(e.getAuthor().getName());
							String description = "강화시 " + (weapon.getUpgraded() + 1) + "성이 됩니다.\n" + weapon.getCost()
									+ "원 소모\n" + "성공확률:" + RFG.sucessPercentage[weapon.getUpgraded()] + "%\n" + "실패확률:"
									+ (100 - (RFG.destroyPercentage[weapon.getUpgraded()]
											+ RFG.sucessPercentage[weapon.getUpgraded()]))
									+ "%\n" + "파괴확률:" + RFG.destroyPercentage[weapon.getUpgraded()] + "%";
							RFG.getReinforceName().openPrivateChannel().queue((channel) -> {
								channel.sendMessage(new embedBuilder()
										.showQuestionBox(weapon.getUpgraded() + "성 입니다 강화 하시겠습니까?", description)
										.build()).queue(message -> {
											message.addReaction("✔️").queue();
											message.addReaction("❌").queue();

											for (int i = 15; i > -1; i--) {
												message.editMessage(new embedBuilder()
														.showQuestionBox(weapon.getUpgraded() + "성 입니다 강화 하시겠습니까?",
																description + "\n" + i + "초 후 삭제")
														.build()).submitAfter(1, TimeUnit.SECONDS);
												try {
													TimeUnit.SECONDS.sleep(1);
												} catch (InterruptedException e1) {
													e1.printStackTrace();
												}
											}
											message.delete().submit();
											isDoing.replace(AuthorName.getName(), 0);
										});
							});
						} else {
							sendMessage(e.getChannel(), "강화에 필요한 돈이 부족합니다.");
						}
					} else if (args[1].equalsIgnoreCase("판매")) {
						RFG.sellOrDestroy(upgradeWeapon.get(e.getAuthor().getName()));
						sendMessage(e.getChannel(),
								upgradeWeapon.get(e.getAuthor().getName()).getUpgraded() * 100000 + "원입니다.\n현재 돈은 " + RFIF.getMoney() + "원 입니다.");
					} else if (args[1].equalsIgnoreCase("내무기")) {
						if (RFIF.getWeapon(args[2]) == null) {
							sendMessage(e.getChannel(), "가지고 계신 무기가 아닙니다.");
							return;
						}
						sendEmbed(e.getChannel(), new embedBuilder().showWeaponInfo(RFIF, upgradeWeapon.get(e.getAuthor().getName())));
					} else if (args[1].equalsIgnoreCase("착용") || args[1].equalsIgnoreCase("장착")) {
						if (upgradeWeapon.get(e.getAuthor().getName()).equals(RFIF.getEquipedWeapon())) {
							sendMessage(e.getChannel(), "이미 착용중인 장비 입니다.");
						} else {
							RFIF.equipWeapon(upgradeWeapon.get(e.getAuthor().getName()));
							sendMessage(e.getChannel(), "장비 " + upgradeWeapon.get(e.getAuthor().getName()).getProperName() + " 착용되었습니다.");
						}
					} else if (args[1].equalsIgnoreCase("착용해제") || args[1].equalsIgnoreCase("장착해제")) {
						if (RFIF.getEquipedWeapon() == null) {
							sendMessage(e.getChannel(), "장착중인 장비가 없습니다.");
						} else {
							RFIF.disarmWeapon(upgradeWeapon.get(e.getAuthor().getName()));
							sendMessage(e.getChannel(), "장비 " + upgradeWeapon.get(e.getAuthor().getName()).getProperName() + " 착용해제되었습니다.");
						}
					}
				}
			}
		}
	}

	public int isDoing(String name) {
		if (isDoing.get(name) == null) {
			isDoing.put(name, 0);
			return 0;
		}
		return isDoing.get(name);
	}

	public void onPrivateMessageReactionAdd(PrivateMessageReactionAddEvent e) {
		if (!e.getChannel().retrieveMessageById(e.getMessageId()).complete().getAuthor().isBot()) {
			return;
		}
//		emotions = e.getChannel().retrieveMessageById(e.getMessageId()).complete().getEmotes();
//		for (int i = 0; i < emotions.size(); i++) {
//			emotionsName.add(emotions.get(i).getName());
//		}
//		if (emotionsName.contains("✔️") && emotionsName.contains("❌")) {
		if (e.getReactionEmote().getName().equals("✔️") && !e.getUser().equals(e.getJDA().getSelfUser())) {
			isDoing.replace(e.getUser().getName(), 0);
			e.getChannel().retrieveMessageById(e.getMessageId()).complete().delete().queue();
			sendMessage(e.getChannel(), RFG.upgradeWeapon(upgradeWeapon.get(e.getUser().getName()), e.getUser().getName()));
			sendMessage(noticechannel, RFG.getDescribe());
		} else if (e.getReactionEmote().getName().equals("❌") && !e.getUser().equals(e.getJDA().getSelfUser())) {
			isDoing.replace(e.getUser().getName(), 0);
			e.getChannel().retrieveMessageById(e.getMessageId()).complete().delete().queue();
			sendMessage(e.getChannel(), "강화를 취소 하셨습니다");
		}
	}
//	}

	private void sendMessage(MessageChannel ch, String msg) {
		ch.sendMessage(msg).queue();
	}

	private void sendEmbed(MessageChannel ch, EmbedBuilder msg) {
		ch.sendMessage(msg.build()).queue();
	}
}
