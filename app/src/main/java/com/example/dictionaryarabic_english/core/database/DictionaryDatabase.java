package com.example.dictionaryarabic_english.core.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
public class DictionaryDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "dictionary.db";
    private static final int DB_VERSION = 1;
    public DictionaryDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE dictionary (id INTEGER PRIMARY KEY, word TEXT, meaning TEXT)");
        db.execSQL("INSERT INTO dictionary (word, meaning) VALUES " +
                "('apple', 'تفاحة')," +
                "('book', 'كتاب')," +
                "('cat', 'قط')," +
                "('dog', 'كلب')," +
                "('sun', 'شمس');");
    }
    public String getMeaning(String word) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT meaning FROM dictionary WHERE word = ?", new String[]{word});
        if (cursor.moveToFirst()) {
            String meaning = cursor.getString(0);
            cursor.close();
            db.close();
            return meaning;
        }
        cursor.close();
        db.close();
        return "Not found";
    }
  public long insert(String word, String meaning) {
      ContentValues contentValues=new ContentValues();
      contentValues.put("word",word);
      contentValues.put("meaning",meaning);
      SQLiteDatabase db=this.getWritableDatabase();
      long value=db.insert("dictionary",null,contentValues);
      db.close();
      return value;
  }
    public List<String> getAllWords() {
        List<String> words = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT word FROM dictionary", null);
        while (cursor.moveToNext()) {
            words.add(cursor.getString(0));
        }
        cursor.close();
        db.close();
        return words;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS dictionary");
        onCreate(db);
    }
}