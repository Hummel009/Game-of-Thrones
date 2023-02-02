package got.common.util;

import java.io.*;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.*;

import got.common.block.other.*;
import got.common.database.*;
import got.common.database.GOTInvasions.InvasionSpawnEntry;
import got.common.entity.other.*;
import got.common.entity.other.GOTUnitTradeEntry.PledgeType;
import got.common.entity.westeros.legendary.captain.*;
import got.common.entity.westeros.legendary.deco.*;
import got.common.entity.westeros.legendary.quest.*;
import got.common.entity.westeros.legendary.reborn.GOTEntityJonSnow.JonSnowLife1;
import got.common.entity.westeros.legendary.reborn.GOTEntityLancelLannister.LancelLannisterNormal;
import got.common.entity.westeros.legendary.trader.*;
import got.common.entity.westeros.legendary.warrior.*;
import got.common.faction.*;
import got.common.item.other.GOTItemBanner.BannerType;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.GOTBiomeDecorator.*;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.biome.variant.GOTBiomeVariantList.VariantBucket;
import got.common.world.feature.GOTTreeType;
import got.common.world.feature.GOTTreeType.WeightedTreeType;
import got.common.world.map.*;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTBiomeSpawnList.*;
import got.common.world.spawning.GOTSpawnEntry;
import got.common.world.structure.other.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.*;

public class DatabaseGenerator extends GOTStructureBase {
	public static String display = "null";
	public static Map<Class<? extends Entity>, Entity> classToObjectMapping = new HashMap<>();
	public static Map<Class<? extends Entity>, GOTWaypoint> classToWaypointMapping = new HashMap<>();
	public static Map<String, String> factionPageMapping = new HashMap<>();
	public static Map<String, String> entityPageMapping = new HashMap<>();
	public static Map<String, String> biomePageMapping = new HashMap<>();

	public static String biomePage = StatCollector.translateToLocal("got.db.biomeLoc.name");
	public static String factionPage = StatCollector.translateToLocal("got.db.factionLoc.name");
	public static String entityPage = StatCollector.translateToLocal("got.db.entityLoc.name");

