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
import vsp_sp.generator.Generator;
import vsp_sp.node.Output;
import vsp_sp.node.Queue;
import vsp_sp.node.SHO;

/**
 * Simulace celé sítě front
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class Simulation {

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
	public void run() {
		try {
			/* vytvoření simulace */
			sim = new JSimSimulation("First simulation");

			/* vytvoření front */
			queue1 = new Queue("Fronta 1", sim);
			queue2 = new Queue("Fronta 2", sim);
			queue3 = new Queue("Fronta 3", sim);
			queue4 = new Queue("Fronta 4", sim);

			output = new Output(sim);

			createSHOsAndGenerators(queue1, queue2, queue3, queue4, output, sim);

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
		System.out.println("Celkové Tq je " + output.getTq());
	}

	/**
	 * Vytvoření a puštění SHO.
	 */
	private void createSHOsAndGenerators(Queue queue1, Queue queue2, Queue queue3, Queue queue4, Output output, JSimSimulation sim) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException, JSimSecurityException {
		/* vytvoření SHO */
		sho1 = new SHO(2, queue1, "SHO 1", sim);
		sho2 = new SHO(5, queue2, "SHO 2", sim);
		sho3 = new SHO(5, queue3, "SHO 3", sim);
		sho4 = new SHO(5, queue4, "SHO 4", sim);

		Pipeline p1 = new Pipeline(sho3);
		Pipeline p2 = new Pipeline(sho3, sho2, 0.9);
		Pipeline p3 = new Pipeline(sho4, sho2, 0.95);
		Pipeline p4 = new Pipeline(output, sho1, 0.98);

		sho1.setPipeline(p1);
		sho2.setPipeline(p2);
		sho3.setPipeline(p3);
		sho4.setPipeline(p4);

		/* vytvoření generátorů */
		Pipeline p5 = new Pipeline(sho1);
		Pipeline p6 = new Pipeline(sho2);

		Generator gen1 = new Generator(1, 50000, p5, "Generátor s labda = 1", sim);
		gen1.activateNow();

		Generator gen2 = new Generator(3, 50000, p6, "Generátor s labda = 3", sim);
		gen2.activateNow();
	}

}
