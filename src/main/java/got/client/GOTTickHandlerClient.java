package got.client;

import java.util.*;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import got.GOT;
import got.client.effect.GOTEntityDeadMarshFace;
import got.client.gui.*;
import got.client.model.GOTModelCompass;
import got.client.render.other.*;
import got.client.sound.*;
import got.common.*;
import got.common.block.leaves.GOTBlockLeavesBase;
import got.common.database.*;
import got.common.enchant.*;
import got.common.entity.other.*;
import got.common.faction.*;
import got.common.fellowship.GOTFellowshipData;
import got.common.item.*;
import got.common.item.other.*;
import got.common.item.weapon.*;
import got.common.quest.IPickpocketable;
import got.common.util.*;
import got.common.world.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.essos.*;
import got.common.world.biome.ulthos.GOTBiomeUlthos;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.biome.westeros.*;
import got.common.world.map.GOTConquestGrid;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.WorldEvent;

public class GOTTickHandlerClient {
	public static ResourceLocation portalOverlay = new ResourceLocation("got:textures/misc/frost_overlay.png");
	public static ResourceLocation mistOverlay = new ResourceLocation("got:textures/misc/mist_overlay.png");
	public static ResourceLocation frostOverlay = new ResourceLocation("got:textures/misc/frost_overlay.png");
	public static ResourceLocation burnOverlay = new ResourceLocation("got:textures/misc/burn_overlay.png");
	public static ResourceLocation wightOverlay = new ResourceLocation("got:textures/misc/wight.png");
	public static float[] frostRGBMiddle = { 0.4F, 0.46F, 0.74F };
	public static float[] frostRGBEdge = { 1.0F, 1.0F, 1.0F };
	public static HashMap playersInPortals = new HashMap<>();
	public static int clientTick;
	public static float renderTick;
	public static GOTInvasionStatus watchedInvasion = new GOTInvasionStatus();
	public static GOTGuiNotificationDisplay notificationDisplay;
	public static GOTGuiMiniquestTracker miniquestTracker;
	public static boolean anyWightsViewed;
	public GOTAmbience ambienceTicker;
	public GuiScreen lastGuiOpen;
	public int mistTick;
	public int prevMistTick;
	public float mistFactor;
	public float sunGlare;
	public float prevSunGlare;
	public float rainFactor;
	public float prevRainFactor;
	public int alignmentXBase;
	public int alignmentYBase;
	public int alignmentXCurrent;
	public int alignmentYCurrent;
	public int alignmentXPrev;
	public int alignmentYPrev;
	public boolean firstAlignmentRender = true;
	public boolean wasShowingBannerRepossessMessage;
	public int bannerRepossessDisplayTick;
	public int frostTick;
	public int burnTick;
	public int drunkennessDirection = 1;
	public int newDate = 0;
	public int prevWightLookTick = 0;
	public int wightLookTick = 0;
	public int prevWightNearTick = 0;
	public int wightNearTick = 0;
	public boolean addedClientPoisonEffect = false;
	public GOTMusicTrack lastTrack = null;
	public int musicTrackTick = 0;
	public boolean cancelItemHighlight = false;
	public ItemStack lastHighlightedItemstack;
	public String highlightedItemstackName;
	public static boolean renderMenuPrompt = false;

	public GOTTickHandlerClient() {
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
		ambienceTicker = new GOTAmbience();
		notificationDisplay = new GOTGuiNotificationDisplay();
		miniquestTracker = new GOTGuiMiniquestTracker();
	}

