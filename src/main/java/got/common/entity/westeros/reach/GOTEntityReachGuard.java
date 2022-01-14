package got.common.entity.westeros.reach;

import got.common.database.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityReachGuard extends GOTEntityReachSoldier {
	public GOTEntityReachGuard(World world) {
		super(world);
		canBeMarried = false;
		npcShield = GOTShields.REACHGUARD;
		spawnRidingHorse = false;
		addTargetTasks(false);
	}

	@Override
	public float getAlignmentBonus() {
		return 5.0f;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.BANDIT;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(GOTRegistry.westerosPike));
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.westerosPike));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.reachguardBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.reachguardLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.reachguardChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.reachguardHelmet));
		return data;
	}
}
