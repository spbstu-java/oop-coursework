package coursework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

	private static JFrame frame;
	private static JPanel pages;
	
	private static ActionListener menuListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	CardLayout l = (CardLayout) pages.getLayout();
        	String tag = e.getActionCommand();
        	l.show(pages, tag);
        	frame.setVisible(true);
        }
    };
    
	public static void main() {
		frame = new JFrame("JFrame Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Выбрать работу");
        JMenuItem lab1Item = new JMenuItem("Работа 1");
        JMenuItem lab2Item = new JMenuItem("Работа 2");
        JMenuItem lab3Item = new JMenuItem("Работа 3");
        JMenuItem lab4Item = new JMenuItem("Работа 4");

        fileMenu.add(lab1Item);
        fileMenu.add(lab2Item);
        fileMenu.add(lab3Item);
        fileMenu.add(lab4Item);
        menuBar.add(fileMenu);
        
        // panel
        pages = new JPanel(new CardLayout());
        frame.add(pages);

        var lab1Panel = lab1.Lab1.getPanel();
        var lab2Panel = lab2.Lab2.getPanel();
        var lab3Panel = lab3.Lab3.getPanel();
        var lab4Panel = lab4.Lab4.getPanel();
        
        var intro = new JPanel(new GridLayout(0, 2));
        JButton lab1Btn = new JButton("Работа 1");
        JButton lab2Btn = new JButton("Работа 2");
        JButton lab3Btn = new JButton("Работа 3");
        JButton lab4Btn = new JButton("Работа 4");
        intro.add(lab1Btn);
        intro.add(lab2Btn);
        intro.add(lab3Btn);
        intro.add(lab4Btn);
        
        pages.add(intro, "blank");
        pages.add(lab1Panel, "Работа 1");
    	pages.add(lab2Panel, "Работа 2");
    	pages.add(lab3Panel, "Работа 3");
    	pages.add(lab4Panel, "Работа 4");

        lab1Item.addActionListener(menuListener);
        lab2Item.addActionListener(menuListener);
        lab3Item.addActionListener(menuListener);
        lab4Item.addActionListener(menuListener);

        lab1Btn.addActionListener(menuListener);
        lab2Btn.addActionListener(menuListener);
        lab3Btn.addActionListener(menuListener);
        lab4Btn.addActionListener(menuListener);

        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
	}
}
