/*
 * VSP - Semestrální práce.
 */
package vsp_sp;

import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimLink;
import cz.zcu.fav.kiv.jsim.JSimSecurityException;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimSystem;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Elementární SHO.
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class SHO extends BaseProcess {

	/**
	 * Fronta tohoto SHO.
	 */
	private Queue queue;

	/**
	 * Pipeline pro výstupní proud z tohoto SHO
	 */
	private Pipeline pipeline;

	/**
	 * Mí které rozhoduje, jak rychle se budou zpracovávat prvky.
	 */
	private int mi;

	public SHO(int mi, Queue queue, Pipeline pipeline, String name, JSimSimulation parent)
			throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException {
		super(name, parent);

		this.mi = mi;
		this.queue = queue;
		this.pipeline = pipeline;
	}

	@Override
	/**
	 * Postupně zpracovává prvky z fronty.
	 */
	protected void life() {
		for (int i = 0; i < 100000; i++) {
			double random = JSimSystem.negExp(mi);
			wait(random);

			JSimLink item = queue.last();

			if (item != null) //pokud item je null, je fronta prázdná
			{
				insertPipeline(item);
				System.out.println("SHO " + name + " zpracovalo prvek");
			}
		}
	}

	/**
	 * Vloží prvek do pipline.
	 *
	 * @param item Prvek, co se má vložit do pipeline.
	 */
	private void insertPipeline(JSimLink item) {
		try {
			pipeline.insert(item);
		} catch (JSimSecurityException ex) {
			Logger.getLogger(SHO.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
