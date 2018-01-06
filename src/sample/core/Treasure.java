package sample.core;
import java.io.Serializable;

public class Treasure extends Entity implements Serializable
{

    //位置坐标初始化
    private Treasure(Icon[][] map)
    {

        //宝物能否放置
        boolean canPut = true;

        while(canPut)
        {

            //产生随机方向
            int i = (int) (Math.random() * map.length);

            //随机选择合适位置
            for (int j = (int) (Math.random() * map[0].length); j < map[i].length; j++)
            {

                //不能放置的一些地方
                if (map[i][j] != Icon.END && map[i][j] != Icon.WALL
                        && map[i][j] != Icon.TREASURE && i != 1)
                {

                    x = i;
                    y = j;

                    //让当前位置变为宝物
                    map[x][y] = Icon.TREASURE;

                    //跳出循环
                    canPut = false;
                    break;
                }
            }
        }
    }

    //定义方法构建宝物数组
    public static Treasure[] makeTreasures(int x, Icon[][] map)
    {
        Treasure[] treasures = new Treasure[x];
        for(int i = 0; i < x; i++)
            treasures[i] = new Treasure(map);
        return treasures;
    }
}
