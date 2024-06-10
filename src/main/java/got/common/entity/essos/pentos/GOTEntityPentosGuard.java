package got.common.entity.essos.pentos;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityPentosGuard extends GOTEntityPentosLevyman {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityPentosGuard(World world) {
		super(world);
		cape = GOTCapes.PENTOS;
		shield = GOTShields.PENTOS;
		addTargetTasks(false);
	}

	@Override
	public float getAlignmentBonus() {
		return 5.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(GOTItems.essosPolearm));
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.essosPolearm));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.pentosBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.pentosLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.pentosChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.pentosHelmet));
		return entityData;
	}
}