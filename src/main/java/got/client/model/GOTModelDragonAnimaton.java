package got.client.model;

import got.common.entity.dragon.GOTEntityDragon;
import got.common.util.GOTCircularBuffer;
import got.common.util.GOTTickFloat;
import net.minecraft.util.MathHelper;

public class GOTModelDragonAnimaton {

	public static float CR00 = -0.5f;
	public static float CR01 = 1.5f;
	public static float CR02 = -1.5f;
	public static float CR03 = 0.5f;
	public static float CR10 = 1.0f;
	public static float CR11 = -2.5f;
	public static float CR12 = 2.0f;
	public static float CR13 = -0.5f;
	public static float CR20 = -0.5f;
	public static float CR21;
	public static float CR22 = 0.5f;
	public static float CR23;
	public static float CR30;
	public static float CR31 = 1.0f;
	public static float CR32;
	public static float CR33;

	public static double PI_D = Math.PI;

	public static float PI_F = (float) Math.PI;

	public static boolean useLUT = true;
	public GOTEntityDragon entity;
	public float partialTicks;

	public float ticksExisted;
	public float moveTime;
	public float moveSpeed;
	public float lookYaw;
	public float lookPitch;
	public double prevRenderYawOffset;
	public double yawAbs;
	public float animBase;
	public float cycleOfs;

	public float anim;
	public float ground;
	public float flutter;
	public float walk;
	public float sit;
	public float jaw;
	public float speed;
	public GOTTickFloat animTimer = new GOTTickFloat();
	public GOTTickFloat groundTimer = new GOTTickFloat(1).setLimit(0, 1);

	public GOTTickFloat flutterTimer = new GOTTickFloat().setLimit(0, 1);
	public GOTTickFloat walkTimer = new GOTTickFloat().setLimit(0, 1);
	public GOTTickFloat sitTimer = new GOTTickFloat().setLimit(0, 1);
	public GOTTickFloat jawTimer = new GOTTickFloat().setLimit(0, 1);
	public GOTTickFloat speedTimer = new GOTTickFloat(1).setLimit(0, 1);
	public boolean initTrails = true;
	public GOTCircularBuffer yTrail = new GOTCircularBuffer(8);

	public GOTCircularBuffer yawTrail = new GOTCircularBuffer(16);
	public GOTCircularBuffer pitchTrail = new GOTCircularBuffer(16);
	public boolean onGround;
	public boolean openJaw;

	public boolean wingsDown;
	public float[] wingArm = new float[3];
	public float[] wingForearm = new float[3];

	public float[] wingArmFlutter = new float[3];
	public float[] wingForearmFlutter = new float[3];
	public float[] wingArmGlide = new float[3];
	public float[] wingForearmGlide = new float[3];
	public float[] wingArmGround = new float[3];
	public float[] wingForearmGround = new float[3];
	public float[] xGround = {0, 0, 0, 0};
	public float[][] xGroundStand = {{0.8f, -1.5f, 1.3f, 0}, {-0.3f, 1.5f, -0.2f, 0},};

	public float[][] xGroundSit = {{0.3f, -1.8f, 1.8f, 0}, {-0.8f, 1.8f, -0.9f, 0},};

	public float[][][] xGroundWalk = {{{0.4f, -1.4f, 1.3f, 0}, {0.1f, 1.2f, -0.5f, 0}}, {{1.2f, -1.6f, 1.3f, 0}, {-0.3f, 2.1f, -0.9f, 0.6f}}, {{0.9f, -2.1f, 1.8f, 0.6f}, {-0.7f, 1.4f, -0.2f, 0}}};
	public float[] xGroundWalk2 = {0, 0, 0, 0};

	public float[] yGroundStand = {-0.25f, 0.25f};

	public float[] yGroundSit = {0.1f, 0.35f};

	public float[] yGroundWalk = {-0.1f, 0.1f};
	public float[] xAir;
	public float[][] xAirAll = {{0, 0, 0, 0}, {0, 0, 0, 0}};

	public float[] yAirAll = {-0.1f, 0.1f};

	public GOTModelDragonAnimaton(GOTEntityDragon dragon) {
		entity = dragon;
	}

