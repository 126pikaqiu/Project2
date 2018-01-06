package sample.core;

import java.io.Serializable;

public class Map  implements Serializable {

    public Icon[][] map;

    //初始化地图
    public Map(int x, int y)
    {
        map = new Icon[x][y];
        for(int i = 0; i < x; i++)
            for(int j = 0; j < y; j++)
                map[i][j] = Icon.WALL;
        setRandomOriginalMap(1,1);
        map[map.length - 2][map[0].length - 2] = Icon.END;
    }

    //定义方法生成随机地图，最小生成树算法
    private void setRandomOriginalMap(int x, int y) {
        int[][] direction = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};//four directions
        for (int i = 0; i < 4; i++)//make the four directions disorganize
        {
            int n = (int) (Math.random() * 4);
            int t = direction[i][0];
            direction[i][0] = direction[n][0];
            direction[n][0] = t;
            t = direction[i][1];
            direction[i][1] = direction[n][1];
            direction[n][1] = t;
        }

        map[x][y] = Icon.EMPTY;
        //break the wall(try four directions)
        for (int i = 0; i < 4; i++)
        {
            if (x + 2 * direction[i][0] >= 0 && y + 2 * direction[i][1] >= 0
                    && x + direction[i][0] < map.length - 1 &&
                    y + direction[i][1] < map[0].length - 1 &&
                    map[x + 2 * direction[i][0]][y + 2 * direction[i][1]] == Icon.WALL)
            //if the adjacent of current position is wall
            {
                map[x + direction[i][0]][y + direction[i][1]] = Icon.EMPTY;
                //than break the wall between the current position and the adjacent
                if(x + 2 * direction[i][0] < map.length - 1 && y + 2 * direction[i][1] < map[0].length - 1)
                setRandomOriginalMap(x + 2 * direction[i][0], y + 2 * direction[i][1]);
                //to next position as current position and loop
            }
        }
    }
}
