package got.client.model;

import got.common.entity.dragon.GOTEntityDragon;
import got.common.util.GOTCircularBuffer;
import got.common.util.GOTTickFloat;
import net.minecraft.util.MathHelper;

public class GOTModelDragonAnimaton {
	private static final float CR00 = -0.5f;
	private static final float CR01 = 1.5f;
	private static final float CR02 = -1.5f;
	private static final float CR03 = 0.5f;
	private static final float CR10 = 1.0f;
	private static final float CR11 = -2.5f;
	private static final float CR12 = 2.0f;
	private static final float CR13 = -0.5f;
	private static final float CR20 = -0.5f;
	private static final float CR21 = 0.0f;
	private static final float CR22 = 0.5f;
	private static final float CR23 = 0.0f;
	private static final float CR30 = 0.0f;
	private static final float CR31 = 1.0f;
	private static final float CR32 = 0.0f;
	private static final float CR33 = 0.0f;

	private static final float PI_F = (float) Math.PI;

	private static final boolean useLUT = true;

	private final GOTEntityDragon entity;
	private final GOTTickFloat animTimer = new GOTTickFloat();
	private final GOTTickFloat groundTimer = new GOTTickFloat(1).setLimit(0, 1);
	private final GOTTickFloat flutterTimer = new GOTTickFloat().setLimit(0, 1);
	private final GOTTickFloat walkTimer = new GOTTickFloat().setLimit(0, 1);
	private final GOTTickFloat sitTimer = new GOTTickFloat().setLimit(0, 1);
	private final GOTTickFloat jawTimer = new GOTTickFloat().setLimit(0, 1);
	private final GOTTickFloat speedTimer = new GOTTickFloat(1).setLimit(0, 1);
	private final GOTCircularBuffer yTrail = new GOTCircularBuffer(8);
	private final GOTCircularBuffer yawTrail = new GOTCircularBuffer(16);
	private final GOTCircularBuffer pitchTrail = new GOTCircularBuffer(16);

	private final float[] wingArm = new float[3];
	private final float[] wingForearm = new float[3];
	private final float[] wingArmFlutter = new float[3];
	private final float[] wingForearmFlutter = new float[3];
	private final float[] wingArmGlide = new float[3];
	private final float[] wingForearmGlide = new float[3];
	private final float[] wingArmGround = new float[3];
	private final float[] wingForearmGround = new float[3];
	private final float[] xGround = new float[]{0, 0, 0, 0};
	private final float[] xGroundWalk2 = new float[]{0, 0, 0, 0};
	private final float[] yGroundStand = new float[]{-0.25f, 0.25f};
	private final float[] yGroundSit = new float[]{0.1f, 0.35f};
	private final float[] yGroundWalk = new float[]{-0.1f, 0.1f};
	private final float[] yAirAll = new float[]{-0.1f, 0.1f};
	private final float[][] xAirAll = new float[][]{new float[]{0, 0, 0, 0}, new float[]{0, 0, 0, 0}};
	private final float[][] xGroundStand = new float[][]{new float[]{0.8f, -1.5f, 1.3f, 0}, new float[]{-0.3f, 1.5f, -0.2f, 0},};
	private final float[][] xGroundSit = new float[][]{new float[]{0.3f, -1.8f, 1.8f, 0}, new float[]{-0.8f, 1.8f, -0.9f, 0},};
	private final float[][][] xGroundWalk = new float[][][]{new float[][]{new float[]{0.4f, -1.4f, 1.3f, 0}, new float[]{0.1f, 1.2f, -0.5f, 0}}, new float[][]{new float[]{1.2f, -1.6f, 1.3f, 0}, new float[]{-0.3f, 2.1f, -0.9f, 0.6f}}, new float[][]{new float[]{0.9f, -2.1f, 1.8f, 0.6f}, new float[]{-0.7f, 1.4f, -0.2f, 0}}};

	private boolean initTrails = true;
	private boolean onGround;
	private boolean wingsDown;

	private double prevRenderYawOffset;
	private double yawAbs;

	private float partialTicks;
	private float ticksExisted;
	private float moveTime;
	private float moveSpeed;
	private float lookYaw;
	private float lookPitch;
	private float animBase;
	private float cycleOfs;
	private float ground;
	private float flutter;
	private float walk;
	private float sit;
	private float jaw;
	private float speed;

	public GOTModelDragonAnimaton(GOTEntityDragon dragon) {
		entity = dragon;
	}

