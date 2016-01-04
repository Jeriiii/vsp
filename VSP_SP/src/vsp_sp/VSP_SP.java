/*
 * VSP - Semestrální práce.
 */
package vsp_sp;

import cz.zcu.fav.kiv.jsim.JSimException;
import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimLink;
import cz.zcu.fav.kiv.jsim.JSimSecurityException;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;

/**
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class VSP_SP {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		JSimSimulation sim = null;
		Queue queue1 = null, queue2 = null, queue3 = null, queue4 = null;
		MyProcess process = null;
		Output output = null;

		try {
			/* vytvoření simulace */
			sim = new JSimSimulation("First simulation");

			/* vytvoření front */
			queue1 = new Queue("Fronta 1", sim);
			queue2 = new Queue("Fronta 2", sim);
			queue3 = new Queue("Fronta 3", sim);
			queue4 = new Queue("Fronta 4", sim);

			output = new Output();

			createGenetarors(queue1, queue2, sim);
			createSHO(queue1, queue2, queue3, queue4, output, sim);

			/* spuštění simulace */
			while (sim.step() == true);
		} catch (JSimException e) {
			e.printStackTrace();
			e.printComment(System.err);
		} finally {
			sim.shutdown();
		}
	}

	/**
	 * Vytvoření a spuštění generátorů.
	 */
	private static void createGenetarors(Queue queue1, Queue queue2, JSimSimulation sim) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException, JSimSecurityException {
		Generator gen1 = new Generator(1, 2000, queue1, "Generátor s labda = 1", sim);
		gen1.activate(0);

		Generator gen2 = new Generator(3, 2000, queue2, "Generátor s labda = 3", sim);
		gen2.activate(0);
	}

	/**
	 * Vytvoření a puštění SHO.
	 */
	private static void createSHO(Queue queue1, Queue queue2, Queue queue3, Queue queue4, Output output, JSimSimulation sim) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException, JSimSecurityException {
		Pipeline p1 = new Pipeline(queue3);
		SHO sho1 = new SHO(2, queue1, p1, "SHO 1", sim);
		sho1.activate(0);

		Pipeline p2 = new Pipeline(queue3, queue2, 0.9);
		SHO sho2 = new SHO(5, queue2, p2, "SHO 2", sim);
		sho2.activate(0);

		Pipeline p3 = new Pipeline(queue4, queue2, 0.95);
		SHO sho3 = new SHO(5, queue3, p3, "SHO 3", sim);
		sho3.activate(0);

		Pipeline p4 = new Pipeline(output, queue1, 0.98);
		SHO sho4 = new SHO(5, queue4, p4, "SHO 4", sim);
		sho4.activate(0);
	}

	/* process */
	/*process = new MyProcess("My process No 1",
	 /*sim /*,... other parameters  ...*//*);
	 process.activate(1.2345);

	 /* vkládání prvku do fronty */
	/*JSimLink element;
	 int i;
	 for (i = 0; i < 5; i++) {
	 element = new JSimLink(new Integer(i));
	 element.into(queue1);
	 }

	 /* vkládání před a za prvek */
	/*JSimLink before, after;
	 after = new JSimLink(new Float(0.5));
	 before = new JSimLink(new Float(3.5));
	 after.follow(queue1.first());
	 before.precede(queue1.last());

	 /* projde a vytiskne prvky ve frontě - po vytištění prvku je prvek odstraněn */
//			String dataType;
//			Integer intObject;
//			Float floatObject;
//
//			while (!queue.empty()) {
//				element = queue.first();
//				dataType = element.getDataType();
//				System.out.println("The first element is of type: "
//						+ dataType);
//				switch (dataType.charAt(dataType.lastIndexOf('.') + 1)) {
//					case 'I': // Integer
//						intObject = (Integer) element.getData();
//						System.out.println("Data: " + intObject.toString());
//						break;
//					case 'F': // Float
//						floatObject = (Float) element.getData();
//						System.out.println("Data: " + floatObject.toString());
//						break;
//					default:
//						System.out.println("There is an unknown object at"
//								+ " the head of the queue.");
//				} // switch
//				element.out();
//				System.out.println("Number of items in the queue: "
//						+ queue.cardinal());
//			}
}
