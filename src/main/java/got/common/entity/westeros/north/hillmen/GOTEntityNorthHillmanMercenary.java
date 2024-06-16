package got.common.entity.westeros.north.hillmen;

import got.common.database.GOTUnitTradeEntries;
import got.common.entity.other.iface.GOTMercenary;
import got.common.entity.other.utils.GOTEntityUtils;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTEntityNorthHillmanMercenary extends GOTEntityNorthHillman implements GOTMercenary {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityNorthHillmanMercenary(World world) {
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

		GOTWeaponSetFactory.setupHillmenWeaponSet(this, rand);

		GOTEntityUtils.setupWesterosLevymanArmor(this, rand);

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityPlayer) {
		return isFriendlyAndAligned(entityPlayer);
	}

	@Override
	public float getMercAlignmentRequired() {
		return GOTUnitTradeEntries.LEVYMAN_F;
	}

	@Override
	public int getMercBaseCost() {
		return GOTUnitTradeEntries.LEVYMAN;
	}
}