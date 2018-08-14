/**
 * Get criteria bean with company code.
 * 
 * @author Naing Win Htun
 * @since 03-May-2018
 */
function getDefaultCriteria() {
	return {
		companyCode: $("#companyCode").val(),
		order: "ASC"
	};
}

/**
 * Checking value is not empty or not null.
 * 
 * @param value
 *            value
 * @return status
 * 
 * @author Naing Win Htun
 * @since 20-October-2016
 */
function isNotEmpty(pValue) {
	if (null == pValue) {
		return false;
	}
	else if (pValue.toString() != "") {
		return true;
	}
	return false;
}

/**
 * Reset Form.
 * 
 * @param pSelector
 * 			element selector
 * 
 * @author Naing Win Htun
 * @since 03-May-2018
 */
function resetForm(pSelector) {
	$(isNotEmpty(pSelector) ? pSelector : 'form').trigger('reset');
}

/**
 * Select Picker by Value.
 * 
 * @param pSelector
 * 			element selector
 * @param pValue
 * 			selected value
 * 
 * @author Naing Win Htun
 * @since 03-May-2018
 */
function setSelect(pSelector, pValue) {

	$(pSelector).val(pValue).selectpicker('refresh');
}

/**
 * Get the display text of selected option.
 * 
 * @param selector
 * 			Element Selector
 * 
 * @returns Display Text
 * 
 * @author Naing Win Htun
 * @since 03-May-2018
 */
function getSelectedOptionText(selector) {
	return $(selector).find("option:selected").text();
}

/**
 * Get the display sub-text of selected option.
 * 
 * @param selector
 * 			Element Selector
 * 
 * @returns Display Sub-Text
 * 
 * @author May Thu Hnin
 * @since 30-May-2018
 */
function getSelectedOptionSubText(selector) {
	return $(selector).find('option:selected').attr('data-subtext');
}

/**
 * Add thousand separator in amount.
 * 
 * @param pAmount
 *            pAmount
 * @returns formatted amount string

 * @author Naing Win Htun
 * @since 26-December-2016
 */
function mFormatAmount(pAmount) {

	if (isNotEmpty(pAmount)) {

		var parts = pAmount.toString().split(".");

		parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");

		if (parts.length > 1) {

			if (parts[1].length == 0) {
				parts[1] = parts[1] + "00";
			}
			else if (parts[1].length == 1) {
				parts[1] = parts[1] + "0";
			}

			return parts.join(".");
		}
		else {
			return parts[0] + ".00";
		}
	}
	else {
		return "-";
	}
}

/**
 * Transform double exponential value to user readable value.
 * 
 * @param value
 *            exponential value
 * @return transformed value
 * @author Naing Win Htun
 * @since 21-November-2016
 */
function transformDouble(value) {

	if (isNotEmpty(value)) {

		var separator = "";

		if (value.indexOf("E") > -1) {
			separator = "E";
		}
		else if (value.indexOf("e") > -1) {
			separator = "e";
		}
		else {
			return value;
		}

		var splitValue = value.split(separator);

		value = splitValue[0].replace(".", "");

		var dotLength = parseInt(splitValue[1]);

		if (value.length < (dotLength + 1)) {
			for (var index = value.length; index < (dotLength + 1); index++) {
				value += "0";
			}
		}

		if (isNotEmpty(value.substring(dotLength + 1, value.length))) {
			value = value.substring(0, dotLength + 1) + "." + value.substring(dotLength + 1, value.length);
		}
		else {
			value = value.substring(0, dotLength + 1) + ".0";
		}

		return value;
	}
}

/**
 * Adding JQuery validate method for AlphaNumeric
 * 
 * @author Naing Win Htun
 * @since 14-May-2018
 */
function addAlphaNumericValidator() {
	jQuery.validator.addMethod("regAlphaNumeric", function(value, element, params) {
		return isRegValid("[a-zA-Z0-9 ]{" + params + "}", value);
	});
}

/**
 * Adding JQuery validate method for AlphaBatics
 * 
 * @author May Thu Hnin
 * @since 14-May-2018
 */
function addAlphaBaticsValidator() {
	jQuery.validator.addMethod("regAlphaBatics", function(value, element, params) {
		return isRegValid("[a-zA-Z_ ]{" + params + "}", value);
	});
}

/**
 * Adding JQuery validate method for Reference No
 * 
 * @author May Thu Hnin
 * @since 14-May-2018
 */
function addReferenceNoValidator() {
	jQuery.validator.addMethod("regReferenceNo", function(value, element, params) {
		return isRegValid("[a-zA-Z0-9-# ]{" + params + "}", value);
	});
}

/**
 * Adding JQuery validate method for Account Name
 * 
 * @author May Thu Hnin
 * @since 14-May-2018
 */
function addACNameValidator() {
	jQuery.validator.addMethod("regAccountName", function(value, element, params) {
		return isRegValid("[a-zA-Z0-9-/()& ]{" + params + "}", value);
	});
}

/**
 * Adding JQuery validate method for Coa Currency Code
 * 
 * @param pCurrencyCode
 * 			Coa Currency Code
 * @returns status (valid or not)
 * 
 * @author May Thu Hnin	
 * @since 30-May-2018
 */
