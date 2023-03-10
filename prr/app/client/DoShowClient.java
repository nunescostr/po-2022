package prr.app.client;

import prr.core.Client;
import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;

import prr.core.exception.ClientNotFoundException;


/**
 * Show specific client: also show previous notifications.
 */
class DoShowClient extends Command<Network> {

  DoShowClient(Network receiver) {
    super(Label.SHOW_CLIENT, receiver);

    addStringField("key",Message.key());
  }
  
  @Override
  protected final void execute() throws UnknownClientKeyException {
    String key = stringField("key");
     
    try{
      Client c = _receiver.getClient(key);
      _display.add(c.toString());
      _display.addAll(c.getNotificationsList());
      _display.display();
      c.deleteNotifications();
    }
    catch(ClientNotFoundException cnfe){
      throw new UnknownClientKeyException(key);
    }
  }
}
