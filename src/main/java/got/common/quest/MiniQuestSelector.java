package got.common.quest;

import got.common.faction.GOTFaction;

import java.util.UUID;
import java.util.function.Supplier;

public interface MiniQuestSelector {
	boolean include(GOTMiniQuest var1);

	class BountyActiveAnyFaction extends OptionalActive {
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

	class BountyActiveFaction extends BountyActiveAnyFaction {
		protected final Supplier<GOTFaction> factionGet;

		public BountyActiveFaction(Supplier<GOTFaction> sup) {
			factionGet = sup;
		}

		@Override
		public boolean include(GOTMiniQuest quest) {
			return super.include(quest) && quest.getEntityFaction() == factionGet.get();
		}
	}

	class EntityId extends OptionalActive {
		protected final UUID entityID;

		public EntityId(UUID id) {
			entityID = id;
		}

		@Override
		public boolean include(GOTMiniQuest quest) {
			return super.include(quest) && quest.getEntityUUID().equals(entityID);
		}
	}

	class Faction extends OptionalActive {
		protected final Supplier<GOTFaction> factionGet;

		public Faction(Supplier<GOTFaction> sup) {
			factionGet = sup;
		}

		@Override
		public boolean include(GOTMiniQuest quest) {
			return super.include(quest) && quest.getEntityFaction() == factionGet.get();
		}
	}

	class OptionalActive implements MiniQuestSelector {
		protected boolean activeOnly;

		@Override
		public boolean include(GOTMiniQuest quest) {
			return !activeOnly || quest.isActive();
		}

		public OptionalActive setActiveOnly() {
			activeOnly = true;
			return this;
		}
	}
}