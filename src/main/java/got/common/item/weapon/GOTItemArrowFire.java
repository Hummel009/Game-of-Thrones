package got.common.item.weapon;

import got.common.database.GOTCreativeTabs;
import got.common.dispense.GOTDispenseArrowFire;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.Item;

public class GOTItemArrowFire extends Item {
	public GOTItemArrowFire() {
		setCreativeTab(GOTCreativeTabs.tabCombat);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new GOTDispenseArrowFire());
	}
}
