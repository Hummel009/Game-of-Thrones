package got.common.command;

import got.common.GOTLevelData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GOTCommandAllowStructures extends CommandBase {
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			ArrayList<String> bannedNames = new ArrayList<>(GOTLevelData.getBannedStructurePlayersUsernames());
			return getListOfStringsMatchingLastWord(args, bannedNames.toArray(new String[0]));
		}
		return Collections.emptyList();
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
			if (GOTLevelData.getStructuresBanned() != 1) {
				throw new WrongUsageException("got.command.allowStructures.alreadyAllowed");
			}
			GOTLevelData.setStructuresBanned(false);
			func_152373_a(sender, this, "got.command.allowStructures.allow");
		} else {
			GOTLevelData.setPlayerBannedForStructures(args[0], false);
			func_152373_a(sender, this, "got.command.allowStructures.allowPlayer", args[0]);
			EntityPlayerMP entityplayer = getPlayer(sender, args[0]);
			entityplayer.addChatMessage(new ChatComponentTranslation("got.chat.allowStructures"));
		}
	}
}