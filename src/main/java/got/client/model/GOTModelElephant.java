package got.client.model;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

@SideOnly(value = Side.CLIENT)
public class GOTModelElephant extends ModelBase {
	ModelRenderer Head;
	ModelRenderer Neck;
	ModelRenderer HeadBump;
	ModelRenderer Chin;
	ModelRenderer LowerLip;
	ModelRenderer Back;
	ModelRenderer LeftSmallEar;
	ModelRenderer LeftBigEar;
	ModelRenderer RightSmallEar;
	ModelRenderer RightBigEar;
	ModelRenderer Hump;
	ModelRenderer Body;
	ModelRenderer Skirt;
	ModelRenderer RightTuskA;
	ModelRenderer RightTuskB;
	ModelRenderer RightTuskC;
	ModelRenderer RightTuskD;
	ModelRenderer LeftTuskA;
	ModelRenderer LeftTuskB;
	ModelRenderer LeftTuskC;
	ModelRenderer LeftTuskD;
	ModelRenderer TrunkA;
	ModelRenderer TrunkB;
	ModelRenderer TrunkC;
	ModelRenderer TrunkD;
	ModelRenderer TrunkE;
	ModelRenderer FrontRightUpperLeg;
	ModelRenderer FrontRightLowerLeg;
	ModelRenderer FrontLeftUpperLeg;
	ModelRenderer FrontLeftLowerLeg;
	ModelRenderer BackRightUpperLeg;
	ModelRenderer BackRightLowerLeg;
	ModelRenderer BackLeftUpperLeg;
	ModelRenderer BackLeftLowerLeg;
	ModelRenderer TailRoot;
	ModelRenderer Tail;
	ModelRenderer TailPlush;
	float radianF = 57.29578f;

