package got.common.command;

import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.world.map.GOTWaypoint;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GOTCommandWaypoints extends CommandBase {
	private static GOTWaypoint.Region findRegionByName(String name) {
		GOTWaypoint.Region region = GOTWaypoint.regionForName(name);
		if (region == null) {
			throw new CommandException("got.command.waypoints.unknown", name);
		}
		return region;
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
		switch (args.length) {
			case 1:
				return getListOfStringsMatchingLastWord(args, "unlock", "lock");
			case 2:
				List<String> names = new ArrayList<>();
				for (GOTWaypoint.Region region : GOTWaypoint.Region.values()) {
					names.add(region.name());
				}
				names.add("all");
				return getListOfStringsMatchingLastWord(args, names.toArray(new String[0]));
			case 3:
				return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
		}
		return Collections.emptyList();
	}

	@Override
	public String getCommandName() {
		return "waypoints";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "got.command.waypoints.usage";
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
			String regionName = args[1];
			EntityPlayerMP entityplayer = args.length >= 3 ? getPlayer(sender, args[2]) : getCommandSenderAsPlayer(sender);
			GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
			if ("unlock".equalsIgnoreCase(args[0])) {
				if ("all".equalsIgnoreCase(regionName)) {
					for (GOTWaypoint.Region region : GOTWaypoint.Region.values()) {
						playerData.unlockFTRegion(region);
					}
					func_152373_a(sender, this, "got.command.waypoints.unlockAll", entityplayer.getCommandSenderName());
					return;
				}
				GOTWaypoint.Region region = findRegionByName(regionName);
				if (playerData.isFTRegionUnlocked(Collections.singletonList(region))) {
					throw new WrongUsageException("got.command.waypoints.unlock.fail", entityplayer.getCommandSenderName(), region.name());
				}
				playerData.unlockFTRegion(region);
				func_152373_a(sender, this, "got.command.waypoints.unlock", entityplayer.getCommandSenderName(), region.name());
				return;
			}
			if ("lock".equalsIgnoreCase(args[0])) {
				if ("all".equalsIgnoreCase(regionName)) {
					for (GOTWaypoint.Region region : GOTWaypoint.Region.values()) {
						playerData.lockFTRegion(region);
					}
					func_152373_a(sender, this, "got.command.waypoints.lockAll", entityplayer.getCommandSenderName());
					return;
				}
				GOTWaypoint.Region region = findRegionByName(regionName);
				if (!playerData.isFTRegionUnlocked(Collections.singletonList(region))) {
					throw new WrongUsageException("got.command.waypoints.lock.fail", entityplayer.getCommandSenderName(), region.name());
				}
				playerData.lockFTRegion(region);
				func_152373_a(sender, this, "got.command.waypoints.lock", entityplayer.getCommandSenderName(), region.name());
				return;
			}
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}