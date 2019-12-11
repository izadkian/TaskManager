package com.example.taskmanager.controller;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanager.model.Repository;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.R;
import com.example.taskmanager.utility.RepoUtils;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PagerMainFragment extends Fragment {

    public static final String ADD_TASK_DIALOG = "AddTaskDialog";
    private static String TAG = "PAGER_MAIN_FRAG_LIFE_CYCLE";
    private static String PAGER_EXTRA = "PAGER_EXTERA";

    private Context mContext = getContext();
    private RecyclerView mTaskRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private TaskRecyclerAdapter mTaskRecyclerAdapter = new TaskRecyclerAdapter();
    private FloatingActionButton mPagerAddFab, mPagerDelFab;
    private TextView mNoTaskTextView;
    private SearchView mSearchView;
    private Toolbar mToolbar;
    private RecyclerView.ViewHolder mViewRecycleItem;
    private MaterialCardView mRecycleMaterialCardView;
    private Task mCurrentItemTask;
    private Repository mRepository = Repository.getInstance();
    private int mCurrentTabPosition;


    //Tasks List for RecyclerView-------------------------------------------------------------------

    private List<Task> mTaskList = new ArrayList<>();

    public void setTaskList(List<Task> taskList) {
        mTaskList = taskList;
    }

    //Callbacks-------------------------------------------------------------------------------------

    private DeleteTaskListener mDeleteTaskListener;

    public interface DeleteTaskListener {
        void onDeleteTask(PagerMainFragment pagerMainFragment);

    }
    //onAttach for all listeners

    @Override
    public void onAttach(@NonNull Context context) {
        Log.d(TAG, "onAttach: ");
        super.onAttach(context);
        try {
            mDeleteTaskListener = (DeleteTaskListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement DeleteTaskListener on Activity");
        }
    }
    //Constructors----------------------------------------------------------------------------------

    public PagerMainFragment() {
        // Required empty public constructor
    }

    public static PagerMainFragment newInstance(int tabPosition) {
        PagerMainFragment fragment = new PagerMainFragment();
        Bundle args = new Bundle();
        args.putInt(PAGER_EXTRA, tabPosition);
        //  args.putParcelableArrayList(PAGER_EXTRA, (ArrayList<Task>) tasks);
        fragment.setArguments(args);
        //  fragment.setTaskList(tasks);
        return fragment;
    }
    //----------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: ");

        View rootView = inflater.inflate(R.layout.tabs_layout, container, false);

        initUI(rootView);
        
        if (mTaskList.size() == 0){
            
        }
        
        prepareRecyclerAdapter();
        setListeners();

        return rootView;
    }

    private void prepareRecyclerAdapter() {

        mTaskRecyclerAdapter.setTaskList(mTaskList);

        if (mTaskRecyclerAdapter.getItemCount() != 0) {

            mNoTaskTextView.setVisibility(View.GONE);
            mSearchView.setVisibility(View.VISIBLE);
            mTaskRecyclerView.setAdapter(mTaskRecyclerAdapter);

        }
    }

    private void initUI(View rootView) {

        mNoTaskTextView = rootView.findViewById(R.id.no_task_textview);

        mSearchView = rootView.findViewById(R.id.tablayout_searchview);
        mSearchView.setVisibility(View.GONE);

        mPagerAddFab = rootView.findViewById(R.id.tabs_layout_add_fab);
        mPagerDelFab = rootView.findViewById(R.id.tabs_layout_del_fab);

        View toolbarView = getLayoutInflater().inflate(R.layout.fragment_main, null);
        mToolbar = toolbarView.findViewById(R.id.tablayout_toolbar);
        mToolbar.inflateMenu(R.menu.main_frag_toolbar_menu);

        mTaskRecyclerView = (RecyclerView) rootView.findViewById(R.id.tab_recyclerview);
        mLinearLayoutManager = new LinearLayoutManager(this.getContext());
        mTaskRecyclerView.setLayoutManager(mLinearLayoutManager);
        Log.d("PAGER_MAIN_FRAG_LIST", "initUI: " + mTaskList.toString());

    }

    //Listeners-------------------------------------------------------------------------------------
    private void setListeners() {

        mPagerAddFab.setOnClickListener(view -> {
            Log.d(TAG, "setListeners: " + "FabAdd");
            FragmentManager fragmentManager = getFragmentManager();
            AddTaskDialogFragment dialog = new AddTaskDialogFragment();
            dialog.show(fragmentManager, ADD_TASK_DIALOG);
        });

        mPagerDelFab.setOnClickListener(view -> {

            int checkedCount = 0;
            for (int i = 0; i < mTaskRecyclerAdapter.getItemCount(); i++) {

                mViewRecycleItem = mTaskRecyclerView.findViewHolderForLayoutPosition(i);
                mRecycleMaterialCardView =
                        (MaterialCardView) mViewRecycleItem.itemView.findViewById(R.id.recycler_view_card);
                if (mRecycleMaterialCardView.isChecked()) {
                    checkedCount++;
                    mCurrentItemTask = mTaskRecyclerAdapter.getCurrentItemTask(i);
                    RepoUtils.deleteTask(getContext(), mCurrentItemTask);
                    //Interface Callback
                    mDeleteTaskListener.onDeleteTask(PagerMainFragment.this);
                }
            }
            if (checkedCount == 0) {
                Toast.makeText(getActivity(),
                        "No Action performed. You must select at least one task to delete."
                        , Toast.LENGTH_SHORT).show();
            }
        });

        mTaskRecyclerAdapter.setOnItemClickListener((itemView, position) -> {

            mCurrentItemTask = mTaskRecyclerAdapter.getCurrentItemTask(position);
            UUID currentTaskUUID = mCurrentItemTask.getTaskID();

            Fragment detailFragment = getActivity().getSupportFragmentManager()
                    .findFragmentByTag("Detail");
            if (detailFragment == null)
                detailFragment = ((MainActivity) getActivity()).createTaskDetailFragment(currentTaskUUID);
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_activity_container,
                            detailFragment, "Detail")
                    .addToBackStack("")
                    .commit();
        });

        mTaskRecyclerAdapter.setOnShareClickListener((itemView, position) -> {

            mCurrentItemTask = mTaskRecyclerAdapter.getCurrentItemTask(position);

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, getTaskReport(mCurrentItemTask));
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);

        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("ERROR", "onQueryTextSubmit: ");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                Log.d("ERROR", "onQueryTextChange: ");
                mTaskRecyclerAdapter.getFilter().filter(query);
                return true;
            }
        });
    }

    private String getTaskReport(Task task) {

        return "Task name: \n"
                + task.getTaskName() + "\n"
                + "Description: \n"
                + task.getDescription() + "\n"
                + "Task State: \n"
                + task.getTaskState().toString() + "\n"
                + "Task Date: \n"
                + task.getDateTime() + "\n"
                + "Task Time: \n"
                + task.getTime();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCurrentTabPosition = getArguments().getInt(PAGER_EXTRA);

        if (mCurrentTabPosition == 0) {
            if (RepoUtils.isAdmin(mContext)) {
                setTaskList(RepoUtils.getAllToDoTask(mContext));
            } else {
                setTaskList(RepoUtils.getToDoTask(mContext));
            }
        } else if (mCurrentTabPosition == 1) {
            if (RepoUtils.isAdmin(mContext)) {
                setTaskList(RepoUtils.getAllDoingTask(mContext));
            } else {
                setTaskList(RepoUtils.getDoingTask(mContext));
            }
        } else {
            if (RepoUtils.isAdmin(mContext)) {
                setTaskList(RepoUtils.getAllDoneTask(mContext));
            } else {
                setTaskList(RepoUtils.getDoneTask(mContext));
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(TAG, "onSaveInstanceState: ");
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
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }
}

