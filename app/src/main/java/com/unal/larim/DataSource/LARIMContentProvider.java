package com.unal.larim.DataSource;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import com.unal.larim.LN.LinnaeusDatabase;
import com.unal.larim.LN.Util;

import java.util.List;

public class LARIMContentProvider extends ContentProvider {
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    public static final String CONTENT_AUTHORITY = "com.unal.larim";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final int participants = 100;
    public static final int participant_by_id = 101;
    public static final int participant_by_type = 102;
    public static final int participant_by_name = 103;
    public static final int participant_by_column = 104;

    public static final int sponsors = 200;
    public static final int sponsors_by_type = 201;
    public static final int sponsors_as_map_points = 202;

    public static final int papers = 300;
    public static final int paper_by_id = 301;

    public static final int notices = 400;

    public static final int conferences = 500;
    public static final int conferences_by_date = 501;
    public static final int conferences_by_hours_and_date = 502;

    public static final int country_by_code = 600;

    private LinnaeusDatabase ln;

    public LARIMContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = ln.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        switch (match) {
            case participants:
                rowsDeleted = db.delete(ParticipantContent.table_name_participant, selection,
                        selectionArgs);
                break;
            case notices:
                rowsDeleted = db.delete(NoticeContent.table_name, selection,
                        selectionArgs);
                break;
            case papers:
                rowsDeleted = db.delete(PaperContent.table_name, selection,
                        selectionArgs);
                break;
            case sponsors:
                rowsDeleted = db.delete(SponsorContent.table_name, selection,
                        selectionArgs);
                break;
            case conferences:
                rowsDeleted = db.delete(ConferenceContent.table_name_conference, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            // Student: Uncomment and fill out these two cases
            case participants:
                return ParticipantContent.CONTENT_TYPE;
            case participant_by_id:
                return ParticipantContent.CONTENT_ITEM_TYPE;
            case participant_by_type:
                return ParticipantContent.CONTENT_TYPE;
            case sponsors:
                return SponsorContent.CONTENT_TYPE;
            case sponsors_by_type:
                return SponsorContent.CONTENT_TYPE;
            case papers:
                return PaperContent.CONTENT_TYPE;
            case paper_by_id:
                return PaperContent.CONTENT_ITEM_TYPE;
            case notices:
                return NoticeContent.CONTENT_TYPE;
            case conferences:
                return ConferenceContent.CONTENT_TYPE;
            case conferences_by_date:
                return ConferenceContent.CONTENT_TYPE;
            case conferences_by_hours_and_date:
                return ConferenceContent.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = ln.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case participants: {
                long _id = db.insert(ParticipantContent.table_name_participant, null, values);
                if (_id > 0)
                    returnUri = ParticipantContent.buildParticipantUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case notices: {
                long _id = db.insert(NoticeContent.table_name, null, values);
                if (_id > 0)
                    returnUri = NoticeContent.buildNoticeUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case papers: {
                long _id = db.insert(PaperContent.table_name, null, values);
                if (_id > 0)
                    returnUri = PaperContent.buildPaperUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case sponsors: {
                long _id = db.insert(SponsorContent.table_name, null, values);
                if (_id > 0)
                    returnUri = SponsorContent.buildSponsorUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case conferences: {
                long _id = db.insert(ConferenceContent.table_name_conference, null, values);
                if (_id > 0)
                    returnUri = ConferenceContent.buildConferenceUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public boolean onCreate() {
        ln = new LinnaeusDatabase(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = ln.getWritableDatabase();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case participants:
                cursor = db.query(getParticipantTableName(),
                        ParticipantContent.column_names,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            case participant_by_id:
                cursor = db.query(getParticipantTableName(),
                        ParticipantContent.column_names,
                        "a." + ParticipantContent._ID + "=?", new String[]{uri.getLastPathSegment()},
                        null, null, sortOrder);
                break;
            case participant_by_type:
                String cad = uri.getLastPathSegment();
                if (cad.equals(ParticipantContent.TYPE_NORMAL)) {
                    cursor = db.query(getParticipantTableName(),
                            ParticipantContent.column_names,
                            ParticipantContent.column_type + " IS NULL ",
                            null, null, null, sortOrder);
                    break;
                } else {
                    if (cad.equals("all")) {
                        cursor = db.query(getParticipantTableName(),
                                new String[]{"DISTINCT " + ParticipantContent.column_type},
                                /*be careful with the number*/
                                "length( " + ParticipantContent.column_type + " )<3 or "
                                        + ParticipantContent.column_type + " IS NULL ",
                                null,
                                null, null, sortOrder);
                        break;
                    } else {
                        cursor = db.query(getParticipantTableName(),
                                ParticipantContent.column_names,
                                ParticipantContent.column_type + " like ?",
                                new String[]{"%" + uri.getLastPathSegment() + "%"}, null, null, sortOrder);
                        break;
                    }
                }
            case participant_by_name:
                cursor = db.query(getParticipantTableName(),
                        ParticipantContent.column_names,
                        "a." + ParticipantContent.column_name + " like ?",
                        new String[]{"%" + uri.getLastPathSegment() + "%"}, null, null, sortOrder);
                break;
            case country_by_code:
                Util.log("URI entro", uri.toString());
                cursor = db.query(ParticipantContent.table_name_country,
                        ParticipantContent.column_names2,
                        ParticipantContent.column_country_code + " like ?",
                        new String[]{"%" + uri.getLastPathSegment() + "%"}, null, null, sortOrder);
                break;
            case participant_by_column:
                List<String> list = uri.getPathSegments();
                cursor = db.query(getParticipantTableName(),
                        ParticipantContent.column_names,
                        "a." + list.get(list.size() - 2) + " = ?",
                        new String[]{"%" + uri.getLastPathSegment() + "%"}, null, null, sortOrder);
                break;
            case notices:
                cursor = db.query(NoticeContent.table_name,
                        NoticeContent.column_names,
                        selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case sponsors_by_type:
                cursor = db.query(SponsorContent.table_name,
                        SponsorContent.column_names,
                        SponsorContent.column_type + "=?",
                        new String[]{uri.getLastPathSegment()}, null, null, sortOrder);
                break;
            case sponsors_as_map_points:
                cursor = db.query(SponsorContent.table_name,
                        SponsorContent.column_names2,
                        SponsorContent.column_type + "=?",
                        new String[]{uri.getLastPathSegment()}, null, null, sortOrder);
                break;
            case conferences:
                cursor = db.query(getConferenceTableName(),
                        ConferenceContent.column_names,
                        null, null, null, null, sortOrder);
                break;
            case conferences_by_date:
                cursor = db.query(getConferenceTableName(),
                        ConferenceContent.column_names,
                        ConferenceContent.column_date + "=?", new String[]{uri.getLastPathSegment()},
                        null, null, sortOrder);
                break;
            case conferences_by_hours_and_date:
                List<String> list2 = uri.getPathSegments();
                int size = list2.size();
                cursor = db.query(getConferenceTableName(),
                        ConferenceContent.column_names,
                        ConferenceContent.column_date
                                + " =? and "
                                + ConferenceContent.column_hour_start
                                + " =?",
                        new String[]{uri.getLastPathSegment(), list2.get(size - 2)},
                        null, null, sortOrder);
                break;
            case paper_by_id:
                cursor = db.query(PaperContent.table_name,
                        PaperContent.column_names,
                        PaperContent._ID + "=?",
                        new String[]{uri.getLastPathSegment()},
                        null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    private String getParticipantTableName() {
        return ParticipantContent.table_name_participant + " a inner join " +
                ParticipantContent.table_name_country + " b on a."
                + ParticipantContent.table_name_country
                + "=b." + BaseColumns._ID;
    }

    private String getConferenceTableName() {
        return ConferenceContent.table_name_conference + " a inner join " +
                ConferenceContent.table_name_code
                + " b on a." + ConferenceContent.column_code_id
                + " = b._id inner join " + ConferenceContent.table_name_chairman
                + " c on c._id=a."
                + ConferenceContent.column_chairman_id;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        final SQLiteDatabase db = ln.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case participants:
                rowsUpdated = db.update(ParticipantContent.table_name_participant, values, selection,
                        selectionArgs);
                break;
            case notices:
                rowsUpdated = db.update(NoticeContent.table_name, values, selection,
                        selectionArgs);
                break;
            case papers:
                rowsUpdated = db.update(PaperContent.table_name, values, selection,
                        selectionArgs);
                break;
            case sponsors:
                rowsUpdated = db.update(SponsorContent.table_name, values, selection,
                        selectionArgs);
                break;
            case conferences:
                rowsUpdated = db.update(ConferenceContent.table_name_conference, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = ln.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case notices:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(NoticeContent.table_name, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public void shutdown() {
        ln.close();
        super.shutdown();
    }

    static UriMatcher buildUriMatcher() {
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, ParticipantContent.PATH_PARTICIPANT, participants);
        matcher.addURI(authority, ParticipantContent.PATH_PARTICIPANT + "/#", participant_by_id);
        matcher.addURI(authority, ParticipantContent.PATH_PARTICIPANT + "/type/*", participant_by_type);
        matcher.addURI(authority, ParticipantContent.PATH_PARTICIPANT + "/name/*", participant_by_name);
        matcher.addURI(authority, ParticipantContent.PATH_PARTICIPANT + "/country/*", country_by_code);
        matcher.addURI(authority, ParticipantContent.PATH_PARTICIPANT + "/*/*", participant_by_column);

        matcher.addURI(authority, SponsorContent.SPONSOR_PATH, sponsors);
        matcher.addURI(authority, SponsorContent.SPONSOR_PATH + "/info/*", sponsors_as_map_points);
        matcher.addURI(authority, SponsorContent.SPONSOR_PATH + "/*", sponsors_by_type);

        matcher.addURI(authority, PaperContent.PAPER_PATH, papers);
        matcher.addURI(authority, PaperContent.PAPER_PATH + "/#", paper_by_id);

        matcher.addURI(authority, NoticeContent.NOTICE_PATH, notices);

        matcher.addURI(authority, ConferenceContent.CONFERENCE_PATH, conferences);
        matcher.addURI(authority, ConferenceContent.CONFERENCE_PATH + "/#", conferences_by_date);
        matcher.addURI(authority, ConferenceContent.CONFERENCE_PATH + "/*/#", conferences_by_hours_and_date);

        return matcher;
    }
}
