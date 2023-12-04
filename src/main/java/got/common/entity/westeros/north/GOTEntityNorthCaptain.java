package got.common.entity.westeros.north;

import got.common.GOTLevelData;
import got.common.database.*;
import got.common.entity.other.GOTUnitTradeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityNorthCaptain extends GOTEntityNorthSoldier implements GOTUnitTradeable {
	public GOTEntityNorthCaptain(World world) {
		super(world);
		canBeMarried = false;
		addTargetTasks(false);
		npcCape = GOTCapes.NORTH;
		spawnRidingHorse = false;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return GOTLevelData.getData(entityplayer).getAlignment(getFaction()) >= 50.0f && isFriendly(entityplayer);
	}

	@Override
	public float getAlignmentBonus() {
		return 5.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (canTradeWith(entityplayer)) {
				return "standard/civilized/usual_friendly";
			}
			return "standard/civilized/usual_neutral";
		}
		return "standard/civilized/usual_hostile";
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.NORTH;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return GOTInvasions.NORTH;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData data1 = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosSword));
		npcItemsInv.setMeleeWeaponMounted(npcItemsInv.getMeleeWeapon());
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		npcItemsInv.setIdleItemMounted(npcItemsInv.getMeleeWeaponMounted());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.northBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.northLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.northChestplate));
		setCurrentItemOrArmor(4, null);
		return data1;
	}

	@Override
	public void onUnitTrade(EntityPlayer entityplayer) {
		GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.trade);
	}
}
