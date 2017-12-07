package com.student.ricky.myapplication;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Ricky on 12/5/2017.
 */
//this is the main app class
public class MainActivity extends AppCompatActivity {
    public static final String RESULT = "result";
    public static final String EVENT = "event";
    private static final String EVENT_PREF = "Event_Pref";
    private static final String MYEVENT_PREF = "MyEvent_Pref";
    private static final int ADD_NOTE = 44;
    private CalendarView mCalendarView;
    private List<EventDay> mEventDays = new ArrayList<>();
    private List<MyEventDay> myEventDays = new ArrayList<>();
    private int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });
        //if data has been loaded this session, don't do it again
        if(counter < 1)
            getDataFromSharedPreferences() ;
        counter++;
        mCalendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                previewNote(eventDay);
            }
        });
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        setDataFromSharedPreferences();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        //getDataFromSharedPreferences();
    }
    //save the data to shared preferences
    private void setDataFromSharedPreferences(){
        Gson gson = new Gson();
        String jsonEvents = gson.toJson(mEventDays);
        String jsonMyEvents = gson.toJson(myEventDays);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(EVENT_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(EVENT_PREF, jsonEvents);
        editor.putString(MYEVENT_PREF, jsonMyEvents);
        editor.commit();
    }
    //retrieves data from shared preferences
    private void getDataFromSharedPreferences(){
        Gson gson = new GsonBuilder().create();
        List<EventDay> eventFromShared = new ArrayList<>();
        List<MyEventDay> myEventFromShared = new ArrayList<>();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(EVENT_PREF, Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(EVENT_PREF, "");
        Type collectionType = new TypeToken<List<MyEventDay>>(){}.getType();
        eventFromShared = gson.fromJson(jsonPreferences, collectionType);
        //sharedPref = getApplicationContext().getSharedPreferences(MYEVENT_PREF, Context.MODE_PRIVATE);
        jsonPreferences = sharedPref.getString(MYEVENT_PREF, "");
        collectionType = new TypeToken<List<MyEventDay>>(){}.getType();
        myEventFromShared = gson.fromJson(jsonPreferences, collectionType);
        //SharedPreferences.Editor editor = sharedPref.edit();
        //editor.clear();
        if (eventFromShared != null && myEventFromShared != null)
        {
            mEventDays = eventFromShared;
            myEventDays = myEventFromShared;
            //mCalendarView.setEvents(mEventDays);
            for (MyEventDay m : myEventDays) {
                mCalendarView.setDate(m.getCalendar());
                mEventDays.add(m);
                mCalendarView.setEvents(mEventDays);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_NOTE && resultCode == RESULT_OK) {
            MyEventDay myEventDay = data.getParcelableExtra(RESULT);
            mCalendarView.setDate(myEventDay.getCalendar());
            mEventDays.add(myEventDay);
            myEventDays.add(myEventDay);
            mCalendarView.setEvents(mEventDays);
        }
    }
    //creates event
    private void addNote() {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivityForResult(intent, ADD_NOTE);
    }
    //previews event
    private void previewNote(EventDay eventDay) {
        Intent intent = new Intent(this, NotePreviewActivity.class);
        if(eventDay instanceof MyEventDay){
            intent.putExtra(EVENT, (MyEventDay) eventDay);
        }
        startActivity(intent);
    }
}
