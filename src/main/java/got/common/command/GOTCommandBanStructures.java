package got.common.command;

import got.common.GOTLevelData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;

import java.util.Collections;
import java.util.List;

public class GOTCommandBanStructures extends CommandBase {
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			return CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
		}
		return Collections.emptyList();
	}

	@Override
	public String getCommandName() {
		return "banStructures";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "got.command.banStructures.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length == 0) {
			if (GOTLevelData.structuresBanned()) {
				throw new WrongUsageException("got.command.banStructures.alreadyBanned");
			}
			GOTLevelData.setStructuresBanned(true);
			CommandBase.func_152373_a(sender, this, "got.command.banStructures.ban");
		} else {
			GOTLevelData.setPlayerBannedForStructures(args[0], true);
			CommandBase.func_152373_a(sender, this, "got.command.banStructures.banPlayer", args[0]);
			EntityPlayerMP entityplayer = CommandBase.getPlayer(sender, args[0]);
			entityplayer.addChatMessage(new ChatComponentTranslation("got.chat.banStructures"));
		}
	}
}
