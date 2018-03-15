package com.example.user.myprogress;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myprogress.data.ExerciseContract;
import com.example.user.myprogress.data.ExerciseDBHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class SportDiary extends AppCompatActivity {
    private ExerciseDBHelper mDBHelper;
    @BindView(R.id.listview) ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayExercise;
    FragmentTransaction fragmentTransaction;
    FragmentAddExercise fragmentAddExercise;
    FragmentCalendar fragmentCalendar;
    String Tag = "ProgresDiary";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_diary);
        ButterKnife.bind(this);
        infromLogger("before init");
        init();
        infromLogger("Succes init");
    }
    public void init(){
        //listView = (ListView)findViewById(R.id.listview);
        mDBHelper = new ExerciseDBHelper(this);
        fragmentAddExercise = new FragmentAddExercise();
        fragmentCalendar = new FragmentCalendar();
        arrayExercise = new ArrayList<>();
        arrayExercise.addAll(Arrays.asList(getResources().getStringArray(R.array.array_exercises)));
        infromLogger("Succes addAll");
        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                arrayExercise);
        listView.setAdapter(adapter);
    }
    private void displayDatabaseInfo(){
        Log.i("dataBD","1");
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Log.i("dataBD","after");
        String [] projection  ={
        ExerciseContract.ExerciseEntry._ID,
                ExerciseContract.ExerciseEntry.COLUMN_NAME,
                ExerciseContract.ExerciseEntry.COLUMN_WEIGHT,
                ExerciseContract.ExerciseEntry.COLUMN_TYPE,
                ExerciseContract.ExerciseEntry.COLUMN_SET,
                ExerciseContract.ExerciseEntry.COLUMN_REP,
                ExerciseContract.ExerciseEntry.COLUMN_DATE
        };

        Cursor cursor = db.query(
                ExerciseContract.ExerciseEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        try{
            Log.i("dataBD","toasta");
        }
        finally {
            cursor.close();
            Log.i("dataBD","toastFinish");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        MenuItem item = menu.findItem(R.id.item_search);
        MenuItem itemCalendar = menu.findItem(R.id.item_calendar);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =item.getItemId();
        switch (id){
            case R.id.item_calendar:
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.layoutForAddExercise,fragmentCalendar);
                fragmentTransaction.commit();
                //Toast.makeText(getApplicationContext(), "123312", Toast.LENGTH_SHORT).show();
                /*FragmentShowExercise fragmentShowExercise = new FragmentShowExercise();
                Log.i("dataBD","fafafafa");
                fragmentTransaction = getFragmentManager().beginTransaction();
                Log.i("dataBD","afafafaf");
                fragmentTransaction.replace(R.id.layoutForAddExercise,fragmentShowExercise);
                Log.i("dataBD","afafafaf");
                fragmentTransaction.commit();
                Log.i("dataBD","124124124");*/
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnItemClick(R.id.listview)
    public void onItemSelected(int position){
        Bundle bundle = new Bundle();
        String str = listView.getItemAtPosition(position).toString();
        bundle.putString("titleExercise",str);
        fragmentAddExercise.setArguments(bundle);
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.layoutForAddExercise,fragmentAddExercise);
        fragmentTransaction.commit();

        listView.setAdapter(null);
        //displayDatabaseInfo();
        //listView.i
        infromLogger("Click on the element");
    }
    public void onClickExercise(View view){
        switch (view.getId()){
            case R.id.buttonAddSet : ((Button)fragmentAddExercise.getView().
                    findViewById(R.id.buttonAddSet)).setText("sdfsdfsf");
            break;
        }
    }
    public  void infromLogger(String statement){
        if(BuildConfig.DEBUG){
            Log.i(Tag,statement);
        }
    }
}
