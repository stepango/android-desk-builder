package com.ucg.desk

import android.app.Application
import org.acra.annotation.ReportsCrashes
import org.acra.ACRA

@ReportsCrashes(
  mailTo = "step.89.g@gmail.com",
  formKey = ""
)
class App extends Application {
  override def onCreate(): Unit = {
    super.onCreate()
    ACRA.init(this)
  }
}