package got.common.world.structure.other;

public class GOTScanAlias {
	private final String name;
	private final Type type;

	public GOTScanAlias(String s, Type t) {
		name = s;
		type = t;
	}

	public String getFullCode() {
		char c = type.getTypeCode();
		return c + name + c;
	}

	public String getName() {
		return name;
	}

	public enum Type {
		BLOCK('#'), BLOCK_META('~');

		private final char typeCode;

		Type(char c) {
			typeCode = c;
		}

		public char getTypeCode() {
			return typeCode;
		}
	}
}