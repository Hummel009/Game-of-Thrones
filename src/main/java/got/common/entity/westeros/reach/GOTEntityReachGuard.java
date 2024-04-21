package got.common.entity.westeros.reach;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityReachGuard extends GOTEntityReachSoldier {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityReachGuard(World world) {
		super(world);
		shield = GOTShields.REACHGUARD;
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
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.reachguardBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.reachguardLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.reachguardChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.reachguardHelmet));
		return entityData;
	}
}