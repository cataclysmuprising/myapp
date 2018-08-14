function getRequiredMessage(pParams) {
	return "'" + pParams + "' is required.";
}

function getMaxLengthMessage(pParams) {
	return "'" + pParams[0] + "' should not exceeds " + pParams[1] + " characters.";
}

function getInvalidMessage(pParams) {
	return "'" + pParams + "' is invalid.";
}

function getInvalidGlAccountMessage(pParams) {
	return "This '" + pParams + "' won't accept this currency.";
}