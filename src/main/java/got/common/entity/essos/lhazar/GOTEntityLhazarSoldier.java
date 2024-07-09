package got.common.entity.essos.lhazar;

import got.common.database.GOTItems;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityLhazarSoldier extends GOTEntityLhazarMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityLhazarSoldier(World world) {
		super(world);
		addTargetTasks(true);
		alignmentBonus = 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupIronWeaponSet(this, rand, true);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.lhazarBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.lhazarLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.lhazarChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.lhazarHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}