/*
 * VSP - Semestrální práce.
 */
package vsp_sp;

import cz.zcu.fav.kiv.jsim.JSimLink;
import cz.zcu.fav.kiv.jsim.JSimSecurityException;
import cz.zcu.fav.kiv.jsim.JSimSystem;

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
	private INode mainTargetSHO;

	/**
	 * Hlavní fronta, představuje frontu do které se prvek přesune s menší
	 * pravděpodobností
	 */
	private INode secondaryTargetSHO = null;

	/**
	 * Pravděpodobnost, že se prvek vloží do fronty mainTargetSHO
	 */
	private double probability = 1;

	/**
	 * Pokud jsou cílové fronty dvě a prvek se přesouvá do jedné z nich na
	 * základě určité pravděpodobnosti.
	 */
	public Pipeline(INode mainTargetSHO, INode secondaryTargetSHO, double probability) {
		this.mainTargetSHO = mainTargetSHO;
		this.secondaryTargetSHO = secondaryTargetSHO;
		this.probability = probability;
	}

	/**
	 * Cílová fronta je pouze jedna.
	 */
	public Pipeline(INode mainTargetSHO) {
		this.mainTargetSHO = mainTargetSHO;
	}

	/**
	 * Vloží prvek do fronty v závislosti na nastavené pravděpodobnosti.
	 *
	 * @param item Prvek co se má vložit.
	 * @throws JSimSecurityException
	 */
	public void insert(JSimLink item) throws JSimSecurityException {
		double random = JSimSystem.uniform(0.0, 1.0);

		if (random <= probability) {
			insertToQueue(item, mainTargetSHO);
		} else {
			insertToQueue(item, secondaryTargetSHO);
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
	private void insertToQueue(JSimLink item, INode node) throws JSimSecurityException {
		if (node instanceof Output) { //pokud jde o výstupní kanál z celé sítě front, prvek se již nikam neukládá
			System.out.println("out");
			item.out();
			((Output) node).out(item);
			return;
		} else {
			SHO sho = (SHO) node;
			sho.insert(item);
			System.out.println("Vloženo do " + sho.name);
		}
	}

}
