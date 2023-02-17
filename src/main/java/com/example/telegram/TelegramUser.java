package com.example.telegram;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelegramUser {
    private String chatId;
    private String step;
    private String msg;
    private String fullName;

    @Override
    public String toString() {
        return "TelegramUser{" +
                "chatId='" + chatId + '\'' +
                ", step='" + step + '\'' +
                ", msg='" + msg + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
