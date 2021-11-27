package got.common.command;

import java.util.List;

import got.common.GOTConfig;
import net.minecraft.command.*;

public class GOTCommandStructureTimelapse extends CommandBase {
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			return CommandBase.getListOfStringsMatchingLastWord(args, "on", "off");
		}
		return null;
	}

	@Override
	public String getCommandName() {
		return "strTimelapse";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "got.command.strTimelapse.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			if ("on".equals(args[0])) {
				GOTConfig.setStructureTimelapse(true);
				CommandBase.func_152373_a(sender, this, "got.command.strTimelapse.on");
				CommandBase.func_152373_a(sender, this, "got.command.strTimelapse.warn");
				return;
			}
			if ("off".equals(args[0])) {
				GOTConfig.setStructureTimelapse(false);
				CommandBase.func_152373_a(sender, this, "got.command.strTimelapse.off");
				return;
			}
			int interval = CommandBase.parseIntWithMin(sender, args[0], 0);
			GOTConfig.setStructureTimelapseInterval(interval);
			CommandBase.func_152373_a(sender, this, "got.command.strTimelapse.interval", interval);
			return;
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
