package got.common.command;

import java.util.List;

import got.common.util.DatabaseGenerator;
import got.common.util.DatabaseGenerator.Database;
import java.util.Collections;
import net.minecraft.command.*;

public class GOTCommandDatabase extends CommandBase {

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			List<String> list = Database.getNames();
			return CommandBase.getListOfStringsMatchingLastWord(args, list.toArray(new String[0]));
		}
		return Collections.emptyList();
	}

	@Override
	public String getCommandName() {
		return "db";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Something went wrong.";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		Database db = Database.forName(args[0]);
		if (db == null) {
			CommandBase.func_152373_a(sender, this, "Database \"" + args[0] + "\" does not exist.");
		} else {
			DatabaseGenerator.display = args[0];
			CommandBase.func_152373_a(sender, this, "Database \"" + args[0] + "\" is prepared.");
		}
	}
}
