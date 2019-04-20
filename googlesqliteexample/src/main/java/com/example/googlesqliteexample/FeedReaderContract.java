package com.example.googlesqliteexample;

import android.provider.BaseColumns;

public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Contacts";
        public static final String NAME = "Name";
        public static final String PHONE = "Phone";
        public static final String FRIEND = "MyFriend";
    }


}


