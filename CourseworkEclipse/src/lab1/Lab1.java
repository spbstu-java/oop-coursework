package lab1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Lab1 {
	public static String[] methods = {"Пешком", "На лошади", "Самолётом"};
	public static Hero hero = new Hero();
	public static JPanel getPanel() {

        var lab1Panel = new JPanel(new GridLayout(0, 2));
        var combobox = new JComboBox<String>(methods);
        combobox.setEditable(false);
        var btnCalculate = new JButton("Идти");
        var lStatus = new JTextField(String.format("Герой в точке (%.2f; %.2f).", hero.location.x, hero.location.y));
        lStatus.setEditable(false);
        var inpX = new JTextField();
        var inpY = new JTextField();
        
        btnCalculate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String method = (String) combobox.getSelectedItem();
					switch(method) {
					case "Пешком":
						hero.wayOfMovement = new MoveByFeet();
						break;
					case "На лошади":
						hero.wayOfMovement = new MoveByHorse();
						break;
					case "Самолётом":
						hero.wayOfMovement = new MoveByPlane();
						break;
					default:
						//error
					}
					var x = Double.parseDouble(inpX.getText());
					var y = Double.parseDouble(inpY.getText());
					lStatus.setText(hero.move(new Point(x, y)));
				}
				catch (Exception NumberFormatException) {
					JOptionPane.showMessageDialog(lab1Panel, "Координаты перемещения (X, Y) должны быть корректными десятичными дробями.");
				}
			}
        	
        });

        lab1Panel.add(new Label("Статус героя:"));
        lab1Panel.add(lStatus);
        
        lab1Panel.add(new Label("Способ передвижения:"));
        lab1Panel.add(combobox);
        
        lab1Panel.add(new Label("Перейти на X:"));
        lab1Panel.add(inpX);
        
        lab1Panel.add(new Label("Перейти на Y:"));
        lab1Panel.add(inpY);
        
        lab1Panel.add(new Label("Вычислить:"));
        lab1Panel.add(btnCalculate);
        
        return lab1Panel;
	}
}
