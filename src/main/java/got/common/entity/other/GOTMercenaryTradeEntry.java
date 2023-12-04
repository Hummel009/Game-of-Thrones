package got.common.entity.other;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTMercenaryTradeEntry extends GOTUnitTradeEntry {
	public GOTMercenary theMerc;

	public GOTMercenaryTradeEntry(GOTMercenary merc) {
		super((Class<? extends Entity>) merc.getClass(), merc.getMercBaseCost(), merc.getMercAlignmentRequired());
		theMerc = merc;
	}

	public static GOTMercenaryTradeEntry createFor(GOTMercenary merc) {
		return new GOTMercenaryTradeEntry(merc);
	}

	@Override
	public GOTEntityNPC getOrCreateHiredNPC(World world) {
		return (GOTEntityNPC) theMerc;
	}

	@Override
	public boolean hasRequiredCostAndAlignment(EntityPlayer entityplayer, GOTHireableBase trader) {
		return !((GOTEntityNPC) theMerc).hiredNPCInfo.isActive && super.hasRequiredCostAndAlignment(entityplayer, trader);
	}
}
