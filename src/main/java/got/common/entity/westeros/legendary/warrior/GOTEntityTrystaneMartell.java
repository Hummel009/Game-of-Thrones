package got.common.entity.westeros.legendary.warrior;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityTrystaneMartell extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityTrystaneMartell(World world) {
		super(world);
		addTargetTasks(true);
		setupLegendaryNPC(true);
		setSize(0.6f * 0.9f, 1.8f * 0.9f);
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killTrystaneMartell;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		dropItem(GOTItems.trystaneSword, 1);
	}

	@Override
	public float getAlignmentBonus() {
		return 500.0f;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.DORNE;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.trystaneSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}