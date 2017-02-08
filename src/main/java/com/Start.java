package com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");
        Parent root = FXMLLoader.load(getClass().getResource("/forms/login.fxml"));
        stage.setTitle("");
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("/styles/list_view_users.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
