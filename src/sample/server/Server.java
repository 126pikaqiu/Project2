package sample.server;

import sample.core.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Server implements Serializable{

    private Icon[][] map;
    private Map totalMap = new Map(18,23);
    private Player player = new Player();
    private Monster[] monsters;
    private transient String name;
    private transient String passWord;
    private transient ArrayList<String> aName;
    private transient ArrayList<String> aPassword;
    private transient ArrayList<Integer> aScore;
    private boolean isSign;
    private boolean isLoadIn;

    //获得地图
    public void createGame() {
        map = totalMap.map;
    }

    //返回地图
    public Icon[][] getGameView() {
        Icon[][] tmp = new Icon[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            System.arraycopy(map[i], 0, tmp[i], 0, map[0].length);
        }
        return tmp;
    }

    //加入宝物
    public void addTreasures(int a){
        Treasure[] treasures = Treasure.makeTreasures(a, totalMap.map);
    }

    //加入怪物
    public void addMonsters(int a){
        monsters = Monster.makeMonsters(a, 2, totalMap.map);
    }

    //控制玩家移动
    public void movePlayer(Direction d) {
        switch (d) {
            case NORTH:
                player.moveUp(totalMap.map);
                break;
            case SOUTH:
                player.moveDown(totalMap.map);
                break;
            case EAST:
                player.moveRight(totalMap.map);
                break;
            case WEST:
                player.moveLeft(totalMap.map);
                break;
            case BACK:
                back();
            default:
                player.isPickTreasure = false;
        }
        createGame();
    }

    //回撤
    private void back(){
        player.back(totalMap.map);
        createGame();
    }

    //获得能否回撤
    public boolean canBack(){
        return player.canBack();
    }

    //获得是否撞墙
    public boolean isAginstWall(){
        return player.isAginstWall();
    }

    //获得是否捡起宝物
    public boolean isPickTreasure(){
        return player.isPickTreasure();
    }

    //移动怪物
    public void moveMosters(){
        if(monsters != null)
        Monster.moveMonsters(monsters, totalMap.map, player.getX(), player.getY());
        createGame();
    }

    //杀死怪物
    public void killMonster(){
        player.killMonster(monsters, totalMap.map);
        createGame();
    }


    //获得是否杀怪
    public boolean isKillMonster(){
        return player.isKillMonster();
    }

    //获得玩家是否取胜
    public boolean isWin(){
        return player.isWin();
    }

    //帮助信息
    public String help(){
        return "\n\n\n游戏目标：\n你的目标是操作人物\n从起点走到终点\n\n\n" + "命令说明：\n" +
                "H 查看帮助信息\n" + "W 人物向上移动一步\n" +"S 人物向下移动一步\n"
                + "A 人物向左移动一步\n" + "D 人物向右移动一步\n"+ "L 加血\n"
                + "K 杀死怪物(站在怪物旁边命令有效)\n" + "B 返回上一步\n"
                + "C 查看玩家信息\n" + "Q or X 结束游戏\n";
    }

    //玩家信息
    public String information(){
        return "\n\n\n击杀怪物数：" + player.getKillMonstersNumber() + "\n获得宝物数：" + player.getTreasureNumber()
                + "\n步数：" + player.getStepNumber() + "\n分数：" + player.getScore() + "\n血量：" + player.getBlood()
        + "\n金币" + player.getMoney();
    }

    //获得玩家是否死亡
    public boolean isDie(){
        return player.isDie();
    }

    //检查
    public void check(){
        player.check(map);
    }


    //刷新地图
    public void refresh(int a){
        totalMap = new Map(totalMap.map.length, totalMap.map[0].length);
        map = totalMap.map;
        player.to0(a);
    }

    //对话信息
    public String helpInformation(int level){
        String string = "";
        switch (level){
            case 1:
                string =  "  前方为一片无人的迷宫，大哥只要穿过迷宫就\n可以进入宫殿内部了,我会在暗中保护你，不用担心....";break;
            case 2:
                string =  "  无人的迷宫已经过去了，宫殿内部藏有宝藏，\n快去寻得宝藏,我一直在你身边，不用担心....";break;
            case 3:
                string =  "   前面有阴阳家的小兵，前去消灭他们，有事\n就叫我，我一直都在";break;
            case 4:
                string =  "   前面有阴阳家的杀手，还是小心为妙，有事\n就叫我，我依旧在";break;
        }
        return string;
    }

    //获得故事背景
    public String storyInformation(){
        return "       大河缓行穿过，沿途残留着森林，沼泽和遗迹。\n鲁班大师曾经设计的精美宫殿，只剩下断壁残垣，\n这里依旧传承着对太古当权者的信仰，而诠释这些\n信仰的权力，掌握在阴阳家们的手中。他们凭借这\n种权力，以及所握有的“奇迹”统治着这片土地。\n" +
                "       星移斗转，再虔诚的地方，也存在腐朽与堕落。\n阴阳家们逐渐老朽，已不再有那么强大的能力，那\n个惊天秘密就要被揭开。很快，命运选择了刘邦。\n" +
                "       刘邦是一位那样另类的大河子民中，他无视信\n仰，不相信阴阳家。他认为阴阳家所掌握的奇迹不\n过是诓骗世人的戏法，如果自己揭发并消灭他们，\n是否可以代替阴阳家们成为这片土地的王者？这个\n想法令他激动不已。\n" +
                "       他敢于如此妄想，多亏了鲁班的弟子鲁班七号。\n鲁班七号在躲避阴阳家的追杀时遇见了刘邦，并告\n诉了他阴阳家是如何抢走了宫殿，如何杀死了自己\n的族人，更为重要的是告诉了他阴阳家的身份.....\n" +
                "       借助鲁班七号的力量，他们成功迷惑守卫，目\n睹了神秘的仪式。“奇迹”璀璨的光芒笼罩着四位呢\n喃着咒语的阴阳家，以及窥视的两人。刘邦在傍边\n偷听。他都听说了些什么？鲁班七号的话得到印证，\n还有更多……更多……关于宫殿宝藏的事……\n" +
                "　　  随后他为惊人的发现而兴奋着……当仪式中的\n阴阳家们揭开神秘的面具…啊，原来统治楚汉之地\n的，竟然是这样一群……一群怪物！阴阳家们的真\n面目！那瞬间，他的脑海中涌现出更加激动人心的\n计划。\n" +
                "       仪式完成，阴阳家们一个接一个结束膜拜，随\n后阴阳家们掩藏了他们的真容——兽的面孔。他偷\n偷拔出护身剑，前去袭击落单的阴阳家，为了鲁班，\n也是为了自己无上的荣誉：鲁班七号心情复杂望着\n眼前欣喜若狂的男人，出手的那刻，便是决定追随于\n刘邦。\n" +
                "       在鲁班七号的陪同下，刘邦开始出发.........　\n";
    }

    //获得玩家位移方向
    public int[] getdxdy(){
        int[] ds = new int[2];
        ds[0] = player.getX() - player.getLX();
        ds[1] = player.getY() - player.getLY();
        return ds;
    }

    //获得玩家位置
    public int[] getXY(){
        int[] ds = new int[2];
        ds[0] = player.getX();
        ds[1] = player.getY();
        return ds;
    }

    //获得玩家是否移动
    public boolean isMove(){
        if(!player.isMove)
            player.isPickTreasure = false;
        player.isMove = !player.isMove;
        return !player.isMove;
    }

    //定义方法储存账户，密码和分数信息
    private void output() throws FileNotFoundException {

        //储存玩家信息
        PrintWriter output = new PrintWriter(new FileOutputStream("PI.txt",true));

            //创立文件夹储存登录名和密码
            output.print(" " + name);
            output.print(" " + passWord);
            output.close();
    }

    //定义方法储存账户和分数信息
    private void output1() throws FileNotFoundException {

        //储存玩家信息
        PrintWriter output = new PrintWriter(new FileOutputStream("PI1.txt",true));

        //创立文件夹储存登录名和密码
        output.print(" " + name);
        output.print(" " + player.getScore());
        output.close();
    }

    //读入信息
    private void input1() throws FileNotFoundException
    {
        aName = new ArrayList<>();
        aScore = new ArrayList<>();

        //创建文件对象
        java.io.File file = new java.io.File("PI1.txt");

        //文件读入器
        Scanner input = new Scanner(file);

        while(input.hasNext())
        {
            aName.add(input.next());
            aScore.add(input.nextInt());
        }

        //关闭文件
        input.close();
    }

    //读入信息
    private void input() throws FileNotFoundException
    {
        aName = new ArrayList<>();
        aPassword = new ArrayList<>();
        //创建文件对象
        java.io.File file = new java.io.File("PI.txt");

        //文件读入器
        Scanner input = new Scanner(file);

        while(input.hasNext())
        {
            aName.add(input.next());
            aPassword.add(input.next());
        }

        //关闭文件
        input.close();
    }


    //登陆判断提示
    public String checkLoad(String name, String passWord){
        try {
            input();
        }catch (FileNotFoundException e){
            return "账户或密码错误";
        }
        for(int i = 0; i < aName.size(); i++){
            if(name.equals(aName.get(i)))
                if(passWord.equals(aPassword.get(i)))
                {
                    isLoadIn = true;
                    return "";
                }
            else
                return "账户或密码错误";
        }
        return "账户或密码错误";
    }

    //注册判断提示
    public String checkSignIn(String name, String passWord){
        try {
            input();
        }catch (FileNotFoundException e){
            try{
                output();
            }catch (FileNotFoundException ignored){}
        }
        for (String anAName : aName) {
            if (name.equals(anAName))
                return "账户名已经存在";
        }
        if(!passWord.matches("\\d{6}"))
            return "密码应该为6个数字";
        setName(name);
        setPassWord(passWord);
        try {
            output();
            isSign = true;
            return "注册成功";
        }catch (FileNotFoundException ignored){}
        return "账户或密码错误";
    }

    //设置账户名
    public void setName(String name){
        this.name = name;
    }

    //设置密码
    private void setPassWord(String passWord){
        this.passWord = passWord;
    }

    //游戏存档
    public void saveGame(Server server, Integer integer) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./save/" + name + ".save"));
            oos.writeObject(server);
            oos.writeObject(integer);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //载入游戏
    public Object[] loadGame() {
        Object[] objects = new Object[2];
        objects[0] = null;
        objects[1] = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./save/" + name + ".save"));
            try {
                objects[0] = ois.readObject();
                objects[1] = ois.readObject();
            } catch (ClassNotFoundException ignored) {
            }
        } catch (IOException ignored) {
        }
        return objects;
    }

    //判断是否注册成功
    public boolean isSign(){
        return isSign;
    }

    //判断是否登陆成功
    public boolean isLoadIn(){
        return isLoadIn;
    }

    //获得玩家血量
    public int getFB(){
        return player.getBlood();
    }

    //增加玩家血量
    public void addFB(){
        player.addBlood();
    }

    //获得玩家金币
    public int getMoney(){
        return player.getMoney();
    }

    //获得排名信息
    public String paiMing(){
        try {
            output1();
            input1();
        }catch (FileNotFoundException ignored){}
        int[] scores = new int[aScore.size()];
        for(int i = 0; i < aScore.size(); i++)
            scores[i] = aScore.get(i);
        Arrays.sort(scores);
        StringBuilder a = new StringBuilder();
        int b = 0;
        if(scores.length == 1){
            a = new StringBuilder("你是第一个吃螃蟹的人" + "\n分数为：" + player.getScore());
            return a.toString();
        }
        for(int i = 1; i < 6; i++)
            if(scores.length >= i)
                a.append("\nNumber ").append(++b).append(": ").append(aName.get(aScore.indexOf(scores[scores.length - b]))).append("   ").append(scores[scores.length - b]);
        if(player.getScore() < scores[scores.length - b]) {
            a.append("\n真可惜，分数为：").append(player.getScore()).append("  就差一点就上榜了，下次加油了");
        }

        if(player.getScore() >= scores[scores.length - b]){
            a.append("\n真心厉害，分数为：").append(player.getScore()).append("  琅琊榜上留英名，也不枉此局");
        }
        return a.toString();
    }

    //获得攻击力
    public int getFF(){
        return player.getFF();
    }

    //获得f防御力
    public int getDF(){
        return player.getDF();
    }

    //获得加血数量
    public int getAddBN(){
        return player.getAddBN();
    }

    //捡起宝物后的提示信息
    public String tiShiI(){
        if(player.getPickStatus() == 1)
            return "战斗力增加50点，金币增加20";
        if(player.getPickStatus() == 2)
            return "防御力增加50点，金币增加20";
        if(player.getPickStatus() == 3)
            return "分数暴增500，金币增加20";
        return "";
    }

    //获得分数
    public int getScore(){
        return player.getScore();
    }

}
