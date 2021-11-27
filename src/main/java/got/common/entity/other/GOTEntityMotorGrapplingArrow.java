package got.common.entity.other;

import got.common.util.GOTGrappleHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class GOTEntityMotorGrapplingArrow extends GOTEntityGrapplingArrow {
	public GOTEntityMotorGrapplingArrow(World worldIn) {
		super(worldIn);
	}

	public GOTEntityMotorGrapplingArrow(World worldIn, EntityLivingBase shooter, boolean righthand) {
		super(worldIn, shooter, righthand);
	}

	@Override
	public float func_70182_d() {
		return 20F;
	}

	@Override
	public int getControlId() {
		return GOTGrappleHelper.HOOKID;
	}
}
