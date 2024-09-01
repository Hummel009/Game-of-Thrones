package got.common.command;

import got.common.GOTLevelData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import java.util.Collections;
import java.util.List;

public class GOTCommandOathCooldown extends CommandBase {
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 2) {
			return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
		}
		return Collections.emptyList();
	}

	@Override
	public String getCommandName() {
		return "oathCooldown";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "got.command.oathCooldown.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int i) {
		return i == 1;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length >= 1) {
			EntityPlayerMP entityplayer;
			int cd = parseIntBounded(sender, args[0], 0, 10000000);
			if (args.length >= 2) {
				entityplayer = getPlayer(sender, args[1]);
			} else {
				entityplayer = getCommandSenderAsPlayer(sender);
			}
			GOTLevelData.getData(entityplayer).setOathBreakCooldown(cd);
			func_152373_a(sender, this, "got.command.oathCooldown.set", entityplayer.getCommandSenderName(), cd, GOTLevelData.getHMSTime_Ticks(cd));
			return;
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}