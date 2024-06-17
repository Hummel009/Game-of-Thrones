package got.common.entity.westeros.north.hillmen;

import got.common.database.GOTItems;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityNorthHillmanWarrior extends GOTEntityNorthHillman {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityNorthHillmanWarrior(World world) {
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

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.northBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.northLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.northChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.northHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}