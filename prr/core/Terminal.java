package prr.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import prr.app.exception.UnknownTerminalKeyException;
import prr.core.exception.*;
enum TerminalState{
  IDLE,
  OFF,
  SILENCE,
  OCCUPIED,
  
}

enum TerminalType{
  BASIC,
  FANCY
}
// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable, Comparable<Terminal> /* FIXME maybe addd more interfaces */{

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  
  // FIXME define attributes
  private String _id;
  private long _payment;
  private long _debt;
  private String _owner;
  private List<Terminal> _friends;
  private TerminalState _state;
  private TerminalType _type;
  private List<Communication> _communicationList;

  // FIXME define contructor(s)
  public Terminal(String id, String owner, TerminalType type){
    _id = id;
    _owner = owner;
    _payment = 0;
    _debt = 0;
    _friends = new ArrayList<>();
    _state = TerminalState.IDLE;
    _type = type;
    _communicationList = new ArrayList<Communication>();
  }
  
  public long getTerminalPayments(){
    return _payment;
    
  }

  public void setOnSilent() {
    if (_state != TerminalState.OFF){
      _state = TerminalState.SILENCE;
    }
  } 
  public void turnOff() {
    if (_state != TerminalState.OCCUPIED){
      _state = TerminalState.OFF;
    }
  }
  public void turnOn(){
    if (_state == TerminalState.OFF){
      _state = TerminalState.IDLE;
    }
  }

  public long getTerminalDebts(){
    return _debt;
  }

  public long getTerminalBalance(){
    return _payment - _debt;
  }

  public boolean isOff(){
    return (_state == TerminalState.OFF);
  }
  public boolean isOn(){
    return (_state != TerminalState.OFF);
  }
  public boolean isSilent(){
    return (_state == TerminalState.SILENCE);
  }

  public String getId(){
    return _id;
  }

  public String getOwner(){
    return _owner;
  }

  public List<Terminal> getFriends(){
    return _friends;
  }

  public TerminalState getTerminalState(){
    return _state;
  }

  public TerminalType getType(){
    return _type;
  }

  public void addFriend(Terminal f) throws DuplicateTerminalException{
    
    if(_id.equals(f.getId())) {
      //não pode ser friend dele próprio
      throw new DuplicateTerminalException(f.getId());
    }

    for(Terminal t : _friends){
      //não pode ser friend de um friend já existente na lista de friends
      if (t.getId().equals(f.getId()) ){
        throw new DuplicateTerminalException(f.getId());
      }
    }

    _friends.add(f);
  }

  public void removeFriendById(String friendId ) throws UnknownTerminalKeyException {
    boolean didRemove = false;
    for (Terminal t: _friends){
      
      if (t.getId().equals(friendId)){
        _friends.remove(t);
        didRemove = true;
        break;
      }
    }

    if (!didRemove) {
      throw new UnknownTerminalKeyException(friendId);
    }
  }

  public void removeFriend(Terminal f) throws UnknownTerminalKeyException {
    boolean didRemove = false;
    for (Terminal t: _friends){
      if (t.getId().equals(f.getId())){
        _friends.remove(f);
        didRemove = true;
        break;
      }
    }
    if (!didRemove) {
      throw new UnknownTerminalKeyException(f.getId());
    }
  }
  
  public List<String> getSortedFriendsListIds(){
    ArrayList<String> orderedList = new ArrayList<String>();
    Collections.sort(_friends);
    for (Terminal t : _friends){
      String order = t.getId();
      orderedList.add(order);
    }
    return orderedList;
  }

  public boolean isActive(){
    return (_communicationList.size() > 0);
  }
  

  public int compareTo(Terminal t) {
    return Integer.parseInt(_id) - Integer.parseInt(t.getId());
  }
  /**
   * Checks if this terminal can end the current interactive communication.
   *
   * @return true if this terminal is busy (i.e., it has an active interactive communication) and
   *          it was the originator of this communication.
   **/
  public boolean canEndCurrentCommunication() {
    // FIXME add implementation code
    return false;
  }
  
  /**
   * Checks if this terminal can start a new communication.
   *
   * @return true if this terminal is neither off neither busy, false otherwise.
   **/
  public boolean canStartCommunication() {
    // FIXME add implementation code
    return true; 
  }


  public String toString() {

    String terminalString = 
    getType() +"|"+
    getId() +"|"+
    getOwner() +"|"+
    getTerminalState() +"|"+
    Math.round(getTerminalPayments()) +"|"+
    Math.round(getTerminalDebts());


    if(_friends.size() > 0) {
      terminalString += "|" + String.join(",", getSortedFriendsListIds());
    }

    return terminalString;
  }
}
