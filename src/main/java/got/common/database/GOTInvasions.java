package got.common.database;

import got.common.entity.essos.braavos.GOTEntityBraavosBannerBearer;
import got.common.entity.essos.braavos.GOTEntityBraavosSoldier;
import got.common.entity.essos.braavos.GOTEntityBraavosSoldierArcher;
import got.common.entity.essos.dothraki.GOTEntityDothraki;
import got.common.entity.essos.dothraki.GOTEntityDothrakiArcher;
import got.common.entity.essos.ghiscar.GOTEntityGhiscarBannerBearer;
import got.common.entity.essos.ghiscar.GOTEntityGhiscarSoldier;
import got.common.entity.essos.ghiscar.GOTEntityGhiscarSoldierArcher;
import got.common.entity.essos.ibben.GOTEntityIbbenSoldierArcher;
import got.common.entity.essos.ibben.GOTEntityIbbenBannerBearer;
import got.common.entity.essos.ibben.GOTEntityIbbenSoldier;
import got.common.entity.essos.jogos_nhai.GOTEntityJogosNhaiMan;
import got.common.entity.essos.jogos_nhai.GOTEntityJogosNhaiArcher;
import got.common.entity.essos.lorath.GOTEntityLorathBannerBearer;
import got.common.entity.essos.lorath.GOTEntityLorathSoldier;
import got.common.entity.essos.lorath.GOTEntityLorathSoldierArcher;
import got.common.entity.essos.lys.GOTEntityLysBannerBearer;
import got.common.entity.essos.lys.GOTEntityLysSoldier;
import got.common.entity.essos.lys.GOTEntityLysSoldierArcher;
import got.common.entity.essos.myr.GOTEntityMyrBannerBearer;
import got.common.entity.essos.myr.GOTEntityMyrSoldier;
import got.common.entity.essos.myr.GOTEntityMyrSoldierArcher;
import got.common.entity.essos.norvos.*;
import got.common.entity.essos.pentos.*;
import got.common.entity.essos.tyrosh.GOTEntityTyroshBannerBearer;
import got.common.entity.essos.tyrosh.GOTEntityTyroshSoldier;
import got.common.entity.essos.tyrosh.GOTEntityTyroshSoldierArcher;
import got.common.entity.essos.volantis.GOTEntityVolantisBannerBearer;
import got.common.entity.essos.volantis.GOTEntityVolantisSoldier;
import got.common.entity.essos.volantis.GOTEntityVolantisSoldierArcher;
import got.common.entity.essos.yi_ti.*;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.westeros.arryn.GOTEntityArrynBannerBearer;
import got.common.entity.westeros.arryn.GOTEntityArrynSoldier;
import got.common.entity.westeros.arryn.GOTEntityArrynSoldierArcher;
import got.common.entity.westeros.dorne.GOTEntityDorneBannerBearer;
import got.common.entity.westeros.dorne.GOTEntityDorneSoldier;
import got.common.entity.westeros.dorne.GOTEntityDorneSoldierArcher;
import got.common.entity.westeros.dragonstone.GOTEntityDragonstoneBannerBearer;
import got.common.entity.westeros.dragonstone.GOTEntityDragonstoneSoldier;
import got.common.entity.westeros.dragonstone.GOTEntityDragonstoneSoldierArcher;
import got.common.entity.westeros.hillmen.GOTEntityHillmanArcher;
import got.common.entity.westeros.hillmen.GOTEntityHillmanBannerBearer;
import got.common.entity.westeros.hillmen.GOTEntityHillmanBerserker;
import got.common.entity.westeros.hillmen.GOTEntityHillmanWarrior;
import got.common.entity.westeros.ironborn.GOTEntityIronbornBannerBearer;
import got.common.entity.westeros.ironborn.GOTEntityIronbornSoldier;
import got.common.entity.westeros.ironborn.GOTEntityIronbornSoldierArcher;
import got.common.entity.westeros.north.GOTEntityNorthBannerBearer;
import got.common.entity.westeros.north.GOTEntityNorthSoldier;
import got.common.entity.westeros.north.GOTEntityNorthSoldierArcher;
import got.common.entity.westeros.reach.GOTEntityReachBannerBearer;
import got.common.entity.westeros.reach.GOTEntityReachSoldier;
import got.common.entity.westeros.reach.GOTEntityReachSoldierArcher;
import got.common.entity.westeros.riverlands.GOTEntityRiverlandsBannerBearer;
import got.common.entity.westeros.riverlands.GOTEntityRiverlandsSoldier;
import got.common.entity.westeros.riverlands.GOTEntityRiverlandsSoldierArcher;
import got.common.entity.westeros.stormlands.GOTEntityStormlandsBannerBearer;
import got.common.entity.westeros.stormlands.GOTEntityStormlandsSoldier;
import got.common.entity.westeros.stormlands.GOTEntityStormlandsSoldierArcher;
import got.common.entity.westeros.westerlands.GOTEntityWesterlandsBannerBearer;
import got.common.entity.westeros.westerlands.GOTEntityWesterlandsSoldier;
import got.common.entity.westeros.westerlands.GOTEntityWesterlandsSoldierArcher;
import got.common.entity.westeros.wildling.*;
import got.common.entity.westeros.wildling.thenn.GOTEntityThennArcher;
import got.common.entity.westeros.wildling.thenn.GOTEntityThennBannerBearer;
import got.common.entity.westeros.wildling.thenn.GOTEntityThennWarrior;
import got.common.faction.GOTFaction;
import got.common.item.other.GOTItemWarhorn;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.util.WeightedRandom;

