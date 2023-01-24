package got.common.util;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

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
import got.common.faction.GOTFaction;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.GOTBiomeDecorator.*;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.biome.variant.GOTBiomeVariantList.VariantBucket;
import got.common.world.feature.GOTTreeType;
import got.common.world.feature.GOTTreeType.WeightedTreeType;
import got.common.world.map.*;
import got.common.world.spawning.GOTBiomeSpawnList.*;
import got.common.world.spawning.GOTSpawnEntry;
import got.common.world.structure.other.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.*;

public class DatabaseGenerator extends GOTStructureBase {
	public static String display = "null";
	private static HashMap<Class, Entity> classToObjectMapping = new HashMap<>();
	private static HashMap<Class, GOTWaypoint> classToWaypointMapping = new HashMap<>();
	private static HashMap<String, String> factionPageMapping = new HashMap<>();
	private static HashMap<String, String> entityPageMapping = new HashMap<>();
	private static HashMap<String, String> biomePageMapping = new HashMap<>();
	private static String riderLoc = StatCollector.translateToLocal("db.rider.name");
	private static String categoryTemplates = StatCollector.translateToLocal("db.categoryTemplates.name");
	private static String biomeNoVariants = StatCollector.translateToLocal("db.biomeNoVariants.name");
	private static String biomeNoInvasions = StatCollector.translateToLocal("db.biomeNoInvasions.name");
	private static String biomeHasInvasions = StatCollector.translateToLocal("db.biomeHasInvasions.name");
	private static String biomeNoTrees = StatCollector.translateToLocal("db.biomeNoTrees.name");
	private static String biomeNoAnimals = StatCollector.translateToLocal("db.biomeNoAnimals.name");
	private static String biomeNoStructures = StatCollector.translateToLocal("db.biomeNoStructures.name");
	private static String biomeLoc = StatCollector.translateToLocal("db.biomeLoc.name");
	private static String factionNoEnemies = StatCollector.translateToLocal("db.factionNoEnemies.name");
	private static String factionNoFriends = StatCollector.translateToLocal("db.factionNoFriends.name");
	private static String factionNoCharacters = StatCollector.translateToLocal("db.factionNoCharacters.name");
	private static String factionLoc = StatCollector.translateToLocal("db.factionLoc.name");
	private static String factionNotViolent = StatCollector.translateToLocal("db.factionNotViolent.name");
	private static String factionIsViolent = StatCollector.translateToLocal("db.factionIsViolent.name");
	private static String factionNoStructures = StatCollector.translateToLocal("db.factionNoStructures.name");
	private static String noPledge = StatCollector.translateToLocal("db.noPledge.name");
	private static String rep = StatCollector.translateToLocal("db.rep.name");
	private static String yesPledge = StatCollector.translateToLocal("db.yesPledge.name");
	private static String entityLoc = StatCollector.translateToLocal("db.entityLoc.name");
	private static String biomeNoConquest = StatCollector.translateToLocal("db.biomeNoConquest.name");
	private static String biomeNoSpawn = StatCollector.translateToLocal("db.biomeNoSpawn.name");
	private static String biomeHasSpawn = StatCollector.translateToLocal("db.biomeHasSpawn.name");
	private static String biomeHasConquest = StatCollector.translateToLocal("db.biomeHasConquest.name");
	private static String biomeConquestOnly = StatCollector.translateToLocal("db.biomeConquestOnly.name");
	private static String biomeSpawnOnly = StatCollector.translateToLocal("db.biomeSpawnOnly.name");
	private static String biomeHasStructures = StatCollector.translateToLocal("db.biomeHasStructures.name");
	private static String biomeHasTrees1 = StatCollector.translateToLocal("db.biomeHasTrees1.name");
	private static String biomeHasTrees2 = StatCollector.translateToLocal("db.biomeHasTrees2.name");
	private static String biomeMinerals = StatCollector.translateToLocal("db.biomeMinerals.name");
	private static String biomeHasAnimals = StatCollector.translateToLocal("db.biomeHasAnimals.name");

