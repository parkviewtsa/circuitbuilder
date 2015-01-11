package bc.desc;

import bc.error.*;

public class Terminal {

  Circuit circ;
  Component component;
  public TerminalType type;
  public float xPos; // relative to centre of component
  public float yPos;
  private Wire in;
  private Wire out;

  public Terminal(Component comp, Circuit c) {
	component = comp;
	circ = c;
  }
  
  public Terminal getPrev() {
	return in.from;
  }
  
  public Terminal getNext() {
	return out.to;
  }

  public void connect(Terminal to, boolean allowOverride)
	throws InvalidPolarityError, ConnectionOverrideError
  {
	// TODO: implement new connect() to match new architecture
  }

  public float getGlobalXPos() {
	return xPos + component.xPos;
  }

  public float getGlobalYPos() {
	return yPos + component.yPos;
  }
}
