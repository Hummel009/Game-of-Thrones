package got.common.entity.other;

import got.common.GOTLevelData;
import got.common.database.*;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.ai.GOTEntityAIDrink;
import got.common.entity.ai.GOTEntityAIEat;
import got.common.item.other.GOTItemLeatherHat;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.awt.*;

public class GOTEntityLightSkinScrapTrader extends GOTEntityHumanBase implements GOTTradeable.Smith, GOTBiome.ImmuneToHeat, GOTBiome.ImmuneToFrost {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityLightSkinScrapTrader(World world) {
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
		return GOTTradeEntries.C_BOMZH_BUY;
	}

	@Override
	public String getNPCName() {
		return familyInfo.getName();
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.C_BOMZH_SELL;
	}

	public String getSmithSpeechBank() {
		return "standard/civilized/usual_friendly";
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

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		int weapon = rand.nextInt(4);
		if (weapon == 0 || weapon == 1 || weapon == 2) {
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.ironDagger));
		} else {
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.bronzeDagger));
		}
		npcItemsInv.setIdleItem(null);
		ItemStack hat = new ItemStack(GOTItems.leatherHat);
		float h = 0.06111111f;
		float s = MathHelper.randomFloatClamp(rand, 0.0f, 0.5f);
		float b = MathHelper.randomFloatClamp(rand, 0.0f, 0.5f);
		int hatColor = Color.HSBtoRGB(h, s, b) & 0xFFFFFF;
		GOTItemLeatherHat.setHatColor(hat, hatColor);
		if (rand.nextInt(3) == 0) {
			h = rand.nextFloat();
			s = MathHelper.randomFloatClamp(rand, 0.7f, 0.9f);
			b = MathHelper.randomFloatClamp(rand, 0.8f, 1.0f);
		} else {
			h = 0.0f;
			s = 0.0f;
			b = rand.nextFloat();
		}
		int featherColor = Color.HSBtoRGB(h, s, b) & 0xFFFFFF;
		GOTItemLeatherHat.setFeatherColor(hat, featherColor);
		setCurrentItemOrArmor(4, hat);
		return entityData;
	}

	@Override
	public void setupNPCName() {
		int i = rand.nextInt(4);
		switch (i) {
			case 0:
				familyInfo.setName(GOTNames.getWesterosName(rand, true));
				break;
			case 2:
				familyInfo.setName(GOTNames.getEssosName(rand, true));
				break;
			case 3:
				familyInfo.setName(GOTNames.getQarthName(rand, true));
				break;
			default:
				familyInfo.setName(GOTNames.getWildName(rand, true));
		}
	}
}