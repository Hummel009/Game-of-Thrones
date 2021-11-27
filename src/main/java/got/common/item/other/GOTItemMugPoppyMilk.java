package got.common.item.other;

import got.common.util.GOTReflection;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;

public class GOTItemMugPoppyMilk extends GOTItemMug {
	public GOTItemMugPoppyMilk(float f) {
		super(f);
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		ItemStack result = super.onEaten(itemstack, world, entityplayer);
		if (!world.isRemote) {
			for (Potion potion : Potion.potionTypes) {
				if (potion == null || !GOTReflection.isBadEffect(potion)) {
					continue;
				}
				entityplayer.removePotionEffect(potion.id);
			}
		}
		return result;
	}
}
