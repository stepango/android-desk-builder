package com.ucg.desk.sqlite

import com.elegion.dk.sqlite.{SQLiteSchema, SQLiteContentProvider}
import com.ucg.desk.{Card, Desk}

class SQLiteProvider extends SQLiteContentProvider {

  override protected def onCreateSchema: SQLiteSchema = {
    new SQLiteSchema(SQLiteProvider.DB_NAME, SQLiteProvider.DB_VERSION)
      .addTable(new Card())
      .addTable(new Desk())
  }

}

object SQLiteProvider {
  val AUTHORITY = "com.ucg.desk"
  val DB_NAME = "ucg.db"
  val DB_VERSION = 12
}