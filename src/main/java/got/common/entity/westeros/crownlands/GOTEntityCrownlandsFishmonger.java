package got.common.entity.westeros.crownlands;

import got.common.database.GOTItems;
import got.common.database.GOTTradeEntries;
import got.common.entity.other.iface.GOTTradeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityCrownlandsFishmonger extends GOTEntityCrownlandsMan implements GOTTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityCrownlandsFishmonger(World world) {
		super(world);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return isFriendlyAndAligned(entityplayer);
	}

	@Override
	public GOTTradeEntries getBuyPool() {
		return GOTTradeEntries.C_FISHMONGER_BUY;
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.C_FISHMONGER_SELL;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setIdleItem(new ItemStack(Items.fishing_rod));

		setCurrentItemOrArmor(4, new ItemStack(GOTItems.leatherHat));

		return entityData;
	}
}
