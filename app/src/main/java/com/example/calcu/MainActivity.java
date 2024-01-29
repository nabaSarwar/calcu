package com.example.calcu;


import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView res, sol;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        res = findViewById(R.id.Result);
        sol = findViewById(R.id.Solution);

        initButtons();
    }

    private void initButtons() {
        int[] btnId = {
                R.id.button_c, R.id.button_open_bracket, R.id.button_close_bracket,
                R.id.button_divide, R.id.button_multiply, R.id.button_plus, R.id.button_minus,
                R.id.button_equals, R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3,
                R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9,
                R.id.button_ac, R.id.button_dot
        };

        MaterialButton[] btns = new MaterialButton[btnId.length];

        for (int i = 0; i < btnId.length; i++) {
            btns[i] = findViewById(btnId[i]);
            btns[i].setOnClickListener(this);
        }
    }

    private String Result(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String doneResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if (doneResult.endsWith(".0")) {
                doneResult = doneResult.replace(".0", "");
            }
            return doneResult;
        } catch (Exception e) {
            return "Err";
        }
    }

    @Override
    public void onClick(View view) {
        MaterialButton btn = (MaterialButton) view;
        String btnText = btn.getText().toString();
        String dataCal = sol.getText().toString();

        switch (btnText) {
            case "AC":
                sol.setText("");
                res.setText("0");
                break;
            case "=":
                sol.setText(res.getText());
                break;
            case "C":
                dataCal = dataCal.substring(0, dataCal.length() - 1);
                break;
            default:
                dataCal = dataCal + btnText;
                break;
        }

        sol.setText(dataCal);
        String finResult = Result(dataCal);

        if (!finResult.equals("Err")) {
            res.setText(finResult);
        }
    }
}
