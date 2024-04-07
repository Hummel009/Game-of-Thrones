package got.common.command;

import got.common.database.GOTInvasions;
import got.common.entity.other.GOTEntityInvasionSpawner;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;

public class GOTCommandInvasion extends CommandBase {
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, GOTInvasions.listInvasionNames());
		}
		return Collections.emptyList();
	}

	@Override
	public String getCommandName() {
		return "invasion";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "got.command.invasion.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		EntityPlayer player = sender instanceof EntityPlayer ? (EntityPlayer) sender : null;
		World world = sender.getEntityWorld();
		if (args.length >= 1) {
			String typeName = args[0];
			GOTInvasions type = GOTInvasions.forName(typeName);
			if (type != null) {
				double posX = sender.getPlayerCoordinates().posX + 0.5;
				double posY = sender.getPlayerCoordinates().posY;
				double posZ = sender.getPlayerCoordinates().posZ + 0.5;
				if (args.length >= 4) {
					posX = func_110666_a(sender, posX, args[1]);
					posY = func_110666_a(sender, posY, args[2]);
					posZ = func_110666_a(sender, posZ, args[3]);
				} else {
					posY += 3.0;
				}
				int size = -1;
				if (args.length >= 5) {
					size = parseIntBounded(sender, args[4], 0, 10000);
				}
				GOTEntityInvasionSpawner invasion = new GOTEntityInvasionSpawner(world);
				invasion.setInvasionType(type);
				invasion.setLocationAndAngles(posX, posY, posZ, 0.0f, 0.0f);
				world.spawnEntityInWorld(invasion);
				invasion.selectAppropriateBonusFactions();
				invasion.startInvasion(player, size);
				func_152373_a(sender, this, "got.command.invasion.start", type.invasionName(), invasion.getInvasionSize(), posX, posY, posZ);
				return;
			}
			throw new WrongUsageException("got.command.invasion.noType", typeName);
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
