package got.common.entity.westeros.dorne;

import got.common.database.GOTCapes;
import got.common.database.GOTInvasions;
import got.common.database.GOTItems;
import got.common.database.GOTUnitTradeEntries;
import got.common.entity.other.iface.GOTUnitTradeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityDorneCaptain extends GOTEntityDorneMan implements GOTUnitTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityDorneCaptain(World world) {
		super(world);
	}

	@Override
	public GOTCapes getCape() {
		return GOTCapes.DORNE;
	}

	@Override
	public float getReputationBonus() {
		return 5.0f;
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.DORNE;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return GOTInvasions.DORNE;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_sword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.dorneBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.dorneLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.dorneChestplate));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
