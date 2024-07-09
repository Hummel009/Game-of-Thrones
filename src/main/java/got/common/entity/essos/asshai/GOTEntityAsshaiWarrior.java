package got.common.entity.essos.asshai;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityAsshaiWarrior extends GOTEntityAsshaiMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityAsshaiWarrior(World world) {
		super(world);
		addTargetTasks(true);
		shield = GOTShields.ASSHAI;
		cape = GOTCapes.ASSHAI;
		alignmentBonus = 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupIronWeaponSet(this, rand, false);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.asshaiBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.asshaiLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.asshaiChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.asshaiHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}