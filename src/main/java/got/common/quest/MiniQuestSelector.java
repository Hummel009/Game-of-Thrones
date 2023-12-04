package got.common.quest;

import got.common.faction.GOTFaction;

import java.util.UUID;
import java.util.function.Supplier;

public interface MiniQuestSelector {
	boolean include(GOTMiniQuest var1);

	class BountyActiveAnyFaction extends OptionalActive {
		public BountyActiveAnyFaction() {
			setActiveOnly();
		}

		@Override
		public boolean include(GOTMiniQuest quest) {
			if (super.include(quest) && quest instanceof GOTMiniQuestBounty) {
				GOTMiniQuestBounty bQuest = (GOTMiniQuestBounty) quest;
				return !bQuest.killed;
			}
			return false;
		}
	}

	class BountyActiveFaction extends BountyActiveAnyFaction {
		public Supplier<GOTFaction> factionGet;

		public BountyActiveFaction(Supplier<GOTFaction> sup) {
			factionGet = sup;
		}

		@Override
		public boolean include(GOTMiniQuest quest) {
			return super.include(quest) && quest.entityFaction == factionGet.get();
		}
	}

	class EntityId extends OptionalActive {
		public UUID entityID;

		public EntityId(UUID id) {
			entityID = id;
		}

		@Override
		public boolean include(GOTMiniQuest quest) {
			return super.include(quest) && quest.entityUUID.equals(entityID);
		}
	}

	class Faction extends OptionalActive {
		public Supplier<GOTFaction> factionGet;

		public Faction(Supplier<GOTFaction> sup) {
			factionGet = sup;
		}

		@Override
		public boolean include(GOTMiniQuest quest) {
			return super.include(quest) && quest.entityFaction == factionGet.get();
		}
	}

	class OptionalActive implements MiniQuestSelector {
		public boolean activeOnly;

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
