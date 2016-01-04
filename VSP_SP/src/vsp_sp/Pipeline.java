/*
 * VSP - Semestrální práce.
 */
package vsp_sp;

import cz.zcu.fav.kiv.jsim.JSimLink;
import cz.zcu.fav.kiv.jsim.JSimSecurityException;

/**
 * Spojovací prvek mezi dvěma frontami. Dokáže se rozhodovat do jaké fronty se
 * má prvek poslat, pokud jsou cílové fronty dvě a prvek se do jedné z nich
 * umístí s určitou pravděpodobností.
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class Pipeline {

	/**
	 * Hlavní fronta, představuje frontu do které se prvek přesune s větší
	 * pravděpodobností
	 */
	private Queue mainTargetQueue;

	/**
	 * Hlavní fronta, představuje frontu do které se prvek přesune s menší
	 * pravděpodobností
	 */
	private Queue secondaryTargetQueue = null;

	/**
	 * Pravděpodobnost, že se prvek vloží do fronty mainTargetQueue
	 */
	private double probability = 1;

	/**
	 * Pokud jsou cílové fronty dvě a prvek se přesouvá do jedné z nich na
	 * základě určité pravděpodobnosti.
	 */
	public Pipeline(Queue mainTargetQueue, Queue secondaryTargetQueue, double probability) {
		this.mainTargetQueue = mainTargetQueue;
		this.secondaryTargetQueue = secondaryTargetQueue;
		this.probability = probability;
	}

	/**
	 * Cílová fronta je pouze jedna.
	 */
	public Pipeline(Queue mainTargetQueue) {
		this.mainTargetQueue = mainTargetQueue;
	}

	/**
	 * Vloží prvek do fronty v závislosti na nastavené pravděpodobnosti.
	 *
	 * @param item Prvek co se má vložit.
	 * @throws JSimSecurityException
	 */
	public void insert(JSimLink item) throws JSimSecurityException {
		double random = Math.random();

		if (random <= probability) {
			item.into(mainTargetQueue);
		} else {
			item.into(secondaryTargetQueue);
		}
	}

}
