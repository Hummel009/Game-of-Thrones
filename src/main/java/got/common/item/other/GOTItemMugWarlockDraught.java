package got.common.item.other;

import got.common.GOTLevelData;
import got.common.faction.GOTFaction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.*;
import net.minecraft.world.World;

public class GOTItemMugWarlockDraught extends GOTItemMug {
	public GOTItemMugWarlockDraught() {
		super(0.0f);
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!shouldApplyPotionEffects(itemstack, entityplayer)) {
			ItemStack result = super.onEaten(itemstack, world, entityplayer);
			if (!world.isRemote) {
				entityplayer.addPotionEffect(new PotionEffect(Potion.poison.id, 100));
			}
			return result;
		}
		return super.onEaten(itemstack, world, entityplayer);
	}

	@Override
	public boolean shouldApplyPotionEffects(ItemStack itemstack, EntityPlayer entityplayer) {
		return GOTLevelData.getData(entityplayer).getAlignment(GOTFaction.QARTH) > 0.0f;
	}
}
