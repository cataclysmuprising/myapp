jQuery.validator.addMethod("isMaskValid", function(value, element) {
	return $(element).inputmask("isComplete");
});

function applyCoaCodeMask(pElementIds) {
	mask(pElementIds, "9{5,10}[-9{0,5}]");
}

function applyCurCodeMask(pElementIds) {
	mask(pElementIds, "A{3}");
}

function applyAmountMask(pElementIds, pPrecision, pScale) {
	mask(pElementIds, "9{0," + pPrecision + "}[.9{0," + pScale + "}]");
}

function applyNumericMask(pElementIds) {
	mask(pElementIds, "9{0,}");
}

function mask(pElementIds, format) {
	$(pElementIds).inputmask({
		mask: format,
		greedy: false
	});
}