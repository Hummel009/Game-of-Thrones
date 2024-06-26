package got.client.render.other;

import got.client.model.GOTModelBanner;
import got.common.item.other.GOTItemBanner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class GOTRenderBannerItem implements IItemRenderer {
	private static final GOTModelBanner MODEL = new GOTModelBanner();
	private static final Minecraft MINECRAFT = Minecraft.getMinecraft();

	@Override
	public boolean handleRenderType(ItemStack itemstack, IItemRenderer.ItemRenderType type) {
		return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON;
	}

	@Override
	public void renderItem(IItemRenderer.ItemRenderType type, ItemStack itemstack, Object... data) {
		GL11.glDisable(2884);
		Entity holder = (Entity) data[1];
		boolean isFirstPerson = holder == MINECRAFT.thePlayer && MINECRAFT.gameSettings.thirdPersonView == 0;
		TextureManager textureManager = MINECRAFT.getTextureManager();
		if (isFirstPerson) {
			GL11.glTranslatef(1.0f, 1.0f, 0.0f);
			GL11.glScalef(-1.0f, 1.0f, 1.0f);
		} else {
			GL11.glTranslatef(-1.5f, 0.85f, -0.1f);
			GL11.glRotatef(75.0f, 0.0f, 0.0f, 1.0f);
		}
		GL11.glScalef(1.0f, -1.0f, 1.0f);
		GOTItemBanner.BannerType bannerType = GOTItemBanner.getBannerType(itemstack);
		textureManager.bindTexture(GOTRenderBanner.STAND_TEXTURE);
		MODEL.renderPost(0.0625f);
		MODEL.renderLowerPost(0.0625f);
		textureManager.bindTexture(GOTRenderBanner.getBannerTexture(bannerType));
		MODEL.renderBanner(0.0625f);
	}

	@Override
	public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack itemstack, IItemRenderer.ItemRendererHelper helper) {
		return false;
	}
}