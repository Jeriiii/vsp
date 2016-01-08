package vsp_sp.generator;

/**
 * Generuje náhodná čísla náhodného rozdělení
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class Gauss implements IDistribution {

	/**
	 * Střední hodnota normálového rozdělení.
	 */
	private final double a;

	/**
	 * Směrodatná odchylka normálového rozdělení.
	 */
	private final double sigma;

	/**
	 * Koeficient variace.
	 */
	private final double c;

	/**
	 * Počet hodnot která se vygenerují v rovnoměrném rozdělení, než se z toho
	 * vypočítá normálové.
	 */
	private static final int n = 12;

	public Gauss(double lambda, double c) {
		this.a = 1.0 / lambda;
		this.c = c;
		this.sigma = c * a;
	}

	/**
	 * Vygeneruje KLADNÉ náhodné číslo s pravděpodobnosí zadaného normálového
	 * rozdělení.
	 *
	 * @return Náhodné číslo.
	 */
	@Override
	public double generate() {
		double x;

		while (true) {
			x = generateNext();

			if (x >= 0.0) {
				break;
			}
		}

		return x;
	}

	/**
	 * Vygeneruje náhodné číslo s pravděpodobnosí zadaného normálového
	 * rozdělení.
	 *
	 * @return Náhodné číslo.
	 */
	private double generateNext() {
		double sumRandom = 0;

		for (int i = 0; i < this.n; i++) {
			double randomNumber = Math.random();
			sumRandom = sumRandom + randomNumber;
		}

		double x = (this.a + this.sigma * (sumRandom - 6));

		return x;
	}

}
