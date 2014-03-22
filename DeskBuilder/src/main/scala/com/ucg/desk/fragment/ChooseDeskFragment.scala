package com.ucg.desk.fragment

import android.app.{LoaderManager, Fragment}
import android.view.{LayoutInflater, ViewGroup, View}
import com.ucg.desk.{BuilderActivity, R}
import android.widget.{Button, GridLayout}
import android.graphics.drawable.Drawable
import android.content.Intent
import android.os.Bundle
import android.content.{CursorLoader, Loader}
import android.database.Cursor
import com.ucg.desk.DeskSchema.Columns._
import com.ucg.desk.DeskSchema._

/**
 * Created by sone on 01.03.14.
 */
class ChooseDeskFragment extends Fragment with View.OnClickListener with LoaderManager.LoaderCallbacks[Cursor] {
  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    inflater.inflate(R.layout.fmt_desk_choose, null, false)
  }

  override def onActivityCreated(savedInstanceState: Bundle) {
    super.onActivityCreated(savedInstanceState)
    getLoaderManager.initLoader(R.id.desk_loader, null, this)
  }

  private def getDrawableByName(name: String): Drawable = {
    getResources.getDrawable(getResources.getIdentifier(name, "drawable", getActivity.getPackageName))
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
    new CursorLoader(getActivity, URI, Array(NAME, KIND), NAME + " IS NOT NULL GROUP BY " + NAME, null, null)
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
      button.setText(name)
      button.setCompoundDrawablesWithIntrinsicBounds(null, getDrawableByName(kind), null, null)
      layout.addView(button, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
      data.moveToNext()
    }
    data.close()
  }

  override def onLoaderReset(loader: Loader[Cursor]): Unit = {
  }

  override def onResume(): Unit = {
    super.onResume()
    getLoaderManager.restartLoader(R.id.desk_loader, null, this)
  }
}