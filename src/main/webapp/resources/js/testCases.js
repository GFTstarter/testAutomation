/* Functions to be ready at page load */
$(document).ready(function() {

	$('#testCases').dataTable({
		"scrollY" : "750px",
		"scrollX" : false,
		"scrollCollapse" : true,
		"info" : false,
		"paging" : false
	});

	$('#datepicker').datepicker({
		format : "dd/mm/yyyy",
		calendarWeeks : true,
		todayHighlight : true
	});

	$('#testCaseTestedOn').datepicker({
		format : "dd/mm/yyyy",
		calendarWeeks : true,
		todayHighlight : true
	});

	// Initialize table counter	
	var cont = 1;
	$("#testCases .testCaseId").each(function(){
		$(this).html(cont++);
	});
    
	// Initialize your table
    var oTable = $('#testCases').dataTable();
    
    // Get the length
    var rows = oTable.fnGetData().length;

    // Get value Task ID
	var id = document.getElementById('task_id');
	id.value = rows + 1;
});

/* Function to open "Edit Test Case" modal on click */
$(document).on('click', 'a.edit', function() {
	$('#editModal').modal('show');

	var testCaseId = $(this).data('id');
	var testCaseStatus = $(this).closest('tr').find('td.testCaseStatus').html();
	var testCaseTestedBy = $(this).closest('tr').find('td.testCaseTestedBy').html();
	var testCaseTestedOn = $(this).closest('tr').find('td.testCaseTestedOn').html();
	var testCasePreRequisite = $(this).closest('tr').find('td.testCasePreRequisite').html();
	var testCaseDescription = $(this).closest('tr').find('td.testCaseDescription').html();
	var testCaseResults = $(this).closest('tr').find('td.testCaseResults').html();
	var testCaseComments = $(this).closest('tr').find('td.testCaseComments').html();

	$(".modal-body #testcase_id").val(testCaseId);
	$(".modal-body #testCaseStatus").val(testCaseStatus);
	$(".modal-body #testCaseTestedBy").val(testCaseTestedBy);
	$(".modal-body #testCaseTestedOn").val(testCaseTestedOn);
	$(".modal-body #testCasePreRequisite").val(testCasePreRequisite);
	$(".modal-body #testCaseDescription").val(testCaseDescription);
	$(".modal-body #testCaseResults").val(testCaseResults);
	$(".modal-body #testCaseComments").val(testCaseComments);
});

/* Function to open "Edit Time to run all tests" modal on click */
$(document).on('click', 'a.time', function() {
	$('#modalTime').modal('show');

	var id_ticket = $(this).data('id');
	var runTime = $(this).closest('tr').find('td.lastLine.time').html();

	$(".modal-body #id_ticket").val(id_ticket);
	$(".modal-body #run_time").val(runTime);
});

/* Function to open "Reset tests" modal on click */
$(document).on('click', 'a.reset', function() {
	$('#resetModal').modal('show');

	var id_ticket = $(this).data('id');

	$(".modal-footer #reset_testcase_id").val(id_ticket);
});

/* Function to open "Play tests" modal on click */
$(document).on('click', 'a.play', function() {
	$('#playModal').modal('show');

	var id_ticket = $(this).data('id');

	$(".modal-footer #play_testcase_id").val(id_ticket);
});

/* Function to "Delete Ticket" modal on click */
$(document).on('click', 'a.delete', function() {
	$('#deleteModal').modal('show');

	var id_ticket = $(this).data('id');

	$(".modal-footer #delete_testcase_id").val(id_ticket);
});