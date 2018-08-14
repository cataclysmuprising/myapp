var locDefault = "default";
var locCoaValId = "val-id";
var locFirstRow = "first";
var locLastRow = "last";

/**
 * Initialize currency list to drop down.
 * 
 * @param pElementIds
 * 			Element Id List
 * @param pSelected
 * 			Selected Value
 * 
 * @author Naing Win Htun
 * @since 03-May-2018
 */
function initCurDropDown(pElementIds, pSelected) {

	var mUrl = getPortalResourcePath() + 'currency/search/list';

	var mCriteria = getDefaultCriteria();

	mCriteria.orderBy = "currencyCode";

	pullForDropDown(pElementIds, mUrl, mCriteria, locDefault, pSelected, addOptionToCurPicker);
}

/**
 * Initialize rate code list to drop down.
 * 
 * @param pElementIds
 * 			Element Id List
 * @param pSelected
 * 			Selected Value
 * 
 * @author Naing Win Htun
 * @since 03-May-2018
 */
function initRcdDropDown(pElementIds, pSelected) {

	var mUrl = getApplicationResourcePath() + 'rate_code/search/list';

	var mCriteria = getDefaultCriteria();

	mCriteria.orderBy = "rateCode";

	pullForDropDown(pElementIds, mUrl, mCriteria, locDefault, pSelected, addOptionToRcdPicker);
}

/**
 * Initialize chart of account list to drop down.
 * 
 * @param pElementIds
 * 			Element Id List
 * @param pSelected
 * 			Selected Value
 * 
 * @author Naing Win Htun
 * @since 03-May-2018
 */
function initCoaDropDown(pElementIds, pFormat, pSelected) {

	var mUrl = getApplicationResourcePath() + 'coa/search/list';

	var mCriteria = getDefaultCriteria();

	mCriteria.active = true;

	mCriteria.orderBy = "accountCode";

	pullForDropDown(pElementIds, mUrl, mCriteria, isNotEmpty(pFormat) ? pFormat : locDefault, pSelected, addOptionToCoaPicker);
}

/**
 * Initialize source code list to drop down.
 * 
 * @param pElementIds
 * 			Element Id List
 * @param pSelected
 * 			Selected Value
 * 
 * @author Naing Win Htun
 * @since 03-May-2018
 */
function initScdDropDown(pElementIds, pSelected) {

	var mUrl = getApplicationResourcePath() + 'src/search/list';

	var mCriteria = getDefaultCriteria();

	mCriteria.withSystemSourceCodes = true;

	mCriteria.orderBy = "name";

	pullForDropDown(pElementIds, mUrl, mCriteria, locDefault, pSelected, addOptionToScdPicker);
}

/**
 * Initialize financial year list to drop down.
 * 
 * @param pElementIds
 * 			Element Id List
 * @param pSelected
 * 			Selected Value
 * 
 * @author Naing Win Htun
 * @since 03-May-2018
 */
function initFnyDropDown(pElementIds, pSelected) {

	var mUrl = getApplicationResourcePath() + 'financial_year/search/list';

	var mCriteria = getDefaultCriteria();

	pullForDropDown(pElementIds, mUrl, mCriteria, locDefault, pSelected, addOptionToFnyPicker);
}

/**
 * Initialize book type list to drop down.
 * 
 * @param pElementIds
 * 			Element Id List
 * @param pSelected
 * 			Selected Value
 * 
 * @author Naing Win Htun
 * @since 08-May-2018
 */
function initBotDropDown(pElementIds, pSelected) {

	var mUrl = getApplicationResourcePath() + 'set/search/list';

	var mCriteria = getDefaultCriteria();

	mCriteria.subGroup = "Book Type";
	mCriteria.orderBy = "name";

	pullForDropDown(pElementIds, mUrl, mCriteria, locDefault, pSelected, addOptionToBotPicker);
}

/**
 * Initialize chart of account list to drop down.
 * 
 * @param pElementIds
 * 			Element Id List
 * @param pSelected
 * 			Selected Value
 * 
 * @author Naing Win Htun
 * @since 03-May-2018
 */
function initGlAccDropDown(pElementIds, pSelected) {

	var mUrl = getEmsResourcePath() + 'coa/search/list';

	var mCriteria = getDefaultCriteria();

	mCriteria.active = true;

	mCriteria.orderBy = "accountCode";

	pullForDropDown(pElementIds, mUrl, mCriteria, locDefault, pSelected, addOptionToGlAccPicker);
}

/**
 * Add option to currency picker.
 * 
 * @param pElementIds
 * 			Element Id List
 * @param pDataList
 * 			Data List
 * @param pFormat
 * 			Display Format
 * @param pSelected
 * 			Selected Value
 * 
 * @author Naing Win Htun
 * @since 03-May-2018
 */
function addOptionToCurPicker(pElementIds, pDataList, pFormat, pSelected) {

	$.each(pDataList, function(key, data) {

		switch (pFormat) {

			case locDefault:
			default:
				$(pElementIds).append(getOption(data.currencyCode, data.id, data.description));
				break;
		}
	});

	if (isNotEmpty(pSelected)) {
		setSelect(pElementIds, pSelected);
	}
}

/**
 * Add option to rate code picker.
 * 
 * @param pElementIds
 * 			Element Id List
 * @param pDataList
 * 			Data List
 * @param pFormat
 * 			Display Format
 * @param pSelected
 * 			Selected Value
 * 
 * @author Naing Win Htun
 * @since 03-May-2018
 */
function addOptionToRcdPicker(pElementIds, pDataList, pFormat, pSelected) {

	$.each(pDataList, function(key, data) {

		switch (pFormat) {

			case locDefault:
			default:
				$(pElementIds).append(getOption(data.rateCode, data.id, data.description));
				break;
		}
	});

	if (isNotEmpty(pSelected)) {
		setSelect(pElementIds, pSelected);
	}
}

