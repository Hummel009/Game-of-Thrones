package got.client.gui;

import got.common.database.GOTUnitTradeEntries;
import got.common.entity.other.GOTMercenary;
import got.common.entity.other.GOTMercenaryTradeEntry;
import got.common.entity.other.GOTUnitTradeEntry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.ArrayList;

public class GOTGuiMercenaryHire extends GOTGuiHireBase {
	public GOTGuiMercenaryHire(EntityPlayer entityplayer, GOTMercenary mercenary, World world) {
		super(entityplayer, mercenary, world);
		GOTMercenaryTradeEntry e = GOTMercenaryTradeEntry.createFor(mercenary);
		ArrayList<GOTUnitTradeEntry> sus = new ArrayList<>();
		sus.add(e);
		GOTUnitTradeEntries trades = new GOTUnitTradeEntries(0.0f, sus);
		setTrades(trades);
	}
}