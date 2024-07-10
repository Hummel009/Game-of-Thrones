package got.common.entity.westeros.legendary.mercenary;

import got.common.database.GOTItems;
import got.common.database.GOTUnitTradeEntries;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.iface.GOTMercenary;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityAndrikTheUnsmilling extends GOTEntityHumanBase implements GOTMercenary {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityAndrikTheUnsmilling(World world) {
		super(world);
		addTargetTasks(true);
		setupLegendaryNPC(true);
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.IRONBORN;
	}

	@Override
	public float getAlignmentBonus() {
		return GOTUnitTradeEntries.SOLDIER_F * 5;
	}

	@Override
	public float getMercAlignmentRequired() {
		return GOTUnitTradeEntries.SOLDIER_F;
	}

	@Override
	public int getMercBaseCost() {
		return GOTUnitTradeEntries.SOLDIER * 5;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.alloySteelSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}