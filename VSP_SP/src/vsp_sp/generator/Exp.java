/*
 * VSP - Semestrální práce.
 */
package vsp_sp.generator;

import cz.zcu.fav.kiv.jsim.JSimSystem;

/**
 * Generuje náhodná čísla exponenciálního rozdělení
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class Exp implements IDistribution {

	/**
	 * Lambda exponenciálního rozdělení.
	 */
	private final double lambda;

	public Exp(double lambda) {
		this.lambda = lambda;
	}

	/**
	 * Vygeneruje náhodné číslo s pravděpodobnosí zadaného exp. rozdělení.
	 *
	 * @return Náhodné číslo.
	 */
	@Override
	public double generate() {
		return JSimSystem.negExp(this.lambda);
	}

}
