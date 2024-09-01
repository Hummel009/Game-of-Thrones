package got.common.brotherhood;

import com.google.common.collect.ImmutableList;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.common.GOTPlayerData;
import got.common.database.GOTTitle;
import got.common.network.GOTPacketBrotherhood;
import got.common.network.GOTPacketBrotherhoodPartialUpdate;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public interface BrotherhoodUpdateType {
	IMessage createUpdatePacket(GOTPlayerData var1, GOTBrotherhood var2);

	List<UUID> getPlayersToCheckSharedWaypointsFrom(GOTBrotherhood var1);

	class AddMember implements BrotherhoodUpdateType {
		protected final UUID memberID;

		public AddMember(UUID member) {
			memberID = member;
		}

		@Override
		public IMessage createUpdatePacket(GOTPlayerData pd, GOTBrotherhood fs) {
			return new GOTPacketBrotherhoodPartialUpdate.AddMember(fs, memberID);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(GOTBrotherhood fs) {
			return ImmutableList.of(memberID);
		}
	}

	class ChangeIcon implements BrotherhoodUpdateType {
		@Override
		public IMessage createUpdatePacket(GOTPlayerData pd, GOTBrotherhood fs) {
			return new GOTPacketBrotherhoodPartialUpdate.ChangeIcon(fs);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(GOTBrotherhood fs) {
			return Collections.emptyList();
		}
	}

	class Full implements BrotherhoodUpdateType {
		@Override
		public IMessage createUpdatePacket(GOTPlayerData pd, GOTBrotherhood fs) {
			return new GOTPacketBrotherhood(pd, fs, false);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(GOTBrotherhood fs) {
			return fs.getAllPlayerUUIDs();
		}
	}

	class RemoveAdmin implements BrotherhoodUpdateType {
		protected final UUID adminID;

		public RemoveAdmin(UUID admin) {
			adminID = admin;
		}

		@Override
		public IMessage createUpdatePacket(GOTPlayerData pd, GOTBrotherhood fs) {
			return new GOTPacketBrotherhoodPartialUpdate.RemoveAdmin(fs, adminID, fs.isAdmin(pd.getPlayerUUID()));
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(GOTBrotherhood fs) {
			return Collections.emptyList();
		}
	}

	class RemoveMember implements BrotherhoodUpdateType {
		protected final UUID memberID;

		public RemoveMember(UUID member) {
			memberID = member;
		}

		@Override
		public IMessage createUpdatePacket(GOTPlayerData pd, GOTBrotherhood fs) {
			return new GOTPacketBrotherhoodPartialUpdate.RemoveMember(fs, memberID);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(GOTBrotherhood fs) {
			return ImmutableList.of(memberID);
		}
	}

	class Rename implements BrotherhoodUpdateType {
		@Override
		public IMessage createUpdatePacket(GOTPlayerData pd, GOTBrotherhood fs) {
			return new GOTPacketBrotherhoodPartialUpdate.Rename(fs);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(GOTBrotherhood fs) {
			return Collections.emptyList();
		}
	}

	class SetAdmin implements BrotherhoodUpdateType {
		protected final UUID adminID;

		public SetAdmin(UUID admin) {
			adminID = admin;
		}

		@Override
		public IMessage createUpdatePacket(GOTPlayerData pd, GOTBrotherhood fs) {
			return new GOTPacketBrotherhoodPartialUpdate.SetAdmin(fs, adminID, fs.isAdmin(pd.getPlayerUUID()));
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(GOTBrotherhood fs) {
			return Collections.emptyList();
		}
	}

	class SetOwner implements BrotherhoodUpdateType {
		protected final UUID ownerID;

		public SetOwner(UUID owner) {
			ownerID = owner;
		}

		@Override
		public IMessage createUpdatePacket(GOTPlayerData pd, GOTBrotherhood fs) {
			return new GOTPacketBrotherhoodPartialUpdate.SetOwner(fs, ownerID, fs.isOwner(pd.getPlayerUUID()));
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(GOTBrotherhood fs) {
			return ImmutableList.of(ownerID);
		}
	}

	class ToggleHiredFriendlyFire implements BrotherhoodUpdateType {
		@Override
		public IMessage createUpdatePacket(GOTPlayerData pd, GOTBrotherhood fs) {
			return new GOTPacketBrotherhoodPartialUpdate.ToggleHiredFriendlyFire(fs);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(GOTBrotherhood fs) {
			return Collections.emptyList();
		}
	}

	class TogglePvp implements BrotherhoodUpdateType {
		@Override
		public IMessage createUpdatePacket(GOTPlayerData pd, GOTBrotherhood fs) {
			return new GOTPacketBrotherhoodPartialUpdate.TogglePvp(fs);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(GOTBrotherhood fs) {
			return Collections.emptyList();
		}
	}

	class ToggleShowMapLocations implements BrotherhoodUpdateType {
		@Override
		public IMessage createUpdatePacket(GOTPlayerData pd, GOTBrotherhood fs) {
			return new GOTPacketBrotherhoodPartialUpdate.ToggleShowMap(fs);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(GOTBrotherhood fs) {
			return Collections.emptyList();
		}
	}

	class UpdatePlayerTitle implements BrotherhoodUpdateType {
		protected final UUID playerID;
		protected final GOTTitle.PlayerTitle playerTitle;

		public UpdatePlayerTitle(UUID player, GOTTitle.PlayerTitle title) {
			playerID = player;
			playerTitle = title;
		}

		@Override
		public IMessage createUpdatePacket(GOTPlayerData pd, GOTBrotherhood fs) {
			return new GOTPacketBrotherhoodPartialUpdate.UpdatePlayerTitle(fs, playerID, playerTitle);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(GOTBrotherhood fs) {
			return Collections.emptyList();
		}
	}
}