package got.common.command;

import java.util.*;

import got.common.GOTLevelData;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;

public class GOTCommandAllowStructures extends CommandBase {
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			ArrayList<String> bannedNames = new ArrayList<>(GOTLevelData.getBannedStructurePlayersUsernames());
			return CommandBase.getListOfStringsMatchingLastWord(args, bannedNames.toArray(new String[0]));
		}
		return null;
	}

	@Override
	public String getCommandName() {
		return "allowStructures";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "got.command.allowStructures.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length == 0) {
			if (!GOTLevelData.structuresBanned()) {
				throw new WrongUsageException("got.command.allowStructures.alreadyAllowed");
			}
			GOTLevelData.setStructuresBanned(false);
			CommandBase.func_152373_a(sender, this, "got.command.allowStructures.allow");
		} else {
			GOTLevelData.setPlayerBannedForStructures(args[0], false);
			CommandBase.func_152373_a(sender, this, "got.command.allowStructures.allowPlayer", args[0]);
			EntityPlayerMP entityplayer = CommandBase.getPlayer(sender, args[0]);
			if (entityplayer != null) {
				entityplayer.addChatMessage(new ChatComponentTranslation("got.chat.allowStructures"));
			}
		}
	}
}
