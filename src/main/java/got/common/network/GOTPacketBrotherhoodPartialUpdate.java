package got.common.network;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.brotherhood.GOTBrotherhood;
import got.common.brotherhood.GOTBrotherhoodClient;
import got.common.database.GOTTitle;
import got.common.util.GOTLog;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;
import java.util.UUID;

public abstract class GOTPacketBrotherhoodPartialUpdate implements IMessage {
	private UUID brotherhoodID;

	protected GOTPacketBrotherhoodPartialUpdate() {
	}

	protected GOTPacketBrotherhoodPartialUpdate(GOTBrotherhood fs) {
		brotherhoodID = fs.getBrotherhoodID();
	}

	@Override
	public void fromBytes(ByteBuf data) {
		brotherhoodID = new UUID(data.readLong(), data.readLong());
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeLong(brotherhoodID.getMostSignificantBits());
		data.writeLong(brotherhoodID.getLeastSignificantBits());
	}

	protected abstract void updateClient(GOTBrotherhoodClient var1);

	protected UUID getBrotherhoodID() {
		return brotherhoodID;
	}

	public static class AddMember extends OnePlayerUpdate {
		private GOTTitle.PlayerTitle playerTitle;

		@SuppressWarnings("unused")
		public AddMember() {
		}

		public AddMember(GOTBrotherhood fs, UUID member) {
			super(fs, member);
			playerTitle = GOTLevelData.getData(member).getPlayerTitle();
		}

		@Override
		public void fromBytes(ByteBuf data) {
			super.fromBytes(data);
			playerTitle = GOTTitle.PlayerTitle.readNullableTitle(data);
		}

		@Override
		public void toBytes(ByteBuf data) {
			super.toBytes(data);
			GOTTitle.PlayerTitle.writeNullableTitle(data, playerTitle);
		}

		@Override
		public void updateClient(GOTBrotherhoodClient brotherhood) {
			brotherhood.addMember(playerProfile, playerTitle);
		}

		public static class Handler extends GOTPacketBrotherhoodPartialUpdate.Handler<AddMember> {
		}
	}

	public static class ChangeIcon extends GOTPacketBrotherhoodPartialUpdate {
		private ItemStack brotherhoodIcon;

		@SuppressWarnings("unused")
		public ChangeIcon() {
		}

		public ChangeIcon(GOTBrotherhood fs) {
			super(fs);
			brotherhoodIcon = fs.getIcon();
		}

		@Override
		public void fromBytes(ByteBuf data) {
			super.fromBytes(data);
			NBTTagCompound iconData = new NBTTagCompound();
			try {
				iconData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
			} catch (IOException e) {
				FMLLog.severe("Hummel009: Error reading brotherhood icon data");
				e.printStackTrace();
			}
			brotherhoodIcon = ItemStack.loadItemStackFromNBT(iconData);
		}

		@Override
		public void toBytes(ByteBuf data) {
			super.toBytes(data);
			NBTTagCompound iconData = new NBTTagCompound();
			if (brotherhoodIcon != null) {
				brotherhoodIcon.writeToNBT(iconData);
			}
			try {
				new PacketBuffer(data).writeNBTTagCompoundToBuffer(iconData);
			} catch (IOException e) {
				FMLLog.severe("Hummel009: Error writing brotherhood icon data");
				e.printStackTrace();
			}
		}

		@Override
		public void updateClient(GOTBrotherhoodClient brotherhood) {
			brotherhood.setIcon(brotherhoodIcon);
		}

		public static class Handler extends GOTPacketBrotherhoodPartialUpdate.Handler<ChangeIcon> {
		}
	}

	private abstract static class Handler<P extends GOTPacketBrotherhoodPartialUpdate> implements IMessageHandler<P, IMessage> {
		@Override
		public IMessage onMessage(P packet, MessageContext context) {
			EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			GOTBrotherhoodClient brotherhood = pd.getClientBrotherhoodByID(packet.getBrotherhoodID());
			if (brotherhood != null) {
				packet.updateClient(brotherhood);
			} else {
				GOTLog.getLogger().warn("Client couldn't find brotherhood for ID {}", packet.getBrotherhoodID());
			}
			return null;
		}
	}

	private abstract static class OnePlayerUpdate extends GOTPacketBrotherhoodPartialUpdate {
		protected GameProfile playerProfile;

