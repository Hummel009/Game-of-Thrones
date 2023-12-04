package got.common.entity.essos.lhazar;

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

public class GOTEntityLhazarWarlord extends GOTEntityLhazarWarrior implements GOTUnitTradeable {
	public GOTEntityLhazarWarlord(World world) {
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
				return "standard/civilized/usual_friendly";
			}
			return "standard/civilized/usual_neutral";
		}
		return "standard/civilized/usual_hostile";
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
		IEntityLivingData data1 = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.lhazarBootsLion));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.lhazarLeggingsLion));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.lhazarChestplateLion));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.lhazarHelmetLion));
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.lhazarClub));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(4, null);
		return data1;
	}

	@Override
	public void onUnitTrade(EntityPlayer entityplayer) {
		GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.trade);
	}
}
