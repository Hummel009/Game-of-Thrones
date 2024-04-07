package got.common.command;

import got.common.faction.GOTFaction;
import got.common.faction.GOTFactionRelations;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

import java.util.Collections;
import java.util.List;

public class GOTCommandFactionRelations extends CommandBase {
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
		switch (args.length) {
			case 1:
				return getListOfStringsMatchingLastWord(args, "set", "reset");
			case 2:
			case 3: {
				List<String> list = GOTFaction.getPlayableAlignmentFactionNames();
				return getListOfStringsMatchingLastWord(args, list.toArray(new String[0]));
			}
			case 4:
				List<String> list = GOTFactionRelations.Relation.listRelationNames();
				return getListOfStringsMatchingLastWord(args, list.toArray(new String[0]));
		}
		return Collections.emptyList();
	}

	@Override
	public String getCommandName() {
		return "facRelations";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "got.command.facRelations.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length >= 1) {
			String function = args[0];
			if ("set".equals(function)) {
				if (args.length >= 4) {
					GOTFaction fac1 = GOTFaction.forName(args[1]);
					if (fac1 == null) {
						throw new WrongUsageException("got.command.alignment.noFaction", args[1]);
					}
					GOTFaction fac2 = GOTFaction.forName(args[2]);
					if (fac2 == null) {
						throw new WrongUsageException("got.command.alignment.noFaction", args[2]);
					}
					GOTFactionRelations.Relation relation = GOTFactionRelations.Relation.forName(args[3]);
					if (relation == null) {
						throw new WrongUsageException("got.command.facRelations.noRelation", args[3]);
					}
					try {
						GOTFactionRelations.overrideRelations(fac1, fac2, relation);
						func_152373_a(sender, this, "got.command.facRelations.set", fac1.factionName(), fac2.factionName(), relation.getDisplayName());
						return;
					} catch (IllegalArgumentException e) {
						throw new WrongUsageException("got.command.facRelations.error", e.getMessage());
					}
				}
			} else if ("reset".equals(function)) {
				GOTFactionRelations.resetAllRelations();
				func_152373_a(sender, this, "got.command.facRelations.reset");
				return;
			}
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