	@SubscribeEvent
	public void getItemTooltip(ItemTooltipEvent event) {
		ItemStack itemstack = event.itemStack;
		List<String> tooltip = event.toolTip;
		EntityPlayer entityplayer = event.entityPlayer;
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		List<GOTEnchantment> enchantments = GOTEnchantmentHelper.getEnchantList(itemstack);
		if (!itemstack.hasDisplayName() && !enchantments.isEmpty()) {
			String name = tooltip.get(0);
			name = GOTEnchantmentHelper.getFullEnchantedName(itemstack, name);
			tooltip.set(0, name);
		}
		if (itemstack.getItem() instanceof GOTSquadrons.SquadronItem) {
			String squadron = GOTSquadrons.getSquadron(itemstack);
			if (!StringUtils.isNullOrEmpty(squadron)) {
				List<String> newTooltip = new ArrayList<>();
				newTooltip.add(tooltip.get(0));
				newTooltip.add(StatCollector.translateToLocalFormatted("item.got.generic.squadron", squadron));
				for (int i = 1; i < tooltip.size(); i++) {
					newTooltip.add(tooltip.get(i));
				}
				tooltip.clear();
				tooltip.addAll(newTooltip);
			}
		}
		if (GOTWeaponStats.isMeleeWeapon(itemstack)) {
			int dmgIndex = -1;
			for (int i = 0; i < tooltip.size(); i++) {
				String s = tooltip.get(i);
				if (s.startsWith(EnumChatFormatting.BLUE.toString())) {
					dmgIndex = i;
					break;
				}
			}
			if (dmgIndex >= 0) {
				List<String> newTooltip = new ArrayList<>();
				for (int j = 0; j <= dmgIndex - 1; j++) {
					newTooltip.add(tooltip.get(j));
				}
				float meleeDamage = GOTWeaponStats.getMeleeDamageBonus(itemstack);
				newTooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("got.weaponstat.meleeDamage", Float.valueOf(meleeDamage)));
				float meleeSpeed = GOTWeaponStats.getMeleeSpeed(itemstack);
				int pcSpeed = Math.round(meleeSpeed * 100.0F);
				newTooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("got.weaponstat.meleeSpeed", Integer.valueOf(pcSpeed)));
				float reach = GOTWeaponStats.getMeleeReachFactor(itemstack);
				int pcReach = Math.round(reach * 100.0F);
				newTooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("got.weaponstat.reach", Integer.valueOf(pcReach)));
				int kb = GOTWeaponStats.getTotalKnockback(itemstack);
				if (kb > 0) {
					newTooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("got.weaponstat.kb", Integer.valueOf(kb)));
				}
				for (int k = dmgIndex + 1; k < tooltip.size(); k++) {
					newTooltip.add(tooltip.get(k));
				}
				tooltip.clear();
				tooltip.addAll(newTooltip);
			}
		}
		if (GOTWeaponStats.isRangedWeapon(itemstack)) {
			tooltip.add("");
			float drawSpeed = GOTWeaponStats.getRangedSpeed(itemstack);
			if (drawSpeed > 0.0F) {
				int pcSpeed = Math.round(drawSpeed * 100.0F);
				tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted("got.weaponstat.rangedSpeed", Integer.valueOf(pcSpeed)));
			}
			float damage = GOTWeaponStats.getRangedDamageFactor(itemstack, false);
			if (damage > 0.0F) {
				int pcDamage = Math.round(damage * 100.0F);
				tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted("got.weaponstat.rangedDamage", Integer.valueOf(pcDamage)));
				if (itemstack.getItem() instanceof net.minecraft.item.ItemBow || itemstack.getItem() instanceof GOTItemCrossbow) {
					float range = GOTWeaponStats.getRangedDamageFactor(itemstack, true);
					int pcRange = Math.round(range * 100.0F);
					tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted("got.weaponstat.range", Integer.valueOf(pcRange)));
				}
			}
			int kb = GOTWeaponStats.getRangedKnockback(itemstack);
			if (kb > 0) {
				tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted("got.weaponstat.kb", Integer.valueOf(kb)));
			}
		}
		if (GOTWeaponStats.isPoisoned(itemstack)) {
			tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted("got.weaponstat.poison"));
		}
		int armorProtect = GOTWeaponStats.getArmorProtection(itemstack);
		if (armorProtect > 0) {
			tooltip.add("");
			int pcProtection = Math.round(armorProtect / 25.0F * 100.0F);
			tooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("got.weaponstat.protection", Integer.valueOf(armorProtect), Integer.valueOf(pcProtection)));
		}
		if (!enchantments.isEmpty()) {
			tooltip.add("");
			List<String> enchGood = new ArrayList<>();
			List<String> enchBad = new ArrayList<>();
			for (GOTEnchantment ench : enchantments) {
				String enchDesc = ench.getNamedFormattedDescription(itemstack);
				if (ench.isBeneficial()) {
					enchGood.add(enchDesc);
					continue;
				}
				enchBad.add(enchDesc);
			}
			tooltip.addAll(enchGood);
			tooltip.addAll(enchBad);
		}
		if (GOTPoisonedDrinks.isDrinkPoisoned(itemstack) && GOTPoisonedDrinks.canPlayerSeePoisoned(itemstack, entityplayer)) {
			tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocal("item.got.drink.poison"));
		}
		String currentOwner = GOTItemOwnership.getCurrentOwner(itemstack);
		if (currentOwner != null) {
			tooltip.add("");
			String ownerFormatted = StatCollector.translateToLocalFormatted("item.got.generic.currentOwner", currentOwner);
			List<String> ownerLines = fontRenderer.listFormattedStringToWidth(ownerFormatted, 150);
			for (int i = 0; i < ownerLines.size(); i++) {
				String line = ownerLines.get(i);
				if (i > 0) {
					line = "  " + line;
				}
				tooltip.add(line);
			}
		}
		List<String> previousOwners = GOTItemOwnership.getPreviousOwners(itemstack);
		if (!previousOwners.isEmpty()) {
			tooltip.add("");
			List<String> ownerLines = new ArrayList<>();
			if (previousOwners.size() == 1) {
				String ownerFormatted = EnumChatFormatting.ITALIC + StatCollector.translateToLocalFormatted("item.got.generic.previousOwner", previousOwners.get(0));
				ownerLines.addAll(fontRenderer.listFormattedStringToWidth(ownerFormatted, 150));
			} else {
				String beginList = EnumChatFormatting.ITALIC + StatCollector.translateToLocal("item.got.generic.previousOwnerList");
				ownerLines.add(beginList);
				for (String previousOwner : previousOwners) {
					previousOwner = EnumChatFormatting.ITALIC + previousOwner;
					ownerLines.addAll(fontRenderer.listFormattedStringToWidth(previousOwner, 150));
				}
			}
			for (int i = 0; i < ownerLines.size(); i++) {
				String line = ownerLines.get(i);
				if (i > 0) {
					line = "  " + line;
				}
				tooltip.add(line);
			}
		}
		if (IPickpocketable.Helper.isPickpocketed(itemstack)) {
			tooltip.add("");
			String owner = IPickpocketable.Helper.getOwner(itemstack);
			owner = StatCollector.translateToLocalFormatted("item.got.generic.stolen", owner);
			List<String> robbedLines = new ArrayList<String>(fontRenderer.listFormattedStringToWidth(owner, 200));
			for (int i = 0; i < robbedLines.size(); i++) {
				String line = robbedLines.get(i);
				if (i > 0) {
					line = "  " + line;
				}
				tooltip.add(line);
			}
		}
		if (itemstack.getItem() == Item.getItemFromBlock(Blocks.monster_egg)) {
			tooltip.set(0, EnumChatFormatting.RED + tooltip.get(0));
		}
		if (itemstack.getItem() instanceof GOTMaterialFinder) {
			if ((GOTMaterialFinder) itemstack.getItem() != GOTRegistry.baelishDagger && (((GOTMaterialFinder) itemstack.getItem()).getMaterial() == GOTMaterial.VALYRIAN_TOOL || ((GOTMaterialFinder) itemstack.getItem()).getMaterial() == GOTMaterial.OBSIDIAN_TOOL)) {
				tooltip.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("item.got.antiwalker"));
			}
			if ((GOTMaterialFinder) itemstack.getItem() == GOTRegistry.bericSword) {
				tooltip.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("item.got.antiwight"));
			}
			if ((GOTMaterialFinder) itemstack.getItem() == GOTRegistry.baelishDagger) {
				tooltip.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("item.got.antiking"));
			}
			if (((GOTMaterialFinder) itemstack.getItem()).getMaterial() == GOTMaterial.SILVER_TOOL) {
				tooltip.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("item.got.antimonster"));
			}
		}
	}

	public float getWightLookFactor() {
		float f = prevWightLookTick + (wightLookTick - prevWightLookTick) * renderTick;
		f /= 100.0F;
		return f;
	}

	public boolean isGamePaused(Minecraft mc) {
		return mc.isSingleplayer() && mc.currentScreen != null && mc.currentScreen.doesGuiPauseGame() && !mc.getIntegratedServer().getPublic();
	}

	public void onBurnDamage() {
		burnTick = 40;
	}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		Minecraft minecraft = Minecraft.getMinecraft();
		EntityClientPlayerMP entityplayer = minecraft.thePlayer;
		WorldClient world = minecraft.theWorld;
		if (event.phase == TickEvent.Phase.START) {
			clientTick++;
			if (GOTConfig.fixRenderDistance && !FMLClientHandler.instance().hasOptifine()) {
				GameSettings gs = Minecraft.getMinecraft().gameSettings;
				int renderDistance = gs.renderDistanceChunks;
				if (renderDistance > 16) {
					renderDistance = 16;
					gs.renderDistanceChunks = renderDistance;
					gs.saveOptions();
					GOTLog.logger.info("Hummel009: Render distance was above 16 - set to 16 to prevent a vanilla crash");
				}
			}
			if (minecraft.entityRenderer != null && !(minecraft.entityRenderer instanceof GOTEntityRenderer)) {
				minecraft.entityRenderer = new GOTEntityRenderer(minecraft, minecraft.getResourceManager());
				((IReloadableResourceManager) minecraft.getResourceManager()).registerReloadListener(minecraft.entityRenderer);
				FMLLog.info("Hummel009: Successfully replaced entityrenderer");
			}
		}
		if (event.phase == TickEvent.Phase.END) {
			if (minecraft.currentScreen == null) {
				lastGuiOpen = null;
			}
			if (FMLClientHandler.instance().hasOptifine()) {
				int optifineSetting = 0;
				try {
					Object field = GameSettings.class.getField("ofTrees").get(minecraft.gameSettings);
					if (field instanceof Integer) {
						optifineSetting = (Integer) field;
					}
				} catch (Exception exception) {
				}
				boolean fancyGraphics = optifineSetting == 0 ? minecraft.gameSettings.fancyGraphics : optifineSetting == 1 ? false : optifineSetting == 2;
				GOTBlockLeavesBase.setAllGraphicsLevels(fancyGraphics);
			} else {
				GOTBlockLeavesBase.setAllGraphicsLevels(minecraft.gameSettings.fancyGraphics);
			}
			if (entityplayer != null && world != null) {
				if (GOTConfig.checkUpdates) {
					GOTVersionChecker.checkForUpdates();
				}
				if (!isGamePaused(minecraft)) {
					miniquestTracker.update(minecraft, entityplayer);
					GOTAlignmentTicker.updateAll(entityplayer, false);
					watchedInvasion.tick();
					if (GOTItemBanner.hasChoiceToKeepOriginalOwner(entityplayer)) {
						boolean showBannerRespossessMessage = GOTItemBanner.isHoldingBannerWithExistingProtection(entityplayer);
						if (showBannerRespossessMessage && !wasShowingBannerRepossessMessage) {
							bannerRepossessDisplayTick = 60;
						} else {
							bannerRepossessDisplayTick--;
						}
						wasShowingBannerRepossessMessage = showBannerRespossessMessage;
					} else {
						bannerRepossessDisplayTick = 0;
						wasShowingBannerRepossessMessage = false;
					}
					EntityLivingBase viewer = minecraft.renderViewEntity;
					int i = MathHelper.floor_double(viewer.posX);
					int j = MathHelper.floor_double(viewer.boundingBox.minY);
					int k = MathHelper.floor_double(viewer.posZ);
					BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
					GOTBiome.updateWaterColor(i);
					GOTCloudRenderer.updateClouds(world);
					GOTRenderNorthernLights.update(viewer);
					GOTSpeechClient.update();
					GOTKeyHandler.update();
					GOTAttackTiming.update();
					prevMistTick = mistTick;
					if (viewer.posY >= 72.0D && biome instanceof GOTBiomeFrostfangs && world.canBlockSeeTheSky(i, j, k) && world.getSavedLightValue(EnumSkyBlock.Block, i, j, k) < 7) {
						if (mistTick < 80) {
							mistTick++;
						}
					} else if (mistTick > 0) {
						mistTick--;
					}
					if (frostTick > 0) {
						frostTick--;
					}
					if (burnTick > 0) {
						burnTick--;
					}
					prevWightLookTick = wightLookTick;
					if (anyWightsViewed) {
						if (wightLookTick < 100) {
							wightLookTick++;
						}
					} else if (wightLookTick > 0) {
						wightLookTick--;
					}
					prevWightNearTick = wightNearTick;
					double wightRange = 32.0D;
					List nearbyWights = world.getEntitiesWithinAABB(GOTEntityBarrowWight.class, viewer.boundingBox.expand(wightRange, wightRange, wightRange));
					if (!nearbyWights.isEmpty()) {
						if (wightNearTick < 100) {
							wightNearTick++;
						}
					} else if (wightNearTick > 0) {
						wightNearTick--;
					}
					if (GOTConfig.enableSunFlare && world.provider instanceof GOTWorldProvider && !world.provider.hasNoSky) {
						prevSunGlare = sunGlare;
						MovingObjectPosition look = viewer.rayTrace(10000.0D, renderTick);
						boolean lookingAtSky = look == null || look.typeOfHit == MovingObjectPosition.MovingObjectType.MISS;
						boolean biomeHasSun = true;
						if (biome instanceof GOTBiome) {
							biomeHasSun = ((GOTBiome) biome).hasSky();
						}
						float celestialAngle = world.getCelestialAngle(renderTick) * 360.0F - 90.0F;
						float sunPitch = celestialAngle;
						float sunYaw = 90.0F;
						float yc = MathHelper.cos((float) Math.toRadians(-sunYaw - 180.0F));
						float ys = MathHelper.sin((float) Math.toRadians(-sunYaw - 180.0F));
						float pc = -MathHelper.cos((float) Math.toRadians(-sunPitch));
						float ps = MathHelper.sin((float) Math.toRadians(-sunPitch));
						Vec3 sunVec = Vec3.createVectorHelper(ys * pc, ps, yc * pc);
						Vec3 lookVec = viewer.getLook(renderTick);
						double cos = lookVec.dotProduct(sunVec) / lookVec.lengthVector() * sunVec.lengthVector();
						float cosThreshold = 0.95F;
						float cQ = ((float) cos - cosThreshold) / (1.0F - cosThreshold);
						cQ = Math.max(cQ, 0.0F);
						float brightness = world.getSunBrightness(renderTick);
						float brightnessThreshold = 0.7F;
						float bQ = (brightness - brightnessThreshold) / (1.0F - brightnessThreshold);
						bQ = Math.max(bQ, 0.0F);
						float maxGlare = cQ * bQ;
						if (maxGlare > 0.0F && lookingAtSky && !world.isRaining() && biomeHasSun) {
							if (sunGlare < maxGlare) {
								sunGlare += 0.1F * maxGlare;
								sunGlare = Math.min(sunGlare, maxGlare);
							} else if (sunGlare > maxGlare) {
								sunGlare -= 0.02F;
								sunGlare = Math.max(sunGlare, maxGlare);
							}
						} else {
							if (sunGlare > 0.0F) {
								sunGlare -= 0.02F;
							}
							sunGlare = Math.max(sunGlare, 0.0F);
						}
					} else {
						prevSunGlare = sunGlare = 0.0F;
					}
					prevRainFactor = rainFactor;
					if (world.isRaining()) {
						if (rainFactor < 1.0F) {
							rainFactor += 0.008333334F;
							rainFactor = Math.min(rainFactor, 1.0F);
						}
					} else if (rainFactor > 0.0F) {
						rainFactor -= 0.0016666667F;
						rainFactor = Math.max(rainFactor, 0.0F);
					}
					if (minecraft.gameSettings.particleSetting < 2) {
						spawnEnvironmentFX(entityplayer, world);
					}
					GOTClientProxy.customEffectRenderer.updateEffects();
					if (minecraft.renderViewEntity.isPotionActive(Potion.confusion.id)) {
						float drunkenness = minecraft.renderViewEntity.getActivePotionEffect(Potion.confusion).getDuration();
						drunkenness /= 20.0F;
						if (drunkenness > 100.0F) {
							drunkenness = 100.0F;
						}
						minecraft.renderViewEntity.rotationYaw += drunkennessDirection * drunkenness / 20.0F;
						minecraft.renderViewEntity.rotationPitch += MathHelper.cos(minecraft.renderViewEntity.ticksExisted / 10.0F) * drunkenness / 20.0F;
						if (world.rand.nextInt(100) == 0) {
							drunkennessDirection *= -1;
						}
					}
					if (newDate > 0) {
						newDate--;
					}
					ambienceTicker.updateAmbience(world, entityplayer);
				}
				if ((entityplayer.dimension == 0 || entityplayer.dimension == GOTDimension.GAME_OF_THRONES.dimensionID) && playersInPortals.containsKey(entityplayer)) {
					List<GOTEntityPortal> portals = world.getEntitiesWithinAABB(GOTEntityPortal.class, entityplayer.boundingBox.expand(8.0D, 8.0D, 8.0D));
					boolean inPortal = false;
					int i;
					for (i = 0; i < portals.size(); i++) {
						GOTEntityPortal portal = portals.get(i);
						if (portal.boundingBox.intersectsWith(entityplayer.boundingBox)) {
							inPortal = true;
							break;
						}
					}
					if (inPortal) {
						i = (Integer) playersInPortals.get(entityplayer);
						i++;
						playersInPortals.put(entityplayer, Integer.valueOf(i));
						if (i >= 100) {
							minecraft.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("portal.trigger"), world.rand.nextFloat() * 0.4F + 0.8F));
							playersInPortals.remove(entityplayer);
						}
					} else {
						playersInPortals.remove(entityplayer);
					}
				}
			}
			GOTClientProxy.musicHandler.update();
			if (GOTConfig.displayMusicTrack) {
				GOTMusicTrack nowPlaying = GOTMusicTicker.currentTrack;
				if (nowPlaying != lastTrack) {
					lastTrack = nowPlaying;
					musicTrackTick = 200;
				}
				if (lastTrack != null && musicTrackTick > 0) {
					musicTrackTick--;
				}
			}
			GuiScreen guiscreen = minecraft.currentScreen;
			if (guiscreen != null) {
				if (guiscreen instanceof GuiMainMenu && !(lastGuiOpen instanceof GuiMainMenu)) {
					GOTLevelData.needsLoad = true;
					GOTTime.needsLoad = true;
					GOTFellowshipData.needsLoad = true;
					GOTFactionBounties.needsLoad = true;
					GOTFactionRelations.needsLoad = true;
					GOTDate.resetWorldTimeInMenu();
					GOTConquestGrid.needsLoad = true;
					GOTSpeechClient.clearAll();
					GOTAttackTiming.reset();
					GOTGuiMenu.resetLastMenuScreen();
					GOTGuiMap.clearPlayerLocations();
					GOTCloudRenderer.resetClouds();
					firstAlignmentRender = true;
					watchedInvasion.clear();
				}
				lastGuiOpen = guiscreen;
			}
			anyWightsViewed = false;
		}
	}

	@SubscribeEvent
	public void onFogColors(EntityViewRenderEvent.FogColors event) {
		Minecraft mc = Minecraft.getMinecraft();
		WorldClient worldClient = mc.theWorld;
		WorldProvider provider = ((World) worldClient).provider;
		if (provider instanceof GOTWorldProvider) {
			float[] rgb = { event.red, event.green, event.blue };
			rgb = ((GOTWorldProvider) provider).handleFinalFogColors(event.entity, event.renderPartialTicks, rgb);
			event.red = rgb[0];
			event.green = rgb[1];
			event.blue = rgb[2];
		}
	}

	@SubscribeEvent
	public void onFOVUpdate(FOVUpdateEvent event) {
		EntityPlayerSP entityplayer = event.entity;
		float fov = event.newfov;
		ItemStack itemstack = entityplayer.getHeldItem();
		Item item = itemstack == null ? null : itemstack.getItem();
		float usage = -1.0F;
		if (entityplayer.isUsingItem()) {
			float maxDrawTime = 0.0F;
			if (item instanceof GOTItemBow) {
				maxDrawTime = ((GOTItemBow) item).getMaxDrawTime();
			} else if (item instanceof GOTItemCrossbow) {
				maxDrawTime = ((GOTItemCrossbow) item).getMaxDrawTime();
			} else if (item instanceof GOTItemSpear) {
				maxDrawTime = ((GOTItemSpear) item).getMaxDrawTime();
			} else if (item instanceof GOTItemSarbacane) {
				maxDrawTime = ((GOTItemSarbacane) item).getMaxDrawTime();
			}
			if (maxDrawTime > 0.0F) {
				int i = entityplayer.getItemInUseDuration();
				usage = i / maxDrawTime;
				if (usage > 1.0F) {
					usage = 1.0F;
				} else {
					usage *= usage;
				}
			}
		}
		if (GOTItemCrossbow.isLoaded(itemstack)) {
			usage = 1.0F;
		}
		if (usage >= 0.0F) {
			fov *= 1.0F - usage * 0.15F;
		}
		event.newfov = fov;
	}

	public void onFrostDamage() {
		frostTick = 80;
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		EntityPlayer player = event.player;
		if (event.phase == TickEvent.Phase.END && player instanceof EntityClientPlayerMP) {
			EntityClientPlayerMP clientPlayer = (EntityClientPlayerMP) player;
			if (clientPlayer.isRiding()) {
				GOTMountFunctions.sendControlToServer(clientPlayer);
			}
		}
	}

	@SubscribeEvent
	public void onPostRenderGameOverlay(RenderGameOverlayEvent.Post event) {
		Minecraft mc = Minecraft.getMinecraft();
		WorldClient worldClient = mc.theWorld;
		EntityClientPlayerMP entityClientPlayerMP = mc.thePlayer;
		GuiIngame guiIngame = mc.ingameGUI;
		if (worldClient != null && entityClientPlayerMP != null) {
			if (event.type == RenderGameOverlayEvent.ElementType.ALL && lastHighlightedItemstack != null) {
				if (highlightedItemstackName != null) {
					lastHighlightedItemstack.setStackDisplayName(highlightedItemstackName);
				} else {
					lastHighlightedItemstack.func_135074_t();
				}
				lastHighlightedItemstack = null;
				highlightedItemstackName = null;
			}
			if (event.type == RenderGameOverlayEvent.ElementType.BOSSHEALTH && watchedInvasion.isActive()) {
				GL11.glEnable(3042);
				FontRenderer fr = mc.fontRenderer;
				ScaledResolution scaledresolution = event.resolution;
				int width = scaledresolution.getScaledWidth();
				int barWidth = 182;
				int remainingWidth = (int) (watchedInvasion.getHealth() * (barWidth - 2));
				int barHeight = 5;
				int barX = width / 2 - barWidth / 2;
				int barY = 12;
				if (isBossActive()) {
					barY += 20;
				}
				mc.getTextureManager().bindTexture(GOTClientProxy.alignmentTexture);
				guiIngame.drawTexturedModalRect(barX, barY, 64, 64, barWidth, barHeight);
				if (remainingWidth > 0) {
					float[] rgb = watchedInvasion.getRGB();
					GL11.glColor4f(rgb[0], rgb[1], rgb[2], 1.0F);
					guiIngame.drawTexturedModalRect(barX + 1, barY + 1, 65, 70, remainingWidth, barHeight - 2);
				}
				String s = watchedInvasion.getTitle();
				fr.drawStringWithShadow(s, width / 2 - fr.getStringWidth(s) / 2, barY - 10, 16777215);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.getTextureManager().bindTexture(Gui.icons);
				GL11.glDisable(3042);
			}
			if (event.type == RenderGameOverlayEvent.ElementType.HEALTH && addedClientPoisonEffect) {
				entityClientPlayerMP.removePotionEffectClient(Potion.poison.id);
				addedClientPoisonEffect = false;
			}
			if (event.type == RenderGameOverlayEvent.ElementType.TEXT && bannerRepossessDisplayTick > 0) {
				String text = StatCollector.translateToLocalFormatted("item.got.banner.toggleRepossess", GameSettings.getKeyDisplayString(mc.gameSettings.keyBindSneak.getKeyCode()));
				int fadeAtTick = 10;
				int opacity = (int) (bannerRepossessDisplayTick * 255.0F / fadeAtTick);
				opacity = Math.min(opacity, 255);
				if (opacity > 0) {
					ScaledResolution scaledresolution = event.resolution;
					int width = scaledresolution.getScaledWidth();
					int height = scaledresolution.getScaledHeight();
					int y = height - 59;
					y -= 12;
					if (!mc.playerController.shouldDrawHUD()) {
						y += 14;
					}
					GL11.glPushMatrix();
					GL11.glEnable(3042);
					OpenGlHelper.glBlendFunc(770, 771, 1, 0);
					FontRenderer fr = mc.fontRenderer;
					int x = (width - fr.getStringWidth(text)) / 2;
					fr.drawString(text, x, y, 0xFFFFFF | opacity << 24);
					GL11.glDisable(3042);
					GL11.glPopMatrix();
				}
			}
		}
	}

	@SubscribeEvent
	public void onPreRenderGameOverlay(RenderGameOverlayEvent.Pre event) {
		Minecraft mc = Minecraft.getMinecraft();
		WorldClient worldClient = mc.theWorld;
		EntityClientPlayerMP entityClientPlayerMP = mc.thePlayer;
		float partialTicks = event.partialTicks;
		GuiIngame guiIngame = mc.ingameGUI;
		if (worldClient != null && entityClientPlayerMP != null) {
			if (event.type == RenderGameOverlayEvent.ElementType.ALL) {
				mc.theWorld.theProfiler.startSection("got_fixHighlightedItemName");
				ItemStack itemstack = GOTReflectionClient.getHighlightedItemStack(guiIngame);
				if (itemstack != null && !itemstack.hasDisplayName()) {
					List<GOTEnchantment> enchants = GOTEnchantmentHelper.getEnchantList(itemstack);
					if (!enchants.isEmpty()) {
						lastHighlightedItemstack = itemstack;
						highlightedItemstackName = itemstack.hasDisplayName() ? itemstack.getDisplayName() : null;
						itemstack.setStackDisplayName(GOTEnchantmentHelper.getFullEnchantedName(itemstack, itemstack.getDisplayName()));
					}
				}
				mc.theWorld.theProfiler.endSection();
			}
			if (event.type == RenderGameOverlayEvent.ElementType.HELMET) {
				if (sunGlare > 0.0F && mc.gameSettings.thirdPersonView == 0) {
					float brightness = prevSunGlare + (sunGlare - prevSunGlare) * partialTicks;
					brightness *= 1.0F;
					renderOverlay(null, brightness, mc, null);
				}
				if (playersInPortals.containsKey(entityClientPlayerMP)) {
					int i = (Integer) playersInPortals.get(entityClientPlayerMP);
					if (i > 0) {
						renderOverlay(null, 0.1F + i / 100.0F * 0.6F, mc, portalOverlay);
					}
				}
				float mistTickF = prevMistTick + (mistTick - prevMistTick) * partialTicks;
				mistTickF /= 80.0F;
				float mistFactorY = (float) ((EntityPlayer) entityClientPlayerMP).posY / 256.0F;
				mistFactor = mistTickF * mistFactorY;
				if (mistFactor > 0.0F) {
					renderOverlay(null, mistFactor * 0.75F, mc, mistOverlay);
				}
				if (frostTick > 0) {
					float frostAlpha = frostTick / 80.0F;
					frostAlpha *= 0.9F;
					float frostAlphaEdge = (float) Math.sqrt(frostAlpha);
					renderOverlayWithVerticalGradients(frostRGBEdge, frostRGBMiddle, frostAlphaEdge, frostAlpha, mc);
					renderOverlay(null, frostAlpha * 0.6F, mc, frostOverlay);
				}
				if (burnTick > 0) {
					renderOverlay(null, burnTick / 40.0F * 0.6F, mc, burnOverlay);
				}
				if (wightLookTick > 0) {
					renderOverlay(null, wightLookTick / 100.0F * 0.95F, mc, wightOverlay);
				}
			}
			if (event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
				if (GOTConfig.meleeAttackMeter) {
					GOTAttackTiming.renderAttackMeter(event.resolution, partialTicks);
				}
				if (((EntityPlayer) entityClientPlayerMP).ridingEntity instanceof GOTEntitySpiderBase) {
					GOTEntitySpiderBase spider = (GOTEntitySpiderBase) ((EntityPlayer) entityClientPlayerMP).ridingEntity;
					if (spider.shouldRenderClimbingMeter()) {
						mc.getTextureManager().bindTexture(Gui.icons);
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						GL11.glDisable(3042);
						mc.mcProfiler.startSection("spiderClimb");
						ScaledResolution resolution = event.resolution;
						int width = resolution.getScaledWidth();
						int height = resolution.getScaledHeight();
						float charge = spider.getClimbFractionRemaining();
						int x = width / 2 - 91;
						int filled = (int) (charge * 183.0F);
						int top = height - 32 + 3;
						guiIngame.drawTexturedModalRect(x, top, 0, 84, 182, 5);
						if (filled > 0) {
							guiIngame.drawTexturedModalRect(x, top, 0, 89, filled, 5);
						}
						GL11.glEnable(3042);
						mc.mcProfiler.endSection();
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					}
				}
			}
			if (event.type == RenderGameOverlayEvent.ElementType.HEALTH && entityClientPlayerMP.isPotionActive(GOTPoisonedDrinks.killingPoison) && !entityClientPlayerMP.isPotionActive(Potion.poison)) {
				entityClientPlayerMP.addPotionEffect(new PotionEffect(Potion.poison.id, 20));
				addedClientPoisonEffect = true;
			}
			boolean enchantingDisabled = !GOTLevelData.clientside_thisServer_enchanting && ((World) worldClient).provider instanceof GOTWorldProvider;
			if (event.type == RenderGameOverlayEvent.ElementType.EXPERIENCE && enchantingDisabled) {
				event.setCanceled(true);
				return;
			}
			if (event.type == RenderGameOverlayEvent.ElementType.ALL && enchantingDisabled && ((EntityPlayer) entityClientPlayerMP).ridingEntity == null) {
				GuiIngameForge.left_height -= 6;
				GuiIngameForge.right_height -= 6;
			}
			if (event.type == RenderGameOverlayEvent.ElementType.ARMOR) {
				event.setCanceled(true);
				ScaledResolution resolution = event.resolution;
				int width = resolution.getScaledWidth();
				int height = resolution.getScaledHeight();
				mc.mcProfiler.startSection("armor");
				GL11.glEnable(3042);
				int left = width / 2 - 91;
				int top = height - GuiIngameForge.left_height;
				int level = GOTWeaponStats.getTotalArmorValue(mc.thePlayer);
				if (level > 0) {
					for (int i = 1; i < 20; i += 2) {
						if (i < level) {
							guiIngame.drawTexturedModalRect(left, top, 34, 9, 9, 9);
						} else if (i == level) {
							guiIngame.drawTexturedModalRect(left, top, 25, 9, 9, 9);
						} else if (i > level) {
							guiIngame.drawTexturedModalRect(left, top, 16, 9, 9, 9);
						}
						left += 8;
					}
				}
				GuiIngameForge.left_height += 10;
				GL11.glDisable(3042);
				mc.mcProfiler.endSection();
			}
		}
	}

	@SubscribeEvent
	public void onRenderDebugText(RenderGameOverlayEvent.Text event) {
		Minecraft mc = Minecraft.getMinecraft();
		if (mc.gameSettings.showDebugInfo && mc.theWorld != null && mc.thePlayer != null && mc.theWorld.getWorldChunkManager() instanceof GOTWorldChunkManager) {
			mc.theWorld.theProfiler.startSection("gotBiomeDisplay");
			GOTWorldChunkManager chunkManager = (GOTWorldChunkManager) mc.theWorld.getWorldChunkManager();
			int i = MathHelper.floor_double(mc.thePlayer.posX);
			MathHelper.floor_double(mc.thePlayer.boundingBox.minY);
			int k = MathHelper.floor_double(mc.thePlayer.posZ);
			GOTBiome biome = (GOTBiome) mc.theWorld.getBiomeGenForCoords(i, k);
			GOTBiomeVariant variant = chunkManager.getBiomeVariantAt(i, k);
			event.left.add(null);
			biome.addBiomeF3Info(event.left, mc.theWorld, variant);
			mc.theWorld.theProfiler.endSection();
		}
	}

	@SubscribeEvent
	public void onRenderFog(EntityViewRenderEvent.RenderFogEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityLivingBase viewer = event.entity;
		WorldClient worldClient = mc.theWorld;
		WorldProvider provider = ((World) worldClient).provider;
		int i = MathHelper.floor_double(viewer.posX);
		int j = MathHelper.floor_double(viewer.boundingBox.minY);
		int k = MathHelper.floor_double(viewer.posZ);
		BiomeGenBase biome = worldClient.getBiomeGenForCoords(i, k);
		float farPlane = event.farPlaneDistance;
		int fogMode = event.fogMode;
		if (provider instanceof GOTWorldProvider) {
			GOTBiome gotbiome = (GOTBiome) biome;
			float[] fogStartEnd = ((GOTWorldProvider) provider).modifyFogIntensity(farPlane, fogMode);
			float fogStart = fogStartEnd[0];
			float fogEnd = fogStartEnd[1];
			if (gotbiome.getEnableRain() || gotbiome.getEnableSnow()) {
				float rain = prevRainFactor + (rainFactor - prevRainFactor) * renderTick;
				if (rain > 0.0F) {
					float rainOpacityStart = 0.95F;
					float rainOpacityEnd = 0.2F;
					fogStart -= fogStart * rain * rainOpacityStart;
					fogEnd -= fogEnd * rain * rainOpacityEnd;
				}
			}
			if (mistFactor > 0.0F) {
				float mistOpacityStart = 0.95F;
				float mistOpacityEnd = 0.7F;
				fogStart -= fogStart * mistFactor * mistOpacityStart;
				fogEnd -= fogEnd * mistFactor * mistOpacityEnd;
			}
			float wightFactor = prevWightNearTick + (wightNearTick - prevWightNearTick) * renderTick;
			wightFactor /= 100.0F;
			if (wightFactor > 0.0F) {
				float wightOpacityStart = 0.97F;
				float wightOpacityEnd = 0.75F;
				fogStart -= fogStart * wightFactor * wightOpacityStart;
				fogEnd -= fogEnd * wightFactor * wightOpacityEnd;
			}
			if (gotbiome instanceof GOTBiomeNorthBarrows) {
				if (wightFactor > 0.0F) {
					int sky0 = gotbiome.getBaseSkyColorByTemp(i, j, k);
					int sky1 = 9674385;
					int clouds0 = 16777215;
					int clouds1 = 11842740;
					int fog0 = 16777215;
					int fog1 = 10197915;
					gotbiome.biomeColors.setSky(GOTColorUtil.lerpColors_I(sky0, sky1, wightFactor));
					gotbiome.biomeColors.setClouds(GOTColorUtil.lerpColors_I(clouds0, clouds1, wightFactor));
					gotbiome.biomeColors.setFog(GOTColorUtil.lerpColors_I(fog0, fog1, wightFactor));
				} else {
					gotbiome.biomeColors.resetSky();
					gotbiome.biomeColors.resetClouds();
					gotbiome.biomeColors.resetFog();
				}
			}
			GL11.glFogf(2915, fogStart);
			GL11.glFogf(2916, fogEnd);
		}
	}

	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event) {
		Minecraft minecraft = Minecraft.getMinecraft();
		EntityClientPlayerMP entityplayer = minecraft.thePlayer;
		WorldClient worldClient = minecraft.theWorld;
		if (event.phase == TickEvent.Phase.START) {
			renderTick = event.renderTickTime;
			if (cancelItemHighlight) {
				GuiIngame guiIngame = minecraft.ingameGUI;
				int highlightTicks = GOTReflectionClient.getHighlightedItemTicks(guiIngame);
				if (highlightTicks > 0) {
					GOTReflectionClient.setHighlightedItemTicks(guiIngame, 0);
					cancelItemHighlight = false;
				}
			}
		}
		if (event.phase == TickEvent.Phase.END) {
			if (entityplayer != null && worldClient != null) {
				if ((((World) worldClient).provider instanceof GOTWorldProvider || GOTConfig.alwaysShowAlignment) && Minecraft.isGuiEnabled()) {
					alignmentXPrev = alignmentXCurrent;
					alignmentYPrev = alignmentYCurrent;
					alignmentXCurrent = alignmentXBase;
					int yMove = (int) ((alignmentYBase - -20) / 10.0F);
					boolean alignmentOnscreen = (minecraft.currentScreen == null || minecraft.currentScreen instanceof got.client.gui.GOTGuiMessage) && !minecraft.gameSettings.keyBindPlayerList.getIsKeyPressed() && !minecraft.gameSettings.showDebugInfo;
					if (alignmentOnscreen) {
						alignmentYCurrent = Math.min(alignmentYCurrent + yMove, alignmentYBase);
					} else {
						alignmentYCurrent = Math.max(alignmentYCurrent - yMove, -20);
					}
					renderAlignment(minecraft, renderTick);
					if (GOTConfig.enableOnscreenCompass && minecraft.currentScreen == null && !minecraft.gameSettings.showDebugInfo) {
						GL11.glPushMatrix();
						ScaledResolution resolution = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
						int i = resolution.getScaledWidth();
						resolution.getScaledHeight();
						int compassX = i - 60;
						int compassY = 40;
						GL11.glTranslatef(compassX, compassY, 0.0F);
						float rotation = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * event.renderTickTime;
						rotation = 180.0F - rotation;
						GOTModelCompass.compassModel.render(1.0F, rotation);
						GL11.glPopMatrix();
						if (GOTConfig.compassExtraInfo) {
							GL11.glPushMatrix();
							GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
							float scale = 0.5F;
							float invScale = 1.0F / scale;
							compassX = (int) (compassX * invScale);
							compassY = (int) (compassY * invScale);
							GL11.glScalef(scale, scale, scale);
							String coords = MathHelper.floor_double(entityplayer.posX) + ", " + MathHelper.floor_double(entityplayer.boundingBox.minY) + ", " + MathHelper.floor_double(entityplayer.posZ);
							FontRenderer fontRenderer = minecraft.fontRenderer;
							fontRenderer.drawString(coords, compassX - fontRenderer.getStringWidth(coords) / 2, compassY + 70, 16777215);
							int playerX = MathHelper.floor_double(entityplayer.posX);
							int playerZ = MathHelper.floor_double(entityplayer.posZ);
							if (GOTClientProxy.doesClientChunkExist(worldClient, playerX, playerZ)) {
								BiomeGenBase biome = worldClient.getBiomeGenForCoords(playerX, playerZ);
								if (biome instanceof GOTBiome) {
									String biomeName = ((GOTBiome) biome).getBiomeDisplayName();
									fontRenderer.drawString(biomeName, compassX - fontRenderer.getStringWidth(biomeName) / 2, compassY - 70, 16777215);
								}
							}
							GL11.glPopMatrix();
						}
					}
				}
				float promptTick = clientTick + renderTick;
				float promptAlpha = GOTFunctions.triangleWave(promptTick, 0.5f, 1.0f, 80.0f);
				ArrayList<String> message = new ArrayList<>();
				if (entityplayer.dimension != GOTDimension.GAME_OF_THRONES.dimensionID && renderMenuPrompt && minecraft.currentScreen == null) {
					message.add(StatCollector.translateToLocal("got.gui.help1"));
					message.add(StatCollector.translateToLocalFormatted("got.gui.help2", GameSettings.getKeyDisplayString(GOTKeyHandler.keyBindingReturn.getKeyCode())));
				}
				if (!message.isEmpty()) {
					ScaledResolution resolution2 = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
					int width = resolution2.getScaledWidth();
					int height = resolution2.getScaledHeight();
					int x = 0;
					int y = height * 2 / 3 - message.size() * minecraft.fontRenderer.FONT_HEIGHT / 2;
					GL11.glEnable(3042);
					OpenGlHelper.glBlendFunc(770, 771, 1, 0);
					for (String line : message) {
						x = (width - minecraft.fontRenderer.getStringWidth(line)) / 2;
						minecraft.fontRenderer.drawString(line, x, y, 0xFFFFFF | GOTClientProxy.getAlphaInt(promptAlpha) << 24);
						y += minecraft.fontRenderer.FONT_HEIGHT;
					}
					GL11.glDisable(3042);
				}
				if (entityplayer.dimension == GOTDimension.GAME_OF_THRONES.dimensionID && minecraft.currentScreen == null && newDate > 0) {
					int halfMaxDate = 100;
					float alpha = 0.0F;
					if (newDate > halfMaxDate) {
						alpha = (200 - newDate) / halfMaxDate;
					} else {
						alpha = newDate / halfMaxDate;
					}
					String date = GOTDate.AegonCalendar.getDate().getDateName(true);
					ScaledResolution resolution = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
					int i = resolution.getScaledWidth();
					int j = resolution.getScaledHeight();
					float scale = 1.5F;
					float invScale = 1.0F / scale;
					i = (int) (i * invScale);
					j = (int) (j * invScale);
					int x = (i - minecraft.fontRenderer.getStringWidth(date)) / 2;
					int y = (j - minecraft.fontRenderer.FONT_HEIGHT) * 2 / 5;
					GL11.glScalef(scale, scale, scale);
					GL11.glEnable(3042);
					OpenGlHelper.glBlendFunc(770, 771, 1, 0);
					minecraft.fontRenderer.drawString(date, x, y, 16777215 + (GOTClientProxy.getAlphaInt(alpha) << 24));
					GL11.glDisable(3042);
					GL11.glScalef(invScale, invScale, invScale);
				}
				if (GOTConfig.displayMusicTrack && minecraft.currentScreen == null && lastTrack != null && musicTrackTick > 0) {
					List<String> lines = new ArrayList<>();
					lines.add(StatCollector.translateToLocal("got.music.nowPlaying"));
					String title = lastTrack.getTitle();
					lines.add(title);
					if (!lastTrack.getAuthors().isEmpty()) {
						StringBuilder authors = new StringBuilder("(");
						int a = 0;
						for (String auth : lastTrack.getAuthors()) {
							authors.append(auth);
							if (a < lastTrack.getAuthors().size() - 1) {
								authors.append(", ");
							}
							a++;
						}
						authors.append(")");
						lines.add(authors.toString());
					}
					ScaledResolution resolution = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
					int w = resolution.getScaledWidth();
					int h = resolution.getScaledHeight();
					int border = 20;
					int x = w - border;
					int y = h - border - lines.size() * minecraft.fontRenderer.FONT_HEIGHT;
					float alpha = 1.0F;
					if (musicTrackTick >= 140) {
						alpha = (200 - musicTrackTick) / 60.0F;
					} else if (musicTrackTick <= 60) {
						alpha = musicTrackTick / 60.0F;
					}
					for (String line : lines) {
						x = w - border - minecraft.fontRenderer.getStringWidth(line);
						minecraft.fontRenderer.drawString(line, x, y, 16777215 + (GOTClientProxy.getAlphaInt(alpha) << 24));
						y += minecraft.fontRenderer.FONT_HEIGHT;
					}
				}
			}
			notificationDisplay.updateWindow();
			if (GOTConfig.enableQuestTracker && minecraft.currentScreen == null && !minecraft.gameSettings.showDebugInfo) {
				miniquestTracker.drawTracker(minecraft, entityplayer);
			}
		}
	}

	@SubscribeEvent
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		float f = event.partialTicks;
		if (GOTDimension.getCurrentDimension(mc.theWorld) == GOTDimension.GAME_OF_THRONES) {
			GOTRenderNorthernLights.render(mc, mc.theWorld, f);
		}
		mc.entityRenderer.enableLightmap(f);
		RenderHelper.disableStandardItemLighting();
		GOTClientProxy.customEffectRenderer.renderParticles(mc.renderViewEntity, f);
		mc.entityRenderer.disableLightmap(f);
		if (Minecraft.isGuiEnabled() && mc.entityRenderer.debugViewDirection == 0) {
			mc.mcProfiler.startSection("gotSpeech");
			GOTNPCRendering.renderAllNPCSpeeches(mc, mc.theWorld, f);
			mc.mcProfiler.endSection();
		}
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		if (event.world instanceof WorldClient) {
			GOTClientProxy.customEffectRenderer.clearEffectsAndSetWorld(event.world);
		}
	}

	public void renderAlignment(Minecraft mc, float f) {
		EntityClientPlayerMP entityClientPlayerMP = mc.thePlayer;
		GOTPlayerData pd = GOTLevelData.getData(entityClientPlayerMP);
		GOTFaction viewingFac = pd.getViewingFaction();
		ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		int width = resolution.getScaledWidth();
		resolution.getScaledHeight();
		alignmentXBase = width / 2 + GOTConfig.alignmentXOffset;
		alignmentYBase = 4 + GOTConfig.alignmentYOffset;
		if (isBossActive()) {
			alignmentYBase += 20;
		}
		if (watchedInvasion.isActive()) {
			alignmentYBase += 20;
		}
		if (firstAlignmentRender) {
			GOTAlignmentTicker.updateAll(entityClientPlayerMP, true);
			alignmentXPrev = alignmentXCurrent = alignmentXBase;
			alignmentYPrev = alignmentYCurrent = -20;
			firstAlignmentRender = false;
		}
		float alignmentXF = alignmentXPrev + (alignmentXCurrent - alignmentXPrev) * f;
		float alignmentYF = alignmentYPrev + (alignmentYCurrent - alignmentYPrev) * f;
		boolean text = alignmentYCurrent == alignmentYBase;
		float alignment = GOTAlignmentTicker.forFaction(viewingFac).getInterpolatedAlignment(f);
		renderAlignmentBar(alignment, false, viewingFac, alignmentXF, alignmentYF, text, text, text, false);
	}

	public void renderOverlay(float[] rgb, float alpha, Minecraft mc, ResourceLocation texture) {
		ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		int width = resolution.getScaledWidth();
		int height = resolution.getScaledHeight();
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		if (rgb != null) {
			GL11.glColor4f(rgb[0], rgb[1], rgb[2], alpha);
		} else {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
		}
		GL11.glDisable(3008);
		if (texture != null) {
			mc.getTextureManager().bindTexture(texture);
		} else {
			GL11.glDisable(3553);
		}
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(0.0D, height, -90.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV(width, height, -90.0D, 1.0D, 1.0D);
		tessellator.addVertexWithUV(width, 0.0D, -90.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
		tessellator.draw();
		if (texture == null) {
			GL11.glEnable(3553);
		}
		GL11.glDepthMask(true);
		GL11.glEnable(2929);
		GL11.glEnable(3008);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public void renderOverlayWithVerticalGradients(float[] rgbEdge, float[] rgbCentre, float alphaEdge, float alphaCentre, Minecraft mc) {
		ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		int width = resolution.getScaledWidth();
		int height = resolution.getScaledHeight();
		int heightThird = height / 3;
		int heightTwoThirds = height * 2 / 3;
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glDisable(3008);
		GL11.glDisable(3553);
		GL11.glShadeModel(7425);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_F(rgbCentre[0], rgbCentre[1], rgbCentre[2], alphaCentre);
		tessellator.addVertex(0.0D, heightThird, -90.0D);
		tessellator.addVertex(width, heightThird, -90.0D);
		tessellator.setColorRGBA_F(rgbEdge[0], rgbEdge[1], rgbEdge[2], alphaEdge);
		tessellator.addVertex(width, 0.0D, -90.0D);
		tessellator.addVertex(0.0D, 0.0D, -90.0D);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_F(rgbCentre[0], rgbCentre[1], rgbCentre[2], alphaCentre);
		tessellator.addVertex(0.0D, heightTwoThirds, -90.0D);
		tessellator.addVertex(width, heightTwoThirds, -90.0D);
		tessellator.setColorRGBA_F(rgbCentre[0], rgbCentre[1], rgbCentre[2], alphaCentre);
		tessellator.addVertex(width, heightThird, -90.0D);
		tessellator.addVertex(0.0D, heightThird, -90.0D);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_F(rgbEdge[0], rgbEdge[1], rgbEdge[2], alphaEdge);
		tessellator.addVertex(0.0D, height, -90.0D);
		tessellator.addVertex(width, height, -90.0D);
		tessellator.setColorRGBA_F(rgbCentre[0], rgbCentre[1], rgbCentre[2], alphaCentre);
		tessellator.addVertex(width, heightTwoThirds, -90.0D);
		tessellator.addVertex(0.0D, heightTwoThirds, -90.0D);
		tessellator.draw();
		GL11.glShadeModel(7424);
		GL11.glEnable(3553);
		GL11.glDepthMask(true);
		GL11.glEnable(2929);
		GL11.glEnable(3008);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public void spawnEnvironmentFX(EntityPlayer entityplayer, World world) {
		world.theProfiler.startSection("gotEnvironmentFX");
		int i = MathHelper.floor_double(entityplayer.posX);
		int j = MathHelper.floor_double(entityplayer.boundingBox.minY);
		int k = MathHelper.floor_double(entityplayer.posZ);
		byte range = 16;
		for (int l = 0; l < 1000; l++) {
			int i1 = i + world.rand.nextInt(range) - world.rand.nextInt(range);
			int j1 = j + world.rand.nextInt(range) - world.rand.nextInt(range);
			int k1 = k + world.rand.nextInt(range) - world.rand.nextInt(range);
			Block block = world.getBlock(i1, j1, k1);
			int meta = world.getBlockMetadata(i1, j1, k1);
			if (block.getMaterial() == Material.water) {
				BiomeGenBase biome = world.getBiomeGenForCoords(i1, k1);
				if (biome instanceof GOTBiomeUlthos && world.rand.nextInt(20) == 0) {
					GOT.proxy.spawnParticle("ulthosWater", i1 + world.rand.nextFloat(), j1 + 0.75D, k1 + world.rand.nextFloat(), 0.0D, 0.05D, 0.0D);
				}
				if (biome instanceof GOTBiomeShadowLand && world.rand.nextInt(40) == 0) {
					GOT.proxy.spawnParticle("asshaiWater", i1 + world.rand.nextFloat(), j1 + 0.75D, k1 + world.rand.nextFloat(), 0.0D, 0.05D, 0.0D);
				}
				if (biome instanceof GOTBiomeSuudanKorkuu && world.rand.nextInt(800) == 0) {
					world.spawnEntityInWorld(new GOTEntityDeadMarshFace(world, i1 + world.rand.nextFloat(), j1 + 0.25D - world.rand.nextFloat(), k1 + world.rand.nextFloat()));
				}
			}
			if (block.getMaterial() == Material.water && meta != 0) {
				Block below = world.getBlock(i1, j1 - 1, k1);
				if (below.getMaterial() == Material.water) {
					for (int i2 = i1 - 1; i2 <= i1 + 1; i2++) {
						for (int k2 = k1 - 1; k2 <= k1 + 1; k2++) {
							Block adjBlock = world.getBlock(i2, j1 - 1, k2);
							int adjMeta = world.getBlockMetadata(i2, j1 - 1, k2);
							if (adjBlock.getMaterial() == Material.water && adjMeta == 0 && world.isAirBlock(i2, j1, k2)) {
								for (int l1 = 0; l1 < 2; l1++) {
									double d = i1 + 0.5D + (i2 - i1) * world.rand.nextFloat();
									double d1 = j1 + world.rand.nextFloat() * 0.2F;
									double d2 = k1 + 0.5D + (k2 - k1) * world.rand.nextFloat();
									world.spawnParticle("explode", d, d1, d2, 0.0D, 0.0D, 0.0D);
								}
							}
						}
					}
				}
			}
		}
		world.theProfiler.endSection();
	}

	public void updateDate() {
		newDate = 200;
	}

	public static void drawAlignmentText(FontRenderer f, int x, int y, String s, float alphaF) {
		drawBorderedText(f, x, y, s, 16772620, alphaF);
	}

	public static void drawBorderedText(FontRenderer f, int x, int y, String s, int color, float alphaF) {
		int alpha = (int) (alphaF * 255.0F);
		alpha = MathHelper.clamp_int(alpha, 4, 255);
		alpha <<= 24;
		f.drawString(s, x - 1, y - 1, 0x0 | alpha);
		f.drawString(s, x, y - 1, 0x0 | alpha);
		f.drawString(s, x + 1, y - 1, 0x0 | alpha);
		f.drawString(s, x + 1, y, 0x0 | alpha);
		f.drawString(s, x + 1, y + 1, 0x0 | alpha);
		f.drawString(s, x, y + 1, 0x0 | alpha);
		f.drawString(s, x - 1, y + 1, 0x0 | alpha);
		f.drawString(s, x - 1, y, 0x0 | alpha);
		f.drawString(s, x, y, color | alpha);
	}

	public static void drawConquestText(FontRenderer f, int x, int y, String s, boolean cleanse, float alphaF) {
		drawBorderedText(f, x, y, s, cleanse ? 16773846 : 14833677, alphaF);
	}

	public static void drawTexturedModalRect(double x, double y, int u, int v, int width, int height) {
		float f = 0.00390625F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0.0D, y + height, 0.0D, (u + 0) * f, (v + height) * f);
		tessellator.addVertexWithUV(x + width, y + height, 0.0D, (u + width) * f, (v + height) * f);
		tessellator.addVertexWithUV(x + width, y + 0.0D, 0.0D, (u + width) * f, (v + 0) * f);
		tessellator.addVertexWithUV(x + 0.0D, y + 0.0D, 0.0D, (u + 0) * f, (v + 0) * f);
		tessellator.draw();
	}

	public static boolean isBossActive() {
		return BossStatus.bossName != null && BossStatus.statusBarTime > 0;
	}

	public static void renderAlignmentBar(float alignment, boolean isOtherPlayer, GOTFaction faction, float x, float y, boolean renderFacName, boolean renderValue, boolean renderLimits, boolean renderLimitValues) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityClientPlayerMP entityClientPlayerMP = mc.thePlayer;
		GOTPlayerData clientPD = GOTLevelData.getData(entityClientPlayerMP);
		GOTFactionRank rank = faction.getRank(alignment);
		boolean pledged = clientPD.isPledgedTo(faction);
		GOTAlignmentTicker ticker = GOTAlignmentTicker.forFaction(faction);
		float alignMin = 0.0F;
		float alignMax = 0.0F;
		GOTFactionRank rankMin = null;
		GOTFactionRank rankMax = null;
		if (!rank.isDummyRank()) {
			alignMin = rank.alignment;
			rankMin = rank;
			GOTFactionRank nextRank = faction.getRankAbove(rank);
			if (nextRank != null && !nextRank.isDummyRank() && nextRank != rank) {
				alignMax = nextRank.alignment;
				rankMax = nextRank;
			} else {
				alignMax = rank.alignment * 10.0F;
				rankMax = rank;
				while (alignment >= alignMax) {
					alignMin = alignMax;
					alignMax = alignMin * 10.0F;
				}
			}
		} else {
			float firstRankAlign;
			GOTFactionRank firstRank = faction.getFirstRank();
			if (firstRank != null && !firstRank.isDummyRank()) {
				firstRankAlign = firstRank.alignment;
			} else {
				firstRankAlign = 10.0F;
			}
			if (Math.abs(alignment) < firstRankAlign) {
				alignMin = -firstRankAlign;
				alignMax = firstRankAlign;
				rankMin = GOTFactionRank.RANK_ENEMY;
				rankMax = firstRank != null && !firstRank.isDummyRank() ? firstRank : GOTFactionRank.RANK_NEUTRAL;
			} else if (alignment < 0.0F) {
				alignMax = -firstRankAlign;
				alignMin = alignMax * 10.0F;
				rankMin = rankMax = GOTFactionRank.RANK_ENEMY;
				while (alignment <= alignMin) {
					alignMax *= 10.0F;
					alignMin = alignMax * 10.0F;
				}
			} else {
				alignMin = firstRankAlign;
				alignMax = alignMin * 10.0F;
				rankMin = rankMax = GOTFactionRank.RANK_NEUTRAL;
				while (alignment >= alignMax) {
					alignMin = alignMax;
					alignMax = alignMin * 10.0F;
				}
			}
		}
		float ringProgress = (alignment - alignMin) / (alignMax - alignMin);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(GOTClientProxy.alignmentTexture);
		int barWidth = 232;
		int barHeight = 14;
		int activeBarWidth = 220;
		float[] factionColors = faction.getFactionRGB();
		GL11.glColor4f(factionColors[0], factionColors[1], factionColors[2], 1.0F);
		drawTexturedModalRect(x - barWidth / 2, y, 0, 14, barWidth, barHeight);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(x - barWidth / 2, y, 0, 0, barWidth, barHeight);
		float ringProgressAdj = (ringProgress - 0.5F) * 2.0F;
		int ringSize = 16;
		float ringX = x - ringSize / 2 + ringProgressAdj * activeBarWidth / 2.0F;
		float ringY = y + barHeight / 2 - ringSize / 2;
		int flashTick = ticker.flashTick;
		if (pledged) {
			drawTexturedModalRect(ringX, ringY, 16 * Math.round(flashTick / 3), 212, ringSize, ringSize);
		} else {
			drawTexturedModalRect(ringX, ringY, 16 * Math.round(flashTick / 3), 36, ringSize, ringSize);
		}
		if (faction.isPlayableAlignmentFaction()) {
			float alpha = 0.0F;
			boolean definedZone = false;
			if (faction.inControlZone(entityClientPlayerMP)) {
				alpha = 1.0F;
				definedZone = faction.inDefinedControlZone(entityClientPlayerMP);
			} else {
				alpha = faction.getControlZoneAlignmentMultiplier(entityClientPlayerMP);
				definedZone = true;
			}
			if (alpha > 0.0F) {
				int arrowSize = 14;
				int y0 = definedZone ? 60 : 88;
				int y1 = definedZone ? 74 : 102;
				GL11.glEnable(3042);
				OpenGlHelper.glBlendFunc(770, 771, 1, 0);
				GL11.glColor4f(factionColors[0], factionColors[1], factionColors[2], alpha);
				drawTexturedModalRect(x - barWidth / 2 - arrowSize, y, 0, y1, arrowSize, arrowSize);
				drawTexturedModalRect(x + barWidth / 2, y, arrowSize, y1, arrowSize, arrowSize);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
				drawTexturedModalRect(x - barWidth / 2 - arrowSize, y, 0, y0, arrowSize, arrowSize);
				drawTexturedModalRect(x + barWidth / 2, y, arrowSize, y0, arrowSize, arrowSize);
				GL11.glDisable(3042);
			}
		}
		FontRenderer fr = mc.fontRenderer;
		int textX = Math.round(x);
		int textY = Math.round(y + barHeight + 4.0F);
		if (renderLimits) {
			String sMin = rankMin.getShortNameWithGender(clientPD);
			String sMax = rankMax.getShortNameWithGender(clientPD);
			if (renderLimitValues) {
				sMin = StatCollector.translateToLocalFormatted("got.gui.factions.alignment.limits", sMin, GOTAlignmentValues.formatAlignForDisplay(alignMin));
				sMax = StatCollector.translateToLocalFormatted("got.gui.factions.alignment.limits", sMax, GOTAlignmentValues.formatAlignForDisplay(alignMax));
			}
			int limitsX = barWidth / 2 - 6;
			int xMin = Math.round(x - limitsX);
			int xMax = Math.round(x + limitsX);
			GL11.glPushMatrix();
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			drawAlignmentText(fr, xMin * 2 - fr.getStringWidth(sMin) / 2, textY * 2, sMin, 1.0F);
			drawAlignmentText(fr, xMax * 2 - fr.getStringWidth(sMax) / 2, textY * 2, sMax, 1.0F);
			GL11.glPopMatrix();
		}
		if (renderFacName) {
			String name = faction.factionName();
			drawAlignmentText(fr, textX - fr.getStringWidth(name) / 2, textY, name, 1.0F);
		}
		if (renderValue) {
			String alignS;
			float alignAlpha;
			int numericalTick = ticker.numericalTick;
			if (numericalTick > 0) {
				alignS = GOTAlignmentValues.formatAlignForDisplay(alignment);
				alignAlpha = GOTFunctions.triangleWave(numericalTick, 0.7F, 1.0F, 30.0F);
				int fadeTick = 15;
				if (numericalTick < fadeTick) {
					alignAlpha *= numericalTick / fadeTick;
				}
			} else {
				alignS = rank.getShortNameWithGender(clientPD);
				alignAlpha = 1.0F;
			}
			GL11.glEnable(3042);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			drawAlignmentText(fr, textX - fr.getStringWidth(alignS) / 2, textY + fr.FONT_HEIGHT + 3, alignS, alignAlpha);
			GL11.glDisable(3042);
		}
	}
}
