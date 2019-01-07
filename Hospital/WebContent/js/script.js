var executorType, executorId, patientId, selectedRb, selectedProcedure, selectedMedicine, selectedSurgery, executorInic;

function signIn() {
	document.getElementById('requestParameter').value = "SIGN_IN";
}

function signUp() {
	document.getElementById('requestParameter').value = "SIGN_UP";
}

function confirmReg() {
	document.getElementById('requestParameter').value = "CONFIRM_REG";
}

function backToMain() {
	document.getElementById('requestParameter').value = "BACK_TO_WELCOME_PAGE";
}

function makeAppointment() {
	var selectedTreatment;
	if (selectedRb === "procedureRb") {
		selectedTreatment = selectedProcedure;
	} else if (selectedRb === "medicineRb") {
		selectedTreatment = selectedMedicine;
	} else if (selectedRb === "surgeryRb") {
		selectedTreatment = selectedSurgery;
	}
	var a = 4;
	$.ajax({
		url : 'appoint',
		method : 'POST',
		data : {
			patientId : patientId,
			executorId : executorId,
			selectedTreatmentType : selectedRb,
			selectedTreatment : selectedTreatment,
			requestParameter : 'create_appointment'
		},
		success : function(responseText) {
			if (responseText.match('[0-9]+') == responseText) {
				var appointmentsForm = document
						.getElementById('therapistAppointments');
				var appointmentRow = document.createElement('div');
				appointmentRow.className = 'divTableRow';
				var divName = document.createElement('div');
				divName.className = 'divTableCell';
				var divExecutor = document.createElement('div');
				divExecutor.className = 'divTableCell';
				var divTreatmentType = document.createElement('div');
				divTreatmentType.className = 'divTableCell';
				var divTreatment = document.createElement('div');
				divTreatment.className = 'divTableCell';
				var divTreatmentStatus = document.createElement('div');
				divTreatmentStatus.className = 'divTableCell';
				divTreatmentStatus.id = 'statusCell' + responseText;
				var divCancelButtonCell = document.createElement('div');
				// class="reg-buttons" type="submit"
				// onclick="cancelAppointmen(this,
				// <c:out value="${apps.appointmentId}" />);"
				// id="cancelButton<c:out value="${apps.appointmentId}" />"
				// value="Cancel" />
				divCancelButtonCell.className = 'divTableCell';
				var cancelButton = document.createElement('input');
				cancelButton.className = 'reg-buttons';
				cancelButton.type = 'submit';
				cancelButton.onclick = function() {
					cancelAppointment(this, this.id.split('cancelButton')[1]);
				};
				cancelButton.id = 'cancelButton' + responseText;
				cancelButton.value = 'Cancel';

				divCancelButtonCell.appendChild(cancelButton);
				var fn = document.getElementById("p"
						+ patientId.split("appointment")[1] + "f");
				var ln = document.getElementById("p"
						+ patientId.split("appointment")[1] + "l");

				divName.innerHTML = fn.textContent + " " + ln.textContent;
				divExecutor.innerHTML = executorInic;
				divTreatmentType.innerHTML = selectedRb.split("Rb")[0];
				divTreatment.innerHTML = selectedTreatment;
				divTreatmentStatus.innerHTML = "not completed";
				appointmentRow.appendChild(divName);
				appointmentRow.appendChild(divExecutor);
				appointmentRow.appendChild(divTreatmentType);
				appointmentRow.appendChild(divTreatment);
				appointmentRow.appendChild(divTreatmentStatus);
				appointmentRow.appendChild(divCancelButtonCell);
				appointmentsForm.appendChild(appointmentRow);
			}
		}
	});
	closeForm();
}

function openForm(appointmentButton) {
	patientId = appointmentButton.id;
	document.getElementById("myForm").style.display = "block";
}

function setDefaultFormState() {
	$('#executorDb').val('defOpt');
	$('#surgeriesDb').val('defOpt');
	$('#proceduresDb').val('defOpt');
	$('#medicineDb').val('defOpt');
	hideAllTreatmentDb();
	document.getElementById('procedureRb').disabled = true;
	document.getElementById('medicineRb').disabled = true;
	document.getElementById('surgeryRb').disabled = true;
	document.getElementById('procedureRb').checked = false;
	document.getElementById('medicineRb').checked = false;
	document.getElementById('surgeryRb').checked = false;
}

function closeForm() {
	document.getElementById("myForm").style.display = "none";
	setDefaultFormState();
}

function userLogOut() {
	document.getElementById('requestParameter').value = "LOG_OUT";
}

function completeAppointment(clickedButton, appointmentId) {
	$.ajax({
		url : 'completeAppointment',
		method : 'POST',
		data : {
			appointmentId : appointmentId,
			requestParameter : 'complete_appointment'
		},
		success : function(responseText) {
			if (responseText === "success") {
				clickedButton.value = "Completed";
				$(clickedButton).css('background-color', '#86E49C');
				document.getElementById("status" + appointmentId).innerHTML = "completed";
				clickedButton.disabled = true;
			}
		}
	});
}

function openDischargeForm(dischargeButton) {
	patientId = dischargeButton.id;
	document.getElementById("myDischargeForm").style.display = "block";
}

