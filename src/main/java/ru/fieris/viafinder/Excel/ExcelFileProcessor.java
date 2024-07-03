package ru.fieris.viafinder.Excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Класс, занимающийся всей обработкой эксель документа
 */
public class ExcelFileProcessor {

    private final LinkedList<String> titleCellList = new LinkedList<>();
    private final LinkedList<ExcelRow> VIAandVVARowList = new LinkedList<>();

    /**
     * Конструктор открывает эксель файл и сохраняет все нужные данные из него
     * @param file Принимает Excel файл
     */
    public ExcelFileProcessor(File file) {
        try (XSSFWorkbook workbook = new XSSFWorkbook(file)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();


            //Итератор для тайтлов таблицы
            Iterator<Cell> titleCellIterator = rowIterator.next().cellIterator();

            //сохранение тайтлов таблицы в лист
            while (titleCellIterator.hasNext()) {
                titleCellList.add(titleCellIterator.next().getStringCellValue());
            }


            //Сохранение виа и вва товара в LinkedList
            while (rowIterator.hasNext()){
                Iterator<Cell> cellIterator = rowIterator.next().cellIterator();

                ExcelRow excelRow = new ExcelRow();
                excelRow.setMagazin(cellIterator.next().getStringCellValue());
                excelRow.setNa_sklade(cellIterator.next().getNumericCellValue());
                excelRow.setProdano(cellIterator.next().getNumericCellValue());
                excelRow.setArticul(cellIterator.next().getStringCellValue());
                excelRow.setNaimenovanie(cellIterator.next().getStringCellValue());
                excelRow.setProizvoditel(cellIterator.next().getStringCellValue());
                excelRow.setMassa(cellIterator.next().getNumericCellValue());
                excelRow.setShtrih_kod(cellIterator.next().getStringCellValue());
                excelRow.setPo_matrice(cellIterator.next().getNumericCellValue());

                if(excelRow.getNaimenovanie().toUpperCase().contains("ВИА") ||
                        excelRow.getNaimenovanie().toUpperCase().contains("ВВА")){
                    VIAandVVARowList.add(excelRow);
                }

            }


        } catch (IOException | InvalidFormatException e) {
            throw new RuntimeException(e);
        }

    }


    public LinkedList<String> getTitleCellList() {
        return titleCellList;
    }

    public LinkedList<ExcelRow> getVIAandVVARowList() {
        return VIAandVVARowList;
    }
}
