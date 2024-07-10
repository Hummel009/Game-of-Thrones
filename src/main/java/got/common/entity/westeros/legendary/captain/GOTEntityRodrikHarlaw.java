package got.common.entity.westeros.legendary.captain;

import got.common.database.GOTInvasions;
import got.common.database.GOTItems;
import got.common.database.GOTUnitTradeEntries;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.iface.GOTUnitTradeable;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityRodrikHarlaw extends GOTEntityHumanBase implements GOTUnitTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityRodrikHarlaw(World world) {
		super(world);
		setupLegendaryNPC(true);
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.IRONBORN;
	}

	@Override
	public float getAlignmentBonus() {
		return 100.0f;
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.IRONBORN;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return GOTInvasions.IRONBORN;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.alloySteelDagger));
		npcItemsInv.setIdleItem(new ItemStack(Items.book));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}