	public static float atg2(float y, float x) {
		return (float) Math.atan2(y, x);
	}

	public static double clamp(double value, double min, double max) {
		return value < min ? min : Math.min(value, max);
	}

	public static float clamp(float value, float min, float max) {
		return value < min ? min : Math.min(value, max);
	}

	public static int clamp(int value, int min, int max) {
		return value < min ? min : Math.min(value, max);
	}

	public static float cos(float a) {
		if (useLUT) {
			return MathHelper.cos(a);
		}
		return (float) Math.cos(a);
	}

	public static float[] getLinearEndKnots(float... internalKnots) {
		float[] result = new float[internalKnots.length + 2];
		float diff1 = internalKnots[1] - internalKnots[0];
		float diff2 = internalKnots[internalKnots.length - 1] - internalKnots[internalKnots.length - 2];
		result[0] = internalKnots[0] - diff1;
		result[result.length - 1] = internalKnots[internalKnots.length - 1] + diff2;
		System.arraycopy(internalKnots, 0, result, 1, result.length - 1 - 1);
		return result;
	}

	public static float interp(float x, float... knots) {
		int nknots = knots.length;
		int nspans = nknots - 3;
		if (nspans < 1) {
			System.out.println(GOTModelDragonAnimaton.class.getName() + " Spline has too few knots");
			return 0;
		}
		x = clamp(x, 0, 0.9999f) * nspans;
		int span = (int) x;
		if (span >= nknots - 3) {
			span = nknots - 3;
		}
		x -= span;
		int knot = 0;
		knot += span;
		float knot0 = knots[knot];
		float knot1 = knots[knot + 1];
		float knot2 = knots[knot + 2];
		float knot3 = knots[knot + 3];

		float c3 = CR00 * knot0 + CR01 * knot1 + CR02 * knot2 + CR03 * knot3;
		float c2 = CR10 * knot0 + CR11 * knot1 + CR12 * knot2 + CR13 * knot3;
		float c1 = CR20 * knot0 + CR21 * knot1 + CR22 * knot2 + CR23 * knot3;
		float c0 = CR30 * knot0 + CR31 * knot1 + CR32 * knot2 + CR33 * knot3;
		return ((c3 * x + c2) * x + c1) * x + c0;
	}

	public static void interp(float x, float[] result, float[]... knots) {
		int nknots = knots.length;
		int nspans = nknots - 3;
		if (nspans < 1) {
			System.out.println(GOTModelDragonAnimaton.class.getName() + " Spline has too few knots");
			return;
		}
		x = clamp(x, 0, 0.9999f) * nspans;
		int span = (int) x;
		if (span >= nknots - 3) {
			span = nknots - 3;
		}
		x -= span;
		int knot = 0;
		knot += span;
		int dimension = result.length;
		for (int i = 0; i < dimension; i++) {
			float knot0 = knots[knot][i];
			float knot1 = knots[knot + 1][i];
			float knot2 = knots[knot + 2][i];
			float knot3 = knots[knot + 3][i];

			float c3 = CR00 * knot0 + CR01 * knot1 + CR02 * knot2 + CR03 * knot3;
			float c2 = CR10 * knot0 + CR11 * knot1 + CR12 * knot2 + CR13 * knot3;
			float c1 = CR20 * knot0 + CR21 * knot1 + CR22 * knot2 + CR23 * knot3;
			float c0 = CR30 * knot0 + CR31 * knot1 + CR32 * knot2 + CR33 * knot3;

			result[i] = ((c3 * x + c2) * x + c1) * x + c0;
		}
	}

	public static float[] interpArray(float[] inputs, float... knots) {
		float[] result = new float[inputs.length];
		for (int i = 0; i < inputs.length; i++) {
			result[i] = interp(inputs[i], knots);
		}
		return result;
	}

	public static float[] interpEndsArray(float[] inputs, float... internalKnots) {
		float[] knots = getLinearEndKnots(internalKnots);
		float[] result = new float[inputs.length];
		for (int i = 0; i < inputs.length; i++) {
			result[i] = interp(inputs[i], knots);
		}
		return result;
	}

