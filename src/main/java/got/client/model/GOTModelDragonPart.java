package got.client.model;

import static org.lwjgl.opengl.GL11.*;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;

public class GOTModelDragonPart extends ModelRenderer {

	public float renderScaleX = 1;
	public float renderScaleY = 1;
	public float renderScaleZ = 1;

	public float preRotateAngleX;
	private float preRotateAngleY;
	private float preRotateAngleZ;

	private ModelBase base;
	private boolean compiled;
	private int displayList;

	private GOTModelDragonPart(ModelBase modelbase, int i, int j) {
		super(modelbase, i, j);
		base = modelbase;
	}

	public GOTModelDragonPart(ModelBase base, String name) {
		super(base, name);
		this.base = base;
	}

	public GOTModelDragonPart addChildBox(String name, float xOfs, float yOfs, float zOfs, int width, int length, int height) {
		GOTModelDragonPart part = new GOTModelDragonPart(base, boxName);
		part.mirror = mirror;
		part.addBox(name, xOfs, yOfs, zOfs, width, length, height);
		addChild(part);

		return part;
	}

	public void compileDisplayList(float scale) {
		displayList = GLAllocation.generateDisplayLists(1);
		glNewList(displayList, GL_COMPILE);
		for (Object obj : cubeList) {
			((ModelBox) obj).render(Tessellator.instance, scale);
		}
		glEndList();
		compiled = true;
	}

	public float getPreRotateAngleX() {
		return preRotateAngleX;
	}

	public float getPreRotateAngleY() {
		return preRotateAngleY;
	}

	public float getPreRotateAngleZ() {
		return preRotateAngleZ;
	}

	public float getRenderScaleX() {
		return renderScaleX;
	}

	public float getRenderScaleY() {
		return renderScaleY;
	}

	public float getRenderScaleZ() {
		return renderScaleZ;
	}

	@Override
	public void postRender(float scale) {

		if (isHidden || !showModel) {
			return;
		}

		glTranslatef(rotationPointX * scale, rotationPointY * scale, rotationPointZ * scale);

		if (getPreRotateAngleZ() != 0) {
			glRotatef(GOTModelDragonAnimaton.toDegrees(getPreRotateAngleZ()), 0, 0, 1);
		}
		if (getPreRotateAngleY() != 0) {
			glRotatef(GOTModelDragonAnimaton.toDegrees(getPreRotateAngleY()), 0, 1, 0);
		}
		if (getPreRotateAngleX() != 0) {
			glRotatef(GOTModelDragonAnimaton.toDegrees(getPreRotateAngleX()), 1, 0, 0);
		}

		if (rotateAngleZ != 0) {
			glRotatef(GOTModelDragonAnimaton.toDegrees(rotateAngleZ), 0, 0, 1);
		}
		if (rotateAngleY != 0) {
			glRotatef(GOTModelDragonAnimaton.toDegrees(rotateAngleY), 0, 1, 0);
		}
		if (rotateAngleX != 0) {
			glRotatef(GOTModelDragonAnimaton.toDegrees(rotateAngleX), 1, 0, 0);
		}

		if (getRenderScaleX() != 0 || getRenderScaleY() != 0 || getRenderScaleZ() != 0) {
			glScalef(getRenderScaleX(), getRenderScaleY(), getRenderScaleZ());
		}
	}

	@Override
	public void render(float scale) {
		renderWithRotation(scale);
	}

	@Override
	public void renderWithRotation(float scale) {

		if (isHidden || !showModel) {
			return;
		}

		if (!compiled) {
			compileDisplayList(scale);
		}

		glPushMatrix();
		postRender(scale);
		glCallList(displayList);

		if (childModels != null) {
			for (Object obj : childModels) {
				((ModelRenderer) obj).render(scale);
			}
		}

		glPopMatrix();
	}

	public GOTModelDragonPart setAngles(float x, float y, float z) {
		rotateAngleX = x;
		rotateAngleY = y;
		rotateAngleZ = z;

		return this;
	}

	public void setPreRotateAngleX(float preRotateAngleX) {
		this.preRotateAngleX = preRotateAngleX;
	}

	public void setPreRotateAngleY(float preRotateAngleY) {
		this.preRotateAngleY = preRotateAngleY;
	}

	public void setPreRotateAngleZ(float preRotateAngleZ) {
		this.preRotateAngleZ = preRotateAngleZ;
	}

	public GOTModelDragonPart setRenderScale(float scale) {
		return setRenderScale(scale, scale, scale);
	}

	private GOTModelDragonPart setRenderScale(float scaleX, float scaleY, float scaleZ) {
		setRenderScaleX(scaleX);
		setRenderScaleY(scaleY);
		setRenderScaleZ(scaleZ);

		return this;
	}

	public void setRenderScaleX(float renderScaleX) {
		this.renderScaleX = renderScaleX;
	}

	public float setRenderScaleY(float renderScaleY) {
		this.renderScaleY = renderScaleY;
		return renderScaleY;
	}

	public void setRenderScaleZ(float renderScaleZ) {
		this.renderScaleZ = renderScaleZ;
	}
}
