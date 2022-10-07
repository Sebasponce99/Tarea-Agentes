package behaviours;

import agents.AgenteVendedor;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * 
 * @author García Briseño José Carlos
 *         Soto Larios Maribella
 *         Torres Amezcua María Guadalupe
 */

public class OfferRequestServer extends CyclicBehaviour{
  
  AgenteVendedor bsAgent;
  
  public OfferRequestServer(AgenteVendedor a) {
    bsAgent = a;
  }
  
  public void action() {
    MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
    ACLMessage msg = bsAgent.receive(mt);
    
    if(msg != null) {
      String title = msg.getContent();
      ACLMessage reply = msg.createReply();
      
      Integer price = (Integer) bsAgent.getCatalogue().get(title);
      
      if(price != null) {
        reply.setPerformative(ACLMessage.PROPOSE);
        reply.setContent(String.valueOf(price.intValue()));
      } else {
        reply.setPerformative(ACLMessage.REFUSE);
        reply.setContent("not-available");
      }
      
      bsAgent.send(reply);
    } else {
      block();
    }
  }
}
