package got.common.block.other;

import got.common.GOTCommonProxy;
import got.common.database.GOTItems;
import got.common.item.other.GOTItemCoin;
import got.common.util.GOTItemStackMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Map;

public class GOTBlockIronBank extends GOTBlockForgeBase {
	public static final Map<ItemStack, Integer> BUY = new GOTItemStackMap<>();
	public static final Map<ItemStack, Integer> SELL = new GOTItemStackMap<>();

	public static void preInit() {
		for (int i = 0; i < GOTItemCoin.VALUES.length; ++i) {
			BUY.put(new ItemStack(GOTItems.coin, 1, i), GOTItemCoin.VALUES[i]);
			SELL.put(new ItemStack(GOTItems.coin, 1, i), GOTItemCoin.VALUES[i]);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return null;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			GOTCommonProxy.sendClientsideGUI((EntityPlayerMP) player, 4, 0, 0, 0);
		}
		return true;
	}

	@Override
	public boolean useLargeSmoke() {
		return false;
	}
}