package got.common.entity.essos.qarth;

import got.common.database.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityQarthGuard extends GOTEntityQarthLevyman {
	public GOTEntityQarthGuard(World world) {
		super(world);
		spawnRidingHorse = false;
		npcCape = GOTCapes.QARTH;
		npcShield = GOTShields.QARTH;
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
		setCurrentItemOrArmor(0, new ItemStack(GOTRegistry.essosPolearm));
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.essosPolearm));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.qarthBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.qarthLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.qarthChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.qarthHelmet));
		return data;
	}
}
