package batattack;

public interface GameplayDetails {

    //GameMap
    int gameMapW = 1920;
    int gameMapH = 1080;
    int right = 150;
    int left = 10;
    int down = 960;

    //Virus
    int virusH = 75;

    //Bat
    int batH = 50;
    int batW = 100;
    int bat_SX = 30;
    int bat_YS = 5;
    int moveDown = 100; //how far down do bats move
    int allBats = 40; //number of bats

    //Game
    int random = 100; //the higher the number the least likely a bat is to drop a virus
    int gameDelay = 5; //the higher the number the faster the game

    //World
    int worldW = 90;
    int worldH = 100;
}