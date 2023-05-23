package got.common.inventory;

public class GOTSlotStackSize implements Comparable<Object> {
	public int slot;
	public int stackSize;

	public GOTSlotStackSize(int i, int j) {
		slot = i;
		stackSize = j;
	}

	@Override
	public int compareTo(Object obj) {
		if (obj instanceof GOTSlotStackSize) {
			GOTSlotStackSize obj1 = (GOTSlotStackSize) obj;
			if (obj1.stackSize < stackSize) {
				return 1;
			}
			if (obj1.stackSize > stackSize) {
				return -1;
			}
			if (obj1.slot < slot) {
				return 1;
			}
			if (obj1.slot > slot) {
				return -1;
			}
		}
		return 0;
	}
}
