package got.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class GOTModelGiraffeRug extends ModelBase {
	private static final GOTModelGiraffe GIRAFFE_MODEL = new GOTModelGiraffe(0.0f);

	private static void setRotationAngles() {
		GIRAFFE_MODEL.getLeg1().rotateAngleX = 0.5235987755982988f;
		GIRAFFE_MODEL.getLeg1().rotateAngleZ = 1.5707963267948966f;
		GIRAFFE_MODEL.getLeg2().rotateAngleX = 0.5235987755982988f;
		GIRAFFE_MODEL.getLeg2().rotateAngleZ = -1.5707963267948966f;
		GIRAFFE_MODEL.getLeg3().rotateAngleX = -0.3490658503988659f;
		GIRAFFE_MODEL.getLeg3().rotateAngleZ = 1.5707963267948966f;
		GIRAFFE_MODEL.getLeg4().rotateAngleX = -0.3490658503988659f;
		GIRAFFE_MODEL.getLeg4().rotateAngleZ = -1.5707963267948966f;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		GIRAFFE_MODEL.setRiddenHeadNeckRotation();
		setRotationAngles();
		GL11.glTranslatef(0.0f, 0.1f, 0.0f);
		GL11.glPushMatrix();
		GL11.glScalef(1.5f, 0.4f, 1.0f);
		GIRAFFE_MODEL.getBody().render(f5);
		GIRAFFE_MODEL.getTail().render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0f, 0.6f, -0.2f);
		GIRAFFE_MODEL.getHead().render(f5);
		GIRAFFE_MODEL.getNeck().render(f5);
		GL11.glPopMatrix();
		GL11.glTranslatef(0.0f, 0.0f, 0.0f);
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.25f, 0.0f, 0.0f);
		GIRAFFE_MODEL.getLeg1().render(f5);
		GIRAFFE_MODEL.getLeg3().render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.25f, 0.0f, 0.0f);
		GIRAFFE_MODEL.getLeg2().render(f5);
		GIRAFFE_MODEL.getLeg4().render(f5);
		GL11.glPopMatrix();
	}
}