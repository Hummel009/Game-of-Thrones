package got.common.entity.animal;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityManticore extends GOTEntityScorpionSmall {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityManticore(World world) {
		super(world);
	}

	@Override
	public void onDeath(DamageSource damagesource) {
		super.onDeath(damagesource);
		if (!worldObj.isRemote && damagesource.getEntity() instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) damagesource.getEntity();
			GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.killManticore);
		}
	}
}