package got.common.entity.essos.ghiscar;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.database.GOTTradeEntries;
import got.common.entity.other.GOTTradeable;
import got.common.item.other.GOTItemRobes;
import got.common.item.other.GOTItemTurban;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.awt.*;
import java.util.Random;

public abstract class GOTEntityGhiscarTrader extends GOTEntityGhiscarMan implements GOTTradeable {
	protected GOTEntityGhiscarTrader(World world) {
		super(world);
		addTargetTasks(false);
	}

	private static ItemStack createTraderTurban(Random random) {
		ItemStack turban = new ItemStack(GOTItems.robesHelmet);
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

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return GOTLevelData.getData(entityplayer).getAlignment(getFaction()) >= 0.0f && isFriendly(entityplayer);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, GOTTradeEntries.TradeType type, ItemStack itemstack) {
		GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.trade);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(4, createTraderTurban(rand));
		return entityData;
	}
}