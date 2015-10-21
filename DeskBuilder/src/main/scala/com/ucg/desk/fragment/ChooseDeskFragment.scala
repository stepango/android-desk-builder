package com.ucg.desk.fragment

import android.app.{Fragment, LoaderManager}
import android.content.{CursorLoader, Intent, Loader}
import android.database.Cursor
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.{Button, GridLayout}
import com.ucg.desk.DeskSchema.Columns._
import com.ucg.desk.DeskSchema._
import com.ucg.desk.{BuilderActivity, R}

/**
 * Created by sone on 01.03.14.
 */
class ChooseDeskFragment
  extends Fragment
  with View.OnClickListener
  with LoaderManager.LoaderCallbacks[Cursor] {

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    inflater.inflate(R.layout.fmt_desk_choose, null, false)
  }

  override def onActivityCreated(savedInstanceState: Bundle) {
    super.onActivityCreated(savedInstanceState)
    getLoaderManager.initLoader(R.id.desk_loader, null, this)
  }

  def onClick(v: View) {
    val kind = v.getTag(R.id.kind).asInstanceOf[String]
    val name = v.getTag(R.id.name).asInstanceOf[String]
    val intent: Intent = new Intent(getActivity, classOf[BuilderActivity])
    intent.putExtra("CLASS", kind)
    intent.putExtra("NAME", name)
    startActivity(intent)
  }

  override def onCreateLoader(id: Int, args: Bundle): Loader[Cursor] = {
    new CursorLoader(getActivity, URI, Array(NAME, KIND, "COUNT(" + CARD_PATH + ")"), NAME + " IS NOT NULL GROUP BY " + NAME, null, null)
  }

  override def onLoadFinished(loader: Loader[Cursor], data: Cursor) {
    val layout = getView.findViewById(R.id.lay_buttons).asInstanceOf[GridLayout]
    layout.removeAllViews()
    layout.setColumnCount(3)
    if (data != null && data.moveToFirst()) while (!data.isAfterLast) {
      val button = new Button(getActivity)
      val kind = data.getString(data.getColumnIndex(KIND))
      val name = data.getString(data.getColumnIndex(NAME))
      button.setTag(R.id.kind, kind)
      button.setTag(R.id.name, name)
      button.setOnClickListener(this)
      button.setText(name + " " + data.getString(data.getColumnIndex("COUNT(" + CARD_PATH + ")")))
      button.setCompoundDrawablesWithIntrinsicBounds(null, getDrawableByName(kind), null, null)
      layout.addView(button, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
      data.moveToNext()
    }
    data.close()
  }

  private def getDrawableByName(name: String): Drawable = {
    ContextCompat.getDrawable(getActivity, getResources.getIdentifier(name, "drawable", getActivity.getPackageName))
  }

  override def onLoaderReset(loader: Loader[Cursor]): Unit = {
  }

  override def onResume(): Unit = {
    super.onResume()
    getLoaderManager.restartLoader(R.id.desk_loader, null, this)
  }
}