console.log("FragmentElement: " + fragmentElement);

const numericInput = fragmentElement.querySelector(
	`#${fragmentNamespace}-numeric-input`
);

function handleOnKeydown(event) {
	const isInteger = numericInput.getAttribute('data-type') === 'integer';

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
					numericInput.getAttribute('data-validation-message-text')
				);
			}
		}
	}
}

if (numericInput) {
	if (layoutMode === 'edit') {
		numericInput.setAttribute('disabled', true);
	}
	else {
		numericInput.addEventListener('keydown', handleOnKeydown);
	}
}
