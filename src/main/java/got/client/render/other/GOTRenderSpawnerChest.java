package got.client.render.other;

import got.common.block.other.GOTBlockSpawnerChest;
import got.common.tileentity.GOTTileEntitySpawnerChest;
import net.minecraft.block.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.tileentity.TileEntity;

public class GOTRenderSpawnerChest extends TileEntitySpecialRenderer {
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		GOTTileEntitySpawnerChest chest = (GOTTileEntitySpawnerChest) tileentity;
		Block block = chest.getBlockType();
		if (block instanceof GOTBlockSpawnerChest) {
			GOTBlockSpawnerChest scBlock = (GOTBlockSpawnerChest) block;
			Block model = scBlock.getChestModel();
			if (model instanceof ITileEntityProvider) {
				ITileEntityProvider itep = (ITileEntityProvider) model;
				TileEntity modelTE = itep.createNewTileEntity(chest.getWorldObj(), 0);
				modelTE.setWorldObj(chest.getWorldObj());
				modelTE.xCoord = chest.xCoord;
				modelTE.yCoord = chest.yCoord;
				modelTE.zCoord = chest.zCoord;
				TileEntityRendererDispatcher.instance.getSpecialRenderer(modelTE).renderTileEntityAt(modelTE, d, d1, d2, f);
			}
		}
	}
}
