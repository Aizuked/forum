package com.forum.forum.Post;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Paging репозиторий доступа к PostgreSQL. Используются наследуемые у PagingAndSortingRepository<T, ID> методы.
 */


public interface PostPagingRepository
        extends PagingAndSortingRepository<Post, Long> {
}