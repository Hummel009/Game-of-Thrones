package got.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GOTModelWyvern extends ModelBase {
	private final float radianF = 57.29578f;
	private final ModelRenderer back1;
	private final ModelRenderer Tail;
	private final ModelRenderer tail1;
	private final ModelRenderer tail2;
	private final ModelRenderer tail3;
	private final ModelRenderer tail4;
	private final ModelRenderer tail5;
	private final ModelRenderer chest;
	private final ModelRenderer neckplate3;
	private final ModelRenderer neck3;
	private final ModelRenderer neck2;
	private final ModelRenderer neck1;
	private final ModelRenderer rightupleg;
	private final ModelRenderer rightmidleg;
	private final ModelRenderer rightlowleg;
	private final ModelRenderer rightfoot;
	private final ModelRenderer leftupleg;
	private final ModelRenderer leftmidleg;
	private final ModelRenderer leftlowleg;
	private final ModelRenderer torso;
	private final ModelRenderer rightshoulder;
	private final ModelRenderer leftshoulder;
	private final ModelRenderer leftfoot;
	private final ModelRenderer righttoe3;
	private final ModelRenderer righttoe2;
	private final ModelRenderer righttoe1;
	private final ModelRenderer lefttoe3;
	private final ModelRenderer lefttoe2;
	private final ModelRenderer lefttoe1;
	private final ModelRenderer head;
	private final ModelRenderer Jaw;
	private final ModelRenderer mouthrod;
	private final ModelRenderer helmetstrap1;
	private final ModelRenderer helmetstrap2;
	private final ModelRenderer leftearskin;
	private final ModelRenderer rightearskin;
	private final ModelRenderer rightfing1a;
	private final ModelRenderer rightfing2a;
	private final ModelRenderer rightfing3a;
	private final ModelRenderer leftfing3a;
	private final ModelRenderer leftfing2a;
	private final ModelRenderer leftfing1a;
	private final ModelRenderer leftlowarm;
	private final ModelRenderer rightlowarm;
	private final ModelRenderer rightuparm;
	private final ModelRenderer leftuparm;
	private final ModelRenderer LeftWing;
	private final ModelRenderer RightWing;
	private final ModelRenderer MainHead;

	public GOTModelWyvern() {
		textureWidth = 128;
		textureHeight = 256;
		Tail = new ModelRenderer(this);
		Tail.setRotationPoint(0.0f, 0.0f, 0.0f);
		back1 = new ModelRenderer(this, 92, 0);
		back1.addBox(-3.0f, -2.0f, -12.0f, 6, 2, 12);
		back1.setRotationPoint(0.0f, 0.0f, 0.0f);
		tail1 = new ModelRenderer(this, 0, 22);
		tail1.addBox(-4.0f, 0.0f, 0.0f, 8, 8, 10);
		tail1.setRotationPoint(0.0f, 0.0f, 0.0f);
		Tail.addChild(tail1);
		ModelRenderer back2 = new ModelRenderer(this, 100, 14);
		back2.addBox(-2.0f, -2.0f, 0.0f, 4, 2, 10);
		back2.setRotationPoint(0.0f, 0.0f, 0.0f);
		tail1.addChild(back2);
		tail2 = new ModelRenderer(this, 0, 40);
		tail2.addBox(-3.0f, 0.0f, 0.0f, 6, 6, 9);
		tail2.setRotationPoint(0.0f, 0.0f, 10.0f);
		tail1.addChild(tail2);
		ModelRenderer back3 = new ModelRenderer(this, 104, 26);
		back3.addBox(-1.5f, -2.0f, 0.0f, 3, 2, 9);
		back3.setRotationPoint(0.0f, 0.0f, 0.0f);
		tail2.addChild(back3);
		tail3 = new ModelRenderer(this, 0, 55);
		tail3.addBox(-2.0f, 0.0f, 0.0f, 4, 5, 8);
		tail3.setRotationPoint(0.0f, 0.0f, 8.0f);
		tail2.addChild(tail3);
		ModelRenderer back4 = new ModelRenderer(this, 108, 37);
		back4.addBox(-1.0f, -2.0f, 0.0f, 2, 2, 8);
		back4.setRotationPoint(0.0f, 0.0f, 0.0f);
		tail3.addChild(back4);
		tail4 = new ModelRenderer(this, 0, 68);
		tail4.addBox(-1.0f, 0.0f, 0.0f, 2, 5, 7);
		tail4.setRotationPoint(0.0f, -1.0f, 7.0f);
		tail3.addChild(tail4);
		tail5 = new ModelRenderer(this, 0, 80);
		tail5.addBox(-0.5f, 0.0f, 0.0f, 1, 3, 7);
		tail5.setRotationPoint(0.0f, 1.0f, 6.0f);
		tail4.addChild(tail5);
		chest = new ModelRenderer(this, 44, 0);
		chest.addBox(-4.5f, 2.7f, -13.0f, 9, 10, 4);
		chest.setRotationPoint(0.0f, 0.0f, 0.0f);
		setRotation(chest, -0.2602503f, 0.0f, 0.0f);
		neckplate3 = new ModelRenderer(this, 112, 64);
		neckplate3.addBox(-2.0f, -2.0f, -2.0f, 4, 2, 4);
		neckplate3.setRotationPoint(0.0f, 0.0f, -12.0f);
		setRotation(neckplate3, -0.669215f, 0.0f, 0.0f);
		neck3 = new ModelRenderer(this, 100, 113);
		neck3.addBox(-3.0f, 0.0f, -2.0f, 6, 7, 8);
		neck3.setRotationPoint(0.0f, 0.0f, -12.0f);
		setRotation(neck3, -0.669215f, 0.0f, 0.0f);
		MainHead = new ModelRenderer(this);
		MainHead.setRotationPoint(0.0f, 3.0f, -15.0f);
		neck2 = new ModelRenderer(this, 102, 99);
		neck2.addBox(-2.5f, -3.0f, -8.0f, 5, 6, 8);
		neck2.setRotationPoint(0.0f, 0.0f, 0.0f);
		MainHead.addChild(neck2);
		ModelRenderer neckplate2 = new ModelRenderer(this, 106, 54);
		neckplate2.addBox(-1.5f, -2.0f, -8.0f, 3, 2, 8);
		neckplate2.setRotationPoint(0.0f, -3.0f, 0.0f);
		neck2.addChild(neckplate2);
		neck1 = new ModelRenderer(this, 104, 85);
		neck1.addBox(-2.0f, -3.0f, -8.0f, 4, 6, 8);
		neck1.setRotationPoint(0.0f, -0.5f, -5.5f);
		neck2.addChild(neck1);
		ModelRenderer neckplate1 = new ModelRenderer(this, 80, 108);
		neckplate1.addBox(-1.0f, -2.0f, -8.0f, 2, 2, 8);
		neckplate1.setRotationPoint(0.0f, -3.0f, 0.0f);
		neck1.addChild(neckplate1);
		head = new ModelRenderer(this, 98, 70);
		head.addBox(-3.5f, -3.5f, -8.0f, 7, 7, 8);
		head.setRotationPoint(0.0f, 0.0f, -7.0f);
		neck1.addChild(head);
		ModelRenderer snout = new ModelRenderer(this, 72, 70);
		snout.addBox(-2.0f, -1.5f, -9.0f, 4, 3, 9);
		snout.setRotationPoint(0.0f, -1.5f, -8.0f);
		setRotation(snout, 2.0f / radianF, 0.0f, 0.0f);
		head.addChild(snout);
		ModelRenderer headplate = new ModelRenderer(this, 80, 118);
		headplate.addBox(-1.0f, -1.0f, -4.0f, 2, 2, 8);
		headplate.setRotationPoint(0.0f, -3.0f, -1.0f);
		setRotation(headplate, 10.0f / radianF, 0.0f, 0.0f);
		head.addChild(headplate);
		ModelRenderer beak = new ModelRenderer(this, 60, 85);
		beak.addBox(-1.5f, -2.5f, -1.5f, 3, 5, 3);
		beak.setRotationPoint(0.0f, 0.8f, -8.0f);
		setRotation(beak, -6.0f / radianF, 45.0f / radianF, -6.0f / radianF);
		snout.addChild(beak);
		ModelRenderer righteyesock = new ModelRenderer(this, 70, 108);
		righteyesock.addBox(0.0f, 0.0f, 0.0f, 1, 2, 4);
		righteyesock.setRotationPoint(-3.5f, -2.5f, -8.0f);
		head.addChild(righteyesock);
		ModelRenderer lefteyesock = new ModelRenderer(this, 70, 114);
		lefteyesock.addBox(0.0f, 0.0f, 0.0f, 1, 2, 4);
		lefteyesock.setRotationPoint(2.5f, -2.5f, -8.0f);
		head.addChild(lefteyesock);
		Jaw = new ModelRenderer(this, 72, 82);
		Jaw.addBox(-2.0f, -1.0f, -9.0f, 4, 2, 9);
		Jaw.setRotationPoint(0.0f, 2.5f, -7.5f);
		setRotation(Jaw, -10.0f / radianF, 0.0f, 0.0f);
		head.addChild(Jaw);
		ModelRenderer leftupjaw = new ModelRenderer(this, 42, 93);
		leftupjaw.addBox(-1.0f, -1.0f, -6.5f, 2, 2, 13);
		leftupjaw.setRotationPoint(2.0f, 0.0f, -10.5f);
		setRotation(leftupjaw, -10.0f / radianF, 10.0f / radianF, 0.0f);
		head.addChild(leftupjaw);
		ModelRenderer rightupjaw = new ModelRenderer(this, 72, 93);
		rightupjaw.addBox(-1.0f, -1.0f, -6.5f, 2, 2, 13);
		rightupjaw.setRotationPoint(-2.0f, 0.0f, -10.5f);
		setRotation(rightupjaw, -10.0f / radianF, -10.0f / radianF, 0.0f);
		head.addChild(rightupjaw);
		mouthrod = new ModelRenderer(this, 104, 50);
		mouthrod.addBox(-5.0f, -1.0f, -1.0f, 10, 2, 2);
		mouthrod.setRotationPoint(0.0f, 1.0f, -8.0f);
		head.addChild(mouthrod);
		helmetstrap1 = new ModelRenderer(this, 32, 146);
		helmetstrap1.addBox(-4.0f, -2.0f, 0.0f, 8, 4, 1);
		helmetstrap1.setRotationPoint(0.0f, 2.0f, -7.5f);
		head.addChild(helmetstrap1);
		helmetstrap2 = new ModelRenderer(this, 32, 141);
		helmetstrap2.addBox(-4.0f, -2.0f, 0.0f, 8, 4, 1);
		helmetstrap2.setRotationPoint(0.0f, 2.0f, -3.5f);
		head.addChild(helmetstrap2);
		ModelRenderer controlrope1 = new ModelRenderer(this, 66, 43);
		controlrope1.addBox(0.0f, -2.0f, 0.0f, 0, 4, 23);
		controlrope1.setRotationPoint(4.5f, 1.0f, 0.0f);
		mouthrod.addChild(controlrope1);
		ModelRenderer controlrope2 = new ModelRenderer(this, 66, 43);
		controlrope2.addBox(0.0f, -2.0f, 0.0f, 0, 4, 23);
		controlrope2.setRotationPoint(-4.5f, 1.0f, 0.0f);
		mouthrod.addChild(controlrope2);
		rightearskin = new ModelRenderer(this, 112, 201);
		rightearskin.addBox(0.0f, -4.0f, 0.0f, 0, 8, 8);
		rightearskin.setRotationPoint(-3.0f, -0.5f, 0.0f);
		head.addChild(rightearskin);
		leftearskin = new ModelRenderer(this, 96, 201);
		leftearskin.addBox(0.0f, -4.0f, 0.0f, 0, 8, 8);
		leftearskin.setRotationPoint(3.0f, -0.5f, 0.0f);
		head.addChild(leftearskin);
		ModelRenderer rightspine1 = new ModelRenderer(this, 50, 141);
		rightspine1.addBox(-0.5f, -1.0f, 0.0f, 1, 2, 8);
		rightspine1.setRotationPoint(0.0f, -2.0f, 0.0f);
		setRotation(rightspine1, 15.0f / radianF, 0.0f, 0.0f);
		rightearskin.addChild(rightspine1);
		ModelRenderer rightspine2 = new ModelRenderer(this, 50, 141);
		rightspine2.addBox(-0.5f, -1.0f, 0.0f, 1, 2, 8);
		rightspine2.setRotationPoint(0.0f, 0.0f, 0.0f);
		rightearskin.addChild(rightspine2);
		ModelRenderer rightspine3 = new ModelRenderer(this, 50, 141);
		rightspine3.addBox(-0.5f, -1.0f, 0.0f, 1, 2, 8);
		rightspine3.setRotationPoint(0.0f, 2.0f, 0.0f);
		setRotation(rightspine3, -15.0f / radianF, 0.0f, 0.0f);
		rightearskin.addChild(rightspine3);
		ModelRenderer leftspine1 = new ModelRenderer(this, 68, 141);
		leftspine1.addBox(-0.5f, -1.0f, 0.0f, 1, 2, 8);
		leftspine1.setRotationPoint(0.0f, -2.0f, 0.0f);
		setRotation(leftspine1, 15.0f / radianF, 0.0f, 0.0f);
		leftearskin.addChild(leftspine1);
		ModelRenderer leftspine2 = new ModelRenderer(this, 68, 141);
		leftspine2.addBox(-0.5f, -1.0f, 0.0f, 1, 2, 8);
		leftspine2.setRotationPoint(0.0f, 0.0f, 0.0f);
		leftearskin.addChild(leftspine2);
		ModelRenderer leftspine3 = new ModelRenderer(this, 68, 141);
		leftspine3.addBox(-0.5f, -1.0f, 0.0f, 1, 2, 8);
		leftspine3.setRotationPoint(0.0f, 2.0f, 0.0f);
		setRotation(leftspine3, -15.0f / radianF, 0.0f, 0.0f);
		leftearskin.addChild(leftspine3);
		torso = new ModelRenderer(this, 0, 0);
		torso.addBox(-5.0f, 0.0f, -12.0f, 10, 10, 12);
		torso.setRotationPoint(0.0f, 0.0f, 0.0f);
		ModelRenderer saddle = new ModelRenderer(this, 38, 70);
		saddle.addBox(-3.5f, -2.5f, -8.0f, 7, 3, 10);
		saddle.setRotationPoint(0.0f, 0.0f, 0.0f);
		rightshoulder = new ModelRenderer(this, 42, 83);
		rightshoulder.addBox(-6.0f, 1.0f, -12.5f, 4, 5, 5);
		rightshoulder.setRotationPoint(0.0f, 0.0f, 0.0f);
		setRotation(rightshoulder, -0.2617994f, 0.0f, 0.0f);
		leftshoulder = new ModelRenderer(this, 24, 83);
		leftshoulder.addBox(2.0f, 1.0f, -12.5f, 4, 5, 5);
		leftshoulder.setRotationPoint(0.0f, 0.0f, 0.0f);
		setRotation(leftshoulder, -0.2617994f, 0.0f, 0.0f);
		LeftWing = new ModelRenderer(this);
		LeftWing.setRotationPoint(4.0f, 1.0f, -11.0f);
		leftuparm = new ModelRenderer(this, 44, 14);
		leftuparm.addBox(0.0f, -2.0f, -2.0f, 10, 4, 4);
		leftuparm.setRotationPoint(0.0f, 0.0f, 0.0f);
		setRotation(leftuparm, 0.0f, -10.0f / radianF, 0.0f);
		LeftWing.addChild(leftuparm);
		leftlowarm = new ModelRenderer(this, 72, 14);
		leftlowarm.addBox(0.0f, -2.0f, -2.0f, 10, 4, 4);
		leftlowarm.setRotationPoint(9.0f, 0.0f, 0.0f);
		setRotation(leftlowarm, 0.0f, 10.0f / radianF, 0.0f);
		leftuparm.addChild(leftlowarm);
		leftfing1a = new ModelRenderer(this, 52, 30);
		leftfing1a.addBox(0.0f, 0.0f, -1.0f, 2, 15, 2);
		leftfing1a.setRotationPoint(9.0f, 1.0f, 0.0f);
		setRotation(leftfing1a, 90.0f / radianF, 70.0f / radianF, 0.0f);
		leftlowarm.addChild(leftfing1a);
		ModelRenderer leftfing1b = new ModelRenderer(this, 52, 47);
		leftfing1b.addBox(0.0f, 0.0f, -1.0f, 2, 10, 2);
		leftfing1b.setRotationPoint(0.0f, 14.0f, 0.0f);
		setRotation(leftfing1b, 0.0f, 0.0f, 35.0f / radianF);
		leftfing1a.addChild(leftfing1b);
		leftfing2a = new ModelRenderer(this, 44, 30);
		leftfing2a.addBox(-1.0f, 0.0f, 0.0f, 2, 15, 2);
		leftfing2a.setRotationPoint(9.0f, 1.0f, 0.0f);
		setRotation(leftfing2a, 90.0f / radianF, 35.0f / radianF, 0.0f);
		leftlowarm.addChild(leftfing2a);
		ModelRenderer leftfing2b = new ModelRenderer(this, 44, 47);
		leftfing2b.addBox(-1.0f, 0.0f, 0.0f, 2, 10, 2);
		leftfing2b.setRotationPoint(0.0f, 14.0f, 0.0f);
		setRotation(leftfing2b, 0.0f, 0.0f, 30.0f / radianF);
		leftfing2a.addChild(leftfing2b);
		leftfing3a = new ModelRenderer(this, 36, 30);
		leftfing3a.addBox(-1.0f, 0.0f, 1.0f, 2, 15, 2);
		leftfing3a.setRotationPoint(9.0f, 1.0f, 0.0f);
		setRotation(leftfing3a, 90.0f / radianF, -5.0f / radianF, 0.0f);
		leftlowarm.addChild(leftfing3a);
		ModelRenderer leftfing3b = new ModelRenderer(this, 36, 47);
		leftfing3b.addBox(-1.0f, 0.0f, 1.0f, 2, 10, 2);
		leftfing3b.setRotationPoint(0.0f, 14.0f, 0.0f);
		setRotation(leftfing3b, 0.0f, 0.0f, 30.0f / radianF);
		leftfing3a.addChild(leftfing3b);
		ModelRenderer leftwingflap1 = new ModelRenderer(this, 74, 153);
		leftwingflap1.addBox(3.5f, -3.0f, 0.95f, 14, 24, 0);
		leftwingflap1.setRotationPoint(0.0f, 0.0f, 0.0f);
		setRotation(leftwingflap1, 0.0f, 0.0f, 70.0f / radianF);
		leftfing1a.addChild(leftwingflap1);
		ModelRenderer leftwingflap2 = new ModelRenderer(this, 36, 153);
		leftwingflap2.addBox(-7.0f, 1.05f, 1.05f, 19, 24, 0);
		leftwingflap2.setRotationPoint(0.0f, 0.0f, 0.0f);
		setRotation(leftwingflap2, 0.0f, 0.0f, 40.0f / radianF);
		leftfing2a.addChild(leftwingflap2);
		ModelRenderer leftwingflap3 = new ModelRenderer(this, 0, 153);
		leftwingflap3.addBox(-17.5f, 1.0f, 1.1f, 18, 24, 0);
		leftwingflap3.setRotationPoint(0.0f, 0.0f, 0.0f);
		leftfing3a.addChild(leftwingflap3);
		RightWing = new ModelRenderer(this);
		RightWing.setRotationPoint(-4.0f, 1.0f, -11.0f);
		rightuparm = new ModelRenderer(this, 44, 22);
		rightuparm.addBox(-10.0f, -2.0f, -2.0f, 10, 4, 4);
		rightuparm.setRotationPoint(0.0f, 0.0f, 0.0f);
		setRotation(rightuparm, 0.0f, 10.0f / radianF, 0.0f);
		RightWing.addChild(rightuparm);
		rightlowarm = new ModelRenderer(this, 72, 22);
		rightlowarm.addBox(-10.0f, -2.0f, -2.0f, 10, 4, 4);
		rightlowarm.setRotationPoint(-9.0f, 0.0f, 0.0f);
		setRotation(rightlowarm, 0.0f, -10.0f / radianF, 0.0f);
		rightuparm.addChild(rightlowarm);
		rightfing1a = new ModelRenderer(this, 36, 30);
		rightfing1a.addBox(-1.0f, 0.0f, -1.0f, 2, 15, 2);
		rightfing1a.setRotationPoint(-9.0f, 1.0f, -1.0f);
		setRotation(rightfing1a, 90.0f / radianF, -70.0f / radianF, 0.0f);
		rightlowarm.addChild(rightfing1a);
		ModelRenderer rightfing1b = new ModelRenderer(this, 36, 47);
		rightfing1b.addBox(-1.0f, 0.0f, -1.0f, 2, 10, 2);
		rightfing1b.setRotationPoint(0.0f, 14.0f, 0.0f);
		setRotation(rightfing1b, 0.0f, 0.0f, -35.0f / radianF);
		rightfing1a.addChild(rightfing1b);
		ModelRenderer rightwingflap1 = new ModelRenderer(this, 74, 177);
		rightwingflap1.addBox(-17.5f, -3.0f, 0.95f, 14, 24, 0);
		rightwingflap1.setRotationPoint(0.0f, 0.0f, 0.0f);
		setRotation(rightwingflap1, 0.0f, 0.0f, -70.0f / radianF);
		rightfing1a.addChild(rightwingflap1);
		rightfing2a = new ModelRenderer(this, 44, 30);
		rightfing2a.addBox(-1.0f, 0.0f, 0.0f, 2, 15, 2);
		rightfing2a.setRotationPoint(-9.0f, 1.0f, 0.0f);
		setRotation(rightfing2a, 90.0f / radianF, -35.0f / radianF, 0.0f);
		rightlowarm.addChild(rightfing2a);
		ModelRenderer rightfing2b = new ModelRenderer(this, 44, 47);
		rightfing2b.addBox(-1.0f, 0.0f, 0.0f, 2, 10, 2);
		rightfing2b.setRotationPoint(0.0f, 14.0f, 0.0f);
		setRotation(rightfing2b, 0.0f, 0.0f, -30.0f / radianF);
		rightfing2a.addChild(rightfing2b);
		ModelRenderer rightwingflap2 = new ModelRenderer(this, 36, 177);
		rightwingflap2.addBox(-19.0f, 1.05f, 1.05f, 19, 24, 0);
		rightwingflap2.setRotationPoint(0.0f, 0.0f, 0.0f);
		setRotation(rightwingflap2, 0.0f, 0.0f, -40.0f / radianF);
		rightfing2a.addChild(rightwingflap2);
		rightfing3a = new ModelRenderer(this, 52, 30);
		rightfing3a.addBox(-1.0f, 0.0f, 1.0f, 2, 15, 2);
		rightfing3a.setRotationPoint(-9.0f, 1.0f, 0.0f);
		setRotation(rightfing3a, 90.0f / radianF, 5.0f / radianF, 0.0f);
		rightlowarm.addChild(rightfing3a);
		ModelRenderer rightfing3b = new ModelRenderer(this, 52, 47);
		rightfing3b.addBox(-1.0f, 0.0f, 1.0f, 2, 10, 2);
		rightfing3b.setRotationPoint(0.0f, 14.0f, 0.0f);
		setRotation(rightfing3b, 0.0f, 0.0f, -30.0f / radianF);
		rightfing3a.addChild(rightfing3b);
		ModelRenderer rightwingflap3 = new ModelRenderer(this, 0, 177);
		rightwingflap3.addBox(-0.5f, 1.0f, 1.1f, 18, 24, 0);
		rightwingflap3.setRotationPoint(0.0f, 0.0f, 0.0f);
		rightfing3a.addChild(rightwingflap3);
		leftupleg = new ModelRenderer(this, 0, 111);
		leftupleg.addBox(-2.0f, -3.0f, -3.0f, 4, 10, 7);
		leftupleg.setRotationPoint(5.0f, 6.0f, -5.0f);
		setRotation(leftupleg, -25.0f / radianF, 0.0f, 0.0f);
		leftmidleg = new ModelRenderer(this, 0, 102);
		leftmidleg.addBox(-1.5f, -2.0f, 0.0f, 3, 4, 5);
		leftmidleg.setRotationPoint(0.0f, 5.0f, 4.0f);
		leftupleg.addChild(leftmidleg);
		leftlowleg = new ModelRenderer(this, 0, 91);
		leftlowleg.addBox(-1.5f, 0.0f, -1.5f, 3, 8, 3);
		leftlowleg.setRotationPoint(0.0f, 2.0f, 3.5f);
		leftmidleg.addChild(leftlowleg);
		leftfoot = new ModelRenderer(this, 44, 121);
		leftfoot.addBox(-2.0f, -1.0f, -3.0f, 4, 3, 4);
		leftfoot.setRotationPoint(0.0f, 7.0f, 0.5f);
		setRotation(leftfoot, 25.0f / radianF, 0.0f, 0.0f);
		leftlowleg.addChild(leftfoot);
		lefttoe1 = new ModelRenderer(this, 96, 35);
		lefttoe1.addBox(-0.5f, -1.0f, -3.0f, 1, 2, 3);
		lefttoe1.setRotationPoint(-1.5f, 1.0f, -3.0f);
		leftfoot.addChild(lefttoe1);
		lefttoe3 = new ModelRenderer(this, 96, 30);
		lefttoe3.addBox(-0.5f, -1.0f, -3.0f, 1, 2, 3);
		lefttoe3.setRotationPoint(1.5f, 1.0f, -3.0f);
		leftfoot.addChild(lefttoe3);
		lefttoe2 = new ModelRenderer(this, 84, 30);
		lefttoe2.addBox(-1.0f, -1.5f, -4.0f, 2, 3, 4);
		lefttoe2.setRotationPoint(0.0f, 0.5f, -3.0f);
		leftfoot.addChild(lefttoe2);
		ModelRenderer leftclaw1 = new ModelRenderer(this, 100, 26);
		leftclaw1.addBox(-0.5f, 0.0f, -0.5f, 1, 2, 1);
		leftclaw1.setRotationPoint(0.5f, -0.5f, -2.5f);
		setRotation(leftclaw1, -25.0f / radianF, 0.0f, 0.0f);
		lefttoe1.addChild(leftclaw1);
		ModelRenderer leftclaw2 = new ModelRenderer(this, 100, 26);
		leftclaw2.addBox(-0.5f, 0.0f, -0.5f, 1, 3, 1);
		leftclaw2.setRotationPoint(0.0f, -1.0f, -3.5f);
		setRotation(leftclaw2, -25.0f / radianF, 0.0f, 0.0f);
		lefttoe2.addChild(leftclaw2);
		ModelRenderer leftclaw3 = new ModelRenderer(this, 100, 26);
		leftclaw3.addBox(-0.5f, 0.0f, -0.5f, 1, 2, 1);
		leftclaw3.setRotationPoint(-0.5f, -0.5f, -2.5f);
		setRotation(leftclaw3, -25.0f / radianF, 0.0f, 0.0f);
		lefttoe3.addChild(leftclaw3);
		rightupleg = new ModelRenderer(this, 0, 111);
		rightupleg.addBox(-2.0f, -3.0f, -3.0f, 4, 10, 7);
		rightupleg.setRotationPoint(-5.0f, 6.0f, -5.0f);
		setRotation(rightupleg, -25.0f / radianF, 0.0f, 0.0f);
		rightmidleg = new ModelRenderer(this, 0, 102);
		rightmidleg.addBox(-1.5f, -2.0f, 0.0f, 3, 4, 5);
		rightmidleg.setRotationPoint(0.0f, 5.0f, 4.0f);
		rightupleg.addChild(rightmidleg);
		rightlowleg = new ModelRenderer(this, 0, 91);
		rightlowleg.addBox(-1.5f, 0.0f, -1.5f, 3, 8, 3);
		rightlowleg.setRotationPoint(0.0f, 2.0f, 3.5f);
		rightmidleg.addChild(rightlowleg);
		rightfoot = new ModelRenderer(this, 44, 121);
		rightfoot.addBox(-2.0f, -1.0f, -3.0f, 4, 3, 4);
		rightfoot.setRotationPoint(0.0f, 7.0f, 0.5f);
		setRotation(rightfoot, 25.0f / radianF, 0.0f, 0.0f);
		rightlowleg.addChild(rightfoot);
		righttoe1 = new ModelRenderer(this, 96, 35);
		righttoe1.addBox(-0.5f, -1.0f, -3.0f, 1, 2, 3);
		righttoe1.setRotationPoint(-1.5f, 1.0f, -3.0f);
		rightfoot.addChild(righttoe1);
		righttoe3 = new ModelRenderer(this, 96, 30);
		righttoe3.addBox(-0.5f, -1.0f, -3.0f, 1, 2, 3);
		righttoe3.setRotationPoint(1.5f, 1.0f, -3.0f);
		rightfoot.addChild(righttoe3);
		righttoe2 = new ModelRenderer(this, 84, 30);
		righttoe2.addBox(-1.0f, -1.5f, -4.0f, 2, 3, 4);
		righttoe2.setRotationPoint(0.0f, 0.5f, -3.0f);
		rightfoot.addChild(righttoe2);
		ModelRenderer rightclaw1 = new ModelRenderer(this, 100, 26);
		rightclaw1.addBox(-0.5f, 0.0f, -0.5f, 1, 2, 1);
		rightclaw1.setRotationPoint(0.5f, -0.5f, -2.5f);
		setRotation(rightclaw1, -25.0f / radianF, 0.0f, 0.0f);
		righttoe1.addChild(rightclaw1);
		ModelRenderer rightclaw2 = new ModelRenderer(this, 100, 26);
		rightclaw2.addBox(-0.5f, 0.0f, -0.5f, 1, 3, 1);
		rightclaw2.setRotationPoint(0.0f, -1.0f, -3.5f);
		setRotation(rightclaw2, -25.0f / radianF, 0.0f, 0.0f);
		righttoe2.addChild(rightclaw2);
		ModelRenderer rightclaw3 = new ModelRenderer(this, 100, 26);
		rightclaw3.addBox(-0.5f, 0.0f, -0.5f, 1, 2, 1);
		rightclaw3.setRotationPoint(-0.5f, -0.5f, -2.5f);
		setRotation(rightclaw3, -25.0f / radianF, 0.0f, 0.0f);
		righttoe3.addChild(rightclaw3);
		ModelRenderer storage = new ModelRenderer(this, 28, 59);
		storage.addBox(-5.0f, -4.5f, 1.5f, 10, 5, 6);
		storage.setRotationPoint(0.0f, 0.0f, 0.0f);
		setRotation(storage, -0.2268928f, 0.0f, 0.0f);
		ModelRenderer chestbelt = new ModelRenderer(this, 0, 201);
		chestbelt.addBox(-5.5f, -0.5f, -9.0f, 11, 11, 2);
		chestbelt.setRotationPoint(0.0f, 0.0f, 0.0f);
		ModelRenderer stomachbelt = new ModelRenderer(this, 0, 201);
		stomachbelt.addBox(-5.5f, -0.5f, -3.0f, 11, 11, 2);
		stomachbelt.setRotationPoint(0.0f, 0.0f, 0.0f);
	}

	private static float realAngle(float origAngle) {
		return origAngle % 360.0f;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5);
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0f, 0.0f, 0.0f);
		back1.render(f5);
		Tail.render(f5);
		chest.render(f5);
		LeftWing.render(f5);
		RightWing.render(f5);
		rightshoulder.render(f5);
		leftshoulder.render(f5);
		neckplate3.render(f5);
		neck3.render(f5);
		torso.render(f5);
		mouthrod.isHidden = true;
		helmetstrap1.isHidden = true;
		helmetstrap2.isHidden = true;
		MainHead.render(f5);
		leftupleg.render(f5);
		rightupleg.render(f5);
		GL11.glPopMatrix();
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	private void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
		float f10;
		float RLegXRot = MathHelper.cos(f * 0.6662f + 3.141593f) * 0.8f * f1;
		float LLegXRot = MathHelper.cos(f * 0.6662f) * 0.8f * f1;
		f3 = realAngle(f3);
		f10 = 60.0f;
		if (f3 > f10) {
			f3 = f10;
		}
		if (f3 < -f10) {
			f3 = -f10;
		}
		neck2.rotateAngleX = -66.0f / radianF + f4 / 3.0f / radianF;
		neck1.rotateAngleX = 30.0f / radianF + f4 * 2.0f / 3.0f / radianF;
		head.rotateAngleX = 45.0f / radianF;
		neck2.rotateAngleY = f3 * 2.0f / 3.0f / radianF;
		neck1.rotateAngleY = f3 / 3.0f / radianF;
		head.rotateAngleY = 0.0f;
		head.rotateAngleZ = 0.0f;
		tail1.rotateAngleX = -19.0f / radianF;
		tail2.rotateAngleX = -16.0f / radianF;
		tail3.rotateAngleX = 7.0f / radianF;
		tail4.rotateAngleX = 11.0f / radianF;
		tail5.rotateAngleX = 8.0f / radianF;
		float t = f / 2.0f;
		float A = 0.15f;
		float w = 0.9f;
		float k = 0.6f;
		int i = 0;
		tail1.rotateAngleY = A * MathHelper.sin(w * t - k * i);
		i++;
		tail2.rotateAngleY = A * MathHelper.sin(w * t - k * i);
		i++;
		tail3.rotateAngleY = A * MathHelper.sin(w * t - k * i);
		i++;
		tail4.rotateAngleY = A * MathHelper.sin(w * t - k * i);
		i++;
		tail5.rotateAngleY = A * MathHelper.sin(w * t - k * i);
		leftlowarm.rotateAngleZ = 0.0f;
		leftfing1a.rotateAngleZ = 0.0f;
		leftfing2a.rotateAngleZ = 0.0f;
		rightlowarm.rotateAngleZ = 0.0f;
		rightfing1a.rotateAngleZ = 0.0f;
		rightfing2a.rotateAngleZ = 0.0f;
		leftuparm.rotateAngleZ = 30.0f / radianF;
		leftuparm.rotateAngleY = -60.0f / radianF + LLegXRot / 5.0f;
		leftlowarm.rotateAngleY = 105.0f / radianF;
		leftfing1a.rotateAngleY = -20.0f / radianF;
		leftfing2a.rotateAngleY = -26.0f / radianF;
		leftfing3a.rotateAngleY = -32.0f / radianF;
		rightuparm.rotateAngleY = 60.0f / radianF - RLegXRot / 5.0f;
		rightuparm.rotateAngleZ = -30.0f / radianF;
		rightlowarm.rotateAngleY = -105.0f / radianF;
		rightfing1a.rotateAngleY = 16.0f / radianF;
		rightfing2a.rotateAngleY = 26.0f / radianF;
		rightfing3a.rotateAngleY = 32.0f / radianF;
		leftupleg.rotateAngleX = -25.0f / radianF + LLegXRot;
		rightupleg.rotateAngleX = -25.0f / radianF + RLegXRot;
		leftmidleg.rotateAngleX = 0.0f;
		leftlowleg.rotateAngleX = 0.0f;
		leftfoot.rotateAngleX = 25.0f / radianF - LLegXRot;
		lefttoe1.rotateAngleX = LLegXRot;
		lefttoe2.rotateAngleX = LLegXRot;
		lefttoe3.rotateAngleX = LLegXRot;
		rightmidleg.rotateAngleX = 0.0f;
		rightlowleg.rotateAngleX = 0.0f;
		rightfoot.rotateAngleX = 25.0f / radianF - RLegXRot;
		righttoe1.rotateAngleX = RLegXRot;
		righttoe2.rotateAngleX = RLegXRot;
		righttoe3.rotateAngleX = RLegXRot;
		Jaw.rotateAngleX = -10.0f / radianF;
		leftearskin.rotateAngleY = 0.0f;
		rightearskin.rotateAngleY = 0.0f;
	}
}
