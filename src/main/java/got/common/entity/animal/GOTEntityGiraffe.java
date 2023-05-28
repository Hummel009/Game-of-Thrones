package got.common.entity.animal;

import got.common.database.GOTItems;
import got.common.util.GOTReflection;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTEntityGiraffe extends GOTEntityHorse {
	public GOTEntityGiraffe(World world) {
		super(world);
		setSize(1.7f, 4.0f);
	}

	@Override
	public double clampChildHealth(double health) {
		return MathHelper.clamp_double(health, 12.0, 34.0);
	}

	@Override
	public double clampChildJump(double jump) {
		return MathHelper.clamp_double(jump, 0.2, 1.0);
	}

	@Override
	public double clampChildSpeed(double speed) {
		return MathHelper.clamp_double(speed, 0.08, 0.35);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		if (flag) {
			int rugChance = 30 - i * 5;
			if (rand.nextInt(Math.max(rugChance, 1)) == 0) {
				entityDropItem(new ItemStack(GOTItems.giraffeRug), 0.0f);
			}
		}
	}

	@Override
	public Item getDropItem() {
		return Items.leather;
	}

	@Override
	public int getHorseType() {
		return 0;
	}

	@Override
	public boolean isBreedingItem(ItemStack itemstack) {
		return itemstack != null && Block.getBlockFromItem(itemstack.getItem()) instanceof BlockLeavesBase;
	}

	@Override
	public void onGOTHorseSpawn() {
		double jumpStrength = getEntityAttribute(GOTReflection.getHorseJumpStrength()).getAttributeValue();
		getEntityAttribute(GOTReflection.getHorseJumpStrength()).setBaseValue(jumpStrength * 0.8);
	}
}
