package com.ducpham.wecarepeople.model;

import org.parceler.Parcel;

import java.util.Date;

@Parcel
public class Message {
    String messageId;
    String sender;
    String receiver;
    String message;
    Date date;

    public Message(){

    }

    public Message(String messageId,String sender,String receiver,String message, Date date){
        this.messageId = messageId;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.date = date;
    }

    public Message(String sender,String receiver,String message,Date date){
         this(null,sender,receiver,message,date);
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
