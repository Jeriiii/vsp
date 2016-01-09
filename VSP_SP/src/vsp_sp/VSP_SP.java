/*
 * VSP - Semestrální práce.
 */
package vsp_sp;

import java.util.logging.Level;
import java.util.logging.LogManager;
import vsp_sp.generator.GeneratorCreator;

/**
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class VSP_SP {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE);

		Simulation sim = new Simulation();
		String district = "EXP";
		int maxItems = 100000;

		GeneratorCreator gc = new GeneratorCreator();

		if (args.length == 2) { //spuštění s pramaterem EXP nebo GAUSS

			if (district.equals("EXP") || district.equals("exp")) {
				runExp(gc, sim, maxItems);
			} else {
				runGauss(gc, sim, maxItems);
			}

		} else { //spuštění bez pramateru EXP nebo GAUSS

			if (args.length == 0) { //počet prvků co se mají vygenerovat není zdán, nastaví se defaultní
				maxItems = 100000;
			}

			runExp(gc, sim, maxItems);
			runGauss(gc, sim, maxItems);

		}

	}

	/**
	 * Spustí simulaci pro exponenciální rozdělení.
	 *
	 * @param gc Vytváří gaussovské nebo exponenciální generátory náhod. čísel.
	 * @param sim Spouštěná simulace
	 * @param maxItems Počet položek, které se mají vygenerovat.
	 */
	private static void runExp(GeneratorCreator gc, Simulation sim, int maxItems) {
		System.out.println("Spuštění exp. rozdělení:");
		gc.setCreateExp();
		sim.run(gc, maxItems);
		sim.printStatistics();
	}

	/**
	 * Spustí simulaci pro tři příklady Gaussovo rozdělení.
	 *
	 * @param gc Vytváří gaussovské nebo exponenciální generátory náhod. čísel.
	 * @param sim Spouštěná simulace
	 * @param maxItems Počet položek, které se mají vygenerovat.
	 */
	private static void runGauss(GeneratorCreator gc, Simulation sim, int maxItems) {
		System.out.println("Spuštění Gaussovo rozdělení pro koef. var. = 0.05:");
		gc.setCreateGauss(0.05);
		sim.run(gc, maxItems);
		sim.printStatistics();

		System.out.println("Spuštění Gaussovo rozdělení pro koef. var. = 0.2:");
		gc.setCreateGauss(0.2);
		sim.run(gc, maxItems);
		sim.printStatistics();

		System.out.println("Spuštění Gaussovo rozdělení pro koef. var. = 0.7:");
		gc.setCreateGauss(0.7);
		sim.run(gc, maxItems);
		sim.printStatistics();
	}
}
