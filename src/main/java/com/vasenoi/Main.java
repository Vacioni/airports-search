package com.vasenoi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    static int retype_nColumn() {
        while (true) {
            System.out.print("Введите корректный номер колонки: ");
            try {
                String input = new BufferedReader(new InputStreamReader(System.in)).readLine();
                return Integer.valueOf(input)-1;
            } catch (Exception e) {

            }
        }
    }

    public static void main(String[] args) throws IOException {
        // параметры по умолчанию
        int nColumn = 2;
        String path = "airports.csv";

        try {
            // при отсутствии переданных аргументов программа запрашивает ввод номера колонки
            // первый аргумент переданый при запуске программы принимается за номер столбца
            // второй аргумент переданный при вызове программы принимается за путь до файла
            switch (args.length) {
                case 0:
                    nColumn = retype_nColumn();
                    break;
                case 1:
                    try {
                        nColumn = Integer.valueOf(args[0])-1;
                    } catch (Exception e) {
                        nColumn = retype_nColumn();
                        break;
                    }
                    break;
                default:
                    nColumn = Integer.valueOf(args[0])-1;
                    path = args[1];
                    break;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        String search = "";
        long time;
        ArrayList<String> column = new ArrayList<String>();
        ArrayList<String> tableRows = new ArrayList<String>();


        try
        {
            // Парсинг .csv файла
            String line = "";
            String splitBy = ",";
            time = System.currentTimeMillis();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            while ((line = bufferedReader.readLine()) != null)
            {
                tableRows.add(line);
                String[] row = line.split(splitBy); // символ splitBy принимается за разделитель
                column.add(row[nColumn]);
            }
            System.out.println("Парсинг занял " + (System.currentTimeMillis()-time) + " мс");


            // Поиск
            while (true) {
                ArrayList<String> searchResult = new ArrayList<String>();

                // Ввод искомой строки
                System.out.print("Введите текст для поиска совпадений: ");
                search = new BufferedReader(new InputStreamReader(System.in)).readLine().toLowerCase();
                if (search.equals("!quit")) System.exit(0);

                // Поиск нужных элементов
                time = System.currentTimeMillis();
                for(int i = 0; i<column.size(); i++){
                    if (column.get(i).toLowerCase().contains(search)) searchResult.add(column.get(i)+"["+tableRows.get(i)+"]");
                }

                // Сортировка найденных строк
                Collections.sort(searchResult);

                // Вывод найденных строк
                for(String a : searchResult) System.out.println(a);
                time = System.currentTimeMillis()-time;
                System.out.println("Количество найденных строк: "+searchResult.size()+" Время затраченое на поиск: " + time + " мс");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}