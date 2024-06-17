package got.common.entity.westeros.dragonstone;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityDragonstoneSoldier extends GOTEntityDragonstoneMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityDragonstoneSoldier(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
		shield = GOTShields.DRAGONSTONE;
		cape = GOTCapes.DRAGONSTONE;
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupWesterosWeaponSet(this, rand);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.dragonstoneBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.dragonstoneLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.dragonstoneChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.dragonstoneHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
