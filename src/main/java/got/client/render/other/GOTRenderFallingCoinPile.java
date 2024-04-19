package got.client.render.other;

import got.common.block.other.GOTBlockTreasurePile;
import got.common.entity.other.GOTEntityFallingTreasure;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class GOTRenderFallingCoinPile extends Render {
	private static final RenderBlocks BLOCK_RENDERER = new RenderBlocks();

	public GOTRenderFallingCoinPile() {
		shadowSize = 0.5f;
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		GOTEntityFallingTreasure fallingCoin = (GOTEntityFallingTreasure) entity;
		World world = fallingCoin.worldObj;
		Block block = fallingCoin.theBlock;
		int meta = fallingCoin.theBlockMeta;
		int i = MathHelper.floor_double(fallingCoin.posX);
		int j = MathHelper.floor_double(fallingCoin.posY);
		int k = MathHelper.floor_double(fallingCoin.posZ);
		if (block != null && block != world.getBlock(i, j, k)) {
			GL11.glPushMatrix();
			GL11.glTranslatef((float) d, (float) d1, (float) d2);
			bindEntityTexture(fallingCoin);
			GL11.glDisable(2896);
			GOTBlockTreasurePile.setTreasureBlockBounds(block, meta);
			BLOCK_RENDERER.setRenderBoundsFromBlock(block);
			BLOCK_RENDERER.renderBlockSandFalling(block, world, i, j, k, meta);
			GL11.glEnable(2896);
			GL11.glPopMatrix();
		}
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return TextureMap.locationBlocksTexture;
	}
}