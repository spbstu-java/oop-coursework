package lab4;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.*;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Lab4 {

	public static JPanel getPanel() {
		JPanel lab4Panel = new JPanel(new GridBagLayout());
		var c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		int y = 0;
// ===========================================
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = y++;
		lab4Panel.add(new Label("1) метод, возвращающий среднее значение списка целых чисел"), c);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = y++;
		lab4Panel.add(new Label("список целых чисел (через запятую):"), c);
		var input1 = new JTextField();
		c.gridwidth = 2;
		c.gridx = 1;
		lab4Panel.add(input1, c);
		var btn1 = new JButton("Вычислить среднее");
		var output1 = new JTextArea("");
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String[] t = input1.getText().split(",");
					List<Integer> h = Arrays
							.asList(t)
							.stream()
							.map(x -> Integer.parseInt(x.strip()))
							.toList();
					Double mean_ = mean(h);
					output1.setText(mean_.toString());
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(lab4Panel, e1.getMessage());
				}
			}
		});
		c.gridx = 0;
		c.gridwidth = 1;
		c.gridy = y++;
		lab4Panel.add(btn1, c);
		c.gridwidth = 2;
		c.gridx = 1;
		lab4Panel.add(output1, c);
// ===========================================
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = y++;
		lab4Panel.add(new Label("2) метод, приводящий все строки в списке в верхний регистр и \r\n"
		+ "добавляющий к ним префикс «_new_»"), c);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = y++;
		lab4Panel.add(new Label("список строк (через запятую):"), c);
		var input2 = new JTextField();
		c.gridwidth = 2;
		c.gridx = 1;
		lab4Panel.add(input2, c);
		var btn2 = new JButton("Новый список строк:");
		var output2 = new JTextArea("");
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String[] t = input2.getText().split(",");
					List<String> h = Arrays
							.asList(t)
							.stream()
							.map(x -> x.strip())
							.toList();
					List<String> newStrings = capper(h);
					output2.setText(newStrings.toString());
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(lab4Panel, e1.getMessage());
				}
			}
		});
		c.gridx = 0;
		c.gridwidth = 1;
		c.gridy = y++;
		lab4Panel.add(btn2, c);
		c.gridwidth = 2;
		c.gridx = 1;
		lab4Panel.add(output2, c);

// ===========================================

		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = y++;
		lab4Panel.add(new Label("3) метод, возвращающий список квадратов всех встречающихся \r\n"
			+ "только один раз элементов списка"), c);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = y++;
		lab4Panel.add(new Label("список десятичных дробей (через запятую):"), c);
		var input3 = new JTextField();
		c.gridwidth = 2;
		c.gridx = 1;
		lab4Panel.add(input3, c);
		var btn3 = new JButton("Список квадратов уникальных чисел:");
		var output3 = new JTextArea("");
		btn3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String[] t = input3.getText().split(",");
					List<Double> h = Arrays
							.asList(t)
							.stream()
							.map(x -> Double.parseDouble(x.strip()))
							.toList();
					List<Double> squares = squarer(h);
					output3.setText(squares.toString());
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(lab4Panel, e1.getMessage());
				}
			}
		});
		c.gridx = 0;
		c.gridwidth = 1;
		c.gridy = y++;
		lab4Panel.add(btn3, c);
		c.gridwidth = 2;
		c.gridx = 1;
		lab4Panel.add(output3, c);

// ===========================================
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = y++;
		lab4Panel.add(new Label("4) метод, принимающий на вход коллекцию и возвращающий ее \r\n"
				+ "последний элемент или кидающий исключение, если коллекция \r\n"
				+ "пуста"), c);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = y++;
		lab4Panel.add(new Label("коллекция (через запятую):"), c);
		var input4 = new JTextField();
		c.gridwidth = 2;
		c.gridx = 1;
		lab4Panel.add(input4, c);
		var btn4 = new JButton("Последний элемент:");
		var output4 = new JTextArea("");

		btn4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String[] t = input4.getText().split(",");
					Collection<Object> h;
					if (input4.getText().length() == 0) h = new ArrayList<>();
					else
						h = Arrays
							.asList(t)
							.stream()
