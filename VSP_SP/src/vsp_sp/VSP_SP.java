/*
 * VSP - Semestrální práce.
 */
package vsp_sp;

import java.util.logging.Level;
import java.util.logging.LogManager;
import vsp_sp.generator.Exp;
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

		GeneratorCreator gc = new GeneratorCreator();
		if (district.equals("EXP") || district.equals("exp")) {
			gc.setCreateExp();
		} else {
			gc.setCreateGauss();
		}

		sim.run(gc, 100000);

		sim.printStatistics();
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
