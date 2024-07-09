package got.common.entity.westeros.legendary.captain;

import got.common.database.*;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.iface.GOTUnitTradeable;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityJeorMormont extends GOTEntityHumanBase implements GOTUnitTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityJeorMormont(World world) {
		super(world);
		setupLegendaryNPC(true);
		cape = GOTCapes.NIGHT;
		faction = GOTFaction.NIGHT_WATCH;
		alignmentBonus = 300.0f;
		killAchievement = GOTAchievement.killJeorMormont;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		dropItem(GOTItems.longclaw, 1);
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.GIFT;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return null;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.longclaw));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}