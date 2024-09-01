package got.common.entity.sothoryos.sothoryos;

import got.common.database.GOTInvasions;
import got.common.database.GOTItems;
import got.common.database.GOTUnitTradeEntries;
import got.common.entity.other.iface.GOTUnitTradeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntitySothoryosChieftain extends GOTEntitySothoryosMan implements GOTUnitTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntitySothoryosChieftain(World world) {
		super(world);
	}

	@Override
	public float getReputationBonus() {
		return 5.0f;
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.SOTHORYOS;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return GOTInvasions.SOTHORYOS;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.bronzeSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.sothoryosBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.sothoryosLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.sothoryosChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.sothoryosHelmetChieftain));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}