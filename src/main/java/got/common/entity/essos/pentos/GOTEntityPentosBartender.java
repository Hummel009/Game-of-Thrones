package got.common.entity.essos.pentos;

import got.common.database.GOTItems;
import got.common.database.GOTTradeEntries;
import got.common.database.GOTUnitTradeEntries;
import got.common.entity.other.iface.GOTBartender;
import got.common.entity.other.utils.GOTEntityUtils;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityPentosBartender extends GOTEntityPentosMan implements GOTBartender {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityPentosBartender(World world) {
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
		return GOTTradeEntries.C_BARTENDER_BUY;
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.C_BARTENDER_SELL;
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.PROSTITUTE_KEEPER;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setIdleItem(new ItemStack(GOTItems.gobletCopper));

		GOTEntityUtils.setupTurban(this, rand);

		return entityData;
	}

	@Override
	public void onUnitTrade(EntityPlayer entityPlayer) {
	}
}
