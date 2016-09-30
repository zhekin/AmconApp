/*
 * Copyright (c) 2016 Yevgen Oliinykov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.oliinykov.yevgen.android.amconapp.presentation.view.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.oliinykov.yevgen.android.amconapp.R;
import com.oliinykov.yevgen.android.amconapp.data.RequestDataRepository;
import com.oliinykov.yevgen.android.amconapp.data.ResourceManager;
import com.oliinykov.yevgen.android.amconapp.data.entity.RequestEntity;
import com.oliinykov.yevgen.android.amconapp.data.entity.mapper.JsonSerializer;
import com.oliinykov.yevgen.android.amconapp.data.entity.mapper.RequestEntityDataMapper;
import com.oliinykov.yevgen.android.amconapp.domain.executor.InteractorExecutor;
import com.oliinykov.yevgen.android.amconapp.domain.executor.MainThread;
import com.oliinykov.yevgen.android.amconapp.domain.interactor.GetAllRequests;
import com.oliinykov.yevgen.android.amconapp.domain.repository.RequestRepository;
import com.oliinykov.yevgen.android.amconapp.presentation.AmconApp;
import com.oliinykov.yevgen.android.amconapp.presentation.model.RequestModel;
import com.oliinykov.yevgen.android.amconapp.presentation.model.mapper.RequestModelDataMapper;
import com.oliinykov.yevgen.android.amconapp.presentation.presenter.AllRequestsPresenter;
import com.oliinykov.yevgen.android.amconapp.presentation.view.AllRequestsView;
import com.oliinykov.yevgen.android.amconapp.presentation.view.adapter.RequestsPagerAdapter;
import com.oliinykov.yevgen.android.amconapp.presentation.view.adapter.RequestsRecyclerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity that shows all requests as lists in three separate tabs.
 */
public class RequestsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AllRequestsView {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.fab) FloatingActionButton mFab;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawer;
    @BindView(R.id.nav_view) NavigationView mNavigationView;
    @BindView(R.id.tab_layout) TabLayout mTabs;
    @BindView(R.id.viewpager) ViewPager mViewPager;
    RecyclerView mWaitingRequests;

    private AllRequestsPresenter mPresenter;
    private RequestsRecyclerAdapter mRequestsAdapter;
    private RequestsPagerAdapter mRequestsPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        ButterKnife.bind(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar(mToolbar);
        setupNavDrawer();
        setupTabs();
        setupFab();
        initPresenter();
        mPresenter.getAllRequests();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.requests, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_all_requests) {

        } else if (id == R.id.nav_requests_on_map) {

        } else if (id == R.id.nav_login) {

        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupNavDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                mDrawer,
                mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private void setupTabs() {
        mRequestsPagerAdapter = new RequestsPagerAdapter(getApplicationContext());
        mViewPager.setAdapter(mRequestsPagerAdapter);
        mTabs.setupWithViewPager(mViewPager);
        Log.v(RequestsActivity.class.getSimpleName(), "Children: " + mRequestsPagerAdapter
                .getCount());
//        for (RequestsPagerEnum requestsPagerEnum : RequestsPagerEnum.values()) {
//            switch (requestsPagerEnum) {
//                case INWORK: {
//                    View view = mTabs.getChildAt(1);
//                    mWaitingRequests = (RecyclerView) view.findViewById(R.id
//                            .recview_requests_in_work);
//                    mWaitingRequests.setLayoutManager(
//                            new LinearLayoutManager(getApplicationContext())
//                    );
//                    mRequestsAdapter = new RequestsRecyclerAdapter(getApplicationContext());
//                    mWaitingRequests.setAdapter(mRequestsAdapter);
//                }
//            }
//        }


    }

    private void setupFab() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.action_add_new_request),
                        Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    @Override
    public void renderRequests(List<RequestModel> requests) {
        Log.v(RequestsActivity.class.getSimpleName(), "Children " + mViewPager.getChildCount());
        Log.v(RequestsActivity.class.getSimpleName(), "Requests " + requests.toString());
        mRequestsPagerAdapter.setRequestsList(requests);
    }

    private void initPresenter() {
        MainThread mainThread = AmconApp.getMainThread(this);
        InteractorExecutor interactorExecutor = AmconApp.getInteractorExecutor(this);
        RequestRepository requestRepository = new RequestDataRepository(
                new ResourceManager(getApplicationContext()),
                new JsonSerializer<RequestEntity>(),
                new RequestEntityDataMapper()
        );
        GetAllRequests interactor = new GetAllRequests(
                interactorExecutor,
                mainThread,
                requestRepository
        );
        mPresenter = new AllRequestsPresenter(this, interactor, new RequestModelDataMapper());
    }
}
