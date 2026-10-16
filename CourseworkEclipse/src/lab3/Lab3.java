package lab3;

import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Lab3 {

	public static Dictionary dictionary = new Dictionary();
	
	public static JPanel getPanel() {
		JPanel lab3Panel = new JPanel(new GridLayout(0, 2));
		var input = new JTextArea();
		var output = new JTextArea();
		output.setEditable(false);

		var dict = new JTextArea();
		dict.setEditable(false);

		var scrollableInput = new JScrollPane(input);
		var scrollableOutput = new JScrollPane(output);
		var scrollableDict = new JScrollPane(dict);

		var btnExecute = new JButton("Выполнить");
		var btnChooseInput = new JButton("Выбрать файл ввода");
		var btnChooseDict = new JButton("Выбрать файл словаря");
		var inputFileChooser = new JFileChooser(".");
		var dictionaryFileChooser = new JFileChooser(".");
		btnChooseDict.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				var result = dictionaryFileChooser.showOpenDialog(null);
				if (result==JFileChooser.APPROVE_OPTION) {
					var file = dictionaryFileChooser.getSelectedFile();
					try {
						dictionary = new Dictionary(file.getPath());
						dict.setText(dictionary.toString());
					} catch (FileReadException e1) {
						JOptionPane.showMessageDialog(lab3Panel, "Ошибка чтения файла словаря.");
						e1.printStackTrace();
					} catch (InvalidFileFormatException e1) {
						JOptionPane.showMessageDialog(lab3Panel, "Файл словаря имеет некорректный формат.");
						e1.printStackTrace();
					}
				}
			}
		});
		btnChooseInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				var result = inputFileChooser.showOpenDialog(null);
				if (result==JFileChooser.APPROVE_OPTION) {
					var file = inputFileChooser.getSelectedFile();
					try (FileReader reader = new FileReader(file.getPath())){
						var inputText = reader.readAllAsString();
						input.setText(inputText);
					}
			        catch(IOException ex){
						JOptionPane.showMessageDialog(lab3Panel, "Ошибка чтения файла, содержащего текст для ввода.");
			        }   
				}
			}
		});
		btnExecute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				var outputText = dictionary.translate(input.getText());
				output.setText(outputText);
			}
		});
		lab3Panel.add(new Label("Выбрать словарь"));
		lab3Panel.add(btnChooseDict);
		
		lab3Panel.add(new Label("Словарь"));
		lab3Panel.add(scrollableDict);

		lab3Panel.add(new Label("Выбрать ввод"));
		lab3Panel.add(btnChooseInput);
		
		lab3Panel.add(new Label("Ввод"));
		lab3Panel.add(scrollableInput);
		
		lab3Panel.add(new Label("Выполнить"));
		lab3Panel.add(btnExecute);
		
		lab3Panel.add(new Label("Вывод"));
		lab3Panel.add(scrollableOutput);
		
		return lab3Panel;
	}
}
