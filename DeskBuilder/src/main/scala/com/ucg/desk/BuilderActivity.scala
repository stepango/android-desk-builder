package com.ucg.desk

import android.app.{LoaderManager, Activity}
import android.os.Bundle
import android.graphics.Point
import android.widget._
import com.ucg.desk.view.{CardAdapter, FullCardAdapter}
import android.view.{MenuItem, Menu, ViewGroup, View}
import com.squareup.picasso.Picasso
import org.scaloid.common._
import scala.collection.mutable.ArrayBuffer
import android.content.{CursorLoader, Loader, DialogInterface, ContentValues}
import android.text.TextUtils
import android.database.Cursor
import com.ucg.desk.DeskSchema.Columns._

class BuilderActivity extends Activity with SActivity with Logger with LoaderManager.LoaderCallbacks[Cursor] {

  private val cards = new ArrayBuffer[Card]
  private var kind: String = _

  private var mColumnWidth: Int = 0
  private val mListener = new View.OnClickListener {
    def onClick(v: View) {
      if (v.getParent != null) {
        v.getParent.asInstanceOf[ViewGroup].removeView(v)
        val tag = v.getTag
        if (tag != null && tag.isInstanceOf[Card]) {
          cards -= tag.asInstanceOf[Card]
          toast(cards.size.toString)
        }
      }
    }
  }

  override def onCreateLoader(id: Int, args: Bundle): Loader[Cursor] = {
    new CursorLoader(this, DeskSchema.URI, null, NAME + "=?", Array(args.getString("NAME")), null)
  }

  override def onLoadFinished(loader: Loader[Cursor], data: Cursor) {
    if (data != null && data.moveToFirst()) while (!data.isAfterLast) {
      addCardToDesk(new Card(data.getString(data.getColumnIndex(CARD_PATH))), findViewById(R.id.grid_cards_desk).asInstanceOf[GridLayout])
      data.moveToNext()
    }
    data.close()
  }

  override def onLoaderReset(loader: Loader[Cursor]): Unit = {
  }

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_fullscreen)
    initGrids()

  }

  private def initGrids() {
    val point: Point = new Point
    getWindow.getWindowManager.getDefaultDisplay.getSize(point)
    mColumnWidth = point.x / 4
    kind = getIntent.getStringExtra("CLASS")
    if (getIntent.getExtras.containsKey("NAME")) {
      getLoaderManager.initLoader(R.id.desk_loader, getIntent.getExtras, this)
    }
    val desk: GridLayout = findViewById(R.id.grid_cards_desk).asInstanceOf[GridLayout]
    desk.setColumnCount(2)
    val cards: GridView = findViewById(R.id.grid_cards_all).asInstanceOf[GridView]
    cards.setColumnWidth(mColumnWidth)
    cards.setAdapter(new FullCardAdapter(this, kind))
    cards.setOnItemClickListener(new AdapterView.OnItemClickListener {
      def onItemClick(adapterView: AdapterView[_], view: View, i: Int, l: Long) {
        val s: Card = cards.getAdapter.asInstanceOf[CardAdapter].getCards.get(i)
        addCardToDesk(s, desk)
      }
    })
  }

  private def addCardToDesk(s: Card, desk: GridLayout) {
    val imageView: ImageView = new ImageView(this)
    imageView.setOnClickListener(mListener)
    imageView.setMinimumWidth(mColumnWidth)
    imageView.setMaxWidth(mColumnWidth)
    imageView.setTag(s)
    Picasso.`with`(this).load(s.getUri).placeholder(R.drawable.ic_launcher).into(imageView)
    desk.addView(imageView)
    cards += s
  }

  override def onCreateOptionsMenu(menu: Menu): Boolean = {
    getMenuInflater.inflate(R.menu.save, menu)
    super.onCreateOptionsMenu(menu)
  }

  def saveDesk(item: MenuItem) {
    toast("Save")
    new AlertDialogBuilder("Enter desk name", "") {
      val edit = new SEditText
      setView(edit)
      neutralButton(android.R.string.ok, (d: DialogInterface, i: Int) => {
        val name = edit.getText.toString
        if (!TextUtils.isEmpty(name)) {
          val arrCV = new ArrayBuffer[ContentValues](cards.size)
          for (card <- cards) {
            import DeskSchema.Columns._
            val cv = new ContentValues(3)
            cv.put(CARD_PATH, card.path)
            cv.put(NAME, name)
            cv.put(KIND, kind)
            arrCV += cv
          }
          val num = getContentResolver.bulkInsert(DeskSchema.URI, arrCV.toArray[ContentValues])
          toast(num.toString)
          d.dismiss()
        } else {
          toast("enter name")
        }
      })
    }.show()

  }

}