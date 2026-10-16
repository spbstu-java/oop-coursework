package gruzdevav.coursework;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;

import java.util.Map;

import gruzdevav.lab1.Lab1;
import gruzdevav.lab2.Lab2;
import gruzdevav.lab3.Lab3;
import gruzdevav.lab4.Lab4;

public class MainActivity extends AppCompatActivity {
    public static MainActivity instance;
    public static final int PICK_FILE_REQUEST = 1;
    private ScrollView container;
    private static final Map<String, Integer> ids = Map.of(
            "lab1", R.layout.lab_1_layout,
            "lab2", R.layout.lab_2_layout,
            "lab3", R.layout.lab_3_layout,
            "lab4", R.layout.lab_4_layout
    );
    private Consumer<Uri> callback;
    public void pickFile(Consumer<Uri> onPickFile) {
        this.callback = onPickFile;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, PICK_FILE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null) {
            if (callback != null) {
                callback.accept(data.getData());
                callback = null;
            }
        }
    }
    private final View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            container.removeAllViewsInLayout();
            String tag = view.getTag().toString();
            var id = ids.get(tag);
            if (id == null) {
                throw new RuntimeException("Wrong tag: "+ tag);
            }
            View lab = getLayoutInflater().inflate(id, container, false);
            switch (tag) {
                case "lab1":
                    Lab1.connectView(lab);
                    break;
                case "lab2":
                    Lab2.connectView(lab);
                    break;
                case "lab3":
                    Lab3.connectView(lab, MainActivity.this);
                    break;
                case "lab4":
                    Lab4.connectView(lab);
                    break;
            }
            container.addView(lab);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        instance=this;
        Button btn1 = findViewById(R.id.btn_lab_1);
        Button btn2 = findViewById(R.id.btn_lab_2);
        Button btn3 = findViewById(R.id.btn_lab_3);
        Button btn4 = findViewById(R.id.btn_lab_4);
        container = findViewById(R.id.container);
        btn1.setOnClickListener(onClickListener);
        btn2.setOnClickListener(onClickListener);
        btn3.setOnClickListener(onClickListener);
        btn4.setOnClickListener(onClickListener);
    }
}
