package got.common.entity.westeros.crownlands;

import got.common.database.GOTAchievement;
import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAINearestAttackableTargetPatriot;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityKingsguard extends GOTEntityCrownlandsGuard {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityKingsguard(World world) {
		super(world);
		cape = GOTCapes.ROYALGUARD;
		addTargetTasks(true, GOTEntityAINearestAttackableTargetPatriot.class);
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killKingsguard;
	}

	@Override
	public float getAlignmentBonus() {
		return 3.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(GOTItems.westerosSword));
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.kingsguardBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.kingsguardLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.kingsguardChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.kingsguardHelmet));
		return entityData;
	}
}