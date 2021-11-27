package got.common.entity.other;

import got.common.faction.GOTFaction;
import net.minecraft.entity.Entity;

public class GOTNPCSelectForInfluence extends GOTNPCSelectByFaction {
	public GOTNPCSelectForInfluence(GOTFaction f) {
		super(f);
	}

	@Override
	public boolean isEntityApplicable(Entity entity) {
		boolean flag = super.isEntityApplicable(entity);
		if (flag && entity instanceof GOTEntityNPC && !((GOTEntityNPC) entity).generatesControlZone()) {
			return false;
		}
		return flag;
	}
}
