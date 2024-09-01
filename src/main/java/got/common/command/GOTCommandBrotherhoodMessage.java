package got.common.command;

import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.brotherhood.GOTBrotherhood;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GOTCommandBrotherhoodMessage extends CommandBase {
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
		String[] args1 = args;
		GOTPlayerData playerData = GOTLevelData.getData(getCommandSenderAsPlayer(sender));
		String[] argsOriginal = Arrays.copyOf(args1, args1.length);
		if (args1.length >= 2 && "bind".equals(args1[0])) {
			args1 = GOTCommandBrotherhood.fixArgsForBrotherhood(args1, 1, true);
			return GOTCommandBrotherhood.listBrotherhoodsMatchingLastWord(args1, argsOriginal, 1, playerData, false);
		}
		if (args1.length >= 1) {
			args1 = GOTCommandBrotherhood.fixArgsForBrotherhood(args1, 0, true);
			List<String> matches = new ArrayList<>();
			if (args1.length == 1 && (argsOriginal[0].isEmpty() || argsOriginal[0].charAt(0) != '\"')) {
				matches.addAll(getListOfStringsMatchingLastWord(args1, "bind", "unbind"));
			}
			matches.addAll(GOTCommandBrotherhood.listBrotherhoodsMatchingLastWord(args1, argsOriginal, 0, playerData, false));
			return matches;
		}
		return Collections.emptyList();
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return sender instanceof EntityPlayer || super.canCommandSenderUseCommand(sender);
	}

	@Override
	public List<String> getCommandAliases() {
		return Collections.singletonList("fchat");
	}

	@Override
	public String getCommandName() {
		return "fmsg";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "got.command.fmsg.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		String[] args1 = args;
		EntityPlayerMP entityplayer = getCommandSenderAsPlayer(sender);
		GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
		if (args1.length >= 1) {
			if ("bind".equals(args1[0]) && args1.length >= 2) {
				String fsName = GOTCommandBrotherhood.fixArgsForBrotherhood(args1, 1, false)[1];
				GOTBrotherhood brotherhood = playerData.getBrotherhoodByName(fsName);
				if (brotherhood != null && !brotherhood.isDisbanded() && brotherhood.containsPlayer(entityplayer.getUniqueID())) {
					playerData.setChatBoundBrotherhood(brotherhood);
					IChatComponent notif = new ChatComponentTranslation("got.command.fmsg.bind", brotherhood.getName());
					notif.getChatStyle().setColor(EnumChatFormatting.GRAY);
					notif.getChatStyle().setItalic(true);
					sender.addChatMessage(notif);
					return;
				}
				throw new WrongUsageException("got.command.fmsg.notFound", fsName);
			}
			if ("unbind".equals(args1[0])) {
				GOTBrotherhood preBoundBrotherhood = playerData.getChatBoundBrotherhood();
				playerData.setChatBoundBrotherhoodID(null);
				IChatComponent notif = new ChatComponentTranslation("got.command.fmsg.unbind", preBoundBrotherhood.getName());
				notif.getChatStyle().setColor(EnumChatFormatting.GRAY);
				notif.getChatStyle().setItalic(true);
				sender.addChatMessage(notif);
				return;
			}
			GOTBrotherhood brotherhood = null;
			int msgStartIndex = 0;
			if (!args1[0].isEmpty() && args1[0].charAt(0) == '\"') {
				String fsName = (args1 = GOTCommandBrotherhood.fixArgsForBrotherhood(args1, 0, false))[0];
				brotherhood = playerData.getBrotherhoodByName(fsName);
				if (brotherhood == null) {
					throw new WrongUsageException("got.command.fmsg.notFound", fsName);
				}
				msgStartIndex = 1;
			}
			if (brotherhood == null) {
				brotherhood = playerData.getChatBoundBrotherhood();
				if (brotherhood == null) {
					throw new WrongUsageException("got.command.fmsg.boundNone");
				}
				if (brotherhood.isDisbanded() || !brotherhood.containsPlayer(entityplayer.getUniqueID())) {
					throw new WrongUsageException("got.command.fmsg.boundNotMember", brotherhood.getName());
				}
			}
			IChatComponent message = func_147176_a(sender, args1, msgStartIndex, false);
			brotherhood.sendBrotherhoodMessage(entityplayer, message.getUnformattedText());
			return;
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}