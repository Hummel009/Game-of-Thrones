package got.common.item.weapon;

import got.common.database.GOTCreativeTabs;
import got.common.dispense.GOTDispenseArrowPoisoned;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.Item;

public class GOTItemArrowPoisoned extends Item {
	public GOTItemArrowPoisoned() {
		setCreativeTab(GOTCreativeTabs.TAB_COMBAT);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new GOTDispenseArrowPoisoned());
	}
}