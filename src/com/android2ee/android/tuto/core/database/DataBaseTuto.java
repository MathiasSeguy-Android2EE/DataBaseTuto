/**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br>
 * Produced by <strong>Dr. Mathias SEGUY</strong> with the smart contribution of <strong>Julien PAPUT</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 * Belongs to <strong>Mathias Seguy</strong></br>
 * ****************************************************************************************************************</br>
 * This code is free for any usage but can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br>
 * </br>
 * For any information (Advice, Expertise, J2EE or Android Training, Rates, Business):</br>
 * <em>mathias.seguy.it@gmail.com</em></br>
 * *****************************************************************************************************************</br>
 * Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 * Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br>
 * Sa propriété intellectuelle appartient à <strong>Mathias Séguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br>
 * </br>
 * Pour tous renseignements (Conseil, Expertise, Formations J2EE ou Android, Prestations, Forfaits):</br>
 * <em>mathias.seguy.it@gmail.com</em></br>
 * *****************************************************************************************************************</br>
 * Merci à vous d'avoir confiance en Android2EE les Ebooks de programmation Android.
 * N'hésitez pas à nous suivre sur twitter: http://fr.twitter.com/#!/android2ee
 * N'hésitez pas à suivre le blog Android2ee sur Developpez.com : http://blog.developpez.com/android2ee-mathias-seguy/
 * *****************************************************************************************************************</br>
 * com.android2ee.android.tuto</br>
 * 25 mars 2011</br>
 */
package com.android2ee.android.tuto.core.database;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.widget.TextView;

import com.android2ee.android.tuto.core.database.DBOpenHelper.Constants;

public class DataBaseTuto extends Activity {
	/** * The database */
	private SQLiteDatabase db;
	/** * The database creator and updater helper */
	DBOpenHelper dbOpenHelper;
	/**
	 * The textview that displays the whathappens
	 */
	private TextView txvWhatHappens = null;
	/**
	 * The WhatHappens messages
	 */
	private StringBuilder strWhatHappens;
	/**
	 * Carriage return
	 */
	private final String carriageReturn = "\r\n\r\n";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		txvWhatHappens = (TextView) findViewById(R.id.txvWhatHappens);
		strWhatHappens = new StringBuilder(getString(R.string.whatHappens_title));
		// Create or retrieve the database
		dbOpenHelper = new DBOpenHelper(this, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
		// open the database
		openDB();
		
		//if you need to delete every records:
		//db.delete(DBOpenHelper.Constants.MY_TABLE, null, null);
		
		// Insert a new record
		// -------------------
		ContentValues contentValues = new ContentValues();
		long rowId = insertRecord(contentValues);
		// update that line
		// ----------------
		updateRecord(contentValues, rowId);
		// Query that line
		// ---------------
		queryTheDatabase();
		// And then delete it:
		// -------------------
		deleteRecord(rowId);
		// Query that line
		// ---------------
		queryTheDatabase();
	}

