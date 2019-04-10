package se.mau.ah1429.myeconomy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class NewItemActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText mEditItemView;
    private EditText mEditValueView;
    private EditText mEditCategoryView;
    private EditText mEditDateView;
    private  Spinner sCategory;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        mEditItemView = findViewById(R.id.edit_item);
        mEditValueView = findViewById(R.id.edit_value);
       // mEditCategoryView = findViewById(R.id.edit_category);
        sCategory = findViewById(R.id.edit_category);
        //mEditDateView = findViewById(R.id.edit_date);
        Intent intent = getIntent();

        String activeFrag = intent.getStringExtra("type");
        Log.i("frag",activeFrag);

        sCategory.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        DatePicker simpleDatePicker = (DatePicker)findViewById(R.id.simpleDatePicker); // initiate a date picker
        simpleDatePicker.setSpinnersShown(false); // set false value for the spinner shown function



        final Button button = findViewById(R.id.button_save);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditItemView.getText()) && TextUtils.isEmpty(mEditValueView.getText()) ) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    int day = simpleDatePicker.getDayOfMonth();
                    int month = simpleDatePicker.getMonth() + 1;
                    int year = simpleDatePicker.getYear();

                    String date = Integer.toString(year) + Integer.toString(month) +Integer.toString(day) ;
                    String item = mEditItemView.getText().toString() + ":" + mEditValueView.getText().toString() + ":" +  String.valueOf(sCategory.getSelectedItem()) + ":" +  date;

                    //Item newItem = new Item(mEditItemView.getText().toString(),"income", String.valueOf(sCategory.getSelectedItem()), date , mEditValueView.getText().toString());

                    replyIntent.putExtra(EXTRA_REPLY, item);
                    /*
                    replyIntent.putExtra(EXTRA_REPLY, newItem.getItem());
                    replyIntent.putExtra(EXTRA_REPLY, newItem.getType());
                    replyIntent.putExtra(EXTRA_REPLY, newItem.getValue());
                    replyIntent.putExtra(EXTRA_REPLY,newItem.getCategory());
                    replyIntent.putExtra(EXTRA_REPLY,newItem.getDate());*/

                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}