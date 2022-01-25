package got.common.entity.other;

import got.common.database.*;

public interface GOTBrothelKeeper extends GOTHireableBase {
	GOTUnitTradeEntries getUnits();

	GOTInvasions getWarhorn();
}
