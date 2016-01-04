/*
 * VSP - Semestrální práce.
 */
package vsp_sp;

import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimProcess;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;

/**
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class MyProcess extends JSimProcess {

	public JSimProcess anotherProcess;

	public MyProcess(String name, JSimSimulation sim/*, ... new parameters ...*/)
			throws JSimSimulationAlreadyTerminatedException,
			JSimInvalidParametersException,
			JSimTooManyProcessesException {
		super(name, sim);

		// ..... new code
	}

	protected void life() {
		message("Hello!");
	}

	public void setAnother(JSimProcess another) {
		anotherProcess = another;
	}
}
