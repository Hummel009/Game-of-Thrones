package got.common.entity.westeros.arryn;

import got.common.entity.other.utils.GOTEntityUtils;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class GOTEntityArrynLevyman extends GOTEntityArrynMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityArrynLevyman(World world) {
		super(world);
		addTargetTasks(true);
		alignmentBonus = 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupIronWeaponSet(this, rand, false);
		GOTEntityUtils.setupLeathermanArmor(this, rand);

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}