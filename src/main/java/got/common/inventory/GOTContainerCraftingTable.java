package got.common.inventory;

import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.block.table.GOTBlockCraftingTable;
import got.common.database.GOTBlocks;
import got.common.recipe.GOTRecipe;
import got.common.recipe.GOTRecipePouch;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class GOTContainerCraftingTable extends ContainerWorkbench {
	private final World theWorld;
	private final int tablePosX;
	private final int tablePosY;
	private final int tablePosZ;
	private final EntityPlayer entityplayer;
	private final List<IRecipe> recipeList;

	private final GOTBlockCraftingTable tableBlock;

	protected GOTContainerCraftingTable(InventoryPlayer inv, World world, int i, int j, int k, List<IRecipe> list, Block block) {
		super(inv, world, i, j, k);
		theWorld = world;
		tablePosX = i;
		tablePosY = j;
		tablePosZ = k;
		entityplayer = inv.player;
		tableBlock = (GOTBlockCraftingTable) block;
		recipeList = new ArrayList<>(list);
		recipeList.add(new GOTRecipePouch(tableBlock.getFaction()));
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

	public GOTBlockCraftingTable getTableBlock() {
		return tableBlock;
	}

	public List<IRecipe> getRecipeList() {
		return recipeList;
	}

	public static class Arryn extends GOTContainerCraftingTable {
		public Arryn(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.ARRYN, GOTBlocks.tableArryn);
		}
	}

	public static class Asshai extends GOTContainerCraftingTable {
		public Asshai(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.ASSHAI, GOTBlocks.tableAsshai);
		}
	}

	public static class Braavos extends GOTContainerCraftingTable {
		public Braavos(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.BRAAVOS, GOTBlocks.tableBraavos);
		}
	}

	public static class Crownlands extends GOTContainerCraftingTable {
		public Crownlands(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.CROWNLANDS, GOTBlocks.tableCrownlands);
		}
	}

	public static class Dorne extends GOTContainerCraftingTable {
		public Dorne(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.DORNE, GOTBlocks.tableDorne);
		}
	}

	public static class Dothraki extends GOTContainerCraftingTable {
		public Dothraki(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.DOTHRAKI, GOTBlocks.tableDothraki);
		}
	}

	public static class Dragonstone extends GOTContainerCraftingTable {
		public Dragonstone(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.DRAGONSTONE, GOTBlocks.tableDragonstone);
		}
	}

	public static class Ghiscar extends GOTContainerCraftingTable {
		public Ghiscar(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.GHISCAR, GOTBlocks.tableGhiscar);
		}
	}

	public static class Gift extends GOTContainerCraftingTable {
		public Gift(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.GIFT, GOTBlocks.tableGift);
		}
	}

	public static class Hillmen extends GOTContainerCraftingTable {
		public Hillmen(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.HILLMEN, GOTBlocks.tableHillTribes);
		}
	}

	public static class Ibben extends GOTContainerCraftingTable {
		public Ibben(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.IBBEN, GOTBlocks.tableIbben);
		}
	}

	public static class Ironborn extends GOTContainerCraftingTable {
		public Ironborn(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.IRONBORN, GOTBlocks.tableIronborn);
		}
	}

	public static class Jogos extends GOTContainerCraftingTable {
		public Jogos(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.JOGOS, GOTBlocks.tableJogos);
		}
	}

	public static class Lhazar extends GOTContainerCraftingTable {
		public Lhazar(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.LHAZAR, GOTBlocks.tableLhazar);
		}
	}

	public static class Lorath extends GOTContainerCraftingTable {
		public Lorath(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.LORATH, GOTBlocks.tableLorath);
		}
	}

	public static class Lys extends GOTContainerCraftingTable {
		public Lys(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.LYS, GOTBlocks.tableLys);
		}
	}

	public static class Mossovy extends GOTContainerCraftingTable {
		public Mossovy(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.MOSSOVY, GOTBlocks.tableMossovy);
		}
	}

	public static class Myr extends GOTContainerCraftingTable {
		public Myr(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.MYR, GOTBlocks.tableMyr);
		}
	}

	public static class North extends GOTContainerCraftingTable {
		public North(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.NORTH, GOTBlocks.tableNorth);
		}
	}

	public static class Norvos extends GOTContainerCraftingTable {
		public Norvos(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.NORVOS, GOTBlocks.tableNorvos);
		}
	}

	public static class Pentos extends GOTContainerCraftingTable {
		public Pentos(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.PENTOS, GOTBlocks.tablePentos);
		}
	}

	public static class Qarth extends GOTContainerCraftingTable {
		public Qarth(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.QARTH, GOTBlocks.tableQarth);
		}
	}

	public static class Qohor extends GOTContainerCraftingTable {
		public Qohor(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.QOHOR, GOTBlocks.tableQohor);
		}
	}

	public static class Reach extends GOTContainerCraftingTable {
		public Reach(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.REACH, GOTBlocks.tableReach);
		}
	}

	public static class Riverlands extends GOTContainerCraftingTable {
		public Riverlands(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.RIVERLANDS, GOTBlocks.tableRiverlands);
		}
	}

	public static class Sothoryos extends GOTContainerCraftingTable {
		public Sothoryos(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.SOTHORYOS, GOTBlocks.tableSothoryos);
		}
	}

	public static class Stormlands extends GOTContainerCraftingTable {
		public Stormlands(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.STORMLANDS, GOTBlocks.tableStormlands);
		}
	}

	public static class Summer extends GOTContainerCraftingTable {
		public Summer(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.SUMMER, GOTBlocks.tableSummer);
		}
	}

	public static class Tyrosh extends GOTContainerCraftingTable {
		public Tyrosh(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.TYROSH, GOTBlocks.tableTyrosh);
		}
	}

	public static class Volantis extends GOTContainerCraftingTable {
		public Volantis(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.VOLANTIS, GOTBlocks.tableVolantis);
		}
	}

	public static class Westerlands extends GOTContainerCraftingTable {
		public Westerlands(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.WESTERLANDS, GOTBlocks.tableWesterlands);
		}
	}

	public static class Wildling extends GOTContainerCraftingTable {
		public Wildling(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.WILDLING, GOTBlocks.tableWildling);
		}
	}

	public static class YiTi extends GOTContainerCraftingTable {
		public YiTi(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, GOTRecipe.YITI, GOTBlocks.tableYiTi);
		}
	}

}
