package ru.fieris.viafinder.Excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Класс, занимающийся всей обработкой эксель документа
 */
public class ExcelProcessor {
    //Хранит дату из ячейки А1
    private final String dateInA1Cell;
    private final ArrayList<String> titleCellList = new ArrayList<>();
    private final ArrayList<ExcelRow> excelRowArrayList = new ArrayList<>();
    private final ArrayList<ExcelRow> VIAandVVARowArrayList = new ArrayList<>();

    /**
     * Конструктор открывает эксель файл и сохраняет все нужные данные из него
     *
     * @param file Принимает Excel файл
     */
    public ExcelProcessor(File file) {
        try (XSSFWorkbook workbook = new XSSFWorkbook(file)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();

            //сохраняет дату из ячейки А1
            dateInA1Cell = rowIterator.next().cellIterator().next().getStringCellValue();

            //Итератор для тайтлов таблицы
            Iterator<Cell> titleCellIterator = rowIterator.next().cellIterator();

            //сохранение тайтлов таблицы в лист
            while (titleCellIterator.hasNext()) {
                titleCellList.add(titleCellIterator.next().getStringCellValue());
            }


            //сохранение всей таблицы в excelRowArrayList
            while (rowIterator.hasNext()) {
                Iterator<Cell> cellIterator = rowIterator.next().cellIterator();

                ExcelRow excelRow = new ExcelRow();
                while (cellIterator.hasNext()) {
                    excelRow.getCells().add(cellIterator.next());
                }
                excelRowArrayList.add(excelRow);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }

        //создание ВИАиВВА листа
        for (ExcelRow row : excelRowArrayList) {
            if (row.cells.get(5).getStringCellValue().toUpperCase().contains("ВИА") ||
                    row.cells.get(5).getStringCellValue().toUpperCase().contains("ВВА")) {
                VIAandVVARowArrayList.add(row);
            }
        }

    }


    /**
     * Класс, представляющмй собой одну строку из эксель документа.
     * Работает как аррей лист, каждый индекс листа - ячейка формата Cell.
     */
    @Deprecated
    public class ExcelRow {
        private ArrayList<Cell> cells = new ArrayList<>();

        public ArrayList<Cell> getCells() {
            return cells;
        }
    }

    class ExcelRowOld {
        private Double na_sklade;
        private Double na_prodaju;
        private String rashojdenia;
        private String articul;
        private Double ID;
        private String naimenovanie;
        private String naimenivanie_dlya_cennikov;
        private String naimenovanie_dlya_chekov;
        private String proizvoditel;
        private Double massa_kg;
        private String yacheika;
        private String shtrih_kod;
        private String articulPostavshika;
        private Double kolichestvo_v_upakovke;
        private Double kolichestvo_v_pallete;
        private Double kolichestvo_v_sloe;
        private Double objem;
        private String kategoria;
        private Double cena_zakupki_po_poslednei_postavke;
        private Double cena_prodaji;
        private String tipi_praisov;
        private String shirina_cm;
        private String dlina_cm;
        private String visota_cm;
        private String kategoria_dlya_analitiki;

        public Double getNa_sklade() {
            return na_sklade;
        }

        public void setNa_sklade(Double na_sklade) {
            this.na_sklade = na_sklade;
        }

        public Double getNa_prodaju() {
            return na_prodaju;
        }

        public void setNa_prodaju(Double na_prodaju) {
            this.na_prodaju = na_prodaju;
        }

        public String getRashojdenia() {
            return rashojdenia;
        }

        public void setRashojdenia(String rashojdenia) {
            this.rashojdenia = rashojdenia;
        }

        public String getArticul() {
            return articul;
        }

        public void setArticul(String articul) {
            this.articul = articul;
        }

        public Double getID() {
            return ID;
        }

        public void setID(Double ID) {
            this.ID = ID;
        }

        public String getNaimenovanie() {
            return naimenovanie;
        }

        public void setNaimenovanie(String naimenovanie) {
            this.naimenovanie = naimenovanie;
        }

        public String getNaimenivanie_dlya_cennikov() {
            return naimenivanie_dlya_cennikov;
        }

        public void setNaimenivanie_dlya_cennikov(String naimenivanie_dlya_cennikov) {
            this.naimenivanie_dlya_cennikov = naimenivanie_dlya_cennikov;
        }

        public String getNaimenovanie_dlya_chekov() {
            return naimenovanie_dlya_chekov;
        }

        public void setNaimenovanie_dlya_chekov(String naimenovanie_dlya_chekov) {
            this.naimenovanie_dlya_chekov = naimenovanie_dlya_chekov;
        }

        public String getProizvoditel() {
            return proizvoditel;
        }

        public void setProizvoditel(String proizvoditel) {
            this.proizvoditel = proizvoditel;
        }

        public Double getMassa_kg() {
            return massa_kg;
        }

        public void setMassa_kg(Double massa_kg) {
            this.massa_kg = massa_kg;
        }

        public String getYacheika() {
            return yacheika;
        }

        public void setYacheika(String yacheika) {
            this.yacheika = yacheika;
        }

        public String getShtrih_kod() {
            return shtrih_kod;
        }

        public void setShtrih_kod(String shtrih_kod) {
            this.shtrih_kod = shtrih_kod;
        }

        public String getArticulPostavshika() {
            return articulPostavshika;
        }

        public void setArticulPostavshika(String articulPostavshika) {
            this.articulPostavshika = articulPostavshika;
        }

        public Double getKolichestvo_v_upakovke() {
            return kolichestvo_v_upakovke;
        }

        public void setKolichestvo_v_upakovke(Double kolichestvo_v_upakovke) {
            this.kolichestvo_v_upakovke = kolichestvo_v_upakovke;
        }

        public Double getKolichestvo_v_pallete() {
            return kolichestvo_v_pallete;
        }

        public void setKolichestvo_v_pallete(Double kolichestvo_v_pallete) {
            this.kolichestvo_v_pallete = kolichestvo_v_pallete;
        }

        public Double getKolichestvo_v_sloe() {
            return kolichestvo_v_sloe;
        }

        public void setKolichestvo_v_sloe(Double kolichestvo_v_sloe) {
            this.kolichestvo_v_sloe = kolichestvo_v_sloe;
        }

        public Double getObjem() {
            return objem;
        }

        public void setObjem(Double objem) {
            this.objem = objem;
        }

        public String getKategoria() {
            return kategoria;
        }

        public void setKategoria(String kategoria) {
            this.kategoria = kategoria;
        }

        public Double getCena_zakupki_po_poslednei_postavke() {
            return cena_zakupki_po_poslednei_postavke;
        }

        public void setCena_zakupki_po_poslednei_postavke(Double cena_zakupki_po_poslednei_postavke) {
            this.cena_zakupki_po_poslednei_postavke = cena_zakupki_po_poslednei_postavke;
        }

        public Double getCena_prodaji() {
            return cena_prodaji;
        }

        public void setCena_prodaji(Double cena_prodaji) {
            this.cena_prodaji = cena_prodaji;
        }

        public String getTipi_praisov() {
            return tipi_praisov;
        }

        public void setTipi_praisov(String tipi_praisov) {
            this.tipi_praisov = tipi_praisov;
        }

        public String getShirina_cm() {
            return shirina_cm;
        }

        public void setShirina_cm(String shirina_cm) {
            this.shirina_cm = shirina_cm;
        }

        public String getDlina_cm() {
            return dlina_cm;
        }

        public void setDlina_cm(String dlina_cm) {
            this.dlina_cm = dlina_cm;
        }

        public String getVisota_cm() {
            return visota_cm;
        }

        public void setVisota_cm(String visota_cm) {
            this.visota_cm = visota_cm;
        }

        public String getKategoria_dlya_analitiki() {
            return kategoria_dlya_analitiki;
        }

        public void setKategoria_dlya_analitiki(String kategoria_dlya_analitiki) {
            this.kategoria_dlya_analitiki = kategoria_dlya_analitiki;
        }
    }

    public ArrayList<ExcelRow> getExcelRowArrayList() {
        return excelRowArrayList;
    }

    public ArrayList<String> getTitleCellList() {
        return titleCellList;
    }

    public ArrayList<ExcelRow> getVIAandVVARowArrayList() {
        return VIAandVVARowArrayList;
    }
}
