package got.common.command;

import got.common.util.GOTWikiGenerator;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;

public class GOTCommandWikiGen extends CommandBase {
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			List<String> list = GOTWikiGenerator.Type.getNames();
			return getListOfStringsMatchingLastWord(args, list.toArray(new String[0]));
		}
		return Collections.emptyList();
	}

	@Override
	public String getCommandName() {
		return "db";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "got.command.db.usage";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		World world = sender.getEntityWorld();
		GOTWikiGenerator.Type type = GOTWikiGenerator.Type.forName(args[0]);
		if (type == null) {
			func_152373_a(sender, this, "Database \"" + args[0] + "\" does not exist.");
		} else {
			func_152373_a(sender, this, "Database \"" + type + "\" is prepared.");
			GOTWikiGenerator.generate(type.toString(), world, (EntityPlayer) sender);
		}
	}
}