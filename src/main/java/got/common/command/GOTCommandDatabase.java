package got.common.command;

import got.common.util.DatabaseGenerator;
import net.minecraft.command.*;

public class GOTCommandDatabase extends CommandBase {

	@Override
	public String getCommandName() {
		return "database";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "got.command.database.usage";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		DatabaseGenerator.display = args[0];
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
