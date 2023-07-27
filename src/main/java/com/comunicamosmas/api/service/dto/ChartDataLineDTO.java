package com.comunicamosmas.api.service.dto;

import java.math.BigInteger;
import java.util.List;

public class ChartDataLineDTO
{
    private List<String> labels;
    
    private List<Datasets> datasets;

    

    public List<String> getLabels() {
        return labels;
    }



    public void setLabels(List<String> labels) {
        this.labels = labels;
    }



    public List<Datasets> getDatasets() {
        return datasets;
    }



    public void setDatasets(List<Datasets> datasets) {
        this.datasets = datasets;
    }



    public static class Datasets{

        private String label;

        private List<Integer> data;

        private Boolean fill;

        private String borderColor;

        private Double tension;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public List<Integer> getData() {
            return data;
        }

        public void setData(List<Integer> data) {
            this.data = data;
        }

        public Boolean getFill() {
            return fill;
        }

        public void setFill(Boolean fill) {
            this.fill = fill;
        }

        public String getBorderColor() {
            return borderColor;
        }

        public void setBorderColor(String borderColor) {
            this.borderColor = borderColor;
        }

        public Double getTension() {
            return tension;
        }

        public void setTension(Double tension) {
            this.tension = tension;
        }

        

    }


}