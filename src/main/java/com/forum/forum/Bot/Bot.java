package com.forum.forum.Bot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.*;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Bot implements Runnable {

    @Autowired
    private final SubscriberService subscriberService;

    public Bot(SubscriberService subscriberService) { this.subscriberService = subscriberService; }

    public void sendMsg(String postText) {
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);
        Random random = new Random();
        Keyboard keyboard = new Keyboard();
        GroupActor actor = new GroupActor(213359556, "c41e23e071c1f51b112c60bfac9f5fd216ace88a942fed02ff7d8f1a655900b57b042d6015d3b12df27e2");
        Integer ts = null;
        try {
            ts = vk.messages().getLongPollServer(actor).execute().getTs();
        } catch (ApiException | ClientException e) {
            throw new RuntimeException(e);
        }

        subscriberService.getAllSubscribers()
                .stream()
                .forEach(subscriber -> {
                    try {
                        vk.messages().send(actor).message(postText).userId(Math.toIntExact(subscriber)).randomId(random.nextInt(10000)).execute();
                    } catch (ApiException | ClientException e) {
                        throw new RuntimeException(e);
                    }
                });

    }

    @Override
    public void run() {
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);
        Random random = new Random();
        Keyboard keyboard = new Keyboard();

        List<List<KeyboardButton>> allKey = new ArrayList<>();
        List<KeyboardButton> line1 = new ArrayList<>();
        line1.add(new KeyboardButton().setAction(new KeyboardButtonAction().setLabel("Привет").setType(TemplateActionTypeNames.TEXT)).setColor(KeyboardButtonColor.POSITIVE));
        line1.add(new KeyboardButton().setAction(new KeyboardButtonAction().setLabel("Подписаться").setType(TemplateActionTypeNames.TEXT)).setColor(KeyboardButtonColor.POSITIVE));
        line1.add(new KeyboardButton().setAction(new KeyboardButtonAction().setLabel("Отписаться").setType(TemplateActionTypeNames.TEXT)).setColor(KeyboardButtonColor.POSITIVE));
        allKey.add(line1);
        keyboard.setButtons(allKey);
        GroupActor actor = new GroupActor(213359556, "c41e23e071c1f51b112c60bfac9f5fd216ace88a942fed02ff7d8f1a655900b57b042d6015d3b12df27e2");
        Integer ts = null;
        try {
            ts = vk.messages().getLongPollServer(actor).execute().getTs();
        } catch (ApiException | ClientException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            MessagesGetLongPollHistoryQuery historyQuery = vk.messages().getLongPollHistory(actor).ts(ts);
            List<Message> messages = null;
            try {
                messages = historyQuery.execute().getMessages().getItems();
            } catch (ApiException | ClientException e) {
                throw new RuntimeException(e);
            }
            if (!messages.isEmpty()) {
                messages.forEach(message -> {
                    try {
                        if (message.getText().equals("Привет")) {
                            vk.messages().send(actor).message("Привет!").userId(message.getFromId()).randomId(random.nextInt(10000)).execute();
                        } else if (message.getText().equals("Подписаться")) {
                            String msg = subscriberService.addSubscriber(message.getFromId()) ? "Подписка успешно оформлена!" : "Вы уже подписаны";
                            vk.messages().send(actor).message(msg).userId(message.getFromId()).randomId(random.nextInt(10000)).execute();
                        } else if (message.getText().equals("Отписаться")) {
                            String msg = subscriberService.removeSubscriber(message.getFromId()) ? "Подписка успешно отменена!" : "Вы ещё не подписаны";
                            vk.messages().send(actor).message(msg).userId(message.getFromId()).randomId(random.nextInt(10000)).execute();
                        } else if (message.getText().equals("Кнопки")) {
                            vk.messages().send(actor).message("Кнопки").userId(message.getFromId()).randomId(random.nextInt(10000)).keyboard(keyboard).execute();
                        } else {
                            vk.messages().send(actor).message("Я тебя не понял.").userId(message.getFromId()).randomId(random.nextInt(10000)).execute();
                        }
                    }
                    catch (ApiException | ClientException e) { e.printStackTrace(); }
                });
            }
            try {
                ts = vk.messages().getLongPollServer(actor).execute().getTs();
            } catch (ApiException | ClientException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}