package essentials.utilities;

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
		return (euler / (2*Math.PI)) * 360d;
	}
}
