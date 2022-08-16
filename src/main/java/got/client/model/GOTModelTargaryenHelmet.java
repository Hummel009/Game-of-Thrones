package got.client.model;

import net.minecraft.client.model.*;

public class GOTModelTargaryenHelmet extends GOTModelBiped {
	public ModelRenderer crown;

	public GOTModelTargaryenHelmet() {
		textureWidth = 64;
		textureHeight = 32;

		bipedHead = new ModelRenderer(this);
		bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		bipedHead.cubeList.add(new ModelBox(bipedHead, 0, 0, -4.0F, -8.0F, -4.0F, 8, 8, 8, 1.0F));
		bipedHead.cubeList.add(new ModelBox(bipedHead, 32, 0, -4.0F, -8.0F, -4.0F, 8, 8, 8, 1.5F));

		crown = new ModelRenderer(this);
		crown.setRotationPoint(0.0F, 0.0F, 0.0F);
		bipedHead.addChild(crown);
		crown.cubeList.add(new ModelBox(crown, 0, 16, -5.0F, -15.0F, -5.0F, 10, 6, 10, 0.0F));
		crown.cubeList.add(new ModelBox(crown, 0, 0, -1.0F, -10.0F, -5.0F, 2, 2, 10, -0.01F));
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}