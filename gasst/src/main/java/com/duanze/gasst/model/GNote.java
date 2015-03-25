package com.duanze.gasst.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.duanze.gasst.util.Util;
import com.duanze.gasst.view.GridUnit;

import java.util.Calendar;

public class GNote implements Parcelable {
    public static final int TRUE = 1;
    public static final int FALSE = 0;

    public static final int NOTHING = 0;
    public static final int NEW = 1;
    public static final int UPDATE = 2;
    public static final int DELETE = 3;

    private int id = -1;
    private String time;
    private String alertTime = "";
    private int passed = TRUE;
    private String note = "";
    private int done = FALSE;
    private int color = GridUnit.colorArr[0];//初始透明
    private long editTime;//最后编辑时间
    private long createdTime;//创建时间
    private int synStatus = NOTHING;//同步状态，仅登录EverNote后有效
    private String guid;//evernote 标志符，惟一确定一条note

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public boolean isPassed() {
        return passed == TRUE;
    }

    public boolean isDone() {
        return done == TRUE;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public long getEditTime() {
        return editTime;
    }

    public void setEditTime(long editTime) {
        this.editTime = editTime;
    }

    public int getSynStatus() {
        return synStatus;
    }

    public void setSynStatus(int synStatus) {
        this.synStatus = synStatus;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getAlertTime() {
        return alertTime;
    }

    public int getPassed() {
        return passed;
    }

    public String getNote() {
        return note;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPassed(int passed) {
        this.passed = passed;
    }

    public void setAlertTime(String alertTime) {
        this.alertTime = alertTime;
    }

    public void setNote(String note) {
        this.note = note;
    }

    /**
     * 与一个Calendar进行日期（年月日）比较，小于返回-1，等于返回0，大于返回1
     *
     * @param calendar
     * @return 比较的结果
     */
    public int compareToCalendar(Calendar calendar) {
        String[] allInfo;
        allInfo = time.split(",");
        int mYear = Integer.parseInt(allInfo[0]);
        int mMonth = Integer.parseInt(allInfo[1]);
        int mDay = Integer.parseInt(allInfo[2]);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (mYear < year ||
                mYear == year && mMonth < month ||
                mYear == year && mMonth == month && mDay < day) {
            return -1;
        } else if (mYear == year && mMonth == month && mDay == day) {
            return 0;
        }
        return 1;
    }

    /**
     * 以calendar为日期给note的time赋值
     */
    public void setCalToTime(Calendar calendar) {
        setTime(calendar.get(Calendar.YEAR)
                + ","
                + Util.twoDigit(calendar.get(Calendar.MONTH))
                + ","
                + Util.twoDigit(calendar.get(Calendar.DAY_OF_MONTH)));

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(time);
        parcel.writeString(alertTime);
        parcel.writeInt(passed);
        parcel.writeString(note);
        parcel.writeInt(done);
        parcel.writeInt(color);
        parcel.writeLong(editTime);
        parcel.writeLong(createdTime);
        parcel.writeInt(synStatus);
        parcel.writeString(guid);
    }

    public static final Creator<GNote> CREATOR = new Creator<GNote>() {
        @Override
        public GNote createFromParcel(Parcel parcel) {
            GNote gNote = new GNote();
            gNote.id = parcel.readInt();
            gNote.time = parcel.readString();
            gNote.alertTime = parcel.readString();
            gNote.passed = parcel.readInt();
            gNote.note = parcel.readString();
            gNote.done = parcel.readInt();
            gNote.color = parcel.readInt();
            gNote.editTime = parcel.readLong();
            gNote.createdTime = parcel.readLong();
            gNote.synStatus = parcel.readInt();
            gNote.guid = parcel.readString();
            return gNote;
        }

        @Override
        public GNote[] newArray(int i) {
            return new GNote[i];
        }
    };
}