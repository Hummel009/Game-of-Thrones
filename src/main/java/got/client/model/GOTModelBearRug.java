package got.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class GOTModelBearRug extends ModelBase {
	public GOTModelBear bearModel = new GOTModelBear();

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles();
		GL11.glTranslatef(0.0f, -0.35f, 0.0f);
		GL11.glPushMatrix();
		GL11.glScalef(1.5f, 0.4f, 1.0f);
		bearModel.body.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0f, -0.4f, 0.1f);
		bearModel.head.render(f5);
		GL11.glPopMatrix();
		GL11.glTranslatef(0.0f, 0.0f, 0.0f);
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.3f, 0.0f, 0.0f);
		bearModel.leg1.render(f5);
		bearModel.leg3.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.3f, 0.0f, 0.0f);
		bearModel.leg2.render(f5);
		bearModel.leg4.render(f5);
		GL11.glPopMatrix();
	}

	public void setRotationAngles() {
		bearModel.leg1.rotateAngleX = (float) Math.toRadians(30.0);
		bearModel.leg1.rotateAngleZ = (float) Math.toRadians(90.0);
		bearModel.leg2.rotateAngleX = (float) Math.toRadians(30.0);
		bearModel.leg2.rotateAngleZ = (float) Math.toRadians(-90.0);
		bearModel.leg3.rotateAngleX = (float) Math.toRadians(-20.0);
		bearModel.leg3.rotateAngleZ = (float) Math.toRadians(90.0);
		bearModel.leg4.rotateAngleX = (float) Math.toRadians(-20.0);
		bearModel.leg4.rotateAngleZ = (float) Math.toRadians(-90.0);
	}
}
