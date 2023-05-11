package got.common.entity.essos.yiti;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTRegistry;
import got.common.database.GOTTradeEntries;
import got.common.entity.other.GOTTradeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityYiTiBlacksmith extends GOTEntityYiTiMan implements GOTTradeable.Smith {
	public GOTEntityYiTiBlacksmith(World world) {
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
		return GOTLevelData.getData(entityplayer).getAlignment(getFaction()) >= 0.0f && isFriendly(entityplayer);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int ingots = 1 + rand.nextInt(3) + rand.nextInt(i + 1);
		for (int l = 0; l < ingots; ++l) {
			if (rand.nextBoolean()) {
				dropItem(Items.iron_ingot, 1);
				continue;
			}
			dropItem(GOTRegistry.yitiSteelIngot, 1);
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public GOTTradeEntries getBuyPool() {
		return GOTTradeEntries.YITI_BLACKSMITH_BUY;
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.C_BLACKSMITH_SELL;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "standart/civilized/usual_friendly";
		}
		return "standart/civilized/usual_hostile";
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, GOTTradeEntries.TradeType type, ItemStack itemstack) {
		GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.trade);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.blacksmithHammer));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		return data;
	}
}
