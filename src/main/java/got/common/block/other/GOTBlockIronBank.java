package got.common.block.other;

import got.common.GOTCommonProxy;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GOTBlockIronBank extends GOTBlockForgeBase {

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
