/*
 * VSP - Semestrální práce.
 */
package vsp_sp;

import cz.zcu.fav.kiv.jsim.JSimException;
import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimSecurityException;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimTooManyHeadsException;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;
import vsp_sp.generator.Generator;
import vsp_sp.generator.GeneratorCreator;
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
	private final double P1 = 0.1;
	private final double P2 = 0.05;
	private final double P3 = 0.02;

	/* velká lambda jednotlivých SHO */
	private final double LAMBDA_SHO_1 = 1.08;
	private final double LAMBDA_SHO_2 = 3.57;
	private final double LAMBDA_SHO_3 = 4.3;
	private final double LAMBDA_SHO_4 = 4.08;

	/* mí jednotlivých SHO */
	private final double MI_1 = 2.0;
	private final double MI_2 = 5.0;
	private final double MI_3 = 5.0;
	private final double MI_4 = 6.0;

	/* mí jednotlivých SHO */
	private final double LAMBDA_1 = 1.0;
	private final double LAMBDA_2 = 3.0;

	/**
	 * Aktuální poček kroků simulace
	 */
	private int countSimSteps = 0;

	/**
	 * JSim simulace
	 */
	private JSimSimulation sim = null;

	/**
	 * Jednotlivé fronty v síti.
	 */
	private Queue queue1 = null, queue2 = null, queue3 = null, queue4 = null;

	/**
	 * Jednotlivé SHO v síti.
	 */
	private SHO sho1 = null, sho2 = null, sho3 = null, sho4 = null;

	/**
	 * Sem se zaznamenávají všechny prvky co vychází ze sítě.
	 */
	private Output output = null;

	/**
	 * Generátory vstupních proudů.
	 */
	private Generator gen1 = null, gen2 = null;

	/**
	 * Spustí celou simulaci
	 *
	 * @param gc Vytváří gaussovské nebo exponenciální generátory náhod. čísel.
	 * @param maxItems Počet položek, které se mají vygenerovat.
	 */
	public void run(GeneratorCreator gc, int maxItems) {
		try {
			/* vytvoření simulace */
			sim = new JSimSimulation("First simulation");

			output = new Output(sim);

			createQueues(sim);
			createSHOs(gc);
			createGenerators(gc);

			/* spuštění simulace */
			while ((sim.step() == true)) {
				/* zjistí, jestli generátory již vygenerovali všechny položky */
				if ((gen1.getCountGenItems() + gen2.getCountGenItems()) >= maxItems) {
					/* zastaví oba generátory */
					gen1.setFinished(true);
					gen2.setFinished(true);
				}

				countSimSteps++;
			}

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
		System.out.println("-------------- Obecné -------------- ");
		System.out.println("Počet zpracovaných položek je " + output.counter);
		System.out.println("Doba trvání simulace je " + sim.getCurrentTime());
		System.out.println("Počet kroků simulace je " + countSimSteps);

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

		System.out.println("-------------- Lq -------------- ");

		double Lq1 = sho1.getLq();
		double Lq2 = sho2.getLq();
		double Lq3 = sho3.getLq();
		double Lq4 = sho4.getLq();
		double Lq = Lq1 + Lq2 + Lq3 + Lq4;

		System.out.println("SHO 1 Lqi je " + Lq1);
		System.out.println("SHO 2 Lqi je " + Lq2);
		System.out.println("SHO 3 Lqi je " + Lq3);
		System.out.println("SHO 4 Lqi je " + Lq4);
		System.out.println("Celkové Lq je " + Lq);

		System.out.println("-------------- Zatížení -------------- ");
		System.out.println("SHO 1 - zatížení je " + sho1.getLoad());
		System.out.println("SHO 2 - zatížení je " + sho2.getLoad());
		System.out.println("SHO 3 - zatížení je " + sho3.getLoad());
		System.out.println("SHO 4 - zatížení je " + sho4.getLoad());

		System.out.println("-------------- Fronty -------------- ");
		System.out.println("Fronta 1 Twi je " + sho1.getTw() + " Lwi je " + sho1.getLw());
		System.out.println("Fronta 2 Twi je " + sho2.getTw() + " Lwi je " + sho2.getLw());
		System.out.println("Fronta 3 Twi je " + sho3.getTw() + " Lwi je " + sho3.getLw());
		System.out.println("Fronta 4 Twi je " + sho4.getTw() + " Lwi je " + sho4.getLw());

		System.out.println("");
	}

	/**
	 * Vytvoří jednotlivé fronty v síti front.
	 *
	 * @param sim Simulace.
	 * @throws JSimInvalidParametersException
	 * @throws JSimTooManyHeadsException
	 */
	private void createQueues(JSimSimulation sim) throws JSimInvalidParametersException, JSimTooManyHeadsException {
		/* vytvoření front */
		queue1 = new Queue("Fronta 1", sim);
		queue2 = new Queue("Fronta 2", sim);
		queue3 = new Queue("Fronta 3", sim);
		queue4 = new Queue("Fronta 4", sim);
	}

	/**
	 * Vytvoření SHO.
	 */
	private void createSHOs(GeneratorCreator gc) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException {
		/* vytvoření generátorů rozdělení */
		IDistribution shoDis1 = gc.createGenerator(MI_1);
		IDistribution shoDis2 = gc.createGenerator(MI_2);
		IDistribution shoDis3 = gc.createGenerator(MI_3);
		IDistribution shoDis4 = gc.createGenerator(MI_4);

		/* vytvoření SHO */
		sho1 = new SHO(shoDis1, MI_1, queue1, "SHO 1", sim);
		sho2 = new SHO(shoDis2, MI_2, queue2, "SHO 2", sim);
		sho3 = new SHO(shoDis3, MI_3, queue3, "SHO 3", sim);
		sho4 = new SHO(shoDis4, MI_4, queue4, "SHO 4", sim);

		Pipeline p1 = new Pipeline(sho3);
		Pipeline p2 = new Pipeline(sho3, sho2, 1.0 - P1);
		Pipeline p3 = new Pipeline(sho4, sho2, 1.0 - P2);
		Pipeline p4 = new Pipeline(output, sho1, 1.0 - P3);

		sho1.setPipeline(p1);
		sho2.setPipeline(p2);
		sho3.setPipeline(p3);
		sho4.setPipeline(p4);
	}

	/**
	 * Vytvoření a puštění generátorů vstupních toků.
	 */
	private void createGenerators(GeneratorCreator gc) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException, JSimSecurityException {
		/* vytvoření generátorů rozdělení */
		IDistribution genDis1 = gc.createGenerator(LAMBDA_1);
		IDistribution genDis2 = gc.createGenerator(LAMBDA_2);

		/* vytvoření generátorů */
		Pipeline p5 = new Pipeline(sho1);
		Pipeline p6 = new Pipeline(sho2);

		gen1 = new Generator(genDis1, p5, "Generátor s labda = 1", sim);
		gen1.activateNow();

		gen2 = new Generator(genDis2, p6, "Generátor s labda = 3", sim);
		gen2.activateNow();
	}

}
