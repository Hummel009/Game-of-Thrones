package io.gitlab.dwarfyassassin.gotucp.core.hooks;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.ReflectionHelper;
import got.common.GOTBannerProtection;
import got.common.entity.other.GOTEntityBanner;
import got.common.util.GOTReflection;
import io.gitlab.dwarfyassassin.gotucp.core.UCPCoreMod;
import io.gitlab.dwarfyassassin.gotucp.server.util.PlayerUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

public class ThaumcraftHooks {
	private static boolean doneReflection = false;
	private static Class<? extends Entity> class_golem;
	private static Method method_getOwnerName;

	public static GOTBannerProtection.ProtectType thaumcraftGolemBannerProtection(EntityPlayer player, GOTEntityBanner banner) {
		GameProfile profile;
		FakePlayer fakePlayer;
		World world = player.worldObj;
		if (player instanceof FakePlayer && "FakeThaumcraftGolem".equals((profile = (fakePlayer = (FakePlayer) player).getGameProfile()).getName())) {
			if (!doneReflection) {
				try {
					class_golem = (Class<? extends Entity>) Class.forName("thaumcraft.common.entities.golems.EntityGolemBase");
					method_getOwnerName = class_golem.getDeclaredMethod("getOwnerName");
				} catch (ClassNotFoundException e) {
					UCPCoreMod.log.error("Was unable to find Thaumcraft EntityGolemBase class");
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					UCPCoreMod.log.error("Was unable to find Thaumcraft EntityGolemBase.getOwnerName method");
					e.printStackTrace();
				}
				doneReflection = true;
			}
			if (class_golem == null || method_getOwnerName == null) {
				UCPCoreMod.log.error("Failed to reflectively locate Thaumcraft EntityGolemBase class or getOwnerName method.Found class = %s, found method = %s", class_golem, method_getOwnerName);
				return null;
			}
			List<Entity> foundGolems = world.getEntitiesWithinAABB(class_golem, player.boundingBox.expand(1.0, 1.0, 1.0));
			Entity closestGolem = null;
			double foundDistance = Double.MAX_VALUE;
			for (Entity golem : foundGolems) {
				double distance = player.getDistanceSqToEntity(golem);
				if (distance >= foundDistance) {
					closestGolem = golem;
				}
			}
			if (closestGolem == null) {
				return null;
			}
			UUID uuid = null;
			try {
				String golemOwner = (String) method_getOwnerName.invoke(closestGolem);
				uuid = PlayerUtils.getLastKownUUIDFromUsername(golemOwner);
			} catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
				UCPCoreMod.log.error("Was unable to invoke Thaumcraft EntityGolemBase.getOwnerName method");
				e.printStackTrace();
			}
			if (uuid == null) {
				UCPCoreMod.log.error("Was unable to find the player UUID from Thaumcraft EntityGolemBase.getOwnerName");
				return null;
			}
			try {
				GOTReflection.setFinalField(GameProfile.class, profile, uuid, "id");
				ReflectionHelper.setPrivateValue(Entity.class, fakePlayer, uuid, "entityUniqueID", "field_96093_i");
			} catch (Exception e) {
				UCPCoreMod.log.error("Was unable to set a FakeThaumcraftGolem player uuid to {}", uuid);
				e.printStackTrace();
			}
		}
		return null;
	}
}
