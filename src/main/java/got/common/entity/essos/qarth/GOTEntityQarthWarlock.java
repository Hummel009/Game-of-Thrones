package got.common.entity.essos.qarth;

import got.common.database.GOTItems;
import got.common.database.GOTTradeEntries;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityQarthWarlock extends GOTEntityQarthTrader {
	public GOTEntityQarthWarlock(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return isFriendly(entityplayer);
	}

	@Override
	public GOTTradeEntries getBuyPool() {
		return GOTTradeEntries.C_ALCHEMIST_BUY;
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.C_BOMZH_SELL;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(4, null);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.skullStaff));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}