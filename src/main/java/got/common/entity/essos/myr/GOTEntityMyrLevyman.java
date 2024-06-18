package got.common.entity.essos.myr;

import got.common.entity.other.utils.GOTEntityUtils;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class GOTEntityMyrLevyman extends GOTEntityMyrMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityMyrLevyman(World world) {
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

		GOTWeaponSetFactory.setupEssosWeaponSet(this, rand);
		GOTEntityUtils.setupLeathermanArmorTurban(this, rand);

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