import java.util.ArrayList;
import java.util.Collection;

public enum GOTInvasions {
	WILDLING(GOTFaction.WILDLING), THENN(GOTFaction.WILDLING, "THENN"), GIANT(GOTFaction.WILDLING, "GIANT"), NORTH(GOTFaction.NORTH), IRONBORN(GOTFaction.IRONBORN), WESTERLANDS(GOTFaction.WESTERLANDS), RIVERLANDS(GOTFaction.RIVERLANDS), HILL_TRIBES(GOTFaction.HILL_TRIBES), ARRYN(GOTFaction.ARRYN), DRAGONSTONE(GOTFaction.DRAGONSTONE), STORMLANDS(GOTFaction.STORMLANDS), REACH(GOTFaction.REACH), DORNE(GOTFaction.DORNE), BRAAVOS(GOTFaction.BRAAVOS), VOLANTIS(GOTFaction.VOLANTIS), PENTOS(GOTFaction.PENTOS), NORVOS(GOTFaction.NORVOS), LORATH(GOTFaction.LORATH), MYR(GOTFaction.MYR), LYS(GOTFaction.LYS), TYROSH(GOTFaction.TYROSH), GHISCAR(GOTFaction.GHISCAR), DOTHRAKI(GOTFaction.DOTHRAKI), IBBEN(GOTFaction.IBBEN), JOGOS_NHAI(GOTFaction.JOGOS_NHAI), YI_TI(GOTFaction.YI_TI);

	private final Collection<InvasionSpawnEntry> invasionMobs = new ArrayList<>();
	private final GOTFaction invasionFaction;
	private final String subfaction;

	private Item invasionIcon;

	GOTInvasions(GOTFaction f) {
		this(f, null);
	}

	GOTInvasions(GOTFaction f, String s) {
		invasionFaction = f;
		subfaction = s;
	}

	public static GOTInvasions forID(int ID) {
		for (GOTInvasions i : values()) {
			if (i.ordinal() != ID) {
				continue;
			}
			return i;
		}
		return null;
	}

	public static GOTInvasions forName(String name) {
		for (GOTInvasions i : values()) {
			if (!i.codeName().equals(name)) {
				continue;
			}
			return i;
		}
		return null;
	}

	public static String[] listInvasionNames() {
		String[] names = new String[values().length];
		for (int i = 0; i < names.length; ++i) {
			names[i] = values()[i].codeName();
		}
		return names;
	}

