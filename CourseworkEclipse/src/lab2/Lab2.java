package lab2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.AccessFlag;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Lab2 {
	
	public static JPanel getPanel() {
		JPanel lab2Panel = new JPanel(new GridLayout(0, 2));
		var output = new JTextArea();
		output.setEditable(false);
		
		var scrollable = new JScrollPane(output);
		var btn = new JButton("Выполнить");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PrintStream out = System.out;
				System.setOut(new PrintStream(baos));
				_main();
				output.setText(baos.toString());
				System.setOut(out);
			}
			
		});
		lab2Panel.add(new Label("Выполнить"));
		lab2Panel.add(btn);

		lab2Panel.add(new Label("Вывод"));
		lab2Panel.add(scrollable);
		
		return lab2Panel;
	}
	
	public static void _main() {
		var ac = new AnnotatedClass();
		var cl = ac.getClass();
		System.out.println(int.class.getDeclaredConstructors().toString());
		var methods = cl.getDeclaredMethods();
		for (int i=0; i < methods.length; ++i) {
			var m = methods[i];
			var an = m.getAnnotation(MyAnnotation.class);
			if (an != null && 
					(
					m.accessFlags().contains(AccessFlag.PROTECTED) ||
					m.accessFlags().contains(AccessFlag.PRIVATE)
					)
				) {
				m.setAccessible(true);
				var param_types = m.getParameterTypes();
				try {
					var parameters = ParameterFactory.make_parameters(param_types);
					System.out.print("\n-----\nMethod: ");
					System.out.println(m);
					System.out.print("Annotation: ");
					System.out.println(an);
					
					System.out.println(String.format("\nCalling the method %d times:\n", an.parameter()));
					for (int j=0; j<an.parameter(); ++j) {
						try {
							m.invoke(ac, parameters);
						} catch (IllegalAccessException|InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
