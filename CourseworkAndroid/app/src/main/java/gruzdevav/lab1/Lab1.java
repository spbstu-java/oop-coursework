package gruzdevav.lab1;


import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import gruzdevav.coursework.R;

public class Lab1 {
	public static String[] methods = {"Пешком", "На лошади", "Самолётом"};
	public static Hero hero = new Hero();
	public static View connectView(View view) {

		EditText etStatus = view.findViewById(R.id.lab1_status);
		Spinner combobox = view.findViewById(R.id.lab1_spinner);
		EditText etX = view.findViewById(R.id.lab1_x);
		EditText etY = view.findViewById(R.id.lab1_y);
		Button btn = view.findViewById(R.id.lab1_btn_compute);


        combobox.setAdapter(new ArrayAdapter<>(
			view.getContext(),
			android.R.layout.simple_spinner_item,
			methods
		));
        etStatus.setText(String.format("Герой в точке (%.2f; %.2f).", hero.location.x, hero.location.y));

		btn.setOnClickListener(v -> {
			try {
				String method = combobox.getSelectedItem().toString();
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
				var x = Double.parseDouble(etX.getText().toString());
				var y = Double.parseDouble(etY.getText().toString());
				etStatus.setText(hero.move(new Point(x, y)));
			}
			catch (Exception NumberFormatException) {
				Toast toast = Toast.makeText(
					view.getContext(),
					"Координаты перемещения (X, Y) должны быть корректными десятичными дробями.",
					Toast.LENGTH_LONG
				);
				toast.show();
			}
        });
        
        return view;
	}
}
