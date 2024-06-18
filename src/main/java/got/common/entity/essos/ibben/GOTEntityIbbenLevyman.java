package got.common.entity.essos.ibben;

import got.common.entity.other.utils.GOTEntityUtils;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class GOTEntityIbbenLevyman extends GOTEntityIbbenMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityIbbenLevyman(World world) {
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
		GOTEntityUtils.setupLeathermanArmorNoHelmet(this, rand);

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}