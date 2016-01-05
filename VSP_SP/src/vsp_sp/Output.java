/*
 * VSP - Semestrální práce.
 */
package vsp_sp;

/**
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class Output implements INode {

	/**
	 * Počet položek které prošli sítí.
	 */
	public int counter = 0;

	public void out(Object item) {
		counter++;
	}

}
