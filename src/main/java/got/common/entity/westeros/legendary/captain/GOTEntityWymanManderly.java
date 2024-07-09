package got.common.entity.westeros.legendary.captain;

import got.common.database.GOTAchievement;
import got.common.database.GOTInvasions;
import got.common.database.GOTItems;
import got.common.database.GOTUnitTradeEntries;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.iface.GOTUnitTradeable;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityWymanManderly extends GOTEntityHumanBase implements GOTUnitTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityWymanManderly(World world) {
		super(world);
		setupLegendaryNPC(true);
		faction = GOTFaction.NORTH;
		alignmentBonus = 100.0f;
		killAchievement = GOTAchievement.killWymanManderly;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		dropItem(GOTItems.fin, 1);
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.NORTH;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return GOTInvasions.NORTH;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.fin));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}