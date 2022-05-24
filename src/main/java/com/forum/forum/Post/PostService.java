package com.forum.forum.Post;

import com.forum.forum.Bot.Bot;
import com.forum.forum.Category.Category;
import com.forum.forum.Category.CategoryService;
import com.forum.forum.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Сервис работы с энтити поста Post.class, в основном транзакционного доступа к БД.
 * Зависит от сервиса категорий и имплементаций Jpa репозитория и Paging репозитория.
 * Используется контроллером NewsController.
 */


@Service
public class PostService {
    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final CategoryService categoryService;

    @Autowired
    private final PostPagingRepository postPagingRepository;

    @Autowired
    private final UserService userService;

    @Autowired
    Bot bot;

    public PostService(PostRepository postRepository, CategoryService categoryService, PostPagingRepository postPagingRepository, UserService userService) {
        this.postRepository = postRepository;
        this.categoryService = categoryService;
        this.postPagingRepository = postPagingRepository;
        this.userService = userService;
    }

    /**
     * Метод добавления нового поста.
     * Принимает аргументы @text и @cats, для будущих полей text и categoriesList соответственно, переменной @post.
     */
    @Transactional
    public void addPost(String text, String cats, String principal) {
        ArrayList<Category> categories = formCategories(cats);          //Формирование будущих категорий поста.

        Post post = new Post();                                         //Создание объекта поста и последующее
        post.setText(text);                                             //его наполнение.
        post.setCategoriesList(categories);
        post.setPosterName(principal);

        post = postRepository.save(post);
        postRepository.flush();

        userService.addPostToUserPosts(post, principal);

        bot.sendMsg("\"" + post.getText() + "\"" +              //Отправка сообщения подписчикам рассылки Вк.
                " was posted by " + post.getPosterName());
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = Optional.of(postRepository.getById(postId))
                .orElseThrow(() -> new IllegalStateException(
                        "\nFailed to find post with id=" + postId + "to then delete it"
                ));

        userService.deletePostFromUserByUserName(post.getPosterName(), post);

        postRepository.delete(post);
        postRepository.flush();

    }

    /**
     * Метод обновления поста.
     * Принимает аргументы @text и @cats, для будущих полей text и categoriesList соответственно, переменной @post.
     * Также принимает аргумент id поста для которого требуется произвести изменения.
     */
    @Transactional
    public void updatePost(String text, String cats, Long postId) {
        ArrayList<Category> categories = formCategories(cats);          //Формирование будущих категорий поста.

        Post post = Optional.of(postRepository.getById(postId))         //Поиск поста для изменения по id.
                .orElseThrow(() -> new IllegalStateException(
                        "\nFailed to update post with postId=" + postId
                ));
        post.setText(text);                                             //Установка атрибутов найденного поста.
        post.setCategoriesList(categories);

        postRepository.save(post);                                      //Сохранение в БД.
        postRepository.flush();                                         //Отправляет накопленные изменения в БД.
    }

    /**
     * Функция формирования категорий.
     * Принимает аргумент @cats имён категорий для последующего поиска по ним или создания новых категорий.
     * Возвращает связный список объектов категорий.
     */
    public ArrayList<Category> formCategories(String cats) {
        ArrayList<String> catsNames = new ArrayList<>();                //Список названий категорий.
        if (cats.split(" ").length > 0) {                         //В случае более двух категорий,
                                                                        //разделённых пробелом.
            catsNames = Arrays.stream(cats.split(" "))            //Разделение категорий по пробелу,
                    .collect(Collectors.toCollection(ArrayList::new));  //сборка в связный список.
        } else {
            catsNames.add(cats.trim());                                 //В случае одной категории. Удаление блуждающих
                                                                        //символов пробела.
        }
        return catsNames                                                //Сборка списка категорий из
                .stream()
                .map(categoryService::checkAddCategoryReturnId)         //возможно прежде несуществующих, но теперь
                .map(categoryService::getCategoryById)                  //добавленных, и существующих прежде категорий.
                .collect(Collectors.toCollection(ArrayList::new));      //Сборка в связный список.
    }

    /**
     * Метод получения требуемого разреза всех постов новостей пользователя.
     * Принимает аргументы @page, номер страницы, из него формируется список постов
     *                     @username для фильтрации из всех постов, только постов
     *                               требуемого пользователя.
     * Возвращает формирующийся по заданным параметрам список постов пользователя.
     */
    @Transactional
    public List<Post> getAllValidNewsOfUserSliced(Integer page, String username) {
        List<Post> results = postRepository.findAll()
                .stream()
                .filter(post -> Objects.equals(post.getPosterName(), username))
                .toList();

        return postSlicer(page, results);
    }

    /**
     * Метод получения требуемого разреза всех постов новостей категории.
     * Принимает аргументы @page, номер страницы, из него формируется список постов
     *                     @categoryName для фильтрации из всех постов, только постов
     *                               требуемоq категории.
     * Возвращает сформированный по заданным параметрам список постов категории.
     */
    @Transactional
    public List<Post> getAllValidNewsOfCategorySliced(Integer page, String categoryName) {
        List<Post> results = postRepository.findAll()
                .stream()
                .filter(post -> {
                    for (Category cata: post.getCategoriesList()) {
                        if (cata.getCategoryName().equals(categoryName)) return true;
                    }
                    return false;
                })
                .toList();

        return postSlicer(page, results);
    }

    /**
     * Метод получения требуемого разреза из всех постов.
     * Принимает аргумент @page, номер страницы, по которому будет производиться разрез.
     * Возвращает сформированный по заданному параметру список постов.
     */
    @Transactional
    public List<Post> getAllValidNewsSliced(Integer page) {
        Pageable paging = PageRequest.of(page, 10);                     //Возвращает разрез размера 10 для
                                                                            // заданной страницы.
        Slice<Post> slicedResults = postPagingRepository.findAll(paging);

        if (slicedResults.hasContent()) {                                   //В случае существования постов возвращает
            return slicedResults.getContent();                              //их.
        } else {                                                            //Иначе возвращает
            return new ArrayList<Post>();                                   //пустой связный список.
        }
    }

    /**
     * Функция получения разреза постов.
     * Принимает аргументы @page, номер страницы для разреза
     *                     @toSlice, список статей, по которым производить разрез.
     * Возвращает список объектов Post, размера 10.
     */
    public List<Post> postSlicer(Integer page, List<Post> toSlice) {
        double pageCount = toSlice.size() / 10D;                                                //Общее количество
                                                                                                //страниц.
        if (pageCount <= 1D) {                                                                  //Для первой страницы.
            return toSlice;
        } else if (page < pageCount && pageCount - page < 1D) {                                 //Для Последней
            int subListStartPoint = page * 10;                                                  //страницы.
            int subListEndPoint = (int)((pageCount - (int)pageCount) * 10) + subListStartPoint;

            return new ArrayList<Post>(toSlice.subList(subListStartPoint, subListEndPoint));
        } else if (page < pageCount) {                                                          //Для всех страниц
            int subListStartPoint = page * 10;                                                  //между 1 и последней.
            int subListEndPoint = subListStartPoint + 10;

            return new ArrayList<Post>(toSlice.subList(subListStartPoint, subListEndPoint));
        } else {                                                                                //Иначе пустой связный
            return new ArrayList<Post>();                                                       //список.
        }

    }
}
