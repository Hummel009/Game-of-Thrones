package got.common.command;

import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.database.GOTAchievement;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GOTCommandAchievement extends CommandBase {
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
		switch (args.length) {
			case 1:
				return getListOfStringsMatchingLastWord(args, "give", "remove");
			case 2:
				List<GOTAchievement> achievements = GOTAchievement.getAllAchievements();
				ArrayList<String> names = new ArrayList<>();
				for (GOTAchievement a : achievements) {
					names.add(a.getName());
				}
				if ("remove".equals(args[0])) {
					names.add("all");
				}
				return getListOfStringsMatchingLastWord(args, names.toArray(new String[0]));
			case 3:
				return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
		}
		return Collections.emptyList();
	}

	private static GOTAchievement findAchievementByName(String name) {
		GOTAchievement ach = GOTAchievement.findByName(name);
		if (ach == null) {
			throw new CommandException("got.command.got_achievement.unknown", name);
		}
		return ach;
	}

	@Override
	public String getCommandName() {
		return "got_achievement";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "got.command.got_achievement.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int i) {
		return i == 2;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length >= 2) {
			String achName = args[1];
			EntityPlayerMP entityplayer = args.length >= 3 ? getPlayer(sender, args[2]) : getCommandSenderAsPlayer(sender);
			GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
			if ("give".equalsIgnoreCase(args[0])) {
				GOTAchievement ach = findAchievementByName(achName);
				if (playerData.hasAchievement(ach)) {
					throw new WrongUsageException("got.command.got_achievement.give.fail", entityplayer.getCommandSenderName(), ach.getTitle(entityplayer));
				}
				playerData.addAchievement(ach);
				func_152373_a(sender, this, "got.command.got_achievement.give", entityplayer.getCommandSenderName(), ach.getTitle(entityplayer));
				return;
			}
			if ("remove".equalsIgnoreCase(args[0])) {
				if ("all".equalsIgnoreCase(achName)) {
					Iterable<GOTAchievement> allAchievements = new ArrayList<>(playerData.getAchievements());
					for (GOTAchievement ach : allAchievements) {
						playerData.removeAchievement(ach);
					}
					func_152373_a(sender, this, "got.command.got_achievement.removeAll", entityplayer.getCommandSenderName());
					return;
				}
				GOTAchievement ach = findAchievementByName(achName);
				if (!playerData.hasAchievement(ach)) {
					throw new WrongUsageException("got.command.got_achievement.remove.fail", entityplayer.getCommandSenderName(), ach.getTitle(entityplayer));
				}
				playerData.removeAchievement(ach);
				func_152373_a(sender, this, "got.command.got_achievement.remove", entityplayer.getCommandSenderName(), ach.getTitle(entityplayer));
				return;
			}
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}