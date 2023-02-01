package got.common.command;

import java.util.List;

import got.common.database.GOTInvasions;
import got.common.entity.other.GOTEntityInvasionSpawner;
import java.util.Collections;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTCommandInvasion extends CommandBase {
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			return CommandBase.getListOfStringsMatchingLastWord(args, GOTInvasions.listInvasionNames());
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
	public boolean isUsernameIndex(String[] args, int i) {
		return false;
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
					posX = CommandBase.func_110666_a(sender, posX, args[1]);
					posY = CommandBase.func_110666_a(sender, posY, args[2]);
					posZ = CommandBase.func_110666_a(sender, posZ, args[3]);
				} else {
					posY += 3.0;
				}
				int size = -1;
				if (args.length >= 5) {
					size = CommandBase.parseIntBounded(sender, args[4], 0, 10000);
				}
				GOTEntityInvasionSpawner invasion = new GOTEntityInvasionSpawner(world);
				invasion.setInvasionType(type);
				invasion.setLocationAndAngles(posX, posY, posZ, 0.0f, 0.0f);
				world.spawnEntityInWorld(invasion);
				invasion.selectAppropriateBonusFactions();
				invasion.startInvasion(player, size);
				CommandBase.func_152373_a(sender, this, "got.command.invasion.start", type.invasionName(), invasion.getInvasionSize(), posX, posY, posZ);
				return;
			}
			throw new WrongUsageException("got.command.invasion.noType", typeName);
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
