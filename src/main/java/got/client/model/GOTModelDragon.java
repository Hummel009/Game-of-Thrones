package got.client.model;

import static org.lwjgl.opengl.GL11.*;

import got.common.entity.dragon.*;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class GOTModelDragon extends ModelBase {
	private static int NECK_SIZE = 10;
	private static int TAIL_SIZE = 10;
	private static int VERTS_NECK = 7;
	private static int VERTS_TAIL = 12;
	private static int HEAD_OFS = -16;
	private ResourceLocation bodyTexture;
	private ResourceLocation glowTexture;
	private ResourceLocation saddleTexture;
	private ResourceLocation eggTexture;
	private GOTModelDragonPart head;
	private GOTModelDragonPart neck;
	private GOTModelDragonPart neckScale;
	private GOTModelDragonPart tail;
	private GOTModelDragonPart tailHornLeft;
	private GOTModelDragonPart tailHornRight;
	private GOTModelDragonPart tailScaleLeft;
	private GOTModelDragonPart tailScaleMiddle;
	private GOTModelDragonPart tailScaleRight;
	private GOTModelDragonPart jaw;
	private GOTModelDragonPart body;
	private GOTModelDragonPart back;
	private GOTModelDragonPart forethigh;
	private GOTModelDragonPart forecrus;
	private GOTModelDragonPart forefoot;
	private GOTModelDragonPart foretoe;
	private GOTModelDragonPart hindthigh;
	private GOTModelDragonPart hindcrus;
	private GOTModelDragonPart hindfoot;
	private GOTModelDragonPart hindtoe;
	private GOTModelDragonPart wingArm;
	private GOTModelDragonPart wingForearm;
	private GOTModelDragonPart[] wingFinger = new GOTModelDragonPart[4];
	private GOTModelDragonPartProxy[] neckProxy = new GOTModelDragonPartProxy[VERTS_NECK];
	private GOTModelDragonPartProxy[] tailProxy = new GOTModelDragonPartProxy[VERTS_TAIL];
	private GOTModelDragonPartProxy[] thighProxy = new GOTModelDragonPartProxy[4];
	private int renderPass = -1;
	private float offsetX;
	private float offsetY;
	private float offsetZ;
	private float pitch;
	private float size;
	private GOTDragonBreed breed;

	public GOTModelDragon(GOTDragonBreed breed) {
		textureWidth = 256;
		textureHeight = 256;
		setBodyTexture(new ResourceLocation("got:textures/entity/animal/dragon/body.png"));
		setGlowTexture(new ResourceLocation("got:textures/entity/animal/dragon/glow.png"));
		setSaddleTexture(new ResourceLocation("got:textures/entity/animal/dragon/saddle.png"));
		eggTexture = new ResourceLocation("got:textures/entity/animal/dragon/egg.png");
		this.breed = breed;
		setTextureOffset("body.body", 0, 0);
		setTextureOffset("body.scale", 0, 32);
		setTextureOffset("head.nostril", 48, 0);
		setTextureOffset("head.upperhead", 0, 0);
		setTextureOffset("head.upperjaw", 56, 88);
		setTextureOffset("head.lowerjaw", 0, 88);
		setTextureOffset("head.horn", 28, 32);
		setTextureOffset("forethigh.main", 112, 0);
		setTextureOffset("forecrus.main", 148, 0);
		setTextureOffset("forefoot.main", 210, 0);
		setTextureOffset("foretoe.main", 176, 0);
		setTextureOffset("hindthigh.main", 112, 29);
		setTextureOffset("hindcrus.main", 152, 29);
		setTextureOffset("hindfoot.main", 180, 29);
		setTextureOffset("hindtoe.main", 215, 29);
		setTextureOffset("neck.box", 112, 88);
		setTextureOffset("neck.scale", 0, 0);
		setTextureOffset("tail.box", 152, 88);
		setTextureOffset("tail.scale", 0, 0);
		setTextureOffset("tail.horn", 0, 117);
		setTextureOffset("wingarm.bone", 0, 152);
		setTextureOffset("wingarm.skin", 116, 232);
		setTextureOffset("wingfinger.bone", 0, 172);
		setTextureOffset("wingfinger.shortskin", -32, 224);
		setTextureOffset("wingfinger.skin", -49, 176);
		setTextureOffset("wingforearm.bone", 0, 164);
		buildBody();
		buildNeck();
		buildHead();
		buildTail();
		buildWing();
		buildLegs();
	}

	public void buildBody() {
		body = new GOTModelDragonPart(this, "body");
		body.setRotationPoint(0, 4, 8);
		body.addBox("body", -12, 0, -16, 24, 24, 64);
		body.addBox("scale", -1, -6, 10, 2, 6, 12);
		body.addBox("scale", -1, -6, 30, 2, 6, 12);

		setBack(body.addChildBox("scale", -1, -6, -10, 2, 6, 12));
	}

	public void buildHead() {
		setHead(new GOTModelDragonPart(this, "head"));
		getHead().addBox("upperjaw", -6, -1, -8 + HEAD_OFS, 12, 5, 16);
		getHead().addBox("upperhead", -8, -8, 6 + HEAD_OFS, 16, 16, 16);
		getHead().addBox("nostril", -5, -3, -6 + HEAD_OFS, 2, 2, 4);
		getHead().mirror = true;
		getHead().addBox("nostril", 3, -3, -6 + HEAD_OFS, 2, 2, 4);
		buildHorn(false);
		buildHorn(true);
		setJaw(getHead().addChildBox("lowerjaw", -6, 0, -16, 12, 4, 16));
		getJaw().setRotationPoint(0, 4, 8 + HEAD_OFS);
	}

	public void buildHorn(boolean mirror) {
		int hornThick = 3;
		int hornLength = 12;

		float hornOfs = -(hornThick / 2f);

		float hornPosX = -5;
		float hornPosY = -8;
		float hornPosZ = 0;

		float hornRotX = GOTModelDragonAnimaton.toRadians(30);
		float hornRotY = GOTModelDragonAnimaton.toRadians(-30);
		float hornRotZ = 0;

		if (mirror) {
			hornPosX *= -1;
			hornRotY *= -1;
		}

		getHead().mirror = mirror;
		GOTModelDragonPart horn = getHead().addChildBox("horn", hornOfs, hornOfs, hornOfs, hornThick, hornThick, hornLength);
		horn.setRotationPoint(hornPosX, hornPosY, hornPosZ);
		horn.setAngles(hornRotX, hornRotY, hornRotZ);
	}

	public void buildLeg(boolean hind) {

		boolean skeleton = "ghost".equals(breed.getName());

		float baseLength = 26;
		String baseName = hind ? "hind" : "fore";

		float thighPosX = -11;
		float thighPosY = 18;
		float thighPosZ = 4;

		int thighThick = 9 - (skeleton ? 2 : 0);
		int thighLength = (int) (baseLength * (hind ? 0.9f : 0.77f));

		if (hind) {
			thighThick++;
			thighPosY -= 5;
		}

		float thighOfs = -(thighThick / 2f);

		GOTModelDragonPart thigh = new GOTModelDragonPart(this, baseName + "thigh");
		thigh.setRotationPoint(thighPosX, thighPosY, thighPosZ);
		thigh.addBox("main", thighOfs, thighOfs, thighOfs, thighThick, thighLength, thighThick);

		float crusPosX = 0;
		float crusPosY = thighLength + thighOfs;
		float crusPosZ = 0;

		int crusThick = thighThick - 2;
		int crusLength = (int) (baseLength * (hind ? 0.7f : 0.8f));

		if (hind) {
			crusThick--;
			crusLength -= 2;
		}

		float crusOfs = -(crusThick / 2f);

		GOTModelDragonPart crus = new GOTModelDragonPart(this, baseName + "crus");
		crus.setRotationPoint(crusPosX, crusPosY, crusPosZ);
		crus.addBox("main", crusOfs, crusOfs, crusOfs, crusThick, crusLength, crusThick);
		thigh.addChild(crus);

		float footPosX = 0;
		float footPosY = crusLength + crusOfs / 2f;
		float footPosZ = 0;

		int footWidth = crusThick + 2 + (skeleton ? 2 : 0);
		int footHeight = 4;
		int footLength = (int) (baseLength * (hind ? 0.67f : 0.34f));

		float footOfsX = -(footWidth / 2f);
		float footOfsY = -(footHeight / 2f);
		float footOfsZ = footLength * -0.75f;

		GOTModelDragonPart foot = new GOTModelDragonPart(this, baseName + "foot");
		foot.setRotationPoint(footPosX, footPosY, footPosZ);
		foot.addBox("main", footOfsX, footOfsY, footOfsZ, footWidth, footHeight, footLength);
		crus.addChild(foot);

		int toeWidth = footWidth;
		int toeHeight = footHeight;
		int toeLength = (int) (baseLength * (hind ? 0.27f : 0.33f));

		float toePosX = 0;
		float toePosY = 0;
		float toePosZ = footOfsZ - footOfsY / 2f;

		float toeOfsX = -(toeWidth / 2f);
		float toeOfsY = -(toeHeight / 2f);
		float toeOfsZ = -toeLength;

		GOTModelDragonPart toe = new GOTModelDragonPart(this, baseName + "toe");
		toe.setRotationPoint(toePosX, toePosY, toePosZ);
		toe.addBox("main", toeOfsX, toeOfsY, toeOfsZ, toeWidth, toeHeight, toeLength);
		foot.addChild(toe);

		if (hind) {
			setHindthigh(thigh);
			setHindcrus(crus);
			setHindfoot(foot);
			setHindtoe(toe);
		} else {
			setForethigh(thigh);
			setForecrus(crus);
			setForefoot(foot);
			setForetoe(toe);
		}
	}

	public void buildLegs() {
		buildLeg(false);
		buildLeg(true);

		for (int i = 0; i < 4; i++) {
			if (i % 2 == 0) {
				getThighProxy()[i] = new GOTModelDragonPartProxy(getForethigh());
			} else {
				getThighProxy()[i] = new GOTModelDragonPartProxy(getHindthigh());
			}
		}
	}

	public void buildNeck() {
		setNeck(new GOTModelDragonPart(this, "neck"));
		getNeck().addBox("box", -5, -5, -5, getNeckSize(), getNeckSize(), getNeckSize());
		setNeckScale(getNeck().addChildBox("scale", -1, -7, -3, 2, 4, 6));

		for (int i = 0; i < getNeckProxy().length; i++) {
			getNeckProxy()[i] = new GOTModelDragonPartProxy(getNeck());
		}
	}

	public void buildTail() {
		setTail(new GOTModelDragonPart(this, "tail"));
		getTail().addBox("box", -5, -5, -5, getTailSize(), getTailSize(), getTailSize());
		float scaleRotZ = GOTModelDragonAnimaton.toRadians(45);
		tailScaleLeft = getTail().addChildBox("scale", -1, -8, -3, 2, 4, 6).setAngles(0, 0, scaleRotZ);
		tailScaleMiddle = getTail().addChildBox("scale", -1, -8, -3, 2, 4, 6).setAngles(0, 0, 0);
		tailScaleRight = getTail().addChildBox("scale", -1, -8, -3, 2, 4, 6).setAngles(0, 0, -scaleRotZ);

		boolean fire = "fire".equals(breed.getName());

		tailScaleMiddle.showModel = !fire;
		tailScaleLeft.showModel = fire;
		tailScaleRight.showModel = fire;

		buildTailHorn(false);
		buildTailHorn(true);

		for (int i = 0; i < getTailProxy().length; i++) {
			getTailProxy()[i] = new GOTModelDragonPartProxy(getTail());
		}
	}

	public void buildTailHorn(boolean mirror) {
		int hornThick = 3;
		int hornLength = 32;

		float hornOfs = -(hornThick / 2f);

		float hornPosX = 0;
		float hornPosY = hornOfs;
		float hornPosZ = getTailSize() / 2f;

		float hornRotX = GOTModelDragonAnimaton.toRadians(-15);
		float hornRotY = GOTModelDragonAnimaton.toRadians(-145);
		float hornRotZ = 0;

		if (mirror) {
			hornPosX *= -1;
			hornRotY *= -1;
		}

		getTail().mirror = mirror;
		GOTModelDragonPart horn = getTail().addChildBox("horn", hornOfs, hornOfs, hornOfs, hornThick, hornThick, hornLength);
		horn.setRotationPoint(hornPosX, hornPosY, hornPosZ);
		horn.setAngles(hornRotX, hornRotY, hornRotZ);
		horn.isHidden = true;
		horn.showModel = "water".equals(breed.getName());

		if (mirror) {
			setTailHornLeft(horn);
		} else {
			setTailHornRight(horn);
		}
	}

	public void buildWing() {
		setWingArm(new GOTModelDragonPart(this, "wingarm"));
		getWingArm().setRotationPoint(-10, 5, 4);
		getWingArm().setRenderScale(1.1f);
		getWingArm().addBox("bone", -28, -3, -3, 28, 6, 6);
		getWingArm().addBox("skin", -28, 0, 2, 28, 0, 24);

		setWingForearm(new GOTModelDragonPart(this, "wingforearm"));
		getWingForearm().setRotationPoint(-28, 0, 0);
		getWingForearm().addBox("bone", -48, -2, -2, 48, 4, 4);
		getWingArm().addChild(getWingForearm());

		getWingFinger()[0] = buildWingFinger(false);
		getWingFinger()[1] = buildWingFinger(false);
		getWingFinger()[2] = buildWingFinger(false);
		getWingFinger()[3] = buildWingFinger(true);
	}

	private GOTModelDragonPart buildWingFinger(boolean small) {
		GOTModelDragonPart finger = new GOTModelDragonPart(this, "wingfinger");
		finger.setRotationPoint(-47, 0, 0);
		finger.addBox("bone", -70, -1, -1, 70, 2, 2);
		if (small) {
			finger.addBox("shortskin", -70, 0, 1, 70, 0, 32);
		} else {
			finger.addBox("skin", -70, 0, 1, 70, 0, 48);
		}
		getWingForearm().addChild(finger);

		return finger;
	}

	public GOTModelDragonPart getBack() {
		return back;
	}

	public ResourceLocation getBodyTexture() {
		return bodyTexture;
	}

	public ResourceLocation getEggTexture() {
		return eggTexture;
	}

	public GOTModelDragonPart getForecrus() {
		return forecrus;
	}

	public GOTModelDragonPart getForefoot() {
		return forefoot;
	}

	public GOTModelDragonPart getForethigh() {
		return forethigh;
	}

	public GOTModelDragonPart getForetoe() {
		return foretoe;
	}

	public ResourceLocation getGlowTexture() {
		return glowTexture;
	}

	public GOTModelDragonPart getHead() {
		return head;
	}

	public GOTModelDragonPart getHindcrus() {
		return hindcrus;
	}

	public GOTModelDragonPart getHindfoot() {
		return hindfoot;
	}

	public GOTModelDragonPart getHindthigh() {
		return hindthigh;
	}

	public GOTModelDragonPart getHindtoe() {
		return hindtoe;
	}

	public GOTModelDragonPart getJaw() {
		return jaw;
	}

	public GOTModelDragonPart getNeck() {
		return neck;
	}

	public GOTModelDragonPartProxy[] getNeckProxy() {
		return neckProxy;
	}

	public GOTModelDragonPart getNeckScale() {
		return neckScale;
	}

	public float getOffsetX() {
		return offsetX;
	}

	public float getOffsetY() {
		return offsetY;
	}

	public float getOffsetZ() {
		return offsetZ;
	}

	public float getPitch() {
		return pitch;
	}

	public int getRenderPass() {
		return renderPass;
	}

	public ResourceLocation getSaddleTexture() {
		return saddleTexture;
	}

	public GOTModelDragonPart getTail() {
		return tail;
	}

	public GOTModelDragonPart getTailHornLeft() {
		return tailHornLeft;
	}

	public GOTModelDragonPart getTailHornRight() {
		return tailHornRight;
	}

	public GOTModelDragonPartProxy[] getTailProxy() {
		return tailProxy;
	}

	public GOTModelDragonPartProxy[] getThighProxy() {
		return thighProxy;
	}

	public GOTModelDragonPart getWingArm() {
		return wingArm;
	}

	public GOTModelDragonPart[] getWingFinger() {
		return wingFinger;
	}

	public GOTModelDragonPart getWingForearm() {
		return wingForearm;
	}

	@Override
	public void render(Entity entity, float moveTime, float moveSpeed, float ticksExisted, float lookYaw, float lookPitch, float scale) {
		render((GOTEntityDragon) entity, moveTime, moveSpeed, ticksExisted, lookYaw, lookPitch, scale);
	}

	public void render(GOTEntityDragon dragon, float moveTime, float moveSpeed, float ticksExisted, float lookYaw, float lookPitch, float scale) {
		GOTModelDragonAnimaton animator = dragon.getAnimator();
		animator.setMovement(moveTime, moveSpeed * dragon.getScale());
		animator.setLook(lookYaw, lookPitch);
		animator.animate(this);

		size = dragon.getScale();

		renderModel(dragon, scale);
	}

	public void renderBody(float scale) {
		body.render(scale);
	}

	public void renderHead(float scale) {
		float headScale = 1.4f / (size + 0.4f);

		getHead().setRenderScale(headScale);
		getHead().render(scale);
	}

	public void renderLegs(float scale) {
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);

		for (int i = 0; i < getThighProxy().length; i++) {
			getThighProxy()[i].render(scale);

			if (i == 1) {

				glScalef(-1, 1, 1);

				glCullFace(GL_FRONT);
			}
		}

		glCullFace(GL_BACK);
		glDisable(GL_CULL_FACE);
	}

	public void renderModel(GOTEntityDragon dragon, float scale) {
		glPushMatrix();
		glTranslatef(getOffsetX(), getOffsetY(), getOffsetZ());
		glRotatef(-getPitch(), 1, 0, 0);

		if (getRenderPass() == 0) {
			renderBody(scale);
		} else {
			renderHead(scale);
			renderNeck(scale);
			renderBody(scale);
			renderLegs(scale);
			renderTail(scale);
			renderWings(scale);
		}

		glPopMatrix();
	}

	public void renderNeck(float scale) {
		for (GOTModelDragonPartProxy proxy : getNeckProxy()) {
			proxy.render(scale);
		}
	}

	public void renderTail(float scale) {
		for (GOTModelDragonPartProxy proxy : getTailProxy()) {
			proxy.render(scale);
		}
	}

	public void renderWings(float scale) {
		glPushMatrix();
		glEnable(GL_CULL_FACE);
		glCullFace(GL_FRONT);
		for (int i = 0; i < 2; i++) {
			getWingArm().render(scale);

			if (i == 0) {

				glScalef(-1, 1, 1);

				glCullFace(GL_BACK);
			}
		}

		glDisable(GL_CULL_FACE);
		glPopMatrix();
	}

	public void setBack(GOTModelDragonPart back) {
		this.back = back;
	}

	public void setBodyTexture(ResourceLocation bodyTexture) {
		this.bodyTexture = bodyTexture;
	}

	public void setForecrus(GOTModelDragonPart forecrus) {
		this.forecrus = forecrus;
	}

	public void setForefoot(GOTModelDragonPart forefoot) {
		this.forefoot = forefoot;
	}

	public void setForethigh(GOTModelDragonPart forethigh) {
		this.forethigh = forethigh;
	}

	public void setForetoe(GOTModelDragonPart foretoe) {
		this.foretoe = foretoe;
	}

	public void setGlowTexture(ResourceLocation glowTexture) {
		this.glowTexture = glowTexture;
	}

	public void setHead(GOTModelDragonPart head) {
		this.head = head;
	}

	public void setHindcrus(GOTModelDragonPart hindcrus) {
		this.hindcrus = hindcrus;
	}

	public void setHindfoot(GOTModelDragonPart hindfoot) {
		this.hindfoot = hindfoot;
	}

	public void setHindthigh(GOTModelDragonPart hindthigh) {
		this.hindthigh = hindthigh;
	}

	public void setHindtoe(GOTModelDragonPart hindtoe) {
		this.hindtoe = hindtoe;
	}

	public void setJaw(GOTModelDragonPart jaw) {
		this.jaw = jaw;
	}

	@Override
	public void setLivingAnimations(EntityLivingBase entity, float moveTime, float moveSpeed, float partialTicks) {
		setLivingAnimations((GOTEntityDragon) entity, moveTime, moveSpeed, partialTicks);
	}

	public void setLivingAnimations(GOTEntityDragon dragon, float moveTime, float moveSpeed, float partialTicks) {
		GOTModelDragonAnimaton animator = dragon.getAnimator();
		animator.setPartialTicks(partialTicks);
	}

	public void setNeck(GOTModelDragonPart neck) {
		this.neck = neck;
	}

	public void setNeckProxy(GOTModelDragonPartProxy[] neckProxy) {
		this.neckProxy = neckProxy;
	}

	public void setNeckScale(GOTModelDragonPart neckScale) {
		this.neckScale = neckScale;
	}

	public void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}

	public void setOffsetY(float offsetY) {
		this.offsetY = offsetY;
	}

	public void setOffsetZ(float offsetZ) {
		this.offsetZ = offsetZ;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setRenderPass(int renderPass) {
		this.renderPass = renderPass;
	}

	public void setSaddleTexture(ResourceLocation saddleTexture) {
		this.saddleTexture = saddleTexture;
	}

	public void setTail(GOTModelDragonPart tail) {
		this.tail = tail;
	}

	public void setTailHornLeft(GOTModelDragonPart tailHornLeft) {
		this.tailHornLeft = tailHornLeft;
	}

	public void setTailHornRight(GOTModelDragonPart tailHornRight) {
		this.tailHornRight = tailHornRight;
	}

	public void setTailProxy(GOTModelDragonPartProxy[] tailProxy) {
		this.tailProxy = tailProxy;
	}

	public void setThighProxy(GOTModelDragonPartProxy[] thighProxy) {
		this.thighProxy = thighProxy;
	}

	public void setWingArm(GOTModelDragonPart wingArm) {
		this.wingArm = wingArm;
	}

	public void setWingFinger(GOTModelDragonPart[] wingFinger) {
		this.wingFinger = wingFinger;
	}

	public void setWingForearm(GOTModelDragonPart wingForearm) {
		this.wingForearm = wingForearm;
	}

	public static int getNeckSize() {
		return NECK_SIZE;
	}

	public static int getTailSize() {
		return TAIL_SIZE;
	}

	public static void setNeckSize(int nECK_SIZE) {
		NECK_SIZE = nECK_SIZE;
	}

	public static void setTailSize(int tAIL_SIZE) {
		TAIL_SIZE = tAIL_SIZE;
	}
}
