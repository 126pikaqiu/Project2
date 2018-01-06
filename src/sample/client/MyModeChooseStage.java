package sample.client;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MyModeChooseStage extends Stage  {
    MyModeChooseStage(Main app){
        Button btBack = new Button("back");
        ImageView igvLiuBang = new ImageView("file:image/grass/LiuBang.jpg");
        ImageView igvBaiBan= new ImageView("file:image/story/Baiban.png");
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), event -> {
            if(igvBaiBan.getY() < 693)
                igvBaiBan.setY(igvBaiBan.getY() + 10);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        Button btSand = new Button("Sandbox Mode");
        btSand.getStyleClass().add("my-button");
        Button btStory = new Button("Story Mode");
        btStory.getStyleClass().add("my-button");
        btBack.getStyleClass().add("my-button");
        btSand.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30));
        btStory.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30));
        btBack.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30));
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(btSand, btStory, btBack);
        vbox.setAlignment(Pos.CENTER);
        BorderPane pane = new BorderPane();
        pane.getChildren().add(new ImageView(new Image("file:image/Choose.png")));
        pane.setBottom(vbox);
        pane.getChildren().add(new MediaView(app.mediaPlayerModeChoo));
        app.mediaPlayerModeChoo.setCycleCount(100);
        app.mediaPlayerModeChoo.play();
        btBack.setOnMouseClicked(event -> {
            app.chooseStage.hide();
            app.mediaPlayerModeChoo.pause();
            app.welcomeStage.show();
            app.mediaPlayerWelcom.play();
            app.timelineMoveMonster.play();
        });
        btSand.setOnMouseClicked(event -> {
            app.status = 0;
            app.chooseStage.hide();
            app.passChoose = app.getPassChooseStage();

        });
        btStory.setOnMouseClicked((MouseEvent event) -> {
            igvBaiBan.setY(50);
            app.btOk.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30));
            app.chooseStage.hide();
            app.mediaPlayerModeChoo.pause();
            app.mdStory.play();
            Text text = new Text(app.server.storyInformation());
            text.setX(80);
            text.setY(60);
            Pane pane2 = new Pane();
            pane2.getChildren().add(text);
            VBox vBox = new VBox();
            vBox.getChildren().addAll(igvLiuBang,app. btOk);
            HBox hBox = new HBox(70);
            hBox.getChildren().addAll(pane2, vBox);
            vBox.setAlignment(Pos.CENTER_LEFT);
            Pane pane1 = new Pane();
            pane1.getChildren().addAll(hBox, igvBaiBan);
            app.tempStage = new Stage();
            app.tempStage.setScene(new Scene(pane1, 1200, 800));
            app.tempStage.setTitle("background story");
            app.tempStage.show();
            timeline.play();
            app.btOk.setOnMouseClicked(event1 -> {
                app.mdStory.pause();
                timeline.pause();
                app.tempStage.hide();
                app.status = 1;
                app.stage = app.storyStage(1);
            });
        });
        Scene scene = new Scene(pane, 1200, 730);
        scene.getStylesheets().add("file:res/css/style.css");
        this.setScene(scene);
        this.setResizable(false);
        this.show();
        this.setTitle("Mode Choose");
    }
}
