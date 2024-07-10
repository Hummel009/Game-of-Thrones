package got.common.entity.essos.yi_ti;

import got.common.database.GOTTradeEntries;
import got.common.entity.other.iface.GOTTradeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityYiTiMason extends GOTEntityYiTiMan implements GOTTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityYiTiMason(World world) {
		super(world);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public GOTTradeEntries getSellsPool() {
		return GOTTradeEntries.MASON_SELLS;
	}

	@Override
	public GOTTradeEntries getBuysPool() {
		return GOTTradeEntries.MASON_BUYS;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_pickaxe));
		npcItemsInv.setIdleItem(new ItemStack(Blocks.stone));

		return entityData;
	}
}