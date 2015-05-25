/* Functions to be ready at page load */
$(document).ready(function() {
	$('#table_id').dataTable({
		stateSave: true,
		"columns" : [ {
		"width" : "120px"
		}, null, null, null, null, null, null, null, null, null ]
	});
});

/* Function to open "Edit Ticket" modal on click */
$(document).on('click',	'a.edit', function() {
	$('#editModal').modal('show');

	var ticketId = $(this).data('id');
	var ticketDeveloper = $(this).closest('tr').find('td.developer').html();
	var ticketTester = $(this).closest('tr').find('td.tester').html();
	var ticketStatus = $(this).closest('tr').find('td.status').html();

	$(".modal-body #id_ticket").val(ticketId);
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