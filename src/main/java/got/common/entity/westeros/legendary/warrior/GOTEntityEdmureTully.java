package got.common.entity.westeros.legendary.warrior;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.ai.GOTEntityAIRangedAttack;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.utils.GOTEntityUtils;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityEdmureTully extends GOTEntityHumanBase {
	private final EntityAIBase rangedAttackAI = new GOTEntityAIRangedAttack(this, 1.25, 30, 50, 20.0f);
	private EntityAIBase meleeAttackAI;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityEdmureTully(World world) {
		super(world);
		addTargetTasks(true);
		setupLegendaryNPC(true);
	}

	@Override
	public EntityAIBase getAttackAI() {
		meleeAttackAI = new GOTEntityAIAttackOnCollide(this, 1.4, true);
		return meleeAttackAI;
	}

	@Override
	public void onAttackModeChange(AttackMode mode, boolean mounted) {
		GOTEntityUtils.setupComboAttackModeChange(this, mode, meleeAttackAI, rangedAttackAI);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		dropItem(GOTItems.arrowFire, 64);
	}

	@Override
	public float getAlignmentBonus() {
		return 200.0f;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.RIVERLANDS;
	}

	@Override
	public float getFireArrowChance() {
		return 1.0f;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killEdmureTully;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.alloySteelSword));
		npcItemsInv.setRangedWeapon(new ItemStack(GOTItems.longbow));
		npcItemsInv.setIdleItem(npcItemsInv.getRangedWeapon());

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}