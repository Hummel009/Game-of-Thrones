package got.common.entity.sothoryos.summer;

import got.common.database.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntitySummerHunter extends GOTEntitySummerTrader {
	public GOTEntitySummerHunter(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTTradeEntries getBuyPool() {
		return GOTTradeEntries.ESSOS_HUNTER_BUY;
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.COMMON_HUNTER_SELL;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.summerSpear));
		npcItemsInv.setIdleItem(new ItemStack(GOTRegistry.fur));
		setCurrentItemOrArmor(1, new ItemStack(Items.leather_boots));
		setCurrentItemOrArmor(2, new ItemStack(Items.leather_leggings));
		setCurrentItemOrArmor(3, new ItemStack(Items.leather_chestplate));
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
