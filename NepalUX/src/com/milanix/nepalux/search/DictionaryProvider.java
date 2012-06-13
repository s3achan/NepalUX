/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.milanix.nepalux.search;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * * DictionaryProcider.java contains a code related to actual searching and
 * suggestions . This class is direct implementation of SearchableDictionary
 * provided by Google API Sample.
 * 
 * NepalUX
 * 
 * @author Milan Rajbhandari
 * @version 1.0
 */

public class DictionaryProvider extends ContentProvider {
	String TAG = "DictionaryProvider";

	public static String AUTHORITY = "com.milanix.nepalux.search.DictionaryProvider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/dictionary");

	public static final String WORDS_MIME_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/vnd.example.android.searchabledict";
	public static final String DEFINITION_MIME_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/vnd.example.android.searchabledict";

	private DictionaryDatabase mDictionary;

	private static final int SEARCH_WORDS = 0;
	private static final int GET_WORD = 1;
	private static final int SEARCH_SUGGEST = 2;
	private static final int REFRESH_SHORTCUT = 3;
	private static final UriMatcher sURIMatcher = buildUriMatcher();

	private static UriMatcher buildUriMatcher() {
		UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

		matcher.addURI(AUTHORITY, "dictionary", SEARCH_WORDS);
		matcher.addURI(AUTHORITY, "dictionary/#", GET_WORD);

		matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY,
				SEARCH_SUGGEST);
		matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY + "/*",
				SEARCH_SUGGEST);

		matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_SHORTCUT,
				REFRESH_SHORTCUT);
		matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_SHORTCUT
				+ "/*", REFRESH_SHORTCUT);
		return matcher;
	}

	@Override
	public boolean onCreate() {
		mDictionary = new DictionaryDatabase(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		switch (sURIMatcher.match(uri)) {
		case SEARCH_SUGGEST:
			if (selectionArgs == null) {
				throw new IllegalArgumentException(
						"selectionArgs must be provided for the Uri: " + uri);
			}
			return getSuggestions(selectionArgs[0]);
		case SEARCH_WORDS:
			if (selectionArgs == null) {
				throw new IllegalArgumentException(
						"selectionArgs must be provided for the Uri: " + uri);
			}
			return search(selectionArgs[0]);
		case GET_WORD:
			return getWord(uri);
		case REFRESH_SHORTCUT:
			return refreshShortcut(uri);
		default:
			throw new IllegalArgumentException("Unknown Uri: " + uri);
		}
	}

	private Cursor getSuggestions(String query) {
		query = query.toLowerCase();
		String[] columns = new String[] { BaseColumns._ID,
				DictionaryDatabase.KEY_ITEM,
				DictionaryDatabase.KEY_DESCRIPTION,

				SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID };

		return mDictionary.getWordMatches(query, columns);
	}

	private Cursor search(String query) {
		query = query.toLowerCase();
		String[] columns = new String[] { BaseColumns._ID,
				DictionaryDatabase.KEY_ITEM,
				DictionaryDatabase.KEY_DESCRIPTION };

		return mDictionary.getWordMatches(query, columns);
	}

	private Cursor getWord(Uri uri) {
		String rowId = uri.getLastPathSegment();
		String[] columns = new String[] { DictionaryDatabase.KEY_ITEM,
				DictionaryDatabase.KEY_DESCRIPTION };

		return mDictionary.getWord(rowId, columns);
	}

	private Cursor refreshShortcut(Uri uri) {
		String rowId = uri.getLastPathSegment();
		String[] columns = new String[] { BaseColumns._ID,
				DictionaryDatabase.KEY_ITEM,
				DictionaryDatabase.KEY_DESCRIPTION,
				SearchManager.SUGGEST_COLUMN_SHORTCUT_ID,
				SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID };

		return mDictionary.getWord(rowId, columns);
	}

	@Override
	public String getType(Uri uri) {
		switch (sURIMatcher.match(uri)) {
		case SEARCH_WORDS:
			return WORDS_MIME_TYPE;
		case GET_WORD:
			return DEFINITION_MIME_TYPE;
		case SEARCH_SUGGEST:
			return SearchManager.SUGGEST_MIME_TYPE;
		case REFRESH_SHORTCUT:
			return SearchManager.SHORTCUT_MIME_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URL " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		throw new UnsupportedOperationException();
	}

}
