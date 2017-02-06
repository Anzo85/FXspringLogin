package com.jabber;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

public class JabberTest implements ChatManagerListener, ChatMessageListener {

    private static String username = "admin";
    private static String password = "admin";

    public void chat(String to, AbstractXMPPConnection connection) throws SmackException.NotConnectedException {
        ChatManager chatmanager = ChatManager.getInstanceFor(connection);
        while (connection.isConnected()) {
            chatmanager.addChatListener(this);
        }
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        System.out.println(chat.getParticipant() + " -> " + message.getBody().toUpperCase());
    }

    @Override
    public void chatCreated(Chat chat, boolean createdLocally) {
        if (!createdLocally) {
            chat.addMessageListener(this);
        }
    }


    public static void main(String[] args) throws Exception {
        JabberTest jabberTest = new JabberTest();
        JabberManager jabberManager = new JabberManagerImpl();
        AbstractXMPPConnection connection = jabberManager.performConnect(username, password);
        connection.login();
        jabberManager.addUserToRoster("test","ContactList",connection);
       // jabberManager.sendMsg("admin", "ku-ku :)", connection);
    }
}
