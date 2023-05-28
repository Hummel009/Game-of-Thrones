package got.common.entity.essos.dothraki;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTInvasions;
import got.common.database.GOTItems;
import got.common.database.GOTUnitTradeEntries;
import got.common.entity.other.GOTUnitTradeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityDothrakiKhal extends GOTEntityDothraki implements GOTUnitTradeable {
	public GOTEntityDothrakiKhal(World world) {
		super(world);
		canBeMarried = false;
		addTargetTasks(false);
		npcCape = null;
		spawnRidingHorse = false;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
	}

	@Override
	public boolean canDothrakiSkirmish() {
		return false;
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return GOTLevelData.getData(entityplayer).getAlignment(getFaction()) >= 50.0f && isFriendly(entityplayer);
	}

	@Override
	public float getAlignmentBonus() {
		return 10.0f;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killKhal;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (canTradeWith(entityplayer)) {
				return "standard/wild/usual_friendly";
			}
			return "standard/wild/usual_neutral";
		}
		return "standard/wild/usual_hostile";
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.DOTHRAKI;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return GOTInvasions.DOTHRAKI;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.nomadSword));
		npcItemsInv.setMeleeWeaponMounted(npcItemsInv.getMeleeWeapon());
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		npcItemsInv.setIdleItemMounted(npcItemsInv.getMeleeWeaponMounted());
		setCurrentItemOrArmor(4, null);
		return data;
	}

	@Override
	public void onUnitTrade(EntityPlayer entityplayer) {
		GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.trade);
	}
}
