package sample.client;

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
import sample.server.Server;

class MyWelcomeStage extends Stage {
    MyWelcomeStage(Main app){
        app.map.getChildren().add(new MediaView(app.mdBGM));
        MediaView mediaView = new MediaView(app.mediaPlayerWelcom);
        VBox vBox = new VBox(50);
        Button btStartG = new Button("Play");
        btStartG.setPrefWidth(340);
        btStartG.getStyleClass().add("my-button");
        Button btContinueG = new Button("Continue");
        btContinueG.setPrefHeight(90);
        btContinueG.getStyleClass().add("my-button");
        btStartG.setPrefHeight(90);
        Button btEndG = new Button("Back");
        btEndG.setPrefHeight(90);
        btEndG.getStyleClass().add("my-button");
        btContinueG.setPrefWidth(340);
        btEndG.setPrefWidth(340);
        btStartG.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30));
        btContinueG.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30));
        btEndG.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30));
        vBox.getChildren().addAll(btStartG, btContinueG, btEndG);
        Pane pane = new Pane();
        pane.getChildren().addAll(mediaView, vBox);
        vBox.setLayoutX(470);
        vBox.setLayoutY(290);
        btStartG.setOnMouseClicked(event ->{
            app.welcomeStage.hide();
            app.mediaPlayerWelcom.pause();
            app.timeline.pause();
            app.chooseStage = app.getModeChooseStage();
        });
        btEndG.setOnMouseClicked(event -> {
            app.welcomeStage.hide();
            app.getloadInStage();
        });
        btContinueG.setOnMouseClicked(event -> {
            if(app.status == 0) {
                if(app.server.loadGame()[0] != null) {
                    app.mediaPlayerWelcom.pause();
                    app.server = (Server) app.server.loadGame()[0];
                    app.status = app.server.loadGame()[1] == null ? 0 : (Integer) app.server.loadGame()[1];
                    app.server.setName(app.tfAccount.getText());
                    app.createAndShowGui();
                    app.tempStage = app.getPass1Stage();
                    app.addKeyControls();
                }
            }
            if(app.tempStage != null && !app.server.isWin() && !app.server.isDie()) {
                app.mediaPlayerWelcom.pause();
                app.welcomeStage.hide();
                app. mediaPlayerWelcom.pause();
                app.timeline.pause();
                app.tempStage.show();
                app.mdBGM.play();
            }else{
                new Alert(Alert.AlertType.INFORMATION,"没有可以继续的游戏哟！").showAndWait();
            }
        });
        Scene scene = new Scene(pane, 1200, 700);
        scene.getStylesheets().add("file:res/css/style.css");
        this.setScene(scene);
        this.setResizable(false);
        this.show();
    }
}
