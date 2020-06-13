package cbm.utilities;

public class MathUtilities {
	private MathUtilities() {}

	/*
	 *	euler to degree = ((euler) / (2*pi)) * 360
	 *	degree to euler = (degree/360) * 2*pi
	 */
	public static double degreeToEuler(double degree) {
		return (degree / 360d) * 2 * Math.PI;
	}

	public static double eulerToDegree(double euler) {
		return (euler / (2 * Math.PI)) * 360d;
	}
	
	public static double round(double value, int amount) {
		long roundV = (long) Math.pow(10, amount);
		long tmp = (long) (value * roundV);
		return (double) tmp / (double) roundV;
	}

	public static double toProzent(double ganzes, double anteil) {
		return anteil / ganzes;
	}
}