/**
 * Add option to gl account picker.
 * 
 * @param pElementIds
 * 			Element Id List
 * @param pDataList
 * 			Data List
 * @param pFormat
 * 			Display Format
 * @param pSelected
 * 			Selected Value
 * 
 * @author Naing Win Htun
 * @since 03-May-2018
 */
function addOptionToGlAccPicker(pElementIds, pDataList, pFormat, pSelected) {

	$.each(pDataList, function(key, data) {

		switch (pFormat) {

			case locDefault:
			default:
				$(pElementIds).append(getOption(data.accountCode, data.id, data.description));
				break;
		}
	});

	if (isNotEmpty(pSelected)) {
		setSelect(pElementIds, pSelected);
	}
}

/**
 * Add option to chart of account picker.
 * 
 * @param pElementIds
 * 			Element List
 * @param pDataList
 * 			Data List
 * @param pFormat
 * 			Display Format
 * @param pSelected
 * 			Selected Value
 * 
 * @author Naing Win Htun
 * @since 03-May-2018
 */
function addOptionToCoaPicker(pElementIds, pDataList, pFormat, pSelected) {

	var first = "";
	var last = "";

	$.each(pDataList, function(key, data) {

		switch (pFormat) {

			case locCoaValId: {
				$.each(pElementIds, function(key, element) {
					$(element.id).append(getOption(data.accountCode, data.id, data.description));
				});

				if ("" == first) {
					first = data.id;
				}

				last = data.id;

				break;
			}

			case locDefault:
			default: {
				$.each(pElementIds, function(key, element) {
					$(element.id).append(getOption(data.accountCode, data.accountCode, data.description));
				});

				if ("" == first) {
					first = data.accountCode;
				}

				last = data.accountCode;

				break;
			}
		}
	});

	$.each(pElementIds, function(key, element) {

		if ("first" == element.value) {

			$(element.id).val(first);
		}
		else if ("last" == element.value) {

			$(element.id).val(last);
		}
		else {
			$(element.id).val(element.value);
		}
	});
}

/**
 * Add option to source code picker.
 * 
 * @param pElementIds
 * 			Element Id List
 * @param pDataList
 * 			Data List
 * @param pFormat
 * 			Display Format
 * @param pSelected
 * 			Selected Value
 * 
 * @author Naing Win Htun
 * @since 03-May-2018
 */
function addOptionToScdPicker(pElementIds, pDataList, pFormat, pSelected) {

	$.each(pDataList, function(key, data) {

		switch (pFormat) {

			case locDefault:
			default:
				$(pElementIds).append(getOption(data.name, data.id, data.description));
				break;
		}
	});

	if (isNotEmpty(pSelected)) {
		setSelect(pElementIds, pSelected);
	}
}

/**
 * Add option to financial year picker.
 * 
 * @param pElementIds
 * 			Element Id List
 * @param pDataList
 * 			Data List
 * @param pFormat
 * 			Display Format
 * @param pSelected
 * 			Selected Value
 * 
 * @author Naing Win Htun
 * @since 03-May-2018
 */
function addOptionToFnyPicker(pElementIds, pDataList, pFormat, pSelected) {

	var selected = "";

	$.each(pDataList, function(key, data) {

		if ("ACTIVE" == data.status && "PRIMARY" == data.calType) {
			selected = data.id;
		}

		switch (pFormat) {

			case locDefault:
			default:
				$(pElementIds).append(getOption(data.code + " - [" + data.startDate + ' ~ ' + data.endDate + "]", data.id));
				break;
		}
	});

	$(pElementIds).val(selected);
}

/**
 * Add option to book type picker.
 * 
 * @param pElementIds
 * 			Element Id List
 * @param pDataList
 * 			Data List
 * @param pFormat
 * 			Display Format
 * @param pSelected
 * 			Selected Value
 * 
 * @author Naing Win Htun
 * @since 08-May-2018
 */
function addOptionToBotPicker(pElementIds, pDataList, pFormat, pSelected) {

	$.each(pDataList, function(key, data) {

		switch (pFormat) {

			case locDefault:
			default:
				$(pElementIds).append(getOption(data.name, data.value));
				break;
		}
	});

	if (isNotEmpty(pSelected)) {
		setSelect(pElementIds, pSelected);
	}
}

/**
 * Get option HTML tag.
 * 
 * @param pText
 * 			Display Text
 * @param pValue
 * 			Value
 * @param pSubText
 * 			Sub Display Text
 * @returns option tag
 * 
 * @author Naing Win Htun
 * @since 03-May-2018
 */
function getOption(pText, pValue, pSubText) {
	return "<option value='" + pValue + "'" + (isNotEmpty(pSubText) ? " data-subtext='" + pSubText + "'" : "") + ">" + pText + "</option>"
}

/**
 * Pull data list by AJAX for drop down and initialize options.
 * 
 * @param pElementIds
 * 			Element Id List
 * @param pUrl
 * 			Request Url for Data List
 * @param pCriteria
 * 			Request Criteria
 * @param pFormat
 * 			Drop Down Display Format
 * @param pProcess
 * 			Function for add option to drop down.
 * @param pSelected
 * 			Selected Value
 * 
 * @author Naing Win Htun
 * @since 03-May-2018
 */
function pullForDropDown(pElementIds, pUrl, pCriteria, pFormat, pSelected, pProcess) {

	$.ajax({
		async: false,
		type: "POST",
		contentType: "application/json",
		url: pUrl,
		dataType: 'json',
		timeout: 100000,
		data: JSON.stringify(pCriteria),
		success: function(mResultList) {
			pProcess(pElementIds, mResultList, pFormat, pSelected);
		}
	});
}