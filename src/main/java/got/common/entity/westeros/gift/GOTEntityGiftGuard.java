package got.common.entity.westeros.gift;

import got.common.database.GOTCapes;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class GOTEntityGiftGuard extends GOTEntityGiftMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityGiftGuard(World world) {
		super(world);
		addTargetTasks(true);
		cape = GOTCapes.NIGHT;
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