	public static float[] interpLinearEndsArray(float minInputValue, float maxInputValue, int n, float... internalKnots) {
		float[] inputs = new float[n];
		float stepLength = (maxInputValue - minInputValue) / (n - 1);
		for (int i = 0; i < n; i++) {
			inputs[i] = minInputValue + i * stepLength;
		}
		return interpEndsArray(inputs, internalKnots);
	}

	public static float[] interpLinearEndsArray(int n, float... internalKnots) {
		return interpLinearEndsArray(0.0f, 1.0f, n, internalKnots);
	}

	public static double lerp(double a, double b, double x) {
		return a * (1 - x) + b * x;
	}

	public static float lerp(float a, float b, float x) {
		return a * (1 - x) + b * x;
	}

	public static double normDeg(double a) {
		a %= 360;
		if (a >= 180) {
			a -= 360;
		}
		if (a < -180) {
			a += 360;
		}
		return a;
	}

	public static float normDeg(float a) {
		a %= 360;
		if (a >= 180) {
			a -= 360;
		}
		if (a < -180) {
			a += 360;
		}
		return a;
	}

	public static double normRad(double a) {
		a %= PI_D * 2;
		if (a >= PI_D) {
			a -= PI_D * 2;
		}
		if (a < -PI_D) {
			a += PI_D * 2;
		}
		return a;
	}

	public static float normRad(float a) {
		a %= PI_F * 2;
		if (a >= PI_F) {
			a -= PI_F * 2;
		}
		if (a < -PI_F) {
			a += PI_F * 2;
		}
		return a;
	}

	public static float sin(float a) {
		if (useLUT) {
			return MathHelper.sin(a);
		}
		return (float) Math.sin(a);
	}

	public static double slerp(double a, double b, double x) {
		if (x <= 0) {
			return a;
		}
		if (x >= 1) {
			return b;
		}

		return lerp(a, b, x * x * (3 - 2 * x));
	}

	public static float slerp(float a, float b, float x) {
		if (x <= 0) {
			return a;
		}
		if (x >= 1) {
			return b;
		}

		return lerp(a, b, x * x * (3 - 2 * x));
	}

	public static float sqrtf(float f) {
		return (float) Math.sqrt(f);
	}

	public static double terp(double a, double b, double x) {
		if (x <= 0) {
			return a;
		}
		if (x >= 1) {
			return b;
		}

		double mu2 = (1 - Math.cos(x * PI_D)) / 2.0;
		return a * (1 - mu2) + b * mu2;
	}

	public static float terp(float a, float b, float x) {
		if (x <= 0) {
			return a;
		}
		if (x >= 1) {
			return b;
		}

		float mu2 = (1 - cos(x * PI_F)) / 2.0f;
		return a * (1 - mu2) + b * mu2;
	}

	public static float tg(float a) {
		return (float) Math.tan(a);
	}

	public static float toDegrees(float angrad) {
		return (float) Math.toDegrees(angrad);
	}

	public static float toRadians(float angdeg) {
		return (float) Math.toRadians(angdeg);
	}

	public static float updateRotation(float r1, float r2, float step) {
		return r1 + clamp(normDeg(r2 - r1), -step, step);
	}

	public void animate(GOTModelDragon model) {
		anim = animTimer.get(partialTicks);
		ground = groundTimer.get(partialTicks);
		flutter = flutterTimer.get(partialTicks);
		walk = walkTimer.get(partialTicks);
		sit = sitTimer.get(partialTicks);
		jaw = jawTimer.get(partialTicks);
		speed = speedTimer.get(partialTicks);

		animBase = anim * PI_F * 2;
		cycleOfs = sin(animBase - 1) + 1;

		boolean newWingsDown = cycleOfs > 1;
		if (newWingsDown && !wingsDown && flutter != 0) {
			entity.onWingsDown(speed);
		}
		wingsDown = newWingsDown;

		model.back.isHidden = entity.isSaddled();

		cycleOfs = cycleOfs * (cycleOfs + 2) * 0.05f;

		cycleOfs *= lerp(0.5f, 1, flutter);
		cycleOfs *= lerp(1, 0.5f, ground);

		model.offsetX = getModelOffsetX();
		model.offsetY = getModelOffsetY();
		model.offsetZ = getModelOffsetZ();

		model.pitch = getModelPitch();

		animHeadAndNeck(model);
		animTail(model);
		animWings(model);
		animLegs(model);
	}

