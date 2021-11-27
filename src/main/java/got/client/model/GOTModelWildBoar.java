/*
 * Decompiled with CFR 0.148.
 *
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.model.ModelRenderer
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBow
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.MathHelper
 */
package got.client.model;

import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.util.MathHelper;

public class GOTModelWildBoar extends ModelBase {
	ModelRenderer Hat2;
	ModelRenderer Hat1;
	ModelRenderer Hair;
	ModelRenderer BHair;
	ModelRenderer RHair;
	ModelRenderer LHair;
	ModelRenderer Head;
	ModelRenderer Neck;
	ModelRenderer RArm;
	ModelRenderer LArm;
	ModelRenderer Body;
	ModelRenderer BustTop;
	ModelRenderer BustFront;
	ModelRenderer BustBottom;
	ModelRenderer Body2;
	ModelRenderer Skirt2;
	ModelRenderer RLeg;
	ModelRenderer LLeg;

	public GOTModelWildBoar() {
		textureWidth = 64;
		textureHeight = 64;
		Hat2 = new ModelRenderer(this, 38, 37);
		Hat2.addBox(-3.5f, -10.0f, -3.0f, 7, 1, 6);
		Hat2.setRotationPoint(0.0f, 0.0f, 0.0f);
		Hat2.setTextureSize(64, 64);
		Hat2.mirror = true;
		setRotation(Hat2, 0.0f, 0.0f, 0.0f);
		Hat1 = new ModelRenderer(this, 0, 26);
		Hat1.addBox(-4.0f, -9.0f, -3.5f, 8, 3, 7);
		Hat1.setRotationPoint(0.0f, 0.0f, 0.0f);
		Hat1.setTextureSize(64, 64);
		Hat1.mirror = true;
		setRotation(Hat1, 0.0f, 0.0f, 0.0f);
		Hair = new ModelRenderer(this, 0, 12);
		Hair.addBox(-4.0f, -6.0f, -3.5f, 8, 7, 7);
		Hair.setRotationPoint(0.0f, 0.0f, 0.0f);
		Hair.setTextureSize(64, 64);
		Hair.mirror = true;
		setRotation(Hair, 0.0f, 0.0f, 0.0f);
		BHair = new ModelRenderer(this, 0, 36);
		BHair.addBox(-4.0f, 0.2f, 2.4f, 8, 10, 1);
		BHair.setRotationPoint(0.0f, 0.0f, 0.0f);
		BHair.setTextureSize(64, 64);
		BHair.mirror = true;
		setRotation(BHair, 0.1745329f, 0.0f, 0.0f);
		RHair = new ModelRenderer(this, 18, 36);
		RHair.addBox(1.0f, 0.0f, 1.3f, 2, 10, 1);
		RHair.setRotationPoint(0.0f, 0.0f, 0.0f);
		RHair.setTextureSize(64, 64);
		RHair.mirror = true;
		setRotation(RHair, 0.1745329f, 0.0f, -0.0698132f);
		LHair = new ModelRenderer(this, 24, 36);
		LHair.addBox(-3.0f, 0.0f, 1.3f, 2, 10, 1);
		LHair.setRotationPoint(0.0f, 0.0f, 0.0f);
		LHair.setTextureSize(64, 64);
		LHair.mirror = true;
		setRotation(LHair, 0.1745329f, 0.0f, 0.0698132f);
		Head = new ModelRenderer(this, 0, 0);
		Head.addBox(-3.5f, -6.0f, -3.0f, 7, 6, 6);
		Head.setRotationPoint(0.0f, 0.0f, 0.0f);
		Head.setTextureSize(64, 64);
		Head.mirror = true;
		setRotation(Head, 0.0f, 0.0f, 0.0f);
		Neck = new ModelRenderer(this, 52, 47);
		Neck.addBox(-1.5f, -1.0f, -1.5f, 3, 2, 3);
		Neck.setRotationPoint(0.0f, 0.0f, 0.0f);
		Neck.setTextureSize(64, 64);
		Neck.mirror = true;
		setRotation(Neck, 0.0f, 0.0f, 0.0f);
		RArm = new ModelRenderer(this, 36, 0);
		RArm.addBox(-2.0f, -1.0f, -1.0f, 2, 12, 2);
		RArm.setRotationPoint(-3.5f, 2.0f, 0.0f);
		RArm.setTextureSize(64, 64);
		RArm.mirror = true;
		setRotation(RArm, 0.0f, 0.0f, 0.0f);
		LArm = new ModelRenderer(this, 44, 0);
		LArm.addBox(0.0f, -1.0f, -1.0f, 2, 12, 2);
		LArm.setRotationPoint(3.5f, 2.0f, 0.0f);
		LArm.setTextureSize(64, 64);
		LArm.mirror = true;
		setRotation(LArm, 0.0f, 0.0f, 0.0f);
		Body = new ModelRenderer(this, 0, 47);
		Body.addBox(-3.5f, 0.0f, -1.5f, 7, 5, 3);
		Body.setRotationPoint(0.0f, 1.0f, 0.0f);
		Body.setTextureSize(64, 64);
		Body.mirror = true;
		setRotation(Body, 0.0f, 0.0f, 0.0f);
		BustTop = new ModelRenderer(this, 48, 52);
		BustTop.addBox(-3.0f, 0.0f, 0.0f, 6, 2, 2);
		BustTop.setRotationPoint(0.0f, 1.9f, -1.5f);
		BustTop.setTextureSize(64, 64);
		BustTop.mirror = true;
		setRotation(BustTop, -0.7853982f, 0.0f, 0.0f);
		BustFront = new ModelRenderer(this, 48, 56);
		BustFront.addBox(-3.0f, 3.3f, -2.9f, 6, 2, 2);
		BustFront.setRotationPoint(0.0f, 0.0f, 0.0f);
		BustFront.setTextureSize(64, 64);
		BustFront.mirror = true;
		setRotation(BustFront, 0.0f, 0.0f, 0.0f);
		BustBottom = new ModelRenderer(this, 48, 60);
		BustBottom.addBox(-3.0f, 0.0f, 0.0f, 6, 2, 2);
		BustBottom.setRotationPoint(0.0f, 3.5f, -2.0f);
		BustBottom.setTextureSize(64, 64);
		BustBottom.mirror = true;
		setRotation(BustBottom, -0.4363323f, 0.0f, 0.0f);
		Body2 = new ModelRenderer(this, 0, 55);
		Body2.addBox(-3.0f, 6.0f, -1.5f, 6, 6, 3);
		Body2.setRotationPoint(0.0f, 0.0f, 0.0f);
		Body2.setTextureSize(64, 64);
		Body2.mirror = true;
		setRotation(Body2, 0.0f, 0.0f, 0.0f);
		Skirt2 = new ModelRenderer(this, 42, 30);
		Skirt2.addBox(-3.5f, 0.0f, -2.0f, 7, 3, 4);
		Skirt2.setRotationPoint(0.0f, 10.0f, 0.0f);
		Skirt2.setTextureSize(64, 64);
		Skirt2.mirror = true;
		setRotation(Skirt2, 0.0f, 0.0f, 0.0f);
		RLeg = new ModelRenderer(this, 52, 0);
		RLeg.addBox(-1.5f, -1.0f, -1.5f, 3, 11, 3);
		RLeg.setRotationPoint(-2.0f, 14.0f, 0.0f);
		RLeg.setTextureSize(64, 64);
		RLeg.mirror = true;
		setRotation(RLeg, 0.0f, 0.0f, 0.0f);
		LLeg = new ModelRenderer(this, 52, 14);
		LLeg.addBox(-1.5f, -1.0f, -1.5f, 3, 11, 3);
		LLeg.setRotationPoint(2.0f, 14.0f, 0.0f);
		LLeg.setTextureSize(64, 64);
		LLeg.mirror = true;
		setRotation(LLeg, 0.0f, 0.0f, 0.0f);
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		super.render(par1Entity, par2, par3, par4, par5, par6, par7);
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		Hat2.render(par7);
		Hat1.render(par7);
		Hair.render(par7);
		BHair.render(par7);
		RHair.render(par7);
		LHair.render(par7);
		Head.render(par7);
		Neck.render(par7);
		RArm.render(par7);
		LArm.render(par7);
		Body.render(par7);
		BustTop.render(par7);
		BustFront.render(par7);
		BustBottom.render(par7);
		Body2.render(par7);
		Skirt2.render(par7);
		RLeg.render(par7);
		LLeg.render(par7);
	}

