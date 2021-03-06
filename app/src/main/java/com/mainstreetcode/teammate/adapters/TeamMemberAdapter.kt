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

package com.mainstreetcode.teammate.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mainstreetcode.teammate.R
import com.mainstreetcode.teammate.adapters.viewholders.AdViewHolder
import com.mainstreetcode.teammate.adapters.viewholders.ContentAdViewHolder
import com.mainstreetcode.teammate.adapters.viewholders.InstallAdViewHolder
import com.mainstreetcode.teammate.adapters.viewholders.JoinRequestViewHolder
import com.mainstreetcode.teammate.adapters.viewholders.RoleViewHolder
import com.mainstreetcode.teammate.adapters.viewholders.bind
import com.mainstreetcode.teammate.model.Ad
import com.mainstreetcode.teammate.model.JoinRequest
import com.mainstreetcode.teammate.model.Role
import com.mainstreetcode.teammate.model.TeamMember
import com.mainstreetcode.teammate.util.CONTENT_AD
import com.mainstreetcode.teammate.util.INSTALL_AD
import com.mainstreetcode.teammate.util.JOIN_REQUEST
import com.mainstreetcode.teammate.util.ROLE
import com.tunjid.androidx.recyclerview.adapterOf
import com.tunjid.androidx.recyclerview.diff.Differentiable
import com.tunjid.androidx.view.util.inflate

fun teamMemberAdapter(
        modelSource: () -> List<Differentiable>,
        listener: UserHostListener
): RecyclerView.Adapter<RecyclerView.ViewHolder> = adapterOf(
        itemsSource = modelSource,
        viewHolderCreator = { viewGroup: ViewGroup, viewType: Int ->
            when (viewType) {
                CONTENT_AD -> ContentAdViewHolder(viewGroup.inflate(R.layout.viewholder_grid_content_ad), listener)
                INSTALL_AD -> InstallAdViewHolder(viewGroup.inflate(R.layout.viewholder_grid_install_ad), listener)
                ROLE -> RoleViewHolder(viewGroup.inflate(R.layout.viewholder_grid_item), listener)
                else -> JoinRequestViewHolder(viewGroup.inflate(R.layout.viewholder_grid_item), listener)
            }
        },
        viewHolderBinder = bind@{ holder, item, _ ->
            if (item is Ad<*> && holder is AdViewHolder<*>) holder.bind(item)
            if (item !is TeamMember) return@bind

            val wrapped = item.wrappedModel

            when {
                wrapped is Role && holder is RoleViewHolder -> holder.bind(wrapped)
                wrapped is JoinRequest && holder is JoinRequestViewHolder -> holder.bind(wrapped)
            }
        },
        itemIdFunction = { it.hashCode().toLong() },
        viewTypeFunction = {
            when (it) {
                is Ad<*> -> it.type
                !is TeamMember -> JOIN_REQUEST
                else -> if (it.wrappedModel is Role) ROLE else JOIN_REQUEST
            }
        }
)

interface UserHostListener {
    fun onRoleClicked(role: Role)

    fun onJoinRequestClicked(request: JoinRequest)
}