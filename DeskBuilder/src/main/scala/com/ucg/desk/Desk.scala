package com.ucg.desk

import android.net.Uri
import com.ucg.desk.util.CardUtils
import com.elegion.dk.sqlite.{SQLite, SQLiteColumn, SQLiteTableEditor, SQLiteTable}
import com.ucg.desk.sqlite.SQLiteProvider
import android.provider.BaseColumns
import android.database.sqlite.SQLiteDatabase
import DeskSchema._

class Desk(var kind: String, var fileName: String) extends SQLiteTable{

  def this() = this("", "")

  override def getTableName: String = TABLE

  override def onCreate(db: SQLiteDatabase) {
    new SQLiteTableEditor(this)
      .addColumn(new SQLiteColumn(Columns.KIND))
      .addColumn(new SQLiteColumn(Columns.NAME))
      .addColumn(new SQLiteColumn(Columns.CARD_PATH))
      .create(db)
  }

  def path = kind + "/" + fileName

  def getUri: Uri = {
    Uri.parse(CardUtils.FILE_ANDROID_ASSET + path)
  }

}

object DeskSchema {
  val TABLE = "desk"
  val URI: Uri = SQLite.uri(SQLite.SCHEME, SQLiteProvider.AUTHORITY, TABLE)

  object Columns extends BaseColumns {
    val CARD_PATH = "card_path"
    val NAME = "name"
    val KIND = "kind"
  }
}