		protected OnePlayerUpdate() {
		}

		protected OnePlayerUpdate(GOTBrotherhood fs, UUID player) {
			super(fs);
			playerProfile = GOTPacketBrotherhood.getPlayerProfileWithUsername(player);
		}

		@Override
		public void fromBytes(ByteBuf data) {
			super.fromBytes(data);
			playerProfile = GOTPacketBrotherhood.readPlayerUuidAndUsername(data);
		}

		@Override
		public void toBytes(ByteBuf data) {
			super.toBytes(data);
			GOTPacketBrotherhood.writePlayerUuidAndUsername(data, playerProfile);
		}
	}

	public static class RemoveAdmin extends OnePlayerUpdate {
		private boolean isAdminned;

		@SuppressWarnings("unused")
		public RemoveAdmin() {
		}

		public RemoveAdmin(GOTBrotherhood fs, UUID admin, boolean adminned) {
			super(fs, admin);
			isAdminned = adminned;
		}

		@Override
		public void fromBytes(ByteBuf data) {
			super.fromBytes(data);
			isAdminned = data.readBoolean();
		}

		@Override
		public void toBytes(ByteBuf data) {
			super.toBytes(data);
			data.writeBoolean(isAdminned);
		}

		@Override
		public void updateClient(GOTBrotherhoodClient brotherhood) {
			brotherhood.removeAdmin(playerProfile.getId(), isAdminned);
		}

		public static class Handler extends GOTPacketBrotherhoodPartialUpdate.Handler<RemoveAdmin> {
		}
	}

	public static class RemoveMember extends OnePlayerUpdate {
		@SuppressWarnings("unused")
		public RemoveMember() {
		}

		public RemoveMember(GOTBrotherhood fs, UUID member) {
			super(fs, member);
		}

		@Override
		public void updateClient(GOTBrotherhoodClient brotherhood) {
			brotherhood.removeMember(playerProfile);
		}

		public static class Handler extends GOTPacketBrotherhoodPartialUpdate.Handler<RemoveMember> {
		}
	}

	public static class Rename extends GOTPacketBrotherhoodPartialUpdate {
		private String brotherhoodName;

		public Rename() {
		}

		public Rename(GOTBrotherhood fs) {
			super(fs);
			brotherhoodName = fs.getName();
		}

		@Override
		public void fromBytes(ByteBuf data) {
			super.fromBytes(data);
			byte fsNameLength = data.readByte();
			ByteBuf fsNameBytes = data.readBytes(fsNameLength);
			brotherhoodName = fsNameBytes.toString(Charsets.UTF_8);
		}

		@Override
		public void toBytes(ByteBuf data) {
			super.toBytes(data);
			byte[] fsNameBytes = brotherhoodName.getBytes(Charsets.UTF_8);
			data.writeByte(fsNameBytes.length);
			data.writeBytes(fsNameBytes);
		}

		@Override
		public void updateClient(GOTBrotherhoodClient brotherhood) {
			brotherhood.setName(brotherhoodName);
		}

		public static class Handler extends GOTPacketBrotherhoodPartialUpdate.Handler<Rename> {
		}
	}

	public static class SetAdmin extends OnePlayerUpdate {
		private boolean isAdminned;

		@SuppressWarnings("unused")
		public SetAdmin() {
		}

		public SetAdmin(GOTBrotherhood fs, UUID admin, boolean adminned) {
			super(fs, admin);
			isAdminned = adminned;
		}

		@Override
		public void fromBytes(ByteBuf data) {
			super.fromBytes(data);
			isAdminned = data.readBoolean();
		}

		@Override
		public void toBytes(ByteBuf data) {
			super.toBytes(data);
			data.writeBoolean(isAdminned);
		}

		@Override
		public void updateClient(GOTBrotherhoodClient brotherhood) {
			brotherhood.setAdmin(playerProfile.getId(), isAdminned);
		}

		public static class Handler extends GOTPacketBrotherhoodPartialUpdate.Handler<SetAdmin> {
		}
	}

	public static class SetOwner extends OnePlayerUpdate {
		private boolean isOwned;

		@SuppressWarnings("unused")
		public SetOwner() {
		}

		public SetOwner(GOTBrotherhood fs, UUID owner, boolean owned) {
			super(fs, owner);
			isOwned = owned;
		}

