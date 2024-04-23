package got.common.entity.other.utils;

import got.GOT;
import got.common.faction.GOTFaction;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;

public class GOTNPCSelectByFaction implements IEntitySelector {
	private final GOTFaction faction;

	public GOTNPCSelectByFaction(GOTFaction f) {
		faction = f;
	}

	@Override
	public boolean isEntityApplicable(Entity entity) {
		return entity.isEntityAlive() && GOT.getNPCFaction(entity) == faction;
	}
}