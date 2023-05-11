package got.common.quest;

import com.google.common.base.Supplier;
import got.common.faction.GOTFaction;

import java.util.UUID;

public interface MiniQuestSelector {
	boolean include(GOTMiniQuest var1);

	public static class BountyActiveAnyFaction extends OptionalActive {
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

	public static class BountyActiveFaction extends BountyActiveAnyFaction {
		public Supplier<GOTFaction> factionGet;

		public BountyActiveFaction(Supplier<GOTFaction> sup) {
			factionGet = sup;
		}

		@Override
		public boolean include(GOTMiniQuest quest) {
			return super.include(quest) && quest.entityFaction == factionGet.get();
		}
	}

	public static class EntityId extends OptionalActive {
		public UUID entityID;

		public EntityId(UUID id) {
			entityID = id;
		}

		@Override
		public boolean include(GOTMiniQuest quest) {
			return super.include(quest) && quest.entityUUID.equals(entityID);
		}
	}

	public static class Faction extends OptionalActive {
		public Supplier<GOTFaction> factionGet;

		public Faction(Supplier<GOTFaction> sup) {
			factionGet = sup;
		}

		@Override
		public boolean include(GOTMiniQuest quest) {
			return super.include(quest) && quest.entityFaction == factionGet.get();
		}
	}

	public static class OptionalActive implements MiniQuestSelector {
		public boolean activeOnly;

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
