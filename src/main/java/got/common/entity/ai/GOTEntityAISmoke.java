package got.common.entity.ai;

import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTEntitySmokeRing;
import got.common.item.other.GOTItemPipe;
import net.minecraft.item.ItemStack;

public class GOTEntityAISmoke extends GOTEntityAIConsumeBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityAISmoke(GOTEntityNPC entity, int chance) {
		super(entity, null, chance);
	}

	@Override
	public void consume() {
		GOTEntitySmokeRing smoke = new GOTEntitySmokeRing(theEntity.worldObj, theEntity);
		int color = 0;
		ItemStack itemstack = theEntity.getHeldItem();
		if (itemstack != null && itemstack.getItem() instanceof GOTItemPipe) {
			color = GOTItemPipe.getSmokeColor(itemstack);
		}
		smoke.setSmokeColour(color);
		theEntity.worldObj.spawnEntityInWorld(smoke);
		theEntity.playSound("got:item.puff", 1.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f);
		theEntity.heal(2.0f);
	}

	@Override
	public ItemStack createConsumable() {
		return new ItemStack(GOTItems.pipe);
	}

	@Override
	public void updateConsumeTick(int tick) {
	}
}