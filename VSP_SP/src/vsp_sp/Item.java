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

	/**
	 * Čas, kdy vstoupil prvek do SHO
	 */
	public double inputToSHOTime;

	public Item(double createSimulationTime) {
		this.createSimulationTime = createSimulationTime;
	}

	public double getCreatedDiff(double simulationTime) {
		return simulationTime - createSimulationTime;
	}

	public double getInputToSHODiff(double simulationTime) {
		return simulationTime - inputToSHOTime;
	}

}
