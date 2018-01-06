package sample.client;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MySignStage extends Stage {
    MySignStage(Main app){
        TextField tfAccount = new TextField();
        PasswordField tfPassw = new PasswordField();
        Label lb1 = new Label("Name", tfAccount);
        Label lb2 = new Label("Password", tfPassw);
        Button btSign = new Button("Sign in");
        Button btBack = new Button("back");
        Button btOk = new Button("OK");
        Label message = new Label("");
        MediaView mediaView = new MediaView(app.mediaPlayerWelcom);
        VBox vBox = new VBox(30);
        HBox hBox = new HBox(2);
        lb1.getStyleClass().add("my-button");
        lb2.getStyleClass().add("my-button");
        btOk.setPrefHeight(90);
        btOk.setPrefWidth(170);
        btBack.setPrefHeight(90);
        btBack.setPrefWidth(170);
        btSign.setPrefHeight(90);
        btSign.setPrefWidth(170);
        btOk.getStyleClass().add("my-button");
        btSign.getStyleClass().add("my-button");
        btBack.getStyleClass().add("my-button");
        if(!app.isSign)
            hBox.getChildren().addAll(btOk,btSign);
        else
        {
            hBox.getChildren().addAll(btOk, btBack);
        }
        tfAccount.setPrefHeight(60);
        tfPassw.setPrefHeight(60);
        lb1.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30));
        lb1.setPrefHeight(60);
        lb2.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30));
        lb2.setPrefHeight(60);
        btOk.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30));
        btBack.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30));
        btSign.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30));
        lb1.setContentDisplay(ContentDisplay.RIGHT);
        lb2.setContentDisplay(ContentDisplay.RIGHT);
        vBox.getChildren().addAll(lb1, lb2, message, hBox);
        Pane pane = new Pane();
        pane.getChildren().addAll(mediaView, vBox);
        vBox.setLayoutX(470);
        vBox.setLayoutY(350);
        btOk.setOnMouseClicked(event -> {
            if(!app.isSign){
                app.server.checkLoad(tfAccount.getText(), tfPassw.getText());
                if(app.server.isLoadIn())
                {
                    app.server.setName(tfAccount.getText());
                    new Alert(Alert.AlertType.INFORMATION, "登录成功").showAndWait();
                    app.stage.hide();
                    app.welcomeStage = new MyWelcomeStage(app);
                }else {
                    message.setText(app.server.checkLoad(tfAccount.getText(), tfPassw.getText()));
                    message.setStyle("-fx-background-color:white");
                    message.setTextFill(Color.rgb(210, 39, 30));
                    tfPassw.clear();
                    tfAccount.clear();
                }
            }else {
                app.server.checkSignIn(tfAccount.getText(), tfPassw.getText());
                if(app.server.isSign())
                {
                    app.server.setName(tfAccount.getText());
                    new Alert(Alert.AlertType.INFORMATION, "注册成功").showAndWait();
                    app.stage.hide();
                    app.welcomeStage = new MyWelcomeStage(app);
                }else {
                    message.setText(app.server.checkSignIn(tfAccount.getText(), tfPassw.getText()));
                    message.setStyle("-fx-background-color:white");
                    message.setTextFill(Color.rgb(210, 39, 30));
                    tfPassw.clear();
                    tfAccount.clear();
                }
            }});
        btBack.setOnMouseClicked(event -> {
            app.isSign = false;
            app.stage.hide();
            app.getloadInStage();
        });
        btSign.setOnMouseClicked(event1 -> {
            app.isSign = true;
            app.stage.hide();
            app.getSignInStage();
        });
        Scene scene = new Scene(pane, 1200, 700);
        scene.getStylesheets().add("file:res/css/style.css");
        app.stage.setScene(scene);
        app.stage.setResizable(false);
        app.stage.show();
    }
}
