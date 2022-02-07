package got.client.model;

import org.lwjgl.opengl.GL11;

import got.GOT;
import got.client.GOTTickHandlerClient;
import got.client.render.other.GOTRenderBlocks;
import got.common.database.GOTRegistry;
import got.common.entity.other.GOTPlateFallingInfo;
import got.common.item.other.GOTItemPlate;
import got.common.tileentity.GOTTileEntityPlate;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTModelPlateHead extends GOTModelHuman {
	private RenderBlocks blockRenderer = new RenderBlocks();
	private GOTTileEntityPlate plateTE = new GOTTileEntityPlate();
	private Block plateBlock;

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		ItemStack heldItem;
		float tick = GOTTickHandlerClient.getRenderTick();
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		float headRotateY = f3;
		GOTPlateFallingInfo fallingInfo = entity == null ? null : GOTPlateFallingInfo.getOrCreateFor(entity, false);
		float fallOffset = fallingInfo == null ? 0.0f : fallingInfo.getPlateOffsetY(tick);
		GL11.glEnable(32826);
		GL11.glPushMatrix();
		GL11.glScalef(1.0f, -1.0f, 1.0f);
		GL11.glRotatef(headRotateY, 0.0f, 1.0f, 0.0f);
		GL11.glTranslatef(0.0f, 1.0f - bipedHead.rotationPointY / 16.0f, 0.0f);
		GL11.glTranslatef(0.0f, fallOffset * 0.5f, 0.0f);
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		World world = entity == null ? GOT.proxy.getClientWorld() : entity.worldObj;
		GOTRenderBlocks.renderEntityPlate(world, 0, 0, 0, plateBlock, blockRenderer);
		if (entity instanceof EntityLivingBase && (heldItem = ((EntityLivingBase) entity).getHeldItem()) != null && GOTTileEntityPlate.isValidFoodItem(heldItem)) {
			ItemStack heldItemMinusOne = heldItem.copy();
			--heldItemMinusOne.stackSize;
			if (heldItemMinusOne.stackSize > 0) {
				plateTE.setFoodItem(heldItemMinusOne);
				plateTE.plateFallInfo = fallingInfo;
				TileEntityRendererDispatcher.instance.renderTileEntityAt(plateTE, -0.5, -0.5, -0.5, tick);
				plateTE.plateFallInfo = null;
				GL11.glDisable(2884);
			}
		}
		GL11.glPopMatrix();
		GL11.glDisable(32826);
	}

	public void setPlateItem(ItemStack itemstack) {
		plateBlock = itemstack.getItem() instanceof GOTItemPlate ? ((GOTItemPlate) itemstack.getItem()).plateBlock : GOTRegistry.plateBlock;
	}
}