	public void animHeadAndNeck(GOTModelDragon model) {
		model.neck.rotationPointX = 0;
		model.neck.rotationPointY = 14;
		model.neck.rotationPointZ = -8;

		model.neck.rotateAngleX = 0;
		model.neck.rotateAngleY = 0;
		model.neck.rotateAngleZ = 0;

		double health = entity.getHealthRelative();

		for (int i = 0; i < model.neckProxy.length; i++) {
			float vertMulti = (i + 1) / (float) model.neckProxy.length;

			float baseRotX = cos(i * 0.45f + animBase) * 0.15f;
			baseRotX *= lerp(0.2f, 1, flutter);
			baseRotX *= lerp(1, 0.2f, sit);
			float ofsRotX = sin(vertMulti * PI_F * 0.9f) * 0.75f;

			model.neck.rotateAngleX = baseRotX;

			model.neck.rotateAngleX *= slerp(1, 0.5f, walk);

			model.neck.rotateAngleX += (1 - speed) * vertMulti;

			model.neck.rotateAngleX -= (float) lerp(0, ofsRotX, ground * health);

			model.neck.rotateAngleY = toRadians(lookYaw) * vertMulti * speed;

			model.neck.renderScaleX = model.neck.renderScaleY = lerp(1.6f, 1, vertMulti);
			model.neck.renderScaleZ = 0.6f;

			model.neckScale.isHidden = i % 2 != 0 || i == 0;

			model.neckProxy[i].update();

			float neckSize = GOTModelDragon.NECK_SIZE * model.neck.renderScaleZ - 1.4f;
			model.neck.rotationPointX -= sin(model.neck.rotateAngleY) * cos(model.neck.rotateAngleX) * neckSize;
			model.neck.rotationPointY += sin(model.neck.rotateAngleX) * neckSize;
			model.neck.rotationPointZ -= cos(model.neck.rotateAngleY) * cos(model.neck.rotateAngleX) * neckSize;
		}

		model.head.rotateAngleX = toRadians(lookPitch) + (1 - speed);
		model.head.rotateAngleY = model.neck.rotateAngleY;
		model.head.rotateAngleZ = model.neck.rotateAngleZ * 0.2f;

		model.head.rotationPointX = model.neck.rotationPointX;
		model.head.rotationPointY = model.neck.rotationPointY;
		model.head.rotationPointZ = model.neck.rotationPointZ;

		model.jaw.rotateAngleX = jaw * 0.75f;
		model.jaw.rotateAngleX += (1 - sin(animBase)) * 0.1f * flutter;
	}

	public void animLegs(GOTModelDragon model) {

		if (ground < 1) {
			float footAirOfs = cycleOfs * 0.1f;
			float footAirX = 0.75f + cycleOfs * 0.1f;

			xAirAll[0][0] = 1.3f + footAirOfs;
			xAirAll[0][1] = -(0.7f * speed + 0.1f + footAirOfs);
			xAirAll[0][2] = footAirX;
			xAirAll[0][3] = footAirX * 0.5f;

			xAirAll[1][0] = footAirOfs + 0.6f;
			xAirAll[1][1] = footAirOfs + 0.8f;
			xAirAll[1][2] = footAirX;
			xAirAll[1][3] = footAirX * 0.5f;
		}

		for (int i = 0; i < model.thighProxy.length; i++) {
			GOTModelDragonPart thigh;
			GOTModelDragonPart crus;
			GOTModelDragonPart foot;
			GOTModelDragonPart toe;

			if (i % 2 == 0) {
				thigh = model.forethigh;
				crus = model.forecrus;
				foot = model.forefoot;
				toe = model.foretoe;

				thigh.rotationPointZ = 4;
			} else {
				thigh = model.hindthigh;
				crus = model.hindcrus;
				foot = model.hindfoot;
				toe = model.hindtoe;

				thigh.rotationPointZ = 46;
			}

			xAir = xAirAll[i % 2];

			slerpArrays(xGroundStand[i % 2], xGroundSit[i % 2], xGround, sit);

			xGround[3] = -(xGround[0] + xGround[1] + xGround[2]);

			if (walk > 0) {

				splineArrays(moveTime * 0.2f, i > 1, xGroundWalk2, xGroundWalk[0][i % 2], xGroundWalk[1][i % 2], xGroundWalk[2][i % 2]);

				xGroundWalk2[3] -= xGroundWalk2[0] + xGroundWalk2[1] + xGroundWalk2[2];

				slerpArrays(xGround, xGroundWalk2, xGround, walk);
			}

			float yAir = yAirAll[i % 2];

			float yGround = slerp(yGroundStand[i % 2], yGroundSit[i % 2], sit);

			yGround = slerp(yGround, yGroundWalk[i % 2], walk);

			thigh.rotateAngleY = slerp(yAir, yGround, ground);
			thigh.rotateAngleX = slerp(xAir[0], xGround[0], ground);
			crus.rotateAngleX = slerp(xAir[1], xGround[1], ground);
			foot.rotateAngleX = slerp(xAir[2], xGround[2], ground);
			toe.rotateAngleX = slerp(xAir[3], xGround[3], ground);

			model.thighProxy[i].update();
		}
	}

