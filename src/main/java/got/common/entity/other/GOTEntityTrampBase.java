package got.common.entity.other;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTFoods;
import got.common.database.GOTTradeEntries;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.ai.GOTEntityAIDrink;
import got.common.entity.ai.GOTEntityAIEat;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class GOTEntityTrampBase extends GOTEntityHumanBase implements GOTTradeable.Smith, GOTBiome.ImmuneToHeat, GOTBiome.ImmuneToFrost {
	protected GOTEntityTrampBase(World world) {
		super(world);
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new GOTEntityAIAttackOnCollide(this, 1.4, true));
		tasks.addTask(2, new EntityAIOpenDoor(this, true));
		tasks.addTask(3, new EntityAIWander(this, 1.0));
		tasks.addTask(4, new GOTEntityAIEat(this, GOTFoods.WILD, 8000));
		tasks.addTask(4, new GOTEntityAIDrink(this, GOTFoods.WILD_DRINK, 8000));
		tasks.addTask(5, new EntityAIWatchClosest2(this, EntityPlayer.class, 10.0f, 0.1f));
		tasks.addTask(5, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 5.0f, 0.05f));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(7, new EntityAILookIdle(this));
		addTargetTasks(false);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return isFriendly(entityplayer);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int bones = rand.nextInt(2) + rand.nextInt(i + 1);
		for (int l = 0; l < bones; ++l) {
			dropItem(Items.bone, 1);
		}
	}

	@Override
	public GOTTradeEntries getBuyPool() {
		return GOTTradeEntries.C_TRAMP_BUY;
	}

	@Override
	public String getNPCName() {
		return familyInfo.getName();
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.C_TRAMP_SELL;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "standard/civilized/usual_friendly";
		}
		return "standard/civilized/usual_hostile";
	}

	@Override
	public void onAttackModeChange(GOTEntityNPC.AttackMode mode, boolean mounted) {
		if (mode == GOTEntityNPC.AttackMode.IDLE) {
			setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
		} else {
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, GOTTradeEntries.TradeType type, ItemStack itemstack) {
		GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.trade);
	}
}