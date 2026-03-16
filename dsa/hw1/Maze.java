import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

public class Maze {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> maze = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            maze.add(line);
        }
        errorsEvaluation(maze);
        boolean[][] firstPath = findFirstPath(maze);
        boolean[][] mandatory = findMandatoryCells(maze, firstPath);
        printMaze(maze, mandatory);
    }

    public static char getCharAt(List<String> maze, int row, int col) {
        return maze.get(row).charAt(col);
    }

    public static void exitWithErrorMessage(String errorMessage) {
        System.err.println(errorMessage);
        System.exit(1);
    }

    public static void errorsEvaluation(List<String> maze){
        int width = getWidth(maze); //pocet znaku na 1. radku
        int height = getHeight(maze);        //pocet radku

        // 1. Error: Bludiste neni obdelnikove! – některý z řádků má jinou délku než první řádek
        for (String row: maze){
            if (row.length() != width){
                exitWithErrorMessage("Error: Bludiste neni obdelnikove!");
            }
        }

        // 2. Error: Vstup neni vlevo nahore! – druhý znak v prvním řádku není {.}
        if (getCharAt(maze, 0, 1) != '.'){
            exitWithErrorMessage("Error: Vstup neni vlevo nahore!");
        }

        // 3. Error: Vystup neni vpravo dole! – předposlední znak v posledním korektně načteném řádku není .
        if (getCharAt(maze, height-1, width-2) != '.'){
            exitWithErrorMessage("Error: Vystup neni vpravo dole!");
        }

        // 4. Error: Sirka bludiste je mimo rozsah! – šířka bludiště (počet znaků na řádku) musí být v rozmezí 5 až 100 znaků
        if (width < 5 || width > 100){
            exitWithErrorMessage("Error: Sirka bludiste je mimo rozsah!");
        }

        // 5. Error: Delka bludiste je mimo rozsah! – délka bludiště musí být v rozmezí 5 až 50 řádků
        if (height < 5 || height > 50){
            exitWithErrorMessage("Error: Delka bludiste je mimo rozsah!");
        }

        // 6. Error: Bludiste obsahuje nezname znaky! – znak na vstupu není # nebo . (neznámý znak může být i mezera nebo tabulátor)
        for (int row = 0; row < height; row++){
            for (int col = 0; col < width; col++){
                if (getCharAt(maze, row, col) != '.' && getCharAt(maze, row, col) != '#'){
                    exitWithErrorMessage("Error: Bludiste obsahuje nezname znaky!");
                }
            }
        }

        // 7. Error: Bludiste neni oplocene! – neplatí, že kromě vstupu a výstupu z bludiště jsou všechny znaky po obvodu bludiště {#}
        //horni radek
        for (int col = 0; col < width; col++){
            if (col == 1) continue;
            if (getCharAt(maze, 0, col) != '#') exitWithErrorMessage("Error: Bludiste neni oplocene!");
        }
        //dolni radek
        for (int col = 0; col < width; col++){
            if (col == width-2) continue;
            if (getCharAt(maze, height-1, col) != '#') exitWithErrorMessage("Error: Bludiste neni oplocene!");
        }
        //levy sloupec
        for (int row = 0; row < height; row++){
            if(getCharAt(maze, row, 0) != '#'){
                exitWithErrorMessage("Error: Bludiste neni oplocene!");
            }
        }
        //pravy sloupec
        for (int row = 0; row < height; row++){
            if(getCharAt(maze, row, width-1) != '#'){
                exitWithErrorMessage("Error: Bludiste neni oplocene!");
            }
        }

        // 8. Error: Cesta neexistuje! - neexistuje ani jedna cesta bludištěm
        boolean[][] visited = new boolean[height][width];
        if (!dfs(maze, 0, 1, visited, height, width)){
            exitWithErrorMessage("Error: Cesta neexistuje!");
        }
    }

    public static int getWidth(List<String> maze){
        return maze.get(0).length();
    }

    public static int getHeight(List<String> maze){
        return maze.size();
    }


    public static boolean dfs(List<String> maze, int row, int col, boolean[][] visited, int height, int width){
        if (row == height-1 && col == width-2){
            return true;
        }

        visited[row][col] = true;

        if (row-1 >= 0 && !visited[row - 1][col] && getCharAt(maze, row-1, col) != '#'){
            if(dfs(maze, row - 1, col, visited, height, width)){
                return true;
            }
        }
        if (row+1 < height && !visited[row + 1][col] && getCharAt(maze, row+1, col) != '#'){
            if(dfs(maze, row + 1, col, visited, height, width)){
                return true;
            }
        }
        if (col-1 >= 0 && !visited[row][col - 1] && getCharAt(maze, row, col-1) != '#'){
            if(dfs(maze, row, col - 1, visited, height, width)){
                return true;
            }
        }
        if (col+1 < width && !visited[row][col + 1] && getCharAt(maze, row, col+1) != '#'){
            if(dfs(maze, row, col + 1, visited, height, width)){
                return true;
            }
        }

        return false;
    }

    public static boolean dfs(List<String> maze, int row, int col, boolean[][] visited, int height, int width, boolean[][] path){
        if (row == height-1 && col == width-2){
            path[row][col] = true;
            return true;
        }

        visited[row][col] = true;

        if (row-1 >= 0 && !visited[row - 1][col] && getCharAt(maze, row-1, col) != '#'){
            if(dfs(maze, row - 1, col, visited, height, width, path)){
                path[row][col] = true;
                return true;
            }
        }
        if (row+1 < height && !visited[row + 1][col] && getCharAt(maze, row+1, col) != '#'){
            if(dfs(maze, row + 1, col, visited, height, width, path)){
                path[row][col] = true;
                return true;
            }
        }
        if (col-1 >= 0 && !visited[row][col - 1] && getCharAt(maze, row, col-1) != '#'){
            if(dfs(maze, row, col - 1, visited, height, width, path)){
                path[row][col] = true;
                return true;
            }
        }
        if (col+1 < width && !visited[row][col + 1] && getCharAt(maze, row, col+1) != '#'){
            if(dfs(maze, row, col + 1, visited, height, width, path)){
                path[row][col] = true;
                return true;
            }
        }

        return false;
    }

    public static boolean dfs(List<String> maze, int row, int col, boolean[][] visited, int height, int width, boolean[][] path, boolean[][] blocked){
        if (blocked != null && blocked[row][col]) {
            return false;
        }

        if (row == height-1 && col == width-2){
            path[row][col] = true;
            return true;
        }

        visited[row][col] = true;

        if (row-1 >= 0 && !visited[row - 1][col] && getCharAt(maze, row-1, col) != '#'){
            if(dfs(maze, row - 1, col, visited, height, width, path, blocked)){
                path[row][col] = true;
                return true;
            }
        }
        if (row+1 < height && !visited[row + 1][col] && getCharAt(maze, row+1, col) != '#'){
            if(dfs(maze, row + 1, col, visited, height, width, path, blocked)){
                path[row][col] = true;
                return true;
            }
        }
        if (col-1 >= 0 && !visited[row][col - 1] && getCharAt(maze, row, col-1) != '#'){
            if(dfs(maze, row, col - 1, visited, height, width, path, blocked)){
                path[row][col] = true;
                return true;
            }
        }
        if (col+1 < width && !visited[row][col + 1] && getCharAt(maze, row, col+1) != '#'){
            if(dfs(maze, row, col + 1, visited, height, width, path, blocked)){
                path[row][col] = true;
                return true;
            }
        }

        return false;
    }

    public static boolean[][] findFirstPath(List<String> maze){
        int height = getHeight(maze);
        int width = getWidth(maze);
        boolean[][] visited = new boolean[height][width];
        boolean[][] path = new boolean[height][width];
        dfs(maze, 0, 1, visited, height, width, path);
        return path;
    }

    public static boolean[][] findMandatoryCells(List<String> maze, boolean[][] path){
        boolean[][] mandatory = new boolean[path.length][path[0].length];
        boolean[][] blocked = new boolean[path.length][path[0].length];

        for (int row = 0; row < path.length; row++){
            for (int col = 0; col < path[row].length; col++){
                if (path[row][col] == false) continue;

                blocked[row][col] = true;
                boolean[][] visited2 = new boolean[path.length][path[0].length];
                boolean[][] tempPath = new boolean[path.length][path[0].length];

                if (!dfs(maze, 0, 1, visited2, path.length, path[0].length, tempPath, blocked)) {
                    mandatory[row][col] = true;
                }
                blocked[row][col] = false;
            }
        }

        return mandatory;
    }

    public static void printMaze(List<String> maze, boolean[][] mandatory) {
        int height = getHeight(maze);
        int width = getWidth(maze);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (mandatory[row][col]) {
                    System.out.print('!');
                } else {
                    System.out.print(getCharAt(maze, row, col));
                }
            }
            System.out.println();
        }
    }

}
