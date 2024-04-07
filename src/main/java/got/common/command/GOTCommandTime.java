package got.common.command;

import got.common.GOTTime;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

import java.util.Collections;
import java.util.List;

public class GOTCommandTime extends CommandBase {
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "set", "add");
		}
		if (args.length == 2 && "set".equals(args[0])) {
			return getListOfStringsMatchingLastWord(args, "day", "night");
		}
		return Collections.emptyList();
	}

	@Override
	public String getCommandName() {
		return "got_time";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "got.command.time.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length >= 2) {
			if ("set".equals(args[0])) {
				long time = "day".equals(args[1]) ? Math.round(GOTTime.DAY_LENGTH * 0.03) : "night".equals(args[1]) ? Math.round(GOTTime.DAY_LENGTH * 0.6) : parseIntWithMin(sender, args[1], 0);
				GOTTime.setWorldTime(time);
				func_152373_a(sender, this, "got.command.time.set", time);
				return;
			}
			if ("add".equals(args[0])) {
				int time = parseIntWithMin(sender, args[1], 0);
				GOTTime.addWorldTime(time);
				func_152373_a(sender, this, "got.command.time.add", time);
				return;
			}
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
