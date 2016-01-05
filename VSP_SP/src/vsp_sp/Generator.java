/*
 * VSP - Semestrální práce.
 */
package vsp_sp;

import cz.zcu.fav.kiv.jsim.JSimHead;
import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimLink;
import cz.zcu.fav.kiv.jsim.JSimProcess;
import cz.zcu.fav.kiv.jsim.JSimSecurityException;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimSystem;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Třída zajišťující vstupní proud požadavků do sítě front.
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class Generator extends BaseProcess {

	/**
	 * Lambda - parametr exp. generátor náhodných čísel.
	 */
	private int lambda;

	/**
	 * Fronta, do které bude generátor vkládat vygenerované prvky.
	 */
	private Queue queue;

	/**
	 * Počet položek vygenerovaných generátorem.
	 */
	private int countGenItems;

	/**
	 * TRUE = generátor vygeneroval všechny potřebné prvky, jinak FALSE
	 */
	public boolean finished = false;

	public Generator(int lambda, int countGenItems, Queue queue, String name, JSimSimulation parent)
			throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException {
		super(name, parent);

		this.lambda = lambda;
		this.countGenItems = countGenItems;
		this.queue = queue;
	}

	/**
	 * Postupně generuje prvky
	 */
	@Override
	protected void life() {
		for (int i = 0; i < countGenItems; i++) {
			double random = JSimSystem.negExp(lambda);

			waitTo(random);

			JSimLink item = new JSimLink();
			into(item, queue);

		}

		finished = true;
	}

	/**
	 * Vloží prvek do fronty.
	 *
	 * @param item Prvek, co se má vložit do fronty.
	 * @param queue Fronta.
	 */
	private void into(JSimLink item, Queue queue) {
		try {
			item.into(queue);
		} catch (JSimSecurityException ex) {
			Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Řekne, zda už vygeneroval všechny svoje prvky.
	 *
	 * @return
	 */
	public boolean isFinished() {
		return finished;
	}

}
