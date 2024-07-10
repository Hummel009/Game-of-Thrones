package got.common.entity.westeros.westerlands;

import got.common.database.GOTItems;
import got.common.database.GOTTradeEntries;
import got.common.database.GOTUnitTradeEntries;
import got.common.entity.other.iface.GOTFarmer;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityWesterlandsFarmer extends GOTEntityWesterlandsMan implements GOTFarmer {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityWesterlandsFarmer(World world) {
		super(world);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public GOTTradeEntries getSellsPool() {
		return GOTTradeEntries.FARMER_SELLS;
	}

	@Override
	public GOTTradeEntries getBuysPool() {
		return GOTTradeEntries.FARMER_BUYS;
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.WESTERLANDS_FARMER;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_hoe));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(4, new ItemStack(GOTItems.leatherHat));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
