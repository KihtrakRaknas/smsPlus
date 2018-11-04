package com.example.karthik.studentmessagingservicesms;

import java.util.Date;

public class indivMessage {
    public String messageText;
    public String messageUser;
    public String UID;
    public long messageTime;

    public indivMessage(String messageText, String messageUser, String UID) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.UID = UID;

        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public indivMessage(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
