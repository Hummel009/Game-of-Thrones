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
		return "Something went wrong.";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		DatabaseGenerator.display = args[0];
		CommandBase.func_152373_a(sender, this, "Database key: " + args[0]);
	}
}
