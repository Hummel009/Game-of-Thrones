package got.client.model;

import net.minecraft.client.model.ModelPig;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class GOTModelBoar extends ModelPig {
	private final ModelRenderer tusks;

	public GOTModelBoar() {
		this(0.0f);
	}

	public GOTModelBoar(float f) {
		super(f);
		head.setTextureOffset(24, 0).addBox(-3.0f, 0.0f, -10.0f, 6, 4, 2, f);
		head.setTextureOffset(40, 0).addBox(-5.0f, -5.0f, -6.0f, 1, 2, 2, f);
		head.mirror = true;
		head.addBox(4.0f, -5.0f, -6.0f, 1, 2, 2, f);
		tusks = new ModelRenderer(this, 0, 0);
		tusks.addBox(-4.0f, 2.0f, -11.0f, 1, 1, 2, f);
		tusks.setTextureOffset(1, 1).addBox(-4.0f, 1.0f, -11.5f, 1, 1, 1, f);
		tusks.mirror = true;
		tusks.setTextureOffset(0, 0).addBox(3.0f, 2.0f, -11.0f, 1, 1, 2, f);
		tusks.setTextureOffset(1, 1).addBox(3.0f, 1.0f, -11.5f, 1, 1, 1, f);
		head.addChild(tusks);
	}

	@Override
	public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
		tusks.showModel = !isChild;
		setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
		head.render(p_78088_7_);
		body.render(p_78088_7_);
		leg1.render(p_78088_7_);
		leg2.render(p_78088_7_);
		leg3.render(p_78088_7_);
		leg4.render(p_78088_7_);
	}
}
