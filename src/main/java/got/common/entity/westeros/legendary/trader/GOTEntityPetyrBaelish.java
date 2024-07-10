package got.common.entity.westeros.legendary.trader;

import got.common.GOTLevelData;
import got.common.database.*;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.iface.GOTBartender;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityPetyrBaelish extends GOTEntityHumanBase implements GOTBartender {
	private boolean soldDagger;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityPetyrBaelish(World world) {
		super(world);
		setupLegendaryNPC(true);
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.CROWNLANDS;
	}

	@Override
	public float getAlignmentBonus() {
		return 300.0f;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killPetyrBaelish;
	}

	@Override
	public boolean getTradeCondition(GOTEntityNPC npc, EntityPlayer entityPlayer) {
		return !soldDagger && isFriendlyAndAligned(entityPlayer);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		dropItem(GOTItems.petyrBaelishBrooch, 1);
		if (!soldDagger) {
			dropItem(GOTItems.petyrBaelishDagger, 1);
		}
	}

	@Override
	public GOTTradeEntries getSellsPool() {
		return GOTTradeEntries.BAELISH_SELLS;
	}

	@Override
	public GOTTradeEntries getBuysPool() {
		return GOTTradeEntries.EMPTY_BUYS;
	}

	@Override
	public String getSpeechBank(GOTEntityNPC npc, EntityPlayer entityPlayer) {
		if (npc.isFriendly(entityPlayer)) {
			return "legendary/baelish_friendly";
		}
		return GOTSpeech.HOSTILE;
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.PROSTITUTE_KEEPER;
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityPlayer) {
		GOTLevelData.getData(entityPlayer).addAchievement(GOTAchievement.trade);
		soldDagger = true;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.petyrBaelishDagger));
		npcItemsInv.setIdleItem(null);

		setCurrentItemOrArmor(3, new ItemStack(GOTItems.petyrBaelishBrooch));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}