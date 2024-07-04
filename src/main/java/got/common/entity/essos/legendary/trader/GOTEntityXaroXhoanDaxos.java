package got.common.entity.essos.legendary.trader;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.database.GOTSpeech;
import got.common.database.GOTTradeEntries;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.iface.GOTTradeable;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityXaroXhoanDaxos extends GOTEntityHumanBase implements GOTTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityXaroXhoanDaxos(World world) {
		super(world);
		setupLegendaryNPC(true);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityPlayer) {
		return isFriendlyAndAligned(entityPlayer);
	}

	@Override
	public float getAlignmentBonus() {
		return 500.0f;
	}

	@Override
	public GOTTradeEntries getSellsPool() {
		return GOTTradeEntries.GOLDSMITH_SELLS;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.QARTH;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killXaroXhoanDaxos;
	}

	@Override
	public GOTTradeEntries getBuysPool() {
		return GOTTradeEntries.GOLDSMITH_BUYS;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityPlayer) {
		if (isFriendly(entityPlayer)) {
			return "legendary/xaro_friendly";
		}
		return GOTSpeech.HOSTILE;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.alloySteelDaggerPoisoned));
		npcItemsInv.setIdleItem(null);
		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}