package got.common.command;

import got.common.GOTLevelData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

import java.util.Collections;
import java.util.List;

public class GOTCommandEnableReputationZones extends CommandBase {
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "enable", "disable");
		}
		return Collections.emptyList();
	}

	@Override
	public String getCommandName() {
		return "reputationZones";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "got.command.reputationZones.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length >= 1) {
			String flag = args[0];
			if ("enable".equals(flag)) {
				GOTLevelData.setEnableReputationZones(true);
				func_152373_a(sender, this, "got.command.reputationZones.enable");
				return;
			}
			if ("disable".equals(flag)) {
				GOTLevelData.setEnableReputationZones(false);
				func_152373_a(sender, this, "got.command.reputationZones.disable");
				return;
			}
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}