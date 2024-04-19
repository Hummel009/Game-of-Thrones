package got.client.model;

import got.common.item.other.GOTItemLeatherHat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class GOTModelLeatherHat extends GOTModelBiped {
	private static final ItemStack FEATHER = new ItemStack(Items.feather);

	private ItemStack hatItem;

	public GOTModelLeatherHat(float f) {
		super(f);
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedHead.addBox(-6.0f, -9.0f, -6.0f, 12, 2, 12, f);
		bipedHead.setTextureOffset(0, 14).addBox(-4.0f, -13.0f, -4.0f, 8, 4, 8, f);
		bipedHeadwear.cubeList.clear();
		bipedBody.cubeList.clear();
		bipedRightArm.cubeList.clear();
		bipedLeftArm.cubeList.clear();
		bipedRightLeg.cubeList.clear();
		bipedLeftLeg.cubeList.clear();
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		GL11.glPushMatrix();
		int hatColor = GOTItemLeatherHat.getHatColor(hatItem);
		float r = (hatColor >> 16 & 0xFF) / 255.0f;
		float g = (hatColor >> 8 & 0xFF) / 255.0f;
		float b = (hatColor & 0xFF) / 255.0f;
		GL11.glColor3f(r, g, b);
		bipedHead.render(f5);
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		if (GOTItemLeatherHat.hasFeather(hatItem)) {
			bipedHead.postRender(f5);
			GL11.glScalef(0.375f, 0.375f, 0.375f);
			GL11.glRotatef(130.0f, 1.0f, 0.0f, 0.0f);
			GL11.glRotatef(30.0f, 0.0f, 1.0f, 0.0f);
			GL11.glTranslatef(0.25f, 1.5f, 0.75f);
			GL11.glRotatef(-45.0f, 0.0f, 0.0f, 1.0f);
			int featherColor = GOTItemLeatherHat.getFeatherColor(hatItem);
			r = (featherColor >> 16 & 0xFF) / 255.0f;
			g = (featherColor >> 8 & 0xFF) / 255.0f;
			b = (featherColor & 0xFF) / 255.0f;
			GL11.glColor3f(r, g, b);
			if (entity instanceof EntityLivingBase) {
				RenderManager.instance.itemRenderer.renderItem((EntityLivingBase) entity, FEATHER, 0);
			} else {
				RenderManager.instance.itemRenderer.renderItem(Minecraft.getMinecraft().thePlayer, FEATHER, 0);
			}
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
		}
		GL11.glPopMatrix();
	}

	@SuppressWarnings("unused")
	public ItemStack getHatItem() {
		return hatItem;
	}

	public void setHatItem(ItemStack itemstack) {
		hatItem = itemstack;
	}
}