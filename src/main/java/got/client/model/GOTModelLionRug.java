package got.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class GOTModelLionRug extends ModelBase {
	private static final GOTModelLion LION_MODEL = new GOTModelLion();

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles();
		GL11.glTranslatef(0.0f, -0.4f, 0.0f);
		GL11.glPushMatrix();
		GL11.glScalef(1.5f, 0.4f, 1.0f);
		LION_MODEL.getBody().render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0f, 0.0f, 0.0f);
		LION_MODEL.getTail().render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0f, -0.1f, 0.1f);
		LION_MODEL.getHead().render(f5);
		LION_MODEL.getMane().render(f5);
		GL11.glPopMatrix();
		GL11.glTranslatef(0.0f, 0.15f, 0.0f);
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.4f, 0.0f, 0.0f);
		LION_MODEL.getLeg1().render(f5);
		LION_MODEL.getLeg3().render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.4f, 0.0f, 0.0f);
		LION_MODEL.getLeg2().render(f5);
		LION_MODEL.getLeg4().render(f5);
		GL11.glPopMatrix();
	}

	private void setRotationAngles() {
		LION_MODEL.getLeg1().rotateAngleX = 0.5235987755982988f;
		LION_MODEL.getLeg1().rotateAngleZ = 1.5707963267948966f;
		LION_MODEL.getLeg2().rotateAngleX = 0.5235987755982988f;
		LION_MODEL.getLeg2().rotateAngleZ = -1.5707963267948966f;
		LION_MODEL.getLeg3().rotateAngleX = -0.3490658503988659f;
		LION_MODEL.getLeg3().rotateAngleZ = 1.5707963267948966f;
		LION_MODEL.getLeg4().rotateAngleX = -0.3490658503988659f;
		LION_MODEL.getLeg4().rotateAngleZ = -1.5707963267948966f;
	}
}