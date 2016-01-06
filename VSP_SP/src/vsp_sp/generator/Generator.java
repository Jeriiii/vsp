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
import vsp_sp.Item;
import vsp_sp.Pipeline;

/**
 * Třída zajišťující vstupní proud požadavků do sítě front.
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class Generator extends BaseProcess {

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

	/**
	 * Generátor náhodného čísla daného rozdělení.
	 */
	private IDistribution distribution;

	public Generator(int countGenItems, IDistribution d, Pipeline p, String name, JSimSimulation parent)
			throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException {
		super(name, parent);

		this.countGenItems = countGenItems;
		this.pipeline = p;
		this.distribution = d;
	}

	/**
	 * Postupně generuje prvky
	 */
	@Override
	protected void life() {
		for (int i = 0; i < countGenItems; i++) {
			double random = distribution.generate();

			waitTo(random);

			Item item = new Item(myParent.getCurrentTime());
			JSimLink litem = new JSimLink(item);
			into(litem);

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
