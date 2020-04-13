package com.arunyadav.jphposts.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arunyadav.jphposts.R

abstract class BaseActivity : AppCompatActivity() {
    abstract fun getScreenName(): String
}
