package got.common.entity.westeros.legendary.warrior;

import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.ai.GOTEntityAIRangedAttack;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.utils.GOTEntityUtils;
import got.common.faction.GOTFaction;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityLotharFrey extends GOTEntityHumanBase {
	private final EntityAIBase rangedAttackAI = new GOTEntityAIRangedAttack(this, 1.25, 30, 50, 20.0f);
	private EntityAIBase meleeAttackAI;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityLotharFrey(World world) {
		super(world);
		addTargetTasks(true);
		setupLegendaryNPC(true);
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.RIVERLANDS;
	}

	@Override
	public float getReputationBonus() {
		return 100.0f;
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
	public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
		npcCrossbowAttack(target);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.alloySteelSword));
		npcItemsInv.setRangedWeapon(new ItemStack(GOTItems.ironCrossbow));
		npcItemsInv.setIdleItem(npcItemsInv.getRangedWeapon());

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}