package got.common.entity.westeros.stormlands;

import got.common.database.GOTCapes;
import got.common.database.GOTInvasions;
import got.common.database.GOTItems;
import got.common.database.GOTUnitTradeEntries;
import got.common.entity.other.iface.GOTUnitTradeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityStormlandsCaptain extends GOTEntityStormlandsMan implements GOTUnitTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityStormlandsCaptain(World world) {
		super(world);
	}

	@Override
	public GOTCapes getCape() {
		return GOTCapes.STORMLANDS;
	}

	@Override
	public float getAlignmentBonus() {
		return 5.0f;
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.STORMLANDS;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return GOTInvasions.STORMLANDS;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_sword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.stormlandsBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.stormlandsLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.stormlandsChestplate));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
