package got.common.entity.westeros.ironborn;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.database.GOTSpeech;
import got.common.database.GOTTradeEntries;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.iface.GOTTradeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityIronbornPriest extends GOTEntityIronbornMan implements GOTTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityIronbornPriest(World world) {
		super(world);
	}

	@Override
	public float getReputationBonus() {
		return 2.0f;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killPriest;
	}

	@Override
	public GOTTradeEntries getSellsPool() {
		return GOTTradeEntries.FISHMONGER_SELLS;
	}

	@Override
	public GOTTradeEntries getBuysPool() {
		return GOTTradeEntries.TRAMP_BUYS;
	}

	@Override
	public String getSpeechBank(GOTEntityNPC npc, EntityPlayer entityPlayer) {
		return GOTSpeech.getFatherGrigoriSpeech(npc, entityPlayer);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.ironDagger));
		npcItemsInv.setIdleItem(null);

		return entityData;
	}
}