/* Functions to be ready at page load */
$(document).ready(function() {
	
	$("#opFailTicketResponse" ).toggle();
	$("#validationAlert" ).toggle();
	
	var alertStatus = $('#validationAlert').text();
	console.log("alertStatus: " + alertStatus);
	
	//Check if msg parameter sent by the spring controller has the value of "true", if so, it will show an alert to the screen
	if( alertStatus == "true"){
		$("#opFailTicketResponse" ).toggle();
		$("#opFailTicketResponse").html("<b>All filed must not be empty.</b>").delay(5000).fadeOut()
	}
	else
		console.log("ELSE TRIGGERED");
	
	oTable = $('#table_id').DataTable({
		stateSave: true,
		"aoColumnDefs": [
		    { "sWidth": "150px", "aTargets": [0] },
		    { "sWidth": "100px", "aTargets": [2] },
            { "bSortable": false, "aTargets": [6] },
	        { "bSortable": false, "aTargets": [7] },
	        { "bSortable": false, "aTargets": [8] },
	        { "bSortable": false, "aTargets": [9] },
	        { "bSortable": false, "aTargets": [10] },
	    ]
	});
});


/* Function to open "Add Ticket" modal on click */
$(document).on('click', 'a.add', function() {
	$('#addModal').modal('show');
});

/* Function to open "Edit Ticket" modal on click */
$(document).on('click',	'a.edit', function() {
	$('#editModal').modal('show');

	var ticketId = $(this).data('id');
	var ticketJira = $(this).closest('tr').find('td.jira').children('a').html();
	var ticketDescription = $(this).closest('tr').find('td.description').children('p').html();
	var ticketEnvironment = $(this).closest('tr').find('td.environment').html();
	var ticketDeveloper = $(this).closest('tr').find('td.developer').html();
	var ticketTester = $(this).closest('tr').find('td.tester').html();
	var ticketStatus = $(this).closest('tr').find('td.status').html();

	$(".modal-body #id_ticket").val(ticketId);
	$(".modal-body #ticketJira").val(ticketJira);
	$(".modal-body #ticketDescription").val(ticketDescription);
	$(".modal-body #ticketEnvironment").val(ticketEnvironment);
	$(".modal-body #ticketDeveloper").val(ticketDeveloper);
	$(".modal-body #ticketTester").val(ticketTester);
	$(".modal-body #ticketStatus").val(ticketStatus);
});

/* Function to "Delete Ticket" modal on click */
$(document).on('click', 'a.delete', function() {  
	$('#deleteModal').modal('show');
	
	var ticketId = $(this).data('id');
	
	$(".modal-footer #delete_id_ticket").val(ticketId);	
});