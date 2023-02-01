package got.common.command;

import java.util.List;

import got.common.faction.*;
import java.util.Collections;
import net.minecraft.command.*;

public class GOTCommandFactionRelations extends CommandBase {
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		switch (args.length) {
		case 1:
			return CommandBase.getListOfStringsMatchingLastWord(args, "set", "reset");
		case 2:
		case 3: {
			List<String> list = GOTFaction.getPlayableAlignmentFactionNames();
			return CommandBase.getListOfStringsMatchingLastWord(args, list.toArray(new String[0]));
		}
		case 4: {
			List<String> list = GOTFactionRelations.Relation.listRelationNames();
			return CommandBase.getListOfStringsMatchingLastWord(args, list.toArray(new String[0]));
		}
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
	public boolean isUsernameIndex(String[] args, int i) {
		return false;
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
						CommandBase.func_152373_a(sender, this, "got.command.facRelations.set", fac1.factionName(), fac2.factionName(), relation.getDisplayName());
						return;
					} catch (IllegalArgumentException e) {
						throw new WrongUsageException("got.command.facRelations.error", e.getMessage());
					}
				}
			} else if ("reset".equals(function)) {
				GOTFactionRelations.resetAllRelations();
				CommandBase.func_152373_a(sender, this, "got.command.facRelations.reset");
				return;
			}
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
