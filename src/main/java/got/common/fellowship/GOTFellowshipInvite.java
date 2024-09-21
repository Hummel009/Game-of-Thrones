package got.common.fellowship;

import java.util.UUID;

public class GOTFellowshipInvite {
	private final UUID inviterID;

	private final UUID fellowshipID;

	public GOTFellowshipInvite(UUID fs, UUID inviter) {
		fellowshipID = fs;
		inviterID = inviter;
	}

	public UUID getFellowshipID() {
		return fellowshipID;
	}

	public UUID getInviterID() {
		return inviterID;
	}
}