package got.common.entity.other;

import got.common.database.*;

public interface GOTUnitTradeable extends GOTHireableBase {
	GOTUnitTradeEntries getUnits();

	GOTInvasions getWarhorn();
}