	public DatabaseGenerator(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int y, int j, int k, int rotation) {
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

		for (Class clazz : GOTEntityRegistry.allEntities) {
			classToObjectMapping.put(clazz, GOTReflection.newEntity(clazz, world));
		}

		try {
			List<Item> itmlist = GOTAPI.getObjectFieldsOfType(GOTRegistry.class, Item.class);
			List<GOTUnitTradeEntries> trdntrlist = GOTAPI.getObjectFieldsOfType(GOTUnitTradeEntries.class, GOTUnitTradeEntries.class);
			List<GOTAchievement> achlist = GOTAPI.getObjectFieldsOfType(GOTAchievement.class, GOTAchievement.class);
			List<GOTBiome> bmlist = GOTAPI.getObjectFieldsOfType(GOTBiome.class, GOTBiome.class);
			bmlist.remove(GOTBiome.beach);
			List<GOTFaction> fclist = new ArrayList<>(EnumSet.allOf(GOTFaction.class));

			if ("tables".equals(display)) {
				PrintWriter achievements = new PrintWriter("achievements.txt", "UTF-8");
				for (GOTAchievement ach : achlist) {
					if (ach != null) {
						achievements.println("| " + getAchievementTitle(ach) + " || " + getAchievementDesc(ach));
						achievements.println("|-");
					}
				}
				achievements.close();

				PrintWriter shields = new PrintWriter("shields.txt", "UTF-8");
				for (GOTShields shield : GOTShields.values()) {
					shields.println("| " + shield.getShieldName() + " || " + shield.getShieldDesc() + " || " + getShieldFilename(shield));
					shields.println("|-");
				}
				shields.close();

				PrintWriter capes = new PrintWriter("capes.txt", "UTF-8");
				for (GOTCapes cape : GOTCapes.values()) {
					capes.println("| " + cape.getCapeName() + " || " + cape.getCapeDesc() + " || " + getCapeFilename(cape));
					capes.println("|-");
				}
				capes.close();

				PrintWriter units = new PrintWriter("units.txt", "UTF-8");
				for (GOTUnitTradeEntries entries : trdntrlist) {
					if (entries != null) {
						for (GOTUnitTradeEntry entry : entries.tradeEntries) {
							if (entry != null) {
								if (entry.getPledgeType() == PledgeType.NONE) {
									if (entry.mountClass == null) {
										units.println("| " + getEntityLink(entry.entityClass) + " || {{Coins|" + entry.initialCost * 2 + "}} || {{Coins|" + entry.initialCost + "}} || +" + entry.alignmentRequired + " || - ");
									} else {
										units.println("| " + getEntityLink(entry.entityClass) + " || {{Coins|" + entry.initialCost * 2 + "}} (" + riderLoc + ") || {{Coins|" + entry.initialCost + "}} || +" + entry.alignmentRequired + " || - ");
									}
								} else if (entry.mountClass == null) {
									if (entry.alignmentRequired < 101.0f) {
										units.println("| " + getEntityLink(entry.entityClass) + " || - || {{Coins|" + entry.initialCost + "}} || +100.0 || + ");
									} else {
										units.println("| " + getEntityLink(entry.entityClass) + " || - || {{Coins|" + entry.initialCost + "}} || +" + entry.alignmentRequired + " || + ");
									}
								} else if (entry.alignmentRequired < 101.0f) {
									units.println("| " + getEntityLink(entry.entityClass) + " || - || {{Coins|" + entry.initialCost + "}} (" + riderLoc + ") || +100.0 || + ");
								} else {
									units.println("| " + getEntityLink(entry.entityClass) + " || - || {{Coins|" + entry.initialCost + "}} (" + riderLoc + ") || +" + entry.alignmentRequired + " || + ");
								}
								units.println("|-");
							}
						}
					}
				}
				units.close();

				PrintWriter waypoints = new PrintWriter("waypoints.txt", "UTF-8");
				for (GOTWaypoint wp : GOTWaypoint.values()) {
					waypoints.println("| " + wp.getDisplayName() + " || " + wp.getLoreText(usingPlayer));
					waypoints.println("|-");
				}
				waypoints.close();

				PrintWriter armor = new PrintWriter("armor.txt", "UTF-8");
				for (Item item : itmlist) {
					if (item instanceof ItemArmor) {
						float damage = ((ItemArmor) item).damageReduceAmount;
						ArmorMaterial material = ((ItemArmor) item).getArmorMaterial();
						if (material != null && material.customCraftingMaterial != null) {
							armor.println("| " + getItemName(item) + " || " + getItemFilename(item) + " || " + item.getMaxDamage() + " || " + damage + " || " + getItemName(material.customCraftingMaterial));
						} else {
							armor.println("| " + getItemName(item) + " || " + getItemFilename(item) + " || " + item.getMaxDamage() + " || " + damage + " || - ");
						}
						armor.println("|-");
					}
				}
				armor.close();

				PrintWriter weapon = new PrintWriter("weapon.txt", "UTF-8");
				for (Item item : itmlist) {
					if (item instanceof ItemSword) {
						float damage = GOTReflection.getDamageAmount(item);
						ToolMaterial material = GOTReflection.getToolMaterial(item);
						if (material.getRepairItemStack() != null) {
							weapon.println("| " + getItemName(item) + " || " + getItemFilename(item) + " || " + item.getMaxDamage() + " || " + damage + " || " + getItemName(material.getRepairItemStack().getItem()));
						} else {
							weapon.println("| " + getItemName(item) + " || " + getItemFilename(item) + " || " + item.getMaxDamage() + " || " + damage + " || - ");
						}
						weapon.println("|-");
					}
				}
				weapon.close();

				PrintWriter food = new PrintWriter("food.txt", "UTF-8");
				for (Item item : itmlist) {
					if (item instanceof ItemFood && item != null) {
						int healAmount = ((ItemFood) item).func_150905_g(null);
						float saturationModifier = ((ItemFood) item).func_150906_h(null);
						food.println("| " + getItemName(item) + " || " + getItemFilename(item) + " || " + "{{Bar|bread|" + new DecimalFormat("#.##").format(saturationModifier * healAmount * 2) + "}} || {{Bar|food|" + healAmount + "}} || " + item.getItemStackLimit());
						food.println("|-");
					}
				}
				food.close();

			} else if ("xml".equals(display)) {
				PrintWriter xml = new PrintWriter("xml.txt", "UTF-8");
				xml.println("<mediawiki xmlns=\"http://www.mediawiki.org/xml/export-0.11/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.mediawiki.org/xml/export-0.11/ http://www.mediawiki.org/xml/export-0.11.xsd\" version=\"0.11\" xml:lang=\"ru\">");

				/* ALL PAGES */

				for (Class mob : classToObjectMapping.keySet()) {
					String s1 = "<page><title>";
					String s2 = "</title><revision><text>{{\u0421\u0442\u0430\u0442\u044C\u044F \u041C\u043E\u0431}}</text></revision></page>";
					xml.print(s1 + searchEntityPagename(mob, bmlist, fclist) + s2);
					xml.println();
				}
				for (GOTBiome biome : bmlist) {
					if (biome != null) {
						String s1 = "<page><title>";
						String s2 = "</title><revision><text>{{\u0421\u0442\u0430\u0442\u044C\u044F \u0411\u0438\u043E\u043C}}</text></revision></page>";
						xml.print(s1 + searchBiomePagename(biome, fclist, GOTEntityRegistry.allEntities) + s2);
						xml.println();
					}
				}
				for (GOTFaction fac : fclist) {
					String s1 = "<page><title>";
					String s2 = "</title><revision><text>{{\u0421\u0442\u0430\u0442\u044C\u044F \u0424\u0440\u0430\u043A\u0446\u0438\u044F}}</text></revision></page>";
					xml.print(s1 + searchFactionPagename(fac, bmlist, GOTEntityRegistry.allEntities) + s2);
					xml.println();
				}

				/* DATABASES */

				String begin = "</title><ns>10</ns><revision><text>&lt;includeonly&gt;{{#switch: {{{1}}}";
				String end = "}}&lt;/includeonly&gt;&lt;noinclude&gt;[[" + categoryTemplates + "]]&lt;/noinclude&gt;</text></revision></page>";

				/* BIOMES */

				xml.print("<page><title>Template:DB Biome-SpawnNPC");
				xml.println(begin);
				for (GOTBiome biome : bmlist) {
					if (biome != null) {
						List<FactionContainer> facConts = biome.npcSpawnList.factionContainers;
						if (facConts.isEmpty()) {
							xml.println("| " + getBiomePagename(biome) + " = " + biomeNoSpawn);
						} else {
							ArrayList<FactionContainer> spawnConts = new ArrayList<>();
							for (FactionContainer cont : facConts) {
								if (cont.baseWeight > 0) {
									spawnConts.add(cont);
								}
							}
							if (spawnConts.isEmpty()) {
								xml.println("| " + getBiomePagename(biome) + " = " + biomeConquestOnly);
							} else {
								xml.println("| " + getBiomePagename(biome) + " = " + biomeHasSpawn);
								for (FactionContainer spawnCont : spawnConts) {
									for (SpawnListContainer one : spawnCont.spawnLists) {
										for (GOTSpawnEntry entry : one.spawnList.spawnList) {
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
				for (GOTBiome biome : bmlist) {
					if (biome != null) {
						List<FactionContainer> facConts = biome.npcSpawnList.factionContainers;
						if (facConts.isEmpty()) {
							xml.println("| " + getBiomePagename(biome) + " = " + biomeNoConquest);
						} else {
							ArrayList<FactionContainer> conqConts = new ArrayList<>();
							for (FactionContainer cont : facConts) {
								if (cont.baseWeight <= 0) {
									conqConts.add(cont);
								}
							}
							if (conqConts.isEmpty()) {
								xml.println("| " + getBiomePagename(biome) + " = " + biomeSpawnOnly);
							} else {
								xml.println("| " + getBiomePagename(biome) + " = " + biomeHasConquest);
								for (FactionContainer conqCont : conqConts) {
									HashSet<GOTFaction> facs = new HashSet<>();
									for (SpawnListContainer one : conqCont.spawnLists) {
										for (GOTSpawnEntry entry : one.spawnList.spawnList) {
											Entity entity = classToObjectMapping.get(entry.entityClass);
											if (entity instanceof GOTEntityNPC) {
												GOTFaction fac = ((GOTEntityNPC) entity).getFaction();
												facs.add(fac);
											}
										}
									}
									for (GOTFaction fac : facs) {
										xml.println("* " + getFactionLink(fac) + "; ");
									}
								}
							}
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Biome-Bandits");
				xml.println(begin);
				for (GOTBiome biome : bmlist) {
					if (biome != null) {
						xml.println("| " + getBiomePagename(biome) + " = " + biome.banditChance);
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Biome-Name");
				xml.println(begin);
				for (GOTBiome biome : bmlist) {
					if (biome != null) {
						xml.println("| " + getBiomePagename(biome) + " = " + getBiomeName(biome));
					}
				}
				xml.println(end);

				boolean lotrStyle = false;
				if (lotrStyle) {
					xml.print("<page><title>Template:DB Biome-Rainfall");
					xml.println(begin);
					for (GOTBiome biome : bmlist) {
						if (biome != null) {
							xml.println("| " + getBiomePagename(biome) + " = " + biome.rainfall);
						}
					}
					xml.println(end);

					xml.print("<page><title>Template:DB Biome-Temperature");
					xml.println(begin);
					for (GOTBiome biome : bmlist) {
						if (biome != null) {
							xml.println("| " + getBiomePagename(biome) + " = " + biome.temperature);
						}
					}
				} else {
					xml.print("<page><title>Template:DB Biome-Climat");
					xml.println(begin);
					for (GOTBiome biome : bmlist) {
						if (biome != null) {
							xml.println("| " + getBiomePagename(biome) + " = " + biome.climat);
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Biome-Variants");
				xml.println(begin);
				for (GOTBiome biome : bmlist) {
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
				for (GOTBiome biome : bmlist) {
					if (biome != null) {
						if (biome.invasionSpawns.registeredInvasions.isEmpty()) {
							xml.println("| " + getBiomePagename(biome) + " = " + biomeNoInvasions);
						} else {
							xml.println("| " + getBiomePagename(biome) + " = " + biomeHasInvasions);
							HashSet<GOTFaction> facs = new HashSet<>();
							for (GOTInvasions inv : biome.invasionSpawns.registeredInvasions) {
								for (InvasionSpawnEntry entry : inv.invasionMobs) {
									Entity entity = classToObjectMapping.get(entry.entityClass);
									if (entity instanceof GOTEntityNPC) {
										GOTFaction fac = ((GOTEntityNPC) entity).getFaction();
										facs.add(fac);
									}
								}
							}
							for (GOTFaction fac : facs) {
								xml.println("* " + getFactionLink(fac) + ";");
							}
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Biome-Height");
				xml.println(begin);
				for (GOTBiome biome : bmlist) {
					if (biome != null) {
						xml.println("| " + getBiomePagename(biome) + " = " + biome.heightBaseParameter);
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Biome-Trees");
				xml.println(begin);
				for (GOTBiome biome : bmlist) {
					if (biome != null) {
						HashSet<GOTTreeType> trees1 = new HashSet<>();
						HashMap<GOTTreeType, GOTBiomeVariant> trees2 = new HashMap<>();
						for (WeightedTreeType treeType : biome.decorator.treeTypes) {
							trees1.add(treeType.treeType);
						}
						for (VariantBucket variantBucket : biome.getBiomeVariantsSmall().variantList) {
							for (WeightedTreeType treeType : variantBucket.variant.treeTypes) {
								trees2.put(treeType.treeType, variantBucket.variant);
							}
						}
						if (trees1.isEmpty() && trees2.isEmpty()) {
							xml.println("| " + getBiomePagename(biome) + " = " + biomeNoTrees);
						} else {
							HashMap<GOTTreeType, GOTBiomeVariant> remap = new HashMap<>();
							for (GOTTreeType standart : trees1) {
								for (GOTTreeType variant : trees2.keySet()) {
									if (!trees1.contains(variant)) {
										remap.put(variant, trees2.get(variant));
									}
								}
							}
							if (remap.isEmpty()) {
								xml.println("| " + getBiomePagename(biome) + " = " + biomeHasTrees1);
							} else {
								xml.println("| " + getBiomePagename(biome) + " = " + biomeHasTrees2);
							}
							for (GOTTreeType standart : trees1) {
								xml.println("* " + getTreeName(standart) + ";");
							}
							for (GOTTreeType variant : remap.keySet()) {
								xml.println("* " + getTreeName(variant) + " (" + getBiomeVariantName(remap.get(variant)).toLowerCase() + ")" + ";");
							}
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Biome-Mobs");
				xml.println(begin);
				for (GOTBiome biome : bmlist) {
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
				for (GOTBiome biome : bmlist) {
					if (biome != null) {
						xml.println("| " + getBiomePagename(biome) + " = " + biomeMinerals);
						if (biome != null) {
							List<OreGenerant> sus = new ArrayList<>(biome.decorator.biomeSoils);
							sus.addAll(biome.decorator.biomeOres);
							sus.addAll(biome.decorator.biomeGems);
							for (OreGenerant oreGenerant : sus) {
								WorldGenMinable gen = oreGenerant.oreGen;
								Block block = GOTReflection.getOreBlock(gen);
								int meta = GOTReflection.getOreMeta(gen);

								float oreChance = oreGenerant.oreChance;
								int minHeight = oreGenerant.minHeight;
								int maxHeight = oreGenerant.maxHeight;

								if (block instanceof GOTBlockOreGem || block instanceof BlockDirt || block instanceof GOTBlockRock) {
									xml.println("* " + getBlockMetaName(block, meta) + " (" + oreChance + "%; Y: " + minHeight + "-" + maxHeight + ");");
								} else {
									xml.println("* " + getBlockName(block) + " (" + oreChance + "%; Y: " + minHeight + "-" + maxHeight + ");");
								}
							}
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Biome-HeightVariation");
				xml.println(begin);
				for (GOTBiome biome : bmlist) {
					if (biome != null) {
						xml.println("| " + getBiomePagename(biome) + " = " + biome.heightVariation);
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Biome-Music");
				xml.println(begin);
				for (GOTBiome biome : bmlist) {
					if (biome != null) {
						xml.println("| " + getBiomePagename(biome) + " = " + biome.biomeName);
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Biome-Structures");
				xml.println(begin);
				for (GOTBiome biome : bmlist) {
					if (biome != null) {
						if (biome.decorator.randomStructures.isEmpty()) {
							xml.println("| " + getBiomePagename(biome) + " = " + biomeNoStructures);
						} else {
							xml.println("| " + getBiomePagename(biome) + " = " + biomeHasStructures);
							for (RandomStructure structure : biome.decorator.randomStructures) {
								xml.println("* " + getStructureName(structure.structureGen.getClass()) + ";");
							}
						}
					}
				}
				xml.println(end);

				// Marked for restruct
				xml.print("<page><title>Template:DB Biome-Photo");
				xml.println(begin);
				for (GOTBiome biome : bmlist) {
					if (biome != null) {
						if (biome != GOTBiome.thenn && biome != GOTBiome.mercenary) {
							xml.println("| " + getBiomePagename(biome) + " = " + biome.biomeName + ".png");
						} else {
							xml.println("| " + getBiomePagename(biome) + " = " + biome.biomeName + " (biome).png");
						}
					}
				}
				xml.println(end);

				/* FACTIONS */

				xml.print("<page><title>Template:DB Faction-NPC");
				xml.println(begin);
				for (GOTFaction fac : fclist) {
					xml.println("| " + getFactionPagename(fac) + " = ");
					for (Class mob : classToObjectMapping.keySet()) {
						Entity entity = classToObjectMapping.get(mob);
						if (entity instanceof GOTEntityNPC && ((GOTEntityNPC) entity).getFaction() == fac && !((GOTEntityNPC) entity).isLegendaryNPC()) {
							xml.println("* " + getEntityLink(mob) + ";");
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Faction-Chars");
				xml.println(begin);
				for (GOTFaction fac : fclist) {
					ArrayList<Class> chars = new ArrayList<>();
					for (Class mob : classToObjectMapping.keySet()) {
						Entity entity = classToObjectMapping.get(mob);
						if (entity instanceof GOTEntityNPC && ((GOTEntityNPC) entity).getFaction() == fac && ((GOTEntityNPC) entity).isLegendaryNPC()) {
							chars.add(mob);
						}
					}
					if (chars.isEmpty()) {
						xml.println("| " + getFactionPagename(fac) + " = " + factionNoCharacters);
					} else {
						xml.println("| " + getFactionPagename(fac) + " = ");
						for (Class mob : chars) {
							xml.println("* " + getEntityLink(mob) + ";");
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>Template:DB Faction-Enemies");
				xml.println(begin);
				for (GOTFaction fac1 : fclist) {
					ArrayList<GOTFaction> enemies = new ArrayList<>();
					for (GOTFaction fac2 : fclist) {
						if (fac1.isBadRelation(fac2) && fac1 != fac2) {
							enemies.add(fac2);
						}
					}
					if (enemies.isEmpty()) {
						xml.println("| " + getFactionPagename(fac1) + " = " + factionNoEnemies);
					} else {
						xml.println("| " + getFactionPagename(fac1) + " = ");
						int i = 0;
						for (GOTFaction fac : enemies) {
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

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0424\u0440\u0430\u043A\u0446\u0438\u044F-\u0414\u0440\u0443\u0437\u044C\u044F");
				xml.println(begin);
				for (GOTFaction fac1 : fclist) {
					ArrayList<GOTFaction> enemies = new ArrayList<>();
					for (GOTFaction fac2 : fclist) {
						if (fac1.isGoodRelation(fac2) && fac1 != fac2) {
							enemies.add(fac2);
						}
					}
					if (enemies.isEmpty()) {
						xml.println("| " + getFactionPagename(fac1) + " = " + factionNoFriends);
					} else {
						xml.println("| " + getFactionPagename(fac1) + " = ");
						int i = 0;
						for (GOTFaction fac : enemies) {
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

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0424\u0440\u0430\u043A\u0446\u0438\u044F-\u0416\u0435\u0441\u0442\u043E\u043A\u043E\u0441\u0442\u044C");
				xml.println(begin);
				xml.println("| #default = " + factionNotViolent);
				for (GOTFaction fac : fclist) {
					if (fac.approvesWarCrimes) {
						xml.println("| " + getFactionPagename(fac) + " = " + factionIsViolent);
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0424\u0440\u0430\u043A\u0446\u0438\u044F-\u041A\u043E\u0434");
				xml.println(begin);
				for (GOTFaction fac : fclist) {
					xml.println("| " + getFactionPagename(fac) + " = " + fac.codeName());
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0424\u0440\u0430\u043A\u0446\u0438\u044F-\u0420\u0435\u0433\u0438\u043E\u043D");
				xml.println(begin);
				for (GOTFaction fac : fclist) {
					if (fac.factionRegion != null) {
						xml.println("| " + getFactionPagename(fac) + " = " + fac.factionRegion.getRegionName());
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0424\u0440\u0430\u043A\u0446\u0438\u044F-\u0421\u0442\u0440\u0443\u043A\u0442\u0443\u0440\u044B");
				xml.println(begin);
				for (GOTFaction fac : fclist) {
					boolean empty = true;
					for (Class<? extends WorldGenerator> str : GOTStructureRegistry.classToFactionMapping.keySet()) {
						if (GOTStructureRegistry.classToFactionMapping.get(str) == fac) {
							empty = false;
							break;
						}
					}
					if (!empty) {
						xml.println("| " + getFactionPagename(fac) + " =");
						for (Class<? extends WorldGenerator> str : GOTStructureRegistry.classToFactionMapping.keySet()) {
							if (GOTStructureRegistry.classToFactionMapping.get(str) == fac) {
								xml.println("* " + getStructureName(str) + ";");
							}
						}
					} else {
						xml.println("| " + getFactionPagename(fac) + " = " + factionNoStructures);
					}
				}
				xml.println(end);

				/* MOBS */

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-NPC");
				xml.println(begin);
				for (Class mob : classToObjectMapping.keySet()) {
					if (classToObjectMapping.get(mob) instanceof GOTEntityNPC) {
						xml.println("| " + getEntityName(mob) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0410\u0433\u0440\u0435\u0441\u0441\u043E\u0440");
				xml.println(begin);
				for (Class mob : classToObjectMapping.keySet()) {
					if (classToObjectMapping.get(mob) instanceof GOTEntityNPC && ((GOTEntityNPC) classToObjectMapping.get(mob)).isTargetSeeker || classToObjectMapping.get(mob) instanceof EntityMob || classToObjectMapping.get(mob) instanceof GOTEntityNPC && ((GOTEntityNPC) classToObjectMapping.get(mob)).getFaction() == GOTFaction.HOSTILE) {
						xml.println("| " + getEntityName(mob) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0411\u0438\u043E\u043C");
				xml.println(begin);
				for (Class mob : classToObjectMapping.keySet()) {
					int i = 1;
					for (GOTBiome biome : bmlist) {
						List sus = new ArrayList(biome.getSpawnableList(EnumCreatureType.ambient));
						sus.addAll(biome.getSpawnableList(EnumCreatureType.waterCreature));
						sus.addAll(biome.getSpawnableList(EnumCreatureType.creature));
						sus.addAll(biome.getSpawnableList(EnumCreatureType.monster));
						sus.addAll(biome.spawnableGOTAmbientList);
						for (FactionContainer cont : biome.npcSpawnList.factionContainers) {
							for (SpawnListContainer one : cont.spawnLists) {
								sus.addAll(one.spawnList.spawnList);
							}
						}
						for (Object var : sus) {
							if (((SpawnListEntry) var).entityClass.equals(mob)) {
								if (i == 1) {
									xml.println("| " + getEntityName(mob) + " = ");
								}
								i++;
								xml.println("* " + getBiomeLink(biome) + ";");
							}
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0412\u043B\u0430\u0434\u0435\u043B\u0435\u0446");
				xml.println(begin);
				for (Class mob : classToObjectMapping.keySet()) {
					if (classToObjectMapping.get(mob) instanceof GOTUnitTradeable && !((GOTEntityNPC) classToObjectMapping.get(mob)).isLegendaryNPC()) {
						GOTUnitTradeEntries entries = ((GOTUnitTradeable) classToObjectMapping.get(mob)).getUnits();
						for (GOTUnitTradeEntry entry : entries.tradeEntries) {
							if (entry.mountClass == null) {
								xml.println("| " + getEntityName(entry.entityClass) + " = " + getEntityLink(mob));
							}
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0415\u0437\u0434\u043E\u0432\u043E\u0439");
				xml.println(begin);
				for (Class mob : classToObjectMapping.keySet()) {
					if (classToObjectMapping.get(mob) instanceof GOTEntityNPCRideable) {
						xml.println("| " + getEntityName(mob) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0417\u0434\u043E\u0440\u043E\u0432\u044C\u0435");
				xml.println(begin);
				for (Class mob : classToObjectMapping.keySet()) {
					xml.println("| " + getEntityName(mob) + " = " + ((EntityLivingBase) classToObjectMapping.get(mob)).getMaxHealth());
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0417\u043D\u0430\u043C\u0435\u043D\u043E\u0441\u0435\u0446");
				xml.println(begin);
				for (Class mob : classToObjectMapping.keySet()) {
					if (classToObjectMapping.get(mob) instanceof GOTBannerBearer) {
						xml.println("| " + getEntityName(mob) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0418\u043C\u044F");
				xml.println(begin);
				for (Class mob : classToObjectMapping.keySet()) {
					if (classToObjectMapping.get(mob) instanceof GOTEntityNPC) {
						xml.println("| " + getEntityName(mob) + " = " + ((GOTEntityNPC) classToObjectMapping.get(mob)).getNPCName());
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u041A\u043E\u043C\u0430\u043D\u0434\u0438\u0440");
				xml.println(begin);
				for (Class mob : classToObjectMapping.keySet()) {
					if (classToObjectMapping.get(mob) instanceof GOTUnitTradeable) {
						xml.println("| " + getEntityName(mob) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u041A\u0443\u0437\u043D\u0435\u0446");
				xml.println(begin);
				for (Class mob : classToObjectMapping.keySet()) {
					if (classToObjectMapping.get(mob) instanceof GOTTradeable.Smith) {
						xml.println("| " + getEntityName(mob) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u041B\u0435\u0433\u0435\u043D\u0434\u0430\u0440\u043D\u044B\u0439");
				xml.println(begin);
				for (Class mob : classToObjectMapping.keySet()) {
					if (classToObjectMapping.get(mob) instanceof GOTEntityNPC && ((GOTEntityNPC) classToObjectMapping.get(mob)).isLegendaryNPC) {
						xml.println("| " + getEntityName(mob) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u041C\u0430\u0443\u043D\u0442");
				xml.println(begin);
				for (Class mob : classToObjectMapping.keySet()) {
					if (classToObjectMapping.get(mob) instanceof GOTNPCMount) {
						xml.println("| " + getEntityName(mob) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u041C\u043E\u0440\u043E\u0437\u043E\u0443\u0441\u0442\u043E\u0439\u0447\u0438\u0432\u043E\u0441\u0442\u044C");
				xml.println(begin);
				for (Class mob : classToObjectMapping.keySet()) {
					if (classToObjectMapping.get(mob) instanceof GOTEntityNPC && ((GOTEntityNPC) classToObjectMapping.get(mob)).isImmuneToFrost || classToObjectMapping.get(mob) instanceof GOTBiome.ImmuneToFrost) {
						xml.println("| " + getEntityName(mob) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u041D\u0430\u0451\u043C\u043D\u0438\u043A");
				xml.println(begin);
				for (Class mob : classToObjectMapping.keySet()) {
					if (classToObjectMapping.get(mob) instanceof GOTMercenary) {
						xml.println("| " + getEntityName(mob) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u041D\u043E\u0447\u043D\u043E\u0439 \u0441\u043F\u0430\u0432\u043D");
				xml.println(begin);
				for (Class mob : classToObjectMapping.keySet()) {
					if (classToObjectMapping.get(mob) instanceof GOTEntityNPC && ((GOTEntityNPC) classToObjectMapping.get(mob)).spawnsInDarkness) {
						xml.println("| " + getEntityName(mob) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u041E\u0433\u043D\u0435\u0443\u043F\u043E\u0440\u043D\u043E\u0441\u0442\u044C");
				xml.println(begin);
				for (Class mob : classToObjectMapping.keySet()) {
					if (classToObjectMapping.get(mob).isImmuneToFire()) {
						xml.println("| " + getEntityName(mob) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u041F\u043E\u043A\u0443\u043F\u0430\u0435\u0442");
				xml.println(begin);
				for (Class mob : classToObjectMapping.keySet()) {
					if (classToObjectMapping.get(mob) instanceof GOTTradeable) {
						GOTTradeEntries entries = ((GOTTradeable) classToObjectMapping.get(mob)).getSellPool();
						xml.println("| " + getEntityName(mob) + " = ");
						for (GOTTradeEntry entry : entries.tradeEntries) {
							xml.println("* " + entry.tradeItem.getDisplayName() + ": {{Coins|" + entry.getCost() + "}};");
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u041F\u0440\u043E\u0434\u0430\u0451\u0442");
				xml.println(begin);
				for (Class mob : classToObjectMapping.keySet()) {
					if (classToObjectMapping.get(mob) instanceof GOTTradeable) {
						GOTTradeEntries entries = ((GOTTradeable) classToObjectMapping.get(mob)).getBuyPool();
						xml.println("| " + getEntityName(mob) + " = ");
						for (GOTTradeEntry entry : entries.tradeEntries) {
							xml.println("* " + entry.tradeItem.getDisplayName() + ": {{Coins|" + entry.getCost() + "}};");
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0420\u0430\u0431\u043E\u0442\u043D\u0438\u043A");
				xml.println(begin);
				for (Class mob : classToObjectMapping.keySet()) {
					if (classToObjectMapping.get(mob) instanceof GOTFarmhand) {
						xml.println("| " + getEntityName(mob) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0420\u0435\u043F\u0443\u0442\u0430\u0446\u0438\u044F");
				xml.println(begin);
				for (GOTUnitTradeEntries entries : GOTAPI.getObjectFieldsOfType(GOTUnitTradeEntries.class, GOTUnitTradeEntries.class)) {
					for (GOTUnitTradeEntry entry : entries.tradeEntries) {
						xml.println("| " + getEntityName(entry.entityClass) + " = +" + entry.alignmentRequired);
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0421\u0432\u0430\u0434\u044C\u0431\u0430");
				xml.println(begin);
				for (Class mob : classToObjectMapping.keySet()) {
					if (classToObjectMapping.get(mob) instanceof GOTEntityNPC && ((GOTEntityNPC) classToObjectMapping.get(mob)).canBeMarried) {
						xml.println("| " + getEntityName(mob) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0422\u043E\u0440\u0433\u043E\u0432\u0435\u0446");
				xml.println(begin);
				for (Class mob : classToObjectMapping.keySet()) {
					if (classToObjectMapping.get(mob) instanceof GOTTradeable) {
						xml.println("| " + getEntityName(mob) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0423\u0441\u0442\u043E\u0439\u0447\u0438\u0432\u044B\u0439 \u043A \u0436\u0430\u0440\u0435");
				xml.println(begin);
				for (Class mob : classToObjectMapping.keySet()) {
					if (classToObjectMapping.get(mob) instanceof GOTBiome.ImmuneToHeat) {
						xml.println("| " + getEntityName(mob) + " = True");
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0424\u043E\u0442\u043E");
				xml.println(begin);
				for (Class mob : classToObjectMapping.keySet()) {
					xml.println("| " + getEntityName(mob) + " = " + mob.getSimpleName().replace("GOTEntity", "") + ".png");
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0424\u0440\u0430\u043A\u0446\u0438\u044F");
				xml.println(begin);
				for (Class mob : classToObjectMapping.keySet()) {
					if (classToObjectMapping.get(mob) instanceof GOTEntityNPC) {
						xml.println("| " + getEntityName(mob) + " = " + getFactionPagename(((GOTEntityNPC) classToObjectMapping.get(mob)).getFaction()));
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0426\u0435\u043D\u0430");
				xml.println(begin);
				for (GOTUnitTradeEntries entries : GOTAPI.getObjectFieldsOfType(GOTUnitTradeEntries.class, GOTUnitTradeEntries.class)) {
					for (GOTUnitTradeEntry entry : entries.tradeEntries) {
						xml.println("| " + getEntityName(entry.entityClass) + " = {{Coins|" + entry.initialCost * 2 + "}}");
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0426\u0435\u043D\u0430\u041F\u0440\u0438\u0441\u044F\u0433\u0430");
				xml.println(begin);
				for (GOTUnitTradeEntries entries : GOTAPI.getObjectFieldsOfType(GOTUnitTradeEntries.class, GOTUnitTradeEntries.class)) {
					for (GOTUnitTradeEntry entry : entries.tradeEntries) {
						xml.println("| " + getEntityName(entry.entityClass) + " = {{Coins|" + entry.initialCost + "}}");
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0426\u0435\u043D\u043D\u043E\u0441\u0442\u044C");
				xml.println(begin);
				for (Class mob : classToObjectMapping.keySet()) {
					if (classToObjectMapping.get(mob) instanceof GOTEntityNPC) {
						xml.println("| " + getEntityName(mob) + " = " + ((GOTEntityNPC) classToObjectMapping.get(mob)).getAlignmentBonus());
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u042E\u043D\u0438\u0442\u044B");
				xml.println(begin);
				for (Class mob : classToObjectMapping.keySet()) {
					if (classToObjectMapping.get(mob) instanceof GOTUnitTradeable) {
						GOTUnitTradeEntries entries = ((GOTUnitTradeable) classToObjectMapping.get(mob)).getUnits();
						xml.println("| " + getEntityName(mob) + " = ");
						for (GOTUnitTradeEntry entry : entries.tradeEntries) {
							if (entry.mountClass == null) {
								xml.println("* " + getEntityLink(entry.entityClass) + ": {{Coins|" + entry.initialCost * 2 + "}} " + noPledge + ", {{Coins|" + entry.initialCost + "}} " + yesPledge + "; " + entry.alignmentRequired + "+ " + rep + ";");
							}
						}
					}
				}
				xml.println(end);

				xml.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0422\u043E\u0447\u043A\u0430");
				xml.println(begin);
				for (Class<? extends Entity> entity : classToWaypointMapping.keySet()) {
					xml.println("| " + getEntityName(entity) + " = " + classToWaypointMapping.get(entity).getDisplayName());
				}
				for (GOTWaypoint wp : GOTFixer.structures.keySet()) {
					GOTStructureBase str = GOTFixer.structures.get(wp);
					str.notGen = true;
					str.generate(world, random, y, j, k);
					for (EntityCreature entity : GOTFixer.structures.get(wp).legendaryChar) {
						xml.println("| " + getEntityName(entity.getClass()) + " = " + wp.getDisplayName());
					}
				}
				xml.println(end);
				xml.println("</mediawiki>");
				xml.close();
			}
		} catch (FileNotFoundException |

					UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return true;
	}

	private String getAchievementDesc(GOTAchievement ach) {
		return StatCollector.translateToLocal("got.achievement." + ach.getCodeName() + ".desc");
	}

	private String getAchievementTitle(GOTAchievement ach) {
		return StatCollector.translateToLocal("got.achievement." + ach.getCodeName() + ".title");
	}

	private String getBiomeLink(GOTBiome biome) {
		String biomeName = getBiomeName(biome);
		String biomePagename = getBiomePagename(biome);
		if (biomeName.equals(biomePagename)) {
			return "[[" + biomeName + "]]";
		}
		return "[[" + biomePagename + "|" + biomeName + "]]";
	}

	private String getBiomeName(GOTBiome biome) {
		return StatCollector.translateToLocal("got.biome." + biome.biomeName);
	}

	private String getBiomePagename(GOTBiome biome) {
		String biomeName = getBiomeName(biome);
		return biomePageMapping.get(biomeName);
	}

	private String getFactionPagename(GOTFaction fac) {
		String facName = fac.factionName();
		return factionPageMapping.get(facName);
	}

	private String getEntityPagename(Class clazz) {
		String entityName = getEntityName(clazz);
		return entityPageMapping.get(entityName);
	}

	private String getBiomeVariantName(GOTBiomeVariant variant) {
		return StatCollector.translateToLocal("got.variant." + variant.variantName + ".name");
	}

	private String getBlockMetaName(Block block, int meta) {
		return StatCollector.translateToLocal(block.getUnlocalizedName() + "." + meta + ".name");
	}

	private String getBlockName(Block block) {
		return StatCollector.translateToLocal(block.getUnlocalizedName() + ".name");
	}

	private String getCapeFilename(GOTCapes cape) {
		return "[[File:Cape " + cape.name().toLowerCase() + ".png]]";
	}

	private String getEntityLink(Class clazz) {
		String entityName = getEntityName(clazz);
		String entityPagename = getEntityPagename(clazz);
		if (entityName.equals(entityPagename)) {
			return "[[" + entityPagename + "]]";
		}
		return "[[" + entityPagename + "|" + entityName + "]]";
	}

	private String getEntityName(Class<? extends Entity> entityClass) {
		return StatCollector.translateToLocal("entity.got." + GOTEntityRegistry.classToNameMapping.get(entityClass) + ".name");
	}

	private String getEntityVanillaName(Class<? extends Entity> entityClass) {
		return StatCollector.translateToLocal("entity." + EntityList.classToStringMapping.get(entityClass) + ".name");
	}

	private String getFactionLink(GOTFaction fac) {
		String facName = fac.factionName();
		String facPagename = getFactionPagename(fac);
		if (facName.equals(facPagename)) {
			return "[[" + facPagename + "]]";
		}
		return "[[" + facPagename + "|" + facName + "]]";
	}

	private String getItemFilename(Item item) {
		return "[[File:" + item.getUnlocalizedName().substring("item.got:".length()) + ".png|32px|link=]]";
	}

	private String getItemName(Item item) {
		return StatCollector.translateToLocal(item.getUnlocalizedName() + ".name");
	}

	private String getShieldFilename(GOTShields shield) {
		return "[[File:Shield " + shield.name().toLowerCase() + ".png]]";
	}

	private String getStructureName(Class entityClass) {
		return StatCollector.translateToLocal("got.structure." + GOTStructureRegistry.classToNameMapping.get(entityClass) + ".name");
	}

	private String getTreeName(GOTTreeType treeType) {
		return StatCollector.translateToLocal("got.tree." + treeType.name().toLowerCase() + ".name");
	}

	private String searchBiomePagename(GOTBiome biome, List<GOTFaction> facList, List<Class> entityList) {
		String preName = getBiomeName(biome);
		for (GOTFaction fac : facList) {
			if (preName.equals(fac.factionName())) {
				biomePageMapping.put(preName, preName + " (" + biomeLoc + ")");
				return preName + " (" + biomeLoc + ")";
			}
		}
		for (Class entity : entityList) {
			if (preName.equals(getEntityName(entity))) {
				biomePageMapping.put(preName, preName + " (" + biomeLoc + ")");
				return preName + " (" + biomeLoc + ")";
			}
		}
		biomePageMapping.put(preName, preName);
		return preName;
	}

	private String searchEntityPagename(Class entity, List<GOTBiome> bmList, List<GOTFaction> facList) {
		String preName = getEntityName(entity);
		for (GOTBiome biome : bmList) {
			if (preName.equals(getBiomeName(biome))) {
				entityPageMapping.put(preName, preName + " (" + entityLoc + ")");
				return preName + " (" + entityLoc + ")";
			}
		}
		for (GOTFaction fac : facList) {
			if (preName.equals(fac.factionName())) {
				entityPageMapping.put(preName, preName + " (" + entityLoc + ")");
				return preName + " (" + entityLoc + ")";
			}
		}
		entityPageMapping.put(preName, preName);
		return preName;
	}

	private String searchFactionPagename(GOTFaction fac, List<GOTBiome> bmList, List<Class> entityList) {
		String preName = fac.factionName();
		for (GOTBiome biome : bmList) {
			if (preName.equals(getBiomeName(biome))) {
				factionPageMapping.put(preName, preName + " (" + factionLoc + ")");
				return preName + " (" + factionLoc + ")";
			}
		}
		for (Class entity : entityList) {
			if (preName.equals(getEntityName(entity))) {
				factionPageMapping.put(preName, preName + " (" + factionLoc + ")");
				return preName + " (" + factionLoc + ")";
			}
		}
		factionPageMapping.put(preName, preName);
		return preName;
	}

	public enum Database {
		XML("xml"), TABLES("tables");

		String codeName;

		Database(String string) {
			codeName = string;
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