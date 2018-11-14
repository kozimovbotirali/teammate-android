package com.mainstreetcode.teammate.baseclasses;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.mainstreetcode.teammate.viewmodel.UserViewModel;

/**
 * Base Fragment for registration
 * <p>
 * Created by Shemanigans on 6/3/17.
 */

public abstract class RegistrationActivityFragment extends TeammatesBaseFragment {

    protected UserViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }
}