	public void animTail(GOTModelDragon model) {
		model.tail.rotationPointX = 0;
		model.tail.rotationPointY = 16;
		model.tail.rotationPointZ = 62;

		model.tail.rotateAngleX = 0;
		model.tail.rotateAngleY = 0;
		model.tail.rotateAngleZ = 0;

		float rotYStand = 0;
		float rotXAir = 0;
		float rotYAir = 0;

		for (int i = 0; i < model.tailProxy.length; i++) {
			float vertMulti = (i + 1) / (float) model.tailProxy.length;

			float amp = 0.1f + i / (model.tailProxy.length * 2.0f);

			float rotXStand = (i - model.tailProxy.length * 0.6f) * -amp * 0.4f;
			rotXStand += (sin(animBase * 0.2f) * sin(animBase * 0.37f) * 0.4f * amp - 0.1f) * (1 - sit);
			float rotXSit = rotXStand * 0.8f;

			rotYStand = (rotYStand + sin(i * 0.45f + animBase * 0.5f)) * amp * 0.4f;
			float rotYSit = sin(vertMulti * PI_F) * PI_F * 1.2f - 0.5f;

			rotXAir -= sin(i * 0.45f + animBase) * 0.04f * lerp(0.3f, 1, flutter);

			model.tail.rotateAngleX = lerp(rotXStand, rotXSit, sit);
			model.tail.rotateAngleY = lerp(rotYStand, rotYSit, sit);

			model.tail.rotateAngleX = lerp(rotXAir, model.tail.rotateAngleX, ground);
			model.tail.rotateAngleY = lerp(rotYAir, model.tail.rotateAngleY, ground);

			float angleLimit = 160 * vertMulti;
			float yawOfs = clamp((float) yawTrail.get(partialTicks, 0, i + 1) * 2, -angleLimit, angleLimit);
			float pitchOfs = clamp((float) pitchTrail.get(partialTicks, 0, i + 1) * 2, -angleLimit, angleLimit);

			model.tail.rotateAngleX += toRadians(pitchOfs);
			model.tail.rotateAngleX -= (1 - speed) * vertMulti * 2;
			model.tail.rotateAngleY += toRadians(180 - yawOfs);

			boolean horn = i > model.tailProxy.length - 7 && i < model.tailProxy.length - 3;
			model.tailHornLeft.isHidden = model.tailHornRight.isHidden = !horn;

			float neckScale = lerp(1.5f, 0.3f, vertMulti);
			model.tail.setRenderScale(neckScale);

			model.tailProxy[i].update();

			float tailSize = GOTModelDragon.TAIL_SIZE * model.tail.renderScaleZ - 0.7f;
			model.tail.rotationPointY += sin(model.tail.rotateAngleX) * tailSize;
			model.tail.rotationPointZ -= cos(model.tail.rotateAngleY) * cos(model.tail.rotateAngleX) * tailSize;
			model.tail.rotationPointX -= sin(model.tail.rotateAngleY) * cos(model.tail.rotateAngleX) * tailSize;
		}
	}

