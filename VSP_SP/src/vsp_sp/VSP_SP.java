/*
 * VSP - Semestrální práce.
 */
package vsp_sp;

import cz.zcu.fav.kiv.jsim.JSimException;
import cz.zcu.fav.kiv.jsim.JSimHead;
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
		JSimSimulation simulation = null;
		JSimHead queue = null;
		MyProcess process = null;

		try {
			simulation = new JSimSimulation("First simulation");

			queue = new JSimHead("Little queue", simulation);
			/* vkládání prvku do fronty */
			JSimLink element;
			int i;
			for (i = 0; i < 5; i++) {
				element = new JSimLink(new Integer(i));
				element.into(queue);
			}

			/* vkládání před a za prvek */
			JSimLink before, after;
			after = new JSimLink(new Float(0.5));
			before = new JSimLink(new Float(3.5));
			after.follow(queue.first());
			before.precede(queue.last());

			/* projde a vytiskne prvky ve frontě - po vytištění prvku je prvek odstraněn */
			String dataType;
			Integer intObject;
			Float floatObject;

			while (!queue.empty()) {
				element = queue.first();
				dataType = element.getDataType();
				System.out.println("The first element is of type: "
						+ dataType);
				switch (dataType.charAt(dataType.lastIndexOf('.') + 1)) {
					case 'I': // Integer
						intObject = (Integer) element.getData();
						System.out.println("Data: " + intObject.toString());
						break;
					case 'F': // Float
						floatObject = (Float) element.getData();
						System.out.println("Data: " + floatObject.toString());
						break;
					default:
						System.out.println("There is an unknown object at"
								+ " the head of the queue.");
				} // switch
				element.out();
				System.out.println("Number of items in the queue: "
						+ queue.cardinal());
			}

			/* process */
			process = new MyProcess("My process No 1",
					simulation /*,... other parameters  ...*/);
			process.activate(1.2345);
		} catch (JSimException e) {
			e.printStackTrace();
			e.printComment(System.err);
		} finally {
			simulation.shutdown();
		}
	}
}
