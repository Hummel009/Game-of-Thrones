package got.common.item.other;

import got.common.block.other.GOTBlockPlate;
import got.common.database.GOTCreativeTabs;
import got.common.dispense.GOTDispensePlate;
import got.common.entity.other.GOTEntityPlate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemReed;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTItemPlate extends ItemReed {
	private final Block plateBlock;

	public GOTItemPlate(Block block) {
		super(block);
		plateBlock = block;
		((GOTBlockPlate) plateBlock).setPlateItem(this);
		setCreativeTab(GOTCreativeTabs.TAB_FOOD);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new GOTDispensePlate(plateBlock));
	}

	@Override
	public boolean isValidArmor(ItemStack itemstack, int armorType, Entity entity) {
		return armorType == 0;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		GOTEntityPlate plate = new GOTEntityPlate(world, plateBlock, entityplayer);
		world.playSoundAtEntity(entityplayer, "random.bow", 1.0f, 1.0f / (itemRand.nextFloat() * 0.4f + 1.2f) + 0.25f);
		if (!world.isRemote) {
			world.spawnEntityInWorld(plate);
		}
		if (!entityplayer.capabilities.isCreativeMode) {
			--itemstack.stackSize;
		}
		return itemstack;
	}

	public Block getPlateBlock() {
		return plateBlock;
	}
}