	public GOTModelElephant() {
		textureWidth = 128;
		textureHeight = 256;
		Head = new ModelRenderer(this, 60, 0);
		Head.addBox(-5.5f, -6.0f, -8.0f, 11, 15, 10);
		Head.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(Head, -0.1745329f, 0.0f, 0.0f);
		Neck = new ModelRenderer(this, 46, 48);
		Neck.addBox(-4.95f, -6.0f, -8.0f, 10, 14, 8);
		Neck.setRotationPoint(0.0f, -8.0f, -10.0f);
		setRotation(Neck, -0.2617994f, 0.0f, 0.0f);
		HeadBump = new ModelRenderer(this, 104, 41);
		HeadBump.addBox(-3.0f, -9.0f, -6.0f, 6, 3, 6);
		HeadBump.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(HeadBump, -0.1745329f, 0.0f, 0.0f);
		Chin = new ModelRenderer(this, 86, 56);
		Chin.addBox(-1.5f, -6.0f, -10.7f, 3, 5, 4);
		Chin.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(Chin, 2.054118f, 0.0f, 0.0f);
		LowerLip = new ModelRenderer(this, 80, 65);
		LowerLip.addBox(-2.0f, -2.0f, -14.0f, 4, 2, 6);
		LowerLip.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(LowerLip, 1.570796f, 0.0f, 0.0f);
		Back = new ModelRenderer(this, 0, 48);
		Back.addBox(-5.0f, -10.0f, -10.0f, 10, 2, 26);
		Back.setRotationPoint(0.0f, -4.0f, -3.0f);
		LeftSmallEar = new ModelRenderer(this, 102, 0);
		LeftSmallEar.addBox(2.0f, -8.0f, -5.0f, 8, 10, 1);
		LeftSmallEar.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(LeftSmallEar, -0.1745329f, -0.5235988f, 0.5235988f);
		LeftBigEar = new ModelRenderer(this, 102, 0);
		LeftBigEar.addBox(2.0f, -8.0f, -5.0f, 12, 14, 1);
		LeftBigEar.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(LeftBigEar, -0.1745329f, -0.5235988f, 0.5235988f);
		RightSmallEar = new ModelRenderer(this, 106, 15);
		RightSmallEar.addBox(-10.0f, -8.0f, -5.0f, 8, 10, 1);
		RightSmallEar.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(RightSmallEar, -0.1745329f, 0.5235988f, -0.5235988f);
		RightBigEar = new ModelRenderer(this, 102, 15);
		RightBigEar.addBox(-14.0f, -8.0f, -5.0f, 12, 14, 1);
		RightBigEar.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(RightBigEar, -0.1745329f, 0.5235988f, -0.5235988f);
		Hump = new ModelRenderer(this, 88, 30);
		Hump.addBox(-6.0f, -2.0f, -3.0f, 12, 3, 8);
		Hump.setRotationPoint(0.0f, -13.0f, -5.5f);
		Body = new ModelRenderer(this, 0, 0);
		Body.addBox(-8.0f, -10.0f, -10.0f, 16, 20, 28);
		Body.setRotationPoint(0.0f, -2.0f, -3.0f);
		Skirt = new ModelRenderer(this, 28, 94);
		Skirt.addBox(-8.0f, -10.0f, -6.0f, 16, 28, 6);
		Skirt.setRotationPoint(0.0f, 8.0f, -3.0f);
		setRotation(Skirt, 1.570796f, 0.0f, 0.0f);
		RightTuskA = new ModelRenderer(this, 2, 60);
		RightTuskA.addBox(-3.8f, -3.5f, -19.0f, 2, 2, 10);
		RightTuskA.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(RightTuskA, 1.22173f, 0.0f, 0.1745329f);
		RightTuskB = new ModelRenderer(this, 0, 0);
		RightTuskB.addBox(-3.8f, 6.2f, -24.2f, 2, 2, 7);
		RightTuskB.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(RightTuskB, 0.6981317f, 0.0f, 0.1745329f);
		RightTuskC = new ModelRenderer(this, 0, 18);
		RightTuskC.addBox(-3.8f, 17.1f, -21.9f, 2, 2, 5);
		RightTuskC.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(RightTuskC, 0.1745329f, 0.0f, 0.1745329f);
		RightTuskD = new ModelRenderer(this, 14, 18);
		RightTuskD.addBox(-3.8f, 25.5f, -14.5f, 2, 2, 5);
		RightTuskD.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(RightTuskD, -0.3490659f, 0.0f, 0.1745329f);
		LeftTuskA = new ModelRenderer(this, 2, 48);
		LeftTuskA.addBox(1.8f, -3.5f, -19.0f, 2, 2, 10);
		LeftTuskA.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(LeftTuskA, 1.22173f, 0.0f, -0.1745329f);
		LeftTuskB = new ModelRenderer(this, 0, 9);
		LeftTuskB.addBox(1.8f, 6.2f, -24.2f, 2, 2, 7);
		LeftTuskB.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(LeftTuskB, 0.6981317f, 0.0f, -0.1745329f);
		LeftTuskC = new ModelRenderer(this, 0, 18);
		LeftTuskC.addBox(1.8f, 17.1f, -21.9f, 2, 2, 5);
		LeftTuskC.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(LeftTuskC, 0.1745329f, 0.0f, -0.1745329f);
		LeftTuskD = new ModelRenderer(this, 14, 18);
		LeftTuskD.addBox(1.8f, 25.5f, -14.5f, 2, 2, 5);
		LeftTuskD.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(LeftTuskD, -0.3490659f, 0.0f, -0.1745329f);
		TrunkA = new ModelRenderer(this, 0, 76);
		TrunkA.addBox(-4.0f, -2.5f, -18.0f, 8, 7, 10);
		TrunkA.setRotationPoint(0.0f, -3.0f, -22.46667f);
		setRotation(TrunkA, 1.570796f, 0.0f, 0.0f);
		TrunkB = new ModelRenderer(this, 0, 93);
		TrunkB.addBox(-3.0f, -2.5f, -7.0f, 6, 5, 7);
		TrunkB.setRotationPoint(0.0f, 6.5f, -22.5f);
		setRotation(TrunkB, 1.658063f, 0.0f, 0.0f);
		TrunkC = new ModelRenderer(this, 0, 105);
		TrunkC.addBox(-2.5f, -2.0f, -4.0f, 5, 4, 5);
		TrunkC.setRotationPoint(0.0f, 13.0f, -22.0f);
		setRotation(TrunkC, 1.919862f, 0.0f, 0.0f);
		TrunkD = new ModelRenderer(this, 0, 114);
		TrunkD.addBox(-2.0f, -1.5f, -5.0f, 4, 3, 5);
		TrunkD.setRotationPoint(0.0f, 16.0f, -21.5f);
		setRotation(TrunkD, 2.216568f, 0.0f, 0.0f);
		TrunkE = new ModelRenderer(this, 0, 122);
		TrunkE.addBox(-1.5f, -1.0f, -4.0f, 3, 2, 4);
		TrunkE.setRotationPoint(0.0f, 19.5f, -19.0f);
		setRotation(TrunkE, 2.530727f, 0.0f, 0.0f);
		FrontRightUpperLeg = new ModelRenderer(this, 100, 109);
		FrontRightUpperLeg.addBox(-3.5f, 0.0f, -3.5f, 7, 12, 7);
		FrontRightUpperLeg.setRotationPoint(-4.6f, 4.0f, -9.6f);
		FrontRightLowerLeg = new ModelRenderer(this, 100, 73);
		FrontRightLowerLeg.addBox(-3.5f, 0.0f, -3.5f, 7, 10, 7);
		FrontRightLowerLeg.setRotationPoint(-4.6f, 14.0f, -9.6f);
		FrontLeftUpperLeg = new ModelRenderer(this, 100, 90);
		FrontLeftUpperLeg.addBox(-3.5f, 0.0f, -3.5f, 7, 12, 7);
		FrontLeftUpperLeg.setRotationPoint(4.6f, 4.0f, -9.6f);
		FrontLeftLowerLeg = new ModelRenderer(this, 72, 73);
		FrontLeftLowerLeg.addBox(-3.5f, 0.0f, -3.5f, 7, 10, 7);
		FrontLeftLowerLeg.setRotationPoint(4.6f, 14.0f, -9.6f);
		BackRightUpperLeg = new ModelRenderer(this, 72, 109);
		BackRightUpperLeg.addBox(-3.5f, 0.0f, -3.5f, 7, 12, 7);
		BackRightUpperLeg.setRotationPoint(-4.6f, 4.0f, 11.6f);
		BackRightLowerLeg = new ModelRenderer(this, 100, 56);
		BackRightLowerLeg.addBox(-3.5f, 0.0f, -3.5f, 7, 10, 7);
		BackRightLowerLeg.setRotationPoint(-4.6f, 14.0f, 11.6f);
		BackLeftUpperLeg = new ModelRenderer(this, 72, 90);
		BackLeftUpperLeg.addBox(-3.5f, 0.0f, -3.5f, 7, 12, 7);
		BackLeftUpperLeg.setRotationPoint(4.6f, 4.0f, 11.6f);
		BackLeftLowerLeg = new ModelRenderer(this, 44, 77);
		BackLeftLowerLeg.addBox(-3.5f, 0.0f, -3.5f, 7, 10, 7);
		BackLeftLowerLeg.setRotationPoint(4.6f, 14.0f, 11.6f);
		TailRoot = new ModelRenderer(this, 20, 105);
		TailRoot.addBox(-1.0f, 0.0f, -2.0f, 2, 10, 2);
		TailRoot.setRotationPoint(0.0f, -8.0f, 15.0f);
		setRotation(TailRoot, 0.296706f, 0.0f, 0.0f);
		Tail = new ModelRenderer(this, 20, 117);
		Tail.addBox(-1.0f, 9.7f, -0.2f, 2, 6, 2);
		Tail.setRotationPoint(0.0f, -8.0f, 15.0f);
		setRotation(Tail, 0.1134464f, 0.0f, 0.0f);
		TailPlush = new ModelRenderer(this, 26, 76);
		TailPlush.addBox(-1.5f, 15.5f, -0.7f, 3, 6, 3);
		TailPlush.setRotationPoint(0.0f, -8.0f, 15.0f);
		setRotation(TailPlush, 0.1134464f, 0.0f, 0.0f);
	}

