package got.common.entity.westeros.arryn;

import got.common.database.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityArrynGuard extends GOTEntityArrynSoldier {
	public GOTEntityArrynGuard(World world) {
		super(world);
		npcShield = GOTShields.ARRYNGUARD;
		npcCape = GOTCapes.ARRYNGUARD;
		spawnRidingHorse = false;
		addTargetTasks(false);
	}

	@Override
	public float getAlignmentBonus() {
		return 5.0f;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killThePolice;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(GOTRegistry.westerosPike));
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.westerosPike));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.arrynguardBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.arrynguardLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.arrynguardChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.arrynguardHelmet));
		return data;
	}
}
