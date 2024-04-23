package got.common.entity.other.iface;

import got.common.database.GOTInvasions;
import got.common.database.GOTUnitTradeEntries;

public interface GOTUnitTradeable extends GOTHireableBase {
	GOTUnitTradeEntries getUnits();

	GOTInvasions getWarhorn();
}