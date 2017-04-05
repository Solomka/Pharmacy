package com.upp.apteka.dto;

public class MedicineSeriesParam<T> {
	private String chartName;
	private T chartId;
	private T subsidId;

	public MedicineSeriesParam() {

	}

	public MedicineSeriesParam(String chartName, T chartId, T subsidId) {

		this.chartName = chartName;
		this.chartId = chartId;
		this.subsidId = subsidId;
	}

	public String getChartName() {
		return chartName;
	}

	public void setChartName(String chartName) {
		this.chartName = chartName;
	}

	public T getChartId() {
		return chartId;
	}

	public void setChartId(T chartId) {
		this.chartId = chartId;
	}

	public T getSubsidId() {
		return subsidId;
	}

	public void setSubsidId(T subsidId) {
		this.subsidId = subsidId;
	}

	@Override
	public String toString() {
		return "MedicineSeriesParam [chartName=" + chartName + ", chartId=" + chartId + ", subsidId=" + subsidId + "]";
	}

}
