package got.common.command;

import got.common.GOTLevelData;
import got.common.faction.GOTFaction;
import got.common.world.map.GOTConquestGrid;
import got.common.world.map.GOTConquestZone;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class GOTCommandConquest extends CommandBase {
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "set", "add", "radial", "clear", "rate");
		}
		//noinspection StreamToLoop
		if (args.length == 2 && Stream.of("set", "add", "radial").anyMatch(s -> s.equals(args[0]))) {
			List<String> list = GOTFaction.getPlayableAlignmentFactionNames();
			return getListOfStringsMatchingLastWord(args, list.toArray(new String[0]));
		}
		return Collections.emptyList();
	}

	@Override
	public String getCommandName() {
		return "conquest";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "got.command.conquest.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	public Object[] parseCoordsAndZone(ICommandSender sender, String[] args, int specifyIndex) {
		int posX = sender.getPlayerCoordinates().posX;
		int posZ = sender.getPlayerCoordinates().posZ;
		if (args.length >= specifyIndex + 2) {
			posX = parseInt(sender, args[specifyIndex]);
			posZ = parseInt(sender, args[specifyIndex + 1]);
		}
		GOTConquestZone zone = GOTConquestGrid.getZoneByWorldCoords(posX, posZ);
		if (zone.isDummyZone()) {
			throw new WrongUsageException("got.command.conquest.outOfBounds", posX, posZ);
		}
		return new Object[]{posX, posZ, zone};
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		World world = sender.getEntityWorld();
		if (!GOTConquestGrid.conquestEnabled(world)) {
			throw new WrongUsageException("got.command.conquest.notEnabled");
		}
		if (args.length >= 1) {
			String function = args[0];
			if ("clear".equals(function)) {
				Object[] obj = parseCoordsAndZone(sender, args, 1);
				int posX = (Integer) obj[0];
				int posZ = (Integer) obj[1];
				GOTConquestZone zone = (GOTConquestZone) obj[2];
				zone.clearAllFactions(world);
				func_152373_a(sender, this, "got.command.conquest.clear", posX, posZ);
				return;
			}
			if ("rate".equals(function)) {
				if (args.length >= 2) {
					double rate = parseDoubleBounded(sender, args[1], 0.0, 100.0);
					GOTLevelData.setConquestRate((float) rate);
					func_152373_a(sender, this, "got.command.conquest.rateSet", rate);
					return;
				}
				float currentRate = GOTLevelData.getConquestRate();
				sender.addChatMessage(new ChatComponentTranslation("got.command.conquest.rateGet", currentRate));
				return;
			}
			if (args.length >= 3 && Arrays.asList("set", "add", "radial").contains(function)) {
				GOTFaction fac = GOTFaction.forName(args[1]);
				if (fac == null) {
					throw new WrongUsageException("got.command.conquest.noFaction", args[1]);
				}
				float amount = (float) parseDouble(sender, args[2]);
				Object[] obj = parseCoordsAndZone(sender, args, 3);
				int posX = (Integer) obj[0];
				int posZ = (Integer) obj[1];
				GOTConquestZone zone = (GOTConquestZone) obj[2];
				if ("set".equals(function)) {
					if (amount < 0.0f) {
						throw new WrongUsageException("got.command.conquest.tooLow", 0.0f);
					}
					if (amount > 2147483647.0f) {
						throw new WrongUsageException("got.command.conquest.tooHigh", 2147483647.0f);
					}
					zone.setConquestStrength(fac, amount, world);
					func_152373_a(sender, this, "got.command.conquest.set", fac.factionName(), amount, posX, posZ);
					return;
				}
				if ("add".equals(function)) {
					float currentStr = zone.getConquestStrength(fac, world);
					float newStr = currentStr + amount;
					if (newStr < 0.0f) {
						throw new WrongUsageException("got.command.conquest.tooLow", 0.0f);
					}
					if (newStr > 2147483647.0f) {
						throw new WrongUsageException("got.command.conquest.tooHigh", 2147483647.0f);
					}
					zone.addConquestStrength(fac, amount, world);
					func_152373_a(sender, this, "got.command.conquest.add", fac.factionName(), amount, posX, posZ);
					return;
				}
				float centralStr = zone.getConquestStrength(fac, world);
				if (centralStr + amount > 2147483647.0f) {
					throw new WrongUsageException("got.command.conquest.tooHigh", 2147483647.0f);
				}
				EntityPlayerMP senderIfPlayer = sender instanceof EntityPlayerMP ? (EntityPlayerMP) sender : null;
				if (amount < 0.0f) {
					GOTConquestGrid.doRadialConquest(world, zone, senderIfPlayer, null, fac, -amount, -amount);
				} else {
					GOTConquestGrid.doRadialConquest(world, zone, senderIfPlayer, fac, null, amount, amount);
				}
				func_152373_a(sender, this, "got.command.conquest.radial", fac.factionName(), amount, posX, posZ);
				return;
			}
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
