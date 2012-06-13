package com.milanix.nepalux.search;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.milanix.nepalux.R;

/**
 * * DictionaryDatabase.java contains a code related to database. This class is
 * direct implementation of SearchableDictionary provided by Google API Sample
 * 
 * NepalUX
 * 
 * @author Milan Rajbhandari
 * @version 1.0
 */

public class DictionaryDatabase {
	private static final String TAG = "DictionaryDatabase";

	public static final String KEY_ITEM = SearchManager.SUGGEST_COLUMN_TEXT_1;
	public static final String KEY_DESCRIPTION = SearchManager.SUGGEST_COLUMN_TEXT_2;

	private static final String DATABASE_NAME = "dictionary";
	private static final String FTS_VIRTUAL_TABLE = "FTSdictionary";
	private static final int DATABASE_VERSION = 3;

	private final DictionaryOpenHelper mDatabaseOpenHelper;
	private static final HashMap<String, String> mColumnMap = buildColumnMap();

	public DictionaryDatabase(Context context) {
		mDatabaseOpenHelper = new DictionaryOpenHelper(context);
	}

	private static HashMap<String, String> buildColumnMap() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(KEY_ITEM, KEY_ITEM);
		map.put(KEY_DESCRIPTION, KEY_DESCRIPTION);
		map.put(BaseColumns._ID, "rowid AS " + BaseColumns._ID);
		map.put(SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID, "rowid AS "
				+ SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID);
		map.put(SearchManager.SUGGEST_COLUMN_SHORTCUT_ID, "rowid AS "
				+ SearchManager.SUGGEST_COLUMN_SHORTCUT_ID);
		return map;
	}

	public Cursor getWord(String rowId, String[] columns) {
		String selection = "rowid = ?";
		String[] selectionArgs = new String[] { rowId };

		return query(selection, selectionArgs, columns);
	}

	public Cursor getWordMatches(String query, String[] columns) {
		String selection = KEY_ITEM + " MATCH ?";
		String[] selectionArgs = new String[] { query + "*" };

		return query(selection, selectionArgs, columns);
	}

	private Cursor query(String selection, String[] selectionArgs,
			String[] columns) {
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(FTS_VIRTUAL_TABLE);
		builder.setProjectionMap(mColumnMap);

		Cursor cursor = builder.query(
				mDatabaseOpenHelper.getReadableDatabase(), columns, selection,
				selectionArgs, null, null, null);

		if (cursor == null) {
			return null;
		} else if (!cursor.moveToFirst()) {
			cursor.close();
			return null;
		}
		return cursor;
	}

	private static class DictionaryOpenHelper extends SQLiteOpenHelper {

		private final Context mHelperContext;
		private SQLiteDatabase mDatabase;

		private static final String FTS_TABLE_CREATE = "CREATE VIRTUAL TABLE "
				+ FTS_VIRTUAL_TABLE + " USING fts3 (" + KEY_ITEM + ", "
				+ KEY_DESCRIPTION + ");";

		DictionaryOpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			mHelperContext = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			mDatabase = db;
			mDatabase.execSQL(FTS_TABLE_CREATE);
			loadDictionary();
		}

		private void loadDictionary() {
			new Thread(new Runnable() {
				public void run() {
					try {
						loadWords();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}).start();
		}

		private void loadWords() throws IOException {
			final Resources resources = mHelperContext.getResources();
			InputStream inputStream = resources.openRawResource(R.raw.content);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));

			try {
				String line;
				while ((line = reader.readLine()) != null) {
					String[] strings = TextUtils.split(line, "-");
					if (strings.length < 4) {
						addWord(strings[0].trim(), strings[2].trim());
						System.out.println(strings[0].trim()
								+ strings[2].trim());
					}
				}
			} finally {
				reader.close();
			}
		}

		public long addWord(String word, String definition) {
			ContentValues initialValues = new ContentValues();
			initialValues.put(KEY_ITEM, word);
			initialValues.put(KEY_DESCRIPTION, definition);

			return mDatabase.insert(FTS_VIRTUAL_TABLE, null, initialValues);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE);
			onCreate(db);
		}
	}

}