	/******************************************************************************************/
	/** Displaying what happens **************************************************************************/
	/******************************************************************************************/
	/**
	 * Displays the information to the user of what's happening
	 * 
	 * @param message
	 */
	private void updateTxvWhatHappens(String message) {
		strWhatHappens.append(carriageReturn);
		strWhatHappens.append(message);
		txvWhatHappens.setText(strWhatHappens);
		//Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	/******************************************************************************************/
	/** Managing LifeCycle and database open/close operations *********************************/
	/******************************************************************************************/
	/* * (non-Javadoc)** @see android.app.Activity#onResume() */
	@Override
	protected void onResume() {
		super.onResume();
		openDB();
	}

	/* * (non-Javadoc)** @see android.app.Activity#onPause() */
	@Override
	protected void onPause() {
		super.onPause();
		closeDB();
	}

	/**
	 * * Open the database*
	 * 
	 * @throws SQLiteException
	 */
	public void openDB() throws SQLiteException {
		try {
			db = dbOpenHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			db = dbOpenHelper.getReadableDatabase();
		}
	}

	/** *Close Database */
	public void closeDB() {
		db.close();
	}

	/******************************************************************************************/
	/** Managing Records **************************************************************************/
	/******************************************************************************************/
	/**
	 * Insert a record
	 * 
	 * @param contentValues
	 *            (an empty contentValues)
	 * @return the inserted row id
	 */
	private long insertRecord(ContentValues contentValues) {
		// Assign the values for each column.
		contentValues.put(Constants.KEY_COL_NAME, "name");
		contentValues.put(Constants.KEY_COL_FIRSTNAME, "firstName");
		contentValues.put(Constants.KEY_COL_EYES_COLOR, "green");
		contentValues.put(Constants.KEY_COL_HAIR_COLOR, "blond");
		contentValues.put(Constants.KEY_COL_AGE, 6);
		// Insert the line in the database
		long rowId = db.insert(Constants.MY_TABLE, null, contentValues);
		// test to see if the insertion was ok
		if (rowId == -1) {
			updateTxvWhatHappens(getString(R.string.error_creating_human));
		} else {
			updateTxvWhatHappens(getString(R.string.success_creating_human,rowId));
		}
		return rowId;
	}

	/**
	 * * Update a record
	 * 
	 * @param contentValues
	 * @param rowId
	 * @return the updated row id
	 */
	private long updateRecord(ContentValues contentValues, long rowId) {
		contentValues.clear();
		contentValues.put(Constants.KEY_COL_NAME, "Georges");
		contentValues.put(Constants.KEY_COL_FIRSTNAME, "Walter Bush");
		// update the database
		int numberOfUpdatedElements = db.update(Constants.MY_TABLE, contentValues, Constants.KEY_COL_ID + "=" + rowId, null);
		// test to see if the insertion was ok
		if (rowId == -1) {
			updateTxvWhatHappens(getString(R.string.error_updating_human,numberOfUpdatedElements));
		} else {
			updateTxvWhatHappens(getString(R.string.success_updating_human,numberOfUpdatedElements));
		}
		return numberOfUpdatedElements;
	}

	/**
	 * Delete a Record
	 * 
	 * @param rowId
	 */
	private void deleteRecord(long rowId) {
		int numberOfUpdatedElements = db.delete(DBOpenHelper.Constants.MY_TABLE, DBOpenHelper.Constants.KEY_COL_ID + "=" + rowId, null);
		if (rowId == -1) {
			updateTxvWhatHappens(getString(R.string.error_deleting_human,rowId,numberOfUpdatedElements));
		} else {
			updateTxvWhatHappens(getString(R.string.success_deleting_human,rowId,numberOfUpdatedElements));
		}
	}

	/** *Query a the database */
	private void queryTheDatabase() {
		// Using man made query
		// The projection define what are the column you want to retrieve
		String[] projections = new String[] { Constants.KEY_COL_ID, Constants.KEY_COL_NAME, Constants.KEY_COL_FIRSTNAME };
		// And then store the column index answered by the request (we present an other way to
		// retireve data)
		final int cursorIdColNumber = 0, cursorNameColNumber = 1, cursorFirstNameColNumber = 2;
		// To add a Where clause you can either do that:
		// qb.appendWhere(Constants.KEY_COL_HAIR_COLOR+ "=blond");
		// Or that:
		String selection = Constants.KEY_COL_HAIR_COLOR + "=?";
		String[] selectionArg = new String[] { "blond" };
		// The groupBy clause:
		String groupBy = Constants.KEY_COL_EYES_COLOR;
		// The having clause
		String having = null;
		// The order by clause (column name followed by Asc or DESC)
		String orderBy = Constants.KEY_COL_AGE + "  ASC";
		// Maximal size of the results list
		String maxResultsListSize = "60";
		Cursor cursor = db.query(Constants.MY_TABLE, projections, selection, selectionArg, groupBy, having, orderBy,
				maxResultsListSize);

		displayResults(cursor);

		// Using the QueryBuilder
		// Create a Query SQLite object
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(Constants.MY_TABLE);
		// qb.appendWhere(Constants.KEY_COL_HAIR_COLOR+ "=blond");
		qb.setDistinct(true);
		cursor = qb.query(db, projections, selection, selectionArg, groupBy, having, orderBy);
		displayResults(cursor);
	}

	/**
	 * Display the results stored in the cursor
	 * 
	 * @param cursor
	 */
	private void displayResults(Cursor cursor) {
		// then browse the result:
		if (cursor.moveToFirst()) {
			// The elements to retrieve
			Integer colId;
			String name;
			String firstname;
			// The associated index within the cursor
			int indexId = cursor.getColumnIndex(Constants.KEY_COL_ID);
			int indexName = cursor.getColumnIndex(Constants.KEY_COL_NAME);
			int indexFirstName = cursor.getColumnIndex(Constants.KEY_COL_FIRSTNAME);
			// Browse the results list:
			int count = 0;
			do {
				colId = cursor.getInt(indexId);
				name = cursor.getString(indexName);
				firstname = cursor.getString(indexFirstName);
				updateTxvWhatHappens(getString(R.string.retieve_element, name, firstname, colId));
				count++;
			} while (cursor.moveToNext());
			updateTxvWhatHappens(getString(R.string.elements_retireved_count, count));
		} else {
			updateTxvWhatHappens(getString(R.string.retrieve_no_element));
		}
		// And never ever forget to close the cursor when you have finished with it:
		cursor.close();
	}
}