package got.client;

import java.util.*;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import got.GOT;
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
import got.common.world.biome.sothoryos.GOTBiomeSothoryosHell;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.biome.westeros.GOTBiomeFrostfangs;
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
import net.minecraft.entity.*;
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
	private static ResourceLocation portalOverlay = new ResourceLocation("got:textures/misc/frost_overlay.png");
	private static ResourceLocation mistOverlay = new ResourceLocation("got:textures/misc/mist_overlay.png");
	private static ResourceLocation frostOverlay = new ResourceLocation("got:textures/misc/frost_overlay.png");
	private static ResourceLocation burnOverlay = new ResourceLocation("got:textures/misc/burn_overlay.png");
	private static ResourceLocation wightOverlay = new ResourceLocation("got:textures/misc/wight.png");
	private static HashMap playersInPortals = new HashMap();
	private static int clientTick;
	private static float renderTick;
	private static GOTInvasionStatus watchedInvasion = new GOTInvasionStatus();
	private static GOTGuiNotificationDisplay notificationDisplay;
	private static GOTGuiMiniquestTracker miniquestTracker;
	private static boolean anyWightsViewed;
	private static int scrapTraderMisbehaveTick = 0;
	private static boolean renderMenuPrompt = false;
	private int bannerRepossessDisplayTick;
	private GOTAmbience ambienceTicker;
	private GuiScreen lastGuiOpen;
	private int mistTick;
	private int prevMistTick;
	private float mistFactor;
	private float sunGlare;
	private float prevSunGlare;
	private float rainFactor;
	private float prevRainFactor;
	private int alignmentXBase;
	private int alignmentYBase;
	private int alignmentXCurrent;
	private int alignmentYCurrent;
	private int alignmentXPrev;
	private int alignmentYPrev;
	private boolean firstAlignmentRender = true;
	private int frostTick;
	private int burnTick;
	private int drunkennessDirection = 1;
	private int newDate = 0;
	private int prevWightLookTick = 0;
	private int wightLookTick = 0;
	private int prevWightNearTick = 0;
	private int wightNearTick = 0;
	private float[] storedLightTable;
	private int storedScrapID;
	private boolean addedClientPoisonEffect = false;
	private GOTMusicTrack lastTrack = null;
	private int musicTrackTick = 0;
	private boolean cancelItemHighlight = false;
	private ItemStack lastHighlightedItemstack;
	private boolean wasHoldingBannerWithExistingProtection;

	private String highlightedItemstackName;

	public GOTTickHandlerClient() {
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
		ambienceTicker = new GOTAmbience();
		setNotificationDisplay(new GOTGuiNotificationDisplay());
		setMiniquestTracker(new GOTGuiMiniquestTracker());
	}

	private boolean fancyGraphics(int optifineSetting, Minecraft minecraft) {
		if (optifineSetting == 0) {
			return minecraft.gameSettings.fancyGraphics;
		}
		return optifineSetting == 2;
	}

	@SubscribeEvent
	public void getItemTooltip(ItemTooltipEvent event) {
		int armorProtect;
		int i;
		String currentOwner;
		List<String> previousOwners;
		String squadron;
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
		squadron = GOTSquadrons.getSquadron(itemstack);
		if (itemstack.getItem() instanceof GOTSquadrons.SquadronItem && !StringUtils.isNullOrEmpty(squadron)) {
			ArrayList<String> newTooltip = new ArrayList<>();
			newTooltip.add(tooltip.get(0));
			newTooltip.add(StatCollector.translateToLocalFormatted("item.got.generic.squadron", squadron));
			for (i = 1; i < tooltip.size(); ++i) {
				newTooltip.add(tooltip.get(i));
			}
			tooltip.clear();
			tooltip.addAll(newTooltip);
		}
		if (GOTWeaponStats.isMeleeWeapon(itemstack)) {
			int dmgIndex = -1;
			for (int i2 = 0; i2 < tooltip.size(); ++i2) {
				String s = tooltip.get(i2);
				if (!s.startsWith(EnumChatFormatting.BLUE.toString())) {
					continue;
				}
				dmgIndex = i2;
				break;
			}
			if (dmgIndex >= 0) {
				ArrayList<String> newTooltip = new ArrayList<>();
				for (i = 0; i <= dmgIndex - 1; ++i) {
					newTooltip.add(tooltip.get(i));
				}
				float meleeDamage = GOTWeaponStats.getMeleeDamageBonus(itemstack);
				newTooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("got.weaponstat.meleeDamage", Float.valueOf(meleeDamage)));
				float meleeSpeed = GOTWeaponStats.getMeleeSpeed(itemstack);
				int pcSpeed = Math.round(meleeSpeed * 100.0f);
				newTooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("got.weaponstat.meleeSpeed", pcSpeed));
				float reach = GOTWeaponStats.getMeleeReachFactor(itemstack);
				int pcReach = Math.round(reach * 100.0f);
				newTooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("got.weaponstat.reach", pcReach));
				int kb = GOTWeaponStats.getTotalKnockback(itemstack);
				if (kb > 0) {
					newTooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("got.weaponstat.kb", kb));
				}
				for (int i3 = dmgIndex + 1; i3 < tooltip.size(); ++i3) {
					newTooltip.add(tooltip.get(i3));
				}
				tooltip.clear();
				tooltip.addAll(newTooltip);
			}
		}
		if (GOTWeaponStats.isRangedWeapon(itemstack)) {
			int kb;
			float damage;
			tooltip.add("");
			float drawSpeed = GOTWeaponStats.getRangedSpeed(itemstack);
			if (drawSpeed > 0.0f) {
				int pcSpeed = Math.round(drawSpeed * 100.0f);
				tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted("got.weaponstat.rangedSpeed", pcSpeed));
			}
			damage = GOTWeaponStats.getRangedDamageFactor(itemstack, false);
			if (damage > 0.0f) {
				int pcDamage = Math.round(damage * 100.0f);
				tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted("got.weaponstat.rangedDamage", pcDamage));
				if (itemstack.getItem() instanceof ItemBow || itemstack.getItem() instanceof GOTItemCrossbow) {
					float range = GOTWeaponStats.getRangedDamageFactor(itemstack, true);
					int pcRange = Math.round(range * 100.0f);
					tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted("got.weaponstat.range", pcRange));
				}
			}
			kb = GOTWeaponStats.getRangedKnockback(itemstack);
			if (kb > 0) {
				tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted("got.weaponstat.kb", kb));
			}
		}
		if (GOTWeaponStats.isPoisoned(itemstack)) {
			tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted("got.weaponstat.poison"));
		}
		armorProtect = GOTWeaponStats.getArmorProtection(itemstack);
		if (armorProtect > 0) {
			tooltip.add("");
			int pcProtection = Math.round(armorProtect / 25.0f * 100.0f);
			tooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("got.weaponstat.protection", armorProtect, pcProtection));
		}
		if (!enchantments.isEmpty()) {
			tooltip.add("");
			ArrayList<String> enchGood = new ArrayList<>();
			ArrayList<String> enchBad = new ArrayList<>();
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
		currentOwner = GOTItemOwnership.getCurrentOwner(itemstack);
		if (currentOwner != null) {
			tooltip.add("");
			String ownerFormatted = StatCollector.translateToLocalFormatted("item.got.generic.currentOwner", currentOwner);
			List ownerLines = fontRenderer.listFormattedStringToWidth(ownerFormatted, 150);
			for (int i4 = 0; i4 < ownerLines.size(); ++i4) {
				String line = (String) ownerLines.get(i4);
				if (i4 > 0) {
					line = "  " + line;
				}
				tooltip.add(line);
			}
		}
		previousOwners = GOTItemOwnership.getPreviousOwners(itemstack);
		if (!previousOwners.isEmpty()) {
			tooltip.add("");
			ArrayList<String> ownerLines = new ArrayList<>();
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
			for (int i5 = 0; i5 < ownerLines.size(); ++i5) {
				String line = ownerLines.get(i5);
				if (i5 > 0) {
					line = "  " + line;
				}
				tooltip.add(line);
			}
		}
		if (IPickpocketable.Helper.isPickpocketed(itemstack)) {
			tooltip.add(EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("item.got.generic.stolen"));
		}
		if (itemstack.getItem() == Item.getItemFromBlock(Blocks.monster_egg)) {
			tooltip.set(0, EnumChatFormatting.RED + tooltip.get(0));
		}

		if (itemstack.getItem() instanceof GOTMaterialFinder) {
			if ((GOTMaterialFinder) itemstack.getItem() != GOTRegistry.baelishDagger && (((GOTMaterialFinder) itemstack.getItem()).getMaterial() == GOTMaterial.VALYRIAN || ((GOTMaterialFinder) itemstack.getItem()).getMaterial() == GOTMaterial.OBSIDIAN)) {
				tooltip.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("item.got.antiwalker"));
			}
			if ((GOTMaterialFinder) itemstack.getItem() == GOTRegistry.bericSword) {
				tooltip.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("item.got.antiwight"));
			}
			if ((GOTMaterialFinder) itemstack.getItem() == GOTRegistry.crowbar || (GOTMaterialFinder) itemstack.getItem() == GOTRegistry.baelishDagger) {
				tooltip.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("item.got.antiking"));
			}
		}
	}

	public float getWightLookFactor() {
		float f = prevWightLookTick + (wightLookTick - prevWightLookTick) * getRenderTick();
		f /= 100.0f;
		return f;
	}

	public boolean isCancelItemHighlight() {
		return cancelItemHighlight;
	}

	private boolean isGamePaused(Minecraft mc) {
		return mc.isSingleplayer() && mc.currentScreen != null && mc.currentScreen.doesGuiPauseGame() && !mc.getIntegratedServer().getPublic();
	}

	public void onBurnDamage() {
		burnTick = 40;
	}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		block74: {
			Minecraft minecraft;
			GuiScreen guiscreen;
			block75: {
				EntityClientPlayerMP entityplayer;
				WorldClient world;
				block76: {
					block77: {
						minecraft = Minecraft.getMinecraft();
						entityplayer = minecraft.thePlayer;
						world = minecraft.theWorld;
						if (event.phase == TickEvent.Phase.START) {
							setClientTick(getClientTick() + 1);
							if (GOTConfig.isFixRenderDistance() && !FMLClientHandler.instance().hasOptifine()) {
								GameSettings gs = Minecraft.getMinecraft().gameSettings;
								int renderDistance = gs.renderDistanceChunks;
								if (renderDistance > 16) {
									gs.renderDistanceChunks = renderDistance;
									gs.saveOptions();
									GOTLog.getLogger().info("Hummel009: Render distance was above 16 - set to 16 to prevent a vanilla crash");
								}
							}
							if (!GOTModChecker.hasWeather2() && !GOTModChecker.hasLOTR() && minecraft.entityRenderer != null && !(minecraft.entityRenderer instanceof GOTEntityRenderer)) {
								minecraft.entityRenderer = new GOTEntityRenderer(minecraft, minecraft.getResourceManager());
								((IReloadableResourceManager) minecraft.getResourceManager()).registerReloadListener(minecraft.entityRenderer);
								FMLLog.info("Hummel009: Successfully replaced entityrenderer");
							}
						}
						if (event.phase != TickEvent.Phase.END) {
							break block74;
						}
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
							} catch (Exception field) {
							}
							GOTBlockLeavesBase.setAllGraphicsLevels(fancyGraphics(optifineSetting, minecraft));
						} else {
							GOTBlockLeavesBase.setAllGraphicsLevels(minecraft.gameSettings.fancyGraphics);
						}
						if (entityplayer == null || world == null) {
							break block75;
						}
						if (GOTConfig.isCheckUpdates() && !GOT.isDevMode()) {
							GOTVersionChecker.checkForUpdates();
						}
						if (isGamePaused(minecraft)) {
							break block76;
						}
						getMiniquestTracker().update(minecraft, entityplayer);
						GOTAlignmentTicker.updateAll(entityplayer, false);
						getWatchedInvasion().tick();
						boolean isHoldingBannerWithExistingProtection = GOTItemBanner.isHoldingBannerWithExistingProtection(entityplayer);
						bannerRepossessDisplayTick = isHoldingBannerWithExistingProtection && !wasHoldingBannerWithExistingProtection ? 60 : --bannerRepossessDisplayTick;
						wasHoldingBannerWithExistingProtection = isHoldingBannerWithExistingProtection;
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
						if (viewer.posY >= 72.0 && biome instanceof GOTBiomeFrostfangs && world.canBlockSeeTheSky(i, j, k) && world.getSavedLightValue(EnumSkyBlock.Block, i, j, k) < 7) {
							if (mistTick < 80) {
								++mistTick;
							}
						} else if (mistTick > 0) {
							--mistTick;
						}
						if (frostTick > 0) {
							--frostTick;
						}
						if (burnTick > 0) {
							--burnTick;
						}
						prevWightLookTick = wightLookTick;
						if (isAnyWightsViewed()) {
							if (wightLookTick < 100) {
								++wightLookTick;
							}
						} else if (wightLookTick > 0) {
							--wightLookTick;
						}
						prevWightNearTick = wightNearTick;
						double wightRange = 32.0;
						List nearbyWights = world.getEntitiesWithinAABB(GOTEntityBarrowWight.class, viewer.boundingBox.expand(wightRange, wightRange, wightRange));
						if (!nearbyWights.isEmpty()) {
							if (wightNearTick < 100) {
								++wightNearTick;
							}
						} else if (wightNearTick > 0) {
							--wightNearTick;
						}
						if (GOTConfig.isEnableSunFlare() && world.provider instanceof GOTWorldProvider && !world.provider.hasNoSky) {
							prevSunGlare = sunGlare;
							MovingObjectPosition look = viewer.rayTrace(10000.0, getRenderTick());
							boolean lookingAtSky = look == null || look.typeOfHit == MovingObjectPosition.MovingObjectType.MISS;
							boolean biomeHasSun = true;
							if (biome instanceof GOTBiome) {
								biomeHasSun = ((GOTBiome) biome).hasSky();
							}
							float sunPitch = world.getCelestialAngle(getRenderTick()) * 360.0f - 90.0f;
							float sunYaw = 90.0f;
							float yc = MathHelper.cos((float) Math.toRadians(-sunYaw - 180.0f));
							float ys = MathHelper.sin((float) Math.toRadians(-sunYaw - 180.0f));
							float pc = -MathHelper.cos((float) Math.toRadians(-sunPitch));
							float ps = MathHelper.sin((float) Math.toRadians(-sunPitch));
							Vec3 sunVec = Vec3.createVectorHelper(ys * pc, ps, yc * pc);
							Vec3 lookVec = viewer.getLook(getRenderTick());
							double cos = lookVec.dotProduct(sunVec) / (lookVec.lengthVector() * sunVec.lengthVector());
							float cosThreshold = 0.95f;
							float cQ = ((float) cos - cosThreshold) / (1.0f - cosThreshold);
							cQ = Math.max(cQ, 0.0f);
							float brightness = world.getSunBrightness(getRenderTick());
							float brightnessThreshold = 0.7f;
							float bQ = (brightness - brightnessThreshold) / (1.0f - brightnessThreshold);
							bQ = Math.max(bQ, 0.0f);
							float maxGlare = cQ * bQ;
							if (maxGlare > 0.0f && lookingAtSky && !world.isRaining() && biomeHasSun) {
								if (sunGlare < maxGlare) {
									sunGlare += 0.1f * maxGlare;
									sunGlare = Math.min(sunGlare, maxGlare);
								} else if (sunGlare > maxGlare) {
									sunGlare -= 0.02f;
									sunGlare = Math.max(sunGlare, maxGlare);
								}
							} else {
								if (sunGlare > 0.0f) {
									sunGlare -= 0.02f;
								}
								sunGlare = Math.max(sunGlare, 0.0f);
							}
						} else {
							sunGlare = 0.0f;
							prevSunGlare = 0.0f;
						}
						prevRainFactor = rainFactor;
						if (world.isRaining()) {
							if (rainFactor < 1.0f) {
								rainFactor += 0.008333334f;
								rainFactor = Math.min(rainFactor, 1.0f);
							}
						} else if (rainFactor > 0.0f) {
							rainFactor -= 0.0016666667f;
							rainFactor = Math.max(rainFactor, 0.0f);
						}
						if (minecraft.gameSettings.particleSetting < 2) {
							spawnEnvironmentFX(entityplayer, world);
						}
						GOTClientProxy.getCustomEffectRenderer().updateEffects();
						if (minecraft.renderViewEntity.isPotionActive(Potion.confusion.id)) {
							float drunkenness = minecraft.renderViewEntity.getActivePotionEffect(Potion.confusion).getDuration();
							drunkenness /= 20.0f;
							if (drunkenness > 100.0f) {
								drunkenness = 100.0f;
							}
							minecraft.renderViewEntity.rotationYaw += drunkennessDirection * drunkenness / 20.0f;
							minecraft.renderViewEntity.rotationPitch += MathHelper.cos(minecraft.renderViewEntity.ticksExisted / 10.0f) * drunkenness / 20.0f;
							if (world.rand.nextInt(100) == 0) {
								drunkennessDirection *= -1;
							}
						}
						if (newDate > 0) {
							--newDate;
						}
						if (GOTConfig.isEnableAmbience()) {
							ambienceTicker.updateAmbience(world, entityplayer);
						}
						if (getScrapTraderMisbehaveTick() <= 0) {
							break block77;
						}
						if (setScrapTraderMisbehaveTick(getScrapTraderMisbehaveTick() - 1) > 0) {
							break block76;
						}
						world.provider.lightBrightnessTable = Arrays.copyOf(storedLightTable, storedLightTable.length);
						Entity scrap = world.getEntityByID(storedScrapID);
						if (scrap == null) {
							break block76;
						}
						scrap.ignoreFrustumCheck = false;
						break block76;
					}
					MovingObjectPosition target = minecraft.objectMouseOver;
					if (target != null && target.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && target.entityHit instanceof GOTEntityScrapTrader) {
						GOTEntityScrapTrader scrap = (GOTEntityScrapTrader) target.entityHit;
						if (minecraft.currentScreen == null && world.rand.nextInt(50000) == 0) {
							setScrapTraderMisbehaveTick(400);
							scrap.ignoreFrustumCheck = true;
							storedScrapID = scrap.getEntityId();
							float[] lightTable = world.provider.lightBrightnessTable;
							storedLightTable = Arrays.copyOf(lightTable, lightTable.length);
							Arrays.fill(lightTable, 1.0E-7f);
						}
					}
				}
				if ((entityplayer.dimension == 0 || entityplayer.dimension == GOTDimension.GAME_OF_THRONES.getDimensionID()) && getPlayersInPortals().containsKey(entityplayer)) {
					int i;
					List portals = world.getEntitiesWithinAABB(GOTEntityPortal.class, entityplayer.boundingBox.expand(8.0, 8.0, 8.0));
					boolean inPortal = false;
					for (i = 0; i < portals.size(); ++i) {
						GOTEntityPortal portal = (GOTEntityPortal) portals.get(i);
						if (!portal.boundingBox.intersectsWith(entityplayer.boundingBox)) {
							continue;
						}
						inPortal = true;
						break;
					}
					if (inPortal) {
						i = (Integer) getPlayersInPortals().get(entityplayer);
						i++;
						getPlayersInPortals().put(entityplayer, i);
						if (i >= 100) {
							minecraft.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("portal.trigger"), world.rand.nextFloat() * 0.4f + 0.8f));
							getPlayersInPortals().remove(entityplayer);
						}
					} else {
						getPlayersInPortals().remove(entityplayer);
					}
				}
			}
			GOTClientProxy.getMusicHandler().update();
			if (GOTConfig.isDisplayMusicTrack()) {
				GOTMusicTrack nowPlaying = GOTMusicTicker.getCurrentTrack();
				if (nowPlaying != lastTrack) {
					lastTrack = nowPlaying;
					musicTrackTick = 200;
				}
				if (lastTrack != null && musicTrackTick > 0) {
					--musicTrackTick;
				}
			}
			guiscreen = minecraft.currentScreen;
			if (guiscreen != null) {
				if (guiscreen instanceof GuiMainMenu && !(lastGuiOpen instanceof GuiMainMenu)) {
					GOTLevelData.setNeedsLoad(true);
					GOTTime.setNeedsLoad(true);
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
					getWatchedInvasion().clear();
				}
				lastGuiOpen = guiscreen;
			}
			setAnyWightsViewed(false);
		}
	}

	@SubscribeEvent
	public void onFogColors(EntityViewRenderEvent.FogColors event) {
		Minecraft mc = Minecraft.getMinecraft();
		WorldClient world = mc.theWorld;
		WorldProvider provider = world.provider;
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
		float usage = -1.0f;
		if (entityplayer.isUsingItem()) {
			float maxDrawTime = 0.0f;
			if (item instanceof GOTItemBow) {
				maxDrawTime = ((GOTItemBow) item).getMaxDrawTime();
			} else if (item instanceof GOTItemCrossbow) {
				maxDrawTime = ((GOTItemCrossbow) item).getMaxDrawTime();
			} else if (item instanceof GOTItemSpear) {
				maxDrawTime = ((GOTItemSpear) item).getMaxDrawTime();
			} else if (item instanceof GOTItemSarbacane) {
				maxDrawTime = ((GOTItemSarbacane) item).getMaxDrawTime();
			}
			if (maxDrawTime > 0.0f) {
				int i = entityplayer.getItemInUseDuration();
				usage = i / maxDrawTime;
				usage *= usage;
				usage = usage > 1.0f ? 1.0f : usage;
			}
		}
		if (GOTItemCrossbow.isLoaded(itemstack)) {
			usage = 1.0f;
		}
		if (usage >= 0.0f) {
			fov *= 1.0f - usage * 0.15f;
		}
		event.newfov = fov;
	}

	public void onFrostDamage() {
		frostTick = 80;
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		EntityClientPlayerMP clientPlayer;
		EntityPlayer player = event.player;
		if (event.phase == TickEvent.Phase.END && player instanceof EntityClientPlayerMP && (clientPlayer = (EntityClientPlayerMP) player).isRiding()) {
			GOTMountFunctions.sendControlToServer(clientPlayer);
		}
	}

	@SubscribeEvent
	public void onPostRenderGameOverlay(RenderGameOverlayEvent.Post event) {
		Minecraft mc = Minecraft.getMinecraft();
		WorldClient world = mc.theWorld;
		EntityClientPlayerMP entityplayer = mc.thePlayer;
		GuiIngame guiIngame = mc.ingameGUI;
		if (world != null && entityplayer != null) {
			if (event.type == RenderGameOverlayEvent.ElementType.ALL && lastHighlightedItemstack != null) {
				if (highlightedItemstackName != null) {
					lastHighlightedItemstack.setStackDisplayName(highlightedItemstackName);
				} else {
					lastHighlightedItemstack.func_135074_t();
				}
				lastHighlightedItemstack = null;
				highlightedItemstackName = null;
			}
			if (event.type == RenderGameOverlayEvent.ElementType.BOSSHEALTH && getWatchedInvasion().isActive()) {
				GL11.glEnable(3042);
				FontRenderer fr = mc.fontRenderer;
				ScaledResolution scaledresolution = event.resolution;
				int width = scaledresolution.getScaledWidth();
				int barWidth = 182;
				int remainingWidth = (int) (getWatchedInvasion().getHealth() * (barWidth - 2));
				int barHeight = 5;
				int barX = width / 2 - barWidth / 2;
				int barY = 12;
				if (GOTTickHandlerClient.isBossActive()) {
					barY += 20;
				}
				mc.getTextureManager().bindTexture(GOTClientProxy.getAlignmentTexture());
				guiIngame.drawTexturedModalRect(barX, barY, 64, 64, barWidth, barHeight);
				if (remainingWidth > 0) {
					float[] rgb = getWatchedInvasion().getRGB();
					GL11.glColor4f(rgb[0], rgb[1], rgb[2], 1.0f);
					guiIngame.drawTexturedModalRect(barX + 1, barY + 1, 65, 70, remainingWidth, barHeight - 2);
				}
				String s = getWatchedInvasion().getTitle();
				fr.drawStringWithShadow(s, width / 2 - fr.getStringWidth(s) / 2, barY - 10, 16777215);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				mc.getTextureManager().bindTexture(Gui.icons);
				GL11.glDisable(3042);
			}
			if (event.type == RenderGameOverlayEvent.ElementType.HEALTH && addedClientPoisonEffect) {
				entityplayer.removePotionEffectClient(Potion.poison.id);
				addedClientPoisonEffect = false;
			}
			if (event.type == RenderGameOverlayEvent.ElementType.TEXT && bannerRepossessDisplayTick > 0) {
				String text = StatCollector.translateToLocalFormatted("item.got.banner.toggleRepossess", GameSettings.getKeyDisplayString(mc.gameSettings.keyBindSneak.getKeyCode()));
				int fadeAtTick = 10;
				int opacity = (int) (bannerRepossessDisplayTick * 255.0f / fadeAtTick);
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
		WorldClient world = mc.theWorld;
		EntityClientPlayerMP entityplayer = mc.thePlayer;
		float partialTicks = event.partialTicks;
		GuiIngame guiIngame = mc.ingameGUI;
		if (world != null && entityplayer != null) {
			ScaledResolution resolution;
			boolean enchantingDisabled;
			int height;
			int width;
			if (event.type == RenderGameOverlayEvent.ElementType.ALL) {
				mc.theWorld.theProfiler.startSection("got_fixHighlightedItemName");
				ItemStack itemstack = GOTReflectionClient.getHighlightedItemStack(guiIngame);
				if (itemstack != null && !itemstack.hasDisplayName() && !GOTEnchantmentHelper.getEnchantList(itemstack).isEmpty()) {
					lastHighlightedItemstack = itemstack;
					highlightedItemstackName = itemstack.hasDisplayName() ? itemstack.getDisplayName() : null;
					itemstack.setStackDisplayName(GOTEnchantmentHelper.getFullEnchantedName(itemstack, itemstack.getDisplayName()));
				}
				mc.theWorld.theProfiler.endSection();
			}
			if (event.type == RenderGameOverlayEvent.ElementType.HELMET) {
				int i;
				if (sunGlare > 0.0f && mc.gameSettings.thirdPersonView == 0) {
					float brightness = prevSunGlare + (sunGlare - prevSunGlare) * partialTicks;
					brightness *= 1.0f;
					renderOverlay(null, brightness, mc, null);
				}
				if (getPlayersInPortals().containsKey(entityplayer) && (i = (Integer) getPlayersInPortals().get(entityplayer)) > 0) {
					renderOverlay(null, 0.1f + i / 100.0f * 0.6f, mc, portalOverlay);
				}
				if (GOTConfig.isEnableFrostfangsMist()) {
					float mistTickF = prevMistTick + (mistTick - prevMistTick) * partialTicks;
					float mistFactorY = (float) entityplayer.posY / 256.0f;
					mistTickF /= 80.0f;
					mistFactor = mistTickF * mistFactorY;
					if (mistFactor > 0.0f) {
						renderOverlay(null, mistFactor * 0.75f, mc, mistOverlay);
					}
				} else {
					mistFactor = 0.0f;
				}
				if (frostTick > 0) {
					renderOverlay(null, frostTick / 80.0f * 0.9f, mc, frostOverlay);
				}
				if (burnTick > 0) {
					renderOverlay(null, burnTick / 40.0f * 0.6f, mc, burnOverlay);
				}
				if (wightLookTick > 0) {
					renderOverlay(null, wightLookTick / 100.0f * 0.95f, mc, wightOverlay);
				}
			}
			if (event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
				GOTEntitySpiderBase spider;
				if (GOTConfig.isMeleeAttackMeter()) {
					GOTAttackTiming.renderAttackMeter(event.resolution, partialTicks);
				}
				if (entityplayer.ridingEntity instanceof GOTEntitySpiderBase && (spider = (GOTEntitySpiderBase) entityplayer.ridingEntity).shouldRenderClimbingMeter()) {
					mc.getTextureManager().bindTexture(Gui.icons);
					GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
					GL11.glDisable(3042);
					mc.mcProfiler.startSection("spiderClimb");
					resolution = event.resolution;
					width = resolution.getScaledWidth();
					height = resolution.getScaledHeight();
					float charge = spider.getClimbFractionRemaining();
					int x = width / 2 - 91;
					int filled = (int) (charge * 183.0f);
					int top = height - 32 + 3;
					guiIngame.drawTexturedModalRect(x, top, 0, 84, 182, 5);
					if (filled > 0) {
						guiIngame.drawTexturedModalRect(x, top, 0, 89, filled, 5);
					}
					GL11.glEnable(3042);
					mc.mcProfiler.endSection();
					GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				}
			}
			if (event.type == RenderGameOverlayEvent.ElementType.HEALTH && entityplayer.isPotionActive(GOTPoisonedDrinks.killingPoison) && !entityplayer.isPotionActive(Potion.poison)) {
				entityplayer.addPotionEffect(new PotionEffect(Potion.poison.id, 20));
				addedClientPoisonEffect = true;
			}
			enchantingDisabled = !GOTLevelData.isClientsideThisServerEnchanting() && world.provider instanceof GOTWorldProvider;
			if (event.type == RenderGameOverlayEvent.ElementType.EXPERIENCE && enchantingDisabled) {
				event.setCanceled(true);
				return;
			}
			if (event.type == RenderGameOverlayEvent.ElementType.ALL && enchantingDisabled && entityplayer.ridingEntity == null) {
				GuiIngameForge.left_height -= 6;
				GuiIngameForge.right_height -= 6;
			}
			if (event.type == RenderGameOverlayEvent.ElementType.ARMOR) {
				event.setCanceled(true);
				resolution = event.resolution;
				width = resolution.getScaledWidth();
				height = resolution.getScaledHeight();
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
		WorldClient world = mc.theWorld;
		WorldProvider provider = world.provider;
		int i = MathHelper.floor_double(viewer.posX);
		MathHelper.floor_double(viewer.boundingBox.minY);
		int k = MathHelper.floor_double(viewer.posZ);
		BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
		float farPlane = event.farPlaneDistance;
		int fogMode = event.fogMode;
		if (provider instanceof GOTWorldProvider) {
			float rain;
			GOTBiome gotbiome = (GOTBiome) biome;
			float[] fogStartEnd = ((GOTWorldProvider) provider).modifyFogIntensity(farPlane, fogMode);
			float fogStart = fogStartEnd[0];
			float fogEnd = fogStartEnd[1];
			if ((gotbiome.getEnableRain() || gotbiome.getEnableSnow()) && (rain = prevRainFactor + (rainFactor - prevRainFactor) * getRenderTick()) > 0.0f) {
				float rainOpacityStart = 0.95f;
				float rainOpacityEnd = 0.2f;
				fogStart -= fogStart * (rain * rainOpacityStart);
				fogEnd -= fogEnd * (rain * rainOpacityEnd);
			}
			if (mistFactor > 0.0f) {
				float mistOpacityStart = 0.95f;
				float mistOpacityEnd = 0.7f;
				fogStart -= fogStart * (mistFactor * mistOpacityStart);
				fogEnd -= fogEnd * (mistFactor * mistOpacityEnd);
			}
			float wightFactor = prevWightNearTick + (wightNearTick - prevWightNearTick) * getRenderTick();
			wightFactor /= 100.0f;
			if (wightFactor > 0.0f) {
				float wightOpacityStart = 0.97f;
				float wightOpacityEnd = 0.75f;
				fogStart -= fogStart * (wightFactor * wightOpacityStart);
				fogEnd -= fogEnd * (wightFactor * wightOpacityEnd);
			}
			GL11.glFogf(2915, fogStart);
			GL11.glFogf(2916, fogEnd);
		}
	}

	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event) {
		Minecraft minecraft = Minecraft.getMinecraft();
		EntityClientPlayerMP entityplayer = minecraft.thePlayer;
		WorldClient world = minecraft.theWorld;
		if (event.phase == TickEvent.Phase.START) {
			GuiIngame guiIngame;
			setRenderTick(event.renderTickTime);
			guiIngame = minecraft.ingameGUI;
			if (isCancelItemHighlight() && GOTReflectionClient.getHighlightedItemTicks(guiIngame) > 0) {
				GOTReflectionClient.setHighlightedItemTicks(guiIngame, 0);
				setCancelItemHighlight(false);
			}
		}
		if (event.phase == TickEvent.Phase.END) {
			if (entityplayer != null && world != null) {
				ScaledResolution resolution;
				if (world.provider instanceof GOTWorldProvider || GOTConfig.isAlwaysShowAlignment()) {
					alignmentXPrev = alignmentXCurrent;
					alignmentYPrev = alignmentYCurrent;
					alignmentXCurrent = alignmentXBase;
					int yMove = (int) ((alignmentYBase - -20) / 10.0f);
					boolean alignmentOnscreen = (minecraft.currentScreen == null || minecraft.currentScreen instanceof GOTGuiMessage) && !minecraft.gameSettings.keyBindPlayerList.getIsKeyPressed() && !minecraft.gameSettings.showDebugInfo;
					alignmentYCurrent = alignmentOnscreen ? Math.min(alignmentYCurrent + yMove, alignmentYBase) : Math.max(alignmentYCurrent - yMove, -20);
					renderAlignment(minecraft, getRenderTick());
					if (GOTConfig.isEnableOnscreenCompass() && minecraft.currentScreen == null && !minecraft.gameSettings.showDebugInfo) {
						GL11.glPushMatrix();
						resolution = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
						int i = resolution.getScaledWidth();
						resolution.getScaledHeight();
						int compassX = i - 60;
						int compassY = 40;
						GL11.glTranslatef(compassX, compassY, 0.0f);
						float rotation = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * event.renderTickTime;
						rotation = 180.0f - rotation;
						GOTModelCompass.getCompassModel().render(1.0f, rotation);
						GL11.glPopMatrix();
						if (GOTConfig.isCompassExtraInfo()) {
							BiomeGenBase biome;
							GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
							float scale = 0.5f;
							float invScale = 1.0f / scale;
							compassX = (int) (compassX * invScale);
							compassY = (int) (compassY * invScale);
							GL11.glScalef(scale, scale, scale);
							String coords = MathHelper.floor_double(entityplayer.posX) + ", " + MathHelper.floor_double(entityplayer.boundingBox.minY) + ", " + MathHelper.floor_double(entityplayer.posZ);
							FontRenderer fontRenderer = minecraft.fontRenderer;
							fontRenderer.drawString(coords, compassX - fontRenderer.getStringWidth(coords) / 2, compassY + 70, 16777215);
							int playerX = MathHelper.floor_double(entityplayer.posX);
							int playerZ = MathHelper.floor_double(entityplayer.posZ);
							biome = world.getBiomeGenForCoords(playerX, playerZ);
							if (GOTClientProxy.doesClientChunkExist(world, playerX, playerZ) && biome instanceof GOTBiome) {
								String biomeName = ((GOTBiome) biome).getBiomeDisplayName();
								fontRenderer.drawString(biomeName, compassX - fontRenderer.getStringWidth(biomeName) / 2, compassY - 70, 16777215);
							}
							GL11.glScalef(invScale, invScale, invScale);
						}
					}
				}
				float promptTick = getClientTick() + getRenderTick();
				float promptAlpha = GOTFunctions.triangleWave(promptTick, 0.5f, 1.0f, 80.0f);
				ArrayList<String> message = new ArrayList<>();
				if (entityplayer.dimension != GOTDimension.GAME_OF_THRONES.getDimensionID() && isRenderMenuPrompt() && minecraft.currentScreen == null) {
					message.add(StatCollector.translateToLocal("got.gui.help1"));
					message.add(StatCollector.translateToLocalFormatted("got.gui.help2", GameSettings.getKeyDisplayString(GOTKeyHandler.getKeyBindingReturn().getKeyCode())));
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
				if (entityplayer.dimension == GOTDimension.GAME_OF_THRONES.getDimensionID() && minecraft.currentScreen == null && newDate > 0) {
					int halfMaxDate = 100;
					float alpha;
					alpha = newDate > halfMaxDate ? (float) (200 - newDate) / (float) halfMaxDate : (float) newDate / (float) halfMaxDate;
					String date = GOTDate.AegonCalendar.getDate().getDateName(true);
					ScaledResolution resolution3 = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
					int i = resolution3.getScaledWidth();
					int j = resolution3.getScaledHeight();
					float scale = 1.5f;
					float invScale = 1.0f / scale;
					i = (int) (i * invScale);
					j = (int) (j * invScale);
					int x2 = (i - minecraft.fontRenderer.getStringWidth(date)) / 2;
					int y = (j - minecraft.fontRenderer.FONT_HEIGHT) * 2 / 5;
					GL11.glScalef(scale, scale, scale);
					GL11.glEnable(3042);
					OpenGlHelper.glBlendFunc(770, 771, 1, 0);
					minecraft.fontRenderer.drawString(date, x2, y, 16777215 + (GOTClientProxy.getAlphaInt(alpha) << 24));
					GL11.glDisable(3042);
					GL11.glScalef(invScale, invScale, invScale);
				}
				if (GOTConfig.isDisplayMusicTrack() && minecraft.currentScreen == null && lastTrack != null && musicTrackTick > 0) {
					ArrayList<String> lines = new ArrayList<>();
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
							++a;
						}
						authors.append(")");
						lines.add(authors.toString());
					}
					resolution = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
					int w = resolution.getScaledWidth();
					int h = resolution.getScaledHeight();
					int border = 20;
					int y = h - border - lines.size() * minecraft.fontRenderer.FONT_HEIGHT;
					float alpha = 1.0f;
					if (musicTrackTick >= 140) {
						alpha = (200 - musicTrackTick) / 60.0f;
					} else if (musicTrackTick <= 60) {
						alpha = musicTrackTick / 60.0f;
					}
					for (String line : lines) {
						int x = w - border - minecraft.fontRenderer.getStringWidth(line);
						minecraft.fontRenderer.drawString(line, x, y, 16777215 + (GOTClientProxy.getAlphaInt(alpha) << 24));
						y += minecraft.fontRenderer.FONT_HEIGHT;
					}
				}
			}
			getNotificationDisplay().updateWindow();
			if (GOTConfig.isEnableQuestTracker() && minecraft.currentScreen == null && !minecraft.gameSettings.showDebugInfo) {
				getMiniquestTracker().drawTracker(minecraft, entityplayer);
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
		GOTClientProxy.getCustomEffectRenderer().renderParticles(mc.renderViewEntity, f);
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
			GOTClientProxy.getCustomEffectRenderer().clearEffectsAndSetWorld(event.world);
		}
	}

	private void renderAlignment(Minecraft mc, float f) {
		EntityClientPlayerMP entityplayer = mc.thePlayer;
		GOTPlayerData pd = GOTLevelData.getData(entityplayer);
		GOTFaction viewingFac = pd.getViewingFaction();
		ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		int width = resolution.getScaledWidth();
		resolution.getScaledHeight();
		boolean boss = BossStatus.bossName != null && BossStatus.statusBarTime > 0;
		alignmentXBase = width / 2 + GOTConfig.getAlignmentXOffset();
		alignmentYBase = 4 + GOTConfig.getAlignmentYOffset();
		if (boss) {
			alignmentYBase += 20;
		}
		if (getWatchedInvasion().isActive()) {
			alignmentYBase += 20;
		}
		if (firstAlignmentRender) {
			GOTAlignmentTicker.updateAll(entityplayer, true);
			alignmentXPrev = alignmentXCurrent = alignmentXBase;
			alignmentYCurrent = -20;
			alignmentYPrev = -20;
			firstAlignmentRender = false;
		}
		float alignmentXF = alignmentXPrev + (alignmentXCurrent - alignmentXPrev) * f;
		float alignmentYF = alignmentYPrev + (alignmentYCurrent - alignmentYPrev) * f;
		boolean text = alignmentYCurrent == alignmentYBase;
		float alignment = GOTAlignmentTicker.forFaction(viewingFac).getInterpolatedAlignment(f);
		GOTTickHandlerClient.renderAlignmentBar(alignment, viewingFac, alignmentXF, alignmentYF, text, text, text, false);
	}

	private void renderOverlay(float[] rgb, float alpha, Minecraft mc, ResourceLocation texture) {
		ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		int width = resolution.getScaledWidth();
		int height = resolution.getScaledHeight();
		GL11.glEnable(3042);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(770, 771);
		if (rgb != null) {
			GL11.glColor4f(rgb[0], rgb[1], rgb[2], alpha);
		} else {
			GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
		}
		GL11.glDisable(3008);
		if (texture != null) {
			mc.getTextureManager().bindTexture(texture);
		} else {
			GL11.glDisable(3553);
		}
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(0.0, height, -90.0, 0.0, 1.0);
		tessellator.addVertexWithUV(width, height, -90.0, 1.0, 1.0);
		tessellator.addVertexWithUV(width, 0.0, -90.0, 1.0, 0.0);
		tessellator.addVertexWithUV(0.0, 0.0, -90.0, 0.0, 0.0);
		tessellator.draw();
		if (texture == null) {
			GL11.glEnable(3553);
		}
		GL11.glDepthMask(true);
		GL11.glEnable(2929);
		GL11.glEnable(3008);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	}

	public void setCancelItemHighlight(boolean cancelItemHighlight) {
		this.cancelItemHighlight = cancelItemHighlight;
	}

	private void spawnEnvironmentFX(EntityPlayer entityplayer, World world) {
		world.theProfiler.startSection("gotEnvironmentFX");
		int i = MathHelper.floor_double(entityplayer.posX);
		int j = MathHelper.floor_double(entityplayer.boundingBox.minY);
		int k = MathHelper.floor_double(entityplayer.posZ);
		int range = 16;
		for (int l = 0; l < 1000; ++l) {
			int i1 = i + world.rand.nextInt(range) - world.rand.nextInt(range);
			int j1 = j + world.rand.nextInt(range) - world.rand.nextInt(range);
			int k1 = k + world.rand.nextInt(range) - world.rand.nextInt(range);
			Block block = world.getBlock(i1, j1, k1);
			int meta = world.getBlockMetadata(i1, j1, k1);
			if (block.getMaterial() == Material.water) {
				BiomeGenBase biome = world.getBiomeGenForCoords(i1, k1);
				if (biome instanceof GOTBiomeShadowLand && world.rand.nextInt(20) == 0) {
					GOT.getProxy().spawnParticle("ulthosWater", i1 + world.rand.nextFloat(), j1 + 0.75, k1 + world.rand.nextFloat(), 0.0, 0.05, 0.0);
				}
				if (biome instanceof GOTBiomeValyriaSea && world.rand.nextInt(20) == 0) {
					GOT.getProxy().spawnParticle("ulthosWater", i1 + world.rand.nextFloat(), j1 + 0.75, k1 + world.rand.nextFloat(), 0.0, 0.05, 0.0);
				}
				if (biome instanceof GOTBiomeValyria && world.rand.nextInt(20) == 0) {
					GOT.getProxy().spawnParticle("ulthosWater", i1 + world.rand.nextFloat(), j1 + 0.75, k1 + world.rand.nextFloat(), 0.0, 0.05, 0.0);
				}
				if (biome instanceof GOTBiomeValyriaVolcano && world.rand.nextInt(20) == 0) {
					GOT.getProxy().spawnParticle("ulthosWater", i1 + world.rand.nextFloat(), j1 + 0.75, k1 + world.rand.nextFloat(), 0.0, 0.05, 0.0);
				}
				if (biome instanceof GOTBiomeSothoryosHell && world.rand.nextInt(40) == 0) {
					GOT.getProxy().spawnParticle("asshaiWater", i1 + world.rand.nextFloat(), j1 + 0.75, k1 + world.rand.nextFloat(), 0.0, 0.05, 0.0);
				}
			}
			if (block.getMaterial() != Material.water || meta == 0 || world.getBlock(i1, j1 - 1, k1).getMaterial() != Material.water) {
				continue;
			}
			for (int i2 = i1 - 1; i2 <= i1 + 1; ++i2) {
				for (int k2 = k1 - 1; k2 <= k1 + 1; ++k2) {
					Block adjBlock = world.getBlock(i2, j1 - 1, k2);
					int adjMeta = world.getBlockMetadata(i2, j1 - 1, k2);
					if (adjBlock.getMaterial() != Material.water || adjMeta != 0 || !world.isAirBlock(i2, j1, k2)) {
						continue;
					}
					for (int l1 = 0; l1 < 2; ++l1) {
						double d = i1 + 0.5 + (i2 - i1) * world.rand.nextFloat();
						double d1 = j1 + world.rand.nextFloat() * 0.2f;
						double d2 = k1 + 0.5 + (k2 - k1) * world.rand.nextFloat();
						world.spawnParticle("explode", d, d1, d2, 0.0, 0.0, 0.0);
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
		GOTTickHandlerClient.drawBorderedText(f, x, y, s, 16772620, alphaF);
	}

	private static void drawBorderedText(FontRenderer f, int x, int y, String s, int color, float alphaF) {
		int alpha = (int) (alphaF * 255.0f);
		alpha = MathHelper.clamp_int(alpha, 4, 255);
		alpha <<= 24;
		f.drawString(s, x - 1, y - 1, 0 | alpha);
		f.drawString(s, x, y - 1, 0 | alpha);
		f.drawString(s, x + 1, y - 1, 0 | alpha);
		f.drawString(s, x + 1, y, 0 | alpha);
		f.drawString(s, x + 1, y + 1, 0 | alpha);
		f.drawString(s, x, y + 1, 0 | alpha);
		f.drawString(s, x - 1, y + 1, 0 | alpha);
		f.drawString(s, x - 1, y, 0 | alpha);
		f.drawString(s, x, y, color | alpha);
	}

	public static void drawConquestText(FontRenderer f, int x, int y, String s, boolean cleanse, float alphaF) {
		GOTTickHandlerClient.drawBorderedText(f, x, y, s, cleanse ? 16773846 : 14833677, alphaF);
	}

	public static void drawTexturedModalRect(double x, double y, int u, int v, int width, int height) {
		float f = 0.00390625f;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0.0, y + height, 0.0, (u + 0) * f, (v + height) * f);
		tessellator.addVertexWithUV(x + width, y + height, 0.0, (u + width) * f, (v + height) * f);
		tessellator.addVertexWithUV(x + width, y + 0.0, 0.0, (u + width) * f, (v + 0) * f);
		tessellator.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (u + 0) * f, (v + 0) * f);
		tessellator.draw();
	}

	public static int getClientTick() {
		return clientTick;
	}

	public static GOTGuiMiniquestTracker getMiniquestTracker() {
		return miniquestTracker;
	}

	public static GOTGuiNotificationDisplay getNotificationDisplay() {
		return notificationDisplay;
	}

	public static HashMap getPlayersInPortals() {
		return playersInPortals;
	}

	public static float getRenderTick() {
		return renderTick;
	}

	public static int getScrapTraderMisbehaveTick() {
		return scrapTraderMisbehaveTick;
	}

	public static GOTInvasionStatus getWatchedInvasion() {
		return watchedInvasion;
	}

	public static boolean isAnyWightsViewed() {
		return anyWightsViewed;
	}

	private static boolean isBossActive() {
		return BossStatus.bossName != null && BossStatus.statusBarTime > 0;
	}

	public static boolean isRenderMenuPrompt() {
		return renderMenuPrompt;
	}

	public static void renderAlignmentBar(float alignment, GOTFaction faction, float x, float y, boolean renderFacName, boolean renderValue, boolean renderLimits, boolean renderLimitValues) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityClientPlayerMP entityplayer = mc.thePlayer;
		GOTPlayerData clientPD = GOTLevelData.getData(entityplayer);
		GOTFactionRank rank = faction.getRank(alignment);
		boolean pledged = clientPD.isPledgedTo(faction);
		GOTAlignmentTicker ticker = GOTAlignmentTicker.forFaction(faction);
		float alignMin;
		float alignMax;
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
				alignMax = rank.alignment * 10.0f;
				rankMax = rank;
				while (alignment >= alignMax) {
					alignMin = alignMax;
					alignMax = alignMin * 10.0f;
				}
			}
		} else {
			GOTFactionRank firstRank = faction.getFirstRank();
			float firstRankAlign = firstRank != null && !firstRank.isDummyRank() ? firstRank.alignment : 10.0f;
			if (Math.abs(alignment) < firstRankAlign) {
				alignMin = -firstRankAlign;
				alignMax = firstRankAlign;
				rankMin = GOTFactionRank.RANK_ENEMY;
				rankMax = firstRank != null && !firstRank.isDummyRank() ? firstRank : GOTFactionRank.RANK_NEUTRAL;
			} else if (alignment < 0.0f) {
				alignMax = -firstRankAlign;
				alignMin = alignMax * 10.0f;
				rankMin = rankMax = GOTFactionRank.RANK_ENEMY;
				while (alignment <= alignMin) {
					alignMax *= 10.0f;
					alignMin = alignMax * 10.0f;
				}
			} else {
				alignMin = firstRankAlign;
				alignMax = alignMin * 10.0f;
				rankMin = rankMax = GOTFactionRank.RANK_NEUTRAL;
				while (alignment >= alignMax) {
					alignMin = alignMax;
					alignMax = alignMin * 10.0f;
				}
			}
		}
		float ringProgress = (alignment - alignMin) / (alignMax - alignMin);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(GOTClientProxy.getAlignmentTexture());
		int barWidth = 232;
		int barHeight = 14;
		int activeBarWidth = 220;
		float[] factionColors = faction.getFactionRGB();
		GL11.glColor4f(factionColors[0], factionColors[1], factionColors[2], 1.0f);
		GOTTickHandlerClient.drawTexturedModalRect(x - barWidth / 2, y, 0, 14, barWidth, barHeight);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GOTTickHandlerClient.drawTexturedModalRect(x - barWidth / 2, y, 0, 0, barWidth, barHeight);
		float ringProgressAdj = (ringProgress - 0.5f) * 2.0f;
		int ringSize = 16;
		float ringX = x - ringSize / 2 + ringProgressAdj * activeBarWidth / 2.0f;
		float ringY = y + barHeight / 2 - ringSize / 2;
		int flashTick = ticker.getFlashTick();
		if (pledged) {
			GOTTickHandlerClient.drawTexturedModalRect(ringX, ringY, 16 * Math.round(flashTick / 3), 212, ringSize, ringSize);
		} else {
			GOTTickHandlerClient.drawTexturedModalRect(ringX, ringY, 16 * Math.round(flashTick / 3), 36, ringSize, ringSize);
		}
		if (faction.isPlayableAlignmentFaction()) {
			float alpha;
			boolean definedZone = false;
			if (faction.inControlZone(entityplayer)) {
				alpha = 1.0f;
				definedZone = faction.inDefinedControlZone(entityplayer);
			} else {
				alpha = faction.getControlZoneAlignmentMultiplier(entityplayer);
				definedZone = true;
			}
			if (alpha > 0.0f) {
				int arrowSize = 14;
				int y0 = definedZone ? 60 : 88;
				int y1 = definedZone ? 74 : 102;
				GL11.glEnable(3042);
				OpenGlHelper.glBlendFunc(770, 771, 1, 0);
				GL11.glColor4f(factionColors[0], factionColors[1], factionColors[2], alpha);
				int aboba = barWidth / 2;
				GOTTickHandlerClient.drawTexturedModalRect(x - aboba - arrowSize, y, 0, y1, arrowSize, arrowSize);
				GOTTickHandlerClient.drawTexturedModalRect(x + aboba, y, arrowSize, y1, arrowSize, arrowSize);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
				GOTTickHandlerClient.drawTexturedModalRect(x - aboba - arrowSize, y, 0, y0, arrowSize, arrowSize);
				GOTTickHandlerClient.drawTexturedModalRect(x + aboba, y, arrowSize, y0, arrowSize, arrowSize);
				GL11.glDisable(3042);
			}
		}
		FontRenderer fr = mc.fontRenderer;
		int textX = Math.round(x);
		int textY = Math.round(y + barHeight + 4.0f);
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
			GL11.glScalef(0.5f, 0.5f, 0.5f);
			GOTTickHandlerClient.drawAlignmentText(fr, xMin * 2 - fr.getStringWidth(sMin) / 2, textY * 2, sMin, 1.0f);
			GOTTickHandlerClient.drawAlignmentText(fr, xMax * 2 - fr.getStringWidth(sMax) / 2, textY * 2, sMax, 1.0f);
			GL11.glPopMatrix();
		}
		if (renderFacName) {
			String name = faction.factionName();
			GOTTickHandlerClient.drawAlignmentText(fr, textX - fr.getStringWidth(name) / 2, textY, name, 1.0f);
		}
		if (renderValue) {
			String alignS;
			float alignAlpha;
			int numericalTick = ticker.getNumericalTick();
			if (numericalTick > 0) {
				alignS = GOTAlignmentValues.formatAlignForDisplay(alignment);
				alignAlpha = GOTFunctions.triangleWave(numericalTick, 0.7f, 1.0f, 30.0f);
				int fadeTick = 15;
				if (numericalTick < fadeTick) {
					alignAlpha *= (float) numericalTick / (float) fadeTick;
				}
			} else {
				alignS = rank.getShortNameWithGender(clientPD);
				alignAlpha = 1.0f;
			}
			GL11.glEnable(3042);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GOTTickHandlerClient.drawAlignmentText(fr, textX - fr.getStringWidth(alignS) / 2, textY + fr.FONT_HEIGHT + 3, alignS, alignAlpha);
			GL11.glDisable(3042);
		}
	}

	public static void setAnyWightsViewed(boolean anyWightsViewed) {
		GOTTickHandlerClient.anyWightsViewed = anyWightsViewed;
	}

	public static void setClientTick(int clientTick) {
		GOTTickHandlerClient.clientTick = clientTick;
	}

	public static void setMiniquestTracker(GOTGuiMiniquestTracker miniquestTracker) {
		GOTTickHandlerClient.miniquestTracker = miniquestTracker;
	}

	public static void setNotificationDisplay(GOTGuiNotificationDisplay notificationDisplay) {
		GOTTickHandlerClient.notificationDisplay = notificationDisplay;
	}

	public static void setPlayersInPortals(HashMap playersInPortals) {
		GOTTickHandlerClient.playersInPortals = playersInPortals;
	}

	public static void setRenderMenuPrompt(boolean renderMenuPrompt) {
		GOTTickHandlerClient.renderMenuPrompt = renderMenuPrompt;
	}

	public static void setRenderTick(float renderTick) {
		GOTTickHandlerClient.renderTick = renderTick;
	}

	public static int setScrapTraderMisbehaveTick(int scrapTraderMisbehaveTick) {
		GOTTickHandlerClient.scrapTraderMisbehaveTick = scrapTraderMisbehaveTick;
		return scrapTraderMisbehaveTick;
	}

	public static void setWatchedInvasion(GOTInvasionStatus watchedInvasion) {
		GOTTickHandlerClient.watchedInvasion = watchedInvasion;
	}

}