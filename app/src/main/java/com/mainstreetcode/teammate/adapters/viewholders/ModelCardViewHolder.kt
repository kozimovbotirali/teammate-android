/*
 * MIT License
 *
 * Copyright (c) 2019 Adetunji Dahunsi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.mainstreetcode.teammate.adapters.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.mainstreetcode.teammate.R
import com.mainstreetcode.teammate.model.RemoteImage
import com.mainstreetcode.teammate.util.THUMBNAIL_SIZE
import com.squareup.picasso.Picasso


open class ModelCardViewHolder<H : RemoteImage> internal constructor(
        itemView: View
) : RecyclerView.ViewHolder(itemView), ThumbnailHolder {

    protected lateinit var model: H

    internal var title: TextView = itemView.findViewById(R.id.item_title)
    internal var subtitle: TextView = itemView.findViewById(R.id.item_subtitle)
    override val thumbnail: ImageView = itemView.findViewById(R.id.thumbnail)

    open val isThumbnail: Boolean
        get() = true

    init {
        @Suppress("LeakingThis")
        if (isThumbnail) thumbnail.scaleType = ImageView.ScaleType.CENTER_CROP
    }

    open fun bind(model: H) {
        this.model = model
        val imageUrl = model.imageUrl

        if (imageUrl.isBlank()) thumbnail.setImageResource(R.color.dark_grey)
        else load(imageUrl, thumbnail)
    }

    fun withTitle(@StringRes titleRes: Int): ModelCardViewHolder<H> {
        title.setText(titleRes)
        return this
    }

    fun withSubTitle(@StringRes subTitleRes: Int): ModelCardViewHolder<H> {
        subtitle.setText(subTitleRes)
        return this
    }

    internal fun setTitle(title: CharSequence) {
        if (title.isNotBlank()) this.title.text = title
    }

    internal fun setSubTitle(subTitle: CharSequence) {
        if (subTitle.isNotBlank()) this.subtitle.text = subTitle
    }

    internal fun load(imageUrl: String, destination: ImageView) {
        val creator = Picasso.get().load(imageUrl)

        if (!isThumbnail) creator.fit().centerCrop()
        else creator.placeholder(R.drawable.bg_image_placeholder)
                .resize(THUMBNAIL_SIZE, THUMBNAIL_SIZE)
                .centerInside()

        creator.into(destination)
    }
}
