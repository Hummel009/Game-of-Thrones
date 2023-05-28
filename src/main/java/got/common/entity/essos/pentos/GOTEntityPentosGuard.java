package got.common.entity.essos.pentos;

import got.common.database.GOTAchievement;
import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityPentosGuard extends GOTEntityPentosLevyman {
	public GOTEntityPentosGuard(World world) {
		super(world);
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
		return GOTAchievement.killThePolice;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(GOTItems.essosPolearm));
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.essosPolearm));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.pentosBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.pentosLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.pentosChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.pentosHelmet));
		return data;
	}
}
