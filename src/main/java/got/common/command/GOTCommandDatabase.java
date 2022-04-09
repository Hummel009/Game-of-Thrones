package got.common.command;

import got.common.util.DatabaseGenerator;
import net.minecraft.command.*;

public class GOTCommandDatabase extends CommandBase {

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
		DatabaseGenerator.display = args[0];
		CommandBase.func_152373_a(sender, this, "org 100h");
		CommandBase.func_152373_a(sender, this, ";====== DISPLAY ======;");
		CommandBase.func_152373_a(sender, this, "        mov ah, 09h");
		CommandBase.func_152373_a(sender, this, "        mov dx, str1");
		CommandBase.func_152373_a(sender, this, "        int 21h");
		CommandBase.func_152373_a(sender, this, "ret");
		CommandBase.func_152373_a(sender, this, ";====== VARIABLES ======;");
		CommandBase.func_152373_a(sender, this, "        str1 db '" + args[0]+ "'");
	}
}
