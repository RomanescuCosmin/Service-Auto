function applyInvalidAppearence(element) {
    element.classList.remove('valid-class');
    element.classList.add('invalid-class')
}

function applyValidAppearence(element) {
    if(element != null) {
        element.classList.remove('invalid-class');
        element.classList.add('valid-class');
    }
}

function getValidationMessage(element) {
    if (element == null || element.parentElement == null) {
        return null;
    }
    return element.parentElement.querySelector('.validation-error');
}

function showError(element){
    if(element != null) {
        applyInvalidAppearence(element);
        const validationMessage = getValidationMessage(element);
        if (validationMessage != null) {
            validationMessage.classList.remove('hidden');
        }
    }
}

function showOk(element) {
    if(element != null) {
        applyValidAppearence(element);
        const validationMessage = getValidationMessage(element);
        if (validationMessage != null) {
            validationMessage.classList.add('hidden');
        }
    }
}

function onInputOnChange(element) {
    if(element.value.trim() === '') {
        showError(element);
    } else {
        showOk(element);
    }
}

function validateContact() {
    let isValid = true;

    const fieldToValidate = ["nume", "email", "telefon", "descriere"];
    const ok = validateMultipleField(fieldToValidate);
    if(ok === false) isValid = false;

    return isValid;
}

function validateMultipleField(errorField) {
    let isValid = true;
    errorField.forEach( (field) => {
        const input = document.getElementById(field);
        if(input.value.trim() === '') {
            showError(input);
            isValid = false;
        } else {
            showOk(input);
        }
    })

    return isValid;
}
