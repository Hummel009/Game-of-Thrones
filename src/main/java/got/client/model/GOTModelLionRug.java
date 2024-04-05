package got.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class GOTModelLionRug extends ModelBase {
	private final GOTModelLion lionModel = new GOTModelLion();

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles();
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

	private void setRotationAngles() {
		lionModel.getLeg1().rotateAngleX = 0.5235987755982988f;
		lionModel.getLeg1().rotateAngleZ = 1.5707963267948966f;
		lionModel.getLeg2().rotateAngleX = 0.5235987755982988f;
		lionModel.getLeg2().rotateAngleZ = -1.5707963267948966f;
		lionModel.getLeg3().rotateAngleX = -0.3490658503988659f;
		lionModel.getLeg3().rotateAngleZ = 1.5707963267948966f;
		lionModel.getLeg4().rotateAngleX = -0.3490658503988659f;
		lionModel.getLeg4().rotateAngleZ = -1.5707963267948966f;
	}
}
