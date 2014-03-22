package com.ucg.desk

import android.net.Uri
import com.ucg.desk.util.CardUtils
import com.elegion.dk.sqlite.{SQLite, SQLiteColumn, SQLiteTableEditor, SQLiteTable}
import com.ucg.desk.sqlite.SQLiteProvider
import android.provider.BaseColumns
import android.database.sqlite.SQLiteDatabase

class Card(var kind: String, var fileName: String) extends SQLiteTable with CardSchema {

  def this() = this("", "")
  def this(path:String) = this(path.split("/")(0), path.split("/")(1))

  override def getTableName: String = TABLE

  override def onCreate(db: SQLiteDatabase) {
    new SQLiteTableEditor(this)
      .addColumn(new SQLiteColumn(Columns.KIND))
      .addColumn(new SQLiteColumn(Columns.FILENAME))
      .create(db)
  }

  def path = kind + "/" + fileName

  def getUri: Uri = {
    Uri.parse(CardUtils.FILE_ANDROID_ASSET + path)
  }

}

trait CardSchema {
  val TABLE = "card"
  val URI: Uri = SQLite.uri(SQLite.SCHEME, SQLiteProvider.AUTHORITY, TABLE)

  object Columns extends BaseColumns {
    val KIND = "kind"
    val FILENAME = "filename"
  }

}