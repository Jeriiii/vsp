/*
 * VSP - Semestrální práce.
 */
package vsp_sp;

import cz.zcu.fav.kiv.jsim.JSimSimulation;

/**
 * Prvek který prostupuje sítí
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class Item {

	/**
	 * Čas, kdy byla položka vytvořena - zaznamenává se čas ze simulace
	 */
	public double createSimulationTime;

	public Item(double createSimulationTime) {
		this.createSimulationTime = createSimulationTime;
	}

	public double getDiff(double simulationTime) {
		return simulationTime - createSimulationTime;
	}

}
