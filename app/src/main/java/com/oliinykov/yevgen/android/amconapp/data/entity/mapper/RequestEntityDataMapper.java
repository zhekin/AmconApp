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

package com.oliinykov.yevgen.android.amconapp.data.entity.mapper;

import com.oliinykov.yevgen.android.amconapp.data.entity.RequestEntity;
import com.oliinykov.yevgen.android.amconapp.domain.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class used to transform {@link RequestEntity} in the data layer to {@link Request} in
 * the domain layer.
 */

public class RequestEntityDataMapper {

    /**
     * Transform a {@link RequestEntity} into an {@link Request}.
     *
     * @param requestEntity entity to be transformed.
     * @return {@link Request} if valid, otherwise {@code null}.
     */
    public Request transform(RequestEntity requestEntity) {
        Request request = null;
        if (requestEntity != null) {
            request = new Request(requestEntity.getId());
            request.setTitle(requestEntity.getTitle());
            request.setHash(requestEntity.getHash());
            request.setCreated(requestEntity.getCreated());
            request.setRegistered(requestEntity.getRegistered());
            request.setSolveUntil(requestEntity.getSolveUntil());
            request.setResponsible(requestEntity.getResponsible());
            request.setDescription(requestEntity.getDescription());
            request.setEstimation(requestEntity.getEstimation());
            request.setLikes(requestEntity.getLikes());
            request.setStatus(requestEntity.getStatus());
            request.setImages(requestEntity.getImages());
        }
        return request;
    }

    /**
     * Transform a List of {@link RequestEntity} into a List of {@link Request}.
     *
     * @param requestEntityList list of {@link RequestEntity} to be transformed.
     * @return list of {@link Request} if source list, otherwise {@code null}.
     */
    public List<Request> transform(List<RequestEntity> requestEntityList) {
        List<Request> requestList = new ArrayList<>();
        Request request;
        for (RequestEntity requestEntity : requestEntityList) {
            request = transform(requestEntity);
            if (request != null) {
                requestList.add(request);
            }
        }

        return requestList;
    }
}
