package got.common.entity.essos.lys;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityLysSoldier extends GOTEntityLysMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityLysSoldier(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
		cape = GOTCapes.LYS;
		shield = GOTShields.LYS;
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupEssosWeaponSet(this, rand);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.lysBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.lysLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.lysChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.lysHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
