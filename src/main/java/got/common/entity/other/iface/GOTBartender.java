package got.common.entity.other.iface;

import got.common.database.GOTInvasions;

public interface GOTBartender extends GOTUnitTradeable, GOTTradeable {
	@Override
	default GOTInvasions getWarhorn() {
		return null;
	}
}