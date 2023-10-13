package got.common.block.other;

import java.util.Map;

import got.common.GOTCommonProxy;
import got.common.database.GOTItems;
import got.common.util.GOTItemStackMapImpl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GOTBlockIronBank extends GOTBlockForgeBase {
	public static Map<ItemStack, Integer> buy = new GOTItemStackMapImpl<>();
	public static Map<ItemStack, Integer> sell = new GOTItemStackMapImpl<>();

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

	public static void preInit() {
		int k = 1;
		for (int i = 0; i < 8; ++i) {
			buy.put(new ItemStack(GOTItems.coin, 1, i), k);
			sell.put(new ItemStack(GOTItems.coin, 1, i), k);
			k *= 4;
		}
	}
}
