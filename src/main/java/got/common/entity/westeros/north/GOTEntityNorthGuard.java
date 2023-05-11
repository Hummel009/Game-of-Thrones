package got.common.entity.westeros.north;

import got.common.database.GOTAchievement;
import got.common.database.GOTCapes;
import got.common.database.GOTRegistry;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityNorthGuard extends GOTEntityNorthLevyman {
	public GOTEntityNorthGuard(World world) {
		super(world);
		spawnRidingHorse = false;
		npcShield = GOTShields.NORTHGUARD;
		npcCape = GOTCapes.NORTHGUARD;
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
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.northguardBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.northguardLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.northguardChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.northguardHelmet));
		return data;
	}
}
