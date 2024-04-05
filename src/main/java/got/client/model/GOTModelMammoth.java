package got.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class GOTModelMammoth extends ModelBase {
	private final ModelRenderer head;
	private final ModelRenderer neck;
	private final ModelRenderer headBump;
	private final ModelRenderer chin;
	private final ModelRenderer lowerLip;
	private final ModelRenderer back;
	private final ModelRenderer leftSmallEar;
	private final ModelRenderer leftBigEar;
	private final ModelRenderer rightSmallEar;
	private final ModelRenderer rightBigEar;
	private final ModelRenderer hump;
	private final ModelRenderer body;
	private final ModelRenderer skirt;
	private final ModelRenderer rightTuskA;
	private final ModelRenderer rightTuskB;
	private final ModelRenderer rightTuskC;
	private final ModelRenderer rightTuskD;
	private final ModelRenderer leftTuskA;
	private final ModelRenderer leftTuskB;
	private final ModelRenderer leftTuskC;
	private final ModelRenderer leftTuskD;
	private final ModelRenderer trunkA;
	private final ModelRenderer trunkB;
	private final ModelRenderer trunkC;
	private final ModelRenderer trunkD;
	private final ModelRenderer trunkE;
	private final ModelRenderer frontRightUpperLeg;
	private final ModelRenderer frontRightLowerLeg;
	private final ModelRenderer frontLeftUpperLeg;
	private final ModelRenderer frontLeftLowerLeg;
	private final ModelRenderer backRightUpperLeg;
	private final ModelRenderer backRightLowerLeg;
	private final ModelRenderer backLeftUpperLeg;
	private final ModelRenderer backLeftLowerLeg;
	private final ModelRenderer tailRoot;
	private final ModelRenderer tail;
	private final ModelRenderer tailPlush;
	private final ModelRenderer harnessBlanket;
	private final ModelRenderer harnessUpperBelt;
	private final ModelRenderer harnessLowerBelt;

	public GOTModelMammoth() {
		textureWidth = 128;
		textureHeight = 256;
		head = new ModelRenderer(this, 60, 0);
		head.addBox(-5.5f, -6.0f, -8.0f, 11, 15, 10);
		head.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(head, -0.1745329f, 0.0f, 0.0f);
		neck = new ModelRenderer(this, 46, 48);
		neck.addBox(-4.95f, -6.0f, -8.0f, 10, 14, 8);
		neck.setRotationPoint(0.0f, -8.0f, -10.0f);
		setRotation(neck, -0.2617994f, 0.0f, 0.0f);
		headBump = new ModelRenderer(this, 104, 41);
		headBump.addBox(-3.0f, -9.0f, -6.0f, 6, 3, 6);
		headBump.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(headBump, -0.1745329f, 0.0f, 0.0f);
		chin = new ModelRenderer(this, 86, 56);
		chin.addBox(-1.5f, -6.0f, -10.7f, 3, 5, 4);
		chin.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(chin, 2.054118f, 0.0f, 0.0f);
		lowerLip = new ModelRenderer(this, 80, 65);
		lowerLip.addBox(-2.0f, -2.0f, -14.0f, 4, 2, 6);
		lowerLip.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(lowerLip, 1.570796f, 0.0f, 0.0f);
		back = new ModelRenderer(this, 0, 48);
		back.addBox(-5.0f, -10.0f, -10.0f, 10, 2, 26);
		back.setRotationPoint(0.0f, -4.0f, -3.0f);
		leftSmallEar = new ModelRenderer(this, 102, 0);
		leftSmallEar.addBox(2.0f, -8.0f, -5.0f, 8, 10, 1);
		leftSmallEar.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(leftSmallEar, -0.1745329f, -0.5235988f, 0.5235988f);
		leftBigEar = new ModelRenderer(this, 102, 0);
		leftBigEar.addBox(2.0f, -8.0f, -5.0f, 12, 14, 1);
		leftBigEar.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(leftBigEar, -0.1745329f, -0.5235988f, 0.5235988f);
		rightSmallEar = new ModelRenderer(this, 106, 15);
		rightSmallEar.addBox(-10.0f, -8.0f, -5.0f, 8, 10, 1);
		rightSmallEar.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(rightSmallEar, -0.1745329f, 0.5235988f, -0.5235988f);
		rightBigEar = new ModelRenderer(this, 102, 15);
		rightBigEar.addBox(-14.0f, -8.0f, -5.0f, 12, 14, 1);
		rightBigEar.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(rightBigEar, -0.1745329f, 0.5235988f, -0.5235988f);
		hump = new ModelRenderer(this, 88, 30);
		hump.addBox(-6.0f, -2.0f, -3.0f, 12, 3, 8);
		hump.setRotationPoint(0.0f, -13.0f, -5.5f);
		body = new ModelRenderer(this, 0, 0);
		body.addBox(-8.0f, -10.0f, -10.0f, 16, 20, 28);
		body.setRotationPoint(0.0f, -2.0f, -3.0f);
		skirt = new ModelRenderer(this, 28, 94);
		skirt.addBox(-8.0f, -10.0f, -6.0f, 16, 28, 6);
		skirt.setRotationPoint(0.0f, 8.0f, -3.0f);
		setRotation(skirt, 1.570796f, 0.0f, 0.0f);
		rightTuskA = new ModelRenderer(this, 2, 60);
		rightTuskA.addBox(-3.8f, -3.5f, -19.0f, 2, 2, 10);
		rightTuskA.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(rightTuskA, 1.22173f, 0.0f, 0.1745329f);
		rightTuskB = new ModelRenderer(this, 0, 0);
		rightTuskB.addBox(-3.8f, 6.2f, -24.2f, 2, 2, 7);
		rightTuskB.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(rightTuskB, 0.6981317f, 0.0f, 0.1745329f);
		rightTuskC = new ModelRenderer(this, 0, 18);
		rightTuskC.addBox(-3.8f, 17.1f, -21.9f, 2, 2, 5);
		rightTuskC.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(rightTuskC, 0.1745329f, 0.0f, 0.1745329f);
		rightTuskD = new ModelRenderer(this, 14, 18);
		rightTuskD.addBox(-3.8f, 25.5f, -14.5f, 2, 2, 5);
		rightTuskD.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(rightTuskD, -0.3490659f, 0.0f, 0.1745329f);
		leftTuskA = new ModelRenderer(this, 2, 48);
		leftTuskA.addBox(1.8f, -3.5f, -19.0f, 2, 2, 10);
		leftTuskA.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(leftTuskA, 1.22173f, 0.0f, -0.1745329f);
		leftTuskB = new ModelRenderer(this, 0, 9);
		leftTuskB.addBox(1.8f, 6.2f, -24.2f, 2, 2, 7);
		leftTuskB.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(leftTuskB, 0.6981317f, 0.0f, -0.1745329f);
		leftTuskC = new ModelRenderer(this, 0, 18);
		leftTuskC.addBox(1.8f, 17.1f, -21.9f, 2, 2, 5);
		leftTuskC.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(leftTuskC, 0.1745329f, 0.0f, -0.1745329f);
		leftTuskD = new ModelRenderer(this, 14, 18);
		leftTuskD.addBox(1.8f, 25.5f, -14.5f, 2, 2, 5);
		leftTuskD.setRotationPoint(0.0f, -10.0f, -16.5f);
		setRotation(leftTuskD, -0.3490659f, 0.0f, -0.1745329f);
		trunkA = new ModelRenderer(this, 0, 76);
		trunkA.addBox(-4.0f, -2.5f, -18.0f, 8, 7, 10);
		trunkA.setRotationPoint(0.0f, -3.0f, -22.46667f);
		setRotation(trunkA, 1.570796f, 0.0f, 0.0f);
		trunkB = new ModelRenderer(this, 0, 93);
		trunkB.addBox(-3.0f, -2.5f, -7.0f, 6, 5, 7);
		trunkB.setRotationPoint(0.0f, 6.5f, -22.5f);
		setRotation(trunkB, 1.658063f, 0.0f, 0.0f);
		trunkC = new ModelRenderer(this, 0, 105);
		trunkC.addBox(-2.5f, -2.0f, -4.0f, 5, 4, 5);
		trunkC.setRotationPoint(0.0f, 13.0f, -22.0f);
		setRotation(trunkC, 1.919862f, 0.0f, 0.0f);
		trunkD = new ModelRenderer(this, 0, 114);
		trunkD.addBox(-2.0f, -1.5f, -5.0f, 4, 3, 5);
		trunkD.setRotationPoint(0.0f, 16.0f, -21.5f);
		setRotation(trunkD, 2.216568f, 0.0f, 0.0f);
		trunkE = new ModelRenderer(this, 0, 122);
		trunkE.addBox(-1.5f, -1.0f, -4.0f, 3, 2, 4);
		trunkE.setRotationPoint(0.0f, 19.5f, -19.0f);
		setRotation(trunkE, 2.530727f, 0.0f, 0.0f);
		frontRightUpperLeg = new ModelRenderer(this, 100, 109);
		frontRightUpperLeg.addBox(-3.5f, 0.0f, -3.5f, 7, 12, 7);
		frontRightUpperLeg.setRotationPoint(-4.6f, 4.0f, -9.6f);
		frontRightLowerLeg = new ModelRenderer(this, 100, 73);
		frontRightLowerLeg.addBox(-3.5f, 0.0f, -3.5f, 7, 10, 7);
		frontRightLowerLeg.setRotationPoint(-4.6f, 14.0f, -9.6f);
		frontLeftUpperLeg = new ModelRenderer(this, 100, 90);
		frontLeftUpperLeg.addBox(-3.5f, 0.0f, -3.5f, 7, 12, 7);
		frontLeftUpperLeg.setRotationPoint(4.6f, 4.0f, -9.6f);
		frontLeftLowerLeg = new ModelRenderer(this, 72, 73);
		frontLeftLowerLeg.addBox(-3.5f, 0.0f, -3.5f, 7, 10, 7);
		frontLeftLowerLeg.setRotationPoint(4.6f, 14.0f, -9.6f);
		backRightUpperLeg = new ModelRenderer(this, 72, 109);
		backRightUpperLeg.addBox(-3.5f, 0.0f, -3.5f, 7, 12, 7);
		backRightUpperLeg.setRotationPoint(-4.6f, 4.0f, 11.6f);
		backRightLowerLeg = new ModelRenderer(this, 100, 56);
		backRightLowerLeg.addBox(-3.5f, 0.0f, -3.5f, 7, 10, 7);
		backRightLowerLeg.setRotationPoint(-4.6f, 14.0f, 11.6f);
		backLeftUpperLeg = new ModelRenderer(this, 72, 90);
		backLeftUpperLeg.addBox(-3.5f, 0.0f, -3.5f, 7, 12, 7);
		backLeftUpperLeg.setRotationPoint(4.6f, 4.0f, 11.6f);
		backLeftLowerLeg = new ModelRenderer(this, 44, 77);
		backLeftLowerLeg.addBox(-3.5f, 0.0f, -3.5f, 7, 10, 7);
		backLeftLowerLeg.setRotationPoint(4.6f, 14.0f, 11.6f);
		tailRoot = new ModelRenderer(this, 20, 105);
		tailRoot.addBox(-1.0f, 0.0f, -2.0f, 2, 10, 2);
		tailRoot.setRotationPoint(0.0f, -8.0f, 15.0f);
		setRotation(tailRoot, 0.296706f, 0.0f, 0.0f);
		tail = new ModelRenderer(this, 20, 117);
		tail.addBox(-1.0f, 9.7f, -0.2f, 2, 6, 2);
		tail.setRotationPoint(0.0f, -8.0f, 15.0f);
		setRotation(tail, 0.1134464f, 0.0f, 0.0f);
		tailPlush = new ModelRenderer(this, 26, 76);
		tailPlush.addBox(-1.5f, 15.5f, -0.7f, 3, 6, 3);
		tailPlush.setRotationPoint(0.0f, -8.0f, 15.0f);
		setRotation(tailPlush, 0.1134464f, 0.0f, 0.0f);
		harnessBlanket = new ModelRenderer(this, 0, 196);
		harnessBlanket.addBox(-8.5f, -2.0f, -3.0f, 17, 14, 18);
		harnessBlanket.setRotationPoint(0.0f, -13.2f, -3.5f);
		harnessUpperBelt = new ModelRenderer(this, 70, 196);
		harnessUpperBelt.addBox(-8.5f, 0.5f, -2.0f, 17, 10, 2);
		harnessUpperBelt.setRotationPoint(0.0f, -2.0f, -2.5f);
		harnessLowerBelt = new ModelRenderer(this, 70, 196);
		harnessLowerBelt.addBox(-8.5f, 0.5f, -2.5f, 17, 10, 2);
		harnessLowerBelt.setRotationPoint(0.0f, -2.0f, 7.0f);
	}

	private void adjustAllRotationPoints(ModelRenderer target, ModelRenderer origin) {
		float distanceY = target.rotationPointY > origin.rotationPointY ? target.rotationPointY - origin.rotationPointY : origin.rotationPointY - target.rotationPointY;
		target.rotationPointY = origin.rotationPointY + MathHelper.sin(origin.rotateAngleX) * distanceY;
		target.rotationPointZ = origin.rotationPointZ - MathHelper.cos(origin.rotateAngleY) * (MathHelper.cos(origin.rotateAngleX) * distanceY);
		target.rotationPointX = origin.rotationPointX - MathHelper.sin(origin.rotateAngleY) * (MathHelper.cos(origin.rotateAngleX) * distanceY);
	}

	private void adjustXRotationPoints(ModelRenderer target, ModelRenderer origin) {
		float distance = target.rotationPointY - origin.rotationPointY;
		if (distance < 0.0f) {
			distance *= -1.0f;
		}
		target.rotationPointZ = origin.rotationPointZ + MathHelper.sin(origin.rotateAngleX) * distance;
		target.rotationPointY = origin.rotationPointY + MathHelper.cos(origin.rotateAngleX) * distance;
	}

	private void AdjustY(float f) {
		head.rotationPointY = f - 10.0f;
		neck.rotationPointY = f - 8.0f;
		headBump.rotationPointY = f - 10.0f;
		chin.rotationPointY = f - 10.0f;
		lowerLip.rotationPointY = f - 10.0f;
		back.rotationPointY = f - 4.0f;
		leftSmallEar.rotationPointY = f - 10.0f;
		leftBigEar.rotationPointY = f - 10.0f;
		rightSmallEar.rotationPointY = f - 10.0f;
		rightBigEar.rotationPointY = f - 10.0f;
		hump.rotationPointY = f - 13.0f;
		body.rotationPointY = f - 2.0f;
		skirt.rotationPointY = f + 8.0f;
		rightTuskA.rotationPointY = f - 10.0f;
		rightTuskB.rotationPointY = f - 10.0f;
		rightTuskC.rotationPointY = f - 10.0f;
		rightTuskD.rotationPointY = f - 10.0f;
		leftTuskA.rotationPointY = f - 10.0f;
		leftTuskB.rotationPointY = f - 10.0f;
		leftTuskC.rotationPointY = f - 10.0f;
		leftTuskD.rotationPointY = f - 10.0f;
		trunkA.rotationPointY = f - 3.0f;
		trunkB.rotationPointY = f + 5.5f;
		trunkC.rotationPointY = f + 13.0f;
		trunkD.rotationPointY = f + 16.0f;
		trunkE.rotationPointY = f + 19.5f;
		frontRightUpperLeg.rotationPointY = f + 4.0f;
		frontRightLowerLeg.rotationPointY = f + 14.0f;
		frontLeftUpperLeg.rotationPointY = f + 4.0f;
		frontLeftLowerLeg.rotationPointY = f + 14.0f;
		backRightUpperLeg.rotationPointY = f + 4.0f;
		backRightLowerLeg.rotationPointY = f + 14.0f;
		backLeftUpperLeg.rotationPointY = f + 4.0f;
		backLeftLowerLeg.rotationPointY = f + 14.0f;
		tailRoot.rotationPointY = f - 8.0f;
		tail.rotationPointY = f - 8.0f;
		tailPlush.rotationPointY = f - 8.0f;
		harnessBlanket.rotationPointY = f - 13.2f;
		harnessUpperBelt.rotationPointY = f - 2.0f;
		harnessLowerBelt.rotationPointY = f - 2.0f;
	}

	private void adjustYRotationPoints(ModelRenderer target, ModelRenderer origin) {
		float distanceZ = target.rotationPointZ > origin.rotationPointZ ? target.rotationPointZ - origin.rotationPointZ : origin.rotationPointZ - target.rotationPointZ;
		target.rotationPointZ = origin.rotationPointZ - MathHelper.cos(origin.rotateAngleY) * distanceZ;
		target.rotationPointX = origin.rotationPointX - MathHelper.sin(origin.rotateAngleY) * distanceZ;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		byte tusks = 0;
		setRotationAngles(f, f1, f2, f3, f4, f5, tusks);
		leftTuskB.render(f5);
		rightTuskB.render(f5);
		leftTuskC.render(f5);
		rightTuskC.render(f5);
		leftTuskD.render(f5);
		rightTuskD.render(f5);
		headBump.render(f5);
		skirt.render(f5);
		harnessBlanket.render(f5);
		harnessUpperBelt.render(f5);
		harnessLowerBelt.render(f5);
		head.render(f5);
		neck.render(f5);
		chin.render(f5);
		lowerLip.render(f5);
		back.render(f5);
		hump.render(f5);
		body.render(f5);
		rightTuskA.render(f5);
		leftTuskA.render(f5);
		trunkA.render(f5);
		trunkB.render(f5);
		trunkC.render(f5);
		trunkD.render(f5);
		trunkE.render(f5);
		frontRightUpperLeg.render(f5);
		frontRightLowerLeg.render(f5);
		frontLeftUpperLeg.render(f5);
		frontLeftLowerLeg.render(f5);
		backRightUpperLeg.render(f5);
		backRightLowerLeg.render(f5);
		backLeftUpperLeg.render(f5);
		backLeftLowerLeg.render(f5);
		tailRoot.render(f5);
		tail.render(f5);
		tailPlush.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	private void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, byte tusks) {
		float f31 = f3;
		float rLegXRot = MathHelper.cos(f * 0.6662f + 3.141593f) * 0.8f * f1;
		float lLegXRot = MathHelper.cos(f * 0.6662f) * 0.8f * f1;
		if (f31 > 20.0f) {
			f31 = 20.0f;
		}
		if (f31 < -20.0f) {
			f31 = -20.0f;
		}
		float headYRot = f31 / 57.29578f;
		float f10 = 0.0f;
		AdjustY(f10);
		float headXRot = 0.0f;
		float trunkXRot = 0.0f;
		float radianF = 57.29578f;
		head.rotateAngleX = -10.0f / radianF + headXRot;
		headBump.rotateAngleY = head.rotateAngleY = headYRot;
		headBump.rotateAngleX = head.rotateAngleX;
		rightTuskA.rotateAngleY = headYRot;
		leftTuskA.rotateAngleY = headYRot;
		rightTuskA.rotateAngleX = 70.0f / radianF + headXRot;
		leftTuskA.rotateAngleX = 70.0f / radianF + headXRot;
		chin.rotateAngleY = headYRot;
		chin.rotateAngleX = 113.0f / radianF + headXRot;
		lowerLip.rotateAngleY = headYRot;
		lowerLip.rotateAngleX = 85.0f / radianF + headXRot;
		float earF = 0.0f;
		float f2a = f2 % 100.0f;
		if (f2a > 60.0f && f2a < 90.0f) {
			earF = MathHelper.cos(f2 * 0.5f) * 0.35f;
		}
		rightBigEar.rotateAngleY = 30.0f / radianF + headYRot + earF;
		rightSmallEar.rotateAngleY = 30.0f / radianF + headYRot + earF;
		leftBigEar.rotateAngleY = -30.0f / radianF + headYRot - earF;
		leftSmallEar.rotateAngleY = -30.0f / radianF + headYRot - earF;
		rightBigEar.rotateAngleX = -10.0f / radianF + headXRot;
		rightSmallEar.rotateAngleX = -10.0f / radianF + headXRot;
		leftBigEar.rotateAngleX = -10.0f / radianF + headXRot;
		leftSmallEar.rotateAngleX = -10.0f / radianF + headXRot;
		trunkA.rotationPointZ = -22.5f;
		adjustAllRotationPoints(trunkA, head);
		trunkA.rotateAngleY = headYRot;
		float trunkARotX = 90.0f - trunkXRot;
		trunkA.rotateAngleX = trunkARotX / radianF + headXRot;
		trunkB.rotationPointZ = -22.5f;
		adjustAllRotationPoints(trunkB, trunkA);
		trunkB.rotateAngleY = headYRot;
		trunkB.rotateAngleX = (95.0f - trunkXRot * 1.5f) / radianF + headXRot;
		trunkC.rotationPointZ = -22.5f;
		adjustAllRotationPoints(trunkC, trunkB);
		trunkC.rotateAngleY = headYRot;
		trunkC.rotateAngleX = (110.0f - trunkXRot * 3.0f) / radianF + headXRot;
		trunkD.rotationPointZ = -21.5f;
		adjustAllRotationPoints(trunkD, trunkC);
		trunkD.rotateAngleY = headYRot;
		trunkD.rotateAngleX = (127.0f - trunkXRot * 4.5f) / radianF + headXRot;
		trunkE.rotationPointZ = -19.0f;
		adjustAllRotationPoints(trunkE, trunkD);
		trunkE.rotateAngleY = headYRot;
		trunkE.rotateAngleX = (145.0f - trunkXRot * 6.0f) / radianF + headXRot;
		frontRightUpperLeg.rotateAngleX = rLegXRot;
		frontLeftUpperLeg.rotateAngleX = lLegXRot;
		backLeftUpperLeg.rotateAngleX = rLegXRot;
		backRightUpperLeg.rotateAngleX = lLegXRot;
		adjustXRotationPoints(frontRightLowerLeg, frontRightUpperLeg);
		adjustXRotationPoints(backRightLowerLeg, backRightUpperLeg);
		adjustXRotationPoints(frontLeftLowerLeg, frontLeftUpperLeg);
		adjustXRotationPoints(backLeftLowerLeg, backLeftUpperLeg);
		float lLegXRotD = lLegXRot * 57.29578f;
		float rLegXRotD = rLegXRot * 57.29578f;
		if (lLegXRotD > 0.0f) {
			lLegXRotD *= 2.0f;
		}
		if (rLegXRotD > 0.0f) {
			rLegXRotD *= 2.0f;
		}
		frontLeftLowerLeg.rotateAngleX = lLegXRotD / radianF;
		frontRightLowerLeg.rotateAngleX = rLegXRotD / radianF;
		backLeftLowerLeg.rotateAngleX = rLegXRotD / radianF;
		backRightLowerLeg.rotateAngleX = lLegXRotD / radianF;
		leftTuskB.rotateAngleY = headYRot;
		leftTuskC.rotateAngleY = headYRot;
		leftTuskD.rotateAngleY = headYRot;
		rightTuskB.rotateAngleY = headYRot;
		rightTuskC.rotateAngleY = headYRot;
		rightTuskD.rotateAngleY = headYRot;
		leftTuskB.rotateAngleX = 40.0f / radianF + headXRot;
		leftTuskC.rotateAngleX = 10.0f / radianF + headXRot;
		leftTuskD.rotateAngleX = -20.0f / radianF + headXRot;
		rightTuskB.rotateAngleX = 40.0f / radianF + headXRot;
		rightTuskC.rotateAngleX = 10.0f / radianF + headXRot;
		rightTuskD.rotateAngleX = -20.0f / radianF + headXRot;
		float tailMov = f1 * 0.9f;
		if (tailMov < 0.0f) {
			tailMov = 0.0f;
		}
		tailRoot.rotateAngleY = 0.0f;
		tailRoot.rotateAngleX = 17.0f / radianF + tailMov;
		tailPlush.rotateAngleX = tail.rotateAngleX = 6.5f / radianF + tailMov;
		tail.rotateAngleY = tailPlush.rotateAngleY = tailRoot.rotateAngleY;
	}
}