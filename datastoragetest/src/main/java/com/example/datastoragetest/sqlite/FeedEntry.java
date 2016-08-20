package com.example.datastoragetest.sqlite;

import android.provider.BaseColumns;

/**
 * Created by Administrator on 2016/8/20.
 */
public abstract class FeedEntry implements BaseColumns {
    public static final String TABLE_NAME = "entry";
    public static final String COLUMN_NAME_ENTRY_ID = "entryid";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_CONTENT = "content";
    public static final String COLUMN_NAME_NULLABLE = COLUMN_NAME_TITLE;

}