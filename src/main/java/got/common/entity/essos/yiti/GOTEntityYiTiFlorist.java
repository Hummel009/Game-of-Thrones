package got.common.entity.essos.yiti;

import got.common.database.*;
import got.common.item.other.GOTItemRobes;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityYiTiFlorist extends GOTEntityYiTiMarketTrader {
	public GOTEntityYiTiFlorist(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTTradeEntries getBuyPool() {
		return GOTTradeEntries.C_FLORIST_BUY;
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.C_FARMER_SELL;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_axe));
		npcItemsInv.setIdleItem(new ItemStack(GOTRegistry.yitiFlower));
		int robeColor = 8538153;
		ItemStack body = new ItemStack(GOTRegistry.kaftanChestplate);
		ItemStack legs = new ItemStack(GOTRegistry.kaftanLeggings);
		GOTItemRobes.setRobesColor(body, robeColor);
		GOTItemRobes.setRobesColor(legs, robeColor);
		setCurrentItemOrArmor(3, body);
		setCurrentItemOrArmor(2, legs);
		return data;
	}
}