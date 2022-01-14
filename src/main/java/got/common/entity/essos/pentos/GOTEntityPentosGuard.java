package got.common.entity.essos.pentos;

import got.common.database.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityPentosGuard extends GOTEntityPentosLevyman {
	public GOTEntityPentosGuard(World world) {
		super(world);
		spawnRidingHorse = false;
		npcCape = GOTCapes.PENTOS;
		npcShield = GOTShields.PENTOS;
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
		setCurrentItemOrArmor(0, new ItemStack(GOTRegistry.essosPolearm));
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.essosPolearm));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.pentosBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.pentosLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.pentosChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.pentosHelmet));
		return data;
	}
}
