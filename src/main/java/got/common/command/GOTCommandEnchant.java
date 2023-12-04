package got.common.command;

import got.common.enchant.GOTEnchantment;
import got.common.enchant.GOTEnchantmentHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GOTCommandEnchant extends CommandBase {
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
		switch (args.length) {
			case 1:
				return CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
			case 2:
				return CommandBase.getListOfStringsMatchingLastWord(args, "add", "remove", "clear");
			case 3:
				ItemStack itemstack;
				if ("add".equals(args[1])) {
					EntityPlayerMP entityplayer2 = CommandBase.getPlayer(sender, args[0]);
					ItemStack itemstack2 = entityplayer2.getHeldItem();
					if (itemstack2 != null) {
						ArrayList<String> enchNames = new ArrayList<>();
						for (GOTEnchantment ench : GOTEnchantment.allEnchantments) {
							if (GOTEnchantmentHelper.hasEnchant(itemstack2, ench) || !ench.canApply(itemstack2, false) || !GOTEnchantmentHelper.checkEnchantCompatible(itemstack2, ench)) {
								continue;
							}
							enchNames.add(ench.enchantName);
						}
						return CommandBase.getListOfStringsMatchingLastWord(args, enchNames.toArray(new String[0]));
					}
				} else if ("remove".equals(args[1]) && (itemstack = CommandBase.getPlayer(sender, args[0]).getHeldItem()) != null) {
					ArrayList<String> enchNames = new ArrayList<>();
					for (GOTEnchantment ench : GOTEnchantment.allEnchantments) {
						if (!GOTEnchantmentHelper.hasEnchant(itemstack, ench)) {
							continue;
						}
						enchNames.add(ench.enchantName);
					}
					return CommandBase.getListOfStringsMatchingLastWord(args, enchNames.toArray(new String[0]));
				}
				break;
			default:
				return Collections.emptyList();
		}
		return Collections.emptyList();
	}

	@Override
	public String getCommandName() {
		return "got_enchant";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "got.command.got_enchant.usage";
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
		if (args.length >= 2) {
			EntityPlayerMP entityplayer = CommandBase.getPlayer(sender, args[0]);
			ItemStack itemstack = entityplayer.getHeldItem();
			if (itemstack == null) {
				throw new WrongUsageException("got.command.got_enchant.noItem");
			}
			String option = args[1];
			if ("add".equals(option) && args.length >= 3) {
				String enchName = args[2];
				GOTEnchantment ench = GOTEnchantment.getEnchantmentByName(enchName);
				if (ench != null) {
					if (!GOTEnchantmentHelper.hasEnchant(itemstack, ench) && ench.canApply(itemstack, false) && GOTEnchantmentHelper.checkEnchantCompatible(itemstack, ench)) {
						GOTEnchantmentHelper.setHasEnchant(itemstack, ench);
						CommandBase.func_152373_a(sender, this, "got.command.got_enchant.add", enchName, entityplayer.getCommandSenderName(), itemstack.getDisplayName());
						return;
					}
					throw new WrongUsageException("got.command.got_enchant.cannotAdd", enchName, itemstack.getDisplayName());
				}
				throw new WrongUsageException("got.command.got_enchant.unknown", enchName);
			}
			if ("remove".equals(option) && args.length >= 3) {
				String enchName = args[2];
				GOTEnchantment ench = GOTEnchantment.getEnchantmentByName(enchName);
				if (ench != null) {
					if (GOTEnchantmentHelper.hasEnchant(itemstack, ench)) {
						GOTEnchantmentHelper.removeEnchant(itemstack, ench);
						CommandBase.func_152373_a(sender, this, "got.command.got_enchant.remove", enchName, entityplayer.getCommandSenderName(), itemstack.getDisplayName());
						return;
					}
					throw new WrongUsageException("got.command.got_enchant.cannotRemove", enchName, itemstack.getDisplayName());
				}
				throw new WrongUsageException("got.command.got_enchant.unknown", enchName);
			}
			if ("clear".equals(option)) {
				GOTEnchantmentHelper.clearEnchantsAndProgress(itemstack);
				CommandBase.func_152373_a(sender, this, "got.command.got_enchant.clear", entityplayer.getCommandSenderName(), itemstack.getDisplayName());
				return;
			}
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
