package got.common.quest;

import got.common.faction.GOTFaction;

import java.util.UUID;
import java.util.function.Supplier;

public class GOTMiniQuestSelector {
	private boolean activeOnly;

	public boolean include(GOTMiniQuest quest) {
		return !activeOnly || quest.isActive();
	}

	public GOTMiniQuestSelector setActiveOnly() {
		activeOnly = true;
		return this;
	}

	public static class BountyActiveAnyFaction extends GOTMiniQuestSelector {
		protected BountyActiveAnyFaction() {
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
		protected final Supplier<GOTFaction> factionGet;

		public BountyActiveFaction(Supplier<GOTFaction> sup) {
			factionGet = sup;
		}

		@Override
		public boolean include(GOTMiniQuest quest) {
			return super.include(quest) && quest.getEntityFaction() == factionGet.get();
		}
	}

	public static class EntityId extends GOTMiniQuestSelector {
		protected final UUID entityID;

		public EntityId(UUID id) {
			entityID = id;
		}

		@Override
		public boolean include(GOTMiniQuest quest) {
			return super.include(quest) && quest.getEntityUUID().equals(entityID);
		}
	}

	public static class Faction extends GOTMiniQuestSelector {
		protected final Supplier<GOTFaction> factionGet;

		public Faction(Supplier<GOTFaction> sup) {
			factionGet = sup;
		}

		@Override
		public boolean include(GOTMiniQuest quest) {
			return super.include(quest) && quest.getEntityFaction() == factionGet.get();
		}
	}
}