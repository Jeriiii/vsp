/*
 * VSP - Semestrální práce.
 */
package vsp_sp.generator;

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
import vsp_sp.BaseProcess;
import vsp_sp.Pipeline;

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
	 * Pipeline, do které bude generátor vkládat vygenerované prvky.
	 */
	private Pipeline pipeline;

	/**
	 * Počet položek vygenerovaných generátorem.
	 */
	private int countGenItems;

	/**
	 * TRUE = generátor vygeneroval všechny potřebné prvky, jinak FALSE
	 */
	public boolean finished = false;

	public Generator(int lambda, int countGenItems, Pipeline p, String name, JSimSimulation parent)
			throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException {
		super(name, parent);

		this.lambda = lambda;
		this.countGenItems = countGenItems;
		this.pipeline = p;
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
			into(item);

		}

		finished = true;
	}

	/**
	 * Vloží prvek do fronty.
	 *
	 * @param item Prvek, co se má vložit do fronty.
	 */
	private void into(JSimLink item) {
		try {
			pipeline.insert(item);
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
