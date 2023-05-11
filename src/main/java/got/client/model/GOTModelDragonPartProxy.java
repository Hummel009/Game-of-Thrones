package got.client.model;

import java.util.ArrayList;
import java.util.List;

public class GOTModelDragonPartProxy {

	public float renderScaleX = 1;
	public float renderScaleY = 1;
	public float renderScaleZ = 1;

	public float rotationPointX;
	public float rotationPointY;
	public float rotationPointZ;

	public float preRotateAngleX;
	public float preRotateAngleY;
	public float preRotateAngleZ;

	public float rotateAngleX;
	public float rotateAngleY;
	public float rotateAngleZ;

	public boolean hidden;
	public boolean showModel;

	public GOTModelDragonPart part;
	public List<GOTModelDragonPartProxy> childs;

	public GOTModelDragonPartProxy(GOTModelDragonPart part) {
		this.part = part;

		if (part.childModels != null) {
			childs = new ArrayList<>();
			for (Object childModel : part.childModels) {
				childs.add(new GOTModelDragonPartProxy((GOTModelDragonPart) childModel));
			}
		} else {
			childs = null;
		}

		update();
	}

	public void apply() {
		part.renderScaleX = renderScaleX;
		part.renderScaleY = renderScaleY;
		part.renderScaleZ = renderScaleZ;

		part.rotationPointX = rotationPointX;
		part.rotationPointY = rotationPointY;
		part.rotationPointZ = rotationPointZ;

		part.preRotateAngleX = preRotateAngleX;
		part.preRotateAngleY = preRotateAngleY;
		part.preRotateAngleZ = preRotateAngleZ;

		part.rotateAngleX = rotateAngleX;
		part.rotateAngleY = rotateAngleY;
		part.rotateAngleZ = rotateAngleZ;

		part.isHidden = hidden;
		part.showModel = showModel;

		if (childs != null) {
			for (GOTModelDragonPartProxy child : childs) {
				child.apply();
			}
		}
	}

	public void render(float scale) {
		apply();
		part.render(scale);
	}

	public void update() {
		renderScaleX = part.renderScaleX;
		renderScaleY = part.renderScaleY;
		renderScaleZ = part.renderScaleZ;

		rotationPointX = part.rotationPointX;
		rotationPointY = part.rotationPointY;
		rotationPointZ = part.rotationPointZ;

		preRotateAngleX = part.preRotateAngleX;
		preRotateAngleY = part.preRotateAngleY;
		preRotateAngleZ = part.preRotateAngleZ;

		rotateAngleX = part.rotateAngleX;
		rotateAngleY = part.rotateAngleY;
		rotateAngleZ = part.rotateAngleZ;

		hidden = part.isHidden;
		showModel = part.showModel;

		if (childs != null) {
			for (GOTModelDragonPartProxy child : childs) {
				child.update();
			}
		}
	}
}
