package com.example.notekeeper;


public final class CourseInfo {
    private final String mCourseId;
    private final String mTitle;

    public CourseInfo(String courseId, String title) {
        mCourseId = courseId;
        mTitle = title;
    }

    public String getCourseId() {
        return mCourseId;
    }

    public String getTitle() {
        return mTitle;
    }

}