package dz.sbenkhaoua.app.scrumit.utils;

public class PKeyGenerator {

	public static String get() {
		return Long.toHexString(System.nanoTime());
	}

}