	public DatabaseGenerator(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int y, int j, int k, int rotation) {
		long time = System.nanoTime();
		String biomeHasAnimals = StatCollector.translateToLocal("got.db.biomeHasAnimals.name");
		String biomeHasConquest = StatCollector.translateToLocal("got.db.biomeHasConquest.name");
		String biomeHasInvasions = StatCollector.translateToLocal("got.db.biomeHasInvasions.name");
		String biomeHasSpawn = StatCollector.translateToLocal("got.db.biomeHasSpawn.name");
		String biomeHasStructures = StatCollector.translateToLocal("got.db.biomeHasStructures.name");
		String biomeHasTreesBiomeAndVariant = StatCollector.translateToLocal("got.db.biomeHasTrees2.name");
		String biomeHasTreesBiomeOnly = StatCollector.translateToLocal("got.db.biomeHasTrees1.name");
		String biomeHasWaypoints = StatCollector.translateToLocal("got.db.biomeHasWaypoints.name");
		String biomeNoAchievement = StatCollector.translateToLocal("got.db.biomeNoAchievement.name");
		String biomeNoAnimals = StatCollector.translateToLocal("got.db.biomeNoAnimals.name");
		String biomeNoConquest = StatCollector.translateToLocal("got.db.biomeNoConquest.name");
		String biomeNoInvasions = StatCollector.translateToLocal("got.db.biomeNoInvasions.name");
		String biomeNoSpawn = StatCollector.translateToLocal("got.db.biomeNoSpawn.name");
		String biomeNoStructures = StatCollector.translateToLocal("got.db.biomeNoStructures.name");
		String biomeNoTrees = StatCollector.translateToLocal("got.db.biomeNoTrees.name");
		String biomeNoVariants = StatCollector.translateToLocal("got.db.biomeNoVariants.name");
		String biomeNoWaypoints = StatCollector.translateToLocal("got.db.biomeNoWaypoints.name");
		String biomeMinerals = StatCollector.translateToLocal("got.db.biomeMinerals.name");
		String biomeConquestOnly = StatCollector.translateToLocal("got.db.biomeConquestOnly.name");
		String biomeSpawnOnly = StatCollector.translateToLocal("got.db.biomeSpawnOnly.name");

		String factionHasBanners = StatCollector.translateToLocal("got.db.factionHasBanners.name");
		String factionHasCharacters = StatCollector.translateToLocal("got.db.factionHasCharacters.name");
		String factionHasConquest = StatCollector.translateToLocal("got.db.factionHasConquest.name");
		String factionHasInvasion = StatCollector.translateToLocal("got.db.factionHasInvasion.name");
		String factionHasRanks = StatCollector.translateToLocal("got.db.factionHasRanks.name");
		String factionHasSpawn = StatCollector.translateToLocal("got.db.factionHasSpawn.name");
		String factionHasWarCrimes = StatCollector.translateToLocal("got.db.factionIsViolent.name");
		String factionHasWaypoints = StatCollector.translateToLocal("got.db.factionHasWaypoints.name");
		String factionNoAttr = StatCollector.translateToLocal("got.db.factionNoAttr.name");
		String factionNoBanners = StatCollector.translateToLocal("got.db.factionNoBanners.name");
		String factionNoCharacters = StatCollector.translateToLocal("got.db.factionNoCharacters.name");
		String factionNoConquest = StatCollector.translateToLocal("got.db.factionNoConquest.name");
		String factionNoEnemies = StatCollector.translateToLocal("got.db.factionNoEnemies.name");
		String factionNoFriends = StatCollector.translateToLocal("got.db.factionNoFriends.name");
		String factionNoInvasion = StatCollector.translateToLocal("got.db.factionNoInvasion.name");
		String factionNoRanks = StatCollector.translateToLocal("got.db.factionNoRanks.name");
		String factionNoSpawn = StatCollector.translateToLocal("got.db.factionNoSpawn.name");
		String factionNoStructures = StatCollector.translateToLocal("got.db.factionNoStructures.name");
		String factionNoWarCrimes = StatCollector.translateToLocal("got.db.factionNotViolent.name");
		String factionNoWaypoints = StatCollector.translateToLocal("got.db.factionNoWaypoints.name");

		String treeHasBiomes = StatCollector.translateToLocal("got.db.treeHasBiomes.name");
		String treeNoBiomes = StatCollector.translateToLocal("got.db.treeNoBiomes.name");
		String treeVariantOnly = StatCollector.translateToLocal("got.db.treeVariantOnly.name");

		String rider = StatCollector.translateToLocal("got.db.rider.name");
		String noPledge = StatCollector.translateToLocal("got.db.noPledge.name");
		String yesPledge = StatCollector.translateToLocal("got.db.yesPledge.name");
		String rep = StatCollector.translateToLocal("got.db.rep.name");
		String category = StatCollector.translateToLocal("got.db.categoryTemplates.name");

		String mineralBiomes = StatCollector.translateToLocal("got.db.mineralBiomes.name");
		String structureBiomes = StatCollector.translateToLocal("got.db.structureBiomes.name");

		String entityNoBiomes = StatCollector.translateToLocal("got.db.entityNoBiomes.name");
		String entityHasBiomes = StatCollector.translateToLocal("got.db.entityHasBiomes.name");
		String entityConquestOnly = StatCollector.translateToLocal("got.db.entityConquestOnly.name");
		String entityInvasionOnly = StatCollector.translateToLocal("got.db.entityInvasionOnly.name");
		String entityConquestInvasion = StatCollector.translateToLocal("got.db.entityConquestInvasion.name");

		classToWaypointMapping.put(GOTEntityYgritte.class, GOTWaypoint.Hardhome);
		classToWaypointMapping.put(GOTEntityTormund.class, GOTWaypoint.Hardhome);
		classToWaypointMapping.put(GOTEntityManceRayder.class, GOTWaypoint.Hardhome);
		classToWaypointMapping.put(GOTEntityCraster.class, GOTWaypoint.CrastersKeep);
		classToWaypointMapping.put(GOTEntityVictarionGreyjoy.class, GOTWaypoint.VictarionLanding);
		classToWaypointMapping.put(LancelLannisterNormal.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityTobhoMott.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityTyrionLannister.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityGendryBaratheon.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityPetyrBaelish.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityBronn.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityPodrickPayne.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityPetyrBaelish.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityHotPie.class, GOTWaypoint.CrossroadsInn);
		classToWaypointMapping.put(GOTEntityVargoHoat.class, GOTWaypoint.CrossroadsInn);
		classToWaypointMapping.put(GOTEntitySandorClegane.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityJoffreyBaratheon.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityCerseiLannister.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityJaimeLannister.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityPycelle.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityJanosSlynt.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityVarys.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityIlynPayne.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityHighSepton.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityTommenBaratheon.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityMyrcellaBaratheon.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityMerynTrant.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityBarristanSelmy.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityJeorMormont.class, GOTWaypoint.CastleBlack);
		classToWaypointMapping.put(JonSnowLife1.class, GOTWaypoint.CastleBlack);
		classToWaypointMapping.put(GOTEntityAemonTargaryen.class, GOTWaypoint.CastleBlack);
		classToWaypointMapping.put(GOTEntityAlliserThorne.class, GOTWaypoint.CastleBlack);
		classToWaypointMapping.put(GOTEntityEdd.class, GOTWaypoint.CastleBlack);
		classToWaypointMapping.put(GOTEntitySamwellTarly.class, GOTWaypoint.CastleBlack);
		classToWaypointMapping.put(GOTEntityCotterPyke.class, GOTWaypoint.EastWatch);
		classToWaypointMapping.put(GOTEntityHarmune.class, GOTWaypoint.EastWatch);
		classToWaypointMapping.put(GOTEntityDenysMallister.class, GOTWaypoint.ShadowTower);
		classToWaypointMapping.put(GOTEntityMullin.class, GOTWaypoint.ShadowTower);

		try {
			HashSet<Item> items = new HashSet<>(GOTAPI.getObjectFieldsOfType(GOTRegistry.class, Item.class));
			HashSet<GOTUnitTradeEntries> units = new HashSet<>(GOTAPI.getObjectFieldsOfType(GOTUnitTradeEntries.class, GOTUnitTradeEntries.class));
			HashSet<GOTAchievement> achievements = new HashSet<>(GOTAPI.getObjectFieldsOfType(GOTAchievement.class, GOTAchievement.class));
			HashSet<GOTBiome> biomes = new HashSet<>(GOTAPI.getObjectFieldsOfType(GOTBiome.class, GOTBiome.class));
			EnumSet<GOTFaction> factions = EnumSet.allOf(GOTFaction.class);
			EnumSet<GOTTreeType> trees = EnumSet.allOf(GOTTreeType.class);
			EnumSet<GOTWaypoint> waypoints = EnumSet.allOf(GOTWaypoint.class);
			EnumSet<GOTCapes> capes = EnumSet.allOf(GOTCapes.class);
			EnumSet<GOTShields> shields = EnumSet.allOf(GOTShields.class);
			HashSet<String> minerals = new HashSet<>();
			HashSet<Class<? extends WorldGenerator>> structures = new HashSet<>();
			HashSet<Class<? extends Entity>> hireable = new HashSet<>();
			searchForEntities(world);
			searchForMinerals(biomes, minerals);
			searchForStructures(biomes, structures);
			searchForHireable(hireable, units);
			searchForPagenamesEntity(biomes, factions);
			searchForPagenamesBiome(biomes, factions);
			searchForPagenamesFaction(biomes, factions);
			biomes.remove(GOTBiome.ocean1);
			biomes.remove(GOTBiome.ocean2);
			biomes.remove(GOTBiome.ocean3);
			biomes.remove(GOTBiome.beachGravel);
			biomes.remove(GOTBiome.beachWhite);
			biomes.remove(GOTBiome.beachRed);
			Files.createDirectories(Paths.get("hummel"));
			if ("tables".equals(display)) {
				PrintWriter fAchievements = new PrintWriter("hummel/achievements.txt", "UTF-8");
				for (GOTAchievement ach : achievements) {
					if (ach != null) {
						fAchievements.println("| " + getAchievementTitle(ach) + " || " + getAchievementDesc(ach));
						fAchievements.println("|-");
					}
				}
				fAchievements.close();

				PrintWriter fShields = new PrintWriter("hummel/shields.txt", "UTF-8");
				for (GOTShields shield : shields) {
					fShields.println("| " + shield.getShieldName() + " || " + shield.getShieldDesc() + " || " + getShieldFilename(shield));
					fShields.println("|-");
				}
				fShields.close();

				PrintWriter fCapes = new PrintWriter("hummel/capes.txt", "UTF-8");
				for (GOTCapes cape : capes) {
					fCapes.println("| " + cape.getCapeName() + " || " + cape.getCapeDesc() + " || " + getCapeFilename(cape));
					fCapes.println("|-");
				}
				fCapes.close();

				PrintWriter fUnits = new PrintWriter("hummel/units.txt", "UTF-8");
				for (GOTUnitTradeEntries unitTradeEntries : units) {
					if (unitTradeEntries != null) {
						for (GOTUnitTradeEntry entry : unitTradeEntries.tradeEntries) {
							if (entry != null) {
								if (entry.getPledgeType() == PledgeType.NONE) {
									if (entry.mountClass == null) {
										fUnits.println("| " + getEntityLink(entry.entityClass) + " || {{Coins|" + entry.initialCost * 2 + "}} || {{Coins|" + entry.initialCost + "}} || +" + entry.alignmentRequired + " || - ");
									} else {
										fUnits.println("| " + getEntityLink(entry.entityClass) + " || {{Coins|" + entry.initialCost * 2 + "}} (" + rider + ") || {{Coins|" + entry.initialCost + "}} || +" + entry.alignmentRequired + " || - ");
									}
								} else if (entry.mountClass == null) {
									if (entry.alignmentRequired < 101.0f) {
										fUnits.println("| " + getEntityLink(entry.entityClass) + " || N/A || {{Coins|" + entry.initialCost + "}} || +100.0 || + ");
									} else {
										fUnits.println("| " + getEntityLink(entry.entityClass) + " || N/A || {{Coins|" + entry.initialCost + "}} || +" + entry.alignmentRequired + " || + ");
									}
								} else if (entry.alignmentRequired < 101.0f) {
									fUnits.println("| " + getEntityLink(entry.entityClass) + " || N/A || {{Coins|" + entry.initialCost + "}} (" + rider + ") || +100.0 || + ");
								} else {
									fUnits.println("| " + getEntityLink(entry.entityClass) + " || N/A || {{Coins|" + entry.initialCost + "}} (" + rider + ") || +" + entry.alignmentRequired + " || + ");
								}
								fUnits.println("|-");
							}
						}
					}
				}
				fUnits.close();

				PrintWriter fWaypoints = new PrintWriter("hummel/waypoints.txt", "UTF-8");
				for (GOTWaypoint wp : waypoints) {
					fWaypoints.println("| " + wp.getDisplayName() + " || " + wp.getLoreText(usingPlayer));
					fWaypoints.println("|-");
				}
				fWaypoints.close();

				PrintWriter fArmor = new PrintWriter("hummel/armor.txt", "UTF-8");
				for (Item item : items) {
					if (item instanceof ItemArmor) {
						float damage = ((ItemArmor) item).damageReduceAmount;
						ArmorMaterial material = ((ItemArmor) item).getArmorMaterial();
						if (material != null && material.customCraftingMaterial != null) {
							fArmor.println("| " + getItemName(item) + " || " + getItemFilename(item) + " || " + item.getMaxDamage() + " || " + damage + " || " + getItemName(material.customCraftingMaterial));
						} else {
							fArmor.println("| " + getItemName(item) + " || " + getItemFilename(item) + " || " + item.getMaxDamage() + " || " + damage + " || N/A ");
						}
						fArmor.println("|-");
					}
				}
				fArmor.close();

				PrintWriter fWeapon = new PrintWriter("hummel/weapon.txt", "UTF-8");
				for (Item item : items) {
					if (item instanceof ItemSword) {
						float damage = GOTReflection.getDamageAmount(item);
						ToolMaterial material = GOTReflection.getToolMaterial(item);
						if (material.getRepairItemStack() != null) {
							fWeapon.println("| " + getItemName(item) + " || " + getItemFilename(item) + " || " + item.getMaxDamage() + " || " + damage + " || " + getItemName(material.getRepairItemStack().getItem()));
						} else {
							fWeapon.println("| " + getItemName(item) + " || " + getItemFilename(item) + " || " + item.getMaxDamage() + " || " + damage + " || N/A ");
						}
						fWeapon.println("|-");
					}
				}
				fWeapon.close();

				PrintWriter fFood = new PrintWriter("hummel/food.txt", "UTF-8");
				for (Item item : items) {
					if (item instanceof ItemFood) {
						int heal = ((ItemFood) item).func_150905_g(null);
						float saturation = ((ItemFood) item).func_150906_h(null);
						fFood.println("| " + getItemName(item) + " || " + getItemFilename(item) + " || " + "{{Bar|bread|" + new DecimalFormat("#.##").format(saturation * heal * 2) + "}} || {{Bar|food|" + heal + "}} || " + item.getItemStackLimit());
						fFood.println("|-");
					}
				}
				fFood.close();

			} else if ("xml".equals(display)) {
				PrintWriter xml = new PrintWriter("hummel/xml.txt", "UTF-8");
				xml.println("<mediawiki xmlns=\"http://www.mediawiki.org/xml/export-0.11/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.mediawiki.org/xml/export-0.11/ http://www.mediawiki.org/xml/export-0.11.xsd\" version=\"0.11\" xml:lang=\"ru\">");

				/* ALL PAGES */

				File file = new File("hummel/sitemap.txt");
				if (!file.exists()) {
					file.createNewFile();
				}
				List<String> sitemap;
				try (Stream<String> lines = Files.lines(Paths.get("hummel/sitemap.txt"))) {
					sitemap = lines.collect(Collectors.toList());
				}

				String s1 = "<page><title>";

				for (String str : minerals) {
					if (!sitemap.contains(str)) {
						String s2 = "</title><revision><text>{{\u0421\u0442\u0430\u0442\u044C\u044F \u0418\u0441\u043A\u043E\u043F\u0430\u0435\u043C\u043E\u0435}}</text></revision></page>";
						xml.print(s1 + str + s2);
						xml.println();
					}
				}

				for (Class<? extends Entity> entityClass : classToObjectMapping.keySet()) {
					String pageName = getEntityPagename(entityClass);
					if (!sitemap.contains(pageName)) {
						String s2 = "</title><revision><text>{{\u0421\u0442\u0430\u0442\u044C\u044F \u041C\u043E\u0431}}</text></revision></page>";
						xml.print(s1 + pageName + s2);
						xml.println();
					}
				}

				for (GOTBiome biome : biomes) {
					if (biome != null) {
						String pageName = getBiomePagename(biome);
						if (!sitemap.contains(pageName)) {
							String s2 = "</title><revision><text>{{\u0421\u0442\u0430\u0442\u044C\u044F \u0411\u0438\u043E\u043C}}</text></revision></page>";
							xml.print(s1 + pageName + s2);
							xml.println();
						}
					}
				}

				for (GOTFaction fac : factions) {
					String pageName = getFactionPagename(fac);
					if (!sitemap.contains(pageName)) {
						String s2 = "</title><revision><text>{{\u0421\u0442\u0430\u0442\u044C\u044F \u0424\u0440\u0430\u043A\u0446\u0438\u044F}}</text></revision></page>";
						xml.print(s1 + pageName + s2);
						xml.println();
					}
				}

				for (GOTTreeType tree : trees) {
					String pageName = getTreeName(tree);
					if (!sitemap.contains(pageName)) {
						String s2 = "</title><revision><text>{{\u0421\u0442\u0430\u0442\u044C\u044F \u0414\u0435\u0440\u0435\u0432\u043E}}</text></revision></page>";
						xml.print(s1 + pageName + s2);
						xml.println();
					}
				}

				for (Class<? extends WorldGenerator> strClass : structures) {
					String pageName = getStructureName(strClass);
					if (!sitemap.contains(pageName)) {
						String s2 = "</title><revision><text>{{\u0421\u0442\u0430\u0442\u044C\u044F \u0421\u0442\u0440\u0443\u043A\u0442\u0443\u0440\u0430}}</text></revision></page>";
						xml.print(s1 + pageName + s2);
						xml.println();
					}
				}

				/* DATABASES */

				String begin = "</title><ns>10</ns><revision><text>&lt;includeonly&gt;{{#switch: {{{1}}}";
				String end = "}}&lt;/includeonly&gt;&lt;noinclude&gt;[[" + category + "]]&lt;/noinclude&gt;</text></revision></page>";

				/* STRUCTURES */

				xml.print("<page><title>Template:DB Structure-Biomes");
				xml.println(begin);
				for (Class<? extends WorldGenerator> strClass : structures) {
					xml.println("| " + getStructureName(strClass) + " = " + structureBiomes);
					next: for (GOTBiome biome : biomes) {
						if (biome != null && !biome.decorator.randomStructures.isEmpty()) {
							for (RandomStructure structure : biome.decorator.randomStructures) {
								if (structure.structureGen.getClass() == strClass) {
									xml.println("* " + getBiomeLink(biome) + ";");
									continue next;
								}
							}
						}
					}
				}
				xml.println(end);

				/* MINERALS */

				xml.print("<page><title>Template:DB Mineral-Biomes");
				xml.println(begin);
				for (String mineral : minerals) {
					xml.println("| " + mineral + " = " + mineralBiomes);
					next: for (GOTBiome biome : biomes) {
						if (biome != null) {
							List<OreGenerant> oreGenerants = new ArrayList<>(biome.decorator.biomeSoils);
							oreGenerants.addAll(biome.decorator.biomeOres);
							oreGenerants.addAll(biome.decorator.biomeGems);
							for (OreGenerant oreGenerant : oreGenerants) {
								Block block = GOTReflection.getOreBlock(oreGenerant.oreGen);
								int meta = GOTReflection.getOreMeta(oreGenerant.oreGen);
								if (getBlockMetaName(block, meta).equals(mineral) || getBlockName(block).equals(mineral)) {
									xml.println("* " + getBiomeLink(biome) + " (" + oreGenerant.oreChance + "%; Y: " + oreGenerant.minHeight + "-" + oreGenerant.maxHeight + ");");
									continue next;
								}
							}
						}
					}
				}
				xml.println(end);

				/* TREES */

				xml.print("<page><title>Template:DB Tree-Biomes");
				xml.println(begin);
				for (GOTTreeType tree : trees) {
					HashSet<GOTBiome> biomesTree = new HashSet<>();
					HashSet<GOTBiome> biomesVariantTree = new HashSet<>();
					next: for (GOTBiome biome : biomes) {
						if (biome != null) {
							for (WeightedTreeType weightedTreeType : biome.decorator.treeTypes) {
								if (weightedTreeType.treeType == tree) {
									biomesTree.add(biome);
									continue next;
								}
							}
							for (VariantBucket variantBucket : biome.getBiomeVariantsSmall().variantList) {
								for (WeightedTreeType weightedTreeType : variantBucket.variant.treeTypes) {
									if (weightedTreeType.treeType == tree) {
										biomesVariantTree.add(biome);
										continue next;
									}
								}
							}
						}
					}
					if (biomesTree.isEmpty() && biomesVariantTree.isEmpty()) {
						xml.println("| " + getTreeName(tree) + " = " + treeNoBiomes);
					} else {
						xml.println("| " + getTreeName(tree) + " = " + treeHasBiomes);
					}
					for (GOTBiome biome : biomesTree) {
						xml.println("* " + getBiomeLink(biome) + ";");
					}
					for (GOTBiome biome : biomesVariantTree) {
						xml.println("* " + getBiomeLink(biome) + " (" + treeVariantOnly + ");");
					}
				}
				xml.println(end);

				/* BIOMES */

				xml.print("<page><title>Template:DB Biome-SpawnNPC");
				xml.println(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						List<FactionContainer> facContainers = biome.getNpcSpawnList().factionContainers;
						if (facContainers.isEmpty()) {
							xml.println("| " + getBiomePagename(biome) + " = " + biomeNoSpawn);
						} else {
							ArrayList<FactionContainer> spawnContainers = new ArrayList<>();
							for (FactionContainer facContainer : facContainers) {
								if (facContainer.baseWeight > 0) {
									spawnContainers.add(facContainer);
								}
							}
							if (spawnContainers.isEmpty()) {
								xml.println("| " + getBiomePagename(biome) + " = " + biomeConquestOnly);
							} else {
								xml.println("| " + getBiomePagename(biome) + " = " + biomeHasSpawn);
								for (FactionContainer facContainer : spawnContainers) {
									for (SpawnListContainer container : facContainer.spawnLists) {
										for (GOTSpawnEntry entry : container.spawnList.spawnList) {
											xml.println("* " + getEntityLink(entry.entityClass) + "; ");
										}
									}
								}
							}
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Biome-ConquestNPC");
				xml.println(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						List<FactionContainer> facContainers = biome.getNpcSpawnList().factionContainers;
						if (facContainers.isEmpty()) {
							xml.println("| " + getBiomePagename(biome) + " = " + biomeNoConquest);
						} else {
							ArrayList<FactionContainer> conqestContainers = new ArrayList<>();
							for (FactionContainer facContainer : facContainers) {
								if (facContainer.baseWeight <= 0) {
									conqestContainers.add(facContainer);
								}
							}
							if (conqestContainers.isEmpty()) {
								xml.println("| " + getBiomePagename(biome) + " = " + biomeSpawnOnly);
							} else {
								xml.println("| " + getBiomePagename(biome) + " = " + biomeHasConquest);
								EnumSet<GOTFaction> conquestFactions = EnumSet.allOf(GOTFaction.class);
								for (FactionContainer facContainer : conqestContainers) {
									next: for (SpawnListContainer container : facContainer.spawnLists) {
										for (GOTSpawnEntry entry : container.spawnList.spawnList) {
											Entity entity = classToObjectMapping.get(entry.entityClass);
											if (entity instanceof GOTEntityNPC) {
												GOTFaction fac = ((GOTEntityNPC) entity).getFaction();
												conquestFactions.add(fac);
												continue next;
											}
										}
									}
								}
								for (GOTFaction fac : conquestFactions) {
									xml.println("* " + getFactionLink(fac) + "; ");
								}
							}
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Biome-Bandits");
				xml.println(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						xml.println("| " + getBiomePagename(biome) + " = " + biome.getBanditChance());
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Biome-Name");
				xml.println(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						xml.println("| " + getBiomePagename(biome) + " = " + getBiomeName(biome));
					}
				}
				xml.println(end);

				boolean lotrStyle = false;
				if (lotrStyle) {
					xml.print("<page><title>Template:DB Biome-Rainfall");
					xml.println(begin);
					for (GOTBiome biome : biomes) {
						if (biome != null) {
							xml.println("| " + getBiomePagename(biome) + " = " + biome.rainfall);
						}
					}
					xml.println(end);

					xml.print("<page><title>Template:DB Biome-Temperature");
					xml.println(begin);
					for (GOTBiome biome : biomes) {
						if (biome != null) {
							xml.println("| " + getBiomePagename(biome) + " = " + biome.temperature);
						}
					}
				} else {
					xml.print("<page><title>Template:DB Biome-Climat");
					xml.println(begin);
					for (GOTBiome biome : biomes) {
						if (biome != null) {
							xml.println("| " + getBiomePagename(biome) + " = " + biome.getClimateType());
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Biome-Variants");
				xml.println(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						if (biome.getBiomeVariantsSmall().variantList.isEmpty()) {
							xml.println("| " + getBiomePagename(biome) + " = " + biomeNoVariants);
						} else {
							xml.println("| " + getBiomePagename(biome) + " = ");
							for (VariantBucket variantBucket : biome.getBiomeVariantsSmall().variantList) {
								xml.println("* " + getBiomeVariantName(variantBucket.variant) + ";");
							}
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Biome-Invasions");
				xml.println(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						if (biome.getInvasionSpawns().registeredInvasions.isEmpty()) {
							xml.println("| " + getBiomePagename(biome) + " = " + biomeNoInvasions);
						} else {
							xml.println("| " + getBiomePagename(biome) + " = " + biomeHasInvasions);
							EnumSet<GOTFaction> invasionFactions = EnumSet.allOf(GOTFaction.class);
							next: for (GOTInvasions invasion : biome.getInvasionSpawns().registeredInvasions) {
								for (InvasionSpawnEntry entry : invasion.invasionMobs) {
									Entity entity = classToObjectMapping.get(entry.entityClass);
									if (entity instanceof GOTEntityNPC) {
										GOTFaction fac = ((GOTEntityNPC) entity).getFaction();
										invasionFactions.add(fac);
										continue next;
									}
								}
							}
							for (GOTFaction fac : invasionFactions) {
								xml.println("* " + getFactionLink(fac) + ";");
							}
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Biome-Waypoints");
				xml.println(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						Region region = biome.getBiomeWaypoints();
						if (region == null) {
							xml.println("| " + getBiomePagename(biome) + " = " + biomeNoWaypoints);
						} else {
							xml.println("| " + getBiomePagename(biome) + " = " + biomeHasWaypoints);
							for (GOTWaypoint wp : waypoints) {
								if (wp.region == region) {
									xml.println("* " + wp.getDisplayName() + " (" + getFactionLink(wp.faction) + ");");
								}
							}
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Biome-Achievement");
				xml.println(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						GOTAchievement ach = biome.getBiomeAchievement();
						if (ach == null) {
							xml.println("| " + getBiomePagename(biome) + " = " + biomeNoAchievement);
						} else {
							xml.println("| " + getBiomePagename(biome) + " = \"" + getAchievementTitle(ach) + "\"");
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Biome-Trees");
				xml.println(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						EnumSet<GOTTreeType> treesBiome = EnumSet.allOf(GOTTreeType.class);
						EnumMap<GOTTreeType, GOTBiomeVariant> treesVariant = new EnumMap<>(GOTTreeType.class);
						for (WeightedTreeType weightedTreeType : biome.decorator.treeTypes) {
							treesBiome.add(weightedTreeType.treeType);
						}
						for (VariantBucket variantBucket : biome.getBiomeVariantsSmall().variantList) {
							for (WeightedTreeType weightedTreeType : variantBucket.variant.treeTypes) {
								treesVariant.put(weightedTreeType.treeType, variantBucket.variant);
							}
						}
						if (treesBiome.isEmpty() && treesVariant.isEmpty()) {
							xml.println("| " + getBiomePagename(biome) + " = " + biomeNoTrees);
						} else {
							EnumMap<GOTTreeType, GOTBiomeVariant> treesVariantOnly = new EnumMap<>(GOTTreeType.class);

							if (treesVariantOnly.isEmpty()) {
								xml.println("| " + getBiomePagename(biome) + " = " + biomeHasTreesBiomeOnly);
							} else {
								xml.println("| " + getBiomePagename(biome) + " = " + biomeHasTreesBiomeAndVariant);
							}
							if (!treesBiome.isEmpty()) {
								for (GOTTreeType tree : treesBiome) {
									xml.println("* [[" + getTreeName(tree) + "]];");
								}
							}
							if (!treesVariantOnly.isEmpty()) {
								for (Entry<GOTTreeType, GOTBiomeVariant> tree : treesVariantOnly.entrySet()) {
									if (!treesBiome.contains(tree.getKey())) {
										xml.println("* [[" + getTreeName(tree.getKey()) + "]] (" + getBiomeVariantName(tree.getValue()).toLowerCase() + ")" + ";");
									}
								}
							}
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Biome-Mobs");
				xml.println(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						List<SpawnListEntry> entries = new ArrayList<>(biome.getSpawnableList(EnumCreatureType.ambient));
						entries.addAll(biome.getSpawnableList(EnumCreatureType.waterCreature));
						entries.addAll(biome.getSpawnableList(EnumCreatureType.creature));
						entries.addAll(biome.getSpawnableList(EnumCreatureType.monster));
						entries.addAll(biome.getSpawnableList(GOTBiome.creatureType_GOTAmbient));
						if (entries.isEmpty()) {
							xml.println("| " + getBiomePagename(biome) + " = " + biomeNoAnimals);
						} else {
							xml.println("| " + getBiomePagename(biome) + " = " + biomeHasAnimals);
							for (SpawnListEntry entry : entries) {
								if (GOTEntityRegistry.classToNameMapping.containsKey(entry.entityClass)) {
									xml.println("* " + getEntityLink(entry.entityClass) + ";");
								} else {
									xml.println("* " + getEntityVanillaName(entry.entityClass) + ";");
								}
							}
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Biome-Minerals");
				xml.println(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						xml.println("| " + getBiomePagename(biome) + " = " + biomeMinerals);
						List<OreGenerant> oreGenerants = new ArrayList<>(biome.decorator.biomeSoils);
						oreGenerants.addAll(biome.decorator.biomeOres);
						oreGenerants.addAll(biome.decorator.biomeGems);
						for (OreGenerant oreGenerant : oreGenerants) {
							Block block = GOTReflection.getOreBlock(oreGenerant.oreGen);
							int meta = GOTReflection.getOreMeta(oreGenerant.oreGen);

							if (block instanceof GOTBlockOreGem || block instanceof BlockDirt || block instanceof GOTBlockRock) {
								xml.println("* [[" + getBlockMetaName(block, meta) + "]] (" + oreGenerant.oreChance + "%; Y: " + oreGenerant.minHeight + "-" + oreGenerant.maxHeight + ");");
							} else {
								xml.println("* [[" + getBlockName(block) + "]] (" + oreGenerant.oreChance + "%; Y: " + oreGenerant.minHeight + "-" + oreGenerant.maxHeight + ");");
							}
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Biome-Music");
				xml.println(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						if (biome.getBiomeMusic() != null) {
							xml.println("| " + getBiomePagename(biome) + " = " + biome.getBiomeMusic().subregion);
						} else {
							xml.println("| " + getBiomePagename(biome) + " = N/A");
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Biome-Structures");
				xml.println(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						if (biome.decorator.randomStructures.isEmpty()) {
							xml.println("| " + getBiomePagename(biome) + " = " + biomeNoStructures);
						} else {
							xml.println("| " + getBiomePagename(biome) + " = " + biomeHasStructures);
							for (RandomStructure structure : biome.decorator.randomStructures) {
								xml.println("* [[" + getStructureName(structure.structureGen.getClass()) + "]];");
							}
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Biome-Photo");
				xml.println(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						xml.println("| " + getBiomePagename(biome) + " = " + biome.biomeName + " (biome).png");
					}
				}
				xml.println(end);

				/* FACTIONS */

				xml.print("<page><title>Template:DB Faction-NPC");
				xml.println(begin);
				for (GOTFaction fac : factions) {
					xml.println("| " + getFactionPagename(fac) + " = ");
					for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
						Entity entity = entityEntry.getValue();
						if (entity instanceof GOTEntityNPC && ((GOTEntityNPC) entity).getFaction() == fac && !((GOTEntityNPC) entity).isLegendaryNPC()) {
							xml.println("* " + getEntityLink(entityEntry.getKey()) + ";");
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Faction-Invasions");
				xml.println(begin);
				for (GOTFaction fac : factions) {
					HashSet<GOTBiome> invasionBiomes = new HashSet<>();
					next: for (GOTBiome biome : biomes) {
						if (biome != null && !biome.getInvasionSpawns().registeredInvasions.isEmpty()) {
							for (GOTInvasions invasion : biome.getInvasionSpawns().registeredInvasions) {
								for (InvasionSpawnEntry entry : invasion.invasionMobs) {
									Entity entity = classToObjectMapping.get(entry.entityClass);
									if (entity instanceof GOTEntityNPC && fac == ((GOTEntityNPC) entity).getFaction()) {
										invasionBiomes.add(biome);
										continue next;
									}
								}
							}
						}
					}
					if (invasionBiomes.isEmpty()) {
						xml.println("| " + getFactionPagename(fac) + " = " + factionNoInvasion);
					} else {
						xml.println("| " + getFactionPagename(fac) + " = " + factionHasInvasion);
						for (GOTBiome biome : invasionBiomes) {
							xml.println("* " + getBiomeLink(biome) + ";");
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Faction-Spawn");
				xml.println(begin);
				for (GOTFaction fac : factions) {
					HashSet<GOTBiome> spawnBiomes = new HashSet<>();
					next: for (GOTBiome biome : biomes) {
						if (biome != null) {
							List<FactionContainer> facContainers = biome.getNpcSpawnList().factionContainers;
							if (!facContainers.isEmpty()) {
								ArrayList<FactionContainer> spawnContainers = new ArrayList<>();
								for (FactionContainer facContainer : facContainers) {
									if (facContainer.baseWeight > 0) {
										spawnContainers.add(facContainer);
									}
								}
								if (!spawnContainers.isEmpty()) {
									for (FactionContainer facContainer : spawnContainers) {
										for (SpawnListContainer container : facContainer.spawnLists) {
											for (GOTSpawnEntry entry : container.spawnList.spawnList) {
												Entity entity = classToObjectMapping.get(entry.entityClass);
												if (entity instanceof GOTEntityNPC && ((GOTEntityNPC) entity).getFaction() == fac) {
													spawnBiomes.add(biome);
													continue next;
												}
											}
										}
									}
								}
							}
						}
					}
					if (spawnBiomes.isEmpty()) {
						xml.println("| " + getFactionPagename(fac) + " = " + factionNoSpawn);
					} else {
						xml.println("| " + getFactionPagename(fac) + " = " + factionHasSpawn);
						for (GOTBiome biome : spawnBiomes) {
							xml.println("* " + getBiomeLink(biome) + ";");
						}
					}

				}
				xml.println(end);

				xml.print("<page><title>Template:DB Faction-Conquest");
				xml.println(begin);
				for (GOTFaction fac : factions) {
					HashSet<GOTBiome> conquestBiomes = new HashSet<>();
					next: for (GOTBiome biome : biomes) {
						if (biome != null) {
							List<FactionContainer> facContainers = biome.getNpcSpawnList().factionContainers;
							if (!facContainers.isEmpty()) {
								ArrayList<FactionContainer> conquestContainers = new ArrayList<>();
								for (FactionContainer facContainer : facContainers) {
									if (facContainer.baseWeight <= 0) {
										conquestContainers.add(facContainer);
									}
								}
								if (!conquestContainers.isEmpty()) {
									for (FactionContainer facContainer : conquestContainers) {
										for (SpawnListContainer container : facContainer.spawnLists) {
											for (GOTSpawnEntry entry : container.spawnList.spawnList) {
												Entity entity = classToObjectMapping.get(entry.entityClass);
												if (entity instanceof GOTEntityNPC && ((GOTEntityNPC) entity).getFaction() == fac) {
													conquestBiomes.add(biome);
													continue next;
												}
											}
										}
									}
								}
							}
						}
					}
					if (conquestBiomes.isEmpty()) {
						xml.println("| " + getFactionPagename(fac) + " = " + factionNoConquest);
					} else {
						xml.println("| " + getFactionPagename(fac) + " = " + factionHasConquest);
						for (GOTBiome biome : conquestBiomes) {
							xml.println("* " + getBiomeLink(biome) + ";");
						}
					}

				}
				xml.println(end);

				xml.print("<page><title>Template:DB Faction-Ranks");
				xml.println(begin);
				for (GOTFaction fac : factions) {
					if (fac.ranksSortedDescending.isEmpty()) {
						xml.println("| " + getFactionPagename(fac) + " = " + factionNoRanks);
					} else {
						xml.println("| " + getFactionPagename(fac) + " = " + factionHasRanks);
						for (GOTFactionRank rank : fac.ranksSortedDescending) {
							xml.println("* " + rank.getDisplayFullName() + "/" + rank.getDisplayFullNameFem() + " (+" + rank.alignment + ");");
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Faction-Banners");
				xml.println(begin);
				for (GOTFaction fac : factions) {
					if (fac.factionBanners.isEmpty()) {
						xml.println("| " + getFactionPagename(fac) + " = " + factionNoBanners);
					} else {
						xml.println("| " + getFactionPagename(fac) + " = " + factionHasBanners);
						for (BannerType banner : fac.factionBanners) {
							xml.println("* " + getBannerName(banner) + ";");
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Faction-Waypoints");
				xml.println(begin);
				for (GOTFaction fac : factions) {
					ArrayList<GOTWaypoint> facWaypoints = new ArrayList<>();
					for (GOTWaypoint wp : waypoints) {
						if (wp.faction == fac) {
							facWaypoints.add(wp);
						}
					}
					if (facWaypoints.isEmpty()) {
						xml.println("| " + getFactionPagename(fac) + " = " + factionNoWaypoints);
					} else {
						xml.println("| " + getFactionPagename(fac) + " = " + factionHasWaypoints);
						for (GOTWaypoint wp : facWaypoints) {
							xml.println("* " + wp.getDisplayName() + ";");
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Faction-Attr");
				xml.println(begin);
				for (GOTFaction fac : factions) {
					ArrayList<GOTCapes> facCapes = new ArrayList<>();
					ArrayList<GOTShields> facShields = new ArrayList<>();
					for (GOTCapes cape : capes) {
						if (cape.alignmentFaction == fac) {
							facCapes.add(cape);
						}
					}
					for (GOTShields shield : shields) {
						if (shield.alignmentFaction == fac) {
							facShields.add(shield);
						}
					}

					if (facCapes.isEmpty() && facShields.isEmpty()) {
						xml.println("| " + getFactionPagename(fac) + " = " + factionNoAttr);
					} else {
						xml.println("| " + getFactionPagename(fac) + " = ");
						xml.println("&lt;table class=\"wikitable shields\"&gt;");
						for (GOTCapes cape : facCapes) {
							xml.println("&lt;tr&gt;&lt;td&gt;" + cape.getCapeName() + "&lt;/td&gt;&lt;td&gt;" + cape.getCapeDesc() + "&lt;/td&gt;&lt;td&gt;" + getCapeFilename(cape) + "&lt;/td&gt;&lt;/tr&gt;");
						}
						for (GOTShields shield : facShields) {
							xml.println("&lt;tr&gt;&lt;td&gt;" + shield.getShieldName() + "&lt;/td&gt;&lt;td&gt;" + shield.getShieldDesc() + "&lt;/td&gt;&lt;td&gt;" + getShieldFilename(shield) + "&lt;/td&gt;&lt;/tr&gt;");
						}
						xml.println("&lt;table&gt;");
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Faction-Pledge");
				xml.println(begin);
				for (GOTFaction fac : factions) {
					if (fac.getPledgeRank() != null) {
						xml.println("| " + getFactionPagename(fac) + " = " + fac.getPledgeRank().getDisplayName() + "/" + fac.getPledgeRank().getDisplayNameFem() + " (+" + fac.getPledgeAlignment() + ")");
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Faction-Chars");
				xml.println(begin);
				for (GOTFaction fac : factions) {
					ArrayList<Class<? extends Entity>> facCharacters = new ArrayList<>();
					for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
						Entity entity = entityEntry.getValue();
						if (entity instanceof GOTEntityNPC && ((GOTEntityNPC) entity).getFaction() == fac && ((GOTEntityNPC) entity).isLegendaryNPC()) {
							facCharacters.add(entityEntry.getKey());
						}
					}
					if (facCharacters.isEmpty()) {
						xml.println("| " + getFactionPagename(fac) + " = " + factionNoCharacters);
					} else {
						xml.println("| " + getFactionPagename(fac) + " = " + factionHasCharacters);
						for (Class<? extends Entity> entityClass : facCharacters) {
							xml.println("* " + getEntityLink(entityClass) + ";");
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Faction-Enemies");
				xml.println(begin);
				for (GOTFaction fac1 : factions) {
					ArrayList<GOTFaction> facEnemies = new ArrayList<>();
					for (GOTFaction fac2 : factions) {
						if (fac1.isBadRelation(fac2) && fac1 != fac2) {
							facEnemies.add(fac2);
						}
					}
					if (facEnemies.isEmpty()) {
						xml.println("| " + getFactionPagename(fac1) + " = " + factionNoEnemies);
					} else {
						xml.println("| " + getFactionPagename(fac1) + " = ");
						int i = 0;
						for (GOTFaction fac : facEnemies) {
							if (i == 0) {
								xml.print(getFactionLink(fac));
								i++;
							} else {
								xml.print(" \u2022 " + getFactionLink(fac));
							}
						}
						xml.println();
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Faction-Friends");
				xml.println(begin);
				for (GOTFaction fac1 : factions) {
					ArrayList<GOTFaction> facFriends = new ArrayList<>();
					for (GOTFaction fac2 : factions) {
						if (fac1.isGoodRelation(fac2) && fac1 != fac2) {
							facFriends.add(fac2);
						}
					}
					if (facFriends.isEmpty()) {
						xml.println("| " + getFactionPagename(fac1) + " = " + factionNoFriends);
					} else {
						xml.println("| " + getFactionPagename(fac1) + " = ");
						int i = 0;
						for (GOTFaction fac : facFriends) {
							if (i == 0) {
								xml.print(getFactionLink(fac));
								i++;
							} else {
								xml.print(" \u2022 " + getFactionLink(fac));
							}
						}
						xml.println();
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Faction-WarCrimes");
				xml.println(begin);
				xml.println("| #default = " + factionNoWarCrimes);
				for (GOTFaction fac : factions) {
					if (fac.approvesWarCrimes) {
						xml.println("| " + getFactionPagename(fac) + " = " + factionHasWarCrimes);
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Faction-Codename");
				xml.println(begin);
				for (GOTFaction fac : factions) {
					xml.println("| " + getFactionPagename(fac) + " = " + fac.codeName());
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Faction-Name");
				xml.println(begin);
				for (GOTFaction fac : factions) {
					xml.println("| " + getFactionPagename(fac) + " = " + getFactionName(fac));
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Faction-Region");
				xml.println(begin);
				for (GOTFaction fac : factions) {
					if (fac.factionRegion != null) {
						xml.println("| " + getFactionPagename(fac) + " = " + fac.factionRegion.getRegionName());
					} else {
						xml.println("| " + getFactionPagename(fac) + " = N/A");
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Faction-Structures");
				xml.println(begin);
				for (GOTFaction fac : factions) {
					ArrayList<Class<? extends WorldGenerator>> facStructures = new ArrayList<>();
					for (Class<? extends WorldGenerator> strClass : GOTStructureRegistry.classToFactionMapping.keySet()) {
						if (GOTStructureRegistry.classToFactionMapping.get(strClass) == fac) {
							facStructures.add(strClass);
						}
					}
					if (facStructures.isEmpty()) {
						xml.println("| " + getFactionPagename(fac) + " = " + factionNoStructures);
					} else {
						xml.println("| " + getFactionPagename(fac) + " = ");
						for (Class<? extends WorldGenerator> str : facStructures) {
							xml.println("* " + getStructureName(str) + ";");
						}
					}
				}
				xml.println(end);

				/* MOBS */

				xml.print("<page><title>Template:DB Mob-Spawn");
				xml.println(begin);
				for (Class<? extends Entity> entityClass : classToObjectMapping.keySet()) {
					HashSet<GOTBiome> spawnBiomes = new HashSet<>();
					HashSet<GOTBiome> conquestBiomes = new HashSet<>();
					HashSet<GOTBiome> invasionBiomes = new HashSet<>();
					HashSet<GOTBiome> unnaturalBiomes = new HashSet<>();
					next: for (GOTBiome biome : biomes) {
						if (biome != null) {
							List<SpawnListEntry> spawnEntries = new ArrayList<>();
							List<SpawnListEntry> conquestEntries = new ArrayList<>();
							List<InvasionSpawnEntry> invasionEntries = new ArrayList<>();
							spawnEntries.addAll(biome.getSpawnableList(EnumCreatureType.ambient));
							spawnEntries.addAll(biome.getSpawnableList(EnumCreatureType.waterCreature));
							spawnEntries.addAll(biome.getSpawnableList(EnumCreatureType.creature));
							spawnEntries.addAll(biome.getSpawnableList(EnumCreatureType.monster));
							spawnEntries.addAll(biome.getSpawnableGOTAmbientList());
							for (FactionContainer facContainer : biome.getNpcSpawnList().factionContainers) {
								if (facContainer.baseWeight > 0) {
									for (SpawnListContainer container : facContainer.spawnLists) {
										spawnEntries.addAll(container.spawnList.spawnList);
									}
								} else {
									for (SpawnListContainer container : facContainer.spawnLists) {
										conquestEntries.addAll(container.spawnList.spawnList);
									}
								}
							}
							if (!biome.getInvasionSpawns().registeredInvasions.isEmpty()) {
								for (GOTInvasions invasion : biome.getInvasionSpawns().registeredInvasions) {
									invasionEntries.addAll(invasion.invasionMobs);
								}
							}
							for (SpawnListEntry entry : spawnEntries) {
								if (entry.entityClass == entityClass) {
									spawnBiomes.add(biome);
									continue next;
								}
							}
							for (SpawnListEntry entry : conquestEntries) {
								if (entry.entityClass == entityClass) {
									conquestBiomes.add(biome);
									break;
								}
							}
							for (InvasionSpawnEntry entry : invasionEntries) {
								if (entry.entityClass == entityClass) {
									invasionBiomes.add(biome);
									break;
								}
							}
						}
					}
					if (spawnBiomes.isEmpty() && conquestBiomes.isEmpty() && invasionBiomes.isEmpty()) {
						xml.println("| " + getEntityPagename(entityClass) + " = " + entityNoBiomes);
					} else {
						xml.println("| " + getEntityPagename(entityClass) + " = " + entityHasBiomes);
						for (GOTBiome biome : spawnBiomes) {
							xml.println("* " + getBiomeLink(biome) + ";");
						}
						for (GOTBiome biome : conquestBiomes) {
							if (!invasionBiomes.contains(biome)) {
								xml.println("* " + getBiomeLink(biome) + " " + entityConquestOnly + ";");
							}
						}
						for (GOTBiome biome : invasionBiomes) {
							if (!conquestBiomes.contains(biome)) {
								xml.println("* " + getBiomeLink(biome) + " " + entityInvasionOnly + ";");
							}
						}
						unnaturalBiomes.addAll(conquestBiomes);
						unnaturalBiomes.addAll(invasionBiomes);
						for (GOTBiome biome : unnaturalBiomes) {
							if (conquestBiomes.contains(biome) && invasionBiomes.contains(biome)) {
								xml.println("* " + getBiomeLink(biome) + " " + entityConquestInvasion + ";");
							}
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>DB Mob-Owner");
				xml.println(begin);
				for (Class<? extends Entity> entityClass : hireable) {
					HashMap<Class<? extends Entity>, Class<? extends Entity>> owners = new HashMap<>();
					loop: for (Entry<Class<? extends Entity>, Entity> ownerEntry : classToObjectMapping.entrySet()) {
						if (ownerEntry.getValue() instanceof GOTUnitTradeable) {
							GOTUnitTradeEntries entries = ((GOTUnitTradeable) ownerEntry.getValue()).getUnits();
							if (!((GOTEntityNPC) ownerEntry.getValue()).isLegendaryNPC()) {
								for (GOTUnitTradeEntry entry : entries.tradeEntries) {
									if (entry.entityClass == entityClass) {
										owners.put(entityClass, ownerEntry.getKey());
										break loop;
									}
								}
							}
						}
					}
					if (owners.isEmpty()) {
						loop: for (Entry<Class<? extends Entity>, Entity> ownerEntry : classToObjectMapping.entrySet()) {
							if (ownerEntry.getValue() instanceof GOTUnitTradeable) {
								GOTUnitTradeEntries entries = ((GOTUnitTradeable) ownerEntry.getValue()).getUnits();
								if (((GOTEntityNPC) ownerEntry.getValue()).isLegendaryNPC()) {
									for (GOTUnitTradeEntry entry : entries.tradeEntries) {
										if (entry.entityClass == entityClass) {
											owners.put(entityClass, ownerEntry.getKey());
											break loop;
										}
									}
								}
							}
						}
					}
					if (!owners.isEmpty()) {
						xml.println("| " + getEntityPagename(entityClass) + " = " + getEntityLink(owners.get(entityClass)));
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Mob-Health");
				xml.println(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					EntityLivingBase entity = (EntityLivingBase) entityEntry.getValue();
					xml.println("| " + getEntityPagename(entityEntry.getKey()) + " = " + entity.getMaxHealth());
				}
				xml.println(end);

				xml.print("<page><title>Template: DB Mob-Rideable1");
				xml.println(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTEntityNPCRideable) {
						xml.println("| " + getEntityPagename(entityEntry.getKey()) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Mob-Rideable2");
				xml.println(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTNPCMount) {
						xml.println("| " + getEntityPagename(entityEntry.getKey()) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Mob-BannerBearer");
				xml.println(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTBannerBearer) {
						xml.println("| " + getEntityPagename(entityEntry.getKey()) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Mob-UnitTradeable");
				xml.println(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTUnitTradeable) {
						xml.println("| " + getEntityPagename(entityEntry.getKey()) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Mob-Tradeable");
				xml.println(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTTradeable) {
						xml.println("| " + getEntityPagename(entityEntry.getKey()) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Mob-Smith");
				xml.println(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTTradeable.Smith) {
						xml.println("| " + getEntityPagename(entityEntry.getKey()) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Mob-Mercenary");
				xml.println(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTMercenary) {
						xml.println("| " + getEntityPagename(entityEntry.getKey()) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Mob-Farmhand");
				xml.println(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTFarmhand) {
						xml.println("| " + getEntityPagename(entityEntry.getKey()) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Mob-Buys");
				xml.println(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTTradeable) {
						GOTTradeEntries entries = ((GOTTradeable) entityEntry.getValue()).getSellPool();
						xml.println("| " + getEntityPagename(entityEntry.getKey()) + " = ");
						for (GOTTradeEntry entry : entries.tradeEntries) {
							xml.println("* " + entry.tradeItem.getDisplayName() + ": {{Coins|" + entry.getCost() + "}};");
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Mob-Sells");
				xml.println(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTTradeable) {
						GOTTradeEntries entries = ((GOTTradeable) entityEntry.getValue()).getBuyPool();
						xml.println("| " + getEntityPagename(entityEntry.getKey()) + " = ");
						for (GOTTradeEntry entry : entries.tradeEntries) {
							xml.println("* " + entry.tradeItem.getDisplayName() + ": {{Coins|" + entry.getCost() + "}};");
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Mob-Character");
				xml.println(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTEntityNPC && ((GOTEntityNPC) entityEntry.getValue()).isLegendaryNPC()) {
						xml.println("| " + getEntityPagename(entityEntry.getKey()) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Mob-ImmuneToFrost");
				xml.println(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTEntityNPC && ((GOTEntityNPC) entityEntry.getValue()).isImmuneToFrost || entityEntry.getValue() instanceof GOTBiome.ImmuneToFrost) {
						xml.println("| " + getEntityPagename(entityEntry.getKey()) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Mob-ImmuneToFire");
				xml.println(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue().isImmuneToFire()) {
						xml.println("| " + getEntityPagename(entityEntry.getKey()) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Mob-ImmuneToHeat");
				xml.println(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTBiome.ImmuneToHeat) {
						xml.println("| " + getEntityPagename(entityEntry.getKey()) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Mob-SpawnInDarkness");
				xml.println(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTEntityNPC && ((GOTEntityNPC) entityEntry.getValue()).spawnsInDarkness) {
						xml.println("| " + getEntityPagename(entityEntry.getKey()) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Mob-Alignment");
				xml.println(begin);
				next: for (Class<? extends Entity> entityClass : hireable) {
					for (GOTUnitTradeEntries entries : units) {
						for (GOTUnitTradeEntry entry : entries.tradeEntries) {
							if (entry.entityClass == entityClass) {
								if (entry.getPledgeType() == PledgeType.NONE || entry.alignmentRequired >= 101.0f) {
									xml.println("| " + getEntityPagename(entityClass) + " = " + entry.alignmentRequired);
								} else {
									xml.println("| " + getEntityPagename(entityClass) + " = +" + 100.0);
								}
								continue next;
							}
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Mob-Marriage");
				xml.println(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTEntityNPC && ((GOTEntityNPC) entityEntry.getValue()).canBeMarried) {
						xml.println("| " + getEntityPagename(entityEntry.getKey()) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Mob-Faction");
				xml.println(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTEntityNPC) {
						GOTFaction fac = ((GOTEntityNPC) entityEntry.getValue()).getFaction();
						xml.println("| " + getEntityPagename(entityEntry.getKey()) + " = " + getFactionLink(fac));
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Mob-Achievement");
				xml.println(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTEntityNPC) {
						GOTAchievement ach = ((GOTEntityNPC) entityEntry.getValue()).getKillAchievement();
						if (ach != null) {
							xml.println("| " + getEntityPagename(entityEntry.getKey()) + " = \"" + getAchievementTitle(ach) + "\"");
						} else {
							xml.println("| " + getEntityPagename(entityEntry.getKey()) + " = N/A");
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Mob-Bonus");
				xml.println(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTEntityNPC) {
						float bonus = ((GOTEntityNPC) entityEntry.getValue()).getAlignmentBonus();
						xml.println("| " + getEntityPagename(entityEntry.getKey()) + " = +" + bonus);
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Mob-Price");
				xml.println(begin);
				for (GOTUnitTradeEntries entries : units) {
					for (GOTUnitTradeEntry entry : entries.tradeEntries) {
						if (entry.getPledgeType() == PledgeType.NONE) {
							xml.println("| " + getEntityPagename(entry.entityClass) + " = {{Coins|" + entry.initialCost * 2 + "}}");
						} else {
							xml.println("| " + getEntityPagename(entry.entityClass) + " = N/A");
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Mob-PricePledge");
				xml.println(begin);
				for (GOTUnitTradeEntries entries : units) {
					for (GOTUnitTradeEntry entry : entries.tradeEntries) {
						xml.println("| " + getEntityPagename(entry.entityClass) + " = {{Coins|" + entry.initialCost + "}}");
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Mob-Units");
				xml.println(begin);
				for (Entry<Class<? extends Entity>, Entity> ownerEntry : classToObjectMapping.entrySet()) {
					if (ownerEntry.getValue() instanceof GOTUnitTradeable) {
						GOTUnitTradeEntries entries = ((GOTUnitTradeable) ownerEntry.getValue()).getUnits();
						xml.println("| " + getEntityPagename(ownerEntry.getKey()) + " = ");
						for (GOTUnitTradeEntry entry : entries.tradeEntries) {
							if (entry.mountClass == null) {
								if (entry.getPledgeType() == PledgeType.NONE) {
									xml.println("* " + getEntityLink(entry.entityClass) + ": {{Coins|" + entry.initialCost * 2 + "}} " + noPledge + ", {{Coins|" + entry.initialCost + "}} " + yesPledge + "; " + entry.alignmentRequired + "+ " + rep + ";");
								} else if (entry.alignmentRequired < 101.0f) {
									xml.println("* " + getEntityLink(entry.entityClass) + ": N/A " + noPledge + ", {{Coins|" + entry.initialCost + "}} " + yesPledge + "; +" + 100.0 + "+ " + rep + ";");
								} else {
									xml.println("* " + getEntityLink(entry.entityClass) + ": N/A " + noPledge + ", {{Coins|" + entry.initialCost + "}} " + yesPledge + "; +" + entry.alignmentRequired + "+ " + rep + ";");
								}
							}
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Mob-Waypoint");
				xml.println(begin);
				for (Entry<Class<? extends Entity>, GOTWaypoint> entityEntry : classToWaypointMapping.entrySet()) {
					xml.println("| " + getEntityPagename(entityEntry.getKey()) + " = " + entityEntry.getValue().getDisplayName());
				}
				for (GOTWaypoint wp : GOTFixer.structures.keySet()) {
					GOTStructureBase str = GOTFixer.structures.get(wp);
					str.disable();
					str.generate(world, random, y, j, k);
					for (EntityCreature entity : GOTFixer.structures.get(wp).characters) {
						xml.println("| " + getEntityPagename(entity.getClass()) + " = " + wp.getDisplayName());
					}
					str.clear();
				}
				xml.println(end);
				xml.println("</mediawiki>");
				xml.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		long newTime = System.nanoTime();
		ChatComponentText chatComponentTranslation = new ChatComponentText("Generated databases in " + (newTime - time) / 1.0E9 + "s");
		usingPlayer.addChatMessage(chatComponentTranslation);
		return true;
	}

	public String getAchievementDesc(GOTAchievement ach) {
		return StatCollector.translateToLocal("got.achievement." + ach.getCodeName() + ".desc");
	}

	public String getAchievementTitle(GOTAchievement ach) {
		return StatCollector.translateToLocal("got.achievement." + ach.getCodeName() + ".title");
	}

	public String getBannerName(BannerType banner) {
		return StatCollector.translateToLocal("item.got:banner." + banner.bannerName + ".name");
	}

	public String getBiomeLink(GOTBiome biome) {
		String biomeName = getBiomeName(biome);
		String biomePagename = getBiomePagename(biome);
		if (biomeName.equals(biomePagename)) {
			return "[[" + biomeName + "]]";
		}
		return "[[" + biomePagename + "|" + biomeName + "]]";
	}

	public String getBiomeName(GOTBiome biome) {
		return StatCollector.translateToLocal("got.biome." + biome.biomeName + ".name");
	}

	public String getBiomePagename(GOTBiome biome) {
		return biomePageMapping.get(getBiomeName(biome));
	}

	public String getBiomeVariantName(GOTBiomeVariant variant) {
		return StatCollector.translateToLocal("got.variant." + variant.variantName + ".name");
	}

	public String getBlockMetaName(Block block, int meta) {
		return StatCollector.translateToLocal(block.getUnlocalizedName() + "." + meta + ".name");
	}

	public String getBlockName(Block block) {
		return StatCollector.translateToLocal(block.getUnlocalizedName() + ".name");
	}

	public String getCapeFilename(GOTCapes cape) {
		return "[[File:Cape " + cape.name().toLowerCase() + ".png]]";
	}

	public String getEntityLink(Class<? extends Entity> entityClass) {
		String entityName = getEntityName(entityClass);
		String entityPagename = getEntityPagename(entityClass);
		if (entityName.equals(entityPagename)) {
			return "[[" + entityPagename + "]]";
		}
		return "[[" + entityPagename + "|" + entityName + "]]";
	}

	public String getEntityName(Class<? extends Entity> entityClass) {
		return StatCollector.translateToLocal("entity.got." + GOTEntityRegistry.classToNameMapping.get(entityClass) + ".name");
	}

	public String getEntityPagename(Class<? extends Entity> entityClass) {
		return entityPageMapping.get(getEntityName(entityClass));
	}

	public String getEntityVanillaName(Class<? extends Entity> entityClass) {
		return StatCollector.translateToLocal("entity." + EntityList.classToStringMapping.get(entityClass) + ".name");
	}

	public String getFactionLink(GOTFaction fac) {
		String facName = getFactionName(fac);
		String facPagename = getFactionPagename(fac);
		if (facName.equals(facPagename)) {
			return "[[" + facPagename + "]]";
		}
		return "[[" + facPagename + "|" + facName + "]]";
	}

	public String getFactionName(GOTFaction fac) {
		return StatCollector.translateToLocal("got.faction." + fac.codeName() + ".name");
	}

	public String getFactionPagename(GOTFaction fac) {
		return factionPageMapping.get(getFactionName(fac));
	}

	public String getItemFilename(Item item) {
		return "[[File:" + item.getUnlocalizedName().substring("item.got:".length()) + ".png|32px|link=]]";
	}

	public String getItemName(Item item) {
		return StatCollector.translateToLocal(item.getUnlocalizedName() + ".name");
	}

	public String getShieldFilename(GOTShields shield) {
		return "[[File:Shield " + shield.name().toLowerCase() + ".png]]";
	}

	public String getStructureName(Class<? extends WorldGenerator> structureClass) {
		return StatCollector.translateToLocal("got.structure." + GOTStructureRegistry.classToNameMapping.get(structureClass) + ".name");
	}

	public String getTreeName(GOTTreeType tree) {
		return StatCollector.translateToLocal("got.tree." + tree.name().toLowerCase() + ".name");
	}

	public void searchForEntities(World world) {
		for (Class<? extends Entity> entityClass : GOTEntityRegistry.entitySet) {
			classToObjectMapping.put(entityClass, GOTReflection.newEntity(entityClass, world));
		}
	}

	public void searchForHireable(Collection<Class<? extends Entity>> hireable, Iterable<GOTUnitTradeEntries> units) {
		for (GOTUnitTradeEntries entries : units) {
			for (GOTUnitTradeEntry entry : entries.tradeEntries) {
				hireable.add(entry.entityClass);
			}
		}
	}

	public void searchForMinerals(Iterable<GOTBiome> biomes, Collection<String> minerals) {
		for (GOTBiome biome : biomes) {
			if (biome != null) {
				List<OreGenerant> oreGenerants = new ArrayList<>(biome.decorator.biomeSoils);
				oreGenerants.addAll(biome.decorator.biomeOres);
				oreGenerants.addAll(biome.decorator.biomeGems);
				for (OreGenerant oreGenerant : oreGenerants) {
					WorldGenMinable gen = oreGenerant.oreGen;
					Block block = GOTReflection.getOreBlock(gen);
					int meta = GOTReflection.getOreMeta(gen);
					if (block instanceof GOTBlockOreGem || block instanceof BlockDirt || block instanceof GOTBlockRock) {
						minerals.add(getBlockMetaName(block, meta));
					} else {
						minerals.add(getBlockName(block));
					}
				}
			}
		}
	}

	public void searchForPagenamesBiome(Iterable<GOTBiome> biomes, Iterable<GOTFaction> factions) {
		next: for (GOTBiome biome : biomes) {
			if (biome != null) {
				String preName = getBiomeName(biome);
				for (GOTFaction fac : factions) {
					if (preName.equals(getFactionName(fac))) {
						biomePageMapping.put(preName, preName + " (" + biomePage + ")");
						continue next;
					}
				}
				for (Class<? extends Entity> entityClass : GOTEntityRegistry.entitySet) {
					if (preName.equals(getEntityName(entityClass))) {
						biomePageMapping.put(preName, preName + " (" + biomePage + ")");
						continue next;
					}
				}
				biomePageMapping.put(preName, preName);
			}
		}
	}

	public void searchForPagenamesEntity(Iterable<GOTBiome> biomes, Iterable<GOTFaction> factions) {
		next: for (Class<? extends Entity> entityClass : GOTEntityRegistry.entitySet) {
			String preName = getEntityName(entityClass);
			for (GOTBiome biome : biomes) {
				if (biome != null && preName.equals(getBiomeName(biome))) {
					entityPageMapping.put(preName, preName + " (" + entityPage + ")");
					continue next;
				}
			}
			for (GOTFaction fac : factions) {
				if (preName.equals(getFactionName(fac))) {
					entityPageMapping.put(preName, preName + " (" + entityPage + ")");
					continue next;
				}
			}
			entityPageMapping.put(preName, preName);
		}
	}

	public void searchForPagenamesFaction(Iterable<GOTBiome> biomes, Iterable<GOTFaction> factions) {
		next: for (GOTFaction fac : factions) {
			String preName = getFactionName(fac);
			for (GOTBiome biome : biomes) {
				if (biome != null && preName.equals(getBiomeName(biome))) {
					factionPageMapping.put(preName, preName + " (" + factionPage + ")");
					continue next;
				}
			}
			for (Class<? extends Entity> entityClass : GOTEntityRegistry.entitySet) {
				if (preName.equals(getEntityName(entityClass))) {
					factionPageMapping.put(preName, preName + " (" + factionPage + ")");
					continue next;
				}
			}
			factionPageMapping.put(preName, preName);
		}
	}

	public void searchForStructures(Iterable<GOTBiome> biomes, Collection<Class<? extends WorldGenerator>> structures) {
		for (GOTBiome biome : biomes) {
			if (biome != null && !biome.decorator.randomStructures.isEmpty()) {
				for (RandomStructure structure : biome.decorator.randomStructures) {
					structures.add(structure.structureGen.getClass());
				}
			}
		}
	}

	public enum Database {
		XML("xml"), TABLES("tables");

		public String codeName;

		Database(String name) {
			codeName = name;
		}

		public String getCodename() {
			return codeName;
		}

		public boolean matchesNameOrAlias(String name) {
			return codeName.equals(name);
		}

		public static Database forName(String name) {
			for (Database db : Database.values()) {
				if (db.matchesNameOrAlias(name)) {
					return db;
				}
			}
			return null;
		}

		public static List<String> getNames() {
			ArrayList<String> names = new ArrayList<>();
			for (Database db : Database.values()) {
				names.add(db.getCodename());
			}
			return names;
		}
	}
}