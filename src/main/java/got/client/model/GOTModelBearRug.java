package got.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class GOTModelBearRug extends ModelBase {
	private final GOTModelBear bearModel = new GOTModelBear();

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles();
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

	private void setRotationAngles() {
		bearModel.getLeg1().rotateAngleX = 0.5235987755982988f;
		bearModel.getLeg1().rotateAngleZ = 1.5707963267948966f;
		bearModel.getLeg2().rotateAngleX = 0.5235987755982988f;
		bearModel.getLeg2().rotateAngleZ = -1.5707963267948966f;
		bearModel.getLeg3().rotateAngleX = -0.3490658503988659f;
		bearModel.getLeg3().rotateAngleZ = 1.5707963267948966f;
		bearModel.getLeg4().rotateAngleX = -0.3490658503988659f;
		bearModel.getLeg4().rotateAngleZ = -1.5707963267948966f;
	}
}
