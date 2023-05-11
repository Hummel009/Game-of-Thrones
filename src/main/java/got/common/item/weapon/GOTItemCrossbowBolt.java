package got.common.item.weapon;

import got.common.database.GOTCreativeTabs;
import got.common.dispense.GOTDispenseCrossbowBolt;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.Item;

public class GOTItemCrossbowBolt extends Item {
	public boolean isPoisoned;

	public GOTItemCrossbowBolt() {
		setCreativeTab(GOTCreativeTabs.tabCombat);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new GOTDispenseCrossbowBolt(this));
	}

	public GOTItemCrossbowBolt setPoisoned() {
		isPoisoned = true;
		return this;
	}
}
