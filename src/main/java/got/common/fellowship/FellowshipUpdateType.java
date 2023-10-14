package got.common.fellowship;

import com.google.common.collect.ImmutableList;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.common.GOTPlayerData;
import got.common.database.GOTTitle;
import got.common.network.GOTPacketFellowship;
import got.common.network.GOTPacketFellowshipPartialUpdate;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public interface FellowshipUpdateType {
	IMessage createUpdatePacket(GOTPlayerData var1, GOTFellowship var2);

	List<UUID> getPlayersToCheckSharedWaypointsFrom(GOTFellowship var1);

	class AddMember implements FellowshipUpdateType {
		public UUID memberID;

		public AddMember(UUID member) {
			memberID = member;
		}

		@Override
		public IMessage createUpdatePacket(GOTPlayerData pd, GOTFellowship fs) {
			return new GOTPacketFellowshipPartialUpdate.AddMember(fs, memberID);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(GOTFellowship fs) {
			return ImmutableList.of(memberID);
		}
	}

	class ChangeIcon implements FellowshipUpdateType {
		@Override
		public IMessage createUpdatePacket(GOTPlayerData pd, GOTFellowship fs) {
			return new GOTPacketFellowshipPartialUpdate.ChangeIcon(fs);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(GOTFellowship fs) {
			return Collections.emptyList();
		}
	}

	class Full implements FellowshipUpdateType {
		@Override
		public IMessage createUpdatePacket(GOTPlayerData pd, GOTFellowship fs) {
			return new GOTPacketFellowship(pd, fs, false);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(GOTFellowship fs) {
			return fs.getAllPlayerUUIDs();
		}
	}

	class RemoveAdmin implements FellowshipUpdateType {
		public UUID adminID;

		public RemoveAdmin(UUID admin) {
			adminID = admin;
		}

		@Override
		public IMessage createUpdatePacket(GOTPlayerData pd, GOTFellowship fs) {
			return new GOTPacketFellowshipPartialUpdate.RemoveAdmin(fs, adminID, fs.isAdmin(pd.getPlayerUUID()));
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(GOTFellowship fs) {
			return Collections.emptyList();
		}
	}

	class RemoveMember implements FellowshipUpdateType {
		public UUID memberID;

		public RemoveMember(UUID member) {
			memberID = member;
		}

		@Override
		public IMessage createUpdatePacket(GOTPlayerData pd, GOTFellowship fs) {
			return new GOTPacketFellowshipPartialUpdate.RemoveMember(fs, memberID);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(GOTFellowship fs) {
			return ImmutableList.of(memberID);
		}
	}

	class Rename implements FellowshipUpdateType {
		@Override
		public IMessage createUpdatePacket(GOTPlayerData pd, GOTFellowship fs) {
			return new GOTPacketFellowshipPartialUpdate.Rename(fs);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(GOTFellowship fs) {
			return Collections.emptyList();
		}
	}

	class SetAdmin implements FellowshipUpdateType {
		public UUID adminID;

		public SetAdmin(UUID admin) {
			adminID = admin;
		}

		@Override
		public IMessage createUpdatePacket(GOTPlayerData pd, GOTFellowship fs) {
			return new GOTPacketFellowshipPartialUpdate.SetAdmin(fs, adminID, fs.isAdmin(pd.getPlayerUUID()));
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(GOTFellowship fs) {
			return Collections.emptyList();
		}
	}

	class SetOwner implements FellowshipUpdateType {
		public UUID ownerID;

		public SetOwner(UUID owner) {
			ownerID = owner;
		}

		@Override
		public IMessage createUpdatePacket(GOTPlayerData pd, GOTFellowship fs) {
			return new GOTPacketFellowshipPartialUpdate.SetOwner(fs, ownerID, fs.isOwner(pd.getPlayerUUID()));
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(GOTFellowship fs) {
			return ImmutableList.of(ownerID);
		}
	}

	class ToggleHiredFriendlyFire implements FellowshipUpdateType {
		@Override
		public IMessage createUpdatePacket(GOTPlayerData pd, GOTFellowship fs) {
			return new GOTPacketFellowshipPartialUpdate.ToggleHiredFriendlyFire(fs);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(GOTFellowship fs) {
			return Collections.emptyList();
		}
	}

	class TogglePvp implements FellowshipUpdateType {
		@Override
		public IMessage createUpdatePacket(GOTPlayerData pd, GOTFellowship fs) {
			return new GOTPacketFellowshipPartialUpdate.TogglePvp(fs);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(GOTFellowship fs) {
			return Collections.emptyList();
		}
	}

	class ToggleShowMapLocations implements FellowshipUpdateType {
		@Override
		public IMessage createUpdatePacket(GOTPlayerData pd, GOTFellowship fs) {
			return new GOTPacketFellowshipPartialUpdate.ToggleShowMap(fs);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(GOTFellowship fs) {
			return Collections.emptyList();
		}
	}

	class UpdatePlayerTitle implements FellowshipUpdateType {
		public UUID playerID;
		public GOTTitle.PlayerTitle playerTitle;

		public UpdatePlayerTitle(UUID player, GOTTitle.PlayerTitle title) {
			playerID = player;
			playerTitle = title;
		}

		@Override
		public IMessage createUpdatePacket(GOTPlayerData pd, GOTFellowship fs) {
			return new GOTPacketFellowshipPartialUpdate.UpdatePlayerTitle(fs, playerID, playerTitle);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(GOTFellowship fs) {
			return Collections.emptyList();
		}
	}

}
