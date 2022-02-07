package got.common.inventory;

public class GOTSlotStackSize implements Comparable {
	private int slot;
	private int stackSize;

	public GOTSlotStackSize(int i, int j) {
		setSlot(i);
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
			if (obj1.stackSize == stackSize) {
				if (obj1.getSlot() < getSlot()) {
					return 1;
				}
				if (obj1.getSlot() > getSlot()) {
					return -1;
				}
			}
		}
		return 0;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}
}
