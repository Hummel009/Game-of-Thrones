package got.common.entity.essos.norvos;

import got.common.database.GOTInvasions;
import got.common.database.GOTItems;
import got.common.database.GOTSpeech;
import got.common.database.GOTUnitTradeEntries;
import got.common.entity.other.iface.GOTUnitTradeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityNorvosCaptain extends GOTEntityNorvosMan implements GOTUnitTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityNorvosCaptain(World world) {
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
	public String getSpeechBank(EntityPlayer entityPlayer) {
		return GOTSpeech.getCaptainSpeech(this, entityPlayer);
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.NORVOS;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return GOTInvasions.NORVOS;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.essosSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.norvosBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.norvosLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.norvosChestplate));

		return entityData;
	}
}
