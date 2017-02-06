package com.controller;


import com.model.Msg;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

class CellRendererChatList implements Callback<ListView<Msg>, ListCell<Msg>> {

    @Override
    public ListCell<Msg> call(ListView<Msg> param) {

        ListCell<Msg> cell = new ListCell<Msg>() {

            @Override
            protected void updateItem(Msg msg, boolean bln) {
                super.updateItem(msg, bln);
                setGraphic(null);
                setText(null);

                if (msg != null) {
                    HBox hBox = new HBox();

                    Text msgText = new Text(msg.getMsg());
                    ImageView pictureImageView = new ImageView();

                    String usr = "";
                    if (msg.getName().contains("@")) {
                        usr = msg.getName().substring(0, msg.getName().indexOf("@"));
                    } else {
                        usr = msg.getName();
                    }

                    Image image = new Image(getClass().getClassLoader().getResource("pictures/" + usr + ".png").toString(), 30, 30, true, true);

                    pictureImageView.setImage(image);

                    hBox.getChildren().addAll(pictureImageView, msgText);
                    hBox.setAlignment(Pos.CENTER_LEFT);

                    setGraphic(hBox);
                }
            }
        };
        return cell;
    }
}