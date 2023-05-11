package got.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;

import static org.lwjgl.opengl.GL11.*;

public class GOTModelDragonPart extends ModelRenderer {

	public static boolean renderAxes;

	public float renderScaleX = 1;
	public float renderScaleY = 1;
	public float renderScaleZ = 1;

	public float preRotateAngleX;
	public float preRotateAngleY;
	public float preRotateAngleZ;

	public ModelBase base;
	public boolean compiled;
	public int displayList;

	public GOTModelDragonPart(ModelBase base) {
		this(base, null);
	}

	public GOTModelDragonPart(ModelBase modelbase, int i, int j) {
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

	@Override
	public void postRender(float scale) {

		if (isHidden || !showModel) {
			return;
		}

		glTranslatef(rotationPointX * scale, rotationPointY * scale, rotationPointZ * scale);

		if (preRotateAngleZ != 0) {
			glRotatef(GOTModelDragonAnimaton.toDegrees(preRotateAngleZ), 0, 0, 1);
		}
		if (preRotateAngleY != 0) {
			glRotatef(GOTModelDragonAnimaton.toDegrees(preRotateAngleY), 0, 1, 0);
		}
		if (preRotateAngleX != 0) {
			glRotatef(GOTModelDragonAnimaton.toDegrees(preRotateAngleX), 1, 0, 0);
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

		if (renderScaleX != 0 || renderScaleY != 0 || renderScaleZ != 0) {
			glScalef(renderScaleX, renderScaleY, renderScaleZ);
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

		if (renderAxes) {
//            GLUtils.renderAxes(scale * 8);
		}

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

	public void setRenderScale(float scale) {
		setRenderScale(scale, scale, scale);
	}

	public void setRenderScale(float scaleX, float scaleY, float scaleZ) {
		renderScaleX = scaleX;
		renderScaleY = scaleY;
		renderScaleZ = scaleZ;

	}
}
