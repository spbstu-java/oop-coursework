package gruzdevav.lab3;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import gruzdevav.coursework.MainActivity;
import gruzdevav.coursework.R;

public class Lab3 {

	public static Dictionary dictionary = new Dictionary();

	public static View connectView(View view, MainActivity activity) {

		EditText input = view.findViewById(R.id.lab3_input);
		EditText output = view.findViewById(R.id.lab3_output);
		EditText dict = view.findViewById(R.id.lab3_dict);
		Button btnExecute = view.findViewById(R.id.lab3_btn_execute);
		Button btnChooseDict = view.findViewById(R.id.lab3_btn_choose_dict);
		Button btnChooseInput = view.findViewById(R.id.lab3_btn_choose_input);

		btnChooseDict.setOnClickListener(v -> {
			activity.pickFile(uri -> {
				if (uri!=null) {
					try {
						dictionary = new Dictionary(uri);
						dict.setText(dictionary.toString());
					} catch (FileReadException e1) {
						var toast = Toast.makeText(view.getContext(), "Ошибка чтения файла словаря.", Toast.LENGTH_LONG);
						toast.show();
					} catch (InvalidFileFormatException e1) {
						var toast = Toast.makeText(view.getContext(), "Файл словаря имеет некорректный формат.", Toast.LENGTH_LONG);
						toast.show();
					}
				}
			});

		});
		btnChooseInput.setOnClickListener(v -> {
			activity.pickFile(uri -> {
				if (uri != null) {
					try (var reader = MainActivity.instance.getBaseContext().getContentResolver().openInputStream(uri)){
						var readText = reader.readAllBytes();
						input.setText(new String(readText));
					} catch (IOException ex) {
						var toast = Toast.makeText(view.getContext(), "Ошибка чтения файла, содержащего текст для ввода.", Toast.LENGTH_LONG);
						toast.show();
					}
				}
			});
		});
		btnExecute.setOnClickListener(v -> {
			var outputText = dictionary.translate(input.getText().toString());
			output.setText(outputText);
		});
		
		return view;
	}
}
