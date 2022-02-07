package got.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public class GOTModelBearRug extends ModelBase {
	private GOTModelBear bearModel = new GOTModelBear();

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles();
		GL11.glTranslatef(0.0f, -0.35f, 0.0f);
		GL11.glPushMatrix();
		GL11.glScalef(1.5f, 0.4f, 1.0f);
		bearModel.getBody().render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0f, -0.4f, 0.1f);
		bearModel.getHead().render(f5);
		GL11.glPopMatrix();
		GL11.glTranslatef(0.0f, 0.0f, 0.0f);
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.3f, 0.0f, 0.0f);
		bearModel.getLeg1().render(f5);
		bearModel.getLeg3().render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.3f, 0.0f, 0.0f);
		bearModel.getLeg2().render(f5);
		bearModel.getLeg4().render(f5);
		GL11.glPopMatrix();
	}

	public void setRotationAngles() {
		bearModel.getLeg1().rotateAngleX = (float) Math.toRadians(30.0);
		bearModel.getLeg1().rotateAngleZ = (float) Math.toRadians(90.0);
		bearModel.getLeg2().rotateAngleX = (float) Math.toRadians(30.0);
		bearModel.getLeg2().rotateAngleZ = (float) Math.toRadians(-90.0);
		bearModel.getLeg3().rotateAngleX = (float) Math.toRadians(-20.0);
		bearModel.getLeg3().rotateAngleZ = (float) Math.toRadians(90.0);
		bearModel.getLeg4().rotateAngleX = (float) Math.toRadians(-20.0);
		bearModel.getLeg4().rotateAngleZ = (float) Math.toRadians(-90.0);
	}
}
