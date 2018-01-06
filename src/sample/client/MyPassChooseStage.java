package sample.client;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MyPassChooseStage extends Stage {
    MyPassChooseStage(Main app){
        Button btPass1 = new Button("Pass 1");
        Button btPass2= new Button("Pass 2");
        Button btPass3 = new Button("Pass 3");
        Button btPass4 = new Button("Pass 4");
        Button btBack = new Button("back");
        btPass1.getStyleClass().add("my-button");
        btPass3.getStyleClass().add("my-button");
        btPass2.getStyleClass().add("my-button");
        btPass4.getStyleClass().add("my-button");
        btBack.getStyleClass().add("my-button");
        btPass1.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30));
        btPass2.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30));
        btPass3.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30));
        btPass4.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30));
        btBack.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30));
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(btPass1, btPass2, btPass3, btPass4, btBack);
        vbox.setAlignment(Pos.CENTER);
        BorderPane pane = new BorderPane();
        pane.getChildren().add(new ImageView(new Image("file:image/Choose.png")));
        pane.setBottom(vbox);
        btPass1.setOnMouseClicked(event -> {
            app.status = -1;
            app.passChoose.hide();
            app.mediaPlayerModeChoo.pause();
            app.server.refresh(app.status);
            app.stage = app.getPass1Stage();
            app.createAndShowGui();
            app.stage.show();
            app.mdBGM.play();
            new Alert(Alert.AlertType.INFORMATION, app.server.help()).showAndWait();
        });
        btPass2.setOnMouseClicked(event -> {
            app.status = -2;
            app.passChoose.hide();
            app.mediaPlayerModeChoo.pause();
            app.stage = app.getPass2Stage();
            app.stage.show();
            app.mdBGM.play();
            new Alert(Alert.AlertType.INFORMATION, app.server.help()).showAndWait();
        });
        btPass3.setOnMouseClicked(event -> {
            app.status = -3;
            app.passChoose.hide();
            app.mediaPlayerModeChoo.pause();
            app.stage = app.getPass3Stage();
            app.stage.show();
            app.mdBGM.play();
            new Alert(Alert.AlertType.INFORMATION, app.server.help()).showAndWait();
        });
        btPass4.setOnMouseClicked(event -> {
            app.status = -4;
            app.passChoose.hide();
            app.mediaPlayerModeChoo.pause();
            app.stage = app.getPass4Stage();
            app.timelineMoveMonster.play();
            app.stage.show();
            app.mdBGM.play();
            new Alert(Alert.AlertType.INFORMATION, app.server.help()).showAndWait();
        });
        btBack.setOnMouseClicked(event -> {
            app.passChoose.hide();
            app.chooseStage.show();
        });
        Scene scene = new Scene(pane, 1200, 730);
        scene.getStylesheets().add("file:res/css/style.css");
        this.setScene(scene);
        this.setResizable(false);
        this.show();
        this.setTitle("Mode Choose");
    }
}
