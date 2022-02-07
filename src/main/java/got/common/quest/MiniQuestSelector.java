package got.common.quest;

import java.util.UUID;

import com.google.common.base.Supplier;

import got.common.faction.GOTFaction;

public interface MiniQuestSelector {
	boolean include(GOTMiniQuest var1);

	public static class BountyActiveAnyFaction extends OptionalActive {
		private BountyActiveAnyFaction() {
			setActiveOnly();
		}

		@Override
		public boolean include(GOTMiniQuest quest) {
			if (super.include(quest) && quest instanceof GOTMiniQuestBounty) {
				GOTMiniQuestBounty bQuest = (GOTMiniQuestBounty) quest;
				return !bQuest.isKilled();
			}
			return false;
		}
	}

	public static class BountyActiveFaction extends BountyActiveAnyFaction {
		private Supplier<GOTFaction> factionGet;

		public BountyActiveFaction(Supplier<GOTFaction> sup) {
			factionGet = sup;
		}

		@Override
		public boolean include(GOTMiniQuest quest) {
			return super.include(quest) && quest.getEntityFaction() == factionGet.get();
		}
	}

	public static class EntityId extends OptionalActive {
		private UUID entityID;

		public EntityId(UUID id) {
			entityID = id;
		}

		@Override
		public boolean include(GOTMiniQuest quest) {
			return super.include(quest) && quest.getEntityUUID().equals(entityID);
		}
	}

	public static class Faction extends OptionalActive {
		private Supplier<GOTFaction> factionGet;

		public Faction(Supplier<GOTFaction> sup) {
			factionGet = sup;
		}

		@Override
		public boolean include(GOTMiniQuest quest) {
			return super.include(quest) && quest.getEntityFaction() == factionGet.get();
		}
	}

	public static class OptionalActive implements MiniQuestSelector {
		private boolean activeOnly = false;

		@Override
		public boolean include(GOTMiniQuest quest) {
			if (activeOnly) {
				return quest.isActive();
			}
			return true;
		}

		public OptionalActive setActiveOnly() {
			activeOnly = true;
			return this;
		}
	}

}
