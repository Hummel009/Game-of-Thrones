package got.common.entity.essos.volantis;

import got.common.database.GOTItems;
import got.common.database.GOTTradeEntries;
import got.common.entity.other.iface.GOTSmith;
import got.common.entity.other.utils.GOTEntityUtils;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityVolantisBlacksmith extends GOTEntityVolantisMan implements GOTSmith {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityVolantisBlacksmith(World world) {
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
		return GOTTradeEntries.BLACKSMITH_SELLS;
	}

	@Override
	public GOTTradeEntries getBuysPool() {
		return GOTTradeEntries.BLACKSMITH_BUYS;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.blacksmithHammer));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		GOTEntityUtils.setupTurban(this, rand);

		return entityData;
	}
}
