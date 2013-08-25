/**
 * 
 */

package com.yasiradnan.abstracts;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Adnan
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static String Database_Name = "TestData";

    private static int Database_Version = 1;

    SQLiteDatabase database;

    public long items_id;

    public long authors_id;

    public long abstract_affiliation_id;

    /*
     * Tables Name
     */

    public static final String TABLE_ABS_AFFILIATION_NAME = "ABS_AFFILIATION_NAME";

    public static final String TABLE_ABSTRACT_AFFILIATE_NAME = "ABSTRACT_AFFILIATE_NAME";

    public static final String TABLENAME_ABSTRACT_AFFILIATION = "ABSTRACT_AFFILIATION";

    public static final String TABLENAME_ABSTRACT_AUTHOR = "ABSTRACT_AUTHOR";

    public static final String TABLENAME_ABSTRACT_KEY_WORDS = "ABSTRACT_KEY_WORDS";

    public static final String TABLENAME_ABSTRACTS_ITEM = "ABSTRACTS_ITEM";

    public static final String TABLE_AUTHORS_ABSTRACT = "AUTHORS_ABSTRACT";

    public static final String TABLE_AUTHORS_AFFILIATE = "AUTHORS_AFFILIATE";

    /*
     * Query for Creating Tables
     */

    private String CREATE_ABS_AFFILIATION_NAME = "CREATE TABLE IF NOT EXISTS ABS_AFFILIATION_NAME "
            + "( _id INTEGER PRIMARY KEY AUTOINCREMENT,AF_NAME TEXT);";

    private String CREATE_ABSTRACT_AFFILIATE_NAME = "CREATE TABLE IF NOT EXISTS ABSTRACT_AFFILIATE_NAME"
            + "(ABSTRACTSITEM_ID INTEGER NOT NULL,ABSAFFILIATIONNAME_ID INTEGER NOT NULL);";

    private String CREATE_ABSTRACT_AFFILIATION = "CREATE TABLE IF NOT EXISTS ABSTRACT_AFFILIATION ("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT,AFFILIATION_NUMBER TEXT);";

    private String CREATE_ABSTRACT_AUTHOR = "CREATE TABLE IF NOT EXISTS  ABSTRACT_AUTHOR "
            + "(_id INTEGER PRIMARY KEY AUTOINCREMENT ,NAME TEXT NOT NULL,IS__CORRESPONDING TEXT);";

    private String CREATE_ABSTRACT_KEY_WORDS = "CREATE TABLE IF NOT EXISTS ABSTRACT_KEY_WORDS"
            + "( KEYWORDS TEXT,_id INTEGER PRIMARY KEY AUTOINCREMENT );";

    private String CREATE_ABSTRACTS_ITEM = "CREATE TABLE IF NOT EXISTS ABSTRACTS_ITEM"
            + "(_id INTEGER PRIMARY KEY AUTOINCREMENT , CORRESPONDENCE TEXT NOT NULL,"
            + "TITLE TEXT NOT NULL ,URL TEXT NOT NULL,"
            + "TEXT TEXT NOT NULL,TYPE TEXT NOT NULL, TOPIC TEXT NOT NULL,"
            + "COI TEXT NOT NULL,CITE TEXT NOT NULL,REFS TEXT NOT NULL);";

    private String CREATE_AUTHORS_ABSTRACT = "CREATE TABLE IF NOT EXISTS AUTHORS_ABSTRACT"
            + "( ABSTRACTSITEM_ID INTEGER NOT NULL,ABSTRACTAUTHOR_ID INTEGER NOT NULL);";

    private String CREATE_AUTHORS_AFFILIATE = "CREATE TABLE IF NOT EXISTS AUTHORS_AFFILIATE"
            + "(ABSTRACTAUTHOR_ID INTEGER NOT NULL,ABSTRACTAFFILIATION_ID INTEGER NOT NULL );";

    /*
     * End
     */

    public DatabaseHelper(Context context) {
        super(context, Database_Name, null, Database_Version);
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * @see
     * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
     * .SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase arg0) {
        // TODO Auto-generated method stub

        /*
         * Creating Tables
         */

        database.execSQL(CREATE_ABS_AFFILIATION_NAME);

        database.execSQL(CREATE_ABSTRACT_AFFILIATE_NAME);

        database.execSQL(CREATE_ABSTRACT_AFFILIATION);

        database.execSQL(CREATE_ABSTRACT_AUTHOR);

        database.execSQL(CREATE_ABSTRACT_KEY_WORDS);

        database.execSQL(CREATE_ABSTRACTS_ITEM);

        database.execSQL(CREATE_AUTHORS_ABSTRACT);

        database.execSQL(CREATE_AUTHORS_AFFILIATE);

    }

    /*
     * (non-Javadoc)
     * @see
     * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
     * .SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

        database.execSQL("DROP TABLE IF EXISTS" + TABLE_ABS_AFFILIATION_NAME);

        database.execSQL("DROP TABLE IF EXISTS" + TABLE_ABSTRACT_AFFILIATE_NAME);

        database.execSQL("DROP TABLE IF EXISTS" + TABLENAME_ABSTRACT_AFFILIATION);

        database.execSQL("DROP TABLE IF EXISTS" + TABLENAME_ABSTRACT_AUTHOR);

        database.execSQL("DROP TABLE IF EXISTS" + TABLENAME_ABSTRACTS_ITEM);

        database.execSQL("DROP TABLE IF EXISTS" + TABLENAME_ABSTRACT_KEY_WORDS);

        database.execSQL("DROP TABLE IF EXISTS" + TABLE_AUTHORS_ABSTRACT);

        database.execSQL("DROP TABLE IF EXISTS" + TABLE_AUTHORS_AFFILIATE);

    }

    public void open() {
        /*
         * Opening Database
         */

        database = this.getWritableDatabase();
    }

    public void close() {
        /*
         * Closing Database
         */

        database.close();
    }

    public void addItems(Integer id, String text, String topic, String correspondence, String url,
            String coi, String cite, String type, String title, String refs) {
        ContentValues cd = new ContentValues();

        cd.put("_id", id);

        cd.put("TEXT", text);

        cd.put("CORRESPONDENCE", correspondence);

        cd.put("TITLE", title);

        cd.put("URL", url);

        cd.put("TYPE", type);

        cd.put("TOPIC", topic);

        cd.put("COI", coi);

        cd.put("CITE", cite);

        cd.put("REFS", refs);

        items_id = database.insert(TABLENAME_ABSTRACTS_ITEM, null, cd);

    }

    public void addAbsAffiliation(String af_name, Integer id) {

        ContentValues values = new ContentValues();

        values.put("_id", id);

        values.put("AF_NAME", af_name);

        database.insert(TABLE_ABS_AFFILIATION_NAME, null, values);
    }

    public void addAuthors(Integer id, String Name, String is_corresponding) {

        ContentValues value = new ContentValues();

        value.put("_id", id);

        value.put("NAME", Name);

        value.put("IS__CORRESPONDING", is_corresponding);

        authors_id = database.insert(TABLENAME_ABSTRACT_AUTHOR, null, value);
    }

    public void addKeyWord(String KeyWords, Integer id) {

        ContentValues values = new ContentValues();

        values.put("KEYWORDS", KeyWords);

        values.put("_id ", id);

        database.insert(TABLENAME_ABSTRACT_KEY_WORDS, null, values);

    }

    public void addAbstractAffiliation(Integer id, String af_number) {

        ContentValues values = new ContentValues();

        values.put("_id", id);

        values.put("AFFILIATION_NUMBER", af_number);

        abstract_affiliation_id = database.insert(TABLENAME_ABSTRACT_AFFILIATION, null, values);
    }

    public void addAuthorsAbstractItems(long abstractItems_id, long abstractAuthor_id) {

        ContentValues values = new ContentValues();

        values.put("ABSTRACTSITEM_ID", abstractItems_id);

        values.put("ABSTRACTAUTHOR_ID", abstractAuthor_id);

        database.insert(TABLE_AUTHORS_ABSTRACT, null, values);

    }

    public void authorsAffiliation(long ABSTRACTAFFILIATION_ID, long ABSTRACTAUTHOR_ID) {
        
        ContentValues values = new ContentValues();

        values.put("ABSTRACTAFFILIATION_ID", ABSTRACTAFFILIATION_ID);

        values.put("ABSTRACTAUTHOR_ID", ABSTRACTAUTHOR_ID);

        database.insert(TABLE_AUTHORS_AFFILIATE, null, values);
    }

}