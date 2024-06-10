package got.common.entity.animal;

import got.common.database.GOTItems;
import got.common.util.GOTReflection;
import net.minecraft.block.BlockColored;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTEntityCamel extends GOTEntityHorse {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityCamel(World world) {
		super(world);
		setSize(1.6f, 1.8f);
	}

	@Override
	public double clampChildHealth(double health) {
		return MathHelper.clamp_double(health, 12.0, 36.0);
	}

	@Override
	public double clampChildJump(double jump) {
		return MathHelper.clamp_double(jump, 0.1, 0.6);
	}

	@Override
	public double clampChildSpeed(double speed) {
		return MathHelper.clamp_double(speed, 0.1, 0.35);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		int hides = rand.nextInt(3) + rand.nextInt(1 + i);
		for (int l = 0; l < hides; ++l) {
			dropItem(Items.leather, 1);
		}
		int meat = rand.nextInt(3) + rand.nextInt(1 + i);
		for (int l = 0; l < meat; ++l) {
			if (isBurning()) {
				dropItem(GOTItems.camelCooked, 1);
				continue;
			}
			dropItem(GOTItems.camelRaw, 1);
		}
	}

	@Override
	public boolean func_110259_cr() {
		return true;
	}

	public int getCamelCarpetColor() {
		ItemStack itemstack = getMountArmor();
		if (itemstack != null && itemstack.getItem() == Item.getItemFromBlock(Blocks.carpet)) {
			int meta = itemstack.getItemDamage();
			int dyeMeta = BlockColored.func_150031_c(meta);
			int[] colors = ItemDye.field_150922_c;
			dyeMeta = MathHelper.clamp_int(dyeMeta, 0, colors.length);
			return colors[dyeMeta];
		}
		return -1;
	}

	@Override
	public int getHorseType() {
		return 1;
	}

	@Override
	public boolean isBreedingItem(ItemStack itemstack) {
		return itemstack != null && itemstack.getItem() == Items.wheat;
	}

	public boolean isCamelWearingCarpet() {
		ItemStack itemstack = getMountArmor();
		return itemstack != null && itemstack.getItem() == Item.getItemFromBlock(Blocks.carpet);
	}

	@Override
	public boolean isMountArmorValid(ItemStack itemstack) {
		return itemstack != null && itemstack.getItem() == Item.getItemFromBlock(Blocks.carpet) || super.isMountArmorValid(itemstack);
	}

	@Override
	public void onGOTHorseSpawn() {
		double jumpStrength = getEntityAttribute(GOTReflection.getHorseJumpStrength()).getAttributeValue();
		getEntityAttribute(GOTReflection.getHorseJumpStrength()).setBaseValue(jumpStrength * 0.5);
	}

	public void setNomadChestAndCarpet() {
		setChestedForWorldGen();
		ItemStack carpet = new ItemStack(Blocks.carpet, 1, rand.nextInt(16));
		setMountArmor(carpet);
	}
}