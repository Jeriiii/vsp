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
	private IQueue mainTargetQueue;

	/**
	 * Hlavní fronta, představuje frontu do které se prvek přesune s menší
	 * pravděpodobností
	 */
	private IQueue secondaryTargetQueue = null;

	/**
	 * Pravděpodobnost, že se prvek vloží do fronty mainTargetQueue
	 */
	private double probability = 1;

	/**
	 * Pokud jsou cílové fronty dvě a prvek se přesouvá do jedné z nich na
	 * základě určité pravděpodobnosti.
	 */
	public Pipeline(IQueue mainTargetQueue, IQueue secondaryTargetQueue, double probability) {
		this.mainTargetQueue = mainTargetQueue;
		this.secondaryTargetQueue = secondaryTargetQueue;
		this.probability = probability;
	}

	/**
	 * Cílová fronta je pouze jedna.
	 */
	public Pipeline(IQueue mainTargetQueue) {
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
			insertToQueue(item, mainTargetQueue);
		} else {
			insertToQueue(item, secondaryTargetQueue);
		}
	}

	/**
	 * Vloží prvek do fronty. Pokud jde o výstupní kanál, prvek již nikam
	 * nevkládá.
	 *
	 * @param item Prvek co se má vložit.
	 * @param queue Fronta, do které má prvek vložit.
	 * @throws JSimSecurityException
	 */
	private void insertToQueue(JSimLink item, IQueue queue) throws JSimSecurityException {
		if (queue instanceof Output) { //pokud jde o výstupní kanál z celé sítě front, prvek se již nikam neukládá
			return;
		}

		item.into((Queue) mainTargetQueue);
	}

}
