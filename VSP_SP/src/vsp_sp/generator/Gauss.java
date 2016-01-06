package vsp_sp.generator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

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
	 * Počet hodnot která se vygenerují v rovnoměrném rozdělení, než se z toho
	 * vypočítá normálové.
	 */
	private static final int n = 12;

	public Gauss(double a, double sigma) {
		this.a = a;
		this.sigma = sigma;
	}

	/**
	 * Vygeneruje náhodné číslo s pravděpodobnosí zadaného normálového
	 * rozdělení.
	 *
	 * @return Náhodné číslo.
	 */
	@Override
	public double generate() {
		double sumRandom = 0;

		for (int i = 0; i < this.n; i++) {
			double randomNumber = Math.random();
			sumRandom = sumRandom + randomNumber;
		}

		double x = (this.a + this.sigma * (sumRandom - 6));

		return x;
	}

}
