package com.forum.forum.Bot;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscribersRepository extends JpaRepository<Subscriber, Long> {
}
