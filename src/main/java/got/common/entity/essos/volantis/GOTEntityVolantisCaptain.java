package got.common.entity.essos.volantis;

import got.common.database.GOTInvasions;
import got.common.database.GOTItems;
import got.common.database.GOTUnitTradeEntries;
import got.common.entity.other.iface.GOTUnitTradeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityVolantisCaptain extends GOTEntityVolantisMan implements GOTUnitTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityVolantisCaptain(World world) {
		super(world);
	}

	@Override
	public float getAlignmentBonus() {
		return 5.0f;
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.VOLANTIS;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return GOTInvasions.VOLANTIS;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_sword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.volantisBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.volantisLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.volantisChestplate));

		return entityData;
	}
}
