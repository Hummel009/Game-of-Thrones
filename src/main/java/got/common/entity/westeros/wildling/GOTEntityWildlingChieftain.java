package got.common.entity.westeros.wildling;

import got.common.database.GOTInvasions;
import got.common.database.GOTItems;
import got.common.database.GOTUnitTradeEntries;
import got.common.entity.other.iface.GOTUnitTradeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityWildlingChieftain extends GOTEntityWildling implements GOTUnitTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityWildlingChieftain(World world) {
		super(world);
	}

	@Override
	public float getReputationBonus() {
		return 5.0f;
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.WILDLING;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return GOTInvasions.WILDLING;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.skullStaff));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.furBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.furLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.furChestplate));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}