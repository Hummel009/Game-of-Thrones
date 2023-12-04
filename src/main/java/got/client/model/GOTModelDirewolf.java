package got.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class GOTModelDirewolf extends ModelBase {
	public ModelRenderer Head;
	public ModelRenderer MouthB;
	public ModelRenderer Nose2;
	public ModelRenderer Neck;
	public ModelRenderer Neck2;
	public ModelRenderer LSide;
	public ModelRenderer RSide;
	public ModelRenderer REar2;
	public ModelRenderer Nose;
	public ModelRenderer Mouth;
	public ModelRenderer MouthOpen;
	public ModelRenderer REar;
	public ModelRenderer LEar2;
	public ModelRenderer LEar;

	public ModelRenderer Chest;
	public ModelRenderer Body;

	public ModelRenderer TailA;
	public ModelRenderer TailB;
	public ModelRenderer TailC;
	public ModelRenderer TailD;

	public ModelRenderer Leg4A;
	public ModelRenderer Leg4D;
	public ModelRenderer Leg4B;
	public ModelRenderer Leg4C;
	public ModelRenderer Leg3B;
	public ModelRenderer Leg2A;
	public ModelRenderer Leg2B;
	public ModelRenderer Leg2C;
	public ModelRenderer Leg3D;
	public ModelRenderer Leg3C;
	public ModelRenderer Leg3A;
	public ModelRenderer Leg1A;
	public ModelRenderer Leg1B;
	public ModelRenderer Leg1C;

	public GOTModelDirewolf() {
		textureWidth = 64;
		textureHeight = 128;

		Head = new ModelRenderer(this, 0, 0);
		Head.addBox(-4.0f, -3.0f, -6.0f, 8, 8, 6);
		Head.setRotationPoint(0.0f, 7.0f, -10.0f);

		MouthB = new ModelRenderer(this, 16, 33);
		MouthB.addBox(-2.0f, 4.0f, -7.0f, 4, 1, 2);
		MouthB.setRotationPoint(0.0f, 7.0f, -10.0f);

		Nose2 = new ModelRenderer(this, 0, 25);
		Nose2.addBox(-2.0f, 2.0f, -12.0f, 4, 2, 6);
		Nose2.setRotationPoint(0.0f, 7.0f, -10.0f);

		Neck = new ModelRenderer(this, 28, 0);
		Neck.addBox(-3.5f, -3.0f, -7.0f, 7, 8, 7);
		Neck.setRotationPoint(0.0f, 10.0f, -6.0f);
		setRotation(Neck, -0.4537856f, 0.0f, 0.0f);

		Neck2 = new ModelRenderer(this, 0, 14);
		Neck2.addBox(-1.5f, -2.0f, -5.0f, 3, 4, 7);
		Neck2.setRotationPoint(0.0f, 14.0f, -10.0f);
		setRotation(Neck2, -0.4537856f, 0.0f, 0.0f);

		LSide = new ModelRenderer(this, 28, 33);
		LSide.addBox(3.0f, -0.5f, -2.0f, 2, 6, 6);
		LSide.setRotationPoint(0.0f, 7.0f, -10.0f);
		setRotation(LSide, -0.2094395f, 0.418879f, -0.0872665f);

		RSide = new ModelRenderer(this, 28, 45);
		RSide.addBox(-5.0f, -0.5f, -2.0f, 2, 6, 6);
		RSide.setRotationPoint(0.0f, 7.0f, -10.0f);
		setRotation(RSide, -0.2094395f, -0.418879f, 0.0872665f);

		Nose = new ModelRenderer(this, 44, 33);
		Nose.addBox(-1.5f, -1.8f, -12.4f, 3, 2, 7);
		Nose.setRotationPoint(0.0f, 7.0f, -10.0f);
		setRotation(Nose, 0.2792527f, 0.0f, 0.0f);

		Mouth = new ModelRenderer(this, 1, 34);
		Mouth.addBox(-2.0f, 4.0f, -11.5f, 4, 1, 5);
		Mouth.setRotationPoint(0.0f, 7.0f, -10.0f);

		MouthOpen = new ModelRenderer(this, 1, 34);
		MouthOpen.addBox(-2.0f, 0.0f, -12.5f, 4, 1, 5);
		MouthOpen.setRotationPoint(0.0f, 7.0f, -10.0f);
		setRotation(MouthOpen, 0.6108652f, 0.0f, 0.0f);

		REar = new ModelRenderer(this, 22, 0);
		REar.addBox(-3.5f, -7.0f, -1.5f, 3, 5, 1);
		REar.setRotationPoint(0.0f, 7.0f, -10.0f);
		setRotation(REar, 0.0f, 0.0f, -0.1745329f);

		LEar = new ModelRenderer(this, 13, 14);
		LEar.addBox(0.5f, -7.0f, -1.5f, 3, 5, 1);
		LEar.setRotationPoint(0.0f, 7.0f, -10.0f);
		setRotation(LEar, 0.0f, 0.0f, 0.1745329f);

		Chest = new ModelRenderer(this, 20, 15);
		Chest.addBox(-4.0f, -11.0f, -12.0f, 8, 8, 10);
		Chest.setRotationPoint(0.0f, 5.0f, 2.0f);
		setRotation(Chest, 1.570796f, 0.0f, 0.0f);

		Body = new ModelRenderer(this, 0, 40);
		Body.addBox(-3.0f, -8.0f, -9.0f, 6, 16, 8);
		Body.setRotationPoint(0.0f, 6.5f, 2.0f);
		setRotation(Body, 1.570796f, 0.0f, 0.0f);

		TailA = new ModelRenderer(this, 52, 42);
		TailA.addBox(-1.5f, 0.0f, -1.5f, 3, 4, 3);
		TailA.setRotationPoint(0.0f, 8.5f, 9.0f);
		setRotation(TailA, 1.064651f, 0.0f, 0.0f);

		TailB = new ModelRenderer(this, 48, 49);
		TailB.addBox(-2.0f, 3.0f, -1.0f, 4, 6, 4);
		TailB.setRotationPoint(0.0f, 8.5f, 9.0f);
		setRotation(TailB, 0.7504916f, 0.0f, 0.0f);

		TailC = new ModelRenderer(this, 48, 59);
		TailC.addBox(-2.0f, 7.8f, -4.1f, 4, 6, 4);
		TailC.setRotationPoint(0.0f, 8.5f, 9.0f);
		setRotation(TailC, 1.099557f, 0.0f, 0.0f);

		TailD = new ModelRenderer(this, 52, 69);
		TailD.addBox(-1.5f, 9.8f, -3.6f, 3, 5, 3);
		TailD.setRotationPoint(0.0f, 8.5f, 9.0f);
		setRotation(TailD, 1.099557f, 0.0f, 0.0f);

		Leg1A = new ModelRenderer(this, 28, 57);
		Leg1A.addBox(0.01f, -4.0f, -2.5f, 2, 8, 4);
		Leg1A.setRotationPoint(4.0f, 12.5f, -5.5f);
		setRotation(Leg1A, 0.2617994f, 0.0f, 0.0f);

		Leg1B = new ModelRenderer(this, 28, 69);
		Leg1B.addBox(0.0f, 3.2f, 0.5f, 2, 8, 2);
		Leg1B.setRotationPoint(4.0f, 12.5f, -5.5f);
		setRotation(Leg1B, -0.1745329f, 0.0f, 0.0f);

		Leg1C = new ModelRenderer(this, 28, 79);
		Leg1C.addBox(-0.5066667f, 9.5f, -2.5f, 3, 2, 3);
		Leg1C.setRotationPoint(4.0f, 12.5f, -5.5f);

		Leg2A = new ModelRenderer(this, 28, 84);
		Leg2A.addBox(-2.01f, -4.0f, -2.5f, 2, 8, 4);
		Leg2A.setRotationPoint(-4.0f, 12.5f, -5.5f);
		setRotation(Leg2A, 0.2617994f, 0.0f, 0.0f);

		Leg2B = new ModelRenderer(this, 28, 96);
		Leg2B.addBox(-2.0f, 3.2f, 0.5f, 2, 8, 2);
		Leg2B.setRotationPoint(-4.0f, 12.5f, -5.5f);
		setRotation(Leg2B, -0.1745329f, 0.0f, 0.0f);

		Leg2C = new ModelRenderer(this, 28, 106);
		Leg2C.addBox(-2.506667f, 9.5f, -2.5f, 3, 2, 3);
		Leg2C.setRotationPoint(-4.0f, 12.5f, -5.5f);

		Leg3A = new ModelRenderer(this, 0, 64);
		Leg3A.addBox(0.0f, -3.8f, -3.5f, 2, 7, 5);
		Leg3A.setRotationPoint(3.0f, 12.5f, 7.0f);
		setRotation(Leg3A, -0.3665191f, 0.0f, 0.0f);

		Leg3B = new ModelRenderer(this, 0, 76);
		Leg3B.addBox(-0.1f, 1.9f, -1.8f, 2, 2, 5);
		Leg3B.setRotationPoint(3.0f, 12.5f, 7.0f);
		setRotation(Leg3B, -0.7330383f, 0.0f, 0.0f);

		Leg3C = new ModelRenderer(this, 0, 83);
		Leg3C.addBox(0.0f, 3.2f, 0.0f, 2, 8, 2);
		Leg3C.setRotationPoint(3.0f, 12.5f, 7.0f);
		setRotation(Leg3C, -0.1745329f, 0.0f, 0.0f);

		Leg3D = new ModelRenderer(this, 0, 93);
		Leg3D.addBox(-0.5066667f, 9.5f, -3.0f, 3, 2, 3);
		Leg3D.setRotationPoint(3.0f, 12.5f, 7.0f);

		Leg4A = new ModelRenderer(this, 14, 64);
		Leg4A.addBox(-2.0f, -3.8f, -3.5f, 2, 7, 5);
		Leg4A.setRotationPoint(-3.0f, 12.5f, 7.0f);
		setRotation(Leg4A, -0.3665191f, 0.0f, 0.0f);

		Leg4B = new ModelRenderer(this, 14, 76);
		Leg4B.addBox(-1.9f, 1.9f, -1.8f, 2, 2, 5);
		Leg4B.setRotationPoint(-3.0f, 12.5f, 7.0f);
		setRotation(Leg4B, -0.7330383f, 0.0f, 0.0f);

		Leg4C = new ModelRenderer(this, 14, 83);
		Leg4C.addBox(-2.0f, 3.2f, 0.0f, 2, 8, 2);
		Leg4C.setRotationPoint(-3.0f, 12.5f, 7.0f);
		setRotation(Leg4C, -0.1745329f, 0.0f, 0.0f);

		Leg4D = new ModelRenderer(this, 14, 93);
		Leg4D.addBox(-2.506667f, 9.5f, -3.0f, 3, 2, 3);
		Leg4D.setRotationPoint(-3.0f, 12.5f, 7.0f);

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

		Head.rotateAngleX = f4 / 57.29578f;
		Head.rotateAngleY = f3 / 57.29578f;
		float LLegX = MathHelper.cos(f * 0.6662f) * 1.4f * f1;
		float RLegX = MathHelper.cos(f * 0.6662f + 3.141593f) * 1.4f * f1;

		Mouth.rotateAngleX = Head.rotateAngleX;
		Mouth.rotateAngleY = Head.rotateAngleY;
		MouthB.rotateAngleX = Head.rotateAngleX;
		MouthB.rotateAngleY = Head.rotateAngleY;
		MouthOpen.rotateAngleX = 0.61086524f + Head.rotateAngleX;
		MouthOpen.rotateAngleY = Head.rotateAngleY;
		Nose.rotateAngleX = 0.27925268f + Head.rotateAngleX;
		Nose.rotateAngleY = Head.rotateAngleY;
		Nose2.rotateAngleX = Head.rotateAngleX;
		Nose2.rotateAngleY = Head.rotateAngleY;

		LSide.rotateAngleX = -0.2094395f + Head.rotateAngleX;
		LSide.rotateAngleY = 0.418879f + Head.rotateAngleY;
		RSide.rotateAngleX = -0.2094395f + Head.rotateAngleX;
		RSide.rotateAngleY = -0.418879f + Head.rotateAngleY;

		REar.rotateAngleX = Head.rotateAngleX;
		REar.rotateAngleY = Head.rotateAngleY;
		LEar.rotateAngleX = Head.rotateAngleX;
		LEar.rotateAngleY = Head.rotateAngleY;

		Leg1A.rotateAngleX = 0.2617994f + LLegX;
		Leg1B.rotateAngleX = -0.17453292f + LLegX;
		Leg1C.rotateAngleX = LLegX;

		Leg2A.rotateAngleX = 0.2617994f + RLegX;
		Leg2B.rotateAngleX = -0.17453292f + RLegX;
		Leg2C.rotateAngleX = RLegX;

		Leg3A.rotateAngleX = -0.36651915f + RLegX;
		Leg3B.rotateAngleX = -0.7330383f + RLegX;
		Leg3C.rotateAngleX = -0.17453292f + RLegX;
		Leg3D.rotateAngleX = RLegX;

		Leg4A.rotateAngleX = -0.36651915f + LLegX;
		Leg4B.rotateAngleX = -0.7330383f + LLegX;
		Leg4C.rotateAngleX = -0.17453292f + LLegX;
		Leg4D.rotateAngleX = LLegX;

		float tailMov = -1.3089f + f1 * 1.5f;

		if (tail) {
			TailA.rotateAngleY = 0.0f;
			tailMov = 0;
		} else {
			TailA.rotateAngleY = 0.0f;
		}

		TailA.rotateAngleX = 1.0647582f - tailMov;// -1.3089f+(f1*1.5f);
		TailB.rotateAngleX = 0.75056726f - tailMov;// -1.3089f+(f1*1.5f);
		TailC.rotateAngleX = 1.0996684f - tailMov;// -1.5707f -tailMov;
		TailD.rotateAngleX = 1.0996684f - tailMov;

		TailB.rotateAngleY = TailA.rotateAngleY;
		TailC.rotateAngleY = TailA.rotateAngleY;
		TailD.rotateAngleY = TailA.rotateAngleY;
	}

}