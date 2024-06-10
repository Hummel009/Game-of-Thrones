package got.common.entity.essos.qarth;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityQarthGuard extends GOTEntityQarthLevyman {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityQarthGuard(World world) {
		super(world);
		spawnRidingHorse = false;
		cape = GOTCapes.QARTH;
		shield = GOTShields.QARTH;
		addTargetTasks(false);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(GOTItems.essosPolearm));
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.essosPolearm));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.qarthBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.qarthLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.qarthChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.qarthHelmet));
		return entityData;
	}
}