package se.mau.ah1429.myeconomy;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ItemViewModel mItemViewModel;
    public static final int NEW_ITEM_ACTIVITY_REQUEST_CODE = 1;
    final Fragment fragmentIncome = new IncomeFragment();
    final Fragment fragmentResult = new ResultFragment();
    final Fragment fragmentExpense = new ExpenseFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragmentIncome;
    private String type = "income";
    private String category;
    private String date;
    private int value;
    private String name = "";
    private EditText mEditItemView;
    private EditText mEditValueView;
    private EditText mEditCategoryView;
    private EditText mEditDateView;
    private Spinner sCategory;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    type = "income";
                    fm.beginTransaction().hide(active).show(fragmentIncome).commit();
                    active = fragmentIncome;
                    mTextMessage.setText(R.string.title_home);

                    return true;
                case R.id.navigation_dashboard:
                   mTextMessage.setText(R.string.title_dashboard);
                    fm.beginTransaction().hide(active).show(fragmentResult).commit();
                    active = fragmentResult;

                    return true;
                case R.id.navigation_notifications:
                    type = "expense";
                    fm.beginTransaction().hide(active).show(fragmentExpense).commit();
                    active = fragmentExpense;
                    mTextMessage.setText(R.string.title_notifications);

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm.beginTransaction().add(R.id.main_container, fragmentExpense, "3").hide(fragmentExpense).commit();
        fm.beginTransaction().add(R.id.main_container, fragmentResult, "2").hide(fragmentResult).commit();
        fm.beginTransaction().add(R.id.main_container,fragmentIncome, "1").commit();

        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

        final SharedPreferences mySharedPrefs = this.getSharedPreferences("sharedPrefsName", MainActivity.MODE_PRIVATE);
        EditText mFromDate = findViewById(R.id.edit_filter_first);
        EditText mToDate = findViewById(R.id.edit_filter_second);

        //Filter
        Button mFilter = findViewById(R.id.add_button);
        mFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fDate = mFromDate.getText().toString();
                String tDate = mToDate.getText().toString();
                LiveData<List<Item>> allItems = mItemViewModel.getAllIncomeItems();

            }
        });




        //onclick för add button
        Button mAdd = findViewById(R.id.add_button);
        mAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Intent intent = new Intent(MainActivity.this, NewItemActivity.class);
                //startActivityForResult(intent, NEW_ITEM_ACTIVITY_REQUEST_CODE);
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext());
                final View mView = getLayoutInflater().inflate(R.layout.activity_new_item,null);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                mEditItemView = mView.findViewById(R.id.edit_item);
                mEditValueView = mView.findViewById(R.id.edit_value);

                if(type == "income"){
                    sCategory = (Spinner) mView.findViewById(R.id.edit_category);
                    sCategory.setOnItemSelectedListener(new CustomOnItemSelectedListener());
                } else {
                    sCategory = (Spinner) mView.findViewById(R.id.edit_category);
                    sCategory.setOnItemSelectedListener(new CustomOnItemSelectedListener());
                    ArrayList<String> arrayList1 = new ArrayList<String>();

                    arrayList1.add("Other");
                    arrayList1.add("Spare time");
                    arrayList1.add("Travel");
                    arrayList1.add("Living");
                    arrayList1.add("Food");
                    ArrayAdapter<String> adp = new ArrayAdapter<String> (MainActivity.this,android.R.layout.simple_spinner_dropdown_item,arrayList1);
                    sCategory.setAdapter(adp);
                    sCategory.setVisibility(View.VISIBLE);

                }

                DatePicker simpleDatePicker = (DatePicker)mView.findViewById(R.id.simpleDatePicker); // initiate a date picker
                simpleDatePicker.setSpinnersShown(false); // set false value for the spinner shown function

                final Button button = mView.findViewById(R.id.button_save);

                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (TextUtils.isEmpty(mEditItemView.getText()) && TextUtils.isEmpty(mEditValueView.getText()) ) {

                        } else {
                            int day = simpleDatePicker.getDayOfMonth();
                            int month = simpleDatePicker.getMonth() + 1;
                            int year = simpleDatePicker.getYear();
                            int myNum = 0;
                            try {
                                myNum = Integer.parseInt(mEditValueView.getText().toString());
                            } catch(NumberFormatException nfe) {
                                System.out.println("Could not parse " + nfe);
                            }
                            value = myNum;
                            String date = Integer.toString(day) + "/" + Integer.toString(month) + "/"+ Integer.toString(year);
                            String item = mEditItemView.getText().toString() + ":" + mEditValueView.getText().toString() + ":" +  String.valueOf(sCategory.getSelectedItem()) + ":" +  date;
                            Item newItem = new Item(mEditItemView.getText().toString(),type, String.valueOf(sCategory.getSelectedItem()), date , myNum);
                            mItemViewModel.insert(newItem);
                            dialog.dismiss();
                        }
                    }
                });


            }
        });


        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Button mLogin = (Button) findViewById(R.id.btnShowLogin);
       // mLogin.setOnClickListener(new View.OnClickListener() {
          //  @Override
           // public void onClick(View v) {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_login,null);

        final EditText mFirstName = (EditText) mView.findViewById(R.id.etFirstName);
        final EditText mLastName = (EditText) mView.findViewById(R.id.etLastName);
        Button mSave = (Button) mView.findViewById(R.id.btnSave);
        Button mCancel = (Button) mView.findViewById(R.id.btnCancel);

        Log.i("test", "testmsg");
        final SharedPreferences my_SharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        if(my_SharedPrefs.getString("name", "").equals("")) {
            //ingen popup utan denna line
            dialog.show();
        }
        setTitle("MyEconomy " + my_SharedPrefs.getString("name",""));

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mFirstName.getText().toString().isEmpty() && !mLastName.getText().toString().isEmpty()){
                    String value = mFirstName.getText().toString();
                    value += " "+ mLastName.getText().toString();
                    name = value;
                    //SharedPreferences sp = getSharedPreference("MyKey",0);
                    SharedPreferences.Editor preferencesEditor = my_SharedPrefs.edit();
                    preferencesEditor.putString("name", value);
                    preferencesEditor.commit();
                    Toast.makeText(MainActivity.this,
                            getString(R.string.welcome_msg_login) + " "+ mySharedPrefs.getString("name",""),
                            Toast.LENGTH_SHORT).show();

                    dialog.dismiss();
                }else{
                    Toast.makeText(MainActivity.this,
                            R.string.failed_msg_login,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Toast.makeText(MainActivity.this,
                            R.string.btn_cancel_msg,
                            Toast.LENGTH_SHORT).show();
            }
        });

           // }
       //});

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_ITEM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String newItem = data.getStringExtra(NewItemActivity.EXTRA_REPLY);
           Log.i("msg", newItem);

           StringTokenizer tokens = new StringTokenizer(newItem, ":");
           /* name = tokens.nextToken();//
            Log.i("namn", name);
            value = tokens.nextToken();
            Log.i("value", value);
            category = tokens.nextToken();
            Log.i("cate", category);
            date = tokens.nextToken();
            Log.i("date", date);*/
            ;
           //String[] separated = newItem.split(":");

            Item item = new Item(name,type,category,date,value);
            mItemViewModel.insert(item);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    public String getType() {
        return type;
    }
}