	@Override
	public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4) {
		par1EntityLivingBase.getHeldItem();
		super.setLivingAnimations(par1EntityLivingBase, par2, par3, par4);
	}

	public void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
		Head.rotateAngleY = par4 / 57.295776f;
		Head.rotateAngleX = par5 / 57.295776f;
		Hat2.rotateAngleY = Head.rotateAngleY;
		Hat2.rotateAngleX = Head.rotateAngleX;
		Hat1.rotateAngleY = Head.rotateAngleY;
		Hat1.rotateAngleX = Head.rotateAngleX;
		Hair.rotateAngleY = Head.rotateAngleY;
		Hair.rotateAngleX = Head.rotateAngleX;
		RHair.rotateAngleY = Head.rotateAngleY;
		RHair.rotateAngleX = 0.1745329f + Head.rotateAngleX;
		RHair.rotateAngleZ = -0.0698132f;
		LHair.rotateAngleY = Head.rotateAngleY;
		LHair.rotateAngleX = 0.1745329f + Head.rotateAngleX;
		LHair.rotateAngleZ = 0.0698132f;
		BHair.rotateAngleY = Head.rotateAngleY / 1.25f;
		BHair.rotateAngleX = 0.1745329f;
		Neck.rotateAngleY = Head.rotateAngleY / 2.0f;
		Neck.rotateAngleY = Head.rotateAngleY / 2.0f;
		RLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662f) * 1.4f * par2;
		LLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662f + 3.1415927f) * 1.4f * par2;
		RLeg.rotateAngleY = 0.0f;
		LLeg.rotateAngleY = 0.0f;
		LArm.rotateAngleX = MathHelper.cos(par1 * 0.6662f) * 0.5f * par2;
		LArm.rotateAngleY = 0.0f;
		LArm.rotateAngleZ = -0.1f;
		LArm.rotateAngleZ += MathHelper.cos(par3 * 0.09f) * 0.05f + 0.05f;
		RArm.rotateAngleX = MathHelper.cos(par1 * 0.6662f + 3.1415927f) * 0.5f * par2;
		RArm.rotateAngleY = 0.0f;
		RArm.rotateAngleZ = 0.1f;
		RArm.rotateAngleZ -= MathHelper.cos(par3 * 0.09f) * 0.05f + 0.05f;
	}
}
