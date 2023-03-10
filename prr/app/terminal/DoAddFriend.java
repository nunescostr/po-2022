package prr.app.terminal;

import prr.app.exception.DuplicateTerminalKeyException;
import prr.app.exception.UnknownTerminalKeyException;
import prr.core.Network;
import prr.core.Terminal;
import prr.core.exception.DuplicateTerminalException;
import prr.core.exception.TerminalNotFoundException;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Add a friend.
 */
class DoAddFriend extends TerminalCommand {

  DoAddFriend(Network context, Terminal terminal) {
    super(Label.ADD_FRIEND, context, terminal);
    addStringField("key",Message.terminalKey() );
  }
  
  @Override
  protected final void execute() throws CommandException, UnknownTerminalKeyException, DuplicateTerminalKeyException{
    String key = stringField("key");
    Terminal t;
    try{
      t = _network.getTerminal(key);
      _receiver.addFriend(t);
    }
    catch(TerminalNotFoundException tnfe) {
      throw new UnknownTerminalKeyException(key);
    }
    catch(DuplicateTerminalException dte){
      //nao sabemos o que é suposto fazer nesta situação...
      //throw new DuplicateTerminalKeyException(key);
    }
    




  }
}
