package view;

import javax.swing.BorderFactory;
import javax.swing.JPasswordField;
import javax.swing.border.Border;

import core.App;

public class Pass extends JPasswordField {

	public Pass() {
		super();
		
		setFont(App.GET.getFont(20));
		
		setBorder(BorderFactory.createCompoundBorder(
		        getBorder(), 
		        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	}
	
	@Override 
	public void setBorder(Border border) {
		
	}
	
}
