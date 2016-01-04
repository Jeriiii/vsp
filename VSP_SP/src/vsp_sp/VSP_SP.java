/*
 * VSP - Semestrální práce.
 */
package vsp_sp;

import cz.zcu.fav.kiv.jsim.JSimException;
import cz.zcu.fav.kiv.jsim.JSimLink;
import cz.zcu.fav.kiv.jsim.JSimSimulation;

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
		Generator gen1 = null, gen2 = null;

		try {
			sim = new JSimSimulation("First simulation");

			queue1 = new Queue("Fronta 1", sim);
			queue2 = new Queue("Fronta 2", sim);
			queue3 = new Queue("Fronta 3", sim);
			queue4 = new Queue("Fronta 4", sim);

			/* generátor */
			gen1 = new Generator(1, 2000, queue1, "Generátor s labda = 1", sim);
			gen1.activate(0);

			gen2 = new Generator(3, 2000, queue1, "Generátor s labda = 3", sim);
			gen2.activate(0);

			while (sim.step() == true);
		} catch (JSimException e) {
			e.printStackTrace();
			e.printComment(System.err);
		} finally {
			sim.shutdown();
		}
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
