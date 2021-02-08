package com.genedev.mathsolver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class BottomSheetEquation extends BottomSheetDialogFragment {

    private TextInputLayout tl_comment;
    private MaterialButton btn_save;

    private Bitmap bitmap;
    private String equation;

    public static final String TO_SOLUTIONS_LABEL = "Solutions";
    private static final String TO_SOLUTIONS = "Let's go to the solutions!!!!!!";
    public static final String INPUT_LABEL = "Input";

    public BottomSheetEquation(String ocr_equation) {
        equation = ocr_equation;
    }

    public BottomSheetEquation() {

    }

    public static BottomSheetEquation newInstance() {
        return new BottomSheetEquation();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.bs_recognized_text,container,false);

        tl_comment = view.findViewById(R.id.tl_comment);
        btn_save = view.findViewById(R.id.btn_save);
        Objects.requireNonNull(tl_comment.getEditText()).setText(equation);

        btn_save.setVisibility(View.GONE);

        if (equation.equals("Cloud Vision API request failed. Check logs for details.")){
            btn_save.setVisibility(View.GONE);
        } else if (!equation.equals("Cloud Vision API request failed. Check logs for details.")
            && !tl_comment.getEditText().getText().toString().equals("")) {
            btn_save.setVisibility(View.VISIBLE);
        }
        tl_comment.getEditText().setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    if (tl_comment.getEditText().getText().toString().equals("")
                            || tl_comment.getEditText().getText()
                            .toString().equals("Cloud Vision API request failed. Check logs for details.")){
                        btn_save.setVisibility(View.GONE);
                    } else {
                        btn_save.setVisibility(View.VISIBLE);
                    }
                    return true;
                }
                return false;
            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSolutionsActivity = new Intent(getActivity(), SolutionActivity.class);
                toSolutionsActivity.putExtra(TO_SOLUTIONS_LABEL, TO_SOLUTIONS);
                String finalResult = Objects.requireNonNull(tl_comment.getEditText()).getText().toString();
                toSolutionsActivity.putExtra(INPUT_LABEL, finalResult);
                startActivity(toSolutionsActivity);
            }
        });

        return view;
    }

}
