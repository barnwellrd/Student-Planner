package com.student.ricky.myapplication;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.applandeo.materialcalendarview.CalendarView;
/**
 * Created by Ricky on 12/5/2017.
 */
//this class handles the Event Creation
public class AddNoteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note_activity);
        final CalendarView datePicker = (CalendarView) findViewById(R.id.datePicker);
        Button button = (Button) findViewById(R.id.addNoteButton);
        final EditText noteEditText = (EditText) findViewById(R.id.noteEditText);
        final EditText editText = (EditText) findViewById(R.id.editText);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tag_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        Button add = (Button) findViewById(R.id.addTagButton);
       /* add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myData = spinner.getSelectedItem().toString();
                int position = adapter.getPosition(myData);

                adapter.add(myData);
                adapter.notifyDataSetChanged();
                spinner.setSelection(adapter.getPosition(myData));
            }
        });
        Button remove = (Button) findViewById(R.id.removeTagButton);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myData = spinner.getSelectedItem().toString();
                int position = adapter.getPosition(myData);
            }
        });*/
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                MyEventDay myEventDay = new MyEventDay(datePicker.getSelectedDate(),
                        R.drawable.ic_message_black_48dp, editText.getText().toString(), spinner.getSelectedItem().toString(), noteEditText.getText().toString());
                returnIntent.putExtra(MainActivity.RESULT, myEventDay);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}