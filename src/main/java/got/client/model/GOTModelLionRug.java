package got.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public class GOTModelLionRug extends ModelBase {
	private GOTModelLion lionModel = new GOTModelLion();

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles();
		GL11.glTranslatef(0.0f, -0.4f, 0.0f);
		GL11.glPushMatrix();
		GL11.glScalef(1.5f, 0.4f, 1.0f);
		lionModel.getBody().render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0f, 0.0f, 0.0f);
		lionModel.getTail().render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0f, -0.1f, 0.1f);
		lionModel.getHead().render(f5);
		lionModel.getMane().render(f5);
		GL11.glPopMatrix();
		GL11.glTranslatef(0.0f, 0.15f, 0.0f);
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.4f, 0.0f, 0.0f);
		lionModel.getLeg1().render(f5);
		lionModel.getLeg3().render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.4f, 0.0f, 0.0f);
		lionModel.getLeg2().render(f5);
		lionModel.getLeg4().render(f5);
		GL11.glPopMatrix();
	}

	public void setRotationAngles() {
		lionModel.getLeg1().rotateAngleX = (float) Math.toRadians(30.0);
		lionModel.getLeg1().rotateAngleZ = (float) Math.toRadians(90.0);
		lionModel.getLeg2().rotateAngleX = (float) Math.toRadians(30.0);
		lionModel.getLeg2().rotateAngleZ = (float) Math.toRadians(-90.0);
		lionModel.getLeg3().rotateAngleX = (float) Math.toRadians(-20.0);
		lionModel.getLeg3().rotateAngleZ = (float) Math.toRadians(90.0);
		lionModel.getLeg4().rotateAngleX = (float) Math.toRadians(-20.0);
		lionModel.getLeg4().rotateAngleZ = (float) Math.toRadians(-90.0);
	}
}
