package com.example.telegram;

import jdk.dynalink.linker.LinkerServices;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class TelegramBot extends TelegramLongPollingBot {
    List<TelegramUser> users = new ArrayList<>();

    @Override
    public void onUpdateReceived(Update update) {
         System.out.println(update.getMessage().getText());
         System.out.println(update.getMessage().getFrom().getFirstName());
        if(update.hasMessage()){
            Message message = update.getMessage();
            String chatId = message.getChatId().toString();
            TelegramUser user = saveUser(chatId);
            if (message.hasText()){
                String text =message.getText();
                if (text.equals("/list")){
                    System.out.println(users);
                }
                if (text.equals("/start")){
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Merhaba, Cemile'nin botuna Hoş Geldiniz!\n" +
                            "Lütfen Seçiniz\n" +
                            "1- \n"+
                            "2- ");
                    sendMessage.setChatId(chatId);
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    user.setStep(BotConstant.ENTER_NAME);
                } else if (user.getStep().equals(BotConstant.ENTER_NAME)) {
                    user.setFullName(text



                    );
                } else if (user.getStep().equals(BotConstant.SELECT_LANG)) {
                    if (text.equals("1")){
                        sendText(chatId,"1 i seçtin");
                    } else if (text.equals("2")) {
                        sendText(chatId,"2 i seçtin");
                    }
                    user.setStep(BotConstant.WRITE_MSG);
                } else if (user.getStep().equals(BotConstant.WRITE_MSG)) {
                    user.setMsg(text);
                }
            }
        }
    }


    @Override
    public String getBotUsername() {
        // TODO
        return "CNKtelegramBot";
    }

    @Override
    public String getBotToken() {
        // TODO
        return "5904160373:AAGo7s9C1O7Gmx-BEXOrhci1-X53jD65oak";
    }

    private TelegramUser saveUser(String chatId) {

        for (TelegramUser user:users) {
            if (user.getChatId().equals(chatId)){
                return user;
            }
        }
        TelegramUser user = new TelegramUser();
        user.setChatId(chatId);
        users.add(user);
        return user;
    }
    private void sendText(String chatId, String text){
        SendMessage sendMessage= new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
