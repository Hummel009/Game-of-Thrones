package got.common.entity.westeros.legendary.warrior;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.database.GOTSpeech;
import got.common.entity.ai.GOTEntityAINearestAttackableTargetBasic;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityJaimeLannister extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityJaimeLannister(World world) {
		super(world);
		addTargetTasks();
		setupLegendaryNPC(true);
	}

	private void addTargetTasks() {
		int target = addTargetTasks(true);
		targetTasks.addTask(target + 1, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityRobbStark.class, 0, true));
	}

	@Override
	public float getAlignmentBonus() {
		return 300.0f;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.WESTERLANDS;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killJaimeLannister;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityPlayer) {
		if (isFriendly(entityPlayer)) {
			return "legendary/jaime_friendly";
		}
		return GOTSpeech.HOSTILE;
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