function dischargePatient() {
	var diagnosis = document.getElementById("diagnosis").value;
	var finalDiagnosis = document.getElementById("finalDiagnosis").value;
	var uId = patientId.split('discharge')[1];
	$.ajax({
		url : 'dischargePatient',
		method : 'POST',
		data : {
			userId : uId,
			diagnosis : diagnosis,
			finalDiagnosis : finalDiagnosis,
			requestParameter : 'discharge_patient'
		},
		success : function(responseText) {
			if (responseText === "success") {
				var dischargeForm = document
						.getElementById('dischargedPatientsTable');
				var dischargeRow = document.createElement('div');
				dischargeRow.className = 'divTableRow';
				var rowElements = [];
				for (var i = 0; i < 7; i++) {
					rowElements[i] = document.createElement('div');
					rowElements[i].className = 'divTableCell';
				}
				var fn = document.getElementById("p" + uId + "f").textContent;
				var ln = document.getElementById("p" + uId + "l").textContent;
				var bd = document.getElementById("p" + uId + "bd").textContent;
				var ad = document.getElementById("p" + uId + "ad").textContent;
				rowElements[0].innerHTML = fn;
				rowElements[1].innerHTML = ln;
				rowElements[2].innerHTML = bd;
				rowElements[3].innerHTML = ad;
				rowElements[4].innerHTML = new Date();
				rowElements[5].innerHTML = diagnosis;
				rowElements[6].innerHTML = finalDiagnosis;
				for (var j = 0; j < 7; j++) {
					dischargeRow.appendChild(rowElements[j]);
				}
				dischargeForm.appendChild(dischargeRow);
				document.getElementById("rowAttended" + uId).parentNode
						.removeChild(document.getElementById("rowAttended"
								+ uId));
			}
		}
	});
	closeDischargeForm();
}

function cancelAppointment(cancelButton, appId) {
	$.ajax({
		url : 'cancelAppointment',
		method : 'POST',
		data : {
			appointmentId : appId,
			requestParameter : 'cancel_appointment'
		},
		success : function(responseText) {
			if (responseText == "success") {
				cancelButton.value = 'Canceled';
				$(cancelButton).css('background-color', '#86E49C');
				document.getElementById("statusCell" + appId).innerHTML = "canceled";
				cancelButton.disabled = true;
			}
		}
	});
}

function closeDischargeForm() {
	document.getElementById("myDischargeForm").style.display = "none";
}

function handleTreatmentRb(treatmentRb) {
	hideAllTreatmentDb();
	selectedRb = treatmentRb.value;
	if (selectedRb === "procedureRb") {
		document.getElementById('proceduresDb').style.display = "block";
		document.getElementById('proceduresLabel').style.display = "block";
	} else if (selectedRb === "medicineRb") {
		document.getElementById('medicineDb').style.display = "block";
		document.getElementById('medicineLabel').style.display = "block";
	} else if (selectedRb === "surgeryRb") {
		document.getElementById('surgeriesDb').style.display = "block";
		document.getElementById('surgeriesLabel').style.display = "block";
	}
}

function hideAllTreatmentDb() {
	document.getElementById('surgeriesDb').style.display = "none";
	document.getElementById('surgeriesLabel').style.display = "none";
	document.getElementById('proceduresDb').style.display = "none";
	document.getElementById('proceduresLabel').style.display = "none";
	document.getElementById('medicineDb').style.display = "none";
	document.getElementById('medicineLabel').style.display = "none";
}

var executorDb = document.getElementById("executorDb");
if (executorDb != null) {
	executorDb.onchange = function() {
		document.getElementById('procedureRb').disabled = true;
		document.getElementById('medicineRb').disabled = true;
		document.getElementById('surgeryRb').disabled = true;
		document.getElementById('procedureRb').checked = false;
		document.getElementById('medicineRb').checked = false;
		document.getElementById('surgeryRb').checked = false;
		var lexems = this.value.split(' ');
		executorType = lexems[0];
		executorId = lexems[1];
		executorInic = lexems[2] + " " + lexems[3];
		if (executorType === "Nurse") {
			document.getElementById('procedureRb').disabled = false;
			document.getElementById('medicineRb').disabled = false;
		} else if (executorType === "Doctor") {
			document.getElementById('procedureRb').disabled = false;
			document.getElementById('surgeryRb').disabled = false;
		}
	}
}
var therapistDb = document.getElementById("therapistDb");
if (therapistDb != null) {
	therapistDb.onchange = function() {
		document.getElementById('therapistParameter').value = this.value
				.split(' ')[1];
	}
}
var proceduresDb = document.getElementById("proceduresDb");
if (proceduresDb != null) {
	proceduresDb.onchange = function() {
		selectedProcedure = this.value;
	}
}
var surgeriesDb = document.getElementById("surgeriesDb");
if (surgeriesDb != null) {
	surgeriesDb.onchange = function() {
		selectedSurgery = this.value;
	}
}
var medicineDb = document.getElementById("medicineDb");
if (medicineDb != null) {
	medicineDb.onchange = function() {
		selectedMedicine = this.value
	}
}