	public void adjustAllRotationPoints(ModelRenderer target, ModelRenderer origin) {
		float distanceY;
		distanceY = target.rotationPointY > origin.rotationPointY ? target.rotationPointY - origin.rotationPointY : origin.rotationPointY - target.rotationPointY;
		target.rotationPointY = origin.rotationPointY + MathHelper.sin(origin.rotateAngleX) * distanceY;
		target.rotationPointZ = origin.rotationPointZ - MathHelper.cos(origin.rotateAngleY) * (MathHelper.cos(origin.rotateAngleX) * distanceY);
		target.rotationPointX = origin.rotationPointX - MathHelper.sin(origin.rotateAngleY) * (MathHelper.cos(origin.rotateAngleX) * distanceY);
	}

	public void adjustXRotationPoints(ModelRenderer target, ModelRenderer origin) {
		float distance = target.rotationPointY - origin.rotationPointY;
		if (distance < 0.0f) {
			distance *= -1.0f;
		}
		target.rotationPointZ = origin.rotationPointZ + MathHelper.sin(origin.rotateAngleX) * distance;
		target.rotationPointY = origin.rotationPointY + MathHelper.cos(origin.rotateAngleX) * distance;
	}

	public void AdjustY(float f) {
		float yOff = f;
		Head.rotationPointY = yOff + -10.0f;
		Neck.rotationPointY = yOff + -8.0f;
		HeadBump.rotationPointY = yOff + -10.0f;
		Chin.rotationPointY = yOff + -10.0f;
		LowerLip.rotationPointY = yOff + -10.0f;
		Back.rotationPointY = yOff + -4.0f;
		LeftSmallEar.rotationPointY = yOff + -10.0f;
		LeftBigEar.rotationPointY = yOff + -10.0f;
		RightSmallEar.rotationPointY = yOff + -10.0f;
		RightBigEar.rotationPointY = yOff + -10.0f;
		Hump.rotationPointY = yOff + -13.0f;
		Body.rotationPointY = yOff + -2.0f;
		Skirt.rotationPointY = yOff + 8.0f;
		RightTuskA.rotationPointY = yOff + -10.0f;
		RightTuskB.rotationPointY = yOff + -10.0f;
		RightTuskC.rotationPointY = yOff + -10.0f;
		RightTuskD.rotationPointY = yOff + -10.0f;
		LeftTuskA.rotationPointY = yOff + -10.0f;
		LeftTuskB.rotationPointY = yOff + -10.0f;
		LeftTuskC.rotationPointY = yOff + -10.0f;
		LeftTuskD.rotationPointY = yOff + -10.0f;
		TrunkA.rotationPointY = yOff + -3.0f;
		TrunkB.rotationPointY = yOff + 5.5f;
		TrunkC.rotationPointY = yOff + 13.0f;
		TrunkD.rotationPointY = yOff + 16.0f;
		TrunkE.rotationPointY = yOff + 19.5f;
		FrontRightUpperLeg.rotationPointY = yOff + 4.0f;
		FrontRightLowerLeg.rotationPointY = yOff + 14.0f;
		FrontLeftUpperLeg.rotationPointY = yOff + 4.0f;
		FrontLeftLowerLeg.rotationPointY = yOff + 14.0f;
		BackRightUpperLeg.rotationPointY = yOff + 4.0f;
		BackRightLowerLeg.rotationPointY = yOff + 14.0f;
		BackLeftUpperLeg.rotationPointY = yOff + 4.0f;
		BackLeftLowerLeg.rotationPointY = yOff + 14.0f;
		TailRoot.rotationPointY = yOff + -8.0f;
		Tail.rotationPointY = yOff + -8.0f;
		TailPlush.rotationPointY = yOff + -8.0f;
	}

