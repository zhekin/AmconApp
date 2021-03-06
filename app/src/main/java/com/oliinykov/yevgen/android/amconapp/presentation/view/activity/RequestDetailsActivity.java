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

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.oliinykov.yevgen.android.amconapp.R;
import com.oliinykov.yevgen.android.amconapp.data.RequestDataRepository;
import com.oliinykov.yevgen.android.amconapp.data.ResourceManager;
import com.oliinykov.yevgen.android.amconapp.data.entity.mapper.RequestEntityDataMapper;
import com.oliinykov.yevgen.android.amconapp.domain.executor.InteractorExecutor;
import com.oliinykov.yevgen.android.amconapp.domain.executor.MainThread;
import com.oliinykov.yevgen.android.amconapp.domain.interactor.GetRequestDetails;
import com.oliinykov.yevgen.android.amconapp.domain.repository.RequestRepository;
import com.oliinykov.yevgen.android.amconapp.presentation.AmconApp;
import com.oliinykov.yevgen.android.amconapp.presentation.model.RequestModel;
import com.oliinykov.yevgen.android.amconapp.presentation.model.mapper.RequestModelDataMapper;
import com.oliinykov.yevgen.android.amconapp.presentation.presenter.RequestDetailsPresenter;
import com.oliinykov.yevgen.android.amconapp.presentation.view.RequestDetailsView;
import com.oliinykov.yevgen.android.amconapp.presentation.view.adapter.RequestImagesAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Activity that shows request details.
 */

public class RequestDetailsActivity extends AppCompatActivity implements RequestDetailsView {

    private static final String INTENT_PARAM_REQUEST_ID =
            "com.oliinykov.yevegn.INTENT_PARAM_REQUEST_ID";

    @BindView(R.id.request_title) TextView mTitle;
    @BindView(R.id.request_status) TextView mStatus;
    @BindView(R.id.request_created) TextView mCreated;
    @BindView(R.id.request_registered) TextView mRegistered;
    @BindView(R.id.request_solve_until) TextView mSolveUntil;
    @BindView(R.id.request_responsible) TextView mResponsible;
    @BindView(R.id.request_description) TextView mDescription;
    @BindView(R.id.recview_request_imgs) RecyclerView mRecyclerView;
    private ActionBar mActionBar;
    private RequestImagesAdapter mRequestImagesAdapter;
    private RequestDetailsPresenter mPresenter;

    public static Intent getCallingIntent(Context context, long requestId) {
        Intent callingIntent = new Intent(context, RequestDetailsActivity.class);
        callingIntent.putExtra(INTENT_PARAM_REQUEST_ID, requestId);
        return callingIntent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_request_details);
        ButterKnife.bind(this);
        setupActionBar();
        setupRecyclerView();
        initPresenter();
        mPresenter.getRequest();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
        mRequestImagesAdapter.updateData(null);
    }

    private void setupActionBar() {
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupRecyclerView() {
        mRequestImagesAdapter = new RequestImagesAdapter(getApplicationContext());
        mRecyclerView.setAdapter(mRequestImagesAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
        );
    }

    @Override
    public void renderRequest(RequestModel requestModel) {
        mActionBar.setTitle(requestModel.getHash());
        mTitle.setText(requestModel.getTitle());
        mStatus.setText(getString(requestModel.getStatus().getTitleResId()));
        mCreated.setText(requestModel.getCreated());
        mRegistered.setText(requestModel.getRegistered());
        mSolveUntil.setText(requestModel.getSolveUntil());
        mResponsible.setText(requestModel.getResponsible());
        mDescription.setText(requestModel.getDescription());
        mRequestImagesAdapter.updateData(requestModel.getImages());
    }

    @OnClick({R.id.request_title, R.id.request_status, R.id.request_created,
            R.id.request_registered, R.id.request_solve_until, R.id.request_responsible,
            R.id.request_description
    })
    void onControlClick(View view) {
        String name = getResources().getResourceEntryName(view.getId());
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }

    private void initPresenter() {
        MainThread mainThread = AmconApp.getMainThread(this);
        InteractorExecutor interactorExecutor = AmconApp.getInteractorExecutor(this);
        RequestRepository requestRepository = new RequestDataRepository(
                new ResourceManager(getApplicationContext()),
                new RequestEntityDataMapper(),
                getApplicationContext()
        );
        GetRequestDetails interactor = new GetRequestDetails(
                interactorExecutor,
                mainThread,
                requestRepository,
                getIntent().getLongExtra(INTENT_PARAM_REQUEST_ID, -1)
        );
        mPresenter = new RequestDetailsPresenter(this, interactor, new RequestModelDataMapper());
    }
}
