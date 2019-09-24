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

package com.mainstreetcode.teammate.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.mainstreetcode.teammate.R
import com.mainstreetcode.teammate.baseclasses.TeammatesBaseActivity
import com.mainstreetcode.teammate.fragments.registration.ResetPasswordFragment
import com.mainstreetcode.teammate.fragments.registration.SplashFragment
import com.mainstreetcode.teammate.viewmodel.UserViewModel
import com.tunjid.androidbootstrap.core.components.Navigator
import com.tunjid.androidbootstrap.core.components.stackNavigator

class RegistrationActivity : TeammatesBaseActivity(R.layout.activity_registration), Navigator.NavigationController {

    override val navigator: Navigator by stackNavigator(R.id.main_fragment_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        uiState = uiState.copy(toolbarShows = false, bottomNavShows = false)

        if (savedInstanceState == null) {
            val userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

            if (userViewModel.isSignedIn)
                startMainActivity(this)
            else if (hasNoDeepLink()) navigator.show(SplashFragment.newInstance())
        }
    }

    private fun hasNoDeepLink(): Boolean {
        val intent = intent
        val data = intent.data ?: return true

        if (isForgotPasswordDeepLink(data)) {
            val token = data.getQueryParameter(TOKEN)
            navigator.show(ResetPasswordFragment.newInstance(token ?: ""))
            return false
        }
        return true
    }

    private fun isForgotPasswordDeepLink(data: Uri): Boolean {
        val domain1 = getString(R.string.domain_1)
        val domain2 = getString(R.string.domain_2)

        val path = data.path ?: return false
        val domainMatches = domain1 == data.host || domain2 == data.host
        return domainMatches && path.contains("forgotPassword")
    }

    companion object {

        private const val TOKEN = "token"

        fun startMainActivity(activity: Activity?) {
            if (activity == null) return
            val main = Intent(activity, MainActivity::class.java)
            activity.startActivity(main)
            activity.finish()
        }
    }
}
