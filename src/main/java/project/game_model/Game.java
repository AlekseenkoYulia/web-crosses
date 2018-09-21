package project.game_model;

public class Game {
    //public static int currentPlayer = 0;
    public static boolean over = false;

    static String canvas;

    public Game(){
        canvas = "000000000";
    }

    public void makeTurn(int player, int n){
        if (player == 0) {
            canvas = canvas.substring(0, n) + 'X' + canvas.substring(n + 1);
        }
        if (player == 1) {
            canvas = canvas.substring(0, n) + 'O' + canvas.substring(n + 1);
        }
        System.out.println(canvas);
        over = isGameOver(n);
    }

    static boolean isGameOver(int n) {
        //поиск совпадений по горизонтали
        int row = n - n % 3; //номер строки - проверяем только её
        if (canvas.charAt(row) == canvas.charAt(row + 1) &&
                canvas.charAt(row) == canvas.charAt(row + 2)) return true;
        //поиск совпадений по вертикали
        int column = n % 3; //номер столбца - проверяем только его
        if (canvas.charAt(column) == canvas.charAt(column + 3))
            if (canvas.charAt(column) == canvas.charAt(column + 6)) return true;
        //мы здесь, значит, первый поиск не положительного результата
        //если значение n находится на одной из граней - возвращаем false
        if (n % 2 != 0) return false;
        //проверяем принадлежит ли к левой диагонали значение
        if (n % 4 == 0) {
            //проверяем есть ли совпадения на левой диагонали
            if (canvas.charAt(0) == canvas.charAt(4) &&
                    canvas.charAt(0) == canvas.charAt(8)) return true;
            if (n != 4) return false;
        }
        return canvas.charAt(2) == canvas.charAt(4) &&
                canvas.charAt(2) == canvas.charAt(6);
    }
}
