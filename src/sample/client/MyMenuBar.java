package sample.client;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.*;
import javafx.util.Duration;

class MyMenuBar extends MenuBar implements Zujian{
    private Menu muFile = new Menu("File");
    private Menu muSet = new Menu("Setting");
    private Menu muHelp = new Menu("Help");
    private Menu muSave = new Menu("Save");
    private Menu muLoad = new Menu("Load");
    private Menu muLOM = new Menu("Load Other Map");
    private Menu muBGM = new Menu("Music");
    private Menu muBGM1 = new Menu("ShenMu village");
    private Menu muBGM2 = new Menu("She is my sin");
    private Menu muBGM3 = new Menu("The Down");
    private Menu muBGM4 = new Menu("Wuling Outside");
    private Menu muBGM5 = new Menu("长坂坡之一役");
    private Menu muBGM6 = new Menu("君临天下战斗");
    private Menu muTheme = new Menu("Theme");
    private Menu muThemeG = new Menu("Grass");
    private Menu muThemeS = new Menu("Snow");
    private Menu muVolume = new Menu("Volume");
    private Menu muMessage = new Menu("message");
    private Menu muBack = new Menu("Quit");

    MyMenuBar(Main app) {
        super();
        // add file menu
        Slider slider = new Slider();
        slider.setValue(50);
        app.mdBGM.volumeProperty().bind(slider.valueProperty().divide(100));
        CustomMenuItem muVolume1 = new CustomMenuItem(slider);
        muVolume.getItems().add(muVolume1);
        muTheme.getItems().addAll(muThemeG, muThemeS);
        muHelp.getItems().addAll(muMessage, muBack);
        muSet.getItems().addAll(muTheme, muBGM, muVolume);
        this.getMenus().addAll(muFile, muSet, muHelp);
        muBGM.getItems().addAll(muBGM1, muBGM2, muBGM3, muBGM4, muBGM5, muBGM6);
        muFile.getItems().addAll(muSave,muLoad,muLOM);
        muBack.setOnAction(event -> {
            app.stage.hide();
            app.tempStage.hide();
            app.mdBGM.pause();
            app.welcomeStage.show();
            app.timeline.play();
            mediaPlayerWelcom.play();
            app.timelineMoveMonster.play();
            app.tlMovePlayer.pause();
        });
        muThemeG.setOnAction(event ->{
            app.path = "file:image/grass/";
            app.initResources();
            app.createAndShowGui();
        });
        muThemeS.setOnAction(event ->{
            app.path = "file:image/snow/";
            app.initResources();
            app.createAndShowGui();
        });
        muBGM1.setOnAction(event -> {
            app.mdBGM.pause();
            app.mdBGM = mdBGM1;
            app.mdBGM.setCycleCount(10000);
            app.mdBGM.play();
        });
        muBGM2.setOnAction(event -> {
            app.mdBGM.pause();
            app.mdBGM = mdBGM2;
            app.mdBGM.setCycleCount(100);
            app.mdBGM.play();
        });
        muBGM3.setOnAction(event ->{
            app.mdBGM.pause();
            app.mdBGM = mdBGM3;
            app.mdBGM.setCycleCount(100);
            app.mdBGM.play();
        });
        muBGM4.setOnAction(event -> {
            app.mdBGM.pause();
            app.mdBGM = mdBGM4;
            app.mdBGM.setCycleCount(100);
            app.mdBGM.play();
        });
        muBGM5.setOnAction(event -> {
            app.mdBGM.pause();
            app.mdBGM = mdBGM5;
            app.mdBGM.setCycleCount(100);
            app.mdBGM.play();
        });
        muBGM6.setOnAction(event -> {
            app.mdBGM.pause();
            app.mdBGM = mdBGM6;
            app.mdBGM.setCycleCount(100);
            app.mdBGM.play();
        });
        muMessage.setOnAction(event -> app.help.showAndWait());

    }
}

