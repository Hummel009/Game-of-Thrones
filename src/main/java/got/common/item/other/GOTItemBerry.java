package got.common.item.other;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class GOTItemBerry extends GOTItemFood {
	private boolean isPoisonous;

	public GOTItemBerry() {
		super(2, 0.2f, false);
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		ItemStack ret = super.onEaten(itemstack, world, entityplayer);
		if (isPoisonous && !world.isRemote) {
			int duration = 3 + world.rand.nextInt(4);
			PotionEffect poison = new PotionEffect(Potion.poison.id, duration * 20);
			entityplayer.addPotionEffect(poison);
		}
		return ret;
	}

	public GOTItemBerry setPoisonous() {
		isPoisonous = true;
		return this;
	}
}
