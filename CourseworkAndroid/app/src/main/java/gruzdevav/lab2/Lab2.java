package gruzdevav.lab2;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.lang.reflect.InvocationTargetException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Modifier;

import gruzdevav.coursework.R;

public class Lab2 {
	
	public static View connectView(View view) {

		EditText output = view.findViewById(R.id.lab2_output);
		Button btn = view.findViewById(R.id.lab2_btn_execute);

		btn.setOnClickListener(v -> {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream out = System.out;
			System.setOut(new PrintStream(baos));
			_main();
			output.setText(baos.toString());
			System.setOut(out);
		});
		return view;
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
					Modifier.isProtected(m.getModifiers()) ||
					Modifier.isPrivate(m.getModifiers())
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