	public static double clamp(double value, double min, double max) {
		return value < min ? min : Math.min(value, max);
	}

	public static float clamp(float value, float min, float max) {
		return value < min ? min : Math.min(value, max);
	}

	private static float cos(float a) {
		if (useLUT) {
			return MathHelper.cos(a);
		}
		return (float) Math.cos(a);
	}

	private static void interp(float x, float[] result, float[]... knots) {
		float x1 = x;
		int nknots = knots.length;
		int nspans = nknots - 3;
		int knot = 0;
		if (nspans < 1) {
			System.out.println(GOTModelDragonAnimaton.class.getName() + " Spline has too few knots");
			return;
		}
		x1 = clamp(x1, 0, 0.9999f) * nspans;
		int span = (int) x1;
		if (span >= nknots - 3) {
			span = nknots - 3;
		}
		x1 -= span;
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
			result[i] = ((c3 * x1 + c2) * x1 + c1) * x1 + c0;
		}
	}

	public static double lerp(double a, double b, double x) {
		return a * (1 - x) + b * x;
	}

	public static float lerp(float a, float b, float x) {
		return a * (1 - x) + b * x;
	}

	public static double normDeg(double a) {
		double a1 = a;
		a1 %= 360;
		if (a1 >= 180) {
			a1 -= 360;
		}
		if (a1 < -180) {
			a1 += 360;
		}
		return a1;
	}

	private static float normDeg(float a) {
		float a1 = a;
		a1 %= 360;
		if (a1 >= 180) {
			a1 -= 360;
		}
		if (a1 < -180) {
			a1 += 360;
		}
		return a1;
	}

	private static float sin(float a) {
		if (useLUT) {
			return MathHelper.sin(a);
		}
		return (float) Math.sin(a);
	}

	private static float slerp(float a, float b, float x) {
		if (x <= 0) {
			return a;
		}
		if (x >= 1) {
			return b;
		}
		return lerp(a, b, x * x * (3 - 2 * x));
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
		float anim = animTimer.get(partialTicks);
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
		model.getBack().isHidden = entity.isSaddled();
		cycleOfs = cycleOfs * (cycleOfs + 2) * 0.05f;
		cycleOfs *= lerp(0.5f, 1, flutter);
		cycleOfs *= lerp(1, 0.5f, ground);
		model.setOffsetX(getModelOffsetX());
		model.setOffsetY(getModelOffsetY());
		model.setOffsetZ(getModelOffsetZ());
		model.setPitch(getModelPitch());
		animHeadAndNeck(model);
		animTail(model);
		animWings(model);
		animLegs(model);
	}

	private void animHeadAndNeck(GOTModelDragon model) {
		model.getNeck().rotationPointX = 0;
		model.getNeck().rotationPointY = 14;
		model.getNeck().rotationPointZ = -8;
		model.getNeck().rotateAngleX = 0;
		model.getNeck().rotateAngleY = 0;
		model.getNeck().rotateAngleZ = 0;
		double health = entity.getHealthRelative();
		float neckSize;
		for (int i = 0; i < model.getNeckProxyLength(); i++) {
			float vertMulti = (i + 1) / (float) model.getNeckProxyLength();
			float baseRotX = cos(i * 0.45f + animBase) * 0.15f;
			baseRotX *= lerp(0.2f, 1, flutter);
			baseRotX *= lerp(1, 0.2f, sit);
			float ofsRotX = sin(vertMulti * PI_F * 0.9f) * 0.75f;
			model.getNeck().rotateAngleX = baseRotX;
			model.getNeck().rotateAngleX *= slerp(1, 0.5f, walk);
			model.getNeck().rotateAngleX += (1 - speed) * vertMulti;
			model.getNeck().rotateAngleX -= (float) lerp(0, ofsRotX, ground * health);
			model.getNeck().rotateAngleY = toRadians(lookYaw) * vertMulti * speed;
			float temp = lerp(1.6f, 1, vertMulti);
			model.getNeck().setRenderScaleY(temp);
			model.getNeck().setRenderScaleX(temp);
			model.getNeck().setRenderScaleZ(0.6f);
			model.getNeckScale().isHidden = i % 2 != 0 || i == 0;
			model.getNeckProxyItem(i).update();
			neckSize = GOTModelDragon.NECK_SIZE * model.getNeck().getRenderScaleZ() - 1.4f;
			model.getNeck().rotationPointX -= sin(model.getNeck().rotateAngleY) * cos(model.getNeck().rotateAngleX) * neckSize;
			model.getNeck().rotationPointY += sin(model.getNeck().rotateAngleX) * neckSize;
			model.getNeck().rotationPointZ -= cos(model.getNeck().rotateAngleY) * cos(model.getNeck().rotateAngleX) * neckSize;
		}
		model.getHead().rotateAngleX = toRadians(lookPitch) + (1 - speed);
		model.getHead().rotateAngleY = model.getNeck().rotateAngleY;
		model.getHead().rotateAngleZ = model.getNeck().rotateAngleZ * 0.2f;
		model.getHead().rotationPointX = model.getNeck().rotationPointX;
		model.getHead().rotationPointY = model.getNeck().rotationPointY;
		model.getHead().rotationPointZ = model.getNeck().rotationPointZ;
		model.getJaw().rotateAngleX = jaw * 0.75f;
		model.getJaw().rotateAngleX += (1 - sin(animBase)) * 0.1f * flutter;
	}

	private void animLegs(GOTModelDragon model) {
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
		for (int i = 0; i < model.getThighProxyLength(); i++) {
			GOTModelDragonPart thigh;
			GOTModelDragonPart crus;
			GOTModelDragonPart foot;
			GOTModelDragonPart toe;
			if (i % 2 == 0) {
				thigh = model.getForethigh();
				crus = model.getForecrus();
				foot = model.getForefoot();
				toe = model.getForetoe();
				thigh.rotationPointZ = 4;
			} else {
				thigh = model.getHindthigh();
				crus = model.getHindcrus();
				foot = model.getHindfoot();
				toe = model.getHindtoe();
				thigh.rotationPointZ = 46;
			}
			float[] xAir = xAirAll[i % 2];
			slerpArrays(xGroundStand[i % 2], xGroundSit[i % 2], xGround, sit);
			xGround[3] = -(xGround[0] + xGround[1] + xGround[2]);
			if (walk > 0) {
				splineArrays(moveTime * 0.2f, i > 1, xGroundWalk2, xGroundWalk[0][i % 2], xGroundWalk[1][i % 2], xGroundWalk[2][i % 2]);
				xGroundWalk2[3] -= xGroundWalk2[0] + xGroundWalk2[1] + xGroundWalk2[2];
				slerpArrays(xGround, xGroundWalk2, xGround, walk);
			}
			float yAir = yAirAll[i % 2];
			float yGround;
			yGround = slerp(yGroundStand[i % 2], yGroundSit[i % 2], sit);
			yGround = slerp(yGround, yGroundWalk[i % 2], walk);
			thigh.rotateAngleY = slerp(yAir, yGround, ground);
			thigh.rotateAngleX = slerp(xAir[0], xGround[0], ground);
			crus.rotateAngleX = slerp(xAir[1], xGround[1], ground);
			foot.rotateAngleX = slerp(xAir[2], xGround[2], ground);
			toe.rotateAngleX = slerp(xAir[3], xGround[3], ground);
			model.getThighProxyItem(i).update();
		}
	}

	private void animTail(GOTModelDragon model) {
		model.getTail().rotationPointX = 0;
		model.getTail().rotationPointY = 16;
		model.getTail().rotationPointZ = 62;
		model.getTail().rotateAngleX = 0;
		model.getTail().rotateAngleY = 0;
		model.getTail().rotateAngleZ = 0;
		float rotXStand;
		float rotYStand = 0;
		float rotXSit;
		float rotYSit;
		float rotXAir = 0;
		float rotYAir = 0;
		for (int i = 0; i < model.getTailProxyLength(); i++) {
			float vertMulti = (i + 1) / (float) model.getTailProxyLength();
			float amp = 0.1f + i / (model.getTailProxyLength() * 2.0f);
			rotXStand = (i - model.getTailProxyLength() * 0.6f) * -amp * 0.4f;
			rotXStand += (sin(animBase * 0.2f) * sin(animBase * 0.37f) * 0.4f * amp - 0.1f) * (1 - sit);
			rotXSit = rotXStand * 0.8f;
			rotYStand = (rotYStand + sin(i * 0.45f + animBase * 0.5f)) * amp * 0.4f;
			rotYSit = sin(vertMulti * PI_F) * PI_F * 1.2f - 0.5f;
			rotXAir -= sin(i * 0.45f + animBase) * 0.04f * lerp(0.3f, 1, flutter);
			model.getTail().rotateAngleX = lerp(rotXStand, rotXSit, sit);
			model.getTail().rotateAngleY = lerp(rotYStand, rotYSit, sit);
			model.getTail().rotateAngleX = lerp(rotXAir, model.getTail().rotateAngleX, ground);
			model.getTail().rotateAngleY = lerp(rotYAir, model.getTail().rotateAngleY, ground);
			float angleLimit = 160 * vertMulti;
			float yawOfs = clamp((float) yawTrail.get(partialTicks, 0, i + 1) * 2, -angleLimit, angleLimit);
			float pitchOfs = clamp((float) pitchTrail.get(partialTicks, 0, i + 1) * 2, -angleLimit, angleLimit);
			model.getTail().rotateAngleX += toRadians(pitchOfs);
			model.getTail().rotateAngleX -= (1 - speed) * vertMulti * 2;
			model.getTail().rotateAngleY += toRadians(180 - yawOfs);
			boolean horn = i > model.getTailProxyLength() - 7 && i < model.getTailProxyLength() - 3;
			model.getTailHornLeft().isHidden = model.getTailHornRight().isHidden = !horn;
			float neckScale = lerp(1.5f, 0.3f, vertMulti);
			model.getTail().setRenderScale(neckScale);
			model.getTailProxyItem(i).update();
			float tailSize = GOTModelDragon.TAIL_SIZE * model.getTail().getRenderScaleZ() - 0.7f;
			model.getTail().rotationPointY += sin(model.getTail().rotateAngleX) * tailSize;
			model.getTail().rotationPointZ -= cos(model.getTail().rotateAngleY) * cos(model.getTail().rotateAngleX) * tailSize;
			model.getTail().rotationPointX -= sin(model.getTail().rotateAngleY) * cos(model.getTail().rotateAngleX) * tailSize;
		}
	}

	private void animWings(GOTModelDragon model) {
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
		model.getWingArm().rotateAngleX = wingArm[0];
		model.getWingArm().rotateAngleY = wingArm[1];
		model.getWingArm().rotateAngleZ = wingArm[2];
		model.getWingArm().setPreRotateAngleX(1 - speed);
		model.getWingForearm().rotateAngleX = wingForearm[0];
		model.getWingForearm().rotateAngleY = wingForearm[1];
		model.getWingForearm().rotateAngleZ = wingForearm[2];
		float[] yFold = new float[]{2.7f, 2.8f, 2.9f, 3.0f};
		float[] yUnfold = new float[]{0.1f, 0.9f, 1.7f, 2.5f};
		float rotX = 0;
		float rotYOfs = sin(a1) * sin(a2) * 0.03f;
		float rotYMulti = 1;
		for (int i = 0; i < model.getWingFingerLength(); i++) {
			model.getWingFingerItem(i).rotateAngleX = rotX += 0.005f;
			model.getWingFingerItem(i).rotateAngleY = slerp(yUnfold[i], yFold[i] + rotYOfs * rotYMulti, ground);
			rotYMulti -= 0.2f;
		}
	}

	private float getModelOffsetX() {
		return 0;
	}

	private float getModelOffsetY() {
		return -1.5f + sit * 0.6f;
	}

	private float getModelOffsetZ() {
		return -1.5f;
	}

	private float getModelPitch() {
		return getModelPitch(partialTicks);
	}

	private float getModelPitch(float pt) {
		float pitchMovingMax = 90;
		float pitchMoving = (float) clamp(yTrail.get(pt, 5, 0) * 10, -pitchMovingMax, pitchMovingMax);
		float pitchHover = 60;
		return slerp(pitchHover, pitchMoving, speed);
	}

	@SuppressWarnings("unused")
	public boolean isOnGround() {
		return onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	public void setLook(float lookYaw, float lookPitch) {
		this.lookYaw = clamp(lookYaw, -120, 120);
		this.lookPitch = clamp(lookPitch, -90, 90);
	}

	public void setMovement(float moveTime, float moveSpeed) {
		this.moveTime = moveTime;
		this.moveSpeed = moveSpeed;
	}

	@SuppressWarnings("unused")
	public float getPartialTicks() {
		return partialTicks;
	}

	public void setPartialTicks(float partialTicks) {
		this.partialTicks = partialTicks;
	}

	@SuppressWarnings("unused")
	public float getTicksExisted() {
		return ticksExisted;
	}

	public void setTicksExisted(float ticksExisted) {
		this.ticksExisted = ticksExisted;
	}

	private void slerpArrays(float[] a, float[] b, float[] c, float x) {
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

	private void splineArrays(float x, boolean shift, float[] result, float[]... nodes) {
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