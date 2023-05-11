package got.common.entity.animal;

import got.common.database.GOTRegistry;
import got.common.entity.other.GOTEntityRegistry;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GOTEntityZebra extends GOTEntityHorse {
	public GOTEntityZebra(World world) {
		super(world);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		int j = rand.nextInt(2) + rand.nextInt(1 + i);
		for (int k = 0; k < j; ++k) {
			dropItem(Items.leather, 1);
		}
		j = rand.nextInt(2) + 1 + rand.nextInt(1 + i);
		for (int l = 0; l < j; ++l) {
			if (isBurning()) {
				dropItem(GOTRegistry.zebraCooked, 1);
				continue;
			}
			dropItem(GOTRegistry.zebraRaw, 1);
		}
	}

	@Override
	public boolean func_110259_cr() {
		return false;
	}

	@Override
	public String getAngrySoundName() {
		return "got:zebra.hurt";
	}

	@Override
	public String getCommandSenderName() {
		if (hasCustomNameTag()) {
			return getCustomNameTag();
		}
		String s = EntityList.getEntityString(this);
		return StatCollector.translateToLocal("entity." + s + ".name");
	}

	@Override
	public String getDeathSound() {
		return "got:zebra.death";
	}

	@Override
	public int getHorseType() {
		return 0;
	}

	@Override
	public String getHurtSound() {
		return "got:zebra.hurt";
	}

	@Override
	public String getLivingSound() {
		return "got:zebra.say";
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(GOTRegistry.spawnEgg, 1, GOTEntityRegistry.getEntityID(this));
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		if (itemstack != null && itemstack.getItem() == Items.bucket && !entityplayer.capabilities.isCreativeMode) {
			--itemstack.stackSize;
			if (itemstack.stackSize <= 0) {
				entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.milk_bucket));
			} else if (!entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.milk_bucket))) {
				entityplayer.dropPlayerItemWithRandomChoice(new ItemStack(Items.milk_bucket, 1, 0), false);
			}
			return true;
		}
		return super.interact(entityplayer);
	}
}
