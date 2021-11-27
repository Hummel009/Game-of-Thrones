package got.common.entity.dragon;

public enum GOTDragonLifeStage {

	EGG(-36000), HATCHLING(-24000), JUVENILE(-12000), ADULT(0);

	public int ageLimit;

	GOTDragonLifeStage(int ageLimit) {
		this.ageLimit = ageLimit;
	}
}
