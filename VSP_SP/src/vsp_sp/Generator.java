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
 * Třída zajišťující vstupní proud požadavků do sítě front.
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class Generator extends JSimProcess {

	private int lambda;

	public Generator(int lambda, String name, JSimSimulation parent)
			throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException {
		super(name, parent);

		this.lambda = lambda;
	}

	@Override
	protected void life() {
		message("Lambda je " + lambda);
	}

}
