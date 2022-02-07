package got.client.model;

import org.lwjgl.opengl.GL11;

import got.common.item.other.GOTItemPartyHat;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class GOTModelPartyHat extends GOTModelBiped {
	private ItemStack hatItem;

	public GOTModelPartyHat(float f) {
		super(f);
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedHead.addBox(-4.0f, -14.0f, -4.0f, 8, 8, 8, f);
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
		int hatColor = GOTItemPartyHat.getHatColor(hatItem);
		float r = (hatColor >> 16 & 0xFF) / 255.0f;
		float g = (hatColor >> 8 & 0xFF) / 255.0f;
		float b = (hatColor & 0xFF) / 255.0f;
		GL11.glColor3f(r, g, b);
		bipedHead.render(f5);
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		GL11.glPopMatrix();
	}

	public void setHatItem(ItemStack itemstack) {
		hatItem = itemstack;
	}
}