	public void adjustYRotationPoints(ModelRenderer target, ModelRenderer origin) {
		float distanceZ;
		distanceZ = target.rotationPointZ > origin.rotationPointZ ? target.rotationPointZ - origin.rotationPointZ : origin.rotationPointZ - target.rotationPointZ;
		target.rotationPointZ = origin.rotationPointZ - MathHelper.cos(origin.rotateAngleY) * distanceZ;
		target.rotationPointX = origin.rotationPointX - MathHelper.sin(origin.rotateAngleY) * distanceZ;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		byte tusks = 0;
		this.setRotationAngles(f, f1, f2, f3, f4, f5, tusks, false, false);
		LeftTuskB.render(f5);
		RightTuskB.render(f5);
		LeftTuskC.render(f5);
		RightTuskC.render(f5);
		LeftTuskD.render(f5);
		RightTuskD.render(f5);
		HeadBump.render(f5);
		Skirt.render(f5);
		Head.render(f5);
		Neck.render(f5);
		Chin.render(f5);
		LowerLip.render(f5);
		Back.render(f5);
		Hump.render(f5);
		Body.render(f5);
		RightTuskA.render(f5);
		LeftTuskA.render(f5);
		TrunkA.render(f5);
		TrunkB.render(f5);
		TrunkC.render(f5);
		TrunkD.render(f5);
		TrunkE.render(f5);
		FrontRightUpperLeg.render(f5);
		FrontRightLowerLeg.render(f5);
		FrontLeftUpperLeg.render(f5);
		FrontLeftLowerLeg.render(f5);
		BackRightUpperLeg.render(f5);
		BackRightLowerLeg.render(f5);
		BackLeftUpperLeg.render(f5);
		BackLeftLowerLeg.render(f5);
		TailRoot.render(f5);
		Tail.render(f5);
		TailPlush.render(f5);
	}

