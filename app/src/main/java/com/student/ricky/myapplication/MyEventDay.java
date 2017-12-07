package com.student.ricky.myapplication;
import android.os.Parcel;
import android.os.Parcelable;

import com.applandeo.materialcalendarview.EventDay;

import java.util.Calendar;
/**
 * Created by Ricky on 12/5/2017.
 * Custom class to make an event
 */
class MyEventDay extends EventDay implements Parcelable {
    private String mTitle;
    private String mTag;
    private String mNote;
    MyEventDay(Calendar day, int imageResource, String title, String tag, String note) {
        super(day, imageResource);
        mTitle = title;
        mTag = tag;
        mNote = note;
    }
    String getTitle() {
        return mTitle;
    }
    String getTag() {
        return mTag;
    }
    String getNote() {
        return mNote;
    }
    private MyEventDay(Parcel in) {
        super((Calendar) in.readSerializable(), in.readInt());
        mTitle = in.readString();
        mTag = in.readString();
        mNote = in.readString();
    }
    public static final Creator<MyEventDay> CREATOR = new Creator<MyEventDay>() {
        @Override
        public MyEventDay createFromParcel(Parcel in) {
            return new MyEventDay(in);
        }

        @Override
        public MyEventDay[] newArray(int size) {
            return new MyEventDay[size];
        }
    };
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(getCalendar());
        parcel.writeInt(getImageResource());
        parcel.writeString(mTitle);
        parcel.writeString(mTag);
        parcel.writeString(mNote);
    }
    @Override
    public int describeContents() {
        return 0;
    }
}
