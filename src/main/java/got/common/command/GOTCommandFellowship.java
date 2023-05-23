package got.common.command;

import com.mojang.authlib.GameProfile;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.fellowship.GOTFellowship;
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

public class GOTCommandFellowship extends CommandBase {
	public static String[] fixArgsForFellowship(String[] args, int startIndex, boolean autocompleting) {
		if (args[startIndex].startsWith("\"")) {
			int endIndex = startIndex;
			boolean foundEnd = false;
			while (!foundEnd) {
				if (args[endIndex].endsWith("\"")) {
					foundEnd = true;
					continue;
				}
				if (endIndex >= args.length - 1) {
					if (autocompleting) {
						break;
					}
					throw new WrongUsageException("got.command.fellowship.edit.nameError");
				}
				++endIndex;
			}
			StringBuilder fsName = new StringBuilder();
			for (int i = startIndex; i <= endIndex; ++i) {
				if (i > startIndex) {
					fsName.append(" ");
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
			throw new WrongUsageException("got.command.fellowship.edit.nameError");
		}
		return args;
	}

	public static List<String> listFellowshipsMatchingLastWord(String[] argsFixed, String[] argsOriginal, int fsNameIndex, GOTPlayerData playerData, boolean leadingOnly) {
		String fsName = argsFixed[fsNameIndex];
		List<String> allFellowshipNames = leadingOnly ? playerData.listAllLeadingFellowshipNames() : playerData.listAllFellowshipNames();
		ArrayList<String> autocompletes = new ArrayList<>();
		for (String nextFsName : allFellowshipNames) {
			String autocompFsName = "\"" + nextFsName + "\"";
			if (!autocompFsName.toLowerCase().startsWith(fsName.toLowerCase())) {
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
		return CommandBase.getListOfStringsMatchingLastWord(argsOriginal, autocompletes.toArray(new String[0]));
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			return CommandBase.getListOfStringsMatchingLastWord(args, "create", "option");
		}
		if (args.length == 2) {
			return CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
		}
		if (args.length > 2) {
			String function = args[0];
			if ("create".equals(function)) {
				return Collections.emptyList();
			}
			if ("option".equals(function)) {
				String[] argsOriginal = Arrays.copyOf(args, args.length);
				String ownerName = (args = fixArgsForFellowship(args, 2, true))[1];
				UUID ownerID = getPlayerIDByName(sender, ownerName);
				if (ownerID != null) {
					GOTFellowship fellowship;
					GOTPlayerData playerData = GOTLevelData.getData(ownerID);
					String fsName = args[2];
					if (args.length == 3) {
						return listFellowshipsMatchingLastWord(args, argsOriginal, 2, playerData, true);
					}
					if (fsName != null && (fellowship = playerData.getFellowshipByName(fsName)) != null) {
						if (args.length == 4) {
							return CommandBase.getListOfStringsMatchingLastWord(args, "invite", "add", "remove", "transfer", "op", "deop", "disband", "rename", "icon", "pvp", "hired-ff", "map-show");
						}
						String option = args[3];
						if ("invite".equals(option) || "add".equals(option)) {
							ArrayList<String> notInFellowshipNames = new ArrayList<>();
							for (GameProfile playerProfile : MinecraftServer.getServer().getConfigurationManager().func_152600_g()) {
								UUID playerID = playerProfile.getId();
								if (fellowship.containsPlayer(playerID)) {
									continue;
								}
								notInFellowshipNames.add(playerProfile.getName());
							}
							return CommandBase.getListOfStringsMatchingLastWord(args, notInFellowshipNames.toArray(new String[0]));
						}
						if ("remove".equals(option) || "transfer".equals(option)) {
							ArrayList<String> memberNames = new ArrayList<>();
							for (UUID playerID : fellowship.getMemberUUIDs()) {
								GameProfile playerProfile = MinecraftServer.getServer().func_152358_ax().func_152652_a(playerID);
								if (playerProfile == null || playerProfile.getName() == null) {
									continue;
								}
								memberNames.add(playerProfile.getName());
							}
							return CommandBase.getListOfStringsMatchingLastWord(args, memberNames.toArray(new String[0]));
						}
						if ("op".equals(option)) {
							ArrayList<String> notAdminNames = new ArrayList<>();
							for (UUID playerID : fellowship.getMemberUUIDs()) {
								GameProfile playerProfile;
								if (fellowship.isAdmin(playerID) || (playerProfile = MinecraftServer.getServer().func_152358_ax().func_152652_a(playerID)) == null || playerProfile.getName() == null) {
									continue;
								}
								notAdminNames.add(playerProfile.getName());
							}
							return CommandBase.getListOfStringsMatchingLastWord(args, notAdminNames.toArray(new String[0]));
						}
						if ("deop".equals(option)) {
							ArrayList<String> adminNames = new ArrayList<>();
							for (UUID playerID : fellowship.getMemberUUIDs()) {
								GameProfile playerProfile;
								if (!fellowship.isAdmin(playerID) || (playerProfile = MinecraftServer.getServer().func_152358_ax().func_152652_a(playerID)) == null || playerProfile.getName() == null) {
									continue;
								}
								adminNames.add(playerProfile.getName());
							}
							return CommandBase.getListOfStringsMatchingLastWord(args, adminNames.toArray(new String[0]));
						}
						if ("pvp".equals(option) || "hired-ff".equals(option)) {
							return CommandBase.getListOfStringsMatchingLastWord(args, "prevent", "allow");
						}
						if ("map-show".equals(option)) {
							return CommandBase.getListOfStringsMatchingLastWord(args, "on", "off");
						}
					}
				}
			}
		}
		return Collections.emptyList();
	}

	@Override
	public String getCommandName() {
		return "fellowship";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "got.command.fellowship.usage";
	}

	public UUID getPlayerIDByName(ICommandSender sender, String username) {
		try {
			EntityPlayerMP entityplayer = CommandBase.getPlayer(sender, username);
			if (entityplayer != null) {
				return entityplayer.getUniqueID();
			}
		} catch (PlayerNotFoundException ignored) {
		}
		GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152655_a(username);
		if (profile != null) {
			return profile.getId();
		}
		return null;
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int i) {
		String option;
		if (args.length >= 5 && "option".equals(args[0]) && ("invite".equals(option = args[3]) || "add".equals(option) || "remove".equals(option) || "transfer".equals(option))) {
			return i == 4;
		}
		return false;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length >= 3 && "create".equals(args[0])) {
			args = fixArgsForFellowship(args, 2, false);
			String playerName = args[1];
			String fsName = args[2];
			if (fsName == null) {
				throw new WrongUsageException("got.command.fellowship.edit.notFound", playerName, null);
			}
			UUID playerID = getPlayerIDByName(sender, playerName);
			if (playerID != null) {
				GOTPlayerData playerData = GOTLevelData.getData(playerID);
				GOTFellowship fellowship = playerData.getFellowshipByName(fsName);
				if (fellowship == null) {
					playerData.createFellowship(fsName, false);
					func_152373_a(sender, this, "got.command.fellowship.create", playerName, fsName);
					return;
				}
				throw new WrongUsageException("got.command.fellowship.create.exists", playerName, fsName);
			}
			throw new PlayerNotFoundException();
		}
		if ("option".equals(args[0])) {
			args = fixArgsForFellowship(args, 2, false);
			if (args.length >= 4) {
				String ownerName = args[1];
				String fsName = args[2];
				if (fsName == null) {
					throw new WrongUsageException("got.command.fellowship.edit.notFound", ownerName, null);
				}
				String option = args[3];
				UUID ownerID = getPlayerIDByName(sender, ownerName);
				if (ownerID != null) {
					GOTPlayerData ownerData = GOTLevelData.getData(ownerID);
					GOTFellowship fellowship = ownerData.getFellowshipByName(fsName);
					if (fellowship != null && fellowship.isOwner(ownerID)) {
						if ("disband".equals(option)) {
							ownerData.disbandFellowship(fellowship, ownerName);
							func_152373_a(sender, this, "got.command.fellowship.disband", ownerName, fsName);
							return;
						}
						if ("rename".equals(option)) {
							StringBuilder newName = new StringBuilder();
							int startIndex = 4;
							if (args[startIndex].startsWith("\"")) {
								int endIndex = startIndex;
								while (!args[endIndex].endsWith("\"")) {
									endIndex++;
									if (endIndex >= args.length)
										throw new WrongUsageException("got.command.fellowship.rename.error");
								}
								for (int i = startIndex; i <= endIndex; i++) {
									if (i > startIndex) newName.append(" ");
									newName.append(args[i]);
								}
								newName = new StringBuilder(newName.toString().replace("\"", ""));
							}
							if (!StringUtils.isBlank(newName.toString())) {
								ownerData.renameFellowship(fellowship, newName.toString());
								func_152373_a(sender, this, "got.command.fellowship.rename", ownerName, fsName, newName.toString());
								return;
							}
							throw new WrongUsageException("got.command.fellowship.rename.error");
						}
						if ("icon".equals(option)) {
							String iconData = func_147178_a(sender, args, 4).getUnformattedText();
							if ("clear".equals(iconData)) {
								ownerData.setFellowshipIcon(fellowship, null);
								func_152373_a(sender, this, "got.command.fellowship.icon", ownerName, fsName, "[none]");
								return;
							}
							ItemStack itemstack;
							try {
								NBTBase nbt = JsonToNBT.func_150315_a(iconData);
								if (nbt instanceof NBTTagCompound) {
									NBTTagCompound compound = (NBTTagCompound) nbt;
									itemstack = ItemStack.loadItemStackFromNBT(compound);
								} else {
									func_152373_a(sender, this, "got.command.fellowship.icon.tagError", "Not a valid tag");
									return;
								}
							} catch (NBTException nbtexception) {
								func_152373_a(sender, this, "got.command.fellowship.icon.tagError", nbtexception.getMessage());
								return;
							}
							if (itemstack != null) {
								ownerData.setFellowshipIcon(fellowship, itemstack);
								func_152373_a(sender, this, "got.command.fellowship.icon", ownerName, fsName, itemstack.getDisplayName());
								return;
							}
							func_152373_a(sender, this, "got.command.fellowship.icon.tagError", "No item");
							return;
						}
						if ("pvp".equals(option) || "hired-ff".equals(option)) {
							boolean prevent;
							String setting = args[4];
							if ("prevent".equals(setting)) {
								prevent = true;
							} else if ("allow".equals(setting)) {
								prevent = false;
							} else {
								throw new WrongUsageException(getCommandUsage(sender));
							}
							if ("pvp".equals(option)) {
								ownerData.setFellowshipPreventPVP(fellowship, prevent);
								if (prevent) {
									func_152373_a(sender, this, "got.command.fellowship.pvp.prevent", ownerName, fsName);
								} else {
									func_152373_a(sender, this, "got.command.fellowship.pvp.allow", ownerName, fsName);
								}
								return;
							}
							ownerData.setFellowshipPreventHiredFF(fellowship, prevent);
							if (prevent) {
								func_152373_a(sender, this, "got.command.fellowship.hiredFF.prevent", ownerName, fsName);
							} else {
								func_152373_a(sender, this, "got.command.fellowship.hiredFF.allow", ownerName, fsName);
							}
							return;
						} else {
							if ("map-show".equals(option)) {
								boolean show;
								String setting = args[4];
								if ("on".equals(setting)) {
									show = true;
								} else if ("off".equals(setting)) {
									show = false;
								} else {
									throw new WrongUsageException(getCommandUsage(sender));
								}
								ownerData.setFellowshipShowMapLocations(fellowship, show);
								if (show) {
									func_152373_a(sender, this, "got.command.fellowship.mapShow.on", ownerName, fsName);
								} else {
									func_152373_a(sender, this, "got.command.fellowship.mapShow.off", ownerName, fsName);
								}
								return;
							}
							String playerName = args[4];
							UUID playerID = getPlayerIDByName(sender, playerName);
							if (playerID != null) {
								GOTPlayerData playerData = GOTLevelData.getData(playerID);
								if ("invite".equals(option)) {
									if (!fellowship.containsPlayer(playerID)) {
										ownerData.invitePlayerToFellowship(fellowship, playerID, ownerName);
										func_152373_a(sender, this, "got.command.fellowship.invite", ownerName, fsName, playerName);
										return;
									}
									throw new WrongUsageException("got.command.fellowship.edit.alreadyIn", ownerName, fsName, playerName);
								}
								if ("add".equals(option)) {
									if (!fellowship.containsPlayer(playerID)) {
										ownerData.invitePlayerToFellowship(fellowship, playerID, ownerName);
										playerData.acceptFellowshipInvite(fellowship, false);
										func_152373_a(sender, this, "got.command.fellowship.add", ownerName, fsName, playerName);
										return;
									}
									throw new WrongUsageException("got.command.fellowship.edit.alreadyIn", ownerName, fsName, playerName);
								}
								if ("remove".equals(option)) {
									if (fellowship.hasMember(playerID)) {
										ownerData.removePlayerFromFellowship(fellowship, playerID, ownerName);
										func_152373_a(sender, this, "got.command.fellowship.remove", ownerName, fsName, playerName);
										return;
									}
									throw new WrongUsageException("got.command.fellowship.edit.notMember", ownerName, fsName, playerName);
								}
								if ("transfer".equals(option)) {
									if (fellowship.hasMember(playerID)) {
										ownerData.transferFellowship(fellowship, playerID, ownerName);
										func_152373_a(sender, this, "got.command.fellowship.transfer", ownerName, fsName, playerName);
										return;
									}
									throw new WrongUsageException("got.command.fellowship.edit.notMember", ownerName, fsName, playerName);
								}
								if ("op".equals(option)) {
									if (fellowship.hasMember(playerID)) {
										if (!fellowship.isAdmin(playerID)) {
											ownerData.setFellowshipAdmin(fellowship, playerID, true, ownerName);
											func_152373_a(sender, this, "got.command.fellowship.op", ownerName, fsName, playerName);
											return;
										}
										throw new WrongUsageException("got.command.fellowship.edit.alreadyOp", ownerName, fsName, playerName);
									}
									throw new WrongUsageException("got.command.fellowship.edit.notMember", ownerName, fsName, playerName);
								}
								if ("deop".equals(option)) {
									if (fellowship.hasMember(playerID)) {
										if (fellowship.isAdmin(playerID)) {
											ownerData.setFellowshipAdmin(fellowship, playerID, false, ownerName);
											func_152373_a(sender, this, "got.command.fellowship.deop", ownerName, fsName, playerName);
											return;
										}
										throw new WrongUsageException("got.command.fellowship.edit.notOp", ownerName, fsName, playerName);
									}
									throw new WrongUsageException("got.command.fellowship.edit.notMember", ownerName, fsName, playerName);
								}
							} else {
								throw new PlayerNotFoundException();
							}
						}
					} else {
						throw new WrongUsageException("got.command.fellowship.edit.notFound", ownerName, fsName);
					}
				}
			} else {
				throw new PlayerNotFoundException();
			}
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
