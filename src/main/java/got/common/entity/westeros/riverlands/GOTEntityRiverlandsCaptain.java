package got.common.entity.westeros.riverlands;

import got.common.database.*;
import got.common.entity.other.iface.GOTUnitTradeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityRiverlandsCaptain extends GOTEntityRiverlandsMan implements GOTUnitTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityRiverlandsCaptain(World world) {
		super(world);
		cape = GOTCapes.RIVERLANDS;
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
		return GOTUnitTradeEntries.RIVERLANDS;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return GOTInvasions.RIVERLANDS;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityPlayer) {
		return GOTSpeech.getCaptainSpeech(this, entityPlayer);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_sword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.riverlandsBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.riverlandsLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.riverlandsChestplate));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
