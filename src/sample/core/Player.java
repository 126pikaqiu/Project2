package sample.core;
import java.io.Serializable;
import java.util.*;

public class Player extends Entity implements Serializable
{
    //定义数组储存玩家走过的位置
    private ArrayList<Integer> posOfLocX = new ArrayList<>();
    private ArrayList<Integer> posOfLocY = new ArrayList<>();

    //人物血量
    private int blood;

    //定义玩家是否通关
    private boolean isWin;

    //攻击力
    private int fF;

    //防御力
    private int dF;

    //定义金币
    private int money;

    //定义玩家是否死亡
    private boolean isDie;

    //判断怪物是否被杀掉
    private static boolean isKillMonster;

    //判断能否回撤
    private boolean canBack = true;

    //判断是否移动
    public  boolean isMove = false;


    //定义人物是否撞墙
    private boolean isAginstWall;

    //定义人物是否捡起宝物
    public  boolean isPickTreasure;

    //定义分数
    private int score;

    //定义玩家信息
    private int stepNumber = 0;
    private int killMonstersNumber = 0;
    private int treasureNumber = 0;

    //临时位置
    private int tempX,tempY;

    //是否能够回退
    private boolean isBack;

    //增加血量次数
    private int addBN;

    //捡起宝物后的奖励
    private int pickStatus;


    //初始化人物位置，以及血量,攻击力，防御力
     public Player() {
        x = 1;
        y = 1;
        blood = 100;
        money = 100;
        fF = 200;
        dF = 200;
    }

    //向右移动
    public void moveRight(Icon[][] map){
        isBack = false;
        canBack = true;
        isAginstWall = false;
        isKillMonster  = false;
        if (map[x][y + 1] != Icon.WALL) {
            isMove = true;
            posOfLocX.add(x);
            posOfLocY.add(y);
            y++;
            pickTreasure(map);
            stepNumber++;
        } else
            isAginstWall = true;
        check(map);
        makeFootprint(map);
        isWin = x == map.length - 2 && y == map[0].length - 2;
    }

    //向左移动
    public void moveLeft(Icon[][] map){
        isBack = false;
        canBack = true;
        isAginstWall = false;
        isKillMonster  = false;
        if (map[x][y - 1] != Icon.WALL) {
            isMove = true;
            posOfLocX.add(x);
            posOfLocY.add(y);
            y--;
            pickTreasure(map);
            stepNumber++;
        } else
            isAginstWall = true;
        check(map);
        makeFootprint(map);
        isWin = x == map.length - 2 && y == map[0].length - 2;
    }

    //向上移动
    public void moveUp(Icon[][] map){
        isBack = false;
        canBack = true;
        isAginstWall = false;
        isKillMonster  = false;
        if (map[x - 1][y] != Icon.WALL) {
            isMove = true;
            posOfLocX.add(x);
            posOfLocY.add(y);
            x--;
            pickTreasure(map);
            stepNumber++;
        } else
            isAginstWall = true;
        check(map);
        makeFootprint(map);
        isWin = x == map.length - 2 && y == map[0].length - 2;
    }

    //向下移动
    public void moveDown(Icon[][] map){
        isBack = false;
        canBack = true;
        isAginstWall = false;
        isKillMonster  = false;
        if (map[x + 1][y] != Icon.WALL) {
            isMove = true;
            posOfLocX.add(x);
            posOfLocY.add(y);
            x++;
            pickTreasure(map);
            stepNumber++;
        } else
            isAginstWall = true;
        check(map);
        makeFootprint(map);
        isWin = x == map.length - 2 && y == map[0].length - 2;
    }

    //定义方法实现人物捡起宝物
    private void pickTreasure(Icon[][] map){
        isPickTreasure = false;
        if(map[x][y] == Icon.TREASURE)
        {
            isPickTreasure = true;
            treasureNumber++;
            double a = Math.random();
            if(a < 0.48) {
                fF += 50;
                money += 20;
                pickStatus = 1;
            }
            else if(a < 0.96){
                dF += 50;
                money += 20;
                pickStatus = 2;
            }else if(a < 1){
                money += 20;
                score += 500;
                pickStatus = 3;
            }
            map[x][y] = Icon.EMPTY;
        }
    }

    //实现足迹
    private void makeFootprint(Icon[][] map){
        if(posOfLocX.size() >= 3 &&
                map[posOfLocX.get(posOfLocX.size() - 3)][posOfLocY.get(posOfLocY.size() - 3)] == Icon.FOOTPRINT)
            map[posOfLocX.get(posOfLocX.size() - 3)][posOfLocY.get(posOfLocY.size() - 3)] = Icon.EMPTY;
        if(posOfLocX.size() >= 1 &&
                map[posOfLocX.get(posOfLocX.size() - 1)][posOfLocY.get(posOfLocY.size() - 1)] == Icon.EMPTY)
            map[posOfLocX.get(posOfLocX.size() - 1)][posOfLocY.get(posOfLocX.size() - 1)] = Icon.FOOTPRINT;
        if(posOfLocX.size() >= 2 &&
                map[posOfLocX.get(posOfLocX.size() - 2)][posOfLocY.get(posOfLocY.size() - 2)] == Icon.EMPTY)
            map[posOfLocX.get(posOfLocX.size() - 2)][posOfLocY.get(posOfLocY.size() - 2)] = Icon.FOOTPRINT;

    }

