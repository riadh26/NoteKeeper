package com.example.notekeeper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.notekeeper.NoteKeeperDatabaseContract.CourseInfoEntry;
import com.example.notekeeper.NoteKeeperDatabaseContract.NoteInfoEntry;

import java.util.ArrayList;
import java.util.List;


public class DataManager {
    private static DataManager ourInstance = null;

    private List<CourseInfo> mCourses = new ArrayList<>();
    private List<NoteInfo> mNotes = new ArrayList<>();

    public static DataManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new DataManager();
        }
        return ourInstance;
    }

    public static void loadFromDatabase(NoteKeeperOpenHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        final String[] courseColumns = {
                CourseInfoEntry.COLUMN_COURSE_ID,
                CourseInfoEntry.COLUMN_COURSE_TITLE};

        final Cursor courseCursor = db.query(CourseInfoEntry.TABLE_NAME, courseColumns,
                null,
                null,
                null,
                null,
                CourseInfoEntry.COLUMN_COURSE_TITLE);
        loadCoursesFromDatabase(courseCursor);

        final String[] noteColumns = {
                NoteInfoEntry.COLUMN_NOTE_TITLE,
                NoteInfoEntry.COLUMN_NOTE_TEXT,
                NoteInfoEntry.COLUMN_COURSE_ID,
                NoteInfoEntry._ID};

        String noteOrderBy = NoteInfoEntry.COLUMN_COURSE_ID + "," + NoteInfoEntry.COLUMN_NOTE_TITLE;
        final Cursor noteCursor = db.query(NoteInfoEntry.TABLE_NAME, noteColumns,
                null,
                null,
                null,
                null,
                noteOrderBy);

        loadNotesFromDatabase(noteCursor);

    }

    private static void loadNotesFromDatabase(Cursor cursor) {
        int noteTitlePos = cursor.getColumnIndex(NoteInfoEntry.COLUMN_NOTE_TITLE);
        int noteTextPos = cursor.getColumnIndex(NoteInfoEntry.COLUMN_NOTE_TEXT);
        int courseIdPos = cursor.getColumnIndex(NoteInfoEntry.COLUMN_COURSE_ID);
        int idPos = cursor.getColumnIndex(NoteInfoEntry._ID);

        DataManager dm = getInstance();
        dm.mNotes.clear();

        while (cursor.moveToNext()) {
            String noteTitle = cursor.getString(noteTitlePos);
            String noteText = cursor.getString(noteTextPos);
            String courseId = cursor.getString(courseIdPos);
            int id = cursor.getInt(idPos);

            CourseInfo noteCourse = dm.getCourse(courseId);
            NoteInfo note = new NoteInfo(id, noteCourse, noteTitle, noteText);
            dm.mNotes.add(note);
        }

        cursor.close();
    }

    private static void loadCoursesFromDatabase(Cursor cursor) {
        int courseIdPos = cursor.getColumnIndex(CourseInfoEntry.COLUMN_COURSE_ID);
        int courseTitlePos = cursor.getColumnIndex(CourseInfoEntry.COLUMN_COURSE_TITLE);

        DataManager dm = getInstance();
        dm.getCourses().clear();

        while (cursor.moveToNext()) {
            String courseId = cursor.getString(courseIdPos);
            String courseTitle = cursor.getString(courseTitlePos);
            CourseInfo course = new CourseInfo(courseId, courseTitle);
            dm.mCourses.add(course);
        }
        cursor.close();
    }


    public List<NoteInfo> getNotes() {
        return mNotes;
    }

    public int createNewNote() {
        NoteInfo note = new NoteInfo(null, null, null);
        mNotes.add(note);
        return mNotes.size() - 1;
    }


    public List<CourseInfo> getCourses() {
        return mCourses;
    }

    public CourseInfo getCourse(String id) {
        for (CourseInfo course : mCourses) {
            if (id.equals(course.getCourseId()))
                return course;
        }
        return null;
    }


    private DataManager() {
    }

}
