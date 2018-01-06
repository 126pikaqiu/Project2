package sample.client;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public interface Zujian {
    Button btOk = new Button("OK");
    MediaPlayer mediaPlayerWelcom = new MediaPlayer(new Media(new File("media/Welcome.mp4").toURI().toString()));
    MediaPlayer mdBGM1 = new MediaPlayer(new Media(new File("media/MapleSShenMu.mp3").toURI().toString()));
    MediaPlayer mdDefeated = new MediaPlayer(new Media(new File("media/Defeated.mp3").toURI().toString()));
    MediaPlayer mdBGM5 = new MediaPlayer(new Media(new File("media/长坂坡.wav").toURI().toString()));
    MediaPlayer mdBGM6 = new MediaPlayer(new Media(new File("media/君临天下战斗.wav").toURI().toString()));
    MediaPlayer mdBGM2 = new MediaPlayer(new Media(new File("media/She is my sin.mp3").toURI().toString()));
    MediaPlayer mdBGM3 = new MediaPlayer(new Media(new File("media/The Dawn.mp3").toURI().toString()));
    MediaPlayer mdBGM4 = new MediaPlayer(new Media(new File("media/MapleSWulin.mp3").toURI().toString()));
    MediaPlayer mdPass = new MediaPlayer(new Media(new File("media/Pass.wav").toURI().toString()));
    MediaPlayer mdAginastWall = new MediaPlayer(new Media(new File("media/Wall.mp3").toURI().toString()));
    MediaPlayer mdPickTr = new MediaPlayer(new Media(new File("media/pickTreasure.mp3").toURI().toString()));
    MediaPlayer mdKillMo = new MediaPlayer(new Media(new File("media/KillMonster.wav").toURI().toString()));
    MediaPlayer mediaPlayerModeChoo = new MediaPlayer(new Media(new File("media/ChooseStage.wav").toURI().toString()));
    MediaPlayer mdStory= new MediaPlayer(new Media(new File("media/英雄联盟候场.mp3").toURI().toString()));
    MediaPlayer mdStory1= new MediaPlayer(new Media(new File("media/争渡三国围场音效.mp3").toURI().toString()));
    ImageView igvSP3 = new ImageView( "file:image/story/storyP3.jpg");
    Image imgHB0 = new Image("file:image/blood/h0.png");
    Image imgHB1 = new Image("file:image/blood/h1.png");
    Image imgHB2 = new Image("file:image/blood/h2.png");
    Image imgHB3 = new Image("file:image/blood/h3.png");
    Image imgHB4 = new Image("file:image/blood/h4.png");
    Image imgHB5 = new Image("file:image/blood/h5.png");
    Image imgHB6 = new Image("file:image/blood/h6.png");
    Image imgHB7 = new Image("file:image/blood/h7.png");
    Image imgHB8 = new Image("file:image/blood/h8.png");
    Image imgHB9 = new Image("file:image/blood/h9.png");
    Image imgHB10 = new Image("file:image/blood/h10.png");
    Image imgHB11 = new Image("file:image/blood/h11.png");
    Image imgHB12 = new Image("file:image/blood/h12.png");
    Image imgHB13 = new Image("file:image/blood/h13.png");
    Alert alertS1 = new Alert(Alert.AlertType.INFORMATION, "恭喜通过故事模式第一关，下\n一关可是藏有宝藏的一关哟");
    Alert alertS2 = new Alert(Alert.AlertType.INFORMATION, "恭喜通过故事模式第二关，下\n一关有几个看守宝藏的小怪，前\n去干掉他们");
    Alert alertS3 = new Alert(Alert.AlertType.INFORMATION, "恭喜通过故事模式第三关，下\n可怪物发现了你的踪迹，正在到\n处寻找你，一场大战即将来临");
    TextField tfAccount = new TextField();
}
