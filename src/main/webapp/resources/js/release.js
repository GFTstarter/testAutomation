/* Functions to be ready at page load */
$(document).ready(function() {
	$('#releases').dataTable( {
		stateSave: true		
	});

	$('#datepicker').datepicker({
		format : "dd/mm/yyyy",
		forceParse : false,
		startDate : "today",
		calendarWeeks : true,
		todayHighlight : true
	});

	$('#edit_target_date').datepicker({
		format : "dd/mm/yyyy",
		forceParse : false,
		startDate : "today",
		calendarWeeks : true,
		todayHighlight : true
	});
});

/* Function to open "Add Release" modal on click */
$(document).on('click', 'a.add', function() {
	$('#addModal').modal('show');
});

/* Function to open "Edit Release" modal on click */
$(document).on('click', 'a.edit', function() {
	$('#editModal').modal('show');

	var id_release = $(this).data('id');
	var project = $(this).closest('tr').find('td.project').html();
	var tag = $(this).closest('tr').find('td.tag').html();
	var name = $(this).closest('tr').find('td.name').html();
	var target_date = $(this).closest('tr').find('td.target_date')
				.html();

	$(".modal-body #edit_id_release").val(id_release);
	$(".modal-body #edit_project").val(project);
	$(".modal-body #edit_tag").val(tag);
	$(".modal-body #edit_name").val(name);
	$(".modal-body #edit_target_date").val(target_date);
});

/* Function to "Delete Release" modal on click */
$(document).on('click', 'a.delete', function() {  
	$('#deleteModal').modal('show');
	
	var id_release = $(this).data('id');
	
	$(".modal-footer #delete_id_release").val(id_release);	
});
