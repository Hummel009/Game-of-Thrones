package got.common.command;

import java.util.*;

import got.common.*;
import got.common.database.GOTAchievement;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class GOTCommandAchievement extends CommandBase {
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		switch (args.length) {
		case 1:
			return CommandBase.getListOfStringsMatchingLastWord(args, "give", "remove");
		case 2:
			List<GOTAchievement> achievements = GOTAchievement.getAllAchievements();
			ArrayList<String> names = new ArrayList<>();
			for (GOTAchievement a : achievements) {
				names.add(a.getCodeName());
			}
			if ("remove".equals(args[0])) {
				names.add("all");
			}
			return CommandBase.getListOfStringsMatchingLastWord(args, names.toArray(new String[0]));
		case 3:
			return CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
		default:
			break;
		}
		return null;
	}

	public GOTAchievement findAchievementByName(String name) {
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
			EntityPlayerMP entityplayer = args.length >= 3 ? CommandBase.getPlayer(sender, args[2]) : CommandBase.getCommandSenderAsPlayer(sender);
			GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
			if ("give".equalsIgnoreCase(args[0])) {
				GOTAchievement ach = findAchievementByName(achName);
				if (playerData.hasAchievement(ach)) {
					throw new WrongUsageException("got.command.got_achievement.give.fail", entityplayer.getCommandSenderName(), ach.getTitle(entityplayer));
				}
				playerData.addAchievement(ach);
				CommandBase.func_152373_a(sender, this, "got.command.got_achievement.give", entityplayer.getCommandSenderName(), ach.getTitle(entityplayer));
				return;
			}
			if ("remove".equalsIgnoreCase(args[0])) {
				if ("all".equalsIgnoreCase(achName)) {
					ArrayList<GOTAchievement> allAchievements = new ArrayList<>(playerData.getAchievements());
					for (GOTAchievement ach : allAchievements) {
						playerData.removeAchievement(ach);
					}
					CommandBase.func_152373_a(sender, this, "got.command.got_achievement.removeAll", entityplayer.getCommandSenderName());
					return;
				}
				GOTAchievement ach = findAchievementByName(achName);
				if (!playerData.hasAchievement(ach)) {
					throw new WrongUsageException("got.command.got_achievement.remove.fail", entityplayer.getCommandSenderName(), ach.getTitle(entityplayer));
				}
				playerData.removeAchievement(ach);
				CommandBase.func_152373_a(sender, this, "got.command.got_achievement.remove", entityplayer.getCommandSenderName(), ach.getTitle(entityplayer));
				return;
			}
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
