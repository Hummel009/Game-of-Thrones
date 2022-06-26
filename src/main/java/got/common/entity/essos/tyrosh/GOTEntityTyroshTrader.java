package got.common.entity.essos.tyrosh;

import java.awt.Color;
import java.util.Random;

import got.common.GOTLevelData;
import got.common.database.*;
import got.common.entity.other.*;
import got.common.item.other.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class GOTEntityTyroshTrader extends GOTEntityTyroshMan implements GOTTradeable {
	public GOTEntityTyroshTrader(World world) {
		super(world);
		canBeMarried = false;
		addTargetTasks(false);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return GOTLevelData.getData(entityplayer).getAlignment(getFaction()) >= 0.0f && isFriendly(entityplayer);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
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
		data = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(4, GOTEntityTyroshTrader.createTraderTurban(rand));
		return data;
	}

	public static ItemStack createTraderTurban(Random random) {
		ItemStack turban = new ItemStack(GOTRegistry.robesHelmet);
		if (random.nextInt(3) == 0) {
			GOTItemTurban.setHasOrnament(turban, true);
		}
		float h = random.nextFloat() * 360.0f;
		float s = MathHelper.randomFloatClamp(random, 0.6f, 0.8f);
		float b = MathHelper.randomFloatClamp(random, 0.5f, 0.75f);
		int turbanColor = Color.HSBtoRGB(h, s, b) & 0xFFFFFF;
		GOTItemRobes.setRobesColor(turban, turbanColor);
		return turban;
	}
}