    //定义方法实现人物杀死怪物
    public  void killMonster(Monster[] monsters, Icon[][] map)
    {

        isAginstWall = false;
        isKillMonster  = false;
        isPickTreasure = false;
        if(monsters != null)
        for (Monster monster : monsters)

            //遍历所有怪物
            if (monster != null && ((monster.getX() == x && Math.abs(monster.getY() - y) <= 1 )|| (monster.getY() == y && Math.abs(monster.getX() - x) <= 1)))
            {
                //怪物血量减少
                monster.setBlood(monster.getBlood() - (int)(fF * 0.2));

                isKillMonster  = true;

                //怪物被杀
                if(monster.getBlood() <= 0){

                map[monster.getX()][monster.getY()] = Icon.EMPTY;

                monster.to0();

                //增加血量或分数
                if(blood <= 100){
                    if(monster.getStatus() == 0){
                        blood += 10;
                        money += 10;
                        dF += 15;
                    }
                    else{
                        blood += 30;
                        money += 30;
                        dF += 45;
                    }
                }else {
                    if (monster.getStatus() == 0) {
                        money += 15;
                        dF += 15;
                    }
                    else{
                        money += 45;
                        dF += 45;
                    }
                }

                //杀怪数加一
                killMonstersNumber++;
                }

            }

    }

    //检查被攻击的情况以及血量变化
    public void check(Icon[][] map){
        if(map[x - 1][y] == Icon.MONSTER2)
            blood-= 100 * 8 / dF;
        if(map[x + 1][y] == Icon.MONSTER2)
            blood-= 100 * 8 / dF;
        if(map[x][y + 1] == Icon.MONSTER2)
            blood-= 100 * 8 / dF;
        if(map[x][y - 1] == Icon.MONSTER2)
            blood-= 100 * 8 / dF;
        if(map[x][y] == Icon.MONSTER2)
            blood-= 100 * 8 / dF;
        if(map[x - 1][y] == Icon.MONSTER1)
            blood-= 100 * 5 / dF;
        if(map[x + 1][y] == Icon.MONSTER1)
            blood-= 100 * 5 / dF;
        if(map[x][y + 1] == Icon.MONSTER1)
            blood-= 100 * 5 / dF;
        if(map[x][y - 1] == Icon.MONSTER1)
            blood-= 100 * 5 / dF;
        if(map[x][y] == Icon.MONSTER1)
            blood-= 100 * 5 / dF;
        if(blood <= 0)
            isDie = true;
    }

    //获得人物所走步数
    public  long getStepNumber()
    {
        return stepNumber;
    }

    //获得人物杀怪数
    public  int getKillMonstersNumber()
    {
        return killMonstersNumber;
    }

    //获得人物捡宝数
    public  int getTreasureNumber() {
        return treasureNumber;
    }

    //获得是否捡起宝物
    public boolean isPickTreasure(){
        return isPickTreasure;
    }

    //获得玩家位置坐标
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }

    //定义方法实现人物回归原点,并清除人物位置的记录
    public void to0(int a)
    {
        x = 1;
        y = 1;
        posOfLocX = new ArrayList<>();
        posOfLocY = new ArrayList<>();
        isWin = false;
        addBN = 0;

        //沙盒模式的特殊处理
        if(a <= 0){
            blood = 100;
            score = 0;
            dF = 200;
            fF = 200;
            money = 100;
            isDie = false;
            treasureNumber = 0;
            killMonstersNumber = 0;
            stepNumber = 0;
        }
    }

    //回撤
    public void back(Icon[][] map){
        canBack = true;
        if(posOfLocX.size() > 0)
        {
            isBack = true;
            isMove = true;
            stepNumber--;
            tempX = x;
            tempY = y;
            x = posOfLocX.get(posOfLocX.size() - 1);
            y = posOfLocY.get(posOfLocY.size() - 1);
            System.out.println(posOfLocX.size());
            posOfLocX.remove(posOfLocX.size() - 1);
            posOfLocY.remove(posOfLocY.size() - 1);
            System.out.println(posOfLocX.size());
            makeFootprint(map);
        }else
            canBack = false;
    }

    //获得能否回撤
    public boolean canBack(){
        return canBack;
    }

    //获得是否杀怪
    public boolean isKillMonster()
    {
        return isKillMonster;
    }

    //获得是否撞墙
    public boolean isAginstWall(){
        return isAginstWall;
    }

    //是否胜利
    public boolean isWin(){
        return isWin;
    }

    //获得分数
    public int getScore(){
        return score + 50 * treasureNumber + 100 * killMonstersNumber - stepNumber;
    }

    //是否失败
    public boolean isDie(){
        return isDie;
    }

    //获得上一个位置的坐标
    public int getLX(){
        if(isBack)
            return tempX;
        else
            return posOfLocX.get(posOfLocX.size() - 1);
    }

    //获得生一个位置的坐标
    public int getLY(){
        if(isBack)
            return tempY;
        else
            return posOfLocY.get(posOfLocY.size() - 1);
    }

    //获得血量
    public int getBlood(){
        return blood;
    }

    //获得金币
    public int getMoney(){
        return money;
    }

    //增加血量情况
    public void addBlood(){
        if(money >= Math.pow(2, ++addBN) * 10) {
            if(blood <= 100) {
                blood += 20;
                money -= (int)(Math.pow(2, addBN) * 10);
            }

        }
    }

    //获得攻击力d
    public int getFF(){
        return fF;
    }

    //获得f防御力
    public int getDF(){
        return dF;
    }

    //获得加血数量
    public int getAddBN(){
        return addBN;
    }

    //捡起宝物的获赠信息
    public int getPickStatus(){
        return pickStatus;
    }
}
