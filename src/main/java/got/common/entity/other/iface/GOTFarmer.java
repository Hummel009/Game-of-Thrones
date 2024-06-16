package got.common.entity.other.iface;

import got.common.database.GOTInvasions;

public interface GOTFarmer extends GOTUnitTradeable, GOTTradeable {
	@Override
	default GOTInvasions getWarhorn() {
		return null;
	}
}