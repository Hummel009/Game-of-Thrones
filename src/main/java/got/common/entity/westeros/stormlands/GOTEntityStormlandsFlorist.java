package got.common.entity.westeros.stormlands;

import got.common.database.GOTItems;
import got.common.database.GOTTradeEntries;
import got.common.entity.other.iface.GOTTradeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityStormlandsFlorist extends GOTEntityStormlandsMan implements GOTTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityStormlandsFlorist(World world) {
		super(world);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public GOTTradeEntries getSellsPool() {
		return GOTTradeEntries.FLORIST_SELLS;
	}

	@Override
	public GOTTradeEntries getBuysPool() {
		return GOTTradeEntries.FLORIST_BUYS;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setIdleItem(new ItemStack(Blocks.red_flower));

		setCurrentItemOrArmor(4, new ItemStack(GOTItems.leatherHat));

		return entityData;
	}
}
