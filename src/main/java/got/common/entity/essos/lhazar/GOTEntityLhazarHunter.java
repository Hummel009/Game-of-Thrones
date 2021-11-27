package got.common.entity.essos.lhazar;

import got.common.database.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityLhazarHunter extends GOTEntityLhazarTrader {
	public GOTEntityLhazarHunter(World world) {
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
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.lhazarSpear));
		npcItemsInv.setIdleItem(new ItemStack(GOTRegistry.lionFur));
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.gemsbokBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.gemsbokLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.gemsbokChestplate));
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
