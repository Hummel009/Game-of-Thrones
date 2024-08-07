package got.common.entity.ai;

import got.GOT;
import got.common.database.GOTFoods;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.iface.GOTBartender;
import got.common.entity.other.utils.GOTEntityUtils;
import got.common.item.other.GOTItemMug;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

import java.util.List;

public class GOTEntityAIDrink extends GOTEntityAIConsumeBase {
	public GOTEntityAIDrink(GOTEntityNPC entity, GOTFoods foods, int chance) {
		super(entity, foods, chance);
	}

	@Override
	public void consume() {
		ItemStack itemstack = theEntity.getHeldItem();
		Item item = itemstack.getItem();
		if (item instanceof GOTItemMug) {
			GOTItemMug drink = (GOTItemMug) item;
			drink.applyToNPC(theEntity, itemstack);
			if (drink.getAlcoholicity() > 0.0f && GOTEntityUtils.canSmokeDrink(theEntity) && !theEntity.getFamilyInfo().isDrunk() && rand.nextInt(3) == 0) {
				double range = 12.0;
				IEntitySelector selectNonEnemyBartenders = new EntitySelectorImpl(theEntity);
				List<GOTBartender> nearbyBartenders = theEntity.worldObj.selectEntitiesWithinAABB(GOTBartender.class, theEntity.boundingBox.expand(range, range, range), selectNonEnemyBartenders);
				if (!nearbyBartenders.isEmpty()) {
					int drunkTime = MathHelper.getRandomIntegerInRange(rand, 30, 1500);
					theEntity.getFamilyInfo().setDrunkTime(drunkTime * 20);
				}
			}
		}
	}

	@Override
	public ItemStack createConsumable() {
		ItemStack drink = foodPool.getRandomFood(rand);
		Item item = drink.getItem();
		if (item instanceof GOTItemMug && ((GOTItemMug) item).isBrewable()) {
			GOTItemMug.setStrengthMeta(drink, 1 + rand.nextInt(3));
		}
		return drink;
	}

	@Override
	public int getConsumeTime() {
		int time = super.getConsumeTime();
		if (theEntity.getFamilyInfo().isDrunk()) {
			time *= 1 + rand.nextInt(4);
		}
		return time;
	}

	@Override
	public boolean shouldConsume() {
		return theEntity.getFamilyInfo().isDrunk() && rand.nextInt(100) == 0 || super.shouldConsume();
	}

	@Override
	public void updateConsumeTick(int tick) {
		if (tick % 4 == 0) {
			theEntity.playSound("random.drink", 0.5f, rand.nextFloat() * 0.1f + 0.9f);
		}
	}

	private static class EntitySelectorImpl implements IEntitySelector {
		private final GOTEntityNPC theEntity;

		private EntitySelectorImpl(GOTEntityNPC theEntity) {
			this.theEntity = theEntity;
		}

		@Override
		public boolean isEntityApplicable(Entity entity) {
			return entity.isEntityAlive() && !GOT.getNPCFaction(entity).isBadRelation(theEntity.getFaction());
		}
	}
}