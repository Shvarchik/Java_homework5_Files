import java.io.*;
import java.util.Random;

public class TicTacToeFieldTo3ByteFile {

    private static int fieldSizeX = 3; // Размерность игрового поля
    private static int fieldSizeY = 3; // Размерность игрового поля
    private static String resultFileName;
    private static int [][] field; // Двумерный массив хранит текущее состояние игрового поля

    private static final Random random = new Random();

    public static void main(String[] args) {
        initialize();
        printField();
        resultFileName = "rezultfile.txt";
        saveToFile(resultFileName);
        //checkRezult();

    }
    /**
     * Инициализация игрового поля
     */
    private static void initialize() {

        field = new int [fieldSizeX][fieldSizeY];
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                // Проинициализируем все элементы массива
                // 0 – это пустое поле, 1 – это поле с крестиком, 2 – это поле с ноликом,
                //3 – резервное значение
                field[x][y] = random.nextInt(4);
            }
        }
    }
    /**
     * Отрисовка игрового поля
     */
    private static void printField() {
        System.out.print("--|");
        for (int i = 0; i < fieldSizeY; i++) {
            System.out.printf(" %d  ", i + 1);
        }
        System.out.println();

        for (int i = 0; i < fieldSizeX; i++) {
            System.out.print((i + 1 < 10) ? i + 1 + " |" : i + 1 + "|");

            for (int j = 0; j < fieldSizeY; j++)
                System.out.print(" " + field[i][j] + " |");

            System.out.println();
        }

        for (int i = 0; i < fieldSizeX * 2 + 2; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    /**
     * Метод получения массива бинарных строк (из каждой строки поля - одна строка)
     *
     * @return массив строк по 8 знаков 00******, где каждая пара ** - одна позиция в игровом поле
     */
    public static String [] getBinaryLines (){

        String [] strArr = new String[fieldSizeX];
        for (int x = 0; x<fieldSizeX; x++) {
            StringBuilder str = new StringBuilder("00");
            for (int y = 0; y < fieldSizeY; y++) {
                String strCell = Integer.toBinaryString(field[x][y]);
                if (strCell.length() < 2)
                    str.append('0');
                str.append(strCell);
            }
            strArr[x] = str.toString();
            //System.out.println(strArr[x]);
            str.setLength(0);
        }
        return strArr;
    }

    /**
     * Запись в файл полученного массива бинарных строк
     * @param fileName
     */
    public static void saveToFile (String fileName) {

        String [] arr = getBinaryLines();
        try (ByteArrayOutputStream bout = new ByteArrayOutputStream();
             FileOutputStream fout=new FileOutputStream(fileName)){

            for (int i = 0; i < arr.length; i++) {
                int lineAsInt = Integer.parseUnsignedInt(arr[i], 2);
                //System.out.println(lineAsInt);
                bout.write(lineAsInt);
            }
            bout.writeTo(fout);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Проверка результата
      */

    /*public static void checkRezult () {
        try (FileInputStream fis = new FileInputStream(resultFileName)){
            int i;
            while ((i = fis.read()) != -1){
                System.out.println(i);
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }*/
}
