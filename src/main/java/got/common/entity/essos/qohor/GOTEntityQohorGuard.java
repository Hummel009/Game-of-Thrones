package got.common.entity.essos.qohor;

import got.common.database.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityQohorGuard extends GOTEntityQohorLevyman {
	public GOTEntityQohorGuard(World world) {
		super(world);
		spawnRidingHorse = false;
		npcCape = GOTCapes.QOHOR;
		npcShield = GOTShields.QOHOR;
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
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.qohorBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.qohorLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.qohorChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.qohorHelmet));
		return data;
	}
}
