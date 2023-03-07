package com.vasenoi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) throws IOException {
        int nColumn;
        try {
            // первый аргумент переданый при запуске программы принимается за номер столбца
            nColumn = Integer.valueOf(args[0])-1;
        } catch (Exception e) {
            // в случае если при запуске программы передан не корректный аргумент запрашивается ввод номера столбца
            System.out.print("Введите номер колонки: ");
            String search = new BufferedReader(new InputStreamReader(System.in)).readLine().toLowerCase();
            nColumn = Integer.valueOf(search)-1;
        }

        String line = "";
        String splitBy = ",";
        String path = "airports.csv";
        String search = "";
        long time;
        ArrayList<String> column = new ArrayList<String>();
        ArrayList<String> tableRows = new ArrayList<String>();


        // второй аргумент переданный при вызове программы принимается за путь до файла
        try {
            if (args.length == 2) path = args[1];
        } catch (Exception e) {
            path = "airports.csv";
        }


        // Парсинг .csv файла
        try
        {
            time = System.currentTimeMillis();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            while ((line = bufferedReader.readLine()) != null)
            {
                tableRows.add(line);
                String[] row = line.split(splitBy); // символ splitBy принимается за разделитель
                column.add(row[nColumn]);
            }
            System.out.println("Парсинг занял " + (System.currentTimeMillis()-time) + " мс");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

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
}