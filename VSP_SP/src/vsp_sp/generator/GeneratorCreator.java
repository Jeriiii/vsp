/*
 * VSP - Semestrální práce.
 */
package vsp_sp.generator;

/**
 * Vytváří exponenciální nebo gaussovské generátory
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class GeneratorCreator {

	/**
	 * TRUE = vytvářejí se generátory s gaussovským rozdělením.
	 */
	private boolean createGauss = false;

	/**
	 * TRUE = vytvářejí se generátory s exponenciálním rozdělením.
	 */
	private boolean createExp = false;

	/**
	 * Sigma pro Gauss;v generátor náhodných čísel.
	 */
	private double sigma = 0.5;

	/**
	 * Vytvoří a vrátí nový generátor náhodných čísel s daným rozdělením.
	 *
	 * @param ex Střední hodnota
	 * @return Nový generátor s daným rozdělením. Pokud není žádný generátor
	 * nastavený vrací NULL
	 */
	public IDistribution createGenerator(double ex) {
		IDistribution generator = null;

		if (createGauss) {
			generator = new Gauss(ex);
		}
		if (createExp) {
			generator = new Exp(ex);
		}

		return generator;
	}

	/**
	 * Vytvářejí se generátory s gaussovským rozdělením.
	 */
	public void setCreateGauss() {
		this.createGauss = true;
	}

	/**
	 * Vytvářejí se generátory s exponenciálním rozdělením.
	 */
	public void setCreateExp() {
		this.createExp = true;
	}

	/**
	 * Nastaví sigmu pro Gaussův generátor náhodných čísel.
	 *
	 * @param sigma
	 */
	public void setSigma(double sigma) {
		this.sigma = sigma;
	}

}
