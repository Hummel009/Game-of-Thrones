package got.common.entity.other;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTMercenaryTradeEntry extends GOTUnitTradeEntry {
	public GOTMercenary theMerc;

	public GOTMercenaryTradeEntry(GOTMercenary merc) {
		super(merc.getClass(), merc.getMercBaseCost(), merc.getMercAlignmentRequired());
		theMerc = merc;
	}

	@Override
	public GOTEntityNPC getOrCreateHiredNPC(World world) {
		return (GOTEntityNPC) (theMerc);
	}

	@Override
	public boolean hasRequiredCostAndAlignment(EntityPlayer entityplayer, GOTHireableBase trader) {
		if (((GOTEntityNPC) theMerc).hiredNPCInfo.isActive) {
			return false;
		}
		return super.hasRequiredCostAndAlignment(entityplayer, trader);
	}

	public static GOTMercenaryTradeEntry createFor(GOTMercenary merc) {
		return new GOTMercenaryTradeEntry(merc);
	}
}
