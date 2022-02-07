package got.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public class GOTModelGiraffeRug extends ModelBase {
	private GOTModelGiraffe giraffeModel = new GOTModelGiraffe(0.0f);

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		giraffeModel.setRiddenHeadNeckRotation(f, f1, f2, f3, f4, f5);
		this.setRotationAngles();
		GL11.glTranslatef(0.0f, 0.1f, 0.0f);
		GL11.glPushMatrix();
		GL11.glScalef(1.5f, 0.4f, 1.0f);
		giraffeModel.getBody().render(f5);
		giraffeModel.getTail().render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0f, 0.6f, -0.2f);
		giraffeModel.getHead().render(f5);
		giraffeModel.getNeck().render(f5);
		GL11.glPopMatrix();
		GL11.glTranslatef(0.0f, 0.0f, 0.0f);
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.25f, 0.0f, 0.0f);
		giraffeModel.getLeg1().render(f5);
		giraffeModel.getLeg3().render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.25f, 0.0f, 0.0f);
		giraffeModel.getLeg2().render(f5);
		giraffeModel.getLeg4().render(f5);
		GL11.glPopMatrix();
	}

	public void setRotationAngles() {
		giraffeModel.getLeg1().rotateAngleX = (float) Math.toRadians(30.0);
		giraffeModel.getLeg1().rotateAngleZ = (float) Math.toRadians(90.0);
		giraffeModel.getLeg2().rotateAngleX = (float) Math.toRadians(30.0);
		giraffeModel.getLeg2().rotateAngleZ = (float) Math.toRadians(-90.0);
		giraffeModel.getLeg3().rotateAngleX = (float) Math.toRadians(-20.0);
		giraffeModel.getLeg3().rotateAngleZ = (float) Math.toRadians(90.0);
		giraffeModel.getLeg4().rotateAngleX = (float) Math.toRadians(-20.0);
		giraffeModel.getLeg4().rotateAngleZ = (float) Math.toRadians(-90.0);
	}
}
