package got.client.gui;

import got.common.database.GOTUnitTradeEntries;
import got.common.entity.other.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTGuiMercenaryHire extends GOTGuiHireBase {
	public GOTGuiMercenaryHire(EntityPlayer entityplayer, GOTMercenary mercenary, World world) {
		super(entityplayer, mercenary, world);
		GOTMercenaryTradeEntry e = GOTMercenaryTradeEntry.createFor(mercenary);
		GOTUnitTradeEntries trades = new GOTUnitTradeEntries(0.0f, e);
		setTrades(trades);
	}
}
