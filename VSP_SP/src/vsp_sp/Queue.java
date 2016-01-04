/*
 * VSP - Semestrální práce.
 */
package vsp_sp;

import cz.zcu.fav.kiv.jsim.JSimHead;
import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimTooManyHeadsException;

/**
 * Fronta.
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class Queue extends JSimHead implements IQueue {

	public Queue(String name, JSimSimulation parent) throws JSimInvalidParametersException, JSimTooManyHeadsException {
		super(name, parent);
	}

}
