package view;

import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;

import core.App;

public class NumTextField extends Field {
	
	public NumTextField() {
		super();
		
	}
	
	

	@Override
	protected void processKeyEvent(KeyEvent ev) {
		char c = ev.getKeyChar();
	    try {
	      // Ignore all non-printable characters. Just check the printable ones.
	      if (c > 31 && c < 127) {
	        Integer.parseInt(c + "");
	      }
	      super.processKeyEvent(ev);
	    }
	    catch (NumberFormatException nfe) {
	      // Do nothing. Character inputted is not a number, so ignore it.
	    }
	}


}
