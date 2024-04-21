package got.common.entity.westeros.arryn;

import got.common.database.GOTAchievement;
import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityArrynGuard extends GOTEntityArrynSoldier {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityArrynGuard(World world) {
		super(world);
		shield = GOTShields.ARRYNGUARD;
		cape = GOTCapes.ARRYNGUARD;
		spawnRidingHorse = false;
		addTargetTasks(false);
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killThePolice;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(GOTItems.westerosPike));
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosPike));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.arrynguardBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.arrynguardLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.arrynguardChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.arrynguardHelmet));
		return entityData;
	}
}