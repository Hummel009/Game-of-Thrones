package got.common.command;

import com.mojang.authlib.GameProfile;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.brotherhood.GOTBrotherhood;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class GOTCommandBrotherhood extends CommandBase {
	public static String[] fixArgsForBrotherhood(String[] args, int startIndex, boolean autocompleting) {
		if (!args[startIndex].isEmpty() && args[startIndex].charAt(0) == '\"') {
			int endIndex = startIndex;
			boolean foundEnd = false;
			while (!foundEnd) {
				if (!args[endIndex].isEmpty() && args[endIndex].charAt(args[endIndex].length() - 1) == '\"') {
					foundEnd = true;
					continue;
				}
				if (endIndex >= args.length - 1) {
					if (autocompleting) {
						break;
					}
					throw new WrongUsageException("got.command.brotherhood.edit.nameError");
				}
				++endIndex;
			}
			StringBuilder fsName = new StringBuilder();
			for (int i = startIndex; i <= endIndex; ++i) {
				if (i > startIndex) {
					fsName.append(' ');
				}
				fsName.append(args[i]);
			}
			if (!autocompleting || foundEnd) {
				fsName = new StringBuilder(fsName.toString().replace("\"", ""));
			}
			int diff = endIndex - startIndex;
			String[] argsNew = new String[args.length - diff];
			for (int i = 0; i < argsNew.length; ++i) {
				argsNew[i] = i < startIndex ? args[i] : i == startIndex ? fsName.toString() : args[i + diff];
			}
			return argsNew;
		}
		if (!autocompleting) {
			throw new WrongUsageException("got.command.brotherhood.edit.nameError");
		}
		return args;
	}

	public static List<String> listBrotherhoodsMatchingLastWord(String[] argsFixed, String[] argsOriginal, int fsNameIndex, GOTPlayerData playerData, boolean leadingOnly) {
		String fsName = argsFixed[fsNameIndex];
		List<String> allBrotherhoodNames = leadingOnly ? playerData.listAllLeadingBrotherhoodNames() : playerData.listAllBrotherhoodNames();
		ArrayList<String> autocompletes = new ArrayList<>();
		for (String nextFsName : allBrotherhoodNames) {
			String autocompFsName = '"' + nextFsName + '"';
			if (!autocompFsName.toLowerCase(Locale.ROOT).startsWith(fsName.toLowerCase(Locale.ROOT))) {
				continue;
			}
			if (argsOriginal.length > argsFixed.length) {
				int diff = argsOriginal.length - argsFixed.length;
				for (int j = 0; j < diff; ++j) {
					autocompFsName = autocompFsName.substring(autocompFsName.indexOf(' ') + 1);
				}
			}
			if (autocompFsName.indexOf(' ') >= 0) {
				autocompFsName = autocompFsName.substring(0, autocompFsName.indexOf(' '));
			}
			autocompletes.add(autocompFsName);
		}
		return getListOfStringsMatchingLastWord(argsOriginal, autocompletes.toArray(new String[0]));
	}

	private static UUID getPlayerIDByName(ICommandSender sender, String username) {
		try {
			EntityPlayerMP entityplayer = getPlayer(sender, username);
			return entityplayer.getUniqueID();
		} catch (PlayerNotFoundException ignored) {
		}
		GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152655_a(username);
		if (profile != null) {
			return profile.getId();
		}
		return null;
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
		String[] args1 = args;
		if (args1.length == 1) {
			return getListOfStringsMatchingLastWord(args1, "create", "option");
		}
		if (args1.length == 2) {
			return getListOfStringsMatchingLastWord(args1, MinecraftServer.getServer().getAllUsernames());
		}
		if (args1.length > 2) {
			String function = args1[0];
			if ("create".equals(function)) {
				return Collections.emptyList();
			}
			if ("option".equals(function)) {
				String[] argsOriginal = Arrays.copyOf(args1, args1.length);
				String ownerName = (args1 = fixArgsForBrotherhood(args1, 2, true))[1];
				UUID ownerID = getPlayerIDByName(sender, ownerName);
				if (ownerID != null) {
					GOTBrotherhood brotherhood;
					GOTPlayerData playerData = GOTLevelData.getData(ownerID);
					String fsName = args1[2];
					if (args1.length == 3) {
						return listBrotherhoodsMatchingLastWord(args1, argsOriginal, 2, playerData, true);
					}
					if (fsName != null && (brotherhood = playerData.getBrotherhoodByName(fsName)) != null) {
						if (args1.length == 4) {
							return getListOfStringsMatchingLastWord(args1, "invite", "add", "remove", "transfer", "op", "deop", "disband", "rename", "icon", "pvp", "hired-ff", "map-show");
						}
						String option = args1[3];
						if ("invite".equals(option) || "add".equals(option)) {
							ArrayList<String> notInBrotherhoodNames = new ArrayList<>();
							for (GameProfile playerProfile : MinecraftServer.getServer().getConfigurationManager().func_152600_g()) {
								UUID playerID = playerProfile.getId();
								if (brotherhood.containsPlayer(playerID)) {
									continue;
								}
								notInBrotherhoodNames.add(playerProfile.getName());
							}
							return getListOfStringsMatchingLastWord(args1, notInBrotherhoodNames.toArray(new String[0]));
						}
						if ("remove".equals(option) || "transfer".equals(option)) {
							ArrayList<String> memberNames = new ArrayList<>();
							for (UUID playerID : brotherhood.getMemberUUIDs()) {
								GameProfile playerProfile = MinecraftServer.getServer().func_152358_ax().func_152652_a(playerID);
								if (playerProfile == null || playerProfile.getName() == null) {
									continue;
								}
								memberNames.add(playerProfile.getName());
							}
							return getListOfStringsMatchingLastWord(args1, memberNames.toArray(new String[0]));
						}
						if ("op".equals(option)) {
							ArrayList<String> notAdminNames = new ArrayList<>();
							for (UUID playerID : brotherhood.getMemberUUIDs()) {
								GameProfile playerProfile;
								if (brotherhood.isAdmin(playerID) || (playerProfile = MinecraftServer.getServer().func_152358_ax().func_152652_a(playerID)) == null || playerProfile.getName() == null) {
									continue;
								}
								notAdminNames.add(playerProfile.getName());
							}
							return getListOfStringsMatchingLastWord(args1, notAdminNames.toArray(new String[0]));
						}
						if ("deop".equals(option)) {
							ArrayList<String> adminNames = new ArrayList<>();
							for (UUID playerID : brotherhood.getMemberUUIDs()) {
								GameProfile playerProfile;
								if (!brotherhood.isAdmin(playerID) || (playerProfile = MinecraftServer.getServer().func_152358_ax().func_152652_a(playerID)) == null || playerProfile.getName() == null) {
									continue;
								}
								adminNames.add(playerProfile.getName());
							}
							return getListOfStringsMatchingLastWord(args1, adminNames.toArray(new String[0]));
						}
						if ("pvp".equals(option) || "hired-ff".equals(option)) {
							return getListOfStringsMatchingLastWord(args1, "prevent", "allow");
						}
						if ("map-show".equals(option)) {
							return getListOfStringsMatchingLastWord(args1, "on", "off");
						}
					}
				}
			}
		}
		return Collections.emptyList();
	}

	@Override
	public String getCommandName() {
		return "brotherhood";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "got.command.brotherhood.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int i) {
		String option;
		return args.length >= 5 && "option".equals(args[0]) && ("invite".equals(option = args[3]) || "add".equals(option) || "remove".equals(option) || "transfer".equals(option)) && i == 4;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		String[] args1 = args;
		if (args1.length >= 3 && "create".equals(args1[0])) {
			args1 = fixArgsForBrotherhood(args1, 2, false);
			String playerName = args1[1];
			String fsName = args1[2];
			if (fsName == null) {
				throw new WrongUsageException("got.command.brotherhood.edit.notFound", playerName, null);
			}
			UUID playerID = getPlayerIDByName(sender, playerName);
			if (playerID != null) {
				GOTPlayerData playerData = GOTLevelData.getData(playerID);
				GOTBrotherhood brotherhood = playerData.getBrotherhoodByName(fsName);
				if (brotherhood == null) {
					playerData.createBrotherhood(fsName, false);
					func_152373_a(sender, this, "got.command.brotherhood.create", playerName, fsName);
					return;
				}
				throw new WrongUsageException("got.command.brotherhood.create.exists", playerName, fsName);
			}
			throw new PlayerNotFoundException();
		}
		if ("option".equals(args1[0])) {
			args1 = fixArgsForBrotherhood(args1, 2, false);
			if (args1.length < 4) {
				throw new PlayerNotFoundException();
			}
			String ownerName = args1[1];
			String fsName = args1[2];
			if (fsName == null) {
				throw new WrongUsageException("got.command.brotherhood.edit.notFound", ownerName, null);
			}
			String option = args1[3];
			UUID ownerID = getPlayerIDByName(sender, ownerName);
			if (ownerID != null) {
				GOTPlayerData ownerData = GOTLevelData.getData(ownerID);
				GOTBrotherhood brotherhood = ownerData.getBrotherhoodByName(fsName);
				if (brotherhood == null || !brotherhood.isOwner(ownerID)) {
					throw new WrongUsageException("got.command.brotherhood.edit.notFound", ownerName, fsName);
				}
				if ("disband".equals(option)) {
					ownerData.disbandBrotherhood(brotherhood, ownerName);
					func_152373_a(sender, this, "got.command.brotherhood.disband", ownerName, fsName);
					return;
				}
				if ("rename".equals(option)) {
					StringBuilder newName = new StringBuilder();
					int startIndex = 4;
					if (!args1[startIndex].isEmpty() && args1[startIndex].charAt(0) == '\"') {
						int endIndex = startIndex;
						while (args1[endIndex].isEmpty() || args1[endIndex].charAt(args1[endIndex].length() - 1) != '\"') {
							endIndex++;
							if (endIndex >= args1.length) {
								throw new WrongUsageException("got.command.brotherhood.rename.error");
							}
						}
						for (int i = startIndex; i <= endIndex; i++) {
							if (i > startIndex) {
								newName.append(' ');
							}
							newName.append(args1[i]);
						}
						newName = new StringBuilder(newName.toString().replace("\"", ""));
					}
					if (!StringUtils.isBlank(newName.toString())) {
						ownerData.renameBrotherhood(brotherhood, newName.toString());
						func_152373_a(sender, this, "got.command.brotherhood.rename", ownerName, fsName, newName.toString());
						return;
					}
					throw new WrongUsageException("got.command.brotherhood.rename.error");
				}
				if ("icon".equals(option)) {
					String iconData = func_147178_a(sender, args1, 4).getUnformattedText();
					if ("clear".equals(iconData)) {
						ownerData.setBrotherhoodIcon(brotherhood, null);
						func_152373_a(sender, this, "got.command.brotherhood.icon", ownerName, fsName, "[none]");
						return;
					}
					ItemStack itemstack;
					try {
						NBTBase nbt = JsonToNBT.func_150315_a(iconData);
						if (!(nbt instanceof NBTTagCompound)) {
							func_152373_a(sender, this, "got.command.brotherhood.icon.tagError", "Not a valid tag");
							return;
						}
						NBTTagCompound compound = (NBTTagCompound) nbt;
						itemstack = ItemStack.loadItemStackFromNBT(compound);
					} catch (NBTException nbtexception) {
						func_152373_a(sender, this, "got.command.brotherhood.icon.tagError", nbtexception.getMessage());
						return;
					}
					if (itemstack != null) {
						ownerData.setBrotherhoodIcon(brotherhood, itemstack);
						func_152373_a(sender, this, "got.command.brotherhood.icon", ownerName, fsName, itemstack.getDisplayName());
						return;
					}
					func_152373_a(sender, this, "got.command.brotherhood.icon.tagError", "No item");
					return;
				}
				if ("pvp".equals(option) || "hired-ff".equals(option)) {
					boolean prevent;
					String setting = args1[4];
					if ("prevent".equals(setting)) {
						prevent = true;
					} else if ("allow".equals(setting)) {
						prevent = false;
					} else {
						throw new WrongUsageException(getCommandUsage(sender));
					}
					if ("pvp".equals(option)) {
						ownerData.setBrotherhoodPreventPVP(brotherhood, prevent);
						if (prevent) {
							func_152373_a(sender, this, "got.command.brotherhood.pvp.prevent", ownerName, fsName);
						} else {
							func_152373_a(sender, this, "got.command.brotherhood.pvp.allow", ownerName, fsName);
						}
						return;
					}
					ownerData.setBrotherhoodPreventHiredFF(brotherhood, prevent);
					if (prevent) {
						func_152373_a(sender, this, "got.command.brotherhood.hiredFF.prevent", ownerName, fsName);
					} else {
						func_152373_a(sender, this, "got.command.brotherhood.hiredFF.allow", ownerName, fsName);
					}
					return;
				}
				if ("map-show".equals(option)) {
					boolean show;
					String setting = args1[4];
					if ("on".equals(setting)) {
						show = true;
					} else if ("off".equals(setting)) {
						show = false;
					} else {
						throw new WrongUsageException(getCommandUsage(sender));
					}
					ownerData.setBrotherhoodShowMapLocations(brotherhood, show);
					if (show) {
						func_152373_a(sender, this, "got.command.brotherhood.mapShow.on", ownerName, fsName);
					} else {
						func_152373_a(sender, this, "got.command.brotherhood.mapShow.off", ownerName, fsName);
					}
					return;
				}
				String playerName = args1[4];
				UUID playerID = getPlayerIDByName(sender, playerName);
				if (playerID == null) {
					throw new PlayerNotFoundException();
				}
				GOTPlayerData playerData = GOTLevelData.getData(playerID);
				if ("invite".equals(option)) {
					if (!brotherhood.containsPlayer(playerID)) {
						ownerData.invitePlayerToBrotherhood(brotherhood, playerID, ownerName);
						func_152373_a(sender, this, "got.command.brotherhood.invite", ownerName, fsName, playerName);
						return;
					}
					throw new WrongUsageException("got.command.brotherhood.edit.alreadyIn", ownerName, fsName, playerName);
				}
				if ("add".equals(option)) {
					if (!brotherhood.containsPlayer(playerID)) {
						ownerData.invitePlayerToBrotherhood(brotherhood, playerID, ownerName);
						playerData.acceptBrotherhoodInvite(brotherhood, false);
						func_152373_a(sender, this, "got.command.brotherhood.add", ownerName, fsName, playerName);
						return;
					}
					throw new WrongUsageException("got.command.brotherhood.edit.alreadyIn", ownerName, fsName, playerName);
				}
				if ("remove".equals(option)) {
					if (brotherhood.hasMember(playerID)) {
						ownerData.removePlayerFromBrotherhood(brotherhood, playerID, ownerName);
						func_152373_a(sender, this, "got.command.brotherhood.remove", ownerName, fsName, playerName);
						return;
					}
					throw new WrongUsageException("got.command.brotherhood.edit.notMember", ownerName, fsName, playerName);
				}
				if ("transfer".equals(option)) {
					if (brotherhood.hasMember(playerID)) {
						ownerData.transferBrotherhood(brotherhood, playerID, ownerName);
						func_152373_a(sender, this, "got.command.brotherhood.transfer", ownerName, fsName, playerName);
						return;
					}
					throw new WrongUsageException("got.command.brotherhood.edit.notMember", ownerName, fsName, playerName);
				}
				if ("op".equals(option)) {
					if (brotherhood.hasMember(playerID)) {
						if (!brotherhood.isAdmin(playerID)) {
							ownerData.setBrotherhoodAdmin(brotherhood, playerID, true, ownerName);
							func_152373_a(sender, this, "got.command.brotherhood.op", ownerName, fsName, playerName);
							return;
						}
						throw new WrongUsageException("got.command.brotherhood.edit.alreadyOp", ownerName, fsName, playerName);
					}
					throw new WrongUsageException("got.command.brotherhood.edit.notMember", ownerName, fsName, playerName);
				}
				if ("deop".equals(option)) {
					if (brotherhood.hasMember(playerID)) {
						if (brotherhood.isAdmin(playerID)) {
							ownerData.setBrotherhoodAdmin(brotherhood, playerID, false, ownerName);
							func_152373_a(sender, this, "got.command.brotherhood.deop", ownerName, fsName, playerName);
							return;
						}
						throw new WrongUsageException("got.command.brotherhood.edit.notOp", ownerName, fsName, playerName);
					}
					throw new WrongUsageException("got.command.brotherhood.edit.notMember", ownerName, fsName, playerName);
				}
			}
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}