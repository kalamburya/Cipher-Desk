// логика страницы

var form = document.getElementById("cipherForm");
var rotShiftInput = document.getElementById("rotShift");
var wheelGroup = document.getElementById("wheelGroup");
var freqNote = document.getElementById("freqNote");
var submitBtn = document.getElementById("submitBtn");
var outputArea = document.getElementById("outputArea");

function getRadioValue(name) {
    var checked = form.querySelector('input[name="' + name + '"]:checked');
    if (checked) {
        return checked.value;
    }
    return null;
}

// подсветка выбранного варианта (мод / алфавит / метод)
function syncSegmented(groupEl) {
    var options = groupEl.querySelectorAll(".segmented__option");
    for (var i = 0; i < options.length; i++) {
        var input = options[i].querySelector("input");
        if (input.checked) {
            options[i].classList.add("is-checked");
        } else {
            options[i].classList.remove("is-checked");
        }
    }
}

var segmentedGroups = document.querySelectorAll(".segmented");
for (var i = 0; i < segmentedGroups.length; i++) {
    syncSegmented(segmentedGroups[i]);
    segmentedGroups[i].addEventListener("change", function () {
        syncSegmented(this);
    });
}

// показать/скрыть блок со сдвигом в зависимости от метода
function updateMethodUI() {
    var method = getRadioValue("decryptorName");
    var isRot = method === "ROT";

    if (isRot) {
        wheelGroup.classList.remove("is-disabled");
        freqNote.classList.remove("is-visible");
        rotShiftInput.disabled = false;
    } else {
        wheelGroup.classList.add("is-disabled");
        freqNote.classList.add("is-visible");
        rotShiftInput.disabled = true;
    }
}

// подпись на кнопке в зависимости от режима
function updateModeUI() {
    var mode = getRadioValue("mode");
    if (mode === "ENCRYPT") {
        submitBtn.textContent = "Зашифровать";
    } else {
        submitBtn.textContent = "Расшифровать";
    }
}

document.getElementById("methodGroup").addEventListener("change", updateMethodUI);
document.getElementById("modeGroup").addEventListener("change", updateModeUI);

// стрелки для величины сдвига, с учётом размра алфавита
function currentAlphabetSize() {
    var vocab = getRadioValue("vocabularyName");
    if (vocab === "EN") {
        return 26;
    }
    return 33;
}

function setShift(value) {
    var size = currentAlphabetSize();
    value = value % size;
    if (value < 0) {
        value = value + size;
    }
    rotShiftInput.value = value;
}

document.getElementById("shiftPlus").addEventListener("click", function () {
    var current = parseInt(rotShiftInput.value) || 0;
    setShift(current + 1);
});

document.getElementById("shiftMinus").addEventListener("click", function () {
    var current = parseInt(rotShiftInput.value) || 0;
    setShift(current - 1);
});

rotShiftInput.addEventListener("change", function () {
    setShift(parseInt(rotShiftInput.value) || 0);
});

document.getElementById("vocabGroup").addEventListener("change", function () {
    setShift(parseInt(rotShiftInput.value) || 0);
});

// кнопка скопировать
function bindCopyButton() {
    var btn = document.getElementById("copyBtn");
    var text = document.getElementById("resultText");
    if (!btn || !text) {
        return;
    }
    btn.addEventListener("click", function () {
        navigator.clipboard.writeText(text.textContent);
        var old = btn.textContent;
        btn.textContent = "Скопировано";
        setTimeout(function () {
            btn.textContent = old;
        }, 1500);
    });
}

// кнопки для истории
function bindHistoryButtons() {
    var toggleBtn = document.getElementById("toggleHistoryBtn");
    var historyContainer = document.getElementById("historyContainer");

    if (toggleBtn && historyContainer) {
        toggleBtn.addEventListener("click", function () {
            if (historyContainer.style.display === "none") {
                historyContainer.style.display = "block";
                toggleBtn.textContent = "Свернуть";
            } else {
                historyContainer.style.display = "none";
                toggleBtn.textContent = "Развернуть";
            }
        });
    }

    var clearBtn = document.getElementById("clearHistoryBtn");
    if (clearBtn) {
        clearBtn.addEventListener("click", function () {
            if (!confirm("Очистить историю?")) {
                return;
            }
            fetch("/clear-history", { method: "DELETE" }).then(function (response) {
                if (response.ok) {
                    var history = document.querySelector(".history-section");
                    if (history) {
                        history.remove();
                    }
                }
            });
        });
    }
}

// отправка формы без перезагрузки страницы
form.addEventListener("submit", function (e) {
    e.preventDefault();
    submitBtn.disabled = true;

    fetch(form.action, {
        method: "POST",
        body: new FormData(form)
    })
        .then(function (response) {
            return response.text();
        })
        .then(function (html) {
            var parsed = new DOMParser().parseFromString(html, "text/html");

            var newOutput = parsed.getElementById("outputArea");
            if (newOutput) {
                outputArea.replaceWith(newOutput);
                outputArea = newOutput;
                bindCopyButton();
            }

            var newHistory = parsed.querySelector(".history-section");
            var oldHistory = document.querySelector(".history-section");

            if (newHistory && oldHistory) {
                oldHistory.replaceWith(newHistory);
            } else if (newHistory && !oldHistory) {
                document.querySelector(".desk").appendChild(newHistory);
            } else if (!newHistory && oldHistory) {
                oldHistory.remove();
            }

            bindHistoryButtons();
        })
        .catch(function () {
            outputArea.innerHTML =
                '<div class="output output--error">' +
                '<span class="field__label">Ошибка</span>' +
                '<pre class="output__text">Не удалось связаться с сервером.</pre>' +
                "</div>";
        })
        .finally(function () {
            submitBtn.disabled = false;
        });
});

// Ctrl+Enter = отправить форму
document.getElementById("inputText").addEventListener("keydown", function (e) {
    if ((e.ctrlKey || e.metaKey) && e.key === "Enter") {
        form.requestSubmit();
    }
});

updateMethodUI();
updateModeUI();
bindCopyButton();
bindHistoryButtons();