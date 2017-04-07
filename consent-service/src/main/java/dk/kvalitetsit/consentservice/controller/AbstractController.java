package dk.kvalitetsit.consentservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractController {

	private static Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);
	
	/*@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationErrors handleControllerParseExceptionException(HttpServletRequest req, HttpMessageNotReadableException httpMessageNotReadableException) {
		String errorMessage = httpMessageNotReadableException.getRootCause().getLocalizedMessage();
		LOGGER.error("Parse error for input - error:["+httpMessageNotReadableException.getMessage()+"]");
		ValidationErrors validationErrors = new ValidationErrors();
		validationErrors.setMessage("HttpMessageNotReadableException occured");
		validationErrors.addError(new ValidationError(errorMessage));
		return validationErrors;
	}
*/
}