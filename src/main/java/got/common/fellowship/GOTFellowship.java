package got.common.fellowship;

import java.util.*;

import org.apache.commons.lang3.StringUtils;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.common.GOTLevelData;
import got.common.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.event.ClickEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraftforge.common.ForgeHooks;

public class GOTFellowship {
	public boolean needsSave = false;
	public UUID fellowshipUUID;
	public String fellowshipName;
	public boolean disbanded = false;
	public ItemStack fellowshipIcon;
	public UUID ownerUUID;
	public List<UUID> memberUUIDs = new ArrayList<>();
	public Set<UUID> adminUUIDs = new HashSet<>();
	public boolean preventPVP = true;
	public boolean preventHiredFF = true;
	public boolean showMapLocations = true;

	public GOTFellowship() {
		fellowshipUUID = UUID.randomUUID();
	}

	public GOTFellowship(UUID fsID) {
		fellowshipUUID = fsID;
	}

	public GOTFellowship(UUID owner, String name) {
		this();
		ownerUUID = owner;
		fellowshipName = name;
	}

	public void addMember(UUID player) {
		if (!isOwner(player) && !memberUUIDs.contains(player)) {
			memberUUIDs.add(player);
			GOTLevelData.getData(player).addFellowship(this);
			updateForAllMembers(new FellowshipUpdateType.AddMember(player));
			markDirty();
		}
	}

	public boolean containsPlayer(UUID player) {
		return isOwner(player) || hasMember(player);
	}

	public void createAndRegister() {
		GOTFellowshipData.addFellowship(this);
		GOTLevelData.getData(ownerUUID).addFellowship(this);
		updateForAllMembers(new FellowshipUpdateType.Full());
		markDirty();
	}

	public void disband() {
		disbanded = true;
		ArrayList<UUID> copyMemberIDs = new ArrayList<>(memberUUIDs);
		for (UUID player : copyMemberIDs) {
			removeMember(player);
		}
	}

	public List<UUID> getAllPlayerUUIDs() {
		ArrayList<UUID> list = new ArrayList<>();
		list.add(ownerUUID);
		list.addAll(memberUUIDs);
		return list;
	}

	public UUID getFellowshipID() {
		return fellowshipUUID;
	}

	public ItemStack getIcon() {
		return fellowshipIcon;
	}

	public List<UUID> getMemberUUIDs() {
		return memberUUIDs;
	}

	public String getName() {
		return fellowshipName;
	}

	public UUID getOwner() {
		return ownerUUID;
	}

	public boolean getPreventHiredFriendlyFire() {
		return preventHiredFF;
	}

	public boolean getPreventPVP() {
		return preventPVP;
	}

	public boolean getShowMapLocations() {
		return showMapLocations;
	}

	public boolean hasMember(UUID player) {
		return memberUUIDs.contains(player);
	}

	public boolean isAdmin(UUID player) {
		return hasMember(player) && adminUUIDs.contains(player);
	}

	public boolean isDisbanded() {
		return disbanded;
	}

	public boolean isOwner(UUID player) {
		return ownerUUID.equals(player);
	}

	public void load(NBTTagCompound fsData) {
		if (fsData.hasKey("Owner")) {
			ownerUUID = UUID.fromString(fsData.getString("Owner"));
		}
		memberUUIDs.clear();
		adminUUIDs.clear();
		NBTTagList memberTags = fsData.getTagList("Members", 10);
		for (int i = 0; i < memberTags.tagCount(); ++i) {
			NBTTagCompound nbt = memberTags.getCompoundTagAt(i);
			UUID member = UUID.fromString(nbt.getString("Member"));
			if (member == null) {
				continue;
			}
			memberUUIDs.add(member);
			if (!nbt.hasKey("Admin") || !(nbt.getBoolean("Admin"))) {
				continue;
			}
			adminUUIDs.add(member);
		}
		if (fsData.hasKey("Name")) {
			fellowshipName = fsData.getString("Name");
		}
		if (fsData.hasKey("Icon")) {
			NBTTagCompound itemData = fsData.getCompoundTag("Icon");
			fellowshipIcon = ItemStack.loadItemStackFromNBT(itemData);
		}
		if (fsData.hasKey("PreventPVP")) {
			preventPVP = fsData.getBoolean("PreventPVP");
		}
		if (fsData.hasKey("PreventPVP")) {
			preventHiredFF = fsData.getBoolean("PreventHiredFF");
		}
		if (fsData.hasKey("ShowMap")) {
			showMapLocations = fsData.getBoolean("ShowMap");
		}
		validate();
	}

	public void markDirty() {
		needsSave = true;
	}

	public boolean needsSave() {
		return needsSave;
	}

	public void removeMember(UUID player) {
		if (memberUUIDs.contains(player)) {
			memberUUIDs.remove(player);
			if (adminUUIDs.contains(player)) {
				adminUUIDs.remove(player);
			}
			GOTLevelData.getData(player).removeFellowship(this);
			updateForAllMembers(new FellowshipUpdateType.RemoveMember(player));
			markDirty();
		}
	}

