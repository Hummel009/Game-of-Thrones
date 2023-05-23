package got.common.command;

import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.fellowship.GOTFellowship;
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

public class GOTCommandFellowshipMessage extends CommandBase {
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
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
		return Collections.emptyList();
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		if (sender instanceof EntityPlayer) {
			return true;
		}
		return super.canCommandSenderUseCommand(sender);
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
		EntityPlayerMP entityplayer = CommandBase.getCommandSenderAsPlayer(sender);
		GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
		if (args.length >= 1) {
			if ("bind".equals(args[0]) && args.length >= 2) {
				String fsName = GOTCommandFellowship.fixArgsForFellowship(args, 1, false)[1];
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
			IChatComponent message = CommandBase.func_147176_a(sender, args, msgStartIndex, false);
			fellowship.sendFellowshipMessage(entityplayer, message.getUnformattedText());
			return;
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
