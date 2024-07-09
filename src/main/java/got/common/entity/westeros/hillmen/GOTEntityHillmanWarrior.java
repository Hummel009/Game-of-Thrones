package got.common.entity.westeros.hillmen;

import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityHillmanWarrior extends GOTEntityHillman {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityHillmanWarrior(World world) {
		super(world);
		addTargetTasks(true);
		shield = GOTShields.HILLMEN;
		alignmentBonus = 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupPrimitiveIronWeaponSet(this, rand, false);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.hillmenBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.hillmenLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.hillmenChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.hillmenHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}