	public void save(NBTTagCompound fsData) {
		if (ownerUUID != null) {
			fsData.setString("Owner", ownerUUID.toString());
		}
		NBTTagList memberTags = new NBTTagList();
		for (UUID member : memberUUIDs) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("Member", member.toString());
			if (adminUUIDs.contains(member)) {
				nbt.setBoolean("Admin", true);
			}
			memberTags.appendTag(nbt);
		}
		fsData.setTag("Members", memberTags);
		if (fellowshipName != null) {
			fsData.setString("Name", fellowshipName);
		}
		if (fellowshipIcon != null) {
			NBTTagCompound itemData = new NBTTagCompound();
			fellowshipIcon.writeToNBT(itemData);
			fsData.setTag("Icon", itemData);
		}
		fsData.setBoolean("PreventPVP", preventPVP);
		fsData.setBoolean("PreventHiredFF", preventHiredFF);
		fsData.setBoolean("ShowMap", showMapLocations);
		needsSave = false;
	}

	public void sendFellowshipMessage(EntityPlayerMP sender, String message) {
		if (sender.func_147096_v() == EntityPlayer.EnumChatVisibility.HIDDEN) {
			ChatComponentTranslation msgCannotSend = new ChatComponentTranslation("chat.cannotSend");
			msgCannotSend.getChatStyle().setColor(EnumChatFormatting.RED);
			sender.playerNetServerHandler.sendPacket(new S02PacketChat(msgCannotSend));
		} else {
			sender.func_143004_u();
			message = StringUtils.normalizeSpace(message);
			if (StringUtils.isBlank(message)) {
				return;
			}
			for (int i = 0; i < message.length(); ++i) {
				if (ChatAllowedCharacters.isAllowedCharacter(message.charAt(i))) {
					continue;
				}
				sender.playerNetServerHandler.kickPlayerFromServer("Illegal characters in chat");
				return;
			}
			ClickEvent fMsgClickEvent = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/fmsg \"" + getName() + "\" ");
			IChatComponent msgComponent = ForgeHooks.newChatWithLinks(message);
			msgComponent.getChatStyle().setColor(EnumChatFormatting.YELLOW);
			IChatComponent senderComponent = sender.func_145748_c_();
			senderComponent.getChatStyle().setChatClickEvent(fMsgClickEvent);
			ChatComponentTranslation chatComponent = new ChatComponentTranslation("chat.type.text", senderComponent, "");
			chatComponent = ForgeHooks.onServerChatEvent(sender.playerNetServerHandler, message, chatComponent);
			if (chatComponent == null) {
				return;
			}
			chatComponent.appendSibling(msgComponent);
			ChatComponentTranslation fsComponent = new ChatComponentTranslation("got.command.fmsg.fsPrefix", getName());
			fsComponent.getChatStyle().setColor(EnumChatFormatting.YELLOW);
			fsComponent.getChatStyle().setChatClickEvent(fMsgClickEvent);
			ChatComponentTranslation fullChatComponent = new ChatComponentTranslation("%s %s", fsComponent, chatComponent);
			MinecraftServer server = MinecraftServer.getServer();
			server.addChatMessage(fullChatComponent);
			S02PacketChat packetChat = new S02PacketChat(fullChatComponent, false);
			for (Object player : server.getConfigurationManager().playerEntityList) {
				EntityPlayerMP entityplayer = (EntityPlayerMP) player;
				if (!containsPlayer(entityplayer.getUniqueID())) {
					continue;
				}
				entityplayer.playerNetServerHandler.sendPacket(packetChat);
			}
		}
	}

	public void sendNotification(EntityPlayer entityplayer, String key, Object... args) {
		ChatComponentTranslation message = new ChatComponentTranslation(key, args);
		message.getChatStyle().setColor(EnumChatFormatting.YELLOW);
		entityplayer.addChatMessage(message);
		GOTPacketFellowshipNotification packet = new GOTPacketFellowshipNotification(message);
		GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
	}

	public void setAdmin(UUID player, boolean flag) {
		if (memberUUIDs.contains(player)) {
			if (flag && !adminUUIDs.contains(player)) {
				adminUUIDs.add(player);
				updateForAllMembers(new FellowshipUpdateType.SetAdmin(player));
				markDirty();
			} else if (!flag && adminUUIDs.contains(player)) {
				adminUUIDs.remove(player);
				updateForAllMembers(new FellowshipUpdateType.RemoveAdmin(player));
				markDirty();
			}
		}
	}

	public void setIcon(ItemStack itemstack) {
		fellowshipIcon = itemstack;
		updateForAllMembers(new FellowshipUpdateType.ChangeIcon());
		markDirty();
	}

	public void setName(String name) {
		fellowshipName = name;
		updateForAllMembers(new FellowshipUpdateType.Rename());
		markDirty();
	}

	public void setOwner(UUID owner) {
		UUID prevOwner = ownerUUID;
		if (prevOwner != null && !memberUUIDs.contains(prevOwner)) {
			memberUUIDs.add(0, prevOwner);
		}
		ownerUUID = owner;
		if (memberUUIDs.contains(owner)) {
			memberUUIDs.remove(owner);
		}
		if (adminUUIDs.contains(owner)) {
			adminUUIDs.remove(owner);
		}
		GOTLevelData.getData(ownerUUID).addFellowship(this);
		updateForAllMembers(new FellowshipUpdateType.SetOwner(ownerUUID));
		markDirty();
	}

	public void setPreventHiredFriendlyFire(boolean flag) {
		preventHiredFF = flag;
		updateForAllMembers(new FellowshipUpdateType.ToggleHiredFriendlyFire());
		markDirty();
	}

	public void setPreventPVP(boolean flag) {
		preventPVP = flag;
		updateForAllMembers(new FellowshipUpdateType.TogglePvp());
		markDirty();
	}

	public void setShowMapLocations(boolean flag) {
		showMapLocations = flag;
		updateForAllMembers(new FellowshipUpdateType.ToggleShowMapLocations());
		markDirty();
	}

	public void updateForAllMembers(FellowshipUpdateType updateType) {
		for (UUID player : getAllPlayerUUIDs()) {
			GOTLevelData.getData(player).updateFellowship(this, updateType);
		}
	}

	public void validate() {
		if (fellowshipUUID == null) {
			fellowshipUUID = UUID.randomUUID();
		}
		if (ownerUUID == null) {
			ownerUUID = UUID.randomUUID();
		}
	}
}
