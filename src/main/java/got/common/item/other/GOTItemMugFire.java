package got.common.item.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.util.GOTReflection;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class GOTItemMugFire extends GOTItemMug {
	public GOTItemMugFire(float f) {
		super(f);
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		super.addInformation(itemstack, entityplayer, list, flag);
		list.add(EnumChatFormatting.DARK_GRAY + StatCollector.translateToLocal("item.got.drink.fire"));
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		ItemStack result = super.onEaten(itemstack, world, entityplayer);
		if (!world.isRemote && world.rand.nextInt(2) == 0) {
			entityplayer.setFire(5000);
		}
		if (!world.isRemote) {
			for (Potion potion : Potion.potionTypes) {
				if (potion == null || !GOTReflection.isBadEffect(potion)) {
					continue;
				}
				entityplayer.removePotionEffect(potion.id);
			}
		}
		GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.drinkFire);
		return result;
	}
}
