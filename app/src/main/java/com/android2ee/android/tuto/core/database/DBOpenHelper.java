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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * @author mathias1
 * @goals This class aims to:
 *        <ul>
 *        <li></li>
 *        </ul>
 */
public class DBOpenHelper extends SQLiteOpenHelper {
	/** @goals This class aims to show the constant to use for the DBOpenHelper */
	public static class Constants implements BaseColumns {
		/** The database name */
		public static final String DATABASE_NAME = "myData.db";
		/** The database version */
		public static final int DATABASE_VERSION = 1;
		/** The table Name */
		public static final String MY_TABLE = "Human";
		// Noms de colonnes
		// /!\Si vous utilisez une base de donn�es, les noms des colonnes ont les m�mes que ceux de
		// votre bases, de m�me pour les indexes.
		/** My Column ID and the associated explanation for end-users */
		public static final String KEY_COL_ID = "_id";// Mandatory
		/** My Column Name and the associated explanation for end-users */
		public static final String KEY_COL_NAME = "name";
		/** My Column First Name and the associated explanation for end-users */
		public static final String KEY_COL_FIRSTNAME = "firstName";
		/** My Column Eyes Color and the associated explanation for end-users */
		public static final String KEY_COL_EYES_COLOR = "eyesColor";
		/** My Column Hair color and the associated explanation for end-users */
		public static final String KEY_COL_HAIR_COLOR = "hairColor";
		/** My Column age and the associated explanation for end-users */
		public static final String KEY_COL_AGE = "age";

		// Indexes des colonnes
		/** The index of the column ID */
		public static final int ID_COLUMN = 1;
		/** The index of the column NAME */
		public static final int NAME_COLUMN = 2;
		/** The index of the column FIRST NAME */
		public static final int FIRSTNAME_COLUMN = 3;
		/** The index of the column EYES COLOR */
		public static final int EYES_COLOR_COLUMN = 4;
		/** The index of the column HAIR COLOR */
		public static final int HAIR_COLOR_COLUMN = 5;
		/** The index of the column AGE */
		public static final int AGE_COLUMN = 6;
	}

	// Cr�e une nouvelle base.
	private static final String DATABASE_CREATE = "create table " + Constants.MY_TABLE + " (" + Constants.KEY_COL_ID
			+ " integer primary key autoincrement, " + Constants.KEY_COL_AGE + " INTEGER, " + Constants.KEY_COL_NAME
			+ " TEXT, " + Constants.KEY_COL_FIRSTNAME + " TEXT, " + Constants.KEY_COL_EYES_COLOR + " TEXT, "
			+ Constants.KEY_COL_HAIR_COLOR + " TEXT) ";

	/**
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public DBOpenHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	/* 
	 * * (non-Javadoc) * @see*
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// Create the new database using the SQL string Database_create
		db.execSQL(DATABASE_CREATE);

	}

	/* 
	 * * (non-Javadoc) * @see *
	 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
	 * .SQLiteDatabase,int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("DBOpenHelper", "Mise � jour de la version " + oldVersion + " vers la version " + newVersion
				+ ", les anciennes donn�es seront d�truites ");
		// Drop the old database
		db.execSQL("DROP TABLE IF EXISTS " + Constants.MY_TABLE);
		// Create the new one
		onCreate(db);
		// or do a smartest stuff
	}

}
