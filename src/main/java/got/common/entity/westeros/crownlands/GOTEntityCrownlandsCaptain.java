package got.common.entity.westeros.crownlands;

import got.common.database.*;
import got.common.entity.other.iface.GOTUnitTradeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityCrownlandsCaptain extends GOTEntityCrownlandsMan implements GOTUnitTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityCrownlandsCaptain(World world) {
		super(world);
		cape = GOTCapes.CROWNLANDS;
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
		return GOTUnitTradeEntries.CROWNLANDS;
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

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.crownlandsBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.crownlandsLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.crownlandsChestplate));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
