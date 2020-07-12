package creeper0809.discordbot.main;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.map.HashedMap;

import creeper0809.discordbot.gameinfo.GameSystemInfo;
import creeper0809.discordbot.gameinfo.StaticFile;
import creeper0809.discordbot.gameinfo.UserInfo;
import creeper0809.discordbot.gameinfo.WeaponInfo;
import creeper0809.discordbot.reinforcegame.ReinForceGame;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter {
	// .queue() 가장 기본적인 작업후 호출 방법 호출 할 대상이 없으면 오류 발생
	// .submit() 더 이상 필요하지 않은 경우 실행을 취소해야 하는 경우

	User AuthorName;
	ReinForceGame RFG = new ReinForceGame();
	WeaponInfo weapon;
	HashedMap<String, Integer> isDoing = new HashedMap<String, Integer>();
	
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		System.out.println(StaticFile.gameSystemInfo.findEmemy("쥐").getName());
		String msg = e.getMessage().getContentRaw();
		if (e.getAuthor().isBot())
			return;
		AuthorName = e.getAuthor();
		UserInfo RFIF = RFG.addInfo(AuthorName.getName());
		if (msg.charAt(0) == ']') {
			String[] args = msg.substring(1).split(" ");
			if (args.length <= 0)
				return;
			else if (args[0].equalsIgnoreCase("강화")) {
				if (args.length < 2)
					return;
				if (args[1].equalsIgnoreCase("가입")) {
					if (RFIF == null) {
						sendMessage(e.getChannel(), "강화게임에 참여 하셨습니다. 탈퇴를 원하시면 강화 탈퇴 를입력해주십시오.");
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
				if (args.length == 2) {// 명령어 길이가 2단어 인것들
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
					weapon = RFIF.getWeapon(name.toString());
					if (args[1].equalsIgnoreCase("도전")) {
						int does = IsDoing(e.getChannel().getName());
						if (does == 1) {
							sendMessage(e.getChannel(), "누군가 강화를 시도 하고있습니다. 지켜봐주세요");
							return;
						}
						if (weapon == null) {
							sendMessage(e.getChannel(), "무기 이름을 다시 확인해주십시오.");
							return;
						}
						if (RFIF.getMoney() >= weapon.getCost()) {
							isDoing.replace(e.getChannel().getName(), 1);
							RFG.setReinforceName(e.getAuthor());
							String description = "강화시 " + (weapon.getUpgraded() + 1) + "성이 됩니다.\n" + weapon.getCost()
									+ "원 소모\n" + "성공확률:" + RFG.sucessPercentage[weapon.getUpgraded()] + "%\n" + "실패확률:"
									+ (100 - (RFG.destroyPercentage[weapon.getUpgraded()]
											+ RFG.sucessPercentage[weapon.getUpgraded()]))
									+ "%\n" + "파괴확률:" + RFG.destroyPercentage[weapon.getUpgraded()] + "%";
							e.getChannel().sendMessage(new embedBuilder()
									.showQuestionBox(weapon.getUpgraded() + "성 입니다 강화 하시겠습니까?", description).build())
									.queue(message -> {
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
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
										}
										message.delete().submit();
									});
							isDoing.replace(e.getChannel().getName(), 0);
						} else {
							sendMessage(e.getChannel(), "강화에 필요한 돈이 부족합니다.");
						}
					} else if (args[1].equalsIgnoreCase("판매")) {
						if (weapon.getUpgraded() == 0) {
							sendMessage(e.getChannel(), "어딜 날로 먹을라고.");
							return;
						}
						RFG.sellOrDestroy(weapon);
						sendMessage(e.getChannel(),
								weapon.getUpgraded() * 100000 + "원입니다.\n현재 돈은 " + RFIF.getMoney() + "원 입니다.");
					} else if (args[1].equalsIgnoreCase("내무기")) {
						if(RFIF.getWeapon(args[2]) == null) {
							sendMessage(e.getChannel(), "가지고 계신 무기가 아닙니다.");
							return;
						}
						sendEmbed(e.getChannel(), new embedBuilder().showWeaponInfo(RFIF, weapon));
					} else if (args[1].equalsIgnoreCase("착용") || args[1].equalsIgnoreCase("장착")) {
						if (weapon == null) {
							sendMessage(e.getChannel(), "없는 장비 입니다. 뒤 강화수를 입력하셨는지 무기이름은 제대로 됐는지 확인해주세요.");
						} else if (weapon.equals(RFIF.getEquipedWeapon())) {
							sendMessage(e.getChannel(), "이미 착용중인 장비 입니다.");
						} else {
							RFIF.equipWeapon(weapon);
							sendMessage(e.getChannel(), "장비 " + weapon.getProperName() + " 착용되었습니다.");
						}
					} else if (args[1].equalsIgnoreCase("착용해제") || args[1].equalsIgnoreCase("장착해제")) {
						if (weapon == null) {
							sendMessage(e.getChannel(), "없는 장비 입니다. 뒤 강화수를 입력하셨는지 무기이름은 제대로 됐는지 확인해주세요.");
						} else if (RFIF.getEquipedWeapon() == null) {
							sendMessage(e.getChannel(), "장착중인 장비가 없습니다.");
						} else {
							RFIF.disarmWeapon(weapon);
							sendMessage(e.getChannel(), "장비 " + weapon.getProperName() + " 착용해제되었습니다.");
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
			if (e.getMember().getUser().equals(RFG.getReinforceName())) {
				sendMessage(e.getChannel(), RFG.upgradeWeapon(weapon));
				isDoing.replace(e.getChannel().getName(), 0);
				e.getChannel().retrieveMessageById(e.getMessageId()).complete().delete().queue();
			}
		} else if (e.getReactionEmote().getName().equals("❌")
				&& !e.getMember().getUser().equals(e.getJDA().getSelfUser())) {
			if (e.getMember().getUser().equals(RFG.getReinforceName())) {
				isDoing.replace(e.getChannel().getName(), 0);
				sendMessage(e.getChannel(), "강화를 취소 하셨습니다");
				e.getChannel().retrieveMessageById(e.getMessageId()).complete().delete().queue();
			}
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
