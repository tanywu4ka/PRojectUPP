package view;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JScrollPane;

import core.App;

public class ScrollPane extends JScrollPane {

	public ScrollPane(JList l) {
		super(l);
		configureView();
	}
	public ScrollPane(Component c, int arg1, int arg2) {
		super(c, arg1, arg2);
		configureView();
	}
	
	private void configureView() {
		getVerticalScrollBar().setUnitIncrement(18);
        getVerticalScrollBar().setUI(new ScrollBarStyle());
        getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        
        getHorizontalScrollBar().setUnitIncrement(18);
        getHorizontalScrollBar().setUI(new ScrollBarStyle());
        getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
	}

}
