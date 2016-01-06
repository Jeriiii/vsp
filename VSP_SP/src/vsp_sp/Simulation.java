/*
 * VSP - Semestrální práce.
 */
package vsp_sp;

import cz.zcu.fav.kiv.jsim.JSimException;
import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimSecurityException;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;
import vsp_sp.generator.Exp;
import vsp_sp.generator.Generator;
import vsp_sp.generator.IDistribution;
import vsp_sp.node.Output;
import vsp_sp.node.Queue;
import vsp_sp.node.SHO;

/**
 * Simulace celé sítě front
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class Simulation {

	/* jednotilvé pravděpodobnosti */
	final double P1 = 0.1;
	final double P2 = 0.05;
	final double P3 = 0.02;

	/**
	 * JSim simulace
	 */
	JSimSimulation sim = null;

	/**
	 * Jednotlivé fronty v síti.
	 */
	Queue queue1 = null, queue2 = null, queue3 = null, queue4 = null;

	/**
	 * Jednotlivé SHO v síti.
	 */
	SHO sho1 = null, sho2 = null, sho3 = null, sho4 = null;

	/**
	 * Sem se zaznamenávají všechny prvky co vychází ze sítě.
	 */
	Output output = null;

	/**
	 * Spustí celou simulaci
	 */
	public void run(String district) {
		try {
			/* vytvoření simulace */
			sim = new JSimSimulation("First simulation");

			/* vytvoření front */
			queue1 = new Queue("Fronta 1", sim);
			queue2 = new Queue("Fronta 2", sim);
			queue3 = new Queue("Fronta 3", sim);
			queue4 = new Queue("Fronta 4", sim);

			output = new Output(sim);

			createSHOsAndGenerators(district);

			/* spuštění simulace */
			while ((sim.step() == true))
				;

		} catch (JSimException e) {
			e.printStackTrace();
			e.printComment(System.err);
		} finally {
			sim.shutdown();
		}
	}

	/**
	 * Vytiskne statistiky
	 */
	public void printStatistics() {
		System.out.println("Počet zpracovaných položek je " + output.counter);

		System.out.println("-------------- SHO -------------- ");
		System.out.println("Počet položek zpracovaných SHO 1: " + sho1.getCounter());
		System.out.println("Počet položek zpracovaných SHO 2: " + sho2.getCounter());
		System.out.println("Počet položek zpracovaných SHO 3: " + sho3.getCounter());
		System.out.println("Počet položek zpracovaných SHO 4: " + sho4.getCounter());

		System.out.println("-------------- Tq -------------- ");
		System.out.println("SHO 1 Tqi je " + sho1.getTq());
		System.out.println("SHO 2 Tqi je " + sho2.getTq());
		System.out.println("SHO 3 Tqi je " + sho3.getTq());
		System.out.println("SHO 4 Tqi je " + sho4.getTq());
		System.out.println("Celkové Tq je " + output.getTq());
	}

	/**
	 * Vytvoření a puštění SHO.
	 */
	private void createSHOsAndGenerators(String district) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException, JSimSecurityException {
		/* vytvoření rozdělení */
		IDistribution shoDis1 = null, shoDis2 = null, shoDis3 = null, shoDis4 = null, genDis1 = null, genDis2 = null;

		if (district.equals("EXP") || district.equals("exp")) {
			shoDis1 = new Exp(2);
			shoDis2 = new Exp(5);
			shoDis3 = new Exp(5);
			shoDis4 = new Exp(6);

			genDis1 = new Exp(1);
			genDis2 = new Exp(3);
		}

		/* vytvoření SHO */
		sho1 = new SHO(shoDis1, queue1, "SHO 1", sim);
		sho2 = new SHO(shoDis2, queue2, "SHO 2", sim);
		sho3 = new SHO(shoDis3, queue3, "SHO 3", sim);
		sho4 = new SHO(shoDis4, queue4, "SHO 4", sim);

		Pipeline p1 = new Pipeline(sho3);
		Pipeline p2 = new Pipeline(sho3, sho2, 1.0 - P1);
		Pipeline p3 = new Pipeline(sho4, sho2, 1.0 - P2);
		Pipeline p4 = new Pipeline(output, sho1, 1.0 - P3);

		sho1.setPipeline(p1);
		sho2.setPipeline(p2);
		sho3.setPipeline(p3);
		sho4.setPipeline(p4);

		/* vytvoření generátorů */
		Pipeline p5 = new Pipeline(sho1);
		Pipeline p6 = new Pipeline(sho2);

		Generator gen1 = new Generator(50000, genDis1, p5, "Generátor s labda = 1", sim);
		gen1.activateNow();

		Generator gen2 = new Generator(50000, genDis2, p6, "Generátor s labda = 3", sim);
		gen2.activateNow();
	}

}
