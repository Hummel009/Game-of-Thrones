package got.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class GOTModelWalrus extends ModelBase {
	public ModelRenderer body;
	public ModelRenderer neck;
	public ModelRenderer head;
	public ModelRenderer snout;
	public ModelRenderer tusk1;
	public ModelRenderer flipper1;
	public ModelRenderer flipper2;
	public ModelRenderer tail1;
	public ModelRenderer tusk2;
	public ModelRenderer tail2;
	public ModelRenderer tail3;
	public ModelRenderer flipper3;
	public ModelRenderer flipper4;

	public GOTModelWalrus() {
		textureWidth = 256;
		textureHeight = 64;
		body = new ModelRenderer(this, 180, 0);
		body.addBox(0.0f, 0.0f, 0.0f, 12, 11, 26);
		body.setRotationPoint(-6.0f, 13.0f, -6.5f);
		body.setTextureSize(256, 64);
		body.mirror = true;
		setRotation(body, 0.0f, 0.0f, 0.0f);
		neck = new ModelRenderer(this, 211, 38);
		neck.addBox(0.0f, 0.0f, 0.0f, 10, 8, 12);
		neck.setRotationPoint(-4.533333f, 11.0f, -12.33333f);
		neck.setTextureSize(256, 64);
		neck.mirror = true;
		setRotation(neck, -0.2974289f, 0.0f, 0.0f);
		head = new ModelRenderer(this, 146, 12);
		head.addBox(0.0f, 0.0f, 0.0f, 9, 8, 7);
		head.setRotationPoint(-4.0f, 11.0f, -19.0f);
		head.setTextureSize(256, 64);
		head.mirror = true;
		setRotation(head, 0.0f, 0.0f, 0.0f);
		snout = new ModelRenderer(this, 154, 0);
		snout.addBox(0.0f, 0.0f, 0.0f, 7, 6, 5);
		snout.setRotationPoint(-3.0f, 13.46667f, -23.0f);
		snout.setTextureSize(256, 64);
		snout.mirror = true;
		setRotation(snout, 0.4461433f, 0.0f, 0.0f);
		tusk1 = new ModelRenderer(this, 142, 0);
		tusk1.addBox(0.0f, 0.0f, 0.0f, 1, 5, 1);
		tusk1.setRotationPoint(-2.5f, 16.0f, -21.0f);
		tusk1.setTextureSize(256, 64);
		tusk1.mirror = true;
		setRotation(tusk1, 0.0f, 0.0f, 0.0f);
		flipper1 = new ModelRenderer(this, 0, 0);
		flipper1.addBox(0.0f, 0.0f, 0.0f, 9, 1, 6);
		flipper1.setRotationPoint(5.0f, 20.0f, 0.0f);
		flipper1.setTextureSize(256, 64);
		flipper1.mirror = true;
		setRotation(flipper1, 0.0f, -0.3490659f, 0.3141593f);
		flipper2 = new ModelRenderer(this, 0, 8);
		flipper2.addBox(0.0f, 0.0f, 0.0f, 9, 1, 6);
		flipper2.setRotationPoint(-5.0f, 20.0f, 0.0f);
		flipper2.setTextureSize(256, 64);
		flipper2.mirror = true;
		setRotation(flipper2, 0.0f, -0.3490659f, 2.658271f);
		tail1 = new ModelRenderer(this, 96, 0);
		tail1.addBox(0.0f, 0.0f, 0.0f, 10, 9, 12);
		tail1.setRotationPoint(-5.0f, 14.0f, 19.0f);
		tail1.setTextureSize(256, 64);
		tail1.mirror = true;
		setRotation(tail1, 0.0f, 0.0f, 0.0f);
		tusk2 = new ModelRenderer(this, 148, 0);
		tusk2.addBox(0.0f, 0.0f, 0.0f, 1, 5, 1);
		tusk2.setRotationPoint(2.5f, 16.0f, -21.0f);
		tusk2.setTextureSize(256, 64);
		tusk2.mirror = true;
		setRotation(tusk2, 0.0f, 0.0f, 0.0174533f);
		tail2 = new ModelRenderer(this, 63, 0);
		tail2.addBox(0.0f, 0.0f, 0.0f, 7, 7, 8);
		tail2.setRotationPoint(-3.5f, 15.0f, 30.0f);
		tail2.setTextureSize(256, 64);
		tail2.mirror = true;
		setRotation(tail2, -0.2602503f, 0.0f, 0.0f);
		tail3 = new ModelRenderer(this, 183, 38);
		tail3.addBox(0.0f, 0.0f, 0.0f, 5, 5, 8);
		tail3.setRotationPoint(-2.5f, 18.0f, 36.0f);
		tail3.setTextureSize(256, 64);
		tail3.mirror = true;
		setRotation(tail3, 0.0f, 0.0f, 0.0f);
		flipper3 = new ModelRenderer(this, 147, 28);
		flipper3.addBox(0.0f, 0.0f, 0.0f, 5, 1, 11);
		flipper3.setRotationPoint(-4.0f, 20.4f, 39.0f);
		flipper3.setTextureSize(256, 64);
		flipper3.mirror = true;
		setRotation(flipper3, -0.1487144f, -0.4089647f, 0.0f);
		flipper4 = new ModelRenderer(this, 112, 22);
		flipper4.addBox(0.0f, 0.0f, 0.0f, 5, 1, 11);
		flipper4.setRotationPoint(-1.0f, 20.4f, 41.0f);
		flipper4.setTextureSize(256, 64);
		flipper4.mirror = true;
		setRotation(flipper4, -0.1487144f, 0.3717861f, 0.0f);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		body.render(f5);
		neck.render(f5);
		head.render(f5);
		snout.render(f5);
		tusk1.render(f5);
		flipper1.render(f5);
		flipper2.render(f5);
		tail1.render(f5);
		tusk2.render(f5);
		tail2.render(f5);
		tail3.render(f5);
		flipper3.render(f5);
		flipper4.render(f5);
	}

	public void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
	}
}
