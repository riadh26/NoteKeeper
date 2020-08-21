package com.example.notekeeper;


public final class NoteInfo {
    private CourseInfo mCourse;
    private String mTitle;
    private String mText;
    private int mId;

    public NoteInfo(int id, CourseInfo course, String title, String text) {
        mId = id;
        mCourse = course;
        mTitle = title;
        mText = text;
    }

    public NoteInfo(CourseInfo course, String title, String text) {
        mCourse = course;
        mTitle = title;
        mText = text;
    }


    public int getId() {
        return mId;
    }

    public CourseInfo getCourse() {
        return mCourse;
    }

    public void setCourse(CourseInfo course) {
        mCourse = course;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

}