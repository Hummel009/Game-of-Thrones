package got.common.entity.essos.braavos;

import got.common.database.GOTInvasions;
import got.common.database.GOTItems;
import got.common.database.GOTUnitTradeEntries;
import got.common.entity.other.iface.GOTUnitTradeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityBraavosCaptain extends GOTEntityBraavosMan implements GOTUnitTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityBraavosCaptain(World world) {
		super(world);
	}

	@Override
	public float getAlignmentBonus() {
		return 5.0f;
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.BRAAVOS;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return GOTInvasions.BRAAVOS;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_sword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.braavosBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.braavosLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.braavosChestplate));

		return entityData;
	}
}