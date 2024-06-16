package got.common.entity.essos.yiti;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.ai.GOTEntityAINearestAttackableTargetPatriot;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityYiTiFrontier extends GOTEntityYiTiMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityYiTiFrontier(World world) {
		super(world);
		addTargetTasks(true, GOTEntityAINearestAttackableTargetPatriot.class);
		spawnRidingHorse = rand.nextInt(10) == 0;
		shield = GOTShields.YITI_FRONTIER;
		cape = GOTCapes.YITI;
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupYiTiWeaponSet(this, rand);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.yitiBootsFrontier));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.yitiLeggingsFrontier));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.yitiChestplateFrontier));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.yitiHelmetFrontier));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}