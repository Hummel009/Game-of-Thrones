package got.common.entity.sothoryos.summer;

import got.common.database.GOTItems;
import got.common.database.GOTTradeEntries;
import got.common.entity.other.iface.GOTTradeable;
import got.common.entity.other.utils.GOTEntityUtils;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntitySummerGoldsmith extends GOTEntitySummerMan implements GOTTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntitySummerGoldsmith(World world) {
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
	public GOTTradeEntries getSellsPool() {
		return GOTTradeEntries.GOLDSMITH_SELLS;
	}

	@Override
	public GOTTradeEntries getBuysPool() {
		return GOTTradeEntries.GOLDSMITH_BUYS;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setIdleItem(new ItemStack(GOTItems.silverRing));

		GOTEntityUtils.setupTurban(this, rand);

		return entityData;
	}
}