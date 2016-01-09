/*
 * VSP - Semestrální práce.
 */
package vsp_sp.node;

import vsp_sp.node.INode;
import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimLink;
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
import vsp_sp.generator.IDistribution;

/**
 * Elementární SHO.
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class SHO extends BaseProcess implements INode {

	/**
	 * Fronta tohoto SHO.
	 */
	private Queue queue;

	/**
	 * Pipeline pro výstupní proud z tohoto SHO
	 */
	private Pipeline pipeline;

	/**
	 * Generátor náhodného čísla daného rozdělení.
	 */
	private IDistribution distribution;

	/**
	 * Počet položek co prošel daným SHO
	 */
	private int counter = 0;

	/**
	 * Celkový součet všech Ts co prošli tímto SHO
	 */
	private double sumTs = 0.0;

	/**
	 * Celkový součet všech časů, které strávili položky v tomto SHO
	 */
	private double sumTq = 0.0;

	/**
	 * Intenzita obsluhy
	 */
	private double mi;

	public SHO(IDistribution d, double mi, Queue queue, String name, JSimSimulation parent)
			throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException {
		super(name, parent);

		this.distribution = d;
		this.mi = mi;
		this.queue = queue;
	}

	@Override
	/**
	 * Postupně zpracovává prvky z fronty.
	 */
	protected void life() {
		try {
			while (true) {
				if (queue.empty()) {

					passivate(); /* když je prázdná, tak se uspí */

				} else {

					double random = distribution.generate();
					sumTs = sumTs + random;

					waitTo(random);

					JSimLink jitem = queue.first();

					Item item = (Item) (jitem.getData());
					sumTq = sumTq + item.getInputToSHODiff(myParent.getCurrentTime());

					pipeline.insert(jitem);
					counter++;
				}
			}
		} catch (JSimSecurityException ex) {
			Logger.getLogger(SHO.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Vloží prvek do fronty tohoto SHO.
	 *
	 * @param jitem Prvek co se má vložit
	 * @throws JSimSecurityException
	 */
	public void insert(JSimLink jitem) throws JSimSecurityException {
		Item item = (Item) (jitem.getData());
		item.inputToSHOTime = myParent.getCurrentTime();

		try {
			jitem.out();
		} catch (JSimSecurityException e) {
			//reakce na vyjímku, kdy prvek není v žádné frontě
			//když není v žádné frontě, tak se prostě nic nestane -> opravdu tu nemá být žádný kód
		}
		jitem.into(queue);

		if (this.isIdle() && (!this.isHold())) {
			activateNow();
		}
	}

	public void setPipeline(Pipeline p) {
		this.pipeline = p;
	}

	/**
	 * Vrátí počet prvků které prošli tímto SHO
	 */
	public int getCounter() {
		return counter;
	}

	/**
	 * Vrátí průměrnou dobu, jakou musel prvek čekat ve frontě než přišel na
	 * zpracování.
	 */
	public double getTw() {
		return getTq() - getTs();
	}

	/**
	 * Vrátí průměrný počet položek ve frontě.
	 */
	public double getLw() {
		return getTw() * getLambda();
	}

	/**
	 * Vrátí průměrnou dobu zpracování prvku.
	 */
	public double getTs() {
		return sumTs / ((double) counter);
	}

	/**
	 * Vrátí průměrnou dobu zpracování prvku.
	 */
	public double getLs() {
		return getLq() - getLw();
	}

	/**
	 * Vrátí průměrnou dobu, kterou musel prvek strávit v tomto SHO
	 */
	public double getTq() {
		return sumTq / ((double) counter);
	}

	/**
	 * Vrátí průměrnou počet prvků které jsou v SHO
	 */
	public double getLq() {
		return getLambda() * getTq();
	}

	/**
	 * Vrátí vypočítanou velkou lambdu.
	 */
	public double getLambda() {
		double lambda = counter / myParent.getCurrentTime();

		return lambda;
	}

	/**
	 * Vrátí zatížení tohoto SHO.
	 */
	public double getLoad() {
		double load = getLambda() / mi;

		return load;
	}

	/**
	 * Vrátí rozptyl generátoru tohoto SHO
	 *
	 * @return
	 */
	public double getD() {
		return distribution.getD();
	}

}
