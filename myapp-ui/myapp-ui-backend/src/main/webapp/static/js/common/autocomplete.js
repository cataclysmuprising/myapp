/**
 * Initialize batch list for auto complete.
 * 
 * @param pElementIds
 * 			Element Id List
 * 
 * @author Naing Win Htun
 * @since 03-May-2018
 */
function initBatchAutoComplete(pElementIds) {

	var mUrl = getApplicationResourcePath() + 'user_keyword/search/keywords';

	var mCriteria = getDefaultCriteria();

	mCriteria.type = "BATCH_KEYWORD";

	mCriteria.orderBy = "userKeyword";

	bindAutoComplete(pElementIds, mUrl, mCriteria);
}

/**
 * Pull data list by AJAX and bind for auto complete.
 * 
 * @param pElementIds
 * 			Element List
 * @param pUrl
 * 			Request Url for Data List
 * @param pCriteria
 * 			Request Criteria
 * 
 * @author Naing Win Htun
 * @since 03-May-2018
 */
function bindAutoComplete(pElementIds, pUrl, pCriteria) {

	$.ajax({
		type: "POST",
		contentType: "application/json",
		url: pUrl,
		dataType: 'json',
		timeout: 100000,
		data: JSON.stringify(pCriteria),
		success: function(mResultList) {
			$(pElementIds).autocomplete({
				source: mResultList
			});
		}
	});
}