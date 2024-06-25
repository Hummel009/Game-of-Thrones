package got.common.world.structure.essos.yi_ti;

public abstract class GOTStructureYiTiBaseTown extends GOTStructureYiTiBase {
	protected GOTStructureYiTiBaseTown(boolean flag) {
		super(flag);
	}

	@Override
	public boolean useTownBlocks() {
		return true;
	}
}