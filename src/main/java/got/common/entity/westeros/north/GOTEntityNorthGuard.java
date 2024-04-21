package got.common.entity.westeros.north;

import got.common.database.GOTAchievement;
import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityNorthGuard extends GOTEntityNorthLevyman {
	public GOTEntityNorthGuard(World world) {
		super(world);
		spawnRidingHorse = false;
		shield = GOTShields.NORTHGUARD;
		cape = GOTCapes.NORTHGUARD;
		addTargetTasks(false);
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killThePolice;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(GOTItems.westerosPike));
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosPike));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.northguardBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.northguardLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.northguardChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.northguardHelmet));
		return data;
	}
}