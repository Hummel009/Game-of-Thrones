package got.common.entity.westeros.wildling.thenn;

import got.common.database.GOTItems;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityThennWarrior extends GOTEntityThenn {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityThennWarrior(World world) {
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

		GOTWeaponSetFactory.setupPrimitiveIronWeaponSet(this, rand, false);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.furBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.furLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.furChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.furHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}