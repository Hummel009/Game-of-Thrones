package got.common.entity.westeros.crownlands;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityCrownlandsGuard extends GOTEntityCrownlandsLevyman {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityCrownlandsGuard(World world) {
		super(world);
		shield = GOTShields.CROWNLANDS;
		cape = GOTCapes.CROWNLANDS;
		addTargetTasks(false);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(GOTItems.westerosPike));
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosPike));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.crownlandsBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.crownlandsLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.crownlandsChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.crownlandsHelmet));
		return entityData;
	}
}