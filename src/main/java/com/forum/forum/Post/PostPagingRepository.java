package com.forum.forum.Post;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostPagingRepository
        extends PagingAndSortingRepository<Post, Long> {
}