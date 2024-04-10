package got.common.item.weapon;

import got.common.database.GOTCreativeTabs;
import got.common.dispense.GOTDispenseCrossbowBolt;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.Item;

public class GOTItemCrossbowBolt extends Item {
	private boolean isPoisoned;

	public GOTItemCrossbowBolt() {
		setCreativeTab(GOTCreativeTabs.TAB_COMBAT);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new GOTDispenseCrossbowBolt(this));
	}

	public GOTItemCrossbowBolt setPoisoned() {
		isPoisoned = true;
		return this;
	}

	public boolean isPoisoned() {
		return isPoisoned;
	}
}