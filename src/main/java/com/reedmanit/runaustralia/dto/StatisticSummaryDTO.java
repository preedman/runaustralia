package com.reedmanit.runaustralia.dto;

public class StatisticSummaryDTO {
    private String statisticName;
    private Long count;
    private Float minValue;
    private Float maxValue;
    private Double average;

    public StatisticSummaryDTO(String statisticName, Long count, Float minValue, Float maxValue, Double average) {
        this.statisticName = statisticName;
        this.count = count;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.average = average;
    }

    // Getters and setters
    public String getStatisticName() {
        return statisticName;
    }

    public Long getCount() {
        return count;
    }

    public Float getMinValue() {
        return minValue;
    }

    public Float getMaxValue() {
        return maxValue;
    }

    public Double getAverage() {
        return average;
    }
}

