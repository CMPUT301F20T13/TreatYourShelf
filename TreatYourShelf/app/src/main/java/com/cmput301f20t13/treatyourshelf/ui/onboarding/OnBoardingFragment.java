package com.cmput301f20t13.treatyourshelf.ui.onboarding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import com.cmput301f20t13.treatyourshelf.R;

public class OnBoardingFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.loginFragment);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
