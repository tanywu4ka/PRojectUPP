package view;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;

import core.App;

public class Field extends JTextField {
	
	public Field() {
		super();
		
		setFont(App.GET.getFont(18));
		
		setBorder(BorderFactory.createCompoundBorder(
		        getBorder(), 
		        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	}
	
	@Override 
	public void setBorder(Border border) {
		
	}

}
