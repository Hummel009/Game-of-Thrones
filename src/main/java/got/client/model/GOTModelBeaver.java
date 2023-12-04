package got.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class GOTModelBeaver extends ModelBase {
	private final ModelRenderer bober;
	private final ModelRenderer head;
	private final ModelRenderer leg1;
	private final ModelRenderer leg2;
	private final ModelRenderer leg3;
	private final ModelRenderer leg4;

	public GOTModelBeaver() {
		textureWidth = 64;
		textureHeight = 64;

		bober = new ModelRenderer(this);
		bober.setRotationPoint(0.0f, 24.0f, 0.0f);

		ModelRenderer body = new ModelRenderer(this);
		body.setRotationPoint(0.0f, -7.0f, -0.5f);
		bober.addChild(body);
		body.cubeList.add(new ModelBox(body, 0, 0, -4.0f, -4.0f, -5.5f, 8, 8, 11, 0.0f));

		ModelRenderer tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0f, 2.0f, 5.5f);
		body.addChild(tail);
		setRotationAngle(tail, -0.2618f, 0.0f, 0.0f);
		tail.cubeList.add(new ModelBox(tail, 0, 19, -4.0f, -1.0f, -0.5f, 8, 2, 11, -0.01f));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0f, -8.0f, -5.5f);
		bober.addChild(head);
		head.cubeList.add(new ModelBox(head, 27, 0, -3.0f, -2.0f, -5.0f, 6, 6, 5, 0.0f));
		head.cubeList.add(new ModelBox(head, 27, 19, -2.0f, 1.0f, -6.0f, 4, 2, 1, 0.0f));
		head.cubeList.add(new ModelBox(head, 0, 10, -1.0f, 3.0f, -6.0f, 2, 1, 0, 0.0f));

		ModelRenderer ears = new ModelRenderer(this);
		ears.setRotationPoint(0.0f, 0.0f, 0.0f);
		head.addChild(ears);

		ModelRenderer ear1 = new ModelRenderer(this);
		ear1.setRotationPoint(-2.0f, -2.0f, -3.0f);
		ears.addChild(ear1);
		setRotationAngle(ear1, -0.0873f, 0.2618f, 0.0f);
		ear1.cubeList.add(new ModelBox(ear1, 6, 0, -2.0f, -1.0f, 0.0f, 2, 2, 0, 0.0f));

		ModelRenderer ear2 = new ModelRenderer(this);
		ear2.setRotationPoint(2.0f, -2.0f, -3.0f);
		ears.addChild(ear2);
		setRotationAngle(ear2, -0.0873f, -0.2618f, 0.0f);
		ear2.cubeList.add(new ModelBox(ear2, 6, 5, 0.0f, -1.0f, 0.0f, 2, 2, 0, 0.0f));

		leg1 = new ModelRenderer(this);
		leg1.setRotationPoint(2.0f, -3.0f, 3.0f);
		bober.addChild(leg1);
		leg1.cubeList.add(new ModelBox(leg1, 0, 19, -1.0f, -1.0f, -1.0f, 2, 4, 2, 0.0f));

		leg2 = new ModelRenderer(this);
		leg2.setRotationPoint(-2.0f, -3.0f, 3.0f);
		bober.addChild(leg2);
		leg2.cubeList.add(new ModelBox(leg2, 0, 19, -1.0f, -1.0f, -1.0f, 2, 4, 2, 0.0f));

		leg3 = new ModelRenderer(this);
		leg3.setRotationPoint(-2.0f, -3.0f, -4.0f);
		bober.addChild(leg3);
		leg3.cubeList.add(new ModelBox(leg3, 0, 19, -1.0f, -1.0f, -1.0f, 2, 4, 2, 0.0f));

		leg4 = new ModelRenderer(this);
		leg4.setRotationPoint(2.0f, -3.0f, -4.0f);
		bober.addChild(leg4);
		leg4.cubeList.add(new ModelBox(leg4, 0, 19, -1.0f, -1.0f, -1.0f, 2, 4, 2, 0.0f));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		bober.render(f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		head.rotateAngleX = (float) Math.toRadians(f4);
		head.rotateAngleY = (float) Math.toRadians(f3);
		leg1.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f1;
		leg2.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
		leg3.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
		leg4.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f1;
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}