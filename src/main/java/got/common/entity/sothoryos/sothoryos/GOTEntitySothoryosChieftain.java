package got.common.entity.sothoryos.sothoryos;

import got.common.GOTLevelData;
import got.common.database.*;
import got.common.entity.other.GOTUnitTradeable;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntitySothoryosChieftain extends GOTEntitySothoryosWarrior implements GOTUnitTradeable {
	public GOTEntitySothoryosChieftain(World world) {
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
				return "sothoryos/sothoryos/chieftain/friendly";
			}
			return "sothoryos/sothoryos/chieftain/neutral";
		}
		return "sothoryos/sothoryos/warrior/hostile";
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.SOTHORYOS;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return null;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.sothoryosSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.sothoryosBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.sothoryosLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.sothoryosChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.sothoryosHelmetChieftain));
		return data;
	}

	@Override
	public void onUnitTrade(EntityPlayer entityplayer) {
		GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.TRADE);
	}
}
