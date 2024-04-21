package got.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class GOTModelBearRug extends ModelBase {
	private static final GOTModelBear BEAR_MODEL = new GOTModelBear();

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles();
		GL11.glTranslatef(0.0f, -0.35f, 0.0f);
		GL11.glPushMatrix();
		GL11.glScalef(1.5f, 0.4f, 1.0f);
		BEAR_MODEL.getBody().render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0f, -0.4f, 0.1f);
		BEAR_MODEL.getHead().render(f5);
		GL11.glPopMatrix();
		GL11.glTranslatef(0.0f, 0.0f, 0.0f);
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.3f, 0.0f, 0.0f);
		BEAR_MODEL.getLeg1().render(f5);
		BEAR_MODEL.getLeg3().render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.3f, 0.0f, 0.0f);
		BEAR_MODEL.getLeg2().render(f5);
		BEAR_MODEL.getLeg4().render(f5);
		GL11.glPopMatrix();
	}

	private static void setRotationAngles() {
		BEAR_MODEL.getLeg1().rotateAngleX = 0.5235987755982988f;
		BEAR_MODEL.getLeg1().rotateAngleZ = 1.5707963267948966f;
		BEAR_MODEL.getLeg2().rotateAngleX = 0.5235987755982988f;
		BEAR_MODEL.getLeg2().rotateAngleZ = -1.5707963267948966f;
		BEAR_MODEL.getLeg3().rotateAngleX = -0.3490658503988659f;
		BEAR_MODEL.getLeg3().rotateAngleZ = 1.5707963267948966f;
		BEAR_MODEL.getLeg4().rotateAngleX = -0.3490658503988659f;
		BEAR_MODEL.getLeg4().rotateAngleZ = -1.5707963267948966f;
	}
}