package com.forum.forum.Bot.Subscriber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Сервис работы с энтити пользователя User.class, в основном транзакционного доступа к БД.
 * Зависит от сервиса ролей уровня пользовательского и имплементации Jpa репозитория.
 * Используется Spring Security -> UserDetailsServiceImpl
 *              контроллерами   -> AuthenticationController, IndexController, UsersController
 */


@Service
public class SubscriberService {
    @Autowired
    private final SubscribersRepository subscribersRepository;

    public SubscriberService(SubscribersRepository subscribersRepository) { this.subscribersRepository = subscribersRepository; }

    @Transactional
    public List<Integer> getAllSubscribers() {
        return subscribersRepository.findAll()
                .stream()
                .map(Subscriber::getVk_id)
                .toList();
    }

    @Transactional
    public boolean addSubscriber(int vk_id) {
        List<Subscriber> subscriber = subscribersRepository.findAll()
                .stream()
                .filter(sub -> {
                    if (sub.getVk_id() == vk_id) return true;
                    return false;
                })
                .toList();

        if (subscriber.isEmpty()) {
            Subscriber sub = new Subscriber();
            sub.setVk_id(vk_id);

            subscribersRepository.save(sub);
            subscribersRepository.flush();
            return true;
        }

        return false;
    }

    @Transactional
    public boolean removeSubscriber(int vk_id) {
        List<Subscriber> subscriber = subscribersRepository.findAll()
                .stream()
                .filter(sub -> {
                    if (sub.getVk_id() == vk_id) return true;
                    return false;
                })
                .toList();
        if (!subscriber.isEmpty()) {
            subscribersRepository.delete(subscriber.get(0));
            return true;
        }
        return false;
    }
}
