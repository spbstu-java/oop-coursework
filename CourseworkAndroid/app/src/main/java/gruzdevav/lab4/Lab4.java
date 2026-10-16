package gruzdevav.lab4;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.*;

import gruzdevav.coursework.R;

public class Lab4 {

	public static View connectView(View view) {
		EditText input1 = view.findViewById(R.id.lab4_input1);
		Button btn1 = view.findViewById(R.id.lab4_btn1);
		EditText output1 = view.findViewById(R.id.lab4_output1);

		btn1.setOnClickListener(v -> {
			try {
				String[] t = input1.getText().toString().split(",");
				List<Integer> h = Arrays.stream(t)
						.map(x -> Integer.parseInt(x.strip()))
						.toList();
				Double mean_ = mean(h);
				output1.setText(mean_.toString());
			}
			catch (Exception e1) {
				var toast = Toast.makeText(view.getContext(), e1.getMessage(), Toast.LENGTH_LONG);
				toast.show();
			}
		});
// ===========================================

		EditText input2 = view.findViewById(R.id.lab4_input2);
		Button btn2 = view.findViewById(R.id.lab4_btn2);
		EditText output2 = view.findViewById(R.id.lab4_output2);
		btn2.setOnClickListener(v -> {
			try {
				String[] t = input2.getText().toString().split(",");
				List<String> h = Arrays
						.asList(t)
						.stream()
						.map(x -> x.strip())
						.toList();
				List<String> newStrings = capper(h);
				output2.setText(newStrings.toString());
			}
			catch (Exception e1) {
				var toast = Toast.makeText(view.getContext(), e1.getMessage(), Toast.LENGTH_LONG);
				toast.show();
			}
		});

// ===========================================

		EditText input3 = view.findViewById(R.id.lab4_input3);
		Button btn3 = view.findViewById(R.id.lab4_btn3);
		EditText output3 = view.findViewById(R.id.lab4_output3);

		btn3.setOnClickListener(v -> {
			try {
				String[] t = input3.getText().toString().split(",");
				List<Double> h = Arrays.stream(t)
						.map(x -> Double.parseDouble(x.strip()))
						.toList();
				List<Double> squares = squarer(h);
				output3.setText(squares.toString());
			}
			catch (Exception e1) {
				var toast = Toast.makeText(view.getContext(), e1.getMessage(), Toast.LENGTH_LONG);
				toast.show();
			}
		});

// ===========================================

		EditText input4 = view.findViewById(R.id.lab4_input4);
		Button btn4 = view.findViewById(R.id.lab4_btn4);
		EditText output4 = view.findViewById(R.id.lab4_output4);

		btn4.setOnClickListener(v -> {
			try {
				String[] t = input4.getText().toString().split(",");
				Collection<Object> h;
				if (input4.getText().length() == 0) h = new ArrayList<>();
				else
					h = Arrays.stream(t)
// Для имитации того, что в коллекции есть разные типы элементов, а не просто строки
// Хотя это ни на что не влияет
						.map(x -> parse(x))
						.toList();
				Object last = last_or_exception(h);
				output4.setText(last.toString());
			}
			catch (Exception e1) {
				var toast = Toast.makeText(view.getContext(), e1.getMessage(), Toast.LENGTH_LONG);
				toast.show();
				output4.setText(e1.getMessage());
			}
		});

// ===========================================

		EditText input5 = view.findViewById(R.id.lab4_input5);
		Button btn5 = view.findViewById(R.id.lab4_btn5);
		EditText output5 = view.findViewById(R.id.lab4_output5);

		btn5.setOnClickListener(v -> {
				try {
					String[] t = input5.getText().toString().split(",");
					List<Integer> h = Arrays.stream(t)
							.map(x -> Integer.parseInt(x.strip()))
							.toList();
					Double sumOfEven = sum_of_even(h);
					output5.setText(sumOfEven.toString());
				}
				catch (Exception e1) {
					var toast = Toast.makeText(view.getContext(), e1.getMessage(), Toast.LENGTH_LONG);
					toast.show();
				}
		});

// ===========================================

		EditText input6 = view.findViewById(R.id.lab4_input6);
		Button btn6 = view.findViewById(R.id.lab4_btn6);
		EditText output6 = view.findViewById(R.id.lab4_output6);

		btn6.setOnClickListener( v -> {
			try {
				String[] t = input6.getText().toString().split(",");
				List<String> h = Arrays.stream(t)
						.map(String::strip)
						.toList();
				Map<Character, String> map = mapper(h);
				output6.setText(map.toString());
			}
			catch (Exception e1) {
				var toast = Toast.makeText(view.getContext(), e1.getMessage(), Toast.LENGTH_LONG);
				toast.show();
			}
		});
		
		return view;
	}
	public static Object parse(String input) {
		var temp = input.strip();
		try {
			return Integer.parseInt(temp);
		}
		catch(Exception ignored) {}
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
