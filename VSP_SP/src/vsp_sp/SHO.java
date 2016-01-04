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
 * Elementární SHO.
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class SHO extends JSimProcess {

	/**
	 * Fronta tohoto SHO.
	 */
	private Queue queue;

	public SHO(Queue queue, String name, JSimSimulation parent)
			throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException {
		super(name, parent);

		this.queue = queue;
	}
}
