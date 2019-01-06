
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
	if(selectedRb === "procedureRb"){
		selectedTreatment = selectedProcedure;
	} else if(selectedRb === "medicineRb"){
		selectedTreatment = selectedMedicine;
	} else if(selectedRb === "surgeryRb"){
		selectedTreatment = selectedSurgery;
	}
	var a = 4;
	$.ajax({
		url : 'appoint',
		method: 'POST',
		data : {
			patientId : patientId,
			executorId : executorId,
			selectedTreatmentType : selectedRb,
			selectedTreatment : selectedTreatment,
			requestParameter : 'create_appointment'
		},
		success : function(responseText) {
			if(responseText === "success"){
				var appointmentsForm = document.getElementById('therapistAppointments');
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
				var fn = document.getElementById("p" + patientId.split("appointment")[1] + "f");
				var ln = document.getElementById("p" + patientId.split("appointment")[1] + "l");
				
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

function setDefaultFormState(){
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

function handleTreatmentRb(treatmentRb){
	hideAllTreatmentDb();
	selectedRb = treatmentRb.value;
	if(selectedRb === "procedureRb"){
		document.getElementById('proceduresDb').style.display = "block";
		document.getElementById('proceduresLabel').style.display = "block";
	} else if(selectedRb === "medicineRb"){
		document.getElementById('medicineDb').style.display = "block";
		document.getElementById('medicineLabel').style.display = "block";	
	} else if(selectedRb === "surgeryRb"){
		document.getElementById('surgeriesDb').style.display = "block";
		document.getElementById('surgeriesLabel').style.display = "block";
	}
}

function hideAllTreatmentDb(){
	document.getElementById('surgeriesDb').style.display = "none";
	document.getElementById('surgeriesLabel').style.display = "none";
	document.getElementById('proceduresDb').style.display = "none";
	document.getElementById('proceduresLabel').style.display = "none";
	document.getElementById('medicineDb').style.display = "none";
	document.getElementById('medicineLabel').style.display = "none";	
}

document.getElementById("executorDb").onchange = function() {
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
	if(executorType === "Nurse"){
		document.getElementById('procedureRb').disabled = false;
		document.getElementById('medicineRb').disabled = false;
	} else if(executorType === "Doctor"){
		document.getElementById('procedureRb').disabled = false;
		document.getElementById('surgeryRb').disabled = false;	
	}
}

document.getElementById("proceduresDb").onchange = function() {
	selectedProcedure = this.value;
}

document.getElementById("surgeriesDb").onchange = function() {
	selectedSurgery = this.value;
}

document.getElementById("medicineDb").onchange = function() {
	selectedMedicine = this.value
}