// Для имитации того, что в коллекции есть разные типы элементов, а не просто строки
// Хотя это ни на что не влияет
							.map(x -> parse(x))
							.toList();
					Object last = last_or_exception(h);
					output4.setText(last.toString());
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(lab4Panel, e1.getMessage());
					output4.setText(e1.getMessage());
				}
			}
		});

		c.gridx = 0;
		c.gridwidth = 1;
		c.gridy = y++;
		lab4Panel.add(btn4, c);
		c.gridwidth = 2;
		c.gridx = 1;
		lab4Panel.add(output4, c);
// ===========================================
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = y++;
		lab4Panel.add(new Label("5) метод, принимающий на вход массив целых чисел, возвращающий \r\n"
				+ "сумму чётных чисел или 0, если чётных чисел нет"), c);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = y++;
		lab4Panel.add(new Label("список (через запятую):"), c);
		var input5 = new JTextField();
		c.gridwidth = 2;
		c.gridx = 1;
		lab4Panel.add(input5, c);
		var btn5 = new JButton("сумма чётных:");
		var output5 = new JTextArea("");

		btn5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String[] t = input5.getText().split(",");
					List<Integer> h = Arrays
							.asList(t)
							.stream()
							.map(x -> Integer.parseInt(x.strip()))
							.toList();
					Double sumOfEven = sum_of_even(h);
					output5.setText(sumOfEven.toString());
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(lab4Panel, e1.getMessage());
				}
			}
		});
		c.gridx = 0;
		c.gridwidth = 1;
		c.gridy = y++;
		lab4Panel.add(btn5, c);
		c.gridwidth = 2;
		c.gridx = 1;
		lab4Panel.add(output5, c);

// ===========================================
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = y++;
		lab4Panel.add(new Label("6) метод, преобразовывающий все строки в списке в Map, где первый \r\n"
				+ "символ – ключ, оставшиеся – значение"), c);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = y++;
		lab4Panel.add(new Label("список (через запятую):"), c);
		var input6 = new JTextField();
		c.gridwidth = 2;
		c.gridx = 1;
		lab4Panel.add(input6, c);
		var btn6 = new JButton("map:");
		var output6 = new JTextArea("");

		btn6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String[] t = input6.getText().split(",");
					List<String> h = Arrays
							.asList(t)
							.stream()
							.map(x -> x.strip())
							.toList();
					Map<Character, String> map = mapper(h);
					output6.setText(map.toString());
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(lab4Panel, e1.getMessage());
				}
			}
		});
		c.gridx = 0;
		c.gridwidth = 1;
		c.gridy = y++;
		lab4Panel.add(btn6, c);
		c.gridwidth = 2;
		c.gridx = 1;
		lab4Panel.add(output6, c);
		
		return lab4Panel;
	}
	public static Object parse(String input) {
		var temp = input.strip();
		try {
			return Integer.parseInt(temp);
		}
		catch(Exception e) {}
		try {
			return Double.parseDouble(temp);
		}
		catch(Exception e) {}
		return temp;
	}
	public static double mean(List<Integer> ints) {
		return ints.stream().mapToInt(x->x).average().getAsDouble();
	}
	public static List<String> capper(List<String> strings) {
		return strings.stream()
			.map(s -> String.format("_new_%s", s.toUpperCase()))
			.toList();
	}
	public static List<Double> squarer(List<Double> numbers) {
		var counts = numbers.stream()
			.collect(Collectors.groupingBy(x->x, Collectors.counting()));

		return numbers.stream()
//			.filter(x -> numbers.indexOf(x) == numbers.lastIndexOf(x))
			.filter(x -> counts.get(x)==1)
			.map(x -> x * x)
			.toList();
	}
	public static Object last_or_exception(Collection<Object> col) {
		return col.stream().reduce((a,b) -> b).orElseThrow();
	}
	public static double sum_of_even(List<Integer> ints) {
		return ints.stream().mapToInt(x->x)
			.filter(x -> x%2==0).sum();
//			.reduce((a,b) -> a+b)
//			.orElse(0);
	}
	public static Map<Character, String> mapper(List<String> strings) {

		return strings.stream()
			.collect(
				Collectors.toMap(
					x->x.charAt(0),
					x->x.substring(1),
		// Чтобы строки с одинаковыи первым символом не вызывали исключение.
					(a,b) -> a 
				)
			);
	}
	
}
