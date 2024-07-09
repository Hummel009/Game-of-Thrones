package got.common.entity.essos.pentos;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityPentosSoldier extends GOTEntityPentosMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityPentosSoldier(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
		cape = GOTCapes.PENTOS;
		shield = GOTShields.PENTOS;
		alignmentBonus = 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupIronWeaponSet(this, rand, true);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.pentosBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.pentosLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.pentosChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.pentosHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
