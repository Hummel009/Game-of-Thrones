package got.client.render.other;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import got.GOT;
import got.common.block.other.*;
import got.common.database.GOTBlocks;
import got.common.tileentity.GOTTileEntityBeacon;
import got.common.tileentity.GOTTileEntityChest;
import got.common.tileentity.GOTTileEntityCommandTable;
import got.common.tileentity.GOTTileEntityUnsmeltery;
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class GOTRenderBlocks implements ISimpleBlockRenderingHandler {
	private static final Random BLOCK_RAND = new Random();

	private final boolean renderInvIn3D;

	public GOTRenderBlocks(boolean flag) {
		renderInvIn3D = flag;
	}

	private static int getAO() {
		return Minecraft.getMinecraft().gameSettings.ambientOcclusion;
	}

	private static void setAO(int i) {
		Minecraft.getMinecraft().gameSettings.ambientOcclusion = i;
	}

	private static void renderClover(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks, int petalCount, boolean randomTranslation) {
		double scale = 0.5;
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, i, j, k));
		int l = block.colorMultiplier(world, i, j, k);
		float f = 1.0f;
		float f1 = (l >> 16 & 0xFF) / 255.0f;
		float f2 = (l >> 8 & 0xFF) / 255.0f;
		float f3 = (l & 0xFF) / 255.0f;
		if (EntityRenderer.anaglyphEnable) {
			float f4 = (f1 * 30.0f + f2 * 59.0f + f3 * 11.0f) / 100.0f;
			float f5 = (f1 * 30.0f + f2 * 70.0f) / 100.0f;
			float f6 = (f1 * 30.0f + f3 * 70.0f) / 100.0f;
			f1 = f4;
			f2 = f5;
			f3 = f6;
		}
		tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
		renderblocks.setOverrideBlockTexture(GOTBlockClover.stemIcon);
		double posX = i;
		double posZ = k;
		if (randomTranslation) {
			long seed = i * 3129871L ^ k * 116129781L ^ j;
			seed = seed * seed * 42317861L + seed * 11L;
			posX += ((seed >> 16 & 0xFL) / 15.0f - 0.5) * 0.5;
			posZ += ((seed >> 24 & 0xFL) / 15.0f - 0.5) * 0.5;
		}
		renderblocks.drawCrossedSquares(block.getIcon(2, 0), posX, j, posZ, (float) scale);
		renderblocks.clearOverrideBlockTexture();
		for (int petal = 0; petal < petalCount; ++petal) {
			float rotation = (float) petal / petalCount * 3.1415927f * 2.0f;
			IIcon icon = GOTBlockClover.petalIcon;
			double d = icon.getMinU();
			double d1 = icon.getMinV();
			double d2 = icon.getMaxU();
			double d3 = icon.getMaxV();
			double d4 = posX + 0.5;
			double d5 = j + 0.5 * scale + petal * 0.0025;
			double d6 = posZ + 0.5;
			Vec3[] vecs = {Vec3.createVectorHelper(0.5 * scale, 0.0, -0.5 * scale), Vec3.createVectorHelper(-0.5 * scale, 0.0, -0.5 * scale), Vec3.createVectorHelper(-0.5 * scale, 0.0, 0.5 * scale), Vec3.createVectorHelper(0.5 * scale, 0.0, 0.5 * scale)};
			for (Vec3 vec : vecs) {
				vec.rotateAroundY(rotation);
				vec.xCoord += d4;
				vec.yCoord += d5;
				vec.zCoord += d6;
			}
			tessellator.addVertexWithUV(vecs[0].xCoord, vecs[0].yCoord, vecs[0].zCoord, d, d1);
			tessellator.addVertexWithUV(vecs[1].xCoord, vecs[1].yCoord, vecs[1].zCoord, d, d3);
			tessellator.addVertexWithUV(vecs[2].xCoord, vecs[2].yCoord, vecs[2].zCoord, d2, d3);
			tessellator.addVertexWithUV(vecs[3].xCoord, vecs[3].yCoord, vecs[3].zCoord, d2, d1);
			tessellator.addVertexWithUV(vecs[3].xCoord, vecs[3].yCoord, vecs[3].zCoord, d2, d1);
			tessellator.addVertexWithUV(vecs[2].xCoord, vecs[2].yCoord, vecs[2].zCoord, d2, d3);
			tessellator.addVertexWithUV(vecs[1].xCoord, vecs[1].yCoord, vecs[1].zCoord, d, d3);
			tessellator.addVertexWithUV(vecs[0].xCoord, vecs[0].yCoord, vecs[0].zCoord, d, d1);
		}
	}

	public static void renderEntityPlate(Block block, RenderBlocks renderblocks) {
		renderblocks.renderAllFaces = true;
		renderStandardInvBlock(renderblocks, block, 0.1875, 0.0, 0.1875, 0.8125, 0.0625, 0.8125);
		renderStandardInvBlock(renderblocks, block, 0.125, 0.0625, 0.125, 0.875, 0.125, 0.875);
		renderblocks.renderAllFaces = false;
	}

	private static void renderGrass(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks, boolean randomTranslation) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, i, j, k));
		int meta = world.getBlockMetadata(i, j, k);
		int l = block.colorMultiplier(world, i, j, k);
		float f = 1.0f;
		float f1 = (l >> 16 & 0xFF) / 255.0f;
		float f2 = (l >> 8 & 0xFF) / 255.0f;
		float f3 = (l & 0xFF) / 255.0f;
		if (EntityRenderer.anaglyphEnable) {
			float f4 = (f1 * 30.0f + f2 * 59.0f + f3 * 11.0f) / 100.0f;
			float f5 = (f1 * 30.0f + f2 * 70.0f) / 100.0f;
			float f6 = (f1 * 30.0f + f3 * 70.0f) / 100.0f;
			f1 = f4;
			f2 = f5;
			f3 = f6;
		}
		tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
		double posX = i;
		double posY = j;
		double posZ = k;
		if (randomTranslation) {
			long seed = i * 3129871L ^ k * 116129781L ^ j;
			seed = seed * seed * 42317861L + seed * 11L;
			posX += ((seed >> 16 & 0xFL) / 15.0f - 0.5) * 0.5;
			posY += ((seed >> 20 & 0xFL) / 15.0f - 1.0) * 0.2;
			posZ += ((seed >> 24 & 0xFL) / 15.0f - 0.5) * 0.5;
		}
		renderblocks.drawCrossedSquares(block.getIcon(2, meta), posX, posY, posZ, 1.0f);
		renderblocks.clearOverrideBlockTexture();
		if (block == GOTBlocks.tallGrass && meta >= 0 && meta < GOTBlockTallGrass.grassOverlay.length && GOTBlockTallGrass.grassOverlay[meta]) {
			tessellator.setColorOpaque_F(1.0f, 1.0f, 1.0f);
			renderblocks.drawCrossedSquares(block.getIcon(-1, meta), posX, posY, posZ, 1.0f);
			renderblocks.clearOverrideBlockTexture();
		}
	}

	private static void renderInvClover(Block block, RenderBlocks renderblocks, int petalCount) {
		GL11.glDisable(2896);
		double scale = 1.0;
		Tessellator tessellator = Tessellator.instance;
		int l = block.getRenderColor(0);
		float f = 1.0f;
		float f1 = (l >> 16 & 0xFF) / 255.0f;
		float f2 = (l >> 8 & 0xFF) / 255.0f;
		float f3 = (l & 0xFF) / 255.0f;
		if (EntityRenderer.anaglyphEnable) {
			float f4 = (f1 * 30.0f + f2 * 59.0f + f3 * 11.0f) / 100.0f;
			float f5 = (f1 * 30.0f + f2 * 70.0f) / 100.0f;
			float f6 = (f1 * 30.0f + f3 * 70.0f) / 100.0f;
			f1 = f4;
			f2 = f5;
			f3 = f6;
		}
		tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
		renderblocks.setOverrideBlockTexture(GOTBlockClover.stemIcon);
		tessellator.startDrawingQuads();
		renderblocks.drawCrossedSquares(block.getIcon(2, 0), -scale * 0.5, -scale * 0.5, -scale * 0.5, (float) scale);
		tessellator.draw();
		renderblocks.clearOverrideBlockTexture();
		for (int petal = 0; petal < petalCount; ++petal) {
			float rotation = (float) petal / petalCount * 3.1415927f * 2.0f;
			IIcon icon = GOTBlockClover.petalIcon;
			double d = icon.getMinU();
			double d1 = icon.getMinV();
			double d2 = icon.getMaxU();
			double d3 = icon.getMaxV();
			double d4 = 0.0;
			double d5 = petal * 0.0025;
			double d6 = 0.0;
			Vec3[] vecs = {Vec3.createVectorHelper(0.5 * scale, 0.0, -0.5 * scale), Vec3.createVectorHelper(-0.5 * scale, 0.0, -0.5 * scale), Vec3.createVectorHelper(-0.5 * scale, 0.0, 0.5 * scale), Vec3.createVectorHelper(0.5 * scale, 0.0, 0.5 * scale)};
			for (Vec3 vec : vecs) {
				vec.rotateAroundY(rotation);
				vec.xCoord += d4;
				vec.yCoord += d5;
				vec.zCoord += d6;
			}
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(vecs[0].xCoord, vecs[0].yCoord, vecs[0].zCoord, d, d1);
			tessellator.addVertexWithUV(vecs[1].xCoord, vecs[1].yCoord, vecs[1].zCoord, d, d3);
			tessellator.addVertexWithUV(vecs[2].xCoord, vecs[2].yCoord, vecs[2].zCoord, d2, d3);
			tessellator.addVertexWithUV(vecs[3].xCoord, vecs[3].yCoord, vecs[3].zCoord, d2, d1);
			tessellator.addVertexWithUV(vecs[3].xCoord, vecs[3].yCoord, vecs[3].zCoord, d2, d1);
			tessellator.addVertexWithUV(vecs[2].xCoord, vecs[2].yCoord, vecs[2].zCoord, d2, d3);
			tessellator.addVertexWithUV(vecs[1].xCoord, vecs[1].yCoord, vecs[1].zCoord, d, d3);
			tessellator.addVertexWithUV(vecs[0].xCoord, vecs[0].yCoord, vecs[0].zCoord, d, d1);
			tessellator.draw();
		}
		GL11.glEnable(2896);
	}

	private static void renderInvPlantain(Block block, RenderBlocks renderblocks, int petalCount) {
		GL11.glDisable(2896);
		double scale = 1.0;
		Tessellator tessellator = Tessellator.instance;
		int l = block.getRenderColor(0);
		float f = 1.0f;
		float f1 = (l >> 16 & 0xFF) / 255.0f;
		float f2 = (l >> 8 & 0xFF) / 255.0f;
		float f3 = (l & 0xFF) / 255.0f;
		if (EntityRenderer.anaglyphEnable) {
			float f4 = (f1 * 30.0f + f2 * 59.0f + f3 * 11.0f) / 100.0f;
			float f5 = (f1 * 30.0f + f2 * 70.0f) / 100.0f;
			float f6 = (f1 * 30.0f + f3 * 70.0f) / 100.0f;
			f1 = f4;
			f2 = f5;
			f3 = f6;
		}
		tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
		renderblocks.setOverrideBlockTexture(GOTBlockPlantain.stemIcon);
		tessellator.startDrawingQuads();
		renderblocks.drawCrossedSquares(block.getIcon(2, 0), -scale * 0.5, -scale * 0.5, -scale * 0.5, (float) scale);
		tessellator.draw();
		renderblocks.clearOverrideBlockTexture();
		for (int petal = 0; petal < petalCount; ++petal) {
			float rotation = (float) petal / petalCount * 3.1415927f * 2.0f;
			IIcon icon = GOTBlockPlantain.petalIcon;
			double d = icon.getMinU();
			double d1 = icon.getMinV();
			double d2 = icon.getMaxU();
			double d3 = icon.getMaxV();
			double d4 = 0.0;
			double d5 = petal * 0.0025;
			double d6 = 0.0;
			Vec3[] vecs = {Vec3.createVectorHelper(0.5 * scale, 0.0, -0.5 * scale), Vec3.createVectorHelper(-0.5 * scale, 0.0, -0.5 * scale), Vec3.createVectorHelper(-0.5 * scale, 0.0, 0.5 * scale), Vec3.createVectorHelper(0.5 * scale, 0.0, 0.5 * scale)};
			for (Vec3 vec : vecs) {
				vec.rotateAroundY(rotation);
				vec.xCoord += d4;
				vec.yCoord += d5;
				vec.zCoord += d6;
			}
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(vecs[0].xCoord, vecs[0].yCoord, vecs[0].zCoord, d, d1);
			tessellator.addVertexWithUV(vecs[1].xCoord, vecs[1].yCoord, vecs[1].zCoord, d, d3);
			tessellator.addVertexWithUV(vecs[2].xCoord, vecs[2].yCoord, vecs[2].zCoord, d2, d3);
			tessellator.addVertexWithUV(vecs[3].xCoord, vecs[3].yCoord, vecs[3].zCoord, d2, d1);
			tessellator.addVertexWithUV(vecs[3].xCoord, vecs[3].yCoord, vecs[3].zCoord, d2, d1);
			tessellator.addVertexWithUV(vecs[2].xCoord, vecs[2].yCoord, vecs[2].zCoord, d2, d3);
			tessellator.addVertexWithUV(vecs[1].xCoord, vecs[1].yCoord, vecs[1].zCoord, d, d3);
			tessellator.addVertexWithUV(vecs[0].xCoord, vecs[0].yCoord, vecs[0].zCoord, d, d1);
			tessellator.draw();
		}
		GL11.glEnable(2896);
	}

	private static void renderPlantain(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks, int petalCount, boolean randomTranslation) {
		double scale = 0.5;
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, i, j, k));
		int l = block.colorMultiplier(world, i, j, k);
		float f = 1.0f;
		float f1 = (l >> 16 & 0xFF) / 255.0f;
		float f2 = (l >> 8 & 0xFF) / 255.0f;
		float f3 = (l & 0xFF) / 255.0f;
		if (EntityRenderer.anaglyphEnable) {
			float f4 = (f1 * 30.0f + f2 * 59.0f + f3 * 11.0f) / 100.0f;
			float f5 = (f1 * 30.0f + f2 * 70.0f) / 100.0f;
			float f6 = (f1 * 30.0f + f3 * 70.0f) / 100.0f;
			f1 = f4;
			f2 = f5;
			f3 = f6;
		}
		tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
		renderblocks.setOverrideBlockTexture(GOTBlockPlantain.stemIcon);
		double posX = i;
		double posZ = k;
		if (randomTranslation) {
			long seed = i * 3129871L ^ k * 116129781L ^ j;
			seed = seed * seed * 42317861L + seed * 11L;
			posX += ((seed >> 16 & 0xFL) / 15.0f - 0.5) * 0.5;
			posZ += ((seed >> 24 & 0xFL) / 15.0f - 0.5) * 0.5;
		}
		renderblocks.drawCrossedSquares(block.getIcon(2, 0), posX, j, posZ, (float) scale);
		renderblocks.clearOverrideBlockTexture();
		for (int petal = 0; petal < petalCount; ++petal) {
			float rotation = (float) petal / petalCount * 3.1415927f * 2.0f;
			IIcon icon = GOTBlockPlantain.petalIcon;
			double d = icon.getMinU();
			double d1 = icon.getMinV();
			double d2 = icon.getMaxU();
			double d3 = icon.getMaxV();
			double d4 = posX + 0.5;
			double d5 = j + 0.5 * scale + petal * 0.0025;
			double d6 = posZ + 0.5;
			Vec3[] vecs = {Vec3.createVectorHelper(0.5 * scale, 0.0, -0.5 * scale), Vec3.createVectorHelper(-0.5 * scale, 0.0, -0.5 * scale), Vec3.createVectorHelper(-0.5 * scale, 0.0, 0.5 * scale), Vec3.createVectorHelper(0.5 * scale, 0.0, 0.5 * scale)};
			for (Vec3 vec : vecs) {
				vec.rotateAroundY(rotation);
				vec.xCoord += d4;
				vec.yCoord += d5;
				vec.zCoord += d6;
			}
			tessellator.addVertexWithUV(vecs[0].xCoord, vecs[0].yCoord, vecs[0].zCoord, d, d1);
			tessellator.addVertexWithUV(vecs[1].xCoord, vecs[1].yCoord, vecs[1].zCoord, d, d3);
			tessellator.addVertexWithUV(vecs[2].xCoord, vecs[2].yCoord, vecs[2].zCoord, d2, d3);
			tessellator.addVertexWithUV(vecs[3].xCoord, vecs[3].yCoord, vecs[3].zCoord, d2, d1);
			tessellator.addVertexWithUV(vecs[3].xCoord, vecs[3].yCoord, vecs[3].zCoord, d2, d1);
			tessellator.addVertexWithUV(vecs[2].xCoord, vecs[2].yCoord, vecs[2].zCoord, d2, d3);
			tessellator.addVertexWithUV(vecs[1].xCoord, vecs[1].yCoord, vecs[1].zCoord, d, d3);
			tessellator.addVertexWithUV(vecs[0].xCoord, vecs[0].yCoord, vecs[0].zCoord, d, d1);
		}
	}

	public static void renderStandardInvBlock(RenderBlocks renderblocks, Block block, double d, double d1, double d2, double d3, double d4, double d5) {
		renderStandardInvBlock(renderblocks, block, d, d1, d2, d3, d4, d5, 0);
	}

	private static void renderStandardInvBlock(RenderBlocks renderblocks, Block block, double d, double d1, double d2, double d3, double d4, double d5, int metadata) {
		Tessellator tessellator = Tessellator.instance;
		renderblocks.setRenderBounds(d, d1, d2, d3, d4, d5);
		GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0f, -1.0f, 0.0f);
		renderblocks.renderFaceYNeg(block, 0.0, 0.0, 0.0, renderblocks.getBlockIconFromSideAndMetadata(block, 0, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0f, 1.0f, 0.0f);
		renderblocks.renderFaceYPos(block, 0.0, 0.0, 0.0, renderblocks.getBlockIconFromSideAndMetadata(block, 1, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0f, 0.0f, -1.0f);
		renderblocks.renderFaceZNeg(block, 0.0, 0.0, 0.0, renderblocks.getBlockIconFromSideAndMetadata(block, 2, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0f, 0.0f, 1.0f);
		renderblocks.renderFaceZPos(block, 0.0, 0.0, 0.0, renderblocks.getBlockIconFromSideAndMetadata(block, 3, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0f, 0.0f, 0.0f);
		renderblocks.renderFaceXNeg(block, 0.0, 0.0, 0.0, renderblocks.getBlockIconFromSideAndMetadata(block, 4, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0f, 0.0f, 0.0f);
		renderblocks.renderFaceXPos(block, 0.0, 0.0, 0.0, renderblocks.getBlockIconFromSideAndMetadata(block, 5, metadata));
		tessellator.draw();
		GL11.glTranslatef(0.5f, 0.5f, 0.5f);
	}

	private static void renderStandardInvBlock(RenderBlocks renderblocks, Block block, int meta) {
		block.setBlockBoundsForItemRender();
		renderblocks.setRenderBoundsFromBlock(block);
		double d = renderblocks.renderMinX;
		double d1 = renderblocks.renderMinY;
		double d2 = renderblocks.renderMinZ;
		double d3 = renderblocks.renderMaxX;
		double d4 = renderblocks.renderMaxY;
		double d5 = renderblocks.renderMaxZ;
		renderStandardInvBlock(renderblocks, block, d, d1, d2, d3, d4, d5, meta);
	}

	@Override
	public int getRenderId() {
		return 0;
	}

	private void renderBarrel(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
		int ao = getAO();
		setAO(0);
		renderblocks.renderAllFaces = true;
		renderblocks.setRenderBounds(0.15625, 0.0625, 0.15625, 0.84375, 0.75, 0.84375);
		renderblocks.renderStandardBlock(block, i, j, k);
		for (float f : new float[]{0.25f, 0.5f}) {
			renderblocks.setRenderBounds(0.125, f, 0.125, 0.875, f + 0.0625f, 0.875);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		for (float f : new float[]{0.0f, 0.6875f}) {
			renderblocks.setRenderBounds(0.125, f, 0.125, 0.25, f + 0.125f, 0.875);
			renderblocks.renderStandardBlock(block, i, j, k);
			renderblocks.setRenderBounds(0.75, f, 0.125, 0.875, f + 0.125f, 0.875);
			renderblocks.renderStandardBlock(block, i, j, k);
			renderblocks.setRenderBounds(0.25, f, 0.125, 0.75, f + 0.125f, 0.25);
			renderblocks.renderStandardBlock(block, i, j, k);
			renderblocks.setRenderBounds(0.25, f, 0.75, 0.75, f + 0.125f, 0.875);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		renderblocks.setOverrideBlockTexture(block.getBlockTextureFromSide(-1));
		int meta = world.getBlockMetadata(i, j, k);
		switch (meta) {
			case 2:
				renderblocks.setRenderBounds(0.4375, 0.25, 0.0, 0.5625, 0.375, 0.125);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0.46875, 0.125, 0.0, 0.53125, 0.25, 0.0625);
				renderblocks.renderStandardBlock(block, i, j, k);
				break;
			case 3:
				renderblocks.setRenderBounds(0.4375, 0.25, 0.875, 0.5625, 0.375, 1.0);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0.46875, 0.125, 0.9375, 0.53125, 0.25, 1.0);
				renderblocks.renderStandardBlock(block, i, j, k);
				break;
			case 4:
				renderblocks.setRenderBounds(0.0, 0.25, 0.4375, 0.125, 0.375, 0.5625);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0.0, 0.125, 0.46875, 0.0625, 0.25, 0.53125);
				renderblocks.renderStandardBlock(block, i, j, k);
				break;
			case 5:
				renderblocks.setRenderBounds(0.875, 0.25, 0.4375, 1.0, 0.375, 0.5625);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0.9375, 0.125, 0.46875, 1.0, 0.25, 0.53125);
				renderblocks.renderStandardBlock(block, i, j, k);
				break;
			default:
				break;
		}
		renderblocks.clearOverrideBlockTexture();
		renderblocks.renderAllFaces = false;
		setAO(ao);
	}

	private void renderBeacon(IBlockAccess world, int i, int j, int k, RenderBlocks renderblocks) {
		if (GOTBlockBeacon.isFullyLit(world, i, j, k)) {
			renderblocks.renderBlockFire(Blocks.fire, i, j, k);
		}
	}

	private void renderBeam(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
		int meta = world.getBlockMetadata(i, j, k);
		int dir = meta & 0xC;
		switch (dir) {
			case 0:
				renderblocks.uvRotateEast = 3;
				renderblocks.uvRotateNorth = 3;
				break;
			case 4:
				renderblocks.uvRotateEast = 1;
				renderblocks.uvRotateWest = 2;
				renderblocks.uvRotateTop = 2;
				renderblocks.uvRotateBottom = 1;
				break;
			case 8:
				renderblocks.uvRotateSouth = 1;
				renderblocks.uvRotateNorth = 2;
				break;
			default:
				break;
		}
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.uvRotateSouth = 0;
		renderblocks.uvRotateEast = 0;
		renderblocks.uvRotateWest = 0;
		renderblocks.uvRotateNorth = 0;
		renderblocks.uvRotateTop = 0;
		renderblocks.uvRotateBottom = 0;
	}

	private void renderBirdCage(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
		int ao = getAO();
		setAO(0);
		renderblocks.renderAllFaces = true;
		int meta = world.getBlockMetadata(i, j, k);
		float d = 0.001f;
		if (!GOTBlockBirdCage.isSameBirdCage(world, i, j, k, i - 1, j, k)) {
			renderblocks.setRenderBounds(0.0, 0.0625, 0.0, 0.0f + d, 1.0, 1.0);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		if (!GOTBlockBirdCage.isSameBirdCage(world, i, j, k, i + 1, j, k)) {
			renderblocks.setRenderBounds(1.0f - d, 0.0625, 0.0, 1.0, 1.0, 1.0);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		if (!GOTBlockBirdCage.isSameBirdCage(world, i, j, k, i, j, k - 1)) {
			renderblocks.setRenderBounds(0.0, 0.0625, 0.0, 1.0, 1.0, 0.0f + d);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		if (!GOTBlockBirdCage.isSameBirdCage(world, i, j, k, i, j, k + 1)) {
			renderblocks.setRenderBounds(0.0, 0.0625, 1.0f - d, 1.0, 1.0, 1.0);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		renderblocks.setRenderBounds(0.0, 1.0f - d, 0.0, 1.0, 1.0, 1.0);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setOverrideBlockTexture(block.getIcon(-1, meta));
		renderblocks.setRenderBounds(0.0, 0.0, 0.0, 1.0, 0.0625, 1.0);
		renderblocks.renderStandardBlock(block, i, j, k);
		Block above = world.getBlock(i, j + 1, k);
		above.setBlockBoundsBasedOnState(world, i, j + 1, k);
		if (!above.getMaterial().isSolid() || above.getBlockBoundsMinY() > 0.0 || !above.getMaterial().isOpaque() && above.getRenderType() == 0) {
			renderblocks.setRenderBounds(0.375, 1.0, 0.375, 0.625, 1.0625, 0.625);
			renderblocks.renderStandardBlock(block, i, j, k);
			renderblocks.setRenderBounds(0.46875, 1.0625, 0.46875, 0.53125, 1.1875, 0.53125);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		renderblocks.clearOverrideBlockTexture();
		renderblocks.renderAllFaces = false;
		setAO(ao);
	}

	private void renderBlockRandomRotated(int i, int j, int k, Block block, RenderBlocks renderblocks, boolean rotateSides) {
		int[] sides = new int[6];
		for (int l = 0; l < sides.length; ++l) {
			int hash = i * 234890405 ^ k * 37383934 ^ j;
			BLOCK_RAND.setSeed(hash + l * 285502);
			BLOCK_RAND.setSeed(BLOCK_RAND.nextLong());
			sides[l] = BLOCK_RAND.nextInt(4);
		}
		if (rotateSides) {
			renderblocks.uvRotateEast = sides[0];
			renderblocks.uvRotateWest = sides[1];
			renderblocks.uvRotateSouth = sides[2];
			renderblocks.uvRotateNorth = sides[3];
		}
		renderblocks.uvRotateTop = sides[4];
		renderblocks.uvRotateBottom = sides[5];
		renderblocks.renderStandardBlock(block, i, j, k);
		if (rotateSides) {
			renderblocks.uvRotateEast = 0;
			renderblocks.uvRotateWest = 0;
			renderblocks.uvRotateSouth = 0;
			renderblocks.uvRotateNorth = 0;
		}
		renderblocks.uvRotateTop = 0;
		renderblocks.uvRotateBottom = 0;
	}

	private void renderBomb(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
		int ao = getAO();
		setAO(0);
		renderblocks.renderAllFaces = true;
		renderblocks.setRenderBounds(0.125, 0.1875, 0.125, 0.875, 0.9375, 0.875);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.375, 0.9375, 0.375, 0.625, 1.0, 0.625);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.25, 0.9375, 0.375, 0.3125, 1.0, 0.4375);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.25, 0.9375, 0.5625, 0.3125, 1.0, 0.625);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.6875, 0.9375, 0.375, 0.75, 1.0, 0.4375);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.6875, 0.9375, 0.5625, 0.75, 1.0, 0.625);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.375, 0.9375, 0.25, 0.4375, 1.0, 0.3125);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.5625, 0.9375, 0.25, 0.625, 1.0, 0.3125);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.375, 0.9375, 0.6875, 0.4375, 1.0, 0.75);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.5625, 0.9375, 0.6875, 0.625, 1.0, 0.75);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.125, 0.0, 0.25, 0.1875, 0.1875, 0.75);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.8125, 0.0, 0.25, 0.875, 0.1875, 0.75);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.25, 0.0, 0.125, 0.75, 0.1875, 0.1875);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.25, 0.0, 0.8125, 0.75, 0.1875, 0.875);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setOverrideBlockTexture(block.getIcon(-1, world.getBlockMetadata(i, j, k)));
		renderblocks.setRenderBounds(0.0, 0.625, 0.3125, 0.0625, 0.6875, 0.6875);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.0625, 0.625, 0.3125, 0.125, 0.6875, 0.375);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.0625, 0.625, 0.625, 0.125, 0.6875, 0.6875);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.9375, 0.625, 0.3125, 1.0, 0.6875, 0.6875);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.875, 0.625, 0.3125, 0.9375, 0.6875, 0.375);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.875, 0.625, 0.625, 0.9375, 0.6875, 0.6875);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.3125, 0.625, 0.0, 0.6875, 0.6875, 0.0625);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.3125, 0.625, 0.0625, 0.375, 0.6875, 0.125);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.625, 0.625, 0.0625, 0.6875, 0.6875, 0.125);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.3125, 0.625, 0.9375, 0.6875, 0.6875, 1.0);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.3125, 0.625, 0.875, 0.375, 0.6875, 0.9375);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.625, 0.625, 0.875, 0.6875, 0.6875, 0.9375);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.clearOverrideBlockTexture();
		renderblocks.renderAllFaces = false;
		setAO(ao);
	}

	private void renderButterflyJar(int i, int j, int k, Block block, RenderBlocks renderblocks) {
		int ao = getAO();
		setAO(0);
		renderblocks.renderAllFaces = true;
		renderblocks.setRenderBounds(0.1875, 0.0, 0.1875, 0.8125, 0.5625, 0.8125);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.1875, 0.0, 0.1875, 0.8125, 0.0625, 0.8125);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.25, 0.5625, 0.25, 0.75, 0.6875, 0.75);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setOverrideBlockTexture(block.getIcon(-1, 0));
		renderblocks.setRenderBounds(0.1875, 0.6875, 0.1875, 0.8125, 0.75, 0.8125);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.clearOverrideBlockTexture();
		renderblocks.renderAllFaces = false;
		setAO(ao);
	}

	private void renderCommandTable(int i, int j, int k, Block block, RenderBlocks renderblocks) {
		int ao = getAO();
		setAO(0);
		renderblocks.renderAllFaces = true;
		renderblocks.setRenderBoundsFromBlock(block);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setOverrideBlockTexture(Blocks.planks.getIcon(0, 0));
		renderblocks.setRenderBounds(-0.5, 1.0, -0.5, 0.5, 1.0625, 0.5);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(-0.5, 1.0, 0.5, 0.5, 1.0625, 1.5);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.5, 1.0, -0.5, 1.5, 1.0625, 0.5);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.5, 1.0, 0.5, 1.5, 1.0625, 1.5);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.clearOverrideBlockTexture();
		renderblocks.renderAllFaces = false;
		setAO(ao);
	}

	private void renderCoral(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
		renderblocks.renderStandardBlock(block, i, j, k);
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, i, j + 1, k));
		tessellator.setColorOpaque_F(1.0f, 1.0f, 1.0f);
		IIcon icon = ((GOTBlockCoralReef) block).getRandomPlantIcon(i, j, k);
		renderblocks.drawCrossedSquares(icon, i, j + 1, k, 1.0f);
	}

	private boolean renderDoor(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
		Tessellator tessellator = Tessellator.instance;
		int meta = world.getBlockMetadata(i, j, k);
		boolean topDoor = (meta & 8) != 0;
		if (world.getBlock(i, topDoor ? j - 1 : j + 1, k) != block) {
			return false;
		}
		float f = 0.5f;
		float f1 = 1.0f;
		float f2 = 0.8f;
		float f3 = 0.6f;
		int light = block.getMixedBrightnessForBlock(world, i, j, k);
		if (!topDoor || world.getBlock(i, j - 1, k) != block) {
			tessellator.setBrightness(renderblocks.renderMinY > 0.0 ? light : block.getMixedBrightnessForBlock(world, i, j - 1, k));
			tessellator.setColorOpaque_F(f, f, f);
			renderblocks.renderFaceYNeg(block, i, j, k, renderblocks.getBlockIcon(block, world, i, j, k, 0));
		}
		if (topDoor || world.getBlock(i, j + 1, k) != block) {
			tessellator.setBrightness(renderblocks.renderMaxY < 1.0 ? light : block.getMixedBrightnessForBlock(world, i, j + 1, k));
			tessellator.setColorOpaque_F(f1, f1, f1);
			renderblocks.renderFaceYPos(block, i, j, k, renderblocks.getBlockIcon(block, world, i, j, k, 1));
		}
		tessellator.setBrightness(renderblocks.renderMinZ > 0.0 ? light : block.getMixedBrightnessForBlock(world, i, j, k - 1));
		tessellator.setColorOpaque_F(f2, f2, f2);
		IIcon iicon = renderblocks.getBlockIcon(block, world, i, j, k, 2);
		renderblocks.renderFaceZNeg(block, i, j, k, iicon);
		renderblocks.flipTexture = false;
		tessellator.setBrightness(renderblocks.renderMaxZ < 1.0 ? light : block.getMixedBrightnessForBlock(world, i, j, k + 1));
		tessellator.setColorOpaque_F(f2, f2, f2);
		iicon = renderblocks.getBlockIcon(block, world, i, j, k, 3);
		renderblocks.renderFaceZPos(block, i, j, k, iicon);
		renderblocks.flipTexture = false;
		tessellator.setBrightness(renderblocks.renderMinX > 0.0 ? light : block.getMixedBrightnessForBlock(world, i - 1, j, k));
		tessellator.setColorOpaque_F(f3, f3, f3);
		iicon = renderblocks.getBlockIcon(block, world, i, j, k, 4);
		renderblocks.renderFaceXNeg(block, i, j, k, iicon);
		renderblocks.flipTexture = false;
		tessellator.setBrightness(renderblocks.renderMaxX < 1.0 ? light : block.getMixedBrightnessForBlock(world, i + 1, j, k));
		tessellator.setColorOpaque_F(f3, f3, f3);
		iicon = renderblocks.getBlockIcon(block, world, i, j, k, 5);
		renderblocks.renderFaceXPos(block, i, j, k, iicon);
		renderblocks.flipTexture = false;
		return true;
	}

	private void renderDoublePlant(IBlockAccess world, int i, int j, int k, BlockDoublePlant block, RenderBlocks renderblocks) {
		int plantType;
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, i, j, k));
		int color = block.colorMultiplier(world, i, j, k);
		float r = (color >> 16 & 0xFF) / 255.0f;
		float g = (color >> 8 & 0xFF) / 255.0f;
		float b = (color & 0xFF) / 255.0f;
		if (EntityRenderer.anaglyphEnable) {
			float f3 = (r * 30.0f + g * 59.0f + b * 11.0f) / 100.0f;
			float f4 = (r * 30.0f + g * 70.0f) / 100.0f;
			float f5 = (r * 30.0f + b * 70.0f) / 100.0f;
			r = f3;
			g = f4;
			b = f5;
		}
		tessellator.setColorOpaque_F(r, g, b);
		double d = i;
		double d2 = k;
		long seed = i * 3129871L ^ k * 116129781L;
		seed = seed * seed * 42317861L + seed * 11L;
		d += ((seed >> 16 & 0xFL) / 15.0f - 0.5) * 0.3;
		d2 += ((seed >> 24 & 0xFL) / 15.0f - 0.5) * 0.3;
		int meta = world.getBlockMetadata(i, j, k);
		boolean isTop = BlockDoublePlant.func_149887_c(meta);
		if (isTop) {
			if (world.getBlock(i, j - 1, k) != block) {
				return;
			}
			plantType = BlockDoublePlant.func_149890_d(world.getBlockMetadata(i, j - 1, k));
		} else {
			plantType = BlockDoublePlant.func_149890_d(meta);
		}
		IIcon icon = block.func_149888_a(isTop, plantType);
		renderblocks.drawCrossedSquares(icon, d, j, d2, 1.0f);
	}

	private void renderDoubleTorch(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
		block.setBlockBoundsBasedOnState(world, i, j, k);
		renderblocks.setRenderBoundsFromBlock(block);
		renderblocks.renderStandardBlock(block, i, j, k);
	}

	private void renderFallenLeaves(IBlockAccess world, int i, int j, int k, Block block, int[] minMaxLeaves, int[] minMaxXSize, int[] minMaxZSize, float shade) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, i, j, k));
		world.getBlockMetadata(i, j, k);
		int color = block.colorMultiplier(world, i, j, k);
		float r = (color >> 16 & 0xFF) / 255.0f;
		float g = (color >> 8 & 0xFF) / 255.0f;
		float b = (color & 0xFF) / 255.0f;
		r *= shade;
		g *= shade;
		b *= shade;
		if (EntityRenderer.anaglyphEnable) {
			r = (r * 30.0f + g * 59.0f + b * 11.0f) / 100.0f;
			g = (r * 30.0f + g * 70.0f) / 100.0f;
			b = (r * 30.0f + b * 70.0f) / 100.0f;
		}
		tessellator.setColorOpaque_F(r, g, b);
		long seed = i * 237690503L ^ k * 2689286L ^ j;
		seed = seed * seed * 1732965593L + seed * 673L;
		BLOCK_RAND.setSeed(seed);
		IIcon icon = block.getIcon(world, i, j, k, 0);
		int leaves = MathHelper.getRandomIntegerInRange(BLOCK_RAND, minMaxLeaves[0], minMaxLeaves[1]);
		for (int l = 0; l < leaves; ++l) {
			double posX = i + BLOCK_RAND.nextFloat();
			double posZ = k + BLOCK_RAND.nextFloat();
			double posY = j + 0.01f + (float) l / leaves * 0.1f;
			float rotation = BLOCK_RAND.nextFloat() * 3.1415927f * 2.0f;
			int xSize = MathHelper.getRandomIntegerInRange(BLOCK_RAND, minMaxXSize[0], minMaxXSize[1]);
			int zSize = MathHelper.getRandomIntegerInRange(BLOCK_RAND, minMaxZSize[0], minMaxZSize[1]);
			double xSizeD = xSize / 16.0;
			double zSizeD = zSize / 16.0;
			int intMinU = MathHelper.getRandomIntegerInRange(BLOCK_RAND, 0, 16 - xSize);
			int intMinV = MathHelper.getRandomIntegerInRange(BLOCK_RAND, 0, 16 - zSize);
			double minU = icon.getInterpolatedU(intMinU);
			double minV = icon.getInterpolatedV(intMinV);
			double maxU = icon.getInterpolatedU(intMinU + xSize);
			double maxV = icon.getInterpolatedV(intMinV + zSize);
			double x2 = xSizeD / 2.0;
			double z2 = zSizeD / 2.0;
			Vec3[] vecs = {Vec3.createVectorHelper(-x2, 0.0, -z2), Vec3.createVectorHelper(-x2, 0.0, z2), Vec3.createVectorHelper(x2, 0.0, z2), Vec3.createVectorHelper(x2, 0.0, -z2)};
			for (Vec3 vec2 : vecs) {
				vec2.rotateAroundY(rotation);
				vec2.xCoord += posX;
				vec2.yCoord += posY;
				vec2.zCoord += posZ;
			}
			tessellator.addVertexWithUV(vecs[0].xCoord, vecs[0].yCoord, vecs[0].zCoord, minU, minV);
			tessellator.addVertexWithUV(vecs[1].xCoord, vecs[1].yCoord, vecs[1].zCoord, minU, maxV);
			tessellator.addVertexWithUV(vecs[2].xCoord, vecs[2].yCoord, vecs[2].zCoord, maxU, maxV);
			tessellator.addVertexWithUV(vecs[3].xCoord, vecs[3].yCoord, vecs[3].zCoord, maxU, minV);
		}
	}

	private void renderFlowerBlock(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, i, j, k));
		int color = block.colorMultiplier(world, i, j, k);
		float r = (color >> 16 & 0xFF) / 255.0f;
		float g = (color >> 8 & 0xFF) / 255.0f;
		float b = (color & 0xFF) / 255.0f;
		if (EntityRenderer.anaglyphEnable) {
			float f3 = (r * 30.0f + g * 59.0f + b * 11.0f) / 100.0f;
			float f4 = (r * 30.0f + g * 70.0f) / 100.0f;
			float f5 = (r * 30.0f + b * 70.0f) / 100.0f;
			r = f3;
			g = f4;
			b = f5;
		}
		tessellator.setColorOpaque_F(r, g, b);
		long seed = i * 3129871L ^ k * 116129781L ^ j;
		seed = seed * seed * 42317861L + seed * 11L;
		IIcon iicon = renderblocks.getBlockIconFromSideAndMetadata(block, 0, world.getBlockMetadata(i, j, k));
		renderblocks.drawCrossedSquares(iicon, i + ((seed >> 16 & 0xFL) / 15.0f - 0.5) * 0.3, j, k + ((seed >> 24 & 0xFL) / 15.0f - 0.5) * 0.3, 1.0f);
	}

	private void renderFlowerPot(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
		float f4;
		renderblocks.renderStandardBlock(block, i, j, k);
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, i, j, k));
		float f = 1.0f;
		int l = block.colorMultiplier(world, i, j, k);
		IIcon icon = renderblocks.getBlockIconFromSide(block, 0);
		float f1 = (l >> 16 & 0xFF) / 255.0f;
		float f2 = (l >> 8 & 0xFF) / 255.0f;
		float f3 = (l & 0xFF) / 255.0f;
		if (EntityRenderer.anaglyphEnable) {
			f4 = (f1 * 30.0f + f2 * 59.0f + f3 * 11.0f) / 100.0f;
			float f5 = (f1 * 30.0f + f2 * 70.0f) / 100.0f;
			float f6 = (f1 * 30.0f + f3 * 70.0f) / 100.0f;
			f1 = f4;
			f2 = f5;
			f3 = f6;
		}
		tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
		f4 = 0.1865f;
		renderblocks.renderFaceXPos(block, i - 0.5f + f4, j, k, icon);
		renderblocks.renderFaceXNeg(block, i + 0.5f - f4, j, k, icon);
		renderblocks.renderFaceZPos(block, i, j, k - 0.5f + f4, icon);
		renderblocks.renderFaceZNeg(block, i, j, k + 0.5f - f4, icon);
		renderblocks.renderFaceYPos(block, i, j - 0.5f + f4 + 0.1875f, k, renderblocks.getBlockIcon(Blocks.dirt));
		ItemStack plant = GOTBlockFlowerPot.getPlant(world, i, j, k);
		if (plant != null) {
			int plantMeta;
			Block plantBlock = Block.getBlockFromItem(plant.getItem());
			int plantColor = plantBlock.getRenderColor(plantMeta = plant.getItemDamage());
			if (plantColor != 16777215) {
				float plantR = (plantColor >> 16 & 0xFF) / 255.0f;
				float plantG = (plantColor >> 8 & 0xFF) / 255.0f;
				float plantB = (plantColor & 0xFF) / 255.0f;
				tessellator.setColorOpaque_F(plantR, plantG, plantB);
			}
			tessellator.addTranslation(0.0f, 0.25f, 0.0f);
			if (plantBlock == GOTBlocks.clover) {
				renderClover(world, i, j, k, plantBlock, renderblocks, plantMeta == 1 ? 4 : 3, false);
			} else if (plantBlock == GOTBlocks.plantain) {
				renderPlantain(world, i, j, k, plantBlock, renderblocks, plantMeta == 1 ? 4 : 3, false);
			} else if (plantBlock instanceof GOTBlockGrass) {
				renderGrass(world, i, j, k, plantBlock, renderblocks, false);
			} else if (plantBlock instanceof GOTBlockFlower || plantBlock.getRenderType() == 1) {
				renderblocks.drawCrossedSquares(plantBlock.getIcon(2, plantMeta), i, j, k, 0.75f);
			} else {
				renderblocks.renderBlockByRenderType(plantBlock, i, j, k);
			}
			tessellator.addTranslation(0.0f, -0.25f, 0.0f);
		}
	}

	private void renderGrapevine(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
		block.setBlockBoundsForItemRender();
		renderblocks.setRenderBoundsFromBlock(block);
		renderblocks.renderStandardBlock(block, i, j, k);
		int meta = world.getBlockMetadata(i, j, k);
		renderblocks.setOverrideBlockTexture(block.getIcon(-1, meta));
		block.setBlockBoundsBasedOnState(world, i, j, k);
		renderblocks.setRenderBoundsFromBlock(block);
		double d = 0.001;
		renderblocks.renderMinY += d;
		renderblocks.renderMaxY -= d;
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.clearOverrideBlockTexture();
	}

	private void renderInvBarrel(Block block, RenderBlocks renderblocks) {
		renderblocks.renderAllFaces = true;
		renderStandardInvBlock(renderblocks, block, 0.15625, 0.0625, 0.15625, 0.84375, 0.75, 0.84375);
		for (float f : new float[]{0.25f, 0.5f}) {
			renderStandardInvBlock(renderblocks, block, 0.125, f, 0.125, 0.875, f + 0.0625f, 0.875);
		}
		for (float f : new float[]{0.0f, 0.6875f}) {
			renderStandardInvBlock(renderblocks, block, 0.125, f, 0.125, 0.25, f + 0.125f, 0.875);
			renderStandardInvBlock(renderblocks, block, 0.75, f, 0.125, 0.875, f + 0.125f, 0.875);
			renderStandardInvBlock(renderblocks, block, 0.25, f, 0.125, 0.75, f + 0.125f, 0.25);
			renderStandardInvBlock(renderblocks, block, 0.25, f, 0.75, 0.75, f + 0.125f, 0.875);
		}
		renderblocks.setOverrideBlockTexture(block.getBlockTextureFromSide(-1));
		renderStandardInvBlock(renderblocks, block, 0.875, 0.25, 0.4375, 1.0, 0.375, 0.5625);
		renderStandardInvBlock(renderblocks, block, 0.9375, 0.125, 0.46875, 1.0, 0.25, 0.53125);
		renderblocks.clearOverrideBlockTexture();
		renderblocks.renderAllFaces = false;
	}

	private void renderInvBirdCage(Block block, RenderBlocks renderblocks, int meta) {
		renderblocks.renderAllFaces = true;
		float d = 0.001f;
		renderStandardInvBlock(renderblocks, block, 0.0, 0.0625, 0.0, 0.0f + d, 1.0, 1.0, meta);
		renderStandardInvBlock(renderblocks, block, 1.0f - d, 0.0625, 0.0, 1.0, 1.0, 1.0, meta);
		renderStandardInvBlock(renderblocks, block, 0.0, 0.0625, 0.0, 1.0, 1.0, 0.0f + d, meta);
		renderStandardInvBlock(renderblocks, block, 0.0, 0.0625, 1.0f - d, 1.0, 1.0, 1.0, meta);
		renderStandardInvBlock(renderblocks, block, 0.0, 1.0f - d, 0.0, 1.0, 1.0, 1.0, meta);
		renderblocks.setOverrideBlockTexture(block.getIcon(-1, meta));
		renderStandardInvBlock(renderblocks, block, 0.0, 0.0, 0.0, 1.0, 0.0625, 1.0, meta);
		renderStandardInvBlock(renderblocks, block, 0.375, 1.0, 0.375, 0.625, 1.0625, 0.625, meta);
		renderStandardInvBlock(renderblocks, block, 0.46875, 1.0625, 0.46875, 0.53125, 1.1875, 0.53125, meta);
		renderblocks.clearOverrideBlockTexture();
		renderblocks.renderAllFaces = false;
	}

	private void renderInvBomb(Block block, int meta, RenderBlocks renderblocks) {
		renderblocks.renderAllFaces = true;
		renderStandardInvBlock(renderblocks, block, 0.125, 0.1875, 0.125, 0.875, 0.9375, 0.875, meta);
		renderStandardInvBlock(renderblocks, block, 0.375, 0.9375, 0.375, 0.625, 1.0, 0.625, meta);
		renderStandardInvBlock(renderblocks, block, 0.25, 0.9375, 0.375, 0.3125, 1.0, 0.4375, meta);
		renderStandardInvBlock(renderblocks, block, 0.25, 0.9375, 0.5625, 0.3125, 1.0, 0.625, meta);
		renderStandardInvBlock(renderblocks, block, 0.6875, 0.9375, 0.375, 0.75, 1.0, 0.4375, meta);
		renderStandardInvBlock(renderblocks, block, 0.6875, 0.9375, 0.5625, 0.75, 1.0, 0.625, meta);
		renderStandardInvBlock(renderblocks, block, 0.375, 0.9375, 0.25, 0.4375, 1.0, 0.3125, meta);
		renderStandardInvBlock(renderblocks, block, 0.5625, 0.9375, 0.25, 0.625, 1.0, 0.3125, meta);
		renderStandardInvBlock(renderblocks, block, 0.375, 0.9375, 0.6875, 0.4375, 1.0, 0.75, meta);
		renderStandardInvBlock(renderblocks, block, 0.5625, 0.9375, 0.6875, 0.625, 1.0, 0.75, meta);
		renderStandardInvBlock(renderblocks, block, 0.125, 0.0, 0.25, 0.1875, 0.1875, 0.75, meta);
		renderStandardInvBlock(renderblocks, block, 0.8125, 0.0, 0.25, 0.875, 0.1875, 0.75, meta);
		renderStandardInvBlock(renderblocks, block, 0.25, 0.0, 0.125, 0.75, 0.1875, 0.1875, meta);
		renderStandardInvBlock(renderblocks, block, 0.25, 0.0, 0.8125, 0.75, 0.1875, 0.875, meta);
		renderblocks.setOverrideBlockTexture(block.getIcon(-1, meta));
		renderStandardInvBlock(renderblocks, block, 0.0, 0.625, 0.3125, 0.0625, 0.6875, 0.6875, meta);
		renderStandardInvBlock(renderblocks, block, 0.0625, 0.625, 0.3125, 0.125, 0.6875, 0.375, meta);
		renderStandardInvBlock(renderblocks, block, 0.0625, 0.625, 0.625, 0.125, 0.6875, 0.6875, meta);
		renderStandardInvBlock(renderblocks, block, 0.9375, 0.625, 0.3125, 1.0, 0.6875, 0.6875, meta);
		renderStandardInvBlock(renderblocks, block, 0.875, 0.625, 0.3125, 0.9375, 0.6875, 0.375, meta);
		renderStandardInvBlock(renderblocks, block, 0.875, 0.625, 0.625, 0.9375, 0.6875, 0.6875, meta);
		renderStandardInvBlock(renderblocks, block, 0.3125, 0.625, 0.0, 0.6875, 0.6875, 0.0625, meta);
		renderStandardInvBlock(renderblocks, block, 0.3125, 0.625, 0.0625, 0.375, 0.6875, 0.125, meta);
		renderStandardInvBlock(renderblocks, block, 0.625, 0.625, 0.0625, 0.6875, 0.6875, 0.125, meta);
		renderStandardInvBlock(renderblocks, block, 0.3125, 0.625, 0.9375, 0.6875, 0.6875, 1.0, meta);
		renderStandardInvBlock(renderblocks, block, 0.3125, 0.625, 0.875, 0.375, 0.6875, 0.9375, meta);
		renderStandardInvBlock(renderblocks, block, 0.625, 0.625, 0.875, 0.6875, 0.6875, 0.9375, meta);
		renderblocks.clearOverrideBlockTexture();
		renderblocks.renderAllFaces = false;
	}

	private void renderInvButterflyJar(Block block, RenderBlocks renderblocks) {
		renderblocks.renderAllFaces = true;
		renderStandardInvBlock(renderblocks, block, 0.1875, 0.0, 0.1875, 0.8125, 0.5625, 0.8125);
		renderStandardInvBlock(renderblocks, block, 0.1875, 0.0, 0.1875, 0.8125, 0.0625, 0.8125);
		renderStandardInvBlock(renderblocks, block, 0.25, 0.5625, 0.25, 0.75, 0.6875, 0.75);
		renderblocks.setOverrideBlockTexture(block.getIcon(-1, 0));
		renderStandardInvBlock(renderblocks, block, 0.1875, 0.6875, 0.1875, 0.8125, 0.75, 0.8125);
		renderblocks.clearOverrideBlockTexture();
		renderblocks.renderAllFaces = false;
	}

	private void renderInvCommandTable(Block block, RenderBlocks renderblocks) {
		renderblocks.renderAllFaces = true;
		renderStandardInvBlock(renderblocks, block, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
		renderblocks.setOverrideBlockTexture(Blocks.planks.getIcon(0, 0));
		renderStandardInvBlock(renderblocks, block, -0.5, 1.0, -0.5, 1.5, 1.0625, 1.5);
		renderblocks.clearOverrideBlockTexture();
		renderblocks.renderAllFaces = false;
	}

	@Override
	public void renderInventoryBlock(Block block, int meta, int id, RenderBlocks renderblocks) {
		if (id == GOT.proxy.getBeaconRenderID()) {
			((GOTRenderBeacon) TileEntityRendererDispatcher.instance.getSpecialRendererByClass(GOTTileEntityBeacon.class)).renderInvBeacon();
		}
		if (id == GOT.proxy.getBarrelRenderID()) {
			renderInvBarrel(block, renderblocks);
		}
		if (id == GOT.proxy.getBombRenderID()) {
			renderInvBomb(block, meta, renderblocks);
		}
		if (id == GOT.proxy.getStalactiteRenderID()) {
			renderInvStalactite(block, meta, renderblocks);
		}
		if (id == GOT.proxy.getPlantainRenderID()) {
			renderInvPlantain(block, renderblocks, meta == 1 ? 4 : 3);
		}
		if (id == GOT.proxy.getCloverRenderID()) {
			renderInvClover(block, renderblocks, meta == 1 ? 4 : 3);
		}
		if (id == GOT.proxy.getFenceRenderID()) {
			renderInvFence(block, meta, renderblocks);
		}
		if (id == GOT.proxy.getLeavesRenderID()) {
			renderStandardInvBlock(renderblocks, block, meta);
		}
		if (id == GOT.proxy.getCommandTableRenderID()) {
			renderInvCommandTable(block, renderblocks);
			((GOTRenderCommandTable) TileEntityRendererDispatcher.instance.getSpecialRendererByClass(GOTTileEntityCommandTable.class)).renderInvTable();
		}
		if (id == GOT.proxy.getButterflyJarRenderID()) {
			renderInvButterflyJar(block, renderblocks);
		}
		if (id == GOT.proxy.getUnsmelteryRenderID()) {
			((GOTRenderUnsmeltery) TileEntityRendererDispatcher.instance.getSpecialRendererByClass(GOTTileEntityUnsmeltery.class)).renderInvUnsmeltery();
		}
		if (id == GOT.proxy.getChestRenderID()) {
			((GOTRenderChest) TileEntityRendererDispatcher.instance.getSpecialRendererByClass(GOTTileEntityChest.class)).renderInvChest(block);
		}
		if (id == GOT.proxy.getWasteRenderID()) {
			renderStandardInvBlock(renderblocks, block, meta);
		}
		if (id == GOT.proxy.getBeamRenderID()) {
			renderStandardInvBlock(renderblocks, block, meta);
		}
		if (id == GOT.proxy.getTreasureRenderID()) {
			GOTBlockTreasurePile.setTreasureBlockBounds(block, meta);
			renderblocks.setRenderBoundsFromBlock(block);
			renderblocks.lockBlockBounds = true;
			renderStandardInvBlock(renderblocks, block, meta);
			renderblocks.unlockBlockBounds();
		}
		if (id == GOT.proxy.getBirdCageRenderID()) {
			renderInvBirdCage(block, renderblocks, meta);
		}
		if (id == GOT.proxy.getWildFireJarRenderID()) {
			renderInvWildFireJar(block, renderblocks);
		}
		if (id == GOT.proxy.getCoralRenderID()) {
			renderStandardInvBlock(renderblocks, block, meta);
		}
		if (id == GOT.proxy.getTrapdoorRenderID()) {
			renderStandardInvBlock(renderblocks, block, meta);
		}
	}

	private void renderInvFence(Block block, int meta, RenderBlocks renderblocks) {
		for (int k = 0; k < 4; ++k) {
			float f = 0.125f;
			float f1 = 0.0625f;
			if (k == 0) {
				renderblocks.setRenderBounds(0.5f - f, 0.0, 0.0, 0.5f + f, 1.0, f * 2.0f);
			}
			if (k == 1) {
				renderblocks.setRenderBounds(0.5f - f, 0.0, 1.0f - f * 2.0f, 0.5f + f, 1.0, 1.0);
			}
			if (k == 2) {
				renderblocks.setRenderBounds(0.5f - f1, 1.0f - f1 * 3.0f, -f1 * 2.0f, 0.5f + f1, 1.0f - f1, 1.0f + f1 * 2.0f);
			}
			if (k == 3) {
				renderblocks.setRenderBounds(0.5f - f1, 0.5f - f1 * 3.0f, -f1 * 2.0f, 0.5f + f1, 0.5f - f1, 1.0f + f1 * 2.0f);
			}
			GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0f, -1.0f, 0.0f);
			renderblocks.renderFaceYNeg(block, 0.0, 0.0, 0.0, renderblocks.getBlockIconFromSideAndMetadata(block, 0, meta));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0f, 1.0f, 0.0f);
			renderblocks.renderFaceYPos(block, 0.0, 0.0, 0.0, renderblocks.getBlockIconFromSideAndMetadata(block, 1, meta));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0f, 0.0f, -1.0f);
			renderblocks.renderFaceZNeg(block, 0.0, 0.0, 0.0, renderblocks.getBlockIconFromSideAndMetadata(block, 2, meta));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0f, 0.0f, 1.0f);
			renderblocks.renderFaceZPos(block, 0.0, 0.0, 0.0, renderblocks.getBlockIconFromSideAndMetadata(block, 3, meta));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(-1.0f, 0.0f, 0.0f);
			renderblocks.renderFaceXNeg(block, 0.0, 0.0, 0.0, renderblocks.getBlockIconFromSideAndMetadata(block, 4, meta));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(1.0f, 0.0f, 0.0f);
			renderblocks.renderFaceXPos(block, 0.0, 0.0, 0.0, renderblocks.getBlockIconFromSideAndMetadata(block, 5, meta));
			tessellator.draw();
			GL11.glTranslatef(0.5f, 0.5f, 0.5f);
		}
		renderblocks.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
	}

	private void renderInvStalactite(Block block, int metadata, RenderBlocks renderblocks) {
		renderblocks.renderAllFaces = true;
		for (int l = 0; l < 16; ++l) {
			if (metadata == 0) {
				renderStandardInvBlock(renderblocks, block, 0.5f - l / 16.0f * 0.25f, l / 16.0f, 0.5f - l / 16.0f * 0.25f, 0.5f + l / 16.0f * 0.25f, (l + 1) / 16.0f, 0.5f + l / 16.0f * 0.25f);
			} else if (metadata == 1) {
				renderStandardInvBlock(renderblocks, block, 0.25f + l / 16.0f * 0.25f, l / 16.0f, 0.25f + l / 16.0f * 0.25f, 0.75f - l / 16.0f * 0.25f, (l + 1) / 16.0f, 0.75f - l / 16.0f * 0.25f);
			}
		}
		renderblocks.renderAllFaces = false;
	}

	private void renderInvWildFireJar(Block block, RenderBlocks renderblocks) {
		renderblocks.renderAllFaces = true;
		GOTBlockWildFireJar.renderingStage = 1;
		renderStandardInvBlock(renderblocks, block, 0.125, 0.0, 0.125, 0.875, 0.5, 0.875);
		GOTBlockWildFireJar.renderingStage = 2;
		renderStandardInvBlock(renderblocks, block, 0.3125, 0.5, 0.3125, 0.6875, 0.6875, 0.6875);
		GOTBlockWildFireJar.renderingStage = 3;
		renderStandardInvBlock(renderblocks, block, 0.25, 0.6875, 0.25, 0.75, 0.8125, 0.75);
		GOTBlockWildFireJar.renderingStage = 4;
		renderStandardInvBlock(renderblocks, block, 0.3125, 0.8125, 0.3125, 0.6875, 0.875, 0.6875);
		GOTBlockWildFireJar.renderingStage = 5;
		renderStandardInvBlock(renderblocks, block, 0.375, 0.875, 0.5, 0.625, 1.0, 0.5);
		renderStandardInvBlock(renderblocks, block, 0.5, 0.875, 0.375, 0.5, 1.0, 0.625);
		GOTBlockWildFireJar.renderingStage = 6;
		renderStandardInvBlock(renderblocks, block, 0.0, 0.0, 0.5, 1.0, 1.0, 0.5);
		renderStandardInvBlock(renderblocks, block, 0.5, 0.0, 0.0, 0.5, 1.0, 1.0);
		GOTBlockWildFireJar.renderingStage = 0;
		renderblocks.renderAllFaces = false;
	}

	private void renderPlate(int i, int j, int k, Block block, RenderBlocks renderblocks) {
		int ao = getAO();
		setAO(0);
		renderblocks.renderAllFaces = true;
		renderblocks.setRenderBounds(0.1875, 0.0, 0.1875, 0.8125, 0.0625, 0.8125);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.125, 0.0625, 0.125, 0.875, 0.125, 0.875);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.renderAllFaces = false;
		setAO(ao);
	}

	private void renderReeds(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
		block.setBlockBoundsBasedOnState(world, i, j, k);
		renderblocks.setRenderBoundsFromBlock(block);
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, i, j, k));
		int c = block.colorMultiplier(world, i, j, k);
		float r = (c >> 16 & 0xFF) / 255.0f;
		float g = (c >> 8 & 0xFF) / 255.0f;
		float b = (c & 0xFF) / 255.0f;
		if (EntityRenderer.anaglyphEnable) {
			float r1 = (r * 30.0f + g * 59.0f + b * 11.0f) / 100.0f;
			float g1 = (r * 30.0f + g * 70.0f) / 100.0f;
			float b1 = (r * 30.0f + b * 70.0f) / 100.0f;
			r = r1;
			g = g1;
			b = b1;
		}
		tessellator.setColorOpaque_F(r, g, b);
		double d1 = j;
		if (world.getBlock(i, j - 1, k) == block) {
			IIcon iicon = renderblocks.getBlockIcon(block, world, i, j, k, 0);
			renderblocks.drawCrossedSquares(iicon, i, d1, k, 1.0f);
		} else {
			IIcon iicon = renderblocks.getBlockIcon(block, world, i, j, k, 0);
			renderblocks.drawCrossedSquares(iicon, i, d1, k, 1.0f);
			for (int j1 = j - 1; j1 > 0; --j1) {
				d1 -= 1.0;
				tessellator.setBrightness(block.getMixedBrightnessForBlock(world, i, j1, k));
				boolean lower = world.getBlock(i, j1 - 1, k).isOpaqueCube();
				if (lower) {
					iicon = renderblocks.getBlockIcon(block, world, i, j, k, -2);
					renderblocks.drawCrossedSquares(iicon, i, d1, k, 1.0f);
					break;
				}
				iicon = renderblocks.getBlockIcon(block, world, i, j, k, -1);
				renderblocks.drawCrossedSquares(iicon, i, d1, k, 1.0f);
			}
		}
	}

	private void renderRope(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
		double ropeWidth = 0.125;
		double ropeMinX = 0.5 - ropeWidth / 2.0;
		double ropeMaxX = 1.0 - ropeMinX;
		double ropeOffset = 0.0625;
		int meta = world.getBlockMetadata(i, j, k);
		boolean top = world.getBlock(i, j + 1, k) != block || world.getBlockMetadata(i, j + 1, k) != meta;
		double knotHeight = 0.25;
		double knotBottom = 1.0 - knotHeight;
		double knotWidth = 0.25;
		double knotMinX = 0.5 - knotWidth / 2.0;
		double knotMaxX = 1.0 - knotMinX;
		double ropeTop = top ? 1.0 - knotHeight : 1.0;
		if (meta == 5) {
			renderblocks.setRenderBounds(0.0, 0.0, ropeMinX, ropeOffset, ropeTop, ropeMaxX);
			renderblocks.renderStandardBlock(block, i, j, k);
			if (top) {
				renderblocks.setRenderAllFaces(true);
				renderblocks.setRenderBounds(0.0, knotBottom, knotMinX, knotWidth, 1.0, knotMaxX);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderAllFaces(false);
			}
		}
		if (meta == 4) {
			renderblocks.setRenderBounds(1.0 - ropeOffset, 0.0, ropeMinX, 1.0, ropeTop, ropeMaxX);
			renderblocks.renderStandardBlock(block, i, j, k);
			if (top) {
				renderblocks.setRenderAllFaces(true);
				renderblocks.setRenderBounds(1.0 - knotWidth, knotBottom, knotMinX, 1.0, 1.0, knotMaxX);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderAllFaces(false);
			}
		}
		if (meta == 3) {
			renderblocks.setRenderBounds(ropeMinX, 0.0, 0.0, ropeMaxX, ropeTop, ropeOffset);
			renderblocks.renderStandardBlock(block, i, j, k);
			if (top) {
				renderblocks.setRenderAllFaces(true);
				renderblocks.setRenderBounds(knotMinX, knotBottom, 0.0, knotMaxX, 1.0, knotWidth);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderAllFaces(false);
			}
		}
		if (meta == 2) {
			renderblocks.setRenderBounds(ropeMinX, 0.0, 1.0 - ropeOffset, ropeMaxX, ropeTop, 1.0);
			renderblocks.renderStandardBlock(block, i, j, k);
			if (top) {
				renderblocks.setRenderAllFaces(true);
				renderblocks.setRenderBounds(knotMinX, knotBottom, 1.0 - knotWidth, knotMaxX, 1.0, 1.0);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderAllFaces(false);
			}
		}
	}

	private void renderStalactite(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
		int ao = getAO();
		setAO(0);
		renderblocks.renderAllFaces = true;
		int metadata = world.getBlockMetadata(i, j, k);
		for (int l = 0; l < 16; ++l) {
			if (metadata == 0) {
				renderblocks.setRenderBounds(0.5f - l / 16.0f * 0.25f, l / 16.0f, 0.5f - l / 16.0f * 0.25f, 0.5f + l / 16.0f * 0.25f, (l + 1) / 16.0f, 0.5f + l / 16.0f * 0.25f);
				renderblocks.renderStandardBlock(block, i, j, k);
			} else if (metadata == 1) {
				renderblocks.setRenderBounds(0.25f + l / 16.0f * 0.25f, l / 16.0f, 0.25f + l / 16.0f * 0.25f, 0.75f - l / 16.0f * 0.25f, (l + 1) / 16.0f, 0.75f - l / 16.0f * 0.25f);
				renderblocks.renderStandardBlock(block, i, j, k);
			}
		}
		renderblocks.renderAllFaces = false;
		setAO(ao);
	}

	private void renderTrapdoor(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
		int meta = world.getBlockMetadata(i, j, k);
		if (!BlockTrapDoor.func_150118_d(meta)) {
			int dir = meta & 3;
			switch (dir) {
				case 0:
					renderblocks.uvRotateTop = 3;
					renderblocks.uvRotateBottom = 3;
					break;
				case 1:
					renderblocks.uvRotateTop = 0;
					renderblocks.uvRotateBottom = 0;
					break;
				case 2:
					renderblocks.uvRotateTop = 1;
					renderblocks.uvRotateBottom = 2;
					break;
				case 3:
					renderblocks.uvRotateTop = 2;
					renderblocks.uvRotateBottom = 1;
					break;
				default:
					break;
			}
		}
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.uvRotateBottom = 0;
		renderblocks.uvRotateTop = 0;
	}

	private void renderVanillaCauldron(IBlockAccess world, int i, int j, int k, Block block, RenderBlocks renderblocks) {
		renderblocks.renderStandardBlock(block, i, j, k);
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, i, j, k));
		int color = block.colorMultiplier(world, i, j, k);
		float r = (color >> 16 & 0xFF) / 255.0f;
		float g = (color >> 8 & 0xFF) / 255.0f;
		float b = (color & 0xFF) / 255.0f;
		if (EntityRenderer.anaglyphEnable) {
			float r1 = (r * 30.0f + g * 59.0f + b * 11.0f) / 100.0f;
			float g1 = (r * 30.0f + g * 70.0f) / 100.0f;
			float b1 = (r * 30.0f + b * 70.0f) / 100.0f;
			r = r1;
			g = g1;
			b = b1;
		}
		tessellator.setColorOpaque_F(r, g, b);
		IIcon iconSide = block.getBlockTextureFromSide(2);
		float w = 0.125f;
		renderblocks.renderFaceXPos(block, i - 1.0f + w, j, k, iconSide);
		renderblocks.renderFaceXNeg(block, i + 1.0f - w, j, k, iconSide);
		renderblocks.renderFaceZPos(block, i, j, k - 1.0f + w, iconSide);
		renderblocks.renderFaceZNeg(block, i, j, k + 1.0f - w, iconSide);
		IIcon iconInner = BlockCauldron.getCauldronIcon("inner");
		renderblocks.renderFaceYPos(block, i, j - 1.0f + 0.25f, k, iconInner);
		renderblocks.renderFaceYNeg(block, i, j + 1.0f - 0.75f, k, iconInner);
		int meta = world.getBlockMetadata(i, j, k);
		if (meta > 0) {
			color = Blocks.water.colorMultiplier(world, i, j, k);
			r = (color >> 16 & 0xFF) / 255.0f;
			g = (color >> 8 & 0xFF) / 255.0f;
			b = (color & 0xFF) / 255.0f;
			tessellator.setColorOpaque_F(r, g, b);
			IIcon iconWater = BlockLiquid.getLiquidIcon("water_still");
			renderblocks.renderFaceYPos(block, i, j - 1.0f + BlockCauldron.getRenderLiquidLevel(meta), k, iconWater);
		}
	}

	private void renderWildFireJar(int i, int j, int k, Block block, RenderBlocks renderblocks) {
		int ao = getAO();
		setAO(0);
		renderblocks.renderAllFaces = true;
		GOTBlockWildFireJar.renderingStage = 1;
		renderblocks.setRenderBounds(0.125, 0.0, 0.125, 0.875, 0.5, 0.875);
		renderblocks.renderStandardBlock(block, i, j, k);
		GOTBlockWildFireJar.renderingStage = 2;
		renderblocks.setRenderBounds(0.3125, 0.5, 0.3125, 0.6875, 0.6875, 0.6875);
		renderblocks.renderStandardBlock(block, i, j, k);
		GOTBlockWildFireJar.renderingStage = 3;
		renderblocks.setRenderBounds(0.25, 0.6875, 0.25, 0.75, 0.8125, 0.75);
		renderblocks.renderStandardBlock(block, i, j, k);
		GOTBlockWildFireJar.renderingStage = 4;
		renderblocks.setRenderBounds(0.3125, 0.8125, 0.3125, 0.6875, 0.875, 0.6875);
		renderblocks.renderStandardBlock(block, i, j, k);
		GOTBlockWildFireJar.renderingStage = 5;
		renderblocks.setRenderBounds(0.375, 0.875, 0.5, 0.625, 1.0, 0.5);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.5, 0.875, 0.375, 0.5, 1.0, 0.625);
		renderblocks.renderStandardBlock(block, i, j, k);
		GOTBlockWildFireJar.renderingStage = 6;
		renderblocks.setRenderBounds(0.0, 0.0, 0.5, 1.0, 1.0, 0.5);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.5, 0.0, 0.0, 0.5, 1.0, 1.0);
		renderblocks.renderStandardBlock(block, i, j, k);
		GOTBlockWildFireJar.renderingStage = 0;
		renderblocks.renderAllFaces = false;
		setAO(ao);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int i, int j, int k, Block block, int id, RenderBlocks renderblocks) {
		boolean fancyGraphics = Minecraft.getMinecraft().gameSettings.fancyGraphics;
		world.getBiomeGenForCoords(i, k);
		if (id == GOT.proxy.getBeaconRenderID()) {
			renderBeacon(world, i, j, k, renderblocks);
			return true;
		}
		if (id == GOT.proxy.getBarrelRenderID()) {
			renderBarrel(world, i, j, k, block, renderblocks);
			return true;
		}
		if (id == GOT.proxy.getBombRenderID()) {
			renderBomb(world, i, j, k, block, renderblocks);
			return true;
		}
		if (id == GOT.proxy.getDoubleTorchRenderID()) {
			renderDoubleTorch(world, i, j, k, block, renderblocks);
			return true;
		}
		if (id == GOT.proxy.getPlateRenderID()) {
			renderPlate(i, j, k, block, renderblocks);
			return true;
		}
		if (id == GOT.proxy.getStalactiteRenderID()) {
			renderStalactite(world, i, j, k, block, renderblocks);
			return true;
		}
		if (id == GOT.proxy.getFlowerPotRenderID()) {
			renderFlowerPot(world, i, j, k, block, renderblocks);
			return true;
		}
		if (id == GOT.proxy.getPlantainRenderID()) {
			renderPlantain(world, i, j, k, block, renderblocks, world.getBlockMetadata(i, j, k) == 1 ? 4 : 3, true);
			return true;
		}
		if (id == GOT.proxy.getCloverRenderID()) {
			renderClover(world, i, j, k, block, renderblocks, world.getBlockMetadata(i, j, k) == 1 ? 4 : 3, true);
			return true;
		}
		if (id == GOT.proxy.getFenceRenderID()) {
			return renderblocks.renderBlockFence((BlockFence) block, i, j, k);
		}
		if (id == GOT.proxy.getGrassRenderID()) {
			renderGrass(world, i, j, k, block, renderblocks, true);
			return true;
		}
		if (id == GOT.proxy.getLeavesRenderID()) {
			return renderblocks.renderStandardBlock(block, i, j, k);
		}
		if (id == GOT.proxy.getFallenLeavesRenderID()) {
			if (fancyGraphics) {
				renderFallenLeaves(world, i, j, k, block, new int[]{6, 10}, new int[]{2, 6}, new int[]{2, 6}, 0.7f);
				return true;
			}
			return renderblocks.renderStandardBlock(block, i, j, k);
		}
		if (id == GOT.proxy.getCommandTableRenderID()) {
			renderCommandTable(i, j, k, block, renderblocks);
			return true;
		}
		if (id == GOT.proxy.getButterflyJarRenderID()) {
			renderButterflyJar(i, j, k, block, renderblocks);
			return true;
		}
		if (id == GOT.proxy.getUnsmelteryRenderID() || id == GOT.proxy.getChestRenderID()) {
			return true;
		}
		if (id == GOT.proxy.getReedsRenderID()) {
			renderReeds(world, i, j, k, block, renderblocks);
			return true;
		}
		if (id == GOT.proxy.getWasteRenderID()) {
			renderBlockRandomRotated(i, j, k, block, renderblocks, true);
			return true;
		}
		if (id == GOT.proxy.getBeamRenderID()) {
			renderBeam(world, i, j, k, block, renderblocks);
			return true;
		}
		if (id == GOT.proxy.getVCauldronRenderID()) {
			renderVanillaCauldron(world, i, j, k, block, renderblocks);
			return true;
		}
		if (id == GOT.proxy.getGrapevineRenderID()) {
			renderGrapevine(world, i, j, k, block, renderblocks);
			return true;
		}
		if (id == GOT.proxy.getThatchFloorRenderID()) {
			if (fancyGraphics) {
				renderFallenLeaves(world, i, j, k, block, new int[]{10, 16}, new int[]{6, 12}, new int[]{1, 1}, 1.0f);
				return true;
			}
			return renderblocks.renderStandardBlock(block, i, j, k);
		}
		if (id == GOT.proxy.getTreasureRenderID()) {
			renderBlockRandomRotated(i, j, k, block, renderblocks, false);
			return true;
		}
		if (id == GOT.proxy.getFlowerRenderID()) {
			renderFlowerBlock(world, i, j, k, block, renderblocks);
			return true;
		}
		if (id == GOT.proxy.getDoublePlantRenderID()) {
			renderDoublePlant(world, i, j, k, (BlockDoublePlant) block, renderblocks);
			return true;
		}
		if (id == GOT.proxy.getBirdCageRenderID()) {
			renderBirdCage(world, i, j, k, block, renderblocks);
			return true;
		}
		if (id == GOT.proxy.getWildFireJarRenderID()) {
			renderWildFireJar(i, j, k, block, renderblocks);
			return true;
		}
		if (id == GOT.proxy.getCoralRenderID()) {
			renderCoral(world, i, j, k, block, renderblocks);
			return true;
		}
		if (id == GOT.proxy.getDoorRenderID()) {
			return renderDoor(world, i, j, k, block, renderblocks);
		}
		if (id == GOT.proxy.getRopeRenderID()) {
			renderRope(world, i, j, k, block, renderblocks);
			return true;
		}
		if (id == GOT.proxy.getChainRenderID()) {
			IIcon icon = renderblocks.getIconSafe(block.getIcon(world, i, j, k, 0));
			renderblocks.setOverrideBlockTexture(icon);
			boolean flag = renderblocks.renderCrossedSquares(block, i, j, k);
			renderblocks.clearOverrideBlockTexture();
			return flag;
		}
		if (id == GOT.proxy.getTrapdoorRenderID()) {
			renderTrapdoor(world, i, j, k, block, renderblocks);
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelID) {
		return renderInvIn3D;
	}
}
