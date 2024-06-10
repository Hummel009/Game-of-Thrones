package got.common.entity.essos.ghiscar;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityGhiscarGuard extends GOTEntityGhiscarLevyman {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityGhiscarGuard(World world) {
		super(world);
		cape = GOTCapes.GHISCAR;
		shield = GOTShields.GHISCAR;
		addTargetTasks(false);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(GOTItems.essosPolearm));
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.essosPolearm));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.ghiscarBootsGemsbok));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.ghiscarLeggingsGemsbok));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.ghiscarChestplateGemsbok));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.ghiscarHelmetGemsbok));
		return entityData;
	}
}