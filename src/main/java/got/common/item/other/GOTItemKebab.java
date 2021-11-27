package got.common.item.other;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class GOTItemKebab extends GOTItemFood {
	public GOTItemKebab(int healAmount, float saturation, boolean canWolfEat) {
		super(healAmount, saturation, canWolfEat);
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!world.isRemote && world.rand.nextInt(100) == 0) {
			entityplayer.addChatMessage(new ChatComponentText("That was a good kebab. You feel a lot better."));
		}
		return super.onEaten(itemstack, world, entityplayer);
	}
}
