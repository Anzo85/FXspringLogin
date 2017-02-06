package com.controller;

import com.model.User;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

class CellRendererUserList implements Callback<ListView<User>, ListCell<User>> {
    @Override
    public ListCell<User> call(ListView<User> param) {

        ListCell<User> cell = new ListCell<User>() {

            @Override
            protected void updateItem(User user, boolean bln) {
                super.updateItem(user, bln);
                setGraphic(null);
                setText(null);

                if (user != null) {
                    HBox hBox = new HBox();

                    Text username = new Text(user.getName());
                    ImageView pictureImageView = new ImageView();
                    Image image = new Image(getClass().getResource("/pictures/" + user.getName() + ".png").toString(), 30, 30, true, true);

                    pictureImageView.setImage(image);

                    hBox.getChildren().addAll(pictureImageView, username);
                    hBox.setAlignment(Pos.CENTER_LEFT);

                    setGraphic(hBox);
                }
            }
        };
        return cell;
    }
}