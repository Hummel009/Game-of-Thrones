package got.common.entity.essos.norvos;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityNorvosSoldier extends GOTEntityNorvosMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityNorvosSoldier(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
		cape = GOTCapes.NORVOS;
		shield = GOTShields.NORVOS;
		alignmentBonus = 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupIronWeaponSet(this, rand, false);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.norvosBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.norvosLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.norvosChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.norvosHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
