package got.common.entity.westeros.north.hillmen;

import got.common.database.GOTInvasions;
import got.common.database.GOTItems;
import got.common.database.GOTSpeech;
import got.common.database.GOTUnitTradeEntries;
import got.common.entity.other.iface.GOTUnitTradeable;
import got.common.entity.other.utils.GOTEntityUtils;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityNorthHillmanChieftain extends GOTEntityNorthHillman implements GOTUnitTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityNorthHillmanChieftain(World world) {
		super(world);
	}

	@Override
	public float getAlignmentBonus() {
		return 5.0f;
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityPlayer) {
		return isFriendlyAndStronglyAligned(entityPlayer);
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.NORTH_HILLMEN;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return null;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityPlayer) {
		return GOTSpeech.getCaptainSpeech(this, entityPlayer);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.trident));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		GOTEntityUtils.setupLeathermanArmorNoHelmet(this, rand);

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}