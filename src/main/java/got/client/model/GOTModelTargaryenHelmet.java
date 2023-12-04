package got.client.model;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;

public class GOTModelTargaryenHelmet extends GOTModelBiped {

	public GOTModelTargaryenHelmet() {
		textureWidth = 64;
		textureHeight = 32;

		bipedHead = new ModelRenderer(this);
		bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedHead.cubeList.add(new ModelBox(bipedHead, 0, 0, -4.0f, -8.0f, -4.0f, 8, 8, 8, 1.0f));
		bipedHead.cubeList.add(new ModelBox(bipedHead, 32, 0, -4.0f, -8.0f, -4.0f, 8, 8, 8, 1.5f));

		ModelRenderer crown = new ModelRenderer(this);
		crown.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedHead.addChild(crown);
		crown.cubeList.add(new ModelBox(crown, 0, 16, -5.0f, -15.0f, -5.0f, 10, 6, 10, 0.0f));
		crown.cubeList.add(new ModelBox(crown, 0, 0, -1.0f, -10.0f, -5.0f, 2, 2, 10, -0.01f));
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}