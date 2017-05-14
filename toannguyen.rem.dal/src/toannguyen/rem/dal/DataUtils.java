package toannguyen.rem.dal;
import java.math.BigInteger;
import java.security.SecureRandom;

public class DataUtils {
	private static SecureRandom random = new SecureRandom();

	private static String nextSessionId(int length) {
		return new BigInteger(length * 5, random).toString(32);
	}

	public static String createLoginToken() {
		return nextSessionId(20);
	}

	public static String createResetToken() {
		return nextSessionId(5);
	}
}
