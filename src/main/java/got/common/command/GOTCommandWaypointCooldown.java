package got.common.command;

import java.util.Collections;
import java.util.List;

import got.common.GOTLevelData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

public class GOTCommandWaypointCooldown extends CommandBase {
	public static int MAX_COOLDOWN = 86400;

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			return CommandBase.getListOfStringsMatchingLastWord(args, "max", "min");
		}
		return Collections.emptyList();
	}

	@Override
	public String getCommandName() {
		return "wpCooldown";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "got.command.wpCooldown.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		String function = null;
		int cooldown = -1;
		if (args.length == 1) {
			function = "max";
			cooldown = CommandBase.parseIntBounded(sender, args[0], 0, MAX_COOLDOWN);
		} else if (args.length >= 2) {
			function = args[0];
			cooldown = CommandBase.parseIntBounded(sender, args[1], 0, MAX_COOLDOWN);
		}
		if (function != null && cooldown >= 0) {
			int max = GOTLevelData.getWaypointCooldownMax();
			int min = GOTLevelData.getWaypointCooldownMin();
			if ("max".equals(function)) {
				boolean updatedMin = false;
				max = cooldown;
				if (max < min) {
					min = max;
					updatedMin = true;
				}
				GOTLevelData.setWaypointCooldown(max, min);
				CommandBase.func_152373_a(sender, this, "got.command.wpCooldown.setMax", max, GOTLevelData.getHMSTime_Seconds(max));
				if (updatedMin) {
					CommandBase.func_152373_a(sender, this, "got.command.wpCooldown.updateMin", min);
				}
				return;
			}
			if ("min".equals(function)) {
				boolean updatedMax = false;
				min = cooldown;
				if (min > max) {
					max = min;
					updatedMax = true;
				}
				GOTLevelData.setWaypointCooldown(max, min);
				CommandBase.func_152373_a(sender, this, "got.command.wpCooldown.setMin", min, GOTLevelData.getHMSTime_Seconds(min));
				if (updatedMax) {
					CommandBase.func_152373_a(sender, this, "got.command.wpCooldown.updateMax", max);
				}
				return;
			}
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