	public void animWings(GOTModelDragon model) {

		float aSpeed = sit > 0 ? 0.6f : 1;

		float a1 = animBase * aSpeed * 0.35f;
		float a2 = animBase * aSpeed * 0.5f;
		float a3 = animBase * aSpeed * 0.75f;

		if (ground < 1) {

			wingArmFlutter[0] = 0.125f - cos(animBase) * 0.2f;
			wingArmFlutter[1] = 0.25f;
			wingArmFlutter[2] = (sin(animBase) + 0.125f) * 0.8f;

			wingForearmFlutter[0] = 0;
			wingForearmFlutter[1] = -wingArmFlutter[1] * 2;
			wingForearmFlutter[2] = -(sin(animBase + 2) + 0.5f) * 0.75f;

			wingArmGlide[0] = -0.25f - cos(animBase * 2) * cos(animBase * 1.5f) * 0.04f;
			wingArmGlide[1] = 0.25f;
			wingArmGlide[2] = 0.35f + sin(animBase) * 0.05f;

			wingForearmGlide[0] = 0;
			wingForearmGlide[1] = -wingArmGlide[1] * 2;
			wingForearmGlide[2] = -0.25f + (sin(animBase + 2) + 0.5f) * 0.05f;
		}

		if (ground > 0) {

			wingArmGround[0] = 0;
			wingArmGround[1] = 1.4f - sin(a1) * sin(a2) * 0.02f;
			wingArmGround[2] = 0.8f + sin(a2) * sin(a3) * 0.05f;

			wingArmGround[1] += sin(moveTime * 0.5f) * 0.02f * walk;
			wingArmGround[2] += cos(moveTime * 0.5f) * 0.05f * walk;

			wingForearmGround[0] = 0;
			wingForearmGround[1] = -wingArmGround[1] * 2;
			wingForearmGround[2] = 0;
		}

		slerpArrays(wingArmGlide, wingArmFlutter, wingArm, flutter);
		slerpArrays(wingForearmGlide, wingForearmFlutter, wingForearm, flutter);

		slerpArrays(wingArm, wingArmGround, wingArm, ground);
		slerpArrays(wingForearm, wingForearmGround, wingForearm, ground);

		model.wingArm.rotateAngleX = wingArm[0];
		model.wingArm.rotateAngleY = wingArm[1];
		model.wingArm.rotateAngleZ = wingArm[2];
		model.wingArm.preRotateAngleX = 1 - speed;
		model.wingForearm.rotateAngleX = wingForearm[0];
		model.wingForearm.rotateAngleY = wingForearm[1];
		model.wingForearm.rotateAngleZ = wingForearm[2];

		float[] yFold = {2.7f, 2.8f, 2.9f, 3.0f};
		float[] yUnfold = {0.1f, 0.9f, 1.7f, 2.5f};

		float rotX = 0;
		float rotYOfs = sin(a1) * sin(a2) * 0.03f;
		float rotYMulti = 1;

		for (int i = 0; i < model.wingFinger.length; i++) {
			model.wingFinger[i].rotateAngleX = rotX += 0.005f;
			model.wingFinger[i].rotateAngleY = slerp(yUnfold[i], yFold[i] + rotYOfs * rotYMulti, ground);
			rotYMulti -= 0.2f;
		}
	}

	public float getAnimTime() {
		return anim;
	}

	public float getFlutterTime() {
		return flutter;
	}

	public float getGroundTime() {
		return ground;
	}

	public float getModelOffsetX() {
		return 0;
	}

	public float getModelOffsetY() {
		return -1.5f + sit * 0.6f;
	}

	public float getModelOffsetZ() {
		return -1.5f;
	}

	public float getModelPitch() {
		return getModelPitch(partialTicks);
	}

	public float getModelPitch(float pt) {
		float pitchMovingMax = 90;
		float pitchMoving = (float) clamp(yTrail.get(pt, 5, 0) * 10, -pitchMovingMax, pitchMovingMax);
		float pitchHover = 60;
		return slerp(pitchHover, pitchMoving, speed);
	}

	public float getWalkTime() {
		return walk;
	}

