/*
 * VSP - Semestrální práce.
 */
package vsp_sp.node;

import cz.zcu.fav.kiv.jsim.JSimLink;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import vsp_sp.Item;
import vsp_sp.node.INode;

/**
 * Sem se zaznamenávají všechny prvky co vychází ze sítě.
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class Output implements INode {

	/**
	 * Počet položek které prošli sítí.
	 */
	public int counter = 0;

	/**
	 * Součet všech Tq
	 */
	public double sumTq = 0.0;

	/**
	 * Simulace.
	 */
	private JSimSimulation sim;

	public Output(JSimSimulation sim) {
		this.sim = sim;
	}

	public void out(JSimLink jitem) {
		Item item = (Item) jitem.getData();

		sumTq = sumTq + item.getDiff(sim.getCurrentTime());

		counter++;
	}

	/**
	 * Průměrná Tq celé fronty.
	 */
	public double getTq() {
		return sumTq / ((double) counter);
	}

}
