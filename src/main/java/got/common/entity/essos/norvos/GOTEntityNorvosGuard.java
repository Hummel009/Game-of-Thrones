package got.common.entity.essos.norvos;

import got.common.database.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityNorvosGuard extends GOTEntityNorvosLevyman {
	public GOTEntityNorvosGuard(World world) {
		super(world);
		npcCape = GOTCapes.NORVOS;
		npcShield = GOTShields.NORVOS;
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
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.norvosBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.norvosLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.norvosChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.norvosHelmet));
		return data;
	}
}
