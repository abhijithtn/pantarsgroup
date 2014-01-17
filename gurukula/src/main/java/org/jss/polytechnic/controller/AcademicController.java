package org.jss.polytechnic.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.jss.polytechnic.bean.Result;
import org.jss.polytechnic.dao.QueryData;
import org.jss.polytechnic.dao.ResultDao;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

@ManagedBean(name = "academicController")
@ViewScoped
public class AcademicController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1762563989143654209L;

	private LazyDataModel<Result> lazyModel;

	private Result result = new Result();
	private Result selectedResult = new Result();
	private transient ResultDao dao = new ResultDao();

	@PostConstruct
	void intializeSession() {
		FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}

	/**
	 * @return the result
	 */
	public Result getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(Result result) {
		this.result = result;
	}

	/**
	 * @return the selectedResult
	 */
	public Result getSelectedResult() {
		return selectedResult;
	}

	/**
	 * @param selectedResult
	 *            the selectedResult to set
	 */
	public void setSelectedResult(Result selectedResult) {
		this.selectedResult = selectedResult;
	}

	public void resetSelectedAddress() {
		this.selectedResult = new Result();
	}

	public void onRowSelect(SelectEvent event) {
		selectedResult = (Result) event.getObject();
	}

	public LazyDataModel<Result> getLazyModel() {
		if (lazyModel == null) {
			lazyModel = new LazyDataModel<Result>() {

				private static final long serialVersionUID = 1L;

				@Override
				public Result getRowData(String rowKey) {
					return fetchRow(rowKey);
				}

				@Override
				public Object getRowKey(Result object) {
					// return StringUtils.defaultIfEmpty(object.getRegNo(),
					// null);
					return object;
				}

				@Override
				public List<Result> load(int first, int pageSize,
						String sortField, SortOrder sortOrder,
						Map<String, String> filters) {

					String order = (SortOrder.DESCENDING.equals(sortOrder)) ? "DESC"
							: "ASC";

					Result result = new Result();
					result.mask(filters);

					QueryData<Result> data = new QueryData<Result>(first,
							pageSize, sortField, order, result);

					dao.search(data);

					this.setRowCount(data.getTotalResultCount());

					return data.getData();
				}

				@Override
				public void setRowIndex(int rowIndex) {
					if (rowIndex == -1 || getPageSize() == 0) {
						super.setRowIndex(-1);
					} else {
						super.setRowIndex(rowIndex % getPageSize());
					}
				}

			};
		}
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<Result> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public Result fetchRow(final String rowKey) {
		Result filter = new Result();
		result.setRegNo(rowKey);
		QueryData<Result> data = new QueryData<Result>(0, 1, "studentName",
				"DESC", filter);
		dao.search(data);
		if (data.getData().size() > 0) {
			return data.getData().get(0);
		}

		return null;
	}

	public List<Result> findAll() {
		Result filter = new Result();
		QueryData<Result> data = new QueryData<Result>(0, 1000000000,
				"studentName", "DESC", filter);
		dao.search(data);
		return data.getData();
	}
}
