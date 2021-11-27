package got.client.gui;

import got.common.entity.other.GOTUnitTradeable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTGuiUnitTrade extends GOTGuiHireBase {
	public GOTGuiUnitTrade(EntityPlayer entityplayer, GOTUnitTradeable trader, World world) {
		super(entityplayer, trader, world);
		setTrades(trader.getUnits());
	}
}
