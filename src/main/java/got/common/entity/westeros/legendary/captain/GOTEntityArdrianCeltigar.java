package got.common.entity.westeros.legendary.captain;

import got.common.database.GOTInvasions;
import got.common.database.GOTItems;
import got.common.database.GOTUnitTradeEntries;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.iface.GOTUnitTradeable;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityArdrianCeltigar extends GOTEntityHumanBase implements GOTUnitTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityArdrianCeltigar(World world) {
		super(world);
		setupLegendaryNPC(true);
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.DRAGONSTONE;
	}

	@Override
	public float getAlignmentBonus() {
		return 100.0f;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		dropItem(GOTItems.ardrianCeltigarAxe, 1);
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.DRAGONSTONE;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return GOTInvasions.DRAGONSTONE;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.ardrianCeltigarAxe));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}