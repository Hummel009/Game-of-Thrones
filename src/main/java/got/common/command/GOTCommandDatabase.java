package got.common.command;

import got.common.util.DatabaseGenerator;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GOTCommandDatabase extends CommandBase {
	public Random rand = new Random();

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			List<String> list = DatabaseGenerator.Database.getNames();
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
		return "Something went wrong.";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		World world = sender.getEntityWorld();
		DatabaseGenerator.Database db = DatabaseGenerator.Database.forName(args[0]);
		if (db == null) {
			func_152373_a(sender, this, "Database \"" + args[0] + "\" does not exist.");
		} else {
			DatabaseGenerator.setDisplay(args[0]);
			func_152373_a(sender, this, "Database \"" + args[0] + "\" is prepared.");
			DatabaseGenerator.generate(world, (EntityPlayer) sender, rand);
		}
	}
}
