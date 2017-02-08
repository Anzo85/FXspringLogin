package com.controller;

import com.model.Msg;
import com.model.User;
import com.model.UserService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class LoginController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onLogin(ActionEvent actionEvent) throws Exception {

        User user = new User();



    }


}
