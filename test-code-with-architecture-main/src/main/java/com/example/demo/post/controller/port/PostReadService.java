package com.example.demo.post.controller.port;

import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostUpdate;

public interface PostReadService {
    Post getById(long id);

    Post update(long id, PostUpdate postUpdate);
}
