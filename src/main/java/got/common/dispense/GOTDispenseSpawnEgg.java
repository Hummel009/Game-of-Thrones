package got.common.dispense;

import got.common.entity.other.GOTEntityNPC;
import got.common.item.other.GOTItemSpawnEgg;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class GOTDispenseSpawnEgg extends BehaviorDefaultDispenseItem {
	@Override
	public ItemStack dispenseStack(IBlockSource dispenser, ItemStack itemstack) {
		EnumFacing enumfacing = BlockDispenser.func_149937_b(dispenser.getBlockMetadata());
		double d = dispenser.getX() + enumfacing.getFrontOffsetX();
		double d1 = dispenser.getYInt() + 0.2;
		double d2 = dispenser.getZ() + enumfacing.getFrontOffsetZ();
		Entity entity = GOTItemSpawnEgg.spawnCreature(dispenser.getWorld(), itemstack.getItemDamage(), d, d1, d2);
		if (entity instanceof EntityLiving && itemstack.hasDisplayName()) {
			((EntityLiving) entity).setCustomNameTag(itemstack.getDisplayName());
		}
		if (entity instanceof GOTEntityNPC) {
			((GOTEntityNPC) entity).setNPCPersistent(true);
		}
		itemstack.splitStack(1);
		return itemstack;
	}
}