package agents;

import java.util.Hashtable;

import behaviours.OfferRequestServer;
import behaviours.PurchaseOrderServer;
import gui.GuiVendedor;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

/**
 * 
 * @author García Briseño José Carlos
 *         Soto Larios Maribella
 *         Torres Amezcua María Guadalupe
 */

public class AgenteVendedor extends Agent{

	private Hashtable catalogue;
	private GuiVendedor gui;
	
	protected void setup() {
	  catalogue = new Hashtable();
	  
	  gui = new GuiVendedor(this);
	  gui.showGui();
	  
	  DFAgentDescription dfd = new DFAgentDescription();
	  dfd.setName(getAID());
	  
	  ServiceDescription sd = new ServiceDescription();
	  sd.setType("Venta de libros");
	  sd.setName("CompraVenta de libros");
	  dfd.addServices(sd);
	  
	  try {
	    DFService.register(this, dfd);
	  }catch(FIPAException fe) {
	    fe.printStackTrace();
	  }
	  
	  addBehaviour(new OfferRequestServer(this));
	  
	  addBehaviour(new PurchaseOrderServer(this));
	}
	
	protected void takeDown() {
	  try {
	    DFService.deregister(this);
	  }catch(FIPAException fe) {
	    fe.printStackTrace();
	  }
	  
	  gui.dispose();
	  
	  System.out.println("Agente Vendedor " + getAID().getName() + "Terminando");
	}
	
	public void updateCatalogue(final String title, final int price) {
	  addBehaviour(new OneShotBehaviour() {
	    public void action() {
	      catalogue.put(title, price);
	      System.out.println("El libro " + title + " tiene un costo de $" + price);
	    }
	  });
	}
	
	public Hashtable getCatalogue() {
	  return catalogue;
	}
}
