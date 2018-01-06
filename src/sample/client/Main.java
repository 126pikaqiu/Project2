package sample.client;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.core.Direction;
import sample.core.Icon;
import sample.server.Server;;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application implements Zujian {
    Stage stage;
    Pane map = new Pane();
    private Map<Icon, Image> imageMap = new HashMap<>();
    private transient ImageView[][] imageViews;
    Server server;
    Timeline timeline;
    MediaPlayer mdBGM = mdBGM1;
    String path = "file:image/snow/";
    Stage welcomeStage;
    Stage chooseStage;
    Stage passChoose;
    Stage tempStage;
    Alert help;
    private MenuBar menuBar;
    Timeline timelineMoveMonster;
    Timeline tlMovePlayer;
    boolean isSign;
    private ImageView igvHB = new ImageView(imgHB0);
    private String p1 = "1/";
    private Image imageHero1 = new Image( "file:image/" + p1 + "hero1.png");
    private Image imageHero2 = new Image("file:image/" + p1 + "hero2.png");
    private Image imageHero3 = new Image("file:image/" + p1 + "hero3.png");
    private Image imageHero4 = new Image("file:image/" + p1 + "hero4.png");
    private ImageView igvHero = new ImageView(imageHero1);
    int status;
    private boolean canMove;
    private Timeline timelineCh;
    private Label label = new Label();
    private Rectangle rectangle;



    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("maze game");
        this.stage = primaryStage;
        server = new Server();
        server.createGame();
        initGameFrame();

        // prepare resources
        initResources();
        addKeyControls();
        createAndShowGui();
    }

    //用来刷新界面
    void createAndShowGui() {
        Icon[][] icons = server.getGameView();
        for (int r = 0; r < icons.length; r++) {
            // maze contents
            for (int c = 0; c < icons[0].length; c++) {
                    imageViews[r][c].setImage(imageMap.get(icons[r][c]));
            }
        }
    }


    //按键的控制程序
    void addKeyControls() {
        map.setOnKeyPressed(e -> {
            Direction d = null;
            switch (e.getCode()) {
                case UP:
                case W:
                    d = Direction.NORTH;
                    break;
                case LEFT:
                case A:
                    d = Direction.WEST;
                    break;
                case DOWN:
                case S:
                    d = Direction.SOUTH;
                    break;
                case RIGHT:
                case D:
                    d = Direction.EAST;
                    break;
                case B:
                    d = Direction.BACK;
                    break;
                case K: ;
                    d = Direction.KILL;
                    server.killMonster();
                    break;
                case C:
                    new Alert(Alert.AlertType.INFORMATION, server.information()).showAndWait();
                    return;
                case H:
                    help.showAndWait();
                    break;
                case Q:
                case X:
                    this.stage.hide();
                    mdBGM.pause();
                    welcomeStage.show();
                    mediaPlayerWelcom.play();
                    tlMovePlayer.pause();
                    break;
                case L:
                    if(server.getFB() >= 100)
                        new Alert(Alert.AlertType.INFORMATION,"血已加满，无法加血").showAndWait();
                    else if(server.getMoney() >= Math.pow(2, server.getAddBN()) * 10) {
                        server.addFB();
                        new Alert(Alert.AlertType.INFORMATION,
                                "已加血，金币消耗" + (Math.pow(2,server.getAddBN())) * 10).showAndWait();
                    }
                    else
                        new Alert(Alert.AlertType.INFORMATION,
                                "金币不足，无法加血  需要金币数为" + (Math.pow(2,server.getAddBN())) * 10 ).showAndWait();
                    break;
                default:
                    server.movePlayer(Direction.NULL);
            }

            //玩家控制英雄移动
            if (d != null && canMove) {
                server.movePlayer(d);
                createAndShowGui();
            }

            //判断是否撞墙，能否后退
            if(server.isAginstWall() || !server.canBack())
            {
                mdAginastWall.seek(Duration.ZERO);
                mdAginastWall.play();
            }

            //判断玩家是否移动
            if(server.isMove()){
                if(d == Direction.NORTH) {
                    p1 = "4/";
                }else if(d == Direction.SOUTH){
                    p1 = "1/";
                }else if(d == Direction.WEST){
                    p1 = "2/";
                }else if(d == Direction.EAST){
                    p1 = "3/";
                }

                //更新玩家图片
                initResources1();

                //移动小碎步
                tlMovePlayer.play();
            }else if(d == Direction.KILL)
                killMonsterAn();

            //判断是否捡起宝物
            if(server.isPickTreasure()) {
                mdPickTr.seek(Duration.ZERO);
                mdPickTr.play();
                label.setText(server.tiShiI());
                label.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30));
                StackPane stackPane = new StackPane();
                stackPane.setLayoutY(450);
                rectangle.setFill(Color.GREEN);

                //渐变透明度的游戏提示
                FadeTransition ft = new FadeTransition(Duration.millis(4000),stackPane);
                ft.setFromValue(1.0);
                ft.setToValue(0);
                ft.play();

                stackPane.getChildren().addAll(rectangle, label);
                map.getChildren().add(stackPane);
            }

            //判断是否取得胜利
            if(server.isWin()){

                //音效控制
                mdBGM.pause();
                mdPass.seek(Duration.ZERO);
                mdPass.play();

                //通过游戏状态给出通关信息，并转换舞台
                if(status < 0 || status == 4) {

                    //沙盒模式或故事模式第四关
                    new Alert(Alert.AlertType.INFORMATION, "恭喜过关！").showAndWait();
                    new Alert(Alert.AlertType.INFORMATION, server.paiMing()).showAndWait();
                    this.stage.hide();
                    timelineMoveMonster.pause();
                    passChoose.show();
                }else if(status == 1){

                    //故事模式第一关
                    alertS1.showAndWait();
                    this.stage.hide();
                    this.stage = storyStage(2);
                    this.stage.show();
                    status = 2;
                }else if(status == 2){

                    //故事模式第二关
                    alertS2.showAndWait();
                    this.stage.hide();
                    this.stage = storyStage(3);
                    this.stage.show();
                    status = 3;
                }else if(status == 3){

                    //故事模式第三关
                    alertS3.showAndWait();
                    this.stage.hide();
                    this.stage = storyStage(4);
                    timelineMoveMonster.play();
                    this.stage.show();
                    status = 4;
                }
            }

            //判断玩家是否闯关失败并给出提示信息
            if(server.isDie()){

                //音效和时间轴控制
                mdBGM.pause();
                mdDefeated.seek(Duration.ZERO);
                mdDefeated.play();
                timelineCh.pause();

                new Alert(Alert.AlertType.INFORMATION, "你被怪物吃了！！").showAndWait();
                stage.hide();
                tempStage.hide();
                tempStage = null;
                welcomeStage.show();
                mediaPlayerWelcom.play();
            }

            //判断玩家是否杀怪并播放音效
            if(server.isKillMonster()){
                mdKillMo.seek(Duration.ZERO);
                mdKillMo.play();
            }
        });
    }

    //初始化游戏框架
    private void initGameFrame() {
        rectangle = new Rectangle();
        rectangle.setWidth(1150 + 20);
        rectangle.setHeight(200);

        //登陆界面
        getloadInStage();

        //初始化帮助信息对话框
        mediaPlayerModeChoo.setVolume(0.4);
        help = new Alert(Alert.AlertType.INFORMATION, server.help());

        //初始化视图数组并添加进面板中
        imageViews = new ImageView[server.getGameView().length][server.getGameView()[0].length];
        for (int i = 0; i < imageViews.length; i++)
            for(int j = 0; j < imageViews[0].length; j++){
                imageViews[i][j] = new ImageView();
                imageViews[i][j].setX(j * 50);
                imageViews[i][j].setY(i * 50);
                map.getChildren().add(imageViews[i][j]);
            }

        //添加英雄图片和血条图片，进行相应关联
        map.getChildren().addAll(igvHero,igvHB);
        igvHB.xProperty().bind(igvHero.xProperty());
        igvHB.yProperty().bind(igvHero.yProperty().subtract(30));
        igvHero.setX(50);
        igvHero.setY(50);

        //移动怪物的时间控制和失败检查
        timelineMoveMonster = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            server.moveMosters();
            if(server.isDie()){
                timelineMoveMonster.pause();
                mdBGM.pause();
                mdDefeated.seek(Duration.ZERO);
                mdDefeated.play();
                stage.hide();
                tempStage.hide();
                tempStage = null;
                welcomeStage.show();
                mediaPlayerWelcom.play();
                new Alert(Alert.AlertType.INFORMATION, "你被怪物吃了！！").show();
                mediaPlayerModeChoo.play();
                server.refresh(status);
            }
            createAndShowGui();
        }));

        //玩家的掉血检查
        timelineCh = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            server.check();

            //根据血量换图片
            if(server.getFB() >= 100)
                igvHB.setImage(imgHB0);
            else if(server.getFB() >= 92)
                igvHB.setImage(imgHB1);
            else if(server.getFB() >= 84)
                igvHB.setImage(imgHB2);
            else if(server.getFB() >= 75)
                igvHB.setImage(imgHB3);
            else if(server.getFB() >= 69)
                igvHB.setImage(imgHB4);
            else if(server.getFB() >= 62)
                igvHB.setImage(imgHB5);
            else if(server.getFB() >= 56)
                igvHB.setImage(imgHB6);
            else if(server.getFB() >= 50)
                igvHB.setImage(imgHB7);
            else if(server.getFB() >= 42)
                igvHB.setImage(imgHB8);
            else if(server.getFB() >= 33)
                igvHB.setImage(imgHB9);
            else if(server.getFB() >= 25)
                igvHB.setImage(imgHB10);
            else if(server.getFB() >= 18)
                igvHB.setImage(imgHB11);
            else if(server.getFB() >= 12)
                igvHB.setImage(imgHB12);
            else if(server.getFB() >= 6)
                igvHB.setImage(imgHB13);
        }));

        //时间轴设定
        timelineCh.setCycleCount(Timeline.INDEFINITE);
        timelineCh.play();
        timelineMoveMonster.setCycleCount(Timeline.INDEFINITE);
        tlMovePlayer = new Timeline(new KeyFrame(Duration.millis(50),new HandleMP()));
        tlMovePlayer.setCycleCount(Timeline.INDEFINITE);

        //初始化菜单栏
        menuBar = new MyMenuBar(this);

    }


    //模式选择的界面
    Stage getModeChooseStage(){
        return new MyModeChooseStage(this);
    }

    //第一关的界面
    Stage getPass1Stage(){
        Label label1 = new Label(server.getMoney() + "");
        Label label = new Label("Money: $",label1);
        label.setContentDisplay(ContentDisplay.RIGHT);
        Label label3 = new Label(server.getFB() + "");
        Label label2 = new Label("Blood: ",label3);
        label2.setContentDisplay(ContentDisplay.RIGHT);
        Label label4 = new Label(server.getFF() + " 点");
        Label label5 = new Label("FightingForce: ",label4);
        label5.setContentDisplay(ContentDisplay.RIGHT);
        Label label6 = new Label(server.getDF() + "  点");
        Label label7 = new Label("DefenceForce: ",label6);
        label7.setContentDisplay(ContentDisplay.RIGHT);
        Label label8 = new Label(server.getScore() + "  分");
        Label label9 = new Label("Score: ",label8);
        label9.setContentDisplay(ContentDisplay.RIGHT);
        HBox hBox1 = new HBox(50);
        hBox1.setPadding(new Insets(5,40,5,40));
        hBox1.getChildren().addAll(label, label2, label5, label7, label9);
        mediaPlayerModeChoo.pause();
        igvHero.setX(server.getXY()[1] * 50);
        igvHero.setY(server.getXY()[0] * 50);
        if(status == 4 || status == -4)
            timelineMoveMonster.play();
        else
            timelineMoveMonster.pause();
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(menuBar,hBox1);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50),event -> {
            label1.setText(server.getMoney() + "");
            label3.setText(server.getFB() + "");
            label4.setText(server.getFF() + " 点");
            label6.setText(server.getDF() + "  点");
            label8.setText(server.getScore() + "  分");
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        BorderPane pane1 = new BorderPane();
        pane1.setTop(hBox);
        pane1.setCenter(map);
        pane1.getChildren().add(new MediaView(mdPass));
        tempStage = new Stage();
        tempStage.setScene(new Scene(pane1, 1150, 920));
        tempStage.setResizable(false);
        map.requestFocus();
        return tempStage;
    }

    //第二关的界面
    Stage getPass2Stage(){
        server.refresh(status);
        tempStage = getPass1Stage();
        server.addTreasures(30);
        createAndShowGui();
        return tempStage;
    }

    //第三关的界面
    Stage getPass3Stage(){
        server.refresh(status);
        tempStage = getPass1Stage();
        server.addTreasures(3);
        server.addMonsters(5);
        createAndShowGui();
        return tempStage;
    }

    //第四关的界面
    Stage getPass4Stage(){
        server.refresh(status);
        tempStage = getPass1Stage();
        server.addTreasures(5);
        server.addMonsters(14);
        createAndShowGui();
        timelineMoveMonster.play();
        return tempStage;
    }

    //选关界面
    Stage getPassChooseStage(){
        return new MyPassChooseStage(this);
    }

    //故事串联界面
    Stage storyStage(int level){

        //音效控制
        mediaPlayerWelcom.pause();
        mdStory1.play();

        //面板和图片设置
        Pane pane = new Pane();
        ImageView imageView1 = new ImageView(imageHero1);
        imageView1.setFitWidth(109);
        imageView1.setFitHeight(110);
        pane.getChildren().addAll(igvSP3, imageView1);
        imageView1.setY(540);
        stage.setScene(new Scene(pane));
        stage.show();
        p1 = "3/";
        initResources1();
        imageView1.setImage(imageHero1);

        //移动图片
        pane.setOnKeyPressed(event -> {
            switch (event.getCode()){
                case D:
                case RIGHT:
                    imageView1.setX(imageView1.getX() + 10);
                    if(imageView1.getImage() == imageHero1){
                        imageView1.setImage(imageHero2);
                    }else  if(imageView1.getImage() == imageHero2){
                        imageView1.setImage(imageHero3);
                    }else  if(imageView1.getImage() == imageHero3){
                        imageView1.setImage(imageHero4);
                    }else if(imageView1.getImage() == imageHero4) {
                        imageView1.setImage(imageHero1);
                    }
            }

            //对话
            if(imageView1.getX() ==  340)
                new Alert(Alert.AlertType.INFORMATION, server.helpInformation(level)).showAndWait();

            //转换舞台
            if(imageView1.getX() ==  660)
            {
                stage.hide();
                if(level == 1)
                {
                    mdStory1.pause();
                    mdBGM.play();
                    this.stage = getPass1Stage();
                    createAndShowGui();
                }
                if(level == 2)
                {
                    mdBGM.play();
                    this.storyStage(2);
                    this.stage.close();
                    mdStory1.pause();
                    this.stage = getPass2Stage();
                }
                else if(level == 3)
                {
                    mdBGM.play();
                    this.storyStage(3);
                    this.stage.close();
                    mdStory1.pause();
                    this.stage = getPass3Stage();
                }
                else if(level == 4)
                {
                    mdBGM.play();
                    this.storyStage(4);
                    this.stage.close();
                    mdStory1.pause();
                    this.stage = getPass4Stage();
                }
                this.stage.show();
            }
        });
        pane.requestFocus();
        return stage;

    }

    //初始化资源
    void initResources() {

        //图片资源
        Image WALL_IMAGE = new Image(path + "wall.png");
        Image SPACE_IMAGE = new Image(path + "space.png");
        Image END_IMAGE = new Image(path + "end.png");
        Image FOOTPRINT_IMAGE = new Image(path + "footprint.png");
        Image TREASURE_IMAGE = new Image(path + "treasure.png");
        Image MONSTER1_IMAGE = new Image(path + "monster1.png");
        Image MONSTER2_IMAGE = new Image(path + "monster2.png");

        // 图片转接
        imageMap.put(Icon.EMPTY, SPACE_IMAGE);
        imageMap.put(Icon.WALL, WALL_IMAGE);
        imageMap.put(Icon.FOOTPRINT, FOOTPRINT_IMAGE);
        imageMap.put(Icon.MONSTER1, MONSTER1_IMAGE);
        imageMap.put(Icon.MONSTER2, MONSTER2_IMAGE);
        imageMap.put(Icon.END, END_IMAGE);
        imageMap.put(Icon.TREASURE, TREASURE_IMAGE);
    }

    //初始化资源
    private void initResources1() {

        //更新图片
        imageHero1 = new Image( "file:image/" + p1 + "hero1.png");
        imageHero2 = new Image("file:image/" + p1 + "hero2.png");
        imageHero3 = new Image("file:image/" + p1 + "hero3.png");
        imageHero4 = new Image("file:image/" + p1 + "hero4.png");
        igvHero.setImage(imageHero1);
    }

    //实现人物移动的小碎步
    class HandleMP implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent e) {

            //移动图片和更换图片
            if (Math.abs(igvHero.getX() - server.getXY()[1] * 50) > 0.01 || Math.abs(igvHero.getY() - server.getXY()[0] * 50) > 0.01) {
                if(igvHero.getImage() == imageHero1){
                    igvHero.setImage(imageHero2);
                }else  if(igvHero.getImage() == imageHero2){
                    igvHero.setImage(imageHero3);
                }else  if(igvHero.getImage() == imageHero3){
                    igvHero.setImage(imageHero4);
                }else if(igvHero.getImage() == imageHero4){
                    igvHero.setImage(imageHero1);
                }

                if (Math.abs(igvHero.getX() - server.getXY()[1] * 50) > 0.01) {
                    igvHero.setX(igvHero.getX() + 6.25 * server.getdxdy()[1]);
                }
                if (Math.abs(igvHero.getY() - server.getXY()[0] * 50) > 0.01)
                    igvHero.setY(igvHero.getY() + 6.25 * server.getdxdy()[0]);
                canMove = false;
            }else
                canMove = true;
        }
    }

    //实现人物杀怪的慢动作
    private void killMonsterAn(){
        switch (p1) {
            case "1/":
                p1 = "5/";
                break;
            case "2/":
                p1 = "6/";
                break;
            case "3/":
                p1 = "7/";
                break;
            case "4/":
                p1 = "8/";
                break;
        }
        initResources1();

        //时间轴控制
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            if(igvHero.getImage() == imageHero1){
                igvHero.setImage(imageHero2);
            }else  if(igvHero.getImage() == imageHero2){
                igvHero.setImage(imageHero3);
            }else  if(igvHero.getImage() == imageHero3){
                igvHero.setImage(imageHero4);
            }else if(igvHero.getImage() == imageHero4){
                igvHero.setImage(imageHero1);
        }}));
        timeline.setCycleCount(8);
        timeline.play();
    }

    //获得游戏登陆界面
    void getloadInStage() {
        new MyloadStage(this);
    }

    //获得游戏注册界面
    void getSignInStage() {
        new MySignStage(this);
    }

    //主方法
    public static void main(String[] args) {
        launch(args);
    }

}
