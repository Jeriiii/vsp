/*
 * VSP - Semestrální práce.
 */
package vsp_sp.node;

import vsp_sp.node.INode;
import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimLink;
import cz.zcu.fav.kiv.jsim.JSimSecurityException;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimSystem;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;
import java.util.logging.Level;
import java.util.logging.Logger;
import vsp_sp.BaseProcess;
import vsp_sp.Pipeline;

/**
 * Elementární SHO.
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class SHO extends BaseProcess implements INode {

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

	public SHO(int mi, Queue queue, String name, JSimSimulation parent)
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
		while (true) {
			if (queue.empty()) {
				try { //když je prázdná, tak se uspí
					passivate();
				} catch (JSimSecurityException ex) {
					Logger.getLogger(SHO.class.getName()).log(Level.SEVERE, null, ex);
				}
			} else {
				double random = JSimSystem.negExp(mi);
				waitTo(random);

				JSimLink item = queue.first();

				if (item != null) //pokud item je null, je fronta prázdná
				{
					insertPipeline(item);
					System.out.println("SHO " + name + " zpracovalo prvek");
				}
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

	/**
	 * Vloží prvek do fronty tohoto SHO.
	 *
	 * @param item Prvek co se má vložit
	 * @throws JSimSecurityException
	 */
	public void insert(JSimLink item) throws JSimSecurityException {
		try {
			item.out();
		} catch (JSimSecurityException e) {
			//reakce na vyjímku, kdy prvek není v žádné frontě
			//když není v žádné frontě, tak se prostě nic nestane -> opravdu tu nemá být žádný kód
		}
		item.into(queue);

		if (this.isIdle() && (!this.isHold())) {
			activateNow();
		}
	}

	public void setPipeline(Pipeline p) {
		this.pipeline = p;
	}
}
