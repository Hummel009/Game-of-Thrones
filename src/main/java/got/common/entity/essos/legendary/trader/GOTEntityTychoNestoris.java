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

public class GOTEntityTychoNestoris extends GOTEntityHumanBase implements GOTTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityTychoNestoris(World world) {
		super(world);
		setupLegendaryNPC(true);
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killTychoNestoris;
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityPlayer) {
		return isFriendlyAndAligned(entityPlayer);
	}

	@Override
	public float getAlignmentBonus() {
		return 300.0f;
	}

	@Override
	public GOTTradeEntries getSellsPool() {
		return GOTTradeEntries.GOLDSMITH_SELLS;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.BRAAVOS;
	}

	@Override
	public GOTTradeEntries getBuysPool() {
		return GOTTradeEntries.GOLDSMITH_BUYS;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityPlayer) {
		if (isFriendly(entityPlayer)) {
			return "standard/civilized/usual_friendly";
		}
		return GOTSpeech.HOSTILE;
	}

	@Override
	public int getTotalArmorValue() {
		return 15;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.alloySteelDagger));
		npcItemsInv.setIdleItem(null);
		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}