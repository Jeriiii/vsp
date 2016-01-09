/*
 * VSP - Semestrální práce.
 */
package vsp_sp.generator;

/**
 * Rozdělení (např. normálové, exponenciální)
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public interface IDistribution {

	/**
	 * Vygeneruje náhodné číslo s pravděpodobnosí zadaného rozdělení.
	 *
	 * @return Náhodné číslo.
	 */
	public double generate();

	/**
	 * Vrátí rozptyl daného generátoru.
	 *
	 * @return Rozptyl.
	 */
	public double getD();

}
