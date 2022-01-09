package got.common.entity.essos.lhazar;

import got.common.GOTLevelData;
import got.common.database.*;
import got.common.entity.other.GOTUnitTradeable;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityLhazarWarlord extends GOTEntityLhazarWarrior implements GOTUnitTradeable {
	public GOTEntityLhazarWarlord(World world) {
		super(world);
		canBeMarried = false;
		this.addTargetTasks(false);
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
				return "standart/civilized/usual_friendly";
			}
			return "standart/civilized/usual_neutral";
		}
		return "standart/civilized/usual_hostile";
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.LHAZAR;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return null;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.lhazarBootsLion));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.lhazarLeggingsLion));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.lhazarChestplateLion));
		setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.lhazarHelmetLion));
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.lhazarClub));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(4, null);
		return data;
	}

	@Override
	public void onUnitTrade(EntityPlayer entityplayer) {
		GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.TRADE);
	}
}
