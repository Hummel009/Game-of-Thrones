package got.common.entity.essos.lhazar;

import got.common.entity.other.utils.GOTEntityUtils;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class GOTEntityLhazarLevyman extends GOTEntityLhazarMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityLhazarLevyman(World world) {
		super(world);
		addTargetTasks(true);
		alignmentBonus = 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupIronWeaponSet(this, rand, true);
		GOTEntityUtils.setupLeathermanArmorTurban(this, rand);

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}