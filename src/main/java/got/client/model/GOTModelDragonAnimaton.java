package got.client.model;

import got.common.entity.dragon.GOTEntityDragon;
import got.common.util.*;
import net.minecraft.util.MathHelper;

public class GOTModelDragonAnimaton {
	private static float CR00 = -0.5f;
	private static float CR01 = 1.5f;
	private static float CR02 = -1.5f;
	private static float CR03 = 0.5f;
	private static float CR10 = 1.0f;
	private static float CR11 = -2.5f;
	private static float CR12 = 2.0f;
	private static float CR13 = -0.5f;
	private static float CR20 = -0.5f;
	private static float CR21 = 0.0f;
	private static float CR22 = 0.5f;
	private static float CR23 = 0.0f;
	private static float CR30 = 0.0f;
	private static float CR31 = 1.0f;
	private static float CR32 = 0.0f;
	private static float CR33 = 0.0f;

	private static float PI_F = (float) Math.PI;

	private static boolean useLUT = true;
	private GOTEntityDragon entity;
	private float partialTicks;

	private float moveTime;
	private float moveSpeed;
	private float lookYaw;
	private float lookPitch;
	private double prevRenderYawOffset;
	private double yawAbs;
	private float animBase;
	private float cycleOfs;

	private float ground;
	private float flutter;
	private float walk;
	private float sit;
	private float jaw;
	private float speed;
	private GOTTickFloat animTimer = new GOTTickFloat();
	private GOTTickFloat groundTimer = new GOTTickFloat(1).setLimit(0, 1);

	private GOTTickFloat flutterTimer = new GOTTickFloat().setLimit(0, 1);
	private GOTTickFloat walkTimer = new GOTTickFloat().setLimit(0, 1);
	private GOTTickFloat sitTimer = new GOTTickFloat().setLimit(0, 1);
	private GOTTickFloat jawTimer = new GOTTickFloat().setLimit(0, 1);
	private GOTTickFloat speedTimer = new GOTTickFloat(1).setLimit(0, 1);
	private boolean initTrails = true;
	private GOTCircularBuffer yTrail = new GOTCircularBuffer(8);

	private GOTCircularBuffer yawTrail = new GOTCircularBuffer(16);
	private GOTCircularBuffer pitchTrail = new GOTCircularBuffer(16);
	private boolean onGround;
	private boolean wingsDown;
	private float[] wingArm = new float[3];
	private float[] wingForearm = new float[3];

	private float[] wingArmFlutter = new float[3];
	private float[] wingForearmFlutter = new float[3];
	private float[] wingArmGlide = new float[3];
	private float[] wingForearmGlide = new float[3];
	private float[] wingArmGround = new float[3];
	private float[] wingForearmGround = new float[3];
	private float[] xGround = { 0, 0, 0, 0 };
	private float[][] xGroundStand = { { 0.8f, -1.5f, 1.3f, 0 }, { -0.3f, 1.5f, -0.2f, 0 }, };

	private float[][] xGroundSit = { { 0.3f, -1.8f, 1.8f, 0 }, { -0.8f, 1.8f, -0.9f, 0 }, };

	private float[][][] xGroundWalk = { { { 0.4f, -1.4f, 1.3f, 0 }, { 0.1f, 1.2f, -0.5f, 0 } }, { { 1.2f, -1.6f, 1.3f, 0 }, { -0.3f, 2.1f, -0.9f, 0.6f } }, { { 0.9f, -2.1f, 1.8f, 0.6f }, { -0.7f, 1.4f, -0.2f, 0 } } };
	private float[] xGroundWalk2 = { 0, 0, 0, 0 };

	private float[] yGroundStand = { -0.25f, 0.25f };

	private float[] yGroundSit = { 0.1f, 0.35f };