	public boolean isOnGround() {
		return onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	public boolean isOpenJaw() {
		return openJaw;
	}

	public void setOpenJaw(boolean openJaw) {
		this.openJaw = openJaw;
	}

	public void setLook(float lookYaw, float lookPitch) {

		this.lookYaw = clamp(lookYaw, -120, 120);
		this.lookPitch = clamp(lookPitch, -90, 90);
	}

	public void setMovement(float moveTime, float moveSpeed) {
		this.moveTime = moveTime;
		this.moveSpeed = moveSpeed;
	}

	public void setPartialTicks(float partialTicks) {
		this.partialTicks = partialTicks;
	}

	public void setTicksExisted(float ticksExisted) {
		this.ticksExisted = ticksExisted;
	}

	public void slerpArrays(float[] a, float[] b, float[] c, float x) {
		if (a.length != b.length || b.length != c.length) {
			throw new IllegalArgumentException();
		}

		if (x <= 0) {
			System.arraycopy(a, 0, c, 0, a.length);
			return;
		}
		if (x >= 1) {
			System.arraycopy(b, 0, c, 0, a.length);
			return;
		}

		for (int i = 0; i < c.length; i++) {
			c[i] = slerp(a[i], b[i], x);
		}
	}

	public void splineArrays(float x, boolean shift, float[] result, float[]... nodes) {

		int i1 = (int) x % nodes.length;
		int i2 = (i1 + 1) % nodes.length;
		int i3 = (i1 + 2) % nodes.length;

		float[] a1 = nodes[i1];
		float[] a2 = nodes[i2];
		float[] a3 = nodes[i3];

		float xn = x % nodes.length - i1;

		if (shift) {
			interp(xn, result, a2, a3, a1, a2);
		} else {
			interp(xn, result, a1, a2, a3, a1);
		}
	}

	public void update() {

		if (initTrails) {
			yTrail.fill((float) entity.posY);
			yawTrail.fill(entity.renderYawOffset);
			pitchTrail.fill(getModelPitch());
			initTrails = false;
		}

		if (entity.getHealth() <= 0) {
			animTimer.sync();
			groundTimer.sync();
			flutterTimer.sync();
			walkTimer.sync();
			sitTimer.sync();
			jawTimer.sync();
			return;
		}

		float speedMax = 0.05f;
		float speedEnt = (float) (entity.motionX * entity.motionX + entity.motionZ * entity.motionZ);
		float speedMulti = clamp(speedEnt / speedMax, 0, 1);

		float animAdd = 0.035f;

		if (!onGround) {
			animAdd += (1 - speedMulti) * animAdd;
		}

		animTimer.add(animAdd);

		float groundVal = groundTimer.get();
		if (onGround) {
			groundVal *= 0.95f;
			groundVal += 0.08f;
		} else {
			groundVal -= 0.1f;
		}
		groundTimer.set(groundVal);

		boolean flutterFlag = !onGround && (entity.isCollided || entity.motionY > -0.1 || speedEnt < speedMax);
		flutterTimer.add(flutterFlag ? 0.1f : -0.1f);

		boolean walkFlag = moveSpeed > 0.1 && !entity.isSitting();
		float walkVal = 0.1f;
		walkTimer.add(walkFlag ? walkVal : -walkVal);

		float sitVal = sitTimer.get();
		sitVal += entity.isSitting() ? 0.1f : -0.1f;
		sitVal *= 0.95f;
		sitTimer.set(sitVal);

		boolean jawFlag = entity.attackTime < 20 && entity.attackTime > 15;
		jawTimer.add(jawFlag ? 0.2f : -0.2f);

		boolean nearGround = entity.getAltitude() < entity.height * 2;
		boolean speedFlag = speedEnt > speedMax || onGround || nearGround;
		float speedValue = 0.05f;
		speedTimer.add(speedFlag ? speedValue : -speedValue);

		double yawDiff = entity.renderYawOffset - prevRenderYawOffset;
		prevRenderYawOffset = entity.renderYawOffset;

		if (yawDiff < 180 && yawDiff > -180) {
			yawAbs += yawDiff;
		}

		yTrail.update(entity.posY - entity.yOffset);
		yawTrail.update(yawAbs);
		pitchTrail.update(getModelPitch());
	}

}
