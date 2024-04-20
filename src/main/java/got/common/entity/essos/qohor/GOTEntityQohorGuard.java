package got.common.entity.essos.qohor;

import got.common.database.GOTAchievement;
import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityQohorGuard extends GOTEntityQohorLevyman {
	public GOTEntityQohorGuard(World world) {
		super(world);
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
		return GOTAchievement.killThePolice;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(GOTItems.essosPolearm));
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.essosPolearm));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.qohorBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.qohorLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.qohorChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.qohorHelmet));
		return data;
	}
}