	public void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, byte tusks, boolean sitting, boolean tail) {
		float RLegXRot = MathHelper.cos(f * 0.6662f + 3.141593f) * 0.8f * f1;
		float LLegXRot = MathHelper.cos(f * 0.6662f) * 0.8f * f1;
		if (f4 < 0.0f) {
			f4 = 0.0f;
		}
		float HeadXRot = f4 / 57.29578f;
		if (f3 > 20.0f) {
			f3 = 20.0f;
		}
		if (f3 < -20.0f) {
			f3 = -20.0f;
		}
		float HeadYRot = f3 / 57.29578f;
		float f10 = 0.0f;
		if (sitting) {
			f10 = 8.0f;
		}
		AdjustY(f10);
		float TrunkXRot;
		HeadXRot = 0.0f;
		TrunkXRot = 0.0f;
		if (sitting) {
			HeadXRot = 0.0f;
			TrunkXRot = 0.0f;
		}
		Head.rotateAngleX = -10.0f / radianF + HeadXRot;
		HeadBump.rotateAngleY = Head.rotateAngleY = HeadYRot;
		HeadBump.rotateAngleX = Head.rotateAngleX;
		RightTuskA.rotateAngleY = HeadYRot;
		LeftTuskA.rotateAngleY = HeadYRot;
		RightTuskA.rotateAngleX = 70.0f / radianF + HeadXRot;
		LeftTuskA.rotateAngleX = 70.0f / radianF + HeadXRot;
		Chin.rotateAngleY = HeadYRot;
		Chin.rotateAngleX = 113.0f / radianF + HeadXRot;
		LowerLip.rotateAngleY = HeadYRot;
		LowerLip.rotateAngleX = 85.0f / radianF + HeadXRot;
		float EarF = 0.0f;
		float f2a = f2 % 100.0f;
		if (f2a > 60.0f && f2a < 90.0f) {
			EarF = MathHelper.cos(f2 * 0.5f) * 0.35f;
		}
		RightBigEar.rotateAngleY = 30.0f / radianF + HeadYRot + EarF;
		RightSmallEar.rotateAngleY = 30.0f / radianF + HeadYRot + EarF;
		LeftBigEar.rotateAngleY = -30.0f / radianF + HeadYRot - EarF;
		LeftSmallEar.rotateAngleY = -30.0f / radianF + HeadYRot - EarF;
		RightBigEar.rotateAngleX = -10.0f / radianF + HeadXRot;
		RightSmallEar.rotateAngleX = -10.0f / radianF + HeadXRot;
		LeftBigEar.rotateAngleX = -10.0f / radianF + HeadXRot;
		LeftSmallEar.rotateAngleX = -10.0f / radianF + HeadXRot;
		TrunkA.rotationPointZ = -22.5f;
		adjustAllRotationPoints(TrunkA, Head);
		TrunkA.rotateAngleY = HeadYRot;
		float TrunkARotX = 90.0f - TrunkXRot;
		if (TrunkARotX < 85.0f) {
			TrunkARotX = 85.0f;
		}
		TrunkA.rotateAngleX = TrunkARotX / radianF + HeadXRot;
		TrunkB.rotationPointZ = -22.5f;
		adjustAllRotationPoints(TrunkB, TrunkA);
		TrunkB.rotateAngleY = HeadYRot;
		TrunkB.rotateAngleX = (95.0f - TrunkXRot * 1.5f) / radianF + HeadXRot;
		TrunkC.rotationPointZ = -22.5f;
		adjustAllRotationPoints(TrunkC, TrunkB);
		TrunkC.rotateAngleY = HeadYRot;
		TrunkC.rotateAngleX = (110.0f - TrunkXRot * 3.0f) / radianF + HeadXRot;
		TrunkD.rotationPointZ = -21.5f;
		adjustAllRotationPoints(TrunkD, TrunkC);
		TrunkD.rotateAngleY = HeadYRot;
		TrunkD.rotateAngleX = (127.0f - TrunkXRot * 4.5f) / radianF + HeadXRot;
		TrunkE.rotationPointZ = -19.0f;
		adjustAllRotationPoints(TrunkE, TrunkD);
		TrunkE.rotateAngleY = HeadYRot;
		TrunkE.rotateAngleX = (145.0f - TrunkXRot * 6.0f) / radianF + HeadXRot;
		FrontRightUpperLeg.rotateAngleX = RLegXRot;
		FrontLeftUpperLeg.rotateAngleX = LLegXRot;
		BackLeftUpperLeg.rotateAngleX = RLegXRot;
		BackRightUpperLeg.rotateAngleX = LLegXRot;
		adjustXRotationPoints(FrontRightLowerLeg, FrontRightUpperLeg);
		adjustXRotationPoints(BackRightLowerLeg, BackRightUpperLeg);
		adjustXRotationPoints(FrontLeftLowerLeg, FrontLeftUpperLeg);
		adjustXRotationPoints(BackLeftLowerLeg, BackLeftUpperLeg);
		float LLegXRotD = LLegXRot * 57.29578f;
		float RLegXRotD = RLegXRot * 57.29578f;
		if (LLegXRotD > 0.0f) {
			LLegXRotD *= 2.0f;
		}
		if (RLegXRotD > 0.0f) {
			RLegXRotD *= 2.0f;
		}
		FrontLeftLowerLeg.rotateAngleX = LLegXRotD / radianF;
		FrontRightLowerLeg.rotateAngleX = RLegXRotD / radianF;
		BackLeftLowerLeg.rotateAngleX = RLegXRotD / radianF;
		BackRightLowerLeg.rotateAngleX = LLegXRotD / radianF;
		LeftTuskB.rotateAngleY = HeadYRot;
		LeftTuskC.rotateAngleY = HeadYRot;
		LeftTuskD.rotateAngleY = HeadYRot;
		RightTuskB.rotateAngleY = HeadYRot;
		RightTuskC.rotateAngleY = HeadYRot;
		RightTuskD.rotateAngleY = HeadYRot;
		LeftTuskB.rotateAngleX = 40.0f / radianF + HeadXRot;
		LeftTuskC.rotateAngleX = 10.0f / radianF + HeadXRot;
		LeftTuskD.rotateAngleX = -20.0f / radianF + HeadXRot;
		RightTuskB.rotateAngleX = 40.0f / radianF + HeadXRot;
		RightTuskC.rotateAngleX = 10.0f / radianF + HeadXRot;
		RightTuskD.rotateAngleX = -20.0f / radianF + HeadXRot;
		float tailMov = f1 * 0.9f;
		if (tailMov < 0.0f) {
			tailMov = 0.0f;
		}
		if (tail) {
			TailRoot.rotateAngleY = MathHelper.cos(f2 * 0.4f) * 1.3f;
			tailMov = 30.0f / radianF;
		} else {
			TailRoot.rotateAngleY = 0.0f;
		}
		TailRoot.rotateAngleX = 17.0f / radianF + tailMov;
		TailPlush.rotateAngleX = Tail.rotateAngleX = 6.5f / radianF + tailMov;
		Tail.rotateAngleY = TailPlush.rotateAngleY = TailRoot.rotateAngleY;
	}
}