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

	public SHO(IDistribution d, Queue queue, String name, JSimSimulation parent)
			throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException {
		super(name, parent);

		this.distribution = d;
		this.queue = queue;
		this.pipeline = pipeline;
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

					JSimLink item = queue.first();

					if (item != null) //pokud item je null, je fronta prázdná
					{
						pipeline.insert(item);
						counter++;
						//System.out.println("SHO " + name + " zpracovalo prvek");
					}

				}
			}
		} catch (JSimSecurityException ex) {
			Logger.getLogger(SHO.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Vloží prvek do fronty tohoto SHO.
	 *
	 * @param item Prvek co se má vložit
	 * @throws JSimSecurityException
	 */
	public void insert(JSimLink item) throws JSimSecurityException {
		try {
			item.out();
		} catch (JSimSecurityException e) {
			//reakce na vyjímku, kdy prvek není v žádné frontě
			//když není v žádné frontě, tak se prostě nic nestane -> opravdu tu nemá být žádný kód
		}
		item.into(queue);

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
		return queue.getTw();
	}

	/**
	 * Vrátí průměrnou dobu zpracování prvku.
	 */
	public double getTs() {
		return sumTs / ((double) counter);
	}

	/**
	 * Vrátí průměrnou dobu, kterou musel prvek strávit v tomto SHO
	 */
	public double getTq() {
		return this.getTw() + this.getTs();
	}

}
