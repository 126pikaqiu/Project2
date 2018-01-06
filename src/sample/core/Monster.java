package sample.core;

import java.io.Serializable;

public class Monster extends Entity implements Serializable
{
    //实现怪物追踪玩家
    private int[][] direction = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};//four directions
    private int[] tempPosX;
    private int[] tempPosY;
    private int[] first;

    //图标定义
    private Icon monsterI;

    //怪物血量
    private int blood;

    //怪物等级
    private int status;

    //设置怪物初始位置
    private Monster(Icon[][] map, int status )
    {

        this.status = status;
        if(status == 0){
            blood = 200;
            monsterI = Icon.MONSTER1;
        }
        else {
            blood = 400;
            monsterI = Icon.MONSTER2;
        }

        //怪物能否放置
        boolean canPut = true;
        while(canPut)
        {

            //产生随机方向
            int i = (int) (Math.random() * map.length);

            //随机选择合适位置
            for (int j = (int) (Math.random() * map[0].length); j < map[i].length; j++)
            {

                //不能放置的一些地方
                if (map[i][j] != Icon.END && map[i][j] != monsterI &&
                        map[i][j] != Icon.TREASURE && map[i][j] != Icon.WALL&& i != 1)
                {
                    x = i;
                    y = j;

                    //让当前位置变为怪物
                    map[x][y] = monsterI;

                    //跳出循环
                    canPut = false;
                    break;
                }
            }
        }
    }

    //实现怪物的追踪玩家
    private void moveMonster(Icon[][] map,int hX, int hY)
    {

        //靠近之后就停止移动
        if((Math.abs(x - hX)== 1 && y == hY) || (Math.abs(y - hY)== 1 && x == hX) )
            return;

        boolean[][] researchMap = new boolean[map.length][map[0].length];
        tempPosX = new int[10000];
        tempPosY = new int[10000];
        first = new int[10000];
        researchMap[x][y] = true;
        int m = 1;
        int n = 0;
        tempPosX[m] = x;
        tempPosY[m] = y;
        while(true){
            n++;
            if(tempPosX[n] == hX && tempPosY[n] == hY) {

                //移动判断和位置清理
                if(map[x][y] == monsterI)
                map[x][y] = Icon.EMPTY;

                //距离太远不追踪
                if(n >= 60)
                {
                    int a = (int)(Math.random() * 4);
                    if(map[x + direction[a][0]][y + direction[a][1]] == Icon.EMPTY)
                        map[x += direction[a][0]][y += direction[a][1]] = monsterI;
                    else {
                        int b = (int)(Math.random() * 4);
                        if(map[x + direction[b][0]][y + direction[b][1]] == Icon.EMPTY)
                            map[x += direction[b][0]][y += direction[b][1]] = monsterI;
                        else
                        map[x][y] = monsterI;
                    }
                    return;
                }

                //追踪玩家
                if(map[x += direction[first(n)][0]][y += direction[first(n)][1]] != Icon.END && map[x][y] != Icon.TREASURE)
                    map[x][y] = monsterI;
                return;
            }

            //广度优先搜索
            for(int i = 0; i < 4; i++){
                int tempX = tempPosX[n] + direction[i][0];
                int tempY = tempPosY[n] + direction[i][1];
                if(map[tempX][tempY] != Icon.WALL && !researchMap[tempX][tempY]){
                    m++;
                    tempPosX[m] = tempX;
                    tempPosY[m] = tempY;
                    first[m] = n;
                    researchMap[tempX][tempY] = true;
                }
            }
        }
    }

    //记录位置，返回第一步走法，广度优先探索
    private int first(int n){
        while (first[n] != 1)
            n = first[n];
            for(int i = 0; i < 4; i++){
                int tempX = tempPosX[first[n]] + direction[i][0];
                int tempY = tempPosY[first[n]] + direction[i][1];
                if(tempX == tempPosX[n] && tempY == tempPosY[n])
                    return i;
            }
        return -1;
    }

    //移动所有的怪物
    public static void moveMonsters(Monster[] monsters, Icon[][] map, int hX, int hY) {
        for (Monster monster1 : monsters)
            if (monster1 != null && monster1.x != 0) {

                //清除怪物移动痕迹
                monster1.moveMonster(map, hX, hY);

            }
    }

    //获得怪物位置坐标
    int getX()
    {
        return x;
    }
    int getY()
    {
        return y;
    }

    //让怪物坐标清零
    void to0() {
        x = 0;
        y = 0;
    }


    //定义方法构建怪物数组
    public static Monster[] makeMonsters(int x, int y, Icon[][] map)
    {
        Monster[] monsters = new Monster[x + y];
        for(int i = 0; i < x; i++)
            monsters[i] = new Monster(map, 0);
        for(int i = 0; i < y; i++)
            monsters[i + x] = new Monster(map, 1);
        return monsters;
    }

    //获得血量
    int getBlood(){
        return blood;
    }

    //获得血量
    void setBlood(int blood){
        this.blood = blood;
    }

    //获得等级
    int getStatus(){
        return status;
    }
}
