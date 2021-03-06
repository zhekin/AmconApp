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

package com.oliinykov.yevgen.android.amconapp.presentation.model;

import com.oliinykov.yevgen.android.amconapp.R;
import com.oliinykov.yevgen.android.amconapp.presentation.view.AllRequestsView;

/**
 * Represents possible conditions of
 * {@link com.oliinykov.yevgen.android.amconapp.presentation.model.RequestModel} status and holds
 * information about all ViewPager's pages in {@link AllRequestsView}.
 */

public enum RequestStatus {


    WAITING(R.string.tab_waiting, R.layout.view_listview),
    INWORK(R.string.tab_in_work, R.layout.view_recycleview),
    DONE(R.string.tab_done, R.layout.view_recycleview);


    private int mTitleResId;
    private int mLayoutResId;

    RequestStatus(int titleResId, int layoutResId) {
        this.mTitleResId = titleResId;
        this.mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}
