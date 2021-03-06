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

package com.oliinykov.yevgen.android.amconapp.data;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Helper class to do operations on resource files.
 */

public class ResourceManager {

    private final Context mContext;

    public ResourceManager(Context context) {
        mContext = context;
    }

    /**
     * Reads a content from a raw resource.
     * This is an I/O operation and this method executes in the main thread, so it is recommended to
     * perform the operation using another thread.
     *
     * @param id the id of raw resource.
     * @return a string representation of file content
     * @throws Resources.NotFoundException
     */
    public String readRawResource(int id) throws Resources.NotFoundException {
        StringBuilder contentBuilder = new StringBuilder();
        InputStream inputStream = mContext.getResources().openRawResource(id);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return contentBuilder.toString();
    }
}
