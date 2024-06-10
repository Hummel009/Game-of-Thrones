package got.common.item.other;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.faction.GOTFaction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class GOTItemMugShadeEvening extends GOTItemMug {
	public GOTItemMugShadeEvening() {
		super(0.0f);
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!shouldApplyPotionEffects(entityplayer)) {
			ItemStack result = super.onEaten(itemstack, world, entityplayer);
			if (!world.isRemote) {
				entityplayer.addPotionEffect(new PotionEffect(Potion.poison.id, 100));
			}
			GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.drinkShadeEvening);
			return result;
		}
		return super.onEaten(itemstack, world, entityplayer);
	}

	@Override
	protected boolean shouldApplyPotionEffects(EntityPlayer entityplayer) {
		return GOTLevelData.getData(entityplayer).getAlignment(GOTFaction.QARTH) > 0.0f;
	}
}