package got.common.entity.essos.ghiscar;

import got.common.database.GOTTradeEntries;
import got.common.entity.other.iface.GOTTradeable;
import got.common.entity.other.utils.GOTEntityUtils;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityGhiscarButcher extends GOTEntityGhiscarMan implements GOTTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityGhiscarButcher(World world) {
		super(world);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public GOTTradeEntries getSellsPool() {
		return GOTTradeEntries.BUTCHER_SELLS;
	}

	@Override
	public GOTTradeEntries getBuysPool() {
		return GOTTradeEntries.BUTCHER_BUYS;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setIdleItem(new ItemStack(Items.porkchop));

		GOTEntityUtils.setupTurban(this, rand);

		return entityData;
	}
}
