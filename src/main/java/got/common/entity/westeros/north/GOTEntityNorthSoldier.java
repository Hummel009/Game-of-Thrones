package got.common.entity.westeros.north;

import got.common.database.GOTCapes;
import got.common.database.GOTShields;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class GOTEntityNorthSoldier extends GOTEntityNorthMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityNorthSoldier(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
		shield = GOTShields.NORTH;
		cape = GOTCapes.NORTH;
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupWesterosWeaponSet(this, rand);

		setupFactionArmor(false);

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
