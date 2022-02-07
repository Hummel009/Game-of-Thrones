package got.client.model;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;

public class GOTModelWeaponRack extends ModelBase {
	private ModelRenderer base = new ModelRenderer(this, 0, 0);
	private ModelRenderer stand;
	private ModelRenderer holder;
	private ModelRenderer holderUpperParts;
	private boolean onWall = false;

	public GOTModelWeaponRack() {
		base.setRotationPoint(0.0f, 24.0f, 0.0f);
		base.addBox(-7.0f, -2.0f, -3.0f, 14, 2, 6);
		stand = new ModelRenderer(this, 34, 0);
		stand.setRotationPoint(0.0f, -2.0f, 0.0f);
		stand.addBox(-4.0f, -4.0f, -0.5f, 8, 1, 1);
		stand.setTextureOffset(52, 0);
		stand.addBox(-6.0f, -6.0f, -1.0f, 2, 6, 2);
		stand.mirror = true;
		stand.addBox(4.0f, -6.0f, -1.0f, 2, 6, 2);
		holder = new ModelRenderer(this, 0, 8);
		holder.setRotationPoint(0.0f, -8.0f, 0.0f);
		holder.addBox(-7.0f, -1.0f, -2.0f, 14, 1, 4);
		holder.setTextureOffset(6, 13);
		holder.addBox(-6.0f, -2.0f, -1.5f, 2, 1, 3);
		holder.mirror = true;
		holder.addBox(4.0f, -2.0f, -1.5f, 2, 1, 3);
		holder.mirror = false;
		holder.setTextureOffset(0, 13);
		holder.addBox(-6.0f, -3.0f, 0.5f, 2, 1, 1);
		holder.mirror = true;
		holder.addBox(4.0f, -3.0f, 0.5f, 2, 1, 1);
		holderUpperParts = new ModelRenderer(this, 0, 13);
		holderUpperParts.setRotationPoint(0.0f, 0.0f, 0.0f);
		holderUpperParts.addBox(-6.0f, -3.0f, -1.5f, 2, 1, 1);
		holderUpperParts.mirror = true;
		holderUpperParts.addBox(4.0f, -3.0f, -1.5f, 2, 1, 1);
		base.addChild(stand);
		base.addChild(holder);
		holder.addChild(holderUpperParts);
	}

	public boolean isOnWall() {
		return onWall;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		if (isOnWall()) {
			base.rotateAngleX = (float) Math.toRadians(-90.0);
			stand.isHidden = true;
			holder.rotateAngleX = 0.0f;
			holder.setRotationPoint(0.0f, -2.0f, 0.0f);
			holderUpperParts.showModel = false;
		} else {
			base.rotateAngleX = 0.0f;
			stand.isHidden = false;
			holder.rotateAngleX = 0.0f;
			holder.setRotationPoint(0.0f, -8.0f, 0.0f);
			holderUpperParts.showModel = true;
		}
		base.render(f5);
	}

	public void setOnWall(boolean onWall) {
		this.onWall = onWall;
	}
}
