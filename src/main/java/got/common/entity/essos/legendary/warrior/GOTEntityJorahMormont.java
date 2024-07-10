package got.common.entity.essos.legendary.warrior;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityJorahMormont extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityJorahMormont(World world) {
		super(world);
		setupLegendaryNPC(true);
		setSize(0.6f, 1.8f);
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.DOTHRAKI;
	}

	@Override
	public float getAlignmentBonus() {
		return 300.0f;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killJorahMormont;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.alloySteelSword));
		npcItemsInv.setIdleItem(null);

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}