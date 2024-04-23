package got.client.model;

import got.common.database.GOTArmorModels;
import got.common.item.other.GOTItemTurban;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class GOTModelTurban extends GOTModelRobes {
	private final ModelRenderer ornament;

	public GOTModelTurban(float f) {
		super(f);
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedHead.addBox(-5.0f, -10.0f, -5.0f, 10, 5, 10, 0.0f);
		ModelRenderer shawl = new ModelRenderer(this, 0, 15);
		shawl.addBox(-4.5f, -5.0f, 1.5f, 9, 6, 4, 0.25f);
		shawl.rotateAngleX = 0.22689280275926285f;
		bipedHead.addChild(shawl);
		ornament = new ModelRenderer(this, 0, 0);
		ornament.addBox(-1.0f, -9.0f, -6.0f, 2, 2, 1, 0.0f);
		bipedHead.addChild(ornament);
		bipedHeadwear.cubeList.clear();
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		ornament.showModel = false;
		super.render(entity, f, f1, f2, f3, f4, f5);
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		GOTArmorModels.copyBoxRotations(ornament, bipedHead);
		ornament.showModel = bipedHead.showModel && GOTItemTurban.hasOrnament(robeItem);
		ornament.render(f5);
	}
}