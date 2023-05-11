package got.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class GOTModelDirewolf extends ModelBase {

	ModelRenderer Head;
	ModelRenderer MouthB;
	ModelRenderer Nose2;
	ModelRenderer Neck;
	ModelRenderer Neck2;
	ModelRenderer LSide;
	ModelRenderer RSide;
	ModelRenderer REar2;
	ModelRenderer Nose;
	ModelRenderer Mouth;
	ModelRenderer MouthOpen;
	ModelRenderer REar;
	ModelRenderer LEar2;
	ModelRenderer LEar;

	ModelRenderer Chest;
	ModelRenderer Body;

	ModelRenderer TailA;
	ModelRenderer TailB;
	ModelRenderer TailC;
	ModelRenderer TailD;

	ModelRenderer Leg4A;
	ModelRenderer Leg4D;
	ModelRenderer Leg4B;
	ModelRenderer Leg4C;
	ModelRenderer Leg3B;
	ModelRenderer Leg2A;
	ModelRenderer Leg2B;
	ModelRenderer Leg2C;
	ModelRenderer Leg3D;
	ModelRenderer Leg3C;
	ModelRenderer Leg3A;
	ModelRenderer Leg1A;
	ModelRenderer Leg1B;
	ModelRenderer Leg1C;

	public GOTModelDirewolf() {
		textureWidth = 64;
		textureHeight = 128;

		Head = new ModelRenderer(this, 0, 0);
		Head.addBox(-4.0F, -3.0F, -6.0F, 8, 8, 6);
		Head.setRotationPoint(0.0F, 7.0F, -10.0F);

		MouthB = new ModelRenderer(this, 16, 33);
		MouthB.addBox(-2.0F, 4.0F, -7.0F, 4, 1, 2);
		MouthB.setRotationPoint(0.0F, 7.0F, -10.0F);

		Nose2 = new ModelRenderer(this, 0, 25);
		Nose2.addBox(-2.0F, 2.0F, -12.0F, 4, 2, 6);
		Nose2.setRotationPoint(0.0F, 7.0F, -10.0F);

		Neck = new ModelRenderer(this, 28, 0);
		Neck.addBox(-3.5F, -3.0F, -7.0F, 7, 8, 7);
		Neck.setRotationPoint(0.0F, 10.0F, -6.0F);
		setRotation(Neck, -0.4537856F, 0.0F, 0.0F);

		Neck2 = new ModelRenderer(this, 0, 14);
		Neck2.addBox(-1.5F, -2.0F, -5.0F, 3, 4, 7);
		Neck2.setRotationPoint(0.0F, 14.0F, -10.0F);
		setRotation(Neck2, -0.4537856F, 0.0F, 0.0F);

		LSide = new ModelRenderer(this, 28, 33);
		LSide.addBox(3.0F, -0.5F, -2.0F, 2, 6, 6);
		LSide.setRotationPoint(0.0F, 7.0F, -10.0F);
		setRotation(LSide, -0.2094395F, 0.418879F, -0.0872665F);

		RSide = new ModelRenderer(this, 28, 45);
		RSide.addBox(-5.0F, -0.5F, -2.0F, 2, 6, 6);
		RSide.setRotationPoint(0.0F, 7.0F, -10.0F);
		setRotation(RSide, -0.2094395F, -0.418879F, 0.0872665F);

		Nose = new ModelRenderer(this, 44, 33);
		Nose.addBox(-1.5F, -1.8F, -12.4F, 3, 2, 7);
		Nose.setRotationPoint(0.0F, 7.0F, -10.0F);
		setRotation(Nose, 0.2792527F, 0.0F, 0.0F);

		Mouth = new ModelRenderer(this, 1, 34);
		Mouth.addBox(-2.0F, 4.0F, -11.5F, 4, 1, 5);
		Mouth.setRotationPoint(0.0F, 7.0F, -10.0F);

		MouthOpen = new ModelRenderer(this, 1, 34);
		MouthOpen.addBox(-2.0F, 0.0F, -12.5F, 4, 1, 5);
		MouthOpen.setRotationPoint(0.0F, 7.0F, -10.0F);
		setRotation(MouthOpen, 0.6108652F, 0.0F, 0.0F);

		REar = new ModelRenderer(this, 22, 0);
		REar.addBox(-3.5F, -7.0F, -1.5F, 3, 5, 1);
		REar.setRotationPoint(0.0F, 7.0F, -10.0F);
		setRotation(REar, 0.0F, 0.0F, -0.1745329F);

		LEar = new ModelRenderer(this, 13, 14);
		LEar.addBox(0.5F, -7.0F, -1.5F, 3, 5, 1);
		LEar.setRotationPoint(0.0F, 7.0F, -10.0F);
		setRotation(LEar, 0.0F, 0.0F, 0.1745329F);

		Chest = new ModelRenderer(this, 20, 15);
		Chest.addBox(-4.0F, -11.0F, -12.0F, 8, 8, 10);
		Chest.setRotationPoint(0.0F, 5.0F, 2.0F);
		setRotation(Chest, 1.570796F, 0.0F, 0.0F);

		Body = new ModelRenderer(this, 0, 40);
		Body.addBox(-3.0F, -8.0F, -9.0F, 6, 16, 8);
		Body.setRotationPoint(0.0F, 6.5F, 2.0F);
		setRotation(Body, 1.570796F, 0.0F, 0.0F);

		TailA = new ModelRenderer(this, 52, 42);
		TailA.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3);
		TailA.setRotationPoint(0.0F, 8.5F, 9.0F);
		setRotation(TailA, 1.064651F, 0.0F, 0.0F);

		TailB = new ModelRenderer(this, 48, 49);
		TailB.addBox(-2.0F, 3.0F, -1.0F, 4, 6, 4);
		TailB.setRotationPoint(0.0F, 8.5F, 9.0F);
		setRotation(TailB, 0.7504916F, 0.0F, 0.0F);

		TailC = new ModelRenderer(this, 48, 59);
		TailC.addBox(-2.0F, 7.8F, -4.1F, 4, 6, 4);
		TailC.setRotationPoint(0.0F, 8.5F, 9.0F);
		setRotation(TailC, 1.099557F, 0.0F, 0.0F);

		TailD = new ModelRenderer(this, 52, 69);
		TailD.addBox(-1.5F, 9.8F, -3.6F, 3, 5, 3);
		TailD.setRotationPoint(0.0F, 8.5F, 9.0F);
		setRotation(TailD, 1.099557F, 0.0F, 0.0F);

		Leg1A = new ModelRenderer(this, 28, 57);
		Leg1A.addBox(0.01F, -4.0F, -2.5F, 2, 8, 4);
		Leg1A.setRotationPoint(4.0F, 12.5F, -5.5F);
		setRotation(Leg1A, 0.2617994F, 0.0F, 0.0F);

		Leg1B = new ModelRenderer(this, 28, 69);
		Leg1B.addBox(0.0F, 3.2F, 0.5F, 2, 8, 2);
		Leg1B.setRotationPoint(4.0F, 12.5F, -5.5F);
		setRotation(Leg1B, -0.1745329F, 0.0F, 0.0F);

		Leg1C = new ModelRenderer(this, 28, 79);
		Leg1C.addBox(-0.5066667F, 9.5F, -2.5F, 3, 2, 3);
		Leg1C.setRotationPoint(4.0F, 12.5F, -5.5F);

		Leg2A = new ModelRenderer(this, 28, 84);
		Leg2A.addBox(-2.01F, -4.0F, -2.5F, 2, 8, 4);
		Leg2A.setRotationPoint(-4.0F, 12.5F, -5.5F);
		setRotation(Leg2A, 0.2617994F, 0.0F, 0.0F);

		Leg2B = new ModelRenderer(this, 28, 96);
		Leg2B.addBox(-2.0F, 3.2F, 0.5F, 2, 8, 2);
		Leg2B.setRotationPoint(-4.0F, 12.5F, -5.5F);
		setRotation(Leg2B, -0.1745329F, 0.0F, 0.0F);

		Leg2C = new ModelRenderer(this, 28, 106);
		Leg2C.addBox(-2.506667F, 9.5F, -2.5F, 3, 2, 3);
		Leg2C.setRotationPoint(-4.0F, 12.5F, -5.5F);

		Leg3A = new ModelRenderer(this, 0, 64);
		Leg3A.addBox(0.0F, -3.8F, -3.5F, 2, 7, 5);
		Leg3A.setRotationPoint(3.0F, 12.5F, 7.0F);
		setRotation(Leg3A, -0.3665191F, 0.0F, 0.0F);

		Leg3B = new ModelRenderer(this, 0, 76);
		Leg3B.addBox(-0.1F, 1.9F, -1.8F, 2, 2, 5);
		Leg3B.setRotationPoint(3.0F, 12.5F, 7.0F);
		setRotation(Leg3B, -0.7330383F, 0.0F, 0.0F);

		Leg3C = new ModelRenderer(this, 0, 83);
		Leg3C.addBox(0.0F, 3.2F, 0.0F, 2, 8, 2);
		Leg3C.setRotationPoint(3.0F, 12.5F, 7.0F);
		setRotation(Leg3C, -0.1745329F, 0.0F, 0.0F);

		Leg3D = new ModelRenderer(this, 0, 93);
		Leg3D.addBox(-0.5066667F, 9.5F, -3.0F, 3, 2, 3);
		Leg3D.setRotationPoint(3.0F, 12.5F, 7.0F);

		Leg4A = new ModelRenderer(this, 14, 64);
		Leg4A.addBox(-2.0F, -3.8F, -3.5F, 2, 7, 5);
		Leg4A.setRotationPoint(-3.0F, 12.5F, 7.0F);
		setRotation(Leg4A, -0.3665191F, 0.0F, 0.0F);

		Leg4B = new ModelRenderer(this, 14, 76);
		Leg4B.addBox(-1.9F, 1.9F, -1.8F, 2, 2, 5);
		Leg4B.setRotationPoint(-3.0F, 12.5F, 7.0F);
		setRotation(Leg4B, -0.7330383F, 0.0F, 0.0F);

		Leg4C = new ModelRenderer(this, 14, 83);
		Leg4C.addBox(-2.0F, 3.2F, 0.0F, 2, 8, 2);
		Leg4C.setRotationPoint(-3.0F, 12.5F, 7.0F);
		setRotation(Leg4C, -0.1745329F, 0.0F, 0.0F);

		Leg4D = new ModelRenderer(this, 14, 93);
		Leg4D.addBox(-2.506667F, 9.5F, -3.0F, 3, 2, 3);
		Leg4D.setRotationPoint(-3.0F, 12.5F, 7.0F);

	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, true);
		Head.render(f5);
		Nose2.render(f5);
		Neck.render(f5);
		Neck2.render(f5);
		LSide.render(f5);
		RSide.render(f5);
		Nose.render(f5);
		Mouth.render(f5);
		MouthB.render(f5);
		REar.render(f5);
		LEar.render(f5);
		Chest.render(f5);
		Body.render(f5);
		TailA.render(f5);
		TailB.render(f5);
		TailC.render(f5);
		TailD.render(f5);
		Leg4A.render(f5);
		Leg4D.render(f5);
		Leg4B.render(f5);
		Leg4C.render(f5);
		Leg3B.render(f5);
		Leg2A.render(f5);
		Leg2B.render(f5);
		Leg2C.render(f5);
		Leg3D.render(f5);
		Leg3C.render(f5);
		Leg3A.render(f5);
		Leg1A.render(f5);
		Leg1B.render(f5);
		Leg1C.render(f5);

	}

	public void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, boolean tail) {

		Head.rotateAngleX = f4 / 57.29578F;
		Head.rotateAngleY = f3 / 57.29578F;
		float LLegX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		float RLegX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;

		Mouth.rotateAngleX = Head.rotateAngleX;
		Mouth.rotateAngleY = Head.rotateAngleY;
		MouthB.rotateAngleX = Head.rotateAngleX;
		MouthB.rotateAngleY = Head.rotateAngleY;
		MouthOpen.rotateAngleX = 35 / 57.29578F + Head.rotateAngleX;
		MouthOpen.rotateAngleY = Head.rotateAngleY;
		Nose.rotateAngleX = 16 / 57.29578F + Head.rotateAngleX;
		Nose.rotateAngleY = Head.rotateAngleY;
		Nose2.rotateAngleX = Head.rotateAngleX;
		Nose2.rotateAngleY = Head.rotateAngleY;

		LSide.rotateAngleX = -12 / 57.29578F + Head.rotateAngleX;
		LSide.rotateAngleY = 24 / 57.29578F + Head.rotateAngleY;
		RSide.rotateAngleX = -12 / 57.29578F + Head.rotateAngleX;
		RSide.rotateAngleY = -24 / 57.29578F + Head.rotateAngleY;

		REar.rotateAngleX = Head.rotateAngleX;
		REar.rotateAngleY = Head.rotateAngleY;
		LEar.rotateAngleX = Head.rotateAngleX;
		LEar.rotateAngleY = Head.rotateAngleY;

		Leg1A.rotateAngleX = 15 / 57.29578F + LLegX;
		Leg1B.rotateAngleX = -10 / 57.29578F + LLegX;
		Leg1C.rotateAngleX = LLegX;

		Leg2A.rotateAngleX = 15 / 57.29578F + RLegX;
		Leg2B.rotateAngleX = -10 / 57.29578F + RLegX;
		Leg2C.rotateAngleX = RLegX;

		Leg3A.rotateAngleX = -21 / 57.29578F + RLegX;
		Leg3B.rotateAngleX = -42 / 57.29578F + RLegX;
		Leg3C.rotateAngleX = -10 / 57.29578F + RLegX;
		Leg3D.rotateAngleX = RLegX;

		Leg4A.rotateAngleX = -21 / 57.29578F + LLegX;
		Leg4B.rotateAngleX = -42 / 57.29578F + LLegX;
		Leg4C.rotateAngleX = -10 / 57.29578F + LLegX;
		Leg4D.rotateAngleX = LLegX;

		float tailMov = -1.3089F + f1 * 1.5F;

		if (tail) {
			TailA.rotateAngleY = 0.0F;
			tailMov = 0;
		} else {
			TailA.rotateAngleY = 0.0F;
		}

		TailA.rotateAngleX = 61 / 57.29F - tailMov;// -1.3089F+(f1*1.5F);
		TailB.rotateAngleX = 43 / 57.29F - tailMov;// -1.3089F+(f1*1.5F);
		TailC.rotateAngleX = 63 / 57.29F - tailMov;// -1.5707F -tailMov;
		TailD.rotateAngleX = 63 / 57.29F - tailMov;

		TailB.rotateAngleY = TailA.rotateAngleY;
		TailC.rotateAngleY = TailA.rotateAngleY;
		TailD.rotateAngleY = TailA.rotateAngleY;
	}

}