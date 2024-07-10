package got.common.entity.westeros.legendary.warrior;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityPodrickPayne extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityPodrickPayne(World world) {
		super(world);
		setupLegendaryNPC(true);
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.CROWNLANDS;
	}

	@Override
	public float getAlignmentBonus() {
		return 100.0f;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killPodrickPayne;
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