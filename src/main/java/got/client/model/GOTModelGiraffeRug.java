package got.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class GOTModelGiraffeRug extends ModelBase {
	private final GOTModelGiraffe giraffeModel = new GOTModelGiraffe(0.0f);

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		giraffeModel.setRiddenHeadNeckRotation();
		setRotationAngles();
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

	private void setRotationAngles() {
		giraffeModel.getLeg1().rotateAngleX = 0.5235987755982988f;
		giraffeModel.getLeg1().rotateAngleZ = 1.5707963267948966f;
		giraffeModel.getLeg2().rotateAngleX = 0.5235987755982988f;
		giraffeModel.getLeg2().rotateAngleZ = -1.5707963267948966f;
		giraffeModel.getLeg3().rotateAngleX = -0.3490658503988659f;
		giraffeModel.getLeg3().rotateAngleZ = 1.5707963267948966f;
		giraffeModel.getLeg4().rotateAngleX = -0.3490658503988659f;
		giraffeModel.getLeg4().rotateAngleZ = -1.5707963267948966f;
	}
}
