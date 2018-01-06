package sample.client;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MyloadStage extends Stage {
    MyloadStage(Main app){
        Button btExit = new Button("Exit");
        Button btLoad = new Button("Load in");
        Button btLoad1 = new Button("Visitor");
        app.mediaPlayerWelcom.play();
        EventHandler<ActionEvent> handler = event -> {
            app.mediaPlayerWelcom.seek(Duration.ZERO);
            app.mediaPlayerWelcom.play();
        };
        app.timeline = new Timeline(new KeyFrame(Duration.seconds(20), handler));
        app.timeline.setCycleCount(Timeline.INDEFINITE);
        app.timeline.play();
        app.map.getChildren().add(new MediaView(app.mdBGM));
        app.mdBGM.setCycleCount(10000);
        MediaView mediaView = new MediaView(app.mediaPlayerWelcom);
        VBox vBox = new VBox(50);
        btLoad.setPrefWidth(340);
        btLoad.getStyleClass().add("my-button");
        btLoad1.setPrefHeight(90);
        btLoad1.getStyleClass().add("my-button");
        btLoad.setPrefHeight(90);
        btExit.setPrefHeight(90);
        btExit.getStyleClass().add("my-button");
        btLoad1.setPrefWidth(340);
        btExit.setPrefWidth(340);
        btLoad.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30));
        btLoad1.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30));
        btExit.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30));
        vBox.getChildren().addAll(btLoad, btLoad1, btExit);
        Pane pane = new Pane();
        btLoad.setOnMouseClicked(event ->{
            this.hide();
            app.getSignInStage();
        });
        pane.getChildren().addAll(mediaView, vBox);
        vBox.setLayoutX(470);
        vBox.setLayoutY(290);
        btExit.setOnMouseClicked(event -> {
            if(app.status != 0) {
                app.server.saveGame(app.server, app.status);
                new Alert(Alert.AlertType.INFORMATION,"已存档").showAndWait();
            }
            System.exit(1);
        });
        btLoad1.setOnMouseClicked(event -> {
            app.server.setName("visitor");
            this.hide();
            app.welcomeStage = new MyWelcomeStage(app);
        });
        Scene scene = new Scene(pane, 1200, 700);
        scene.getStylesheets().add("file:res/css/style.css");
        this.setScene(scene);
        this.setResizable(false);
        this.show();
    }
}
