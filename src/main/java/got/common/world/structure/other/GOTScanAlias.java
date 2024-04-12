package got.common.world.structure.other;

public class GOTScanAlias {
	private final String name;
	private final Type type;

	public GOTScanAlias(String s, Type t) {
		name = s;
		type = t;
	}

	public String getFullCode() {
		char c = type.typeCode;
		return c + name + c;
	}

	public String getName() {
		return name;
	}

	public enum Type {
		BLOCK('#'), BLOCK_META('~');

		public final char typeCode;

		Type(char c) {
			typeCode = c;
		}
	}
}