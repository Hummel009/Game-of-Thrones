package got.client.render.other;

import cpw.mods.fml.common.FMLLog;
import got.common.block.other.GOTBlockChest;
import got.common.block.other.GOTBlockSpawnerChest;
import got.common.tileentity.GOTTileEntityChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class GOTRenderChest extends TileEntitySpecialRenderer {
	public static Map<String, ResourceLocation> chestTextures = new HashMap<>();
	public static ModelChest chestModel = new ModelChest();
	public GOTTileEntityChest itemEntity = new GOTTileEntityChest();

	public static ResourceLocation getChestTexture(String s) {
		ResourceLocation r = chestTextures.get(s);
		if (r == null) {
			r = new ResourceLocation("got:textures/model/chest/" + s + ".png");
			chestTextures.put(s, r);
		}
		return r;
	}

	public void renderInvChest(Block block, int meta) {
		Block c;
		GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
		GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
		itemEntity.textureName = "";
		if (block instanceof GOTBlockChest) {
			itemEntity.textureName = ((GOTBlockChest) block).getChestTextureName();
		} else if (block instanceof GOTBlockSpawnerChest && (c = ((GOTBlockSpawnerChest) block).chestModel) instanceof GOTBlockChest) {
			itemEntity.textureName = ((GOTBlockChest) c).getChestTextureName();
		}
		renderTileEntityAt(itemEntity, 0.0, 0.0, 0.0, 0.0f);
		GL11.glEnable(32826);
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		int meta;
		GOTTileEntityChest chest = (GOTTileEntityChest) tileentity;
		if (!chest.hasWorldObj()) {
			meta = 0;
		} else {
			Block block = tileentity.getBlockType();
			meta = tileentity.getBlockMetadata();
			if (block instanceof BlockChest && meta == 0) {
				try {
					((BlockChest) block).func_149954_e(tileentity.getWorldObj(), tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
				} catch (ClassCastException e) {
					FMLLog.severe("Attempted to render a chest at %d,  %d, %d that was not a chest", tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
				}
				meta = tileentity.getBlockMetadata();
			}
		}
		GL11.glPushMatrix();
		GL11.glEnable(32826);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glTranslatef((float) d, (float) d1 + 1.0f, (float) d2 + 1.0f);
		GL11.glScalef(1.0f, -1.0f, -1.0f);
		GL11.glTranslatef(0.5f, 0.5f, 0.5f);
		float rot = 0.0f;
		if (meta == 2) {
			rot = 180.0f;
		}
		if (meta == 3) {
			rot = 0.0f;
		}
		if (meta == 4) {
			rot = 90.0f;
		}
		if (meta == 5) {
			rot = -90.0f;
		}
		GL11.glRotatef(rot, 0.0f, 1.0f, 0.0f);
		GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
		float lid = chest.prevLidAngle + (chest.lidAngle - chest.prevLidAngle) * f;
		lid = 1.0f - lid;
		lid = 1.0f - lid * lid * lid;
		chestModel.chestLid.rotateAngleX = -(lid * 3.1415927f / 2.0f);
		bindTexture(getChestTexture(chest.textureName));
		chestModel.renderAll();
		GL11.glDisable(32826);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	}
}
