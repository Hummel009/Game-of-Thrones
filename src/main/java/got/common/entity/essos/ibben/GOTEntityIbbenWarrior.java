package got.common.entity.essos.ibben;

import got.common.database.GOTItems;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityIbbenWarrior extends GOTEntityIbbenMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityIbbenWarrior(World world) {
		super(world);
		addTargetTasks(true);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupPrimitiveWeaponSet(this, rand);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.ibbenBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.ibbenLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.ibbenChestplate));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}