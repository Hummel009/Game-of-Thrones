package got.common.fellowship;

import java.util.UUID;

public class GOTFellowshipInvite {
	private final UUID inviterID;

	private UUID fellowshipID;

	public GOTFellowshipInvite(UUID fs, UUID inviter) {
		fellowshipID = fs;
		inviterID = inviter;
	}

	public UUID getFellowshipID() {
		return fellowshipID;
	}

	public void setFellowshipID(UUID fellowshipID) {
		this.fellowshipID = fellowshipID;
	}

	public UUID getInviterID() {
		return inviterID;
	}
}