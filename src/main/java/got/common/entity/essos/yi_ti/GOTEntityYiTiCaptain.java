package got.common.entity.essos.yi_ti;

import got.common.database.*;
import got.common.entity.other.iface.GOTUnitTradeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityYiTiCaptain extends GOTEntityYiTiMan implements GOTUnitTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityYiTiCaptain(World world) {
		super(world);
		cape = GOTCapes.YI_TI;
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
		return GOTUnitTradeEntries.YI_TI;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return GOTInvasions.YI_TI;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.yiTiSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.yiTiBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.yiTiLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.yiTiChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.yiTiHelmetCaptain));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}