	public static void preInit() {
		JOGOS_NHAI.invasionIcon = GOTItems.nomadSword;
		DOTHRAKI.invasionIcon = GOTItems.nomadSword;
		LORATH.invasionIcon = GOTItems.essosSword;
		VOLANTIS.invasionIcon = GOTItems.essosSword;
		BRAAVOS.invasionIcon = GOTItems.essosSword;
		NORVOS.invasionIcon = GOTItems.essosSword;
		PENTOS.invasionIcon = GOTItems.essosSword;
		TYROSH.invasionIcon = GOTItems.essosSword;
		GHISCAR.invasionIcon = GOTItems.essosSword;
		MYR.invasionIcon = GOTItems.essosSword;
		LYS.invasionIcon = GOTItems.essosSword;
		IRONBORN.invasionIcon = GOTItems.westerosSword;
		IBBEN.invasionIcon = GOTItems.trident;
		WESTERLANDS.invasionIcon = GOTItems.westerosSword;
		RIVERLANDS.invasionIcon = GOTItems.westerosSword;
		ARRYN.invasionIcon = GOTItems.westerosSword;
		DRAGONSTONE.invasionIcon = GOTItems.westerosSword;
		STORMLANDS.invasionIcon = GOTItems.westerosSword;
		REACH.invasionIcon = GOTItems.westerosSword;
		DORNE.invasionIcon = GOTItems.westerosSword;
		NORTH.invasionIcon = GOTItems.westerosSword;
		THENN.invasionIcon = GOTItems.wildlingSword;
		WILDLING.invasionIcon = GOTItems.wildlingSword;
		GIANT.invasionIcon = GOTItems.wildlingSword;
		YI_TI.invasionIcon = GOTItems.yiTiSword;
		HILL_TRIBES.invasionIcon = GOTItems.trident;

		HILL_TRIBES.invasionMobs.add(new InvasionSpawnEntry(GOTEntityHillmanWarrior.class, 10));
		HILL_TRIBES.invasionMobs.add(new InvasionSpawnEntry(GOTEntityHillmanArcher.class, 5));
		HILL_TRIBES.invasionMobs.add(new InvasionSpawnEntry(GOTEntityHillmanBerserker.class, 2));
		HILL_TRIBES.invasionMobs.add(new InvasionSpawnEntry(GOTEntityHillmanBannerBearer.class, 2));

		WILDLING.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWildlingWarrior.class, 10));
		WILDLING.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWildlingArcher.class, 5));
		WILDLING.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWildlingBannerBearer.class, 2));

		THENN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityThennWarrior.class, 10));
		THENN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityThennArcher.class, 5));
		THENN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityThennBannerBearer.class, 2));

		GIANT.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWildling.class, 10));
		GIANT.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWildlingArcher.class, 5));
		GIANT.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWildlingBannerBearer.class, 2));
		GIANT.invasionMobs.add(new InvasionSpawnEntry(GOTEntityGiant.class, 1));

		DOTHRAKI.invasionMobs.add(new InvasionSpawnEntry(GOTEntityDothraki.class, 10));
		DOTHRAKI.invasionMobs.add(new InvasionSpawnEntry(GOTEntityDothrakiArcher.class, 5));

		JOGOS_NHAI.invasionMobs.add(new InvasionSpawnEntry(GOTEntityJogosNhaiMan.class, 10));
		JOGOS_NHAI.invasionMobs.add(new InvasionSpawnEntry(GOTEntityJogosNhaiArcher.class, 5));

		YI_TI.invasionMobs.add(new InvasionSpawnEntry(GOTEntityYiTiSoldier.class, 10));
		YI_TI.invasionMobs.add(new InvasionSpawnEntry(GOTEntityYiTiSoldierCrossbower.class, 5));
		YI_TI.invasionMobs.add(new InvasionSpawnEntry(GOTEntityYiTiSamurai.class, 3));
		YI_TI.invasionMobs.add(new InvasionSpawnEntry(GOTEntityYiTiSamuraiFlamethrower.class, 2));
		YI_TI.invasionMobs.add(new InvasionSpawnEntry(GOTEntityYiTiBombardier.class, 1));
		YI_TI.invasionMobs.add(new InvasionSpawnEntry(GOTEntityYiTiBannerBearer.class, 2));

		IBBEN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityIbbenSoldier.class, 10));
		IBBEN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityIbbenSoldierArcher.class, 5));
		IBBEN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityIbbenBannerBearer.class, 2));

		VOLANTIS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityVolantisSoldier.class, 10));
		VOLANTIS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityVolantisSoldierArcher.class, 5));
		VOLANTIS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityVolantisBannerBearer.class, 2));

		GHISCAR.invasionMobs.add(new InvasionSpawnEntry(GOTEntityGhiscarSoldier.class, 10));
		GHISCAR.invasionMobs.add(new InvasionSpawnEntry(GOTEntityGhiscarSoldierArcher.class, 5));
		GHISCAR.invasionMobs.add(new InvasionSpawnEntry(GOTEntityGhiscarBannerBearer.class, 3));

		BRAAVOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityBraavosSoldier.class, 10));
		BRAAVOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityBraavosSoldierArcher.class, 5));
		BRAAVOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityBraavosBannerBearer.class, 2));

		LORATH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityLorathSoldier.class, 10));
		LORATH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityLorathSoldierArcher.class, 5));
		LORATH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityLorathBannerBearer.class, 2));

		NORVOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityNorvosSoldier.class, 10));
		NORVOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityNorvosSoldierArcher.class, 5));
		NORVOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityNorvosBannerBearer.class, 2));

		TYROSH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityTyroshSoldier.class, 10));
		TYROSH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityTyroshSoldierArcher.class, 5));
		TYROSH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityTyroshBannerBearer.class, 2));

		MYR.invasionMobs.add(new InvasionSpawnEntry(GOTEntityMyrSoldier.class, 10));
		MYR.invasionMobs.add(new InvasionSpawnEntry(GOTEntityMyrSoldierArcher.class, 5));
		MYR.invasionMobs.add(new InvasionSpawnEntry(GOTEntityMyrBannerBearer.class, 2));

		LYS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityLysSoldier.class, 10));
		LYS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityLysSoldierArcher.class, 5));
		LYS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityLysBannerBearer.class, 2));

		PENTOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityPentosSoldier.class, 10));
		PENTOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityPentosSoldierArcher.class, 5));
		PENTOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityPentosBannerBearer.class, 2));

		IRONBORN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityIronbornSoldier.class, 10));
		IRONBORN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityIronbornSoldierArcher.class, 5));
		IRONBORN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityIronbornBannerBearer.class, 2));

		WESTERLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWesterlandsSoldier.class, 10));
		WESTERLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWesterlandsSoldierArcher.class, 5));
		WESTERLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWesterlandsBannerBearer.class, 2));

		RIVERLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityRiverlandsSoldier.class, 10));
		RIVERLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityRiverlandsSoldierArcher.class, 5));
		RIVERLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityRiverlandsBannerBearer.class, 2));

		ARRYN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityArrynSoldier.class, 10));
		ARRYN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityArrynSoldierArcher.class, 5));
		ARRYN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityArrynBannerBearer.class, 2));

		DRAGONSTONE.invasionMobs.add(new InvasionSpawnEntry(GOTEntityDragonstoneSoldier.class, 10));
		DRAGONSTONE.invasionMobs.add(new InvasionSpawnEntry(GOTEntityDragonstoneSoldierArcher.class, 5));
		DRAGONSTONE.invasionMobs.add(new InvasionSpawnEntry(GOTEntityDragonstoneBannerBearer.class, 2));

		STORMLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityStormlandsSoldier.class, 10));
		STORMLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityStormlandsSoldierArcher.class, 5));
		STORMLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityStormlandsBannerBearer.class, 2));

		REACH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityReachSoldier.class, 10));
		REACH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityReachSoldierArcher.class, 5));
		REACH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityReachBannerBearer.class, 2));

		DORNE.invasionMobs.add(new InvasionSpawnEntry(GOTEntityDorneSoldier.class, 10));
		DORNE.invasionMobs.add(new InvasionSpawnEntry(GOTEntityDorneSoldierArcher.class, 5));
		DORNE.invasionMobs.add(new InvasionSpawnEntry(GOTEntityDorneBannerBearer.class, 2));

		NORTH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityNorthSoldier.class, 10));
		NORTH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityNorthSoldierArcher.class, 5));
		NORTH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityNorthBannerBearer.class, 2));
	}

	public String codeName() {
		StringBuilder s = new StringBuilder(invasionFaction.codeName());
		if (subfaction != null) {
			s.append('_').append(subfaction);
		}
		return s.toString();
	}

	public String codeNameHorn() {
		return "got.invasion." + codeName() + ".horn";
	}

	public ItemStack createWarhorn() {
		ItemStack horn = new ItemStack(GOTItems.warhorn);
		GOTItemWarhorn.setInvasionType(horn, this);
		return horn;
	}

	public ItemStack getInvasionIcon() {
		Item sword = invasionIcon;
		if (sword == null) {
			sword = Items.iron_sword;
		}
		return new ItemStack(sword);
	}

	public String invasionName() {
		if (subfaction == null) {
			return invasionFaction.factionName();
		}
		return StatCollector.translateToLocal("got.invasion." + codeName());
	}

	public GOTFaction getInvasionFaction() {
		return invasionFaction;
	}

	public Collection<InvasionSpawnEntry> getInvasionMobs() {
		return invasionMobs;
	}

	public static class InvasionSpawnEntry extends WeightedRandom.Item {
		private final Class<? extends GOTEntityNPC> entityClass;

		@SuppressWarnings("WeakerAccess")
		public InvasionSpawnEntry(Class<? extends GOTEntityNPC> c, int chance) {
			super(chance);
			entityClass = c;
		}

		public Class<? extends GOTEntityNPC> getEntityClass() {
			return entityClass;
		}
	}
}