	private float[] yGroundWalk = { -0.1f, 0.1f };
	private float[] xAir;
	private float[][] xAirAll = { { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };

	private float[] yAirAll = { -0.1f, 0.1f };

	public GOTModelDragonAnimaton(GOTEntityDragon dragon) {
		entity = dragon;
	}

	public void animate(GOTModelDragon model) {
		float anim = animTimer.get(partialTicks);
		ground = groundTimer.get(partialTicks);
		flutter = flutterTimer.get(partialTicks);
		walk = walkTimer.get(partialTicks);
		sit = sitTimer.get(partialTicks);
		jaw = jawTimer.get(partialTicks);
		speed = speedTimer.get(partialTicks);

		animBase = anim * GOTModelDragonAnimaton.PI_F * 2;
		cycleOfs = GOTModelDragonAnimaton.sin(animBase - 1) + 1;

		boolean newWingsDown = cycleOfs > 1;
		if (newWingsDown && !wingsDown && flutter != 0) {
			entity.onWingsDown(speed);
		}
		wingsDown = newWingsDown;

		model.getBack().isHidden = entity.isSaddled();

		cycleOfs = (cycleOfs * cycleOfs + cycleOfs * 2) * 0.05f;

		cycleOfs *= GOTModelDragonAnimaton.lerp(0.5f, 1, flutter);
		cycleOfs *= GOTModelDragonAnimaton.lerp(1, 0.5f, ground);

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

		for (int i = 0; i < model.getNeckProxy().length; i++) {
			float vertMulti = (i + 1) / (float) model.getNeckProxy().length;

			float baseRotX = GOTModelDragonAnimaton.cos(i * 0.45f + animBase) * 0.15f;
			baseRotX *= GOTModelDragonAnimaton.lerp(0.2f, 1, flutter);
			baseRotX *= GOTModelDragonAnimaton.lerp(1, 0.2f, sit);
			float ofsRotX = GOTModelDragonAnimaton.sin(vertMulti * GOTModelDragonAnimaton.PI_F * 0.9f) * 0.75f;

			model.getNeck().rotateAngleX = baseRotX;

			model.getNeck().rotateAngleX *= GOTModelDragonAnimaton.slerp(1, 0.5f, walk);

			model.getNeck().rotateAngleX += (1 - speed) * vertMulti;

			model.getNeck().rotateAngleX -= GOTModelDragonAnimaton.lerp(0, ofsRotX, ground * health);

			model.getNeck().rotateAngleY = GOTModelDragonAnimaton.toRadians(lookYaw) * vertMulti * speed;

			model.getNeck().renderScaleX = model.getNeck().renderScaleY = GOTModelDragonAnimaton.lerp(1.6f, 1, vertMulti);
			model.getNeck().renderScaleZ = 0.6f;

			model.getNeckScale().isHidden = i % 2 != 0 || i == 0;

			model.getNeckProxy()[i].update();

			neckSize = GOTModelDragon.getNeckSize() * model.getNeck().renderScaleZ - 1.4f;
			model.getNeck().rotationPointX -= GOTModelDragonAnimaton.sin(model.getNeck().rotateAngleY) * GOTModelDragonAnimaton.cos(model.getNeck().rotateAngleX) * neckSize;
			model.getNeck().rotationPointY += GOTModelDragonAnimaton.sin(model.getNeck().rotateAngleX) * neckSize;
			model.getNeck().rotationPointZ -= GOTModelDragonAnimaton.cos(model.getNeck().rotateAngleY) * GOTModelDragonAnimaton.cos(model.getNeck().rotateAngleX) * neckSize;
		}

		model.getHead().rotateAngleX = GOTModelDragonAnimaton.toRadians(lookPitch) + (1 - speed);
		model.getHead().rotateAngleY = model.getNeck().rotateAngleY;
		model.getHead().rotateAngleZ = model.getNeck().rotateAngleZ * 0.2f;

		model.getHead().rotationPointX = model.getNeck().rotationPointX;
		model.getHead().rotationPointY = model.getNeck().rotationPointY;
		model.getHead().rotationPointZ = model.getNeck().rotationPointZ;

		model.getJaw().rotateAngleX = jaw * 0.75f;
		model.getJaw().rotateAngleX += (1 - GOTModelDragonAnimaton.sin(animBase)) * 0.1f * flutter;
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

		for (int i = 0; i < model.getThighProxy().length; i++) {
			GOTModelDragonPart thigh, crus, foot, toe;

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

			xAir = xAirAll[i % 2];

			slerpArrays(xGroundStand[i % 2], xGroundSit[i % 2], xGround, sit);

			xGround[3] = -(xGround[0] + xGround[1] + xGround[2]);

			if (walk > 0) {

				splineArrays(moveTime * 0.2f, i > 1, xGroundWalk2, xGroundWalk[0][i % 2], xGroundWalk[1][i % 2], xGroundWalk[2][i % 2]);

				xGroundWalk2[3] -= xGroundWalk2[0] + xGroundWalk2[1] + xGroundWalk2[2];

				slerpArrays(xGround, xGroundWalk2, xGround, walk);
			}

			float yAir = yAirAll[i % 2];
			float yGround;

			yGround = GOTModelDragonAnimaton.slerp(yGroundStand[i % 2], yGroundSit[i % 2], sit);

			yGround = GOTModelDragonAnimaton.slerp(yGround, yGroundWalk[i % 2], walk);

			thigh.rotateAngleY = GOTModelDragonAnimaton.slerp(yAir, yGround, ground);
			thigh.rotateAngleX = GOTModelDragonAnimaton.slerp(xAir[0], xGround[0], ground);
			crus.rotateAngleX = GOTModelDragonAnimaton.slerp(xAir[1], xGround[1], ground);
			foot.rotateAngleX = GOTModelDragonAnimaton.slerp(xAir[2], xGround[2], ground);
			toe.rotateAngleX = GOTModelDragonAnimaton.slerp(xAir[3], xGround[3], ground);

			model.getThighProxy()[i].update();
		}
	}

	private void animTail(GOTModelDragon model) {
		model.getTail().rotationPointX = 0;
		model.getTail().rotationPointY = 16;
		model.getTail().rotationPointZ = 62;

		model.getTail().rotateAngleX = 0;
		model.getTail().rotateAngleY = 0;
		model.getTail().rotateAngleZ = 0;

		float rotXStand = 0;
		float rotYStand = 0;
		float rotXSit = 0;
		float rotYSit = 0;
		float rotXAir = 0;
		float rotYAir = 0;

		for (int i = 0; i < model.getTailProxy().length; i++) {
			float vertMulti = (i + 1) / (float) model.getTailProxy().length;

			float amp = 0.1f + i / (model.getTailProxy().length * 2f);

			rotXStand = (i - model.getTailProxy().length * 0.6f) * -amp * 0.4f;
			rotXStand += (GOTModelDragonAnimaton.sin(animBase * 0.2f) * GOTModelDragonAnimaton.sin(animBase * 0.37f) * 0.4f * amp - 0.1f) * (1 - sit);
			rotXSit = rotXStand * 0.8f;

			rotYStand = (rotYStand + GOTModelDragonAnimaton.sin(i * 0.45f + animBase * 0.5f)) * amp * 0.4f;
			rotYSit = GOTModelDragonAnimaton.sin(vertMulti * GOTModelDragonAnimaton.PI_F) * GOTModelDragonAnimaton.PI_F * 1.2f - 0.5f;

			rotXAir -= GOTModelDragonAnimaton.sin(i * 0.45f + animBase) * 0.04f * GOTModelDragonAnimaton.lerp(0.3f, 1, flutter);

			model.getTail().rotateAngleX = GOTModelDragonAnimaton.lerp(rotXStand, rotXSit, sit);
			model.getTail().rotateAngleY = GOTModelDragonAnimaton.lerp(rotYStand, rotYSit, sit);

			model.getTail().rotateAngleX = GOTModelDragonAnimaton.lerp(rotXAir, model.getTail().rotateAngleX, ground);
			model.getTail().rotateAngleY = GOTModelDragonAnimaton.lerp(rotYAir, model.getTail().rotateAngleY, ground);

			float angleLimit = 160 * vertMulti;
			float yawOfs = GOTModelDragonAnimaton.clamp((float) yawTrail.get(partialTicks, 0, i + 1) * 2, -angleLimit, angleLimit);
			float pitchOfs = GOTModelDragonAnimaton.clamp((float) pitchTrail.get(partialTicks, 0, i + 1) * 2, -angleLimit, angleLimit);

			model.getTail().rotateAngleX += GOTModelDragonAnimaton.toRadians(pitchOfs);
			model.getTail().rotateAngleX -= (1 - speed) * vertMulti * 2;
			model.getTail().rotateAngleY += GOTModelDragonAnimaton.toRadians(180 - yawOfs);

			boolean horn = i > model.getTailProxy().length - 7 && i < model.getTailProxy().length - 3;
			model.getTailHornLeft().isHidden = model.getTailHornRight().isHidden = !horn;

			float neckScale = GOTModelDragonAnimaton.lerp(1.5f, 0.3f, vertMulti);
			model.getTail().setRenderScale(neckScale);

			model.getTailProxy()[i].update();

			float tailSize = GOTModelDragon.getTailSize() * model.getTail().renderScaleZ - 0.7f;
			model.getTail().rotationPointY += GOTModelDragonAnimaton.sin(model.getTail().rotateAngleX) * tailSize;
			model.getTail().rotationPointZ -= GOTModelDragonAnimaton.cos(model.getTail().rotateAngleY) * GOTModelDragonAnimaton.cos(model.getTail().rotateAngleX) * tailSize;
			model.getTail().rotationPointX -= GOTModelDragonAnimaton.sin(model.getTail().rotateAngleY) * GOTModelDragonAnimaton.cos(model.getTail().rotateAngleX) * tailSize;
		}
	}

	private void animWings(GOTModelDragon model) {

		float aSpeed = sit > 0 ? 0.6f : 1;

		float a1 = animBase * aSpeed * 0.35f;
		float a2 = animBase * aSpeed * 0.5f;
		float a3 = animBase * aSpeed * 0.75f;

		if (ground < 1) {

			wingArmFlutter[0] = 0.125f - GOTModelDragonAnimaton.cos(animBase) * 0.2f;
			wingArmFlutter[1] = 0.25f;
			wingArmFlutter[2] = (GOTModelDragonAnimaton.sin(animBase) + 0.125f) * 0.8f;

			wingForearmFlutter[0] = 0;
			wingForearmFlutter[1] = -wingArmFlutter[1] * 2;
			wingForearmFlutter[2] = -(GOTModelDragonAnimaton.sin(animBase + 2) + 0.5f) * 0.75f;

			wingArmGlide[0] = -0.25f - GOTModelDragonAnimaton.cos(animBase * 2) * GOTModelDragonAnimaton.cos(animBase * 1.5f) * 0.04f;
			wingArmGlide[1] = 0.25f;
			wingArmGlide[2] = 0.35f + GOTModelDragonAnimaton.sin(animBase) * 0.05f;

			wingForearmGlide[0] = 0;
			wingForearmGlide[1] = -wingArmGlide[1] * 2;
			wingForearmGlide[2] = -0.25f + (GOTModelDragonAnimaton.sin(animBase + 2) + 0.5f) * 0.05f;
		}

		if (ground > 0) {

			wingArmGround[0] = 0;
			wingArmGround[1] = 1.4f - GOTModelDragonAnimaton.sin(a1) * GOTModelDragonAnimaton.sin(a2) * 0.02f;
			wingArmGround[2] = 0.8f + GOTModelDragonAnimaton.sin(a2) * GOTModelDragonAnimaton.sin(a3) * 0.05f;

			wingArmGround[1] += GOTModelDragonAnimaton.sin(moveTime * 0.5f) * 0.02f * walk;
			wingArmGround[2] += GOTModelDragonAnimaton.cos(moveTime * 0.5f) * 0.05f * walk;

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
		model.getWingArm().preRotateAngleX = 1 - speed;
		model.getWingForearm().rotateAngleX = wingForearm[0];
		model.getWingForearm().rotateAngleY = wingForearm[1];
		model.getWingForearm().rotateAngleZ = wingForearm[2];

		float[] yFold = { 2.7f, 2.8f, 2.9f, 3.0f };
		float[] yUnfold = { 0.1f, 0.9f, 1.7f, 2.5f };

		float rotX = 0;
		float rotYOfs = GOTModelDragonAnimaton.sin(a1) * GOTModelDragonAnimaton.sin(a2) * 0.03f;
		float rotYMulti = 1;

		for (int i = 0; i < model.getWingFinger().length; i++) {
			model.getWingFinger()[i].rotateAngleX = rotX += 0.005f;
			model.getWingFinger()[i].rotateAngleY = GOTModelDragonAnimaton.slerp(yUnfold[i], yFold[i] + rotYOfs * rotYMulti, ground);
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
		float pitchMoving = (float) GOTModelDragonAnimaton.clamp(yTrail.get(pt, 5, 0) * 10, -pitchMovingMax, pitchMovingMax);
		float pitchHover = 60;
		return GOTModelDragonAnimaton.slerp(pitchHover, pitchMoving, speed);
	}

	public void setLook(float lookYaw, float lookPitch) {

		this.lookYaw = GOTModelDragonAnimaton.clamp(lookYaw, -120, 120);
		this.lookPitch = GOTModelDragonAnimaton.clamp(lookPitch, -90, 90);
	}

	public void setMovement(float moveTime, float moveSpeed) {
		this.moveTime = moveTime;
		this.moveSpeed = moveSpeed;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	public void setOpenJaw(boolean openJaw) {
	}

	public void setPartialTicks(float partialTicks) {
		this.partialTicks = partialTicks;
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
			c[i] = GOTModelDragonAnimaton.slerp(a[i], b[i], x);
		}
	}

	private void splineArrays(float x, boolean shift, float[] result, float[]... nodes) {

//        if (true) {
//            if (shift) {
//                System.arraycopy(nodes[(int) (x + 1) % nodes.length], 0, result, 0, nodes.length);
//            } else {
//                System.arraycopy(nodes[(int) x % nodes.length], 0, result, 0, nodes.length);
//            }
//            return;
//        }

		int i1 = (int) x % nodes.length;
		int i2 = (i1 + 1) % nodes.length;
		int i3 = (i1 + 2) % nodes.length;

		float[] a1 = nodes[i1];
		float[] a2 = nodes[i2];
		float[] a3 = nodes[i3];

		float xn = x % nodes.length - i1;

		if (shift) {
			GOTModelDragonAnimaton.interp(xn, result, a2, a3, a1, a2);
		} else {
			GOTModelDragonAnimaton.interp(xn, result, a1, a2, a3, a1);
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
		float speedMulti = GOTModelDragonAnimaton.clamp(speedEnt / speedMax, 0, 1);

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

	public static double clamp(double value, double min, double max) {
		return value < min ? min : value > max ? max : value;
	}

	public static float clamp(float value, float min, float max) {
		return value < min ? min : value > max ? max : value;
	}

	private static float cos(float a) {
		if (useLUT) {
			return MathHelper.cos(a);
		}
		return (float) Math.cos(a);
	}

	private static void interp(float x, float[] result, float[]... knots) {
		int nknots = knots.length;
		int nspans = nknots - 3;
		int knot = 0;
		if (nspans < 1) {
			System.out.println(GOTModelDragonAnimaton.class.getName() + " Spline has too few knots");
			return;
		}
		x = GOTModelDragonAnimaton.clamp(x, 0, 0.9999f) * nspans;
		int span = (int) x;
		if (span >= nknots - 3) {
			span = nknots - 3;
		}
		x -= span;
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

	private static float normDeg(float a) {
		a %= 360;
		if (a >= 180) {
			a -= 360;
		}
		if (a < -180) {
			a += 360;
		}
		return a;
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

}
