package com.example.taskmanager.controller;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskmanager.model.Repository;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


import com.example.taskmanager.model.User;
import com.example.taskmanager.R;
import com.example.taskmanager.utility.RepoUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class LoginFragment extends Fragment {

    private static String TAG = "LOGIN_FRAG_LIFE_CYCLE";
    private static String USER_NAME_EXTRA = "USER_NAME_EXTRA";
    private static String USER_PASS_EXTRA = "USER_PASS_EXTRA";
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private Button mSignupButton;
    private Repository mRepository = Repository.getInstance();

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment loginFragment = new LoginFragment();
        return loginFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mRepository.setCurrentOrientation(getActivity().getResources().getConfiguration().orientation);

        initUI(view);
        setListneres();

        return view;
    }

    //Save & Restore Frag State---------------------------------------------------------------------
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");

        outState.putCharSequence(USER_NAME_EXTRA, mUsernameEditText.getText());
        outState.putCharSequence(USER_PASS_EXTRA, mPasswordEditText.getText());

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");

        if (savedInstanceState != null) {

            mUsernameEditText.setText(savedInstanceState.getCharSequence(USER_NAME_EXTRA));
            mPasswordEditText.setText(savedInstanceState.getCharSequence(USER_PASS_EXTRA));

        }

    }
    //----------------------------------------------------------------------------------------------

    private void setListneres() {

        mLoginButton.setOnClickListener(view -> {

            Context mContext = getContext();
            String name = mUsernameEditText.getText().toString();
            String pass = mPasswordEditText.getText().toString();
            mUsernameEditText.clearFocus();
            mPasswordEditText.clearFocus();

            if (RepoUtils.isUserExist(mContext, name, pass) == null) {

                Toast.makeText(getActivity(),
                        R.string.wrong_user,
                        Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(getActivity(),
                        R.string.successful_login,
                        Toast.LENGTH_SHORT).show();

                User user = RepoUtils.isUserExist(mContext, name, pass);
                RepoUtils.setCurrentUser(mContext, user);

                Fragment mainFragment = getActivity().getSupportFragmentManager()
                        .findFragmentByTag(getString(R.string.main_frag_tag));
                if (mainFragment == null)
                    mainFragment = ((MainActivity) getActivity()).createMainFragment();
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_activity_container,
                                mainFragment, String.valueOf(R.string.main_frag_tag))
                        .commit();
            }
        });

        mSignupButton.setOnClickListener(view -> {

            Context mContext = getContext();
            String name = mUsernameEditText.getText().toString();
            String pass = mPasswordEditText.getText().toString();

            if (name.matches("") || pass.matches("")) {

                Toast.makeText(getActivity(),
                        R.string.empty_name_pass,
                        Toast.LENGTH_SHORT).show();

            } else {

                User user = new User();
                user.setUserName(mUsernameEditText.getText().toString());
                user.setUserPassword(mPasswordEditText.getText().toString());
                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                user.setMDate(new SimpleDateFormat("yyyy/MM/dd").format(date));

                if (RepoUtils.isUserExist(mContext, user.getUserName(), user.getUserPassword()) != null) {
                    Toast.makeText(getActivity(),
                            R.string.not_available_username
                            , Toast.LENGTH_SHORT).show();
                } else {
                    new MaterialAlertDialogBuilder(mContext)
                            .setTitle("User Creation")
                            .setMessage("Is this user administrator?")
                            .setPositiveButton("Yes", (dialogInterface, i) -> {
                                user.setIsUserAdmin(true);
                                RepoUtils.insertUser(mContext, user);
                                Toast.makeText(getActivity(),
                                        R.string.successful_signup,
                                        Toast.LENGTH_SHORT).show();
                            })
                            .setNegativeButton("No", (dialogInterface, i) -> {
                                user.setIsUserAdmin(false);
                                RepoUtils.insertUser(mContext, user);
                                Toast.makeText(getActivity(),
                                        R.string.successful_signup,
                                        Toast.LENGTH_SHORT).show();
                            }).show();
                }
            }
        });

    }

    private void initUI(View view) {
        mUsernameEditText = view.findViewById(R.id.username_editText);
        mPasswordEditText = view.findViewById(R.id.password_editText);
        mLoginButton = view.findViewById(R.id.login_button);
        mSignupButton = view.findViewById(R.id.singup_button);
    }

    //LifeCycles Callbacks--------------------------------------------------------------------------

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.d(TAG, "onAttachFragment: ");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, "onViewStateRestored: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }
}
