package got.common.entity.ai;

import got.common.database.GOTFoods;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class GOTEntityAIEat extends GOTEntityAIConsumeBase {
	@SuppressWarnings("WeakerAccess")
	public GOTEntityAIEat(GOTEntityNPC entity, GOTFoods foods, int chance) {
		super(entity, foods, chance);
	}

	@Override
	public void consume() {
		ItemStack itemstack = theEntity.getHeldItem();
		Item item = itemstack.getItem();
		if (item instanceof ItemFood) {
			ItemFood food = (ItemFood) item;
			theEntity.heal(food.func_150905_g(itemstack));
		}
	}

	@Override
	public ItemStack createConsumable() {
		return foodPool.getRandomFood(rand);
	}

	@Override
	public void updateConsumeTick(int tick) {
		if (tick % 4 == 0) {
			theEntity.spawnFoodParticles();
			theEntity.playSound("random.eat", 0.5f + 0.5f * rand.nextInt(2), (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f);
		}
	}
}