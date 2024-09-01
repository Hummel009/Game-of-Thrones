package got.common.entity.westeros.north.hillmen;

import got.common.entity.other.utils.GOTEntityUtils;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class GOTEntityNorthHillmanWarrior extends GOTEntityNorthHillman {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityNorthHillmanWarrior(World world) {
		super(world);
		addTargetTasks(true);
	}

	@Override
	public float getReputationBonus() {
		return 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupPrimitiveIronWeaponSet(this, rand, false);
		GOTEntityUtils.setupLeathermanArmorNoHelmet(this, rand);

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}