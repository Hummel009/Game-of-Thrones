package got.common.entity.westeros.wildling;

import got.common.GOTLevelData;
import got.common.database.*;
import got.common.entity.other.GOTUnitTradeable;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityWildlingChieftain extends GOTEntityWildling implements GOTUnitTradeable {
	public GOTEntityWildlingChieftain(World world) {
		super(world);
		canBeMarried = false;
		addTargetTasks(false);
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
				return "westeros/wildling/chief/friendly";
			}
			return "westeros/wildling/chief/neutral";
		}
		return "westeros/wildling/man/hostile";
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.WILDLING;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return GOTInvasions.WILDLING;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.skullStaff));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.furBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.furLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.furChestplate));
		setCurrentItemOrArmor(4, null);
		return data;
	}

	@Override
	public void onUnitTrade(EntityPlayer entityplayer) {
		GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.TRADE);
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}