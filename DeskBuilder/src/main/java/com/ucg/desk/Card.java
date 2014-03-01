package com.ucg.desk;

import android.net.Uri;

import com.ucg.desk.util.CardUtils;

/**
 * Created by sone on 25.02.14.
 */
public class Card {

    private String mType;
    private String mFileName;
    private String mPath;

    public Card(final String type, final String fileName) {
        init(type, fileName);
    }

    public String getType() {
        return mType;
    }

    public void setType(final String type) {
        mType = type;
        init(type, mFileName);
    }

    public String getFileName() {
        return mFileName;
    }

    public void setFileName(final String fileName) {
        mFileName = fileName;
        init(mType, fileName);
    }

    private void init(final String type, final String fileName){
        mType = type;
        mFileName = fileName;
        mPath = type + "/" + fileName;
    }

    public String getPath(){
        return mPath;
    }

    public Uri getUri() {
        return Uri.parse(CardUtils.FILE_ANDROID_ASSET + getPath());
    }
}
