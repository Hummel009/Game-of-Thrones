package got.common.entity.essos.ibben;

import got.common.database.GOTItems;
import got.common.database.GOTTradeEntries;
import got.common.database.GOTUnitTradeEntries;
import got.common.entity.other.iface.GOTFarmer;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityIbbenFarmer extends GOTEntityIbbenMan implements GOTFarmer {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityIbbenFarmer(World world) {
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
		return GOTTradeEntries.C_FARMER_BUY;
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.C_FARMER_SELL;
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.IBBEN_FARMER;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_hoe));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(4, new ItemStack(GOTItems.leatherHat));

		return entityData;
	}
}