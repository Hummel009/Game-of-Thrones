package got.client.sound;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import got.GOT;
import got.client.GOTTickHandlerClient;
import got.client.render.other.GOTRenderWeather;
import got.common.GOTConfig;
import got.common.world.GOTWorldProvider;
import got.common.world.biome.essos.GOTBiomeMossovyMarshes;
import got.common.world.biome.essos.GOTBiomeShadowLand;
import got.common.world.biome.essos.GOTBiomeValyria;
import got.common.world.biome.other.GOTBiomeBeach;
import got.common.world.biome.other.GOTBiomeOcean;
import got.common.world.biome.sothoryos.GOTBiomeYeen;
import got.common.world.biome.westeros.GOTBiomeHauntedForest;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class GOTAmbience {
	public int ticksSinceWight;
	public List<ISound> playingWindSounds = new ArrayList<>();
	public List<ISound> playingSeaSounds = new ArrayList<>();

	public GOTAmbience() {
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onPlaySound(PlaySoundEvent17 event) {
		String name = event.name;
		ISound sound = event.sound;
		if (sound instanceof PositionedSound) {
			PositionedSound ps = (PositionedSound) sound;
			WorldClient world = Minecraft.getMinecraft().theWorld;
			if (world != null && world.provider instanceof GOTWorldProvider) {
				if ("ambient.weather.rain".equals(name)) {
					event.result = new PositionedSoundRecord(new ResourceLocation("got:ambient.weather.rain"), ps.getVolume(), ps.getPitch(), ps.getXPosF(), ps.getYPosF(), ps.getZPosF());
				} else if ("ambient.weather.thunder".equals(name)) {
					event.result = new PositionedSoundRecord(new ResourceLocation("got:ambient.weather.thunder"), ps.getVolume(), ps.getPitch(), ps.getXPosF(), ps.getYPosF(), ps.getZPosF());
				}
			}
		}
	}

	public void updateAmbience(World world, EntityPlayer entityplayer) {
		Minecraft mc;
		block42:
		{
			int xzRange;
			world.theProfiler.startSection("gotAmbience");
			mc = Minecraft.getMinecraft();
			boolean enableAmbience = GOTConfig.enableAmbience;
			double x = entityplayer.posX;
			double y = entityplayer.boundingBox.minY;
			double z = entityplayer.posZ;
			int i = MathHelper.floor_double(x);
			int j = MathHelper.floor_double(y);
			int k = MathHelper.floor_double(z);
			BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
			Random rand = world.rand;
			if (enableAmbience) {
				if (ticksSinceWight > 0) {
					--ticksSinceWight;
				} else if (GOTTickHandlerClient.anyWightsViewed && rand.nextInt(20) == 0) {
					world.playSound(x, y, z, "got:wight.ambience", 1.0f, 0.8f + rand.nextFloat() * 0.4f, false);
					ticksSinceWight = 300;
				}
				boolean spookyBiomeNoise = false;
				float spookyPitch = 1.0f;
				if (biome instanceof GOTBiomeYeen) {
					spookyBiomeNoise = rand.nextInt(1000) == 0;
					spookyPitch = 0.85f;
				} else if (biome instanceof GOTBiomeMossovyMarshes) {
					spookyBiomeNoise = rand.nextInt(2400) == 0;
				} else if (biome instanceof GOTBiomeValyria) {
					spookyBiomeNoise = rand.nextInt(3000) == 0;
				} else if (biome instanceof GOTBiomeHauntedForest) {
					spookyBiomeNoise = rand.nextInt(6000) == 0;
				} else if (biome instanceof GOTBiomeShadowLand) {
					spookyBiomeNoise = rand.nextInt(1000) == 0;
					spookyPitch = 0.75f;
				}
				if (spookyBiomeNoise) {
					world.playSound(x, y, z, "got:wight.ambience", 1.0f, (0.8f + rand.nextFloat() * 0.4f) * spookyPitch, false);
				}
				if (biome instanceof GOTBiomeYeen && world.rand.nextInt(500) == 0) {
					world.playSound(x, y, z, "ambient.cave.cave", 1.0f, 0.8f + rand.nextFloat() * 0.2f, false);
				}
			}
			if (world.provider instanceof GOTWorldProvider) {
				if (playingWindSounds.size() < 4) {
					xzRange = 16;
					int minWindHeight = 100;
					int fullWindHeight = 180;
					if (rand.nextInt(20) == 0) {
						minWindHeight -= 10;
						if (rand.nextInt(10) == 0) {
							minWindHeight -= 10;
						}
					}
					if (world.isRaining()) {
						minWindHeight = 80;
						fullWindHeight = 120;
						if (rand.nextInt(20) == 0) {
							minWindHeight -= 20;
						}
						if (GOTRenderWeather.isSandstormBiome(biome)) {
							minWindHeight = 60;
							fullWindHeight = 80;
						}
					}
					for (int l = 0; l < 2; ++l) {
						int i1 = i + MathHelper.getRandomIntegerInRange(rand, -xzRange, xzRange);
						int k1 = k + MathHelper.getRandomIntegerInRange(rand, -xzRange, xzRange);
						int j1 = j + MathHelper.getRandomIntegerInRange(rand, -16, 16);
						if (j1 >= minWindHeight && world.canBlockSeeTheSky(i1, j1, k1)) {
							float windiness = (float) (j1 - minWindHeight) / (fullWindHeight - minWindHeight);
							windiness = MathHelper.clamp_float(windiness, 0.0f, 1.0f);
							if (windiness >= rand.nextFloat()) {
								float x1 = i1 + 0.5f;
								float y1 = j1 + 0.5f;
								float z1 = k1 + 0.5f;
								float vol = Math.max(0.25f, windiness);
								float pitch = 0.8f + rand.nextFloat() * 0.4f;
								AmbientSoundNoAttentuation wind = new AmbientSoundNoAttentuation(new ResourceLocation("got:ambient.weather.wind"), vol, pitch, x1, y1, z1).calcAmbientVolume(entityplayer, xzRange);
								mc.getSoundHandler().playSound(wind);
								playingWindSounds.add(wind);
								break;
							}
						}
					}
				} else {
					HashSet<ISound> removes = new HashSet<>();
					for (ISound wind : playingWindSounds) {
						if (!mc.getSoundHandler().isSoundPlaying(wind)) {
							removes.add(wind);
						}
					}
					playingWindSounds.removeAll(removes);
				}
			}
			if (enableAmbience) {
				if (playingSeaSounds.size() < 3) {
					if (biome instanceof GOTBiomeOcean) {
						xzRange = 64;
						for (float fr : new float[]{0.25f, 0.5f, 0.75f, 1.0f}) {
							int range = (int) (xzRange * fr);
							for (int l = 0; l < 8; ++l) {
								int i1 = i + MathHelper.getRandomIntegerInRange(rand, -range, range);
								int k1 = k + MathHelper.getRandomIntegerInRange(rand, -range, range);
								int j1 = j + MathHelper.getRandomIntegerInRange(rand, -16, 8);
								Block block = world.getBlock(i1, j1, k1);
								if (block.getMaterial() == Material.water && j1 >= world.getTopSolidOrLiquidBlock(i1, k1)) {
									float x1 = i1 + 0.5f;
									float y1 = j1 + 0.5f;
									float z1 = k1 + 0.5f;
									float vol = 1.0f;
									float pitch = 0.8f + rand.nextFloat() * 0.4f;
									AmbientSoundNoAttentuation sea = new AmbientSoundNoAttentuation(new ResourceLocation("got:ambient.terrain.sea"), vol, pitch, x1, y1, z1).calcAmbientVolume(entityplayer, xzRange);
									mc.getSoundHandler().playSound(sea);
									playingSeaSounds.add(sea);
									int j2 = world.getHeightValue(i1, k1) - 1;
									if (world.getBlock(i1, j2, k1).getMaterial() == Material.water) {
										double dx = i1 + 0.5 - entityplayer.posX;
										double dz = k1 + 0.5 - entityplayer.posZ;
										float angle = (float) Math.atan2(dz, dx);
										float cos = MathHelper.cos(angle);
										float sin = MathHelper.sin(angle);
										float angle90 = angle + (float) -1.5707963267948966;
										float cos90 = MathHelper.cos(angle90);
										float sin90 = MathHelper.sin(angle90);
										float waveSpeed = MathHelper.randomFloatClamp(rand, 0.3f, 0.5f);
										int waveR = 40 + rand.nextInt(100);
										for (int w = -waveR; w <= waveR; ++w) {
											float f = w / 8.0f;
											double d0 = i1 + 0.5;
											double d1 = j2 + 1.0 + MathHelper.randomFloatClamp(rand, 0.02f, 0.1f);
											double d2 = k1 + 0.5;
											if (world.getBlock(MathHelper.floor_double(d0 += f * cos90), MathHelper.floor_double(d1) - 1, MathHelper.floor_double(d2 += f * sin90)).getMaterial() == Material.water) {
												double d3 = waveSpeed * -cos;
												double d4 = 0.0;
												double d5 = waveSpeed * -sin;
												GOT.proxy.spawnParticle("wave", d0, d1, d2, d3, d4, d5);
											}
										}
									}
									break block42;
								}
							}
						}
					}
				} else {
					HashSet<ISound> removes = new HashSet<>();
					for (ISound sea : playingSeaSounds) {
						if (!mc.getSoundHandler().isSoundPlaying(sea)) {
							removes.add(sea);
						}
					}
					playingSeaSounds.removeAll(removes);
				}
			}
		}
		world.theProfiler.endSection();
	}

	public static class AmbientSoundNoAttentuation extends PositionedSoundRecord {
		public AmbientSoundNoAttentuation(ResourceLocation sound, float vol, float pitch, float x, float y, float z) {
			super(sound, vol, pitch, x, y, z);
			field_147666_i = ISound.AttenuationType.NONE;
		}

		public AmbientSoundNoAttentuation calcAmbientVolume(EntityPlayer entityplayer, int maxRange) {
			float distFr = (float) entityplayer.getDistance(xPosF, yPosF, zPosF);
			distFr /= maxRange;
			distFr = Math.min(distFr, 1.0f);
			distFr = 1.0f - distFr;
			distFr *= 1.5f;
			distFr = MathHelper.clamp_float(distFr, 0.1f, 1.0f);
			volume *= distFr;
			return this;
		}
	}

}
