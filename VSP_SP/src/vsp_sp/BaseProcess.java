/*
 * VSP - Semestrální práce.
 */
package vsp_sp;

import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimProcess;
import cz.zcu.fav.kiv.jsim.JSimSecurityException;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public abstract class BaseProcess extends JSimProcess {

	/**
	 * Název procesu
	 */
	public String name;

	public BaseProcess(String name, JSimSimulation parent) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException {
		super(name, parent);

		this.name = name;
	}

	/**
	 * Pozastaví proces voláním metody hold. Případné vyjímky zaloguje.
	 *
	 * @param waitTime Čas, po který se má proces pozastavit.
	 */
	protected void wait(double waitTime) {
		try {
			this.hold(waitTime);
		} catch (JSimSecurityException ex) {
			Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
		} catch (JSimInvalidParametersException ex) {
			Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
