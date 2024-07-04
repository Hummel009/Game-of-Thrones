package got.common.entity.other;

import got.common.database.*;
import got.common.entity.other.iface.GOTTradeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityRedPriest extends GOTEntityHumanBase implements GOTTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityRedPriest(World world) {
		super(world);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return isFriendlyAndAligned(entityplayer);
	}

	@Override
	public GOTTradeEntries getSellsPool() {
		return GOTTradeEntries.ALCHEMIST_SELLS;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killPriest;
	}

	@Override
	public GOTTradeEntries getBuysPool() {
		return GOTTradeEntries.ALCHEMIST_BUYS;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityPlayer) {
		return GOTSpeech.getFatherGrigoriSpeech(this, entityPlayer);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.ironDagger));
		npcItemsInv.setIdleItem(null);

		return entityData;
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(GOTNames.getAsshaiName(rand, familyInfo.isMale()));
	}
}