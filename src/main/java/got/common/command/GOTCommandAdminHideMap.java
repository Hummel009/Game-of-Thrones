package got.common.command;

import java.util.*;

import got.common.GOTLevelData;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;

public class GOTCommandAdminHideMap extends CommandBase {
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		return Collections.emptyList();
	}

	@Override
	public String getCommandName() {
		return "opHideMap";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "got.command.opHideMap.usage";
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
		if (sender instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) sender;
			if (MinecraftServer.getServer().getConfigurationManager().func_152596_g(player.getGameProfile())) {
				if (player.capabilities.isCreativeMode) {
					GOTLevelData.getData(player).setAdminHideMap(true);
					CommandBase.func_152373_a(sender, this, "got.command.opHideMap.hiding");
					return;
				}
				throw new WrongUsageException("got.command.opHideMap.notCreative");
			}
		}
		throw new WrongUsageException("got.command.opHideMap.notOp");
	}

	public static void notifyUnhidden(EntityPlayer entityplayer) {
		entityplayer.addChatMessage(new ChatComponentTranslation("got.command.opHideMap.unhide"));
	}
}
