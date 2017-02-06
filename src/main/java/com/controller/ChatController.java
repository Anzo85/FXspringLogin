package com.controller;

import com.jabber.JabberManager;
import com.jabber.JabberManagerImpl;
import com.model.Msg;
import com.model.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.roster.RosterEntry;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class ChatController implements Initializable, ChatManagerListener, ChatMessageListener {
    private String username = "admin";
    private String password = "admin";
    private String host = "localhost";
    private int port = 5222;
    private String chosenUser;
    private AbstractXMPPConnection abstractXMPPConnection;
    private JabberManager jabberManager = new JabberManagerImpl(host, port);
    private List<Msg> msgList;
    private List<User> users;
    @FXML
    private TextArea msgInput;
    @FXML
    private ListView userList;
    @FXML
    private ListView chatListView;

    public ChatController() {
        this.msgList = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            abstractXMPPConnection = jabberManager.performConnect(username, password);
            abstractXMPPConnection.login();

            System.out.println("Connection -> " + abstractXMPPConnection.isConnected());

            Set<RosterEntry> rosterEntries = jabberManager.listContacts(abstractXMPPConnection);
            System.out.println(rosterEntries);
            for (RosterEntry rosterEntry : rosterEntries) {
                users.add(new User(rosterEntry.getName(), rosterEntry.getName()));
            }
            Platform.runLater(() -> {
                ObservableList<User> observableList = FXCollections.observableList(users);
                userList.setItems(observableList);
                userList.setCellFactory(new CellRendererUserList());
            });
            ChatManager chatManager = ChatManager.getInstanceFor(abstractXMPPConnection);
            chatManager.addChatListener(this);

        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (SmackException.NotLoggedInException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(ActionEvent actionEvent) throws Exception {
        System.out.println(chosenUser);
        System.out.println(abstractXMPPConnection.isConnected());

        jabberManager.sendMsg(chosenUser, msgInput.getText(), abstractXMPPConnection);
        msgList.add(new Msg(username, msgInput.getText()));
        Platform.runLater(() -> {
            ObservableList<Msg> observableList = FXCollections.observableList(msgList);
            chatListView.setItems(observableList);
            chatListView.setCellFactory(new CellRendererChatList());
        });
    }

    public void onListViewClick(Event event) {
        User selectedItem = (User) userList.getSelectionModel().getSelectedItem();
        chosenUser = selectedItem.getName();
    }

    @Override
    public void chatCreated(Chat chat, boolean createdLocally) {
        if (!createdLocally) {
            chat.addMessageListener(this);
        }
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        msgList.add(new Msg(chat.getParticipant(), message.getBody()));
        Platform.runLater(() -> {
            getNotificationSound();
            getNotification(message.getBody(), chat.getParticipant());
            ObservableList<Msg> observableList = FXCollections.observableList(msgList);
            chatListView.setItems(observableList);
            chatListView.setCellFactory(new CellRendererChatList());
        });
    }

    private void getNotificationSound() {
        try {
            Media sound = new Media(getClass().getResource("/sounds/notification.wav").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getNotification(String msg, String username) {
        try {
            Image image = new Image("/pictures/" + new String(username.substring(0, username.indexOf("@"))) + ".png");
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(70);
            imageView.setFitWidth(70);
            Notifications notifications = Notifications.create()
                    .text(msg)
                    .graphic(imageView)
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.TOP_RIGHT);
            notifications.show();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private void getDataFromProperties() {

    }
}
