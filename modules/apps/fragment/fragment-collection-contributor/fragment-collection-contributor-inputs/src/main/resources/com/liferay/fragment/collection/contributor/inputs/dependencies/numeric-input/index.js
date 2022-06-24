const input = fragmentElement.querySelector(
	`#${fragmentNamespace}-numeric-input`
);
const isInteger = input.getAttribute('data-type') === 'integer';

function handleOnKeydown(event) {
	if (isInteger && (event.key === ',' || event.key === '.')) {
		event.preventDefault();
	}

	if (!isInteger) {
		event.target.setCustomValidity('');

		if (event.target.checkValidity()) {
			const numDecimals = event.target.getAttribute('step').length - 2;
			const [, decimalPart = ''] = event.target.value.split(/[.,]/);

			if (decimalPart.length > numDecimals) {
				event.target.setCustomValidity(
					input.getAttribute('data-validation-message-text')
				);
			}
		}
	}
}

if (input) {
	if (layoutMode === 'edit') {
		input.setAttribute('disabled', true);
	}
	else {
		input.addEventListener('keydown', handleOnKeydown);
	}
}
