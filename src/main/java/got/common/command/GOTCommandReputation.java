package got.common.command;

import got.common.GOTLevelData;
import got.common.faction.GOTFaction;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GOTCommandReputation extends CommandBase {
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
		switch (args.length) {
			case 1:
				return getListOfStringsMatchingLastWord(args, "set", "add");
			case 2:
				List<String> list = GOTFaction.getPlayableReputationFactionNames();
				list.add("all");
				return getListOfStringsMatchingLastWord(args, list.toArray(new String[0]));
			case 4:
				return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
		}
		return Collections.emptyList();
	}

	@Override
	public String getCommandName() {
		return "reputation";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "got.command.reputation.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int i) {
		return i == 3;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length >= 2) {
			List<GOTFaction> factions = new ArrayList<>();
			if ("all".equalsIgnoreCase(args[1])) {
				factions = GOTFaction.getPlayableReputationFactions();
			} else {
				GOTFaction faction = GOTFaction.forName(args[1]);
				if (faction == null) {
					throw new WrongUsageException("got.command.reputation.noFaction", args[1]);
				}
				factions.add(faction);
			}
			if ("set".equals(args[0])) {
				EntityPlayerMP entityplayer;
				float reputation = (float) parseDoubleBounded(sender, args[2], -2147483647.0, 2147483647.0);
				if (args.length >= 4) {
					entityplayer = getPlayer(sender, args[3]);
				} else {
					entityplayer = getCommandSenderAsPlayer(sender);
				}
				for (GOTFaction f : factions) {
					GOTLevelData.getData(entityplayer).setReputationFromCommand(f, reputation);
					func_152373_a(sender, this, "got.command.reputation.set", entityplayer.getCommandSenderName(), f.factionName(), reputation);
				}
				return;
			}
			if ("add".equals(args[0])) {
				EntityPlayerMP entityplayer;
				float newReputation;
				float reputation = (float) parseDouble(sender, args[2]);
				if (args.length >= 4) {
					entityplayer = getPlayer(sender, args[3]);
				} else {
					entityplayer = getCommandSenderAsPlayer(sender);
				}
				for (GOTFaction f : factions) {
					newReputation = GOTLevelData.getData(entityplayer).getReputation(f) + reputation;
					if (newReputation < -2147483647.0f) {
						throw new WrongUsageException("got.command.reputation.tooLow", -2147483647.0f);
					}
					if (newReputation > 2147483647.0f) {
						throw new WrongUsageException("got.command.reputation.tooHigh", 2147483647.0f);
					}
				}
				for (GOTFaction f : factions) {
					GOTLevelData.getData(entityplayer).addReputationFromCommand(f, reputation);
					func_152373_a(sender, this, "got.command.reputation.add", reputation, entityplayer.getCommandSenderName(), f.factionName());
				}
				return;
			}
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}