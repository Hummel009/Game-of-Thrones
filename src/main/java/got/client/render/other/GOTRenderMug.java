package got.client.render.other;

import got.client.model.*;
import got.common.database.GOTBlocks;
import got.common.item.other.GOTItemMug;
import got.common.tileentity.GOTTileEntityMug;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTRenderMug extends TileEntitySpecialRenderer {
	private static final ResourceLocation MUG_TEXTURE = new ResourceLocation("got:textures/model/mug.png");
	private static final ResourceLocation MUG_CLAY_TEXTURE = new ResourceLocation("got:textures/model/mug_clay.png");
	private static final ResourceLocation GOBLET_GOLD_TEXTURE = new ResourceLocation("got:textures/model/goblet_gold.png");
	private static final ResourceLocation GOBLET_SILVER_TEXTURE = new ResourceLocation("got:textures/model/goblet_silver.png");
	private static final ResourceLocation GOBLET_BRONZE_TEXTURE = new ResourceLocation("got:textures/model/goblet_bronze.png");
	private static final ResourceLocation GOBLET_VALYRIAN_TEXTURE = new ResourceLocation("got:textures/model/goblet_valyrian.png");
	private static final ResourceLocation GOBLET_COPPER_TEXTURE = new ResourceLocation("got:textures/model/goblet_copper.png");
	private static final ResourceLocation GOBLET_WOOD_TEXTURE = new ResourceLocation("got:textures/model/goblet_wood.png");
	private static final ResourceLocation SKULL_TEXTURE = new ResourceLocation("got:textures/model/skull_cup.png");
	private static final ResourceLocation GLASS_TEXTURE = new ResourceLocation("got:textures/model/wine_glass.png");
	private static final ResourceLocation BOTTLE_TEXTURE = new ResourceLocation("got:textures/model/glass_bottle.png");
	private static final ResourceLocation HORN_TEXTURE = new ResourceLocation("got:textures/model/ale_horn.png");
	private static final ResourceLocation HORN_GOLD_TEXTURE = new ResourceLocation("got:textures/model/ale_horn_gold.png");

	private static final GOTModelAleHorn HORN_MODEL = new GOTModelAleHorn();
	private static final RenderBlocks BLOCK_RENDERER = new RenderBlocks();

	private static final ModelBase MUGOTEL = new GOTModelMug();
	private static final ModelBase GOBLET_MODEL = new GOTModelGoblet();
	private static final ModelBase SKULL_MODEL = new GOTModelSkullCup();
	private static final ModelBase GLASS_MODEL = new GOTModelWineGlass();
	private static final ModelBase BOTTLE_MODEL = new GOTModelGlassBottle();

	private static void renderLiquid(IIcon icon, int uvMin, int uvMax, double yMin, double yMax, float scale) {
		double yMin1 = yMin;
		double yMax1 = yMax;
		double edge = 0.001;
		double xzMin = (double) uvMin * scale;
		double xzMax = (double) uvMax * scale;
		float dxz = 0.5f - (uvMin + uvMax) / 2.0f * scale;
		yMin1 = 16.0 - yMin1;
		yMax1 = 16.0 - yMax1;
		yMin1 *= scale;
		yMax1 *= scale;
		GL11.glPushMatrix();
		GL11.glTranslatef(dxz, -0.5f, dxz);
		BLOCK_RENDERER.setOverrideBlockTexture(icon);
		GOTRenderBlocks.renderStandardInvBlock(BLOCK_RENDERER, GOTBlocks.mug, xzMin += edge, yMax1 - edge, xzMin, xzMax -= edge, yMin1 + edge, xzMax);
		BLOCK_RENDERER.clearOverrideBlockTexture();
		GL11.glPopMatrix();
	}

	private static void renderMeniscus(IIcon icon, int uvMin, int uvMax, double width, double height, float scale) {
		double width1 = width;
		double height1 = height;
		float minU = icon.getInterpolatedU(uvMin);
		float maxU = icon.getInterpolatedU(uvMax);
		float minV = icon.getInterpolatedV(uvMin);
		float maxV = icon.getInterpolatedV(uvMax);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-(width1 *= scale), -(height1 *= scale), width1, minU, maxV);
		tessellator.addVertexWithUV(width1, -height1, width1, maxU, maxV);
		tessellator.addVertexWithUV(width1, -height1, -width1, maxU, minV);
		tessellator.addVertexWithUV(-width1, -height1, -width1, minU, minV);
		tessellator.draw();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		GOTTileEntityMug mug = (GOTTileEntityMug) tileentity;
		ItemStack mugItemstack = mug.getMugItemForRender();
		Item mugItem = mugItemstack.getItem();
		boolean full = !mug.isEmpty();
		GOTItemMug.Vessel vessel = mug.getVessel();
		GL11.glEnable(32826);
		GL11.glDisable(2884);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d + 0.5f, (float) d1, (float) d2 + 0.5f);
		GL11.glScalef(-1.0f, -1.0f, 1.0f);
		float mugScale = 0.75f;
		GL11.glScalef(mugScale, mugScale, mugScale);
		float scale = 0.0625f;
		switch (mug.getBlockMetadata()) {
			case 0:
				GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
				break;
			case 1:
				GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
				break;
			case 2:
				GL11.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
				break;
			case 3:
				GL11.glRotatef(0.0f, 0.0f, 1.0f, 0.0f);
		}
		if (vessel == GOTItemMug.Vessel.SKULL || vessel == GOTItemMug.Vessel.HORN || vessel == GOTItemMug.Vessel.HORN_GOLD) {
			GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
		}
		if (full) {
			GL11.glDisable(2896);
			GL11.glPushMatrix();
			bindTexture(TextureMap.locationItemsTexture);
			IIcon liquidIcon = mugItem.getIconFromDamage(-1);
			if (vessel == GOTItemMug.Vessel.MUG || vessel == GOTItemMug.Vessel.MUG_CLAY) {
				renderMeniscus(liquidIcon, 6, 10, 2.0, 7.0, scale);
			} else if (vessel == GOTItemMug.Vessel.GOBLET_BRONZE || vessel == GOTItemMug.Vessel.GOBLET_VALYRIAN || vessel == GOTItemMug.Vessel.GOBLET_GOLD || vessel == GOTItemMug.Vessel.GOBLET_SILVER || vessel == GOTItemMug.Vessel.GOBLET_COPPER || vessel == GOTItemMug.Vessel.GOBLET_WOOD) {
				renderMeniscus(liquidIcon, 6, 9, 1.5, 8.0, scale);
			} else if (vessel == GOTItemMug.Vessel.SKULL) {
				renderMeniscus(liquidIcon, 5, 11, 3.0, 9.0, scale);
			} else if (vessel == GOTItemMug.Vessel.GLASS) {
				renderLiquid(liquidIcon, 6, 9, 6.0, 9.0, scale);
			} else if (vessel == GOTItemMug.Vessel.BOTTLE) {
				renderLiquid(liquidIcon, 6, 10, 1.0, 5.0, scale);
			} else if (vessel == GOTItemMug.Vessel.HORN || vessel == GOTItemMug.Vessel.HORN_GOLD) {
				HORN_MODEL.prepareLiquid(scale);
				renderMeniscus(liquidIcon, 6, 9, -1.5, 5.0, scale);
			}
			GL11.glPopMatrix();
			GL11.glEnable(2896);
		}
		GL11.glPushMatrix();
		ModelBase model = null;
		if (vessel == GOTItemMug.Vessel.MUG) {
			bindTexture(MUG_TEXTURE);
			model = MUGOTEL;
		} else if (vessel == GOTItemMug.Vessel.MUG_CLAY) {
			bindTexture(MUG_CLAY_TEXTURE);
			model = MUGOTEL;
		} else if (vessel == GOTItemMug.Vessel.GOBLET_GOLD) {
			bindTexture(GOBLET_GOLD_TEXTURE);
			model = GOBLET_MODEL;
		} else if (vessel == GOTItemMug.Vessel.GOBLET_SILVER) {
			bindTexture(GOBLET_SILVER_TEXTURE);
			model = GOBLET_MODEL;
		} else if (vessel == GOTItemMug.Vessel.GOBLET_VALYRIAN) {
			bindTexture(GOBLET_VALYRIAN_TEXTURE);
			model = GOBLET_MODEL;
		} else if (vessel == GOTItemMug.Vessel.GOBLET_BRONZE) {
			bindTexture(GOBLET_BRONZE_TEXTURE);
			model = GOBLET_MODEL;
		} else if (vessel == GOTItemMug.Vessel.GOBLET_COPPER) {
			bindTexture(GOBLET_COPPER_TEXTURE);
			model = GOBLET_MODEL;
		} else if (vessel == GOTItemMug.Vessel.GOBLET_WOOD) {
			bindTexture(GOBLET_WOOD_TEXTURE);
			model = GOBLET_MODEL;
		} else if (vessel == GOTItemMug.Vessel.SKULL) {
			bindTexture(SKULL_TEXTURE);
			model = SKULL_MODEL;
		} else if (vessel == GOTItemMug.Vessel.GLASS) {
			bindTexture(GLASS_TEXTURE);
			model = GLASS_MODEL;
			GL11.glEnable(2884);
		} else if (vessel == GOTItemMug.Vessel.BOTTLE) {
			bindTexture(BOTTLE_TEXTURE);
			model = BOTTLE_MODEL;
			GL11.glEnable(2884);
		} else if (vessel == GOTItemMug.Vessel.HORN) {
			bindTexture(HORN_TEXTURE);
			model = HORN_MODEL;
		} else if (vessel == GOTItemMug.Vessel.HORN_GOLD) {
			bindTexture(HORN_GOLD_TEXTURE);
			model = HORN_MODEL;
		}
		if (model != null) {
			model.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, scale);
		}
		GL11.glPopMatrix();
		GL11.glPopMatrix();
		GL11.glDisable(3042);
		GL11.glEnable(2884);
		GL11.glDisable(32826);
	}
}