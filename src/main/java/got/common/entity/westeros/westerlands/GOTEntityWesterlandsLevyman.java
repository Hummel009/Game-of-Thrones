package got.common.entity.westeros.westerlands;

import got.common.entity.other.utils.GOTEntityUtils;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class GOTEntityWesterlandsLevyman extends GOTEntityWesterlandsMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityWesterlandsLevyman(World world) {
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

		GOTWeaponSetFactory.setupIronWeaponSet(this, rand, false);
		GOTEntityUtils.setupLeathermanArmor(this, rand);

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
