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

function openModal(elementId) {
    const open = document.getElementById(elementId);
    open.classList.remove("hidden");
}

function closeModal(elementId) {
    const close = document.getElementById(elementId);
    close.classList.add("hidden");
}

function showError(element) {
    if (element != null) {
        applyInvalidAppearence(element);
        if (element.nextElementSibling && element.nextElementSibling.classList.contains('validation-error')) {
            element.nextElementSibling.classList.remove('hidden');
        }
    }
}

function showOk(element) {
    if (element != null) {
        applyValidAppearence(element);
        if (element.nextElementSibling && element.nextElementSibling.classList.contains('validation-error')) {
            element.nextElementSibling.classList.add('hidden');
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

function schedulePicker(root) {
    const bookedSlotsRaw = root?.dataset?.bookedSlots ?? '';
    const bookedSlots = bookedSlotsRaw
        .split(',')
        .map((value) => value.trim())
        .filter((value) => value.length > 0);

    return {
        modalOpen: false,
        selectedDate: '',
        selectedTime: '',
        times: [],
        bookedSlots: bookedSlots,
        minDate: new Date().toISOString().split('T')[0],
        initPicker() {
            this.times = this.buildTimes();
            const dateValue = this.$refs.dateField?.value;
            const hourValue = this.$refs.hourField?.value;
            const minuteValue = this.$refs.minuteField?.value;
            if (dateValue) {
                this.selectedDate = dateValue;
            }
            if (hourValue !== '' && minuteValue !== '') {
                this.selectedTime = `${String(hourValue).padStart(2, '0')}:${String(minuteValue).padStart(2, '0')}`;
            }
            this.syncFields();
        },
        buildTimes() {
            const slots = [];
            for (let hour = 8; hour <= 18; hour += 1) {
                for (let minute = 0; minute < 60; minute += 30) {
                    if (hour === 18 && minute > 0) {
                        continue;
                    }
                    slots.push(`${String(hour).padStart(2, '0')}:${String(minute).padStart(2, '0')}`);
                }
            }
            return slots;
        },
        openModal() {
            this.modalOpen = true;
        },
        closeModal() {
            this.modalOpen = false;
        },
        isBooked(date, time) {
            if (!date || !time) {
                return false;
            }
            return this.bookedSlots.includes(`${date} ${time}`);
        },
        slotDisabled(date, time) {
            return !date || this.isBooked(date, time);
        },
        slotClass(date, time) {
            const base = 'w-full rounded-lg border px-3 py-2 text-sm font-semibold transition';
            if (!date) {
                return `${base} cursor-not-allowed bg-slate-100 text-slate-400 border-slate-200`;
            }
            if (this.isBooked(date, time)) {
                return `${base} cursor-not-allowed bg-slate-100 text-slate-400 line-through border-slate-200`;
            }
            if (this.selectedTime === time) {
                return `${base} bg-orange-500 text-white border-orange-500`;
            }
            return `${base} bg-white text-slate-700 border-slate-200 hover:border-orange-500 hover:bg-orange-50`;
        },
        selectTime(time) {
            if (this.slotDisabled(this.selectedDate, time)) {
                return;
            }
            this.selectedTime = time;
            this.syncFields();
            this.closeModal();
        },
        onDateChange() {
            if (this.selectedTime && this.isBooked(this.selectedDate, this.selectedTime)) {
                this.selectedTime = '';
            }
            this.syncFields();
        },
        syncFields() {
            if (this.$refs.dateField) {
                this.$refs.dateField.value = this.selectedDate || '';
            }
            if (this.$refs.hourField) {
                this.$refs.hourField.value = this.selectedTime ? parseInt(this.selectedTime.split(':')[0], 10) : '';
            }
            if (this.$refs.minuteField) {
                this.$refs.minuteField.value = this.selectedTime ? parseInt(this.selectedTime.split(':')[1], 10) : '';
            }
        }
    };
}
