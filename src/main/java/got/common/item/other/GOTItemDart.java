package got.common.item.other;

import got.common.database.GOTCreativeTabs;
import got.common.dispense.GOTDispenseDart;
import got.common.entity.other.inanimate.GOTEntityDart;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTItemDart extends Item {
	private boolean isPoisoned;

	public GOTItemDart() {
		setCreativeTab(GOTCreativeTabs.TAB_COMBAT);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new GOTDispenseDart(this));
	}

	public static GOTEntityDart createDart(World world, EntityLivingBase entity, EntityLivingBase target, ItemStack itemstack, float charge, float inaccuracy) {
		return new GOTEntityDart(world, entity, target, itemstack, charge, inaccuracy);
	}

	public static GOTEntityDart createDart(World world, EntityLivingBase entity, ItemStack itemstack, float charge) {
		return new GOTEntityDart(world, entity, itemstack, charge);
	}

	public static GOTEntityDart createDart(World world, ItemStack itemstack, double d, double d1, double d2) {
		return new GOTEntityDart(world, itemstack, d, d1, d2);
	}

	public GOTItemDart setPoisoned() {
		isPoisoned = true;
		return this;
	}

	public boolean isPoisoned() {
		return isPoisoned;
	}
}