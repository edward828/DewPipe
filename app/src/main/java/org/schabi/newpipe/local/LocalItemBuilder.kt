package org.schabi.newpipe.local

import android.content.Context
import android.widget.ImageView

import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader

import org.schabi.newpipe.database.LocalItem
import org.schabi.newpipe.util.OnClickGesture

/*
 * Created by Christian Schabesberger on 26.09.16.
 * <p>
 * Copyright (C) Christian Schabesberger 2016 <chris.schabesberger@mailbox.org>
 * InfoItemBuilder.java is part of NewPipe.
 * <p>
 * NewPipe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * NewPipe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with NewPipe.  If not, see <http://www.gnu.org/licenses/>.
 */

class LocalItemBuilder(val context: Context?) {
    private val imageLoader = ImageLoader.getInstance()

    var onItemSelectedListener: OnClickGesture<LocalItem>? = null

    fun displayImage(url: String, view: ImageView,
                     options: DisplayImageOptions) {
        imageLoader.displayImage(url, view, options)
    }

    companion object {
        private val TAG = LocalItemBuilder::class.java.toString()
    }
}
