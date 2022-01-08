package got.common.inventory;

import java.util.*;

import got.common.*;
import got.common.block.table.GOTBlockCraftingTable;
import got.common.database.GOTRegistry;
import got.common.recipe.*;
import net.minecraft.block.Block;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.crafting.*;
import net.minecraft.world.World;

public abstract class GOTContainerCraftingTable extends ContainerWorkbench {
	public World theWorld;
	public int tablePosX;
	public int tablePosY;
	public int tablePosZ;
	public List<IRecipe> recipeList;
	public GOTBlockCraftingTable tableBlock;
	public EntityPlayer entityplayer;

	public GOTContainerCraftingTable(InventoryPlayer inv, World world, int i, int j, int k, List<IRecipe> list, Block block) {
		super(inv, world, i, j, k);
		theWorld = world;
		tablePosX = i;
		tablePosY = j;
		tablePosZ = k;
		entityplayer = inv.player;
		tableBlock = (GOTBlockCraftingTable) block;
		recipeList = new ArrayList<>(list);
		recipeList.add(new GOTRecipePouch(tableBlock.tableFaction));
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return theWorld.getBlock(tablePosX, tablePosY, tablePosZ) == tableBlock && entityplayer.getDistanceSq(tablePosX + 0.5, tablePosY + 0.5, tablePosZ + 0.5) <= 64.0;
	}

	@Override
	public void onCraftMatrixChanged(IInventory inv) {
		if (recipeList == null) {
			return;
		}
		GOTPlayerData pd = GOTLevelData.getData(entityplayer);
		boolean tableSwitched = pd.getTableSwitched();
		if (tableSwitched) {
			craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(craftMatrix, theWorld));
		} else {
			craftResult.setInventorySlotContents(0, GOTRecipe.findMatchingRecipe(recipeList, craftMatrix, theWorld));
		}
	}

	public static class Arryn extends GOTContainerCraftingTable {
		public Arryn(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.arryn, GOTRegistry.tableArryn);
		}
	}

	public static class Asshai extends GOTContainerCraftingTable {
		public Asshai(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.asshai, GOTRegistry.tableAsshai);
		}
	}

	public static class Braavos extends GOTContainerCraftingTable {
		public Braavos(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.braavos, GOTRegistry.tableBraavos);
		}
	}

	public static class Crownlands extends GOTContainerCraftingTable {
		public Crownlands(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.crownlands, GOTRegistry.tableCrownlands);
		}
	}

	public static class Dorne extends GOTContainerCraftingTable {
		public Dorne(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.dorne, GOTRegistry.tableDorne);
		}
	}

	public static class Dothraki extends GOTContainerCraftingTable {
		public Dothraki(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.dothraki, GOTRegistry.tableDothraki);
		}
	}

	public static class Dragonstone extends GOTContainerCraftingTable {
		public Dragonstone(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.dragonstone, GOTRegistry.tableDragonstone);
		}
	}

	public static class Ghiscar extends GOTContainerCraftingTable {
		public Ghiscar(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.ghiscar, GOTRegistry.tableGhiscar);
		}
	}

	public static class Gift extends GOTContainerCraftingTable {
		public Gift(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.gift, GOTRegistry.tableGift);
		}
	}

	public static class Hillmen extends GOTContainerCraftingTable {
		public Hillmen(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.hillmen, GOTRegistry.tableHillTribes);
		}
	}

	public static class Ibben extends GOTContainerCraftingTable {
		public Ibben(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.ibben, GOTRegistry.tableIbben);
		}
	}

	public static class Ironborn extends GOTContainerCraftingTable {
		public Ironborn(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.ironborn, GOTRegistry.tableIronborn);
		}
	}

	public static class Jogos extends GOTContainerCraftingTable {
		public Jogos(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.jogos, GOTRegistry.tableJogos);
		}
	}

	public static class Lhazar extends GOTContainerCraftingTable {
		public Lhazar(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.lhazar, GOTRegistry.tableLhazar);
		}
	}

	public static class Lorath extends GOTContainerCraftingTable {
		public Lorath(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.lorath, GOTRegistry.tableLorath);
		}
	}

	public static class Lys extends GOTContainerCraftingTable {
		public Lys(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.lys, GOTRegistry.tableLys);
		}
	}

	public static class Mossovy extends GOTContainerCraftingTable {
		public Mossovy(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.mossovy, GOTRegistry.tableMossovy);
		}
	}

	public static class Myr extends GOTContainerCraftingTable {
		public Myr(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.myr, GOTRegistry.tableMyr);
		}
	}

	public static class North extends GOTContainerCraftingTable {
		public North(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.north, GOTRegistry.tableNorth);
		}
	}

	public static class Norvos extends GOTContainerCraftingTable {
		public Norvos(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.norvos, GOTRegistry.tableNorvos);
		}
	}

	public static class Pentos extends GOTContainerCraftingTable {
		public Pentos(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.pentos, GOTRegistry.tablePentos);
		}
	}

	public static class Qarth extends GOTContainerCraftingTable {
		public Qarth(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.qarth, GOTRegistry.tableQarth);
		}
	}

	public static class Qohor extends GOTContainerCraftingTable {
		public Qohor(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.qohor, GOTRegistry.tableQohor);
		}
	}

	public static class Reach extends GOTContainerCraftingTable {
		public Reach(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.reach, GOTRegistry.tableReach);
		}
	}

	public static class Riverlands extends GOTContainerCraftingTable {
		public Riverlands(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.riverlands, GOTRegistry.tableRiverlands);
		}
	}

	public static class Sothoryos extends GOTContainerCraftingTable {
		public Sothoryos(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.sothoryos, GOTRegistry.tableSothoryos);
		}
	}

	public static class Stormlands extends GOTContainerCraftingTable {
		public Stormlands(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.stormlands, GOTRegistry.tableStormlands);
		}
	}

	public static class Summer extends GOTContainerCraftingTable {
		public Summer(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.summer, GOTRegistry.tableSummer);
		}
	}

	public static class Tyrosh extends GOTContainerCraftingTable {
		public Tyrosh(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.tyrosh, GOTRegistry.tableTyrosh);
		}
	}

	public static class Volantis extends GOTContainerCraftingTable {
		public Volantis(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.volantis, GOTRegistry.tableVolantis);
		}
	}

	public static class Westerlands extends GOTContainerCraftingTable {
		public Westerlands(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.westerlands, GOTRegistry.tableWesterlands);
		}
	}

	public static class Wildling extends GOTContainerCraftingTable {
		public Wildling(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.wildling, GOTRegistry.tableWildling);
		}
	}

	public static class YiTi extends GOTContainerCraftingTable {
		public YiTi(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.yiti, GOTRegistry.tableYiTi);
		}
	}

}