		@Override
		public void fromBytes(ByteBuf data) {
			super.fromBytes(data);
			isOwned = data.readBoolean();
		}

		@Override
		public void toBytes(ByteBuf data) {
			super.toBytes(data);
			data.writeBoolean(isOwned);
		}

		@Override
		public void updateClient(GOTBrotherhoodClient brotherhood) {
			brotherhood.setOwner(playerProfile, isOwned);
		}

		public static class Handler extends GOTPacketBrotherhoodPartialUpdate.Handler<SetOwner> {
		}
	}

	public static class ToggleHiredFriendlyFire extends GOTPacketBrotherhoodPartialUpdate {
		private boolean preventHiredFF;

		@SuppressWarnings("unused")
		public ToggleHiredFriendlyFire() {
		}

		public ToggleHiredFriendlyFire(GOTBrotherhood fs) {
			super(fs);
			preventHiredFF = fs.getPreventHiredFriendlyFire();
		}

		@Override
		public void fromBytes(ByteBuf data) {
			super.fromBytes(data);
			preventHiredFF = data.readBoolean();
		}

		@Override
		public void toBytes(ByteBuf data) {
			super.toBytes(data);
			data.writeBoolean(preventHiredFF);
		}

		@Override
		public void updateClient(GOTBrotherhoodClient brotherhood) {
			brotherhood.setPreventHiredFriendlyFire(preventHiredFF);
		}

		public static class Handler extends GOTPacketBrotherhoodPartialUpdate.Handler<ToggleHiredFriendlyFire> {
		}
	}

	public static class TogglePvp extends GOTPacketBrotherhoodPartialUpdate {
		private boolean preventPVP;

		@SuppressWarnings("unused")
		public TogglePvp() {
		}

		public TogglePvp(GOTBrotherhood fs) {
			super(fs);
			preventPVP = fs.getPreventPVP();
		}

		@Override
		public void fromBytes(ByteBuf data) {
			super.fromBytes(data);
			preventPVP = data.readBoolean();
		}

		@Override
		public void toBytes(ByteBuf data) {
			super.toBytes(data);
			data.writeBoolean(preventPVP);
		}

		@Override
		public void updateClient(GOTBrotherhoodClient brotherhood) {
			brotherhood.setPreventPVP(preventPVP);
		}

		public static class Handler extends GOTPacketBrotherhoodPartialUpdate.Handler<TogglePvp> {
		}
	}

	public static class ToggleShowMap extends GOTPacketBrotherhoodPartialUpdate {
		private boolean showMapLocations;

		@SuppressWarnings("unused")
		public ToggleShowMap() {
		}

		public ToggleShowMap(GOTBrotherhood fs) {
			super(fs);
			showMapLocations = fs.getShowMapLocations();
		}

		@Override
		public void fromBytes(ByteBuf data) {
			super.fromBytes(data);
			showMapLocations = data.readBoolean();
		}

		@Override
		public void toBytes(ByteBuf data) {
			super.toBytes(data);
			data.writeBoolean(showMapLocations);
		}

		@Override
		public void updateClient(GOTBrotherhoodClient brotherhood) {
			brotherhood.setShowMapLocations(showMapLocations);
		}

		public static class Handler extends GOTPacketBrotherhoodPartialUpdate.Handler<ToggleShowMap> {
		}
	}

	public static class UpdatePlayerTitle extends OnePlayerUpdate {
		private GOTTitle.PlayerTitle playerTitle;

		@SuppressWarnings("unused")
		public UpdatePlayerTitle() {
		}

		public UpdatePlayerTitle(GOTBrotherhood fs, UUID player, GOTTitle.PlayerTitle title) {
			super(fs, player);
			playerTitle = title;
		}

		@Override
		public void fromBytes(ByteBuf data) {
			super.fromBytes(data);
			playerTitle = GOTTitle.PlayerTitle.readNullableTitle(data);
		}

		@Override
		public void toBytes(ByteBuf data) {
			super.toBytes(data);
			GOTTitle.PlayerTitle.writeNullableTitle(data, playerTitle);
		}

		@Override
		public void updateClient(GOTBrotherhoodClient brotherhood) {
			brotherhood.updatePlayerTitle(playerProfile.getId(), playerTitle);
		}

		public static class Handler extends GOTPacketBrotherhoodPartialUpdate.Handler<UpdatePlayerTitle> {
		}
	}
}