package ru.fieris.viafinder.Excel;

public class ExcelRow {

        public String getMagazin() {
            return magazin;
        }

        public void setMagazin(String magazin) {
            this.magazin = magazin;
        }

        public Double getNa_sklade() {
            return na_sklade;
        }

        public void setNa_sklade(Double na_sklade) {
            this.na_sklade = na_sklade;
        }

        public Double getProdano() {
            return prodano;
        }

        public void setProdano(Double prodano) {
            this.prodano = prodano;
        }

        public String getArticul() {
            return articul;
        }

        public void setArticul(String articul) {
            this.articul = articul;
        }

        public String getNaimenovanie() {
            return naimenovanie;
        }

        public void setNaimenovanie(String naimenovanie) {
            this.naimenovanie = naimenovanie;
        }

        public String getProizvoditel() {
            return proizvoditel;
        }

        public void setProizvoditel(String proizvoditel) {
            this.proizvoditel = proizvoditel;
        }

        public Double getMassa() {
            return massa;
        }

        public void setMassa(Double massa) {
            this.massa = massa;
        }

        public String getShtrih_kod() {
            return shtrih_kod;
        }

        public void setShtrih_kod(String shtrih_kod) {
            this.shtrih_kod = shtrih_kod;
        }

        public Double getPo_matrice() {
            return po_matrice;
        }

        public void setPo_matrice(Double po_matrice) {
            this.po_matrice = po_matrice;
        }

        private String magazin;
        private Double na_sklade;
        private Double prodano;
        private String articul;
        private String naimenovanie;
        private String proizvoditel;
        private Double massa;
        private String shtrih_kod;
        private Double po_matrice;
}
