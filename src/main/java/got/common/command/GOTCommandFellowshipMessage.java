package got.common.command;

import java.util.*;

import got.common.*;
import got.common.fellowship.GOTFellowship;
import net.minecraft.command.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class GOTCommandFellowshipMessage extends CommandBase {
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		GOTPlayerData playerData = GOTLevelData.getData(CommandBase.getCommandSenderAsPlayer(sender));
		String[] argsOriginal = Arrays.copyOf(args, args.length);
		if (args.length >= 2 && "bind".equals(args[0])) {
			args = GOTCommandFellowship.fixArgsForFellowship(args, 1, true);
			return GOTCommandFellowship.listFellowshipsMatchingLastWord(args, argsOriginal, 1, playerData, false);
		}
		if (args.length >= 1) {
			args = GOTCommandFellowship.fixArgsForFellowship(args, 0, true);
			ArrayList<String> matches = new ArrayList<>();
			if (args.length == 1 && !argsOriginal[0].startsWith("\"")) {
				matches.addAll(CommandBase.getListOfStringsMatchingLastWord(args, "bind", "unbind"));
			}
			matches.addAll(GOTCommandFellowship.listFellowshipsMatchingLastWord(args, argsOriginal, 0, playerData, false));
			return matches;
		}
		return null;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		if (sender instanceof EntityPlayer) {
			return true;
		}
		return super.canCommandSenderUseCommand(sender);
	}

	@Override
	public List getCommandAliases() {
		return Arrays.asList("fchat");
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
	public boolean isUsernameIndex(String[] args, int i) {
		return false;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		EntityPlayerMP entityplayer = CommandBase.getCommandSenderAsPlayer(sender);
		GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
		if (args.length >= 1) {
			if ("bind".equals(args[0]) && args.length >= 2) {
				String fsName = (args = GOTCommandFellowship.fixArgsForFellowship(args, 1, false))[1];
				GOTFellowship fellowship = playerData.getFellowshipByName(fsName);
				if (fellowship != null && !fellowship.isDisbanded() && fellowship.containsPlayer(entityplayer.getUniqueID())) {
					playerData.setChatBoundFellowship(fellowship);
					ChatComponentTranslation notif = new ChatComponentTranslation("got.command.fmsg.bind", fellowship.getName());
					notif.getChatStyle().setColor(EnumChatFormatting.GRAY);
					notif.getChatStyle().setItalic(true);
					sender.addChatMessage(notif);
					return;
				}
				throw new WrongUsageException("got.command.fmsg.notFound", fsName);
			}
			if ("unbind".equals(args[0])) {
				GOTFellowship preBoundFellowship = playerData.getChatBoundFellowship();
				playerData.setChatBoundFellowshipID(null);
				ChatComponentTranslation notif = new ChatComponentTranslation("got.command.fmsg.unbind", preBoundFellowship.getName());
				notif.getChatStyle().setColor(EnumChatFormatting.GRAY);
				notif.getChatStyle().setItalic(true);
				sender.addChatMessage(notif);
				return;
			}
			GOTFellowship fellowship = null;
			int msgStartIndex = 0;
			if (args[0].startsWith("\"")) {
				String fsName = (args = GOTCommandFellowship.fixArgsForFellowship(args, 0, false))[0];
				fellowship = playerData.getFellowshipByName(fsName);
				if (fellowship == null) {
					throw new WrongUsageException("got.command.fmsg.notFound", fsName);
				}
				msgStartIndex = 1;
			}
			if (fellowship == null) {
				fellowship = playerData.getChatBoundFellowship();
				if (fellowship == null) {
					throw new WrongUsageException("got.command.fmsg.boundNone");
				}
				if (fellowship.isDisbanded() || !fellowship.containsPlayer(entityplayer.getUniqueID())) {
					throw new WrongUsageException("got.command.fmsg.boundNotMember", fellowship.getName());
				}
			}
			if (fellowship != null) {
				IChatComponent message = CommandBase.func_147176_a(sender, args, msgStartIndex, false);
				fellowship.sendFellowshipMessage(entityplayer, message.getUnformattedText());
				return;
			}
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