function addCoaCurrencyValidator(pCurrencyCode) {

	jQuery.validator.addMethod("coaCheck", function(value, element, params) {

		var mCoa = getBeanByIdUsingAjax(getEmsResourcePath() + 'coa/' + value);

		if (mCoa == undefined || null == mCoa) {
			return false;
		}

		if ("SINGLECURRENCY" == mCoa.currencyType && mCoa.currencyCode != pCurrencyCode) {
			return false;
		}

		return true;
	});
}
/**
 * Check value is valid for regular expression or not.
 * 
 * @param pPattern
 * 			Reg Pattern
 * @param pValue
 * 			Check Value
 * @returns status (valid or not)
 * 
 * @author Naing Win Htun
 * @since 14-May-2018
 */
function isRegValid(pPattern, pValue) {

	var mRegExp = new RegExp('^' + pPattern + '$');

	return mRegExp.test(pValue);
}

/**
 * Get table meta data for draw (order infos and offset infos).
 * 
 * @param pTable
 * 			Table Selector
 * @param pData
 * 			Datatable Data
 * @param pCriteria
 * 			Request Criteria
 * 
 * @author Naing Win Htun
 * @since 24-May-2018
 */
function getTableMetaData(pTable, pData) {

	var mCriteria = {};

	if (pData.order.length > 0) {

		var index = $(pData.order[0])[0].column;

		var dir = $(pData.order[0])[0].dir;

		var head = $(pTable).find("thead");

		var sortColumn = head.find("th:eq(" + index + ")");

		mCriteria.order = dir.toUpperCase();

		mCriteria.orderBy = $(sortColumn).attr("data-id");
	}

	mCriteria.offset = pData.start;

	mCriteria.limit = pData.length;

	return mCriteria;
}

/**
 * Bind enter event to keyword text field.
 * 
 * @param pTable
 * 			Table Selector
 * @param pSelector
 * 			ELement Selector
 * 
 * @author Naing Win Htun
 * @since 24-May-2018
 */
function bindEnterEventToKeyword(pTable, pSelector) {

	if (!isNotEmpty(pSelector)) {
		pSelector = "#keyword";
	}

	$(pSelector).off('.DT').on('keyup.DT', function(e) {
		if (e.keyCode == 13) {
			pTable.draw();
		}
	});
}

/**
 * Get value for not required field. Show '-' if emtpy.
 * 
 * @param pValue
 * 			Field Value
 * @returns value
 * 
 * @author Naing Win Htun
 * @since 24-May-2018
 */
function getValueForNotRequired(pValue) {
	if (isNotEmpty(pValue)) {
		return pValue;
	}
	else {
		return '<div class="text-center"> - </span>';
	}
}

/**
 * Get checkbox element for data table row.
 * 
 * @param pId
 * 			Row Data Id
 * 
 * @returns check box element
 * 
 * @author Naing Win Htun
 * @since 24-May-2018
 */
function getTableCheckBox(pId) {
	return '<input data-input-type="iCheck" data-id="' + pId + '"type="checkbox">';
}

/**
 * Get month form date (format: YYYYMM)
 * 
 * @param pDate
 * 			Date
 * 
 * @returns month
 * 
 * @author Naing Win Htun
 * @since 24-May-2018
 */
function getMonthFromDate(pDate) {

	var mDataList = pDate.split("-");

	return mDataList[2] + "" + mDataList[1];
}

/**
 * Clear data and re-draw datatable
 * 
 * @param pTable
 * 			Data Table
 * @param pDataList
 * 			Data List
 * 
 * @author Naing Win Htun
 * @since 24-May-2018
 */
function reloadDataTable(pTable, pDataList) {
	pTable.clear().rows.add(pDataList).draw();
}

/**
 * Get the resource url for EMS.
 * 
 * @returns EMS API URL
 * 
 * @author Naing Win Htun
 * @since 25-May-2018
 */
function getEmsResourcePath() {
	return getContextPath() + '/resource/ems/';
}

/**
 * Get the application controller request url.
 * 
 * @param pUrl
 * 			Request Url
 * @returns Complete Request URL
 * 
 * @author Naing Win Htun
 * @since 05-Jun-2018
 */
function getAppRequestUrl(pUrl) {
	return getContextPath() + "/" + getCompanyCode() + "/" + (isNotEmpty(pUrl) ? pUrl : "");
}

/**
 * Getting company code from hidden form field.
 * 
 * @returns company code
 * 
 * @author Naing Win Htun
 * @since 05-Jun-2018
 */
function getCompanyCode() {
	return $("#companyCode").val();
}

/**
 * Get single data bean using data id with AJAX request.
 * 
 * @param pUrl
 * 			Request URL
 * @returns data bean
 * 
 * @author Naing Win Htun
 * @since 30-May-2018
 */
function getBeanByIdUsingAjax(pUrl) {

	var mResult = {};

	$.ajax({
		async: false,
		type: "GET",
		contentType: "application/json",
		url: pUrl,
		dataType: 'json',
		timeout: 100000,
		success: function(pResult) {
			mResult = pResult;
		}
	});

	return mResult;
}