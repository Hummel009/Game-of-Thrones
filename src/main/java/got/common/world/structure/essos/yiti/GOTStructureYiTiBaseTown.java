package got.common.world.structure.essos.yiti;

public abstract class GOTStructureYiTiBaseTown extends GOTStructureYiTiBase {
	public GOTStructureYiTiBaseTown(boolean flag) {
		super(flag);
	}

	@Override
	public boolean useTownBlocks() {
		return true;
	}
}
