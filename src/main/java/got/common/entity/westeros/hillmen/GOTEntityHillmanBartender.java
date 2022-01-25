package got.common.entity.westeros.hillmen;

import got.common.GOTLevelData;
import got.common.database.*;
import got.common.entity.other.GOTTradeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class GOTEntityHillmanBartender extends GOTEntityHillman implements GOTTradeable {
	public GOTEntityHillmanBartender(World world) {
		super(world);
		canBeMarried = false;
		addTargetTasks(false);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return isFriendly(entityplayer);
	}

	@Override
	public void dropHillmanItems(boolean flag, int i) {
		int j = rand.nextInt(3) + rand.nextInt(i + 1);
		block6: for (int k = 0; k < j; ++k) {
			int l = rand.nextInt(7);
			switch (l) {
			case 0:
			case 1:
			case 2: {
				Item food = GOTFoods.WILD.getRandomFood(rand).getItem();
				entityDropItem(new ItemStack(food), 0.0f);
				continue block6;
			}
			case 3: {
				entityDropItem(new ItemStack(Items.gold_nugget, 2 + rand.nextInt(3)), 0.0f);
				continue block6;
			}
			case 4:
			case 5: {
				entityDropItem(new ItemStack(GOTRegistry.mug), 0.0f);
				continue block6;
			}
			case 6: {
				Item drink = GOTFoods.WILD_DRINK.getRandomFood(rand).getItem();
				entityDropItem(new ItemStack(drink, 1, 1 + rand.nextInt(3)), 0.0f);
			}
			}
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public GOTTradeEntries getBuyPool() {
		return GOTTradeEntries.WESTEROS_BARTENDER_BUY;
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.COMMON_BARTENDER_SELL;
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, GOTTradeEntries.TradeType type, ItemStack itemstack) {
		GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.TRADE);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setIdleItem(new ItemStack(GOTRegistry.mug));
		return data;
	}
}
