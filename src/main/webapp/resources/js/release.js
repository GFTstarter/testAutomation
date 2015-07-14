/* Functions to be ready at page load */
$(document).ready(function() {
	
	//Variable to store the result from the ajax call
	var ajaxResp ='';
	$( "#addReleaseResponse" ).toggle();
	$( "#opFailReleaseResponse" ).toggle();
	
	/*Ajax call to delete a registry from releases table sending the idRelease 
	 * via the "data:" attribute*/
	$('#deleteRelease').submit(function(event){
		
		var idRelease = $('#delete_id_release').val();
		console.log("Id - " + idRelease);
		
		$.ajax({
			url: $("#deleteRelease").attr("action"),
			type: "POST",
			data: idRelease,  
			dataType: "json",
			beforeSend: function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
			success: function(d) {
				$('#deleteModal').modal('hide');
				if(d.status == 1){
					console.log("Falha");
					$("#opFailReleaseResponse").toggle();
					$("#opFailReleaseResponse").html("<b>Can not be deleted. This release has tickets assigned to it.</b>").delay(2500).fadeOut()
				}
				oTable.ajax.reload();
			},
			/*NOT BEING USED - error function  to handle return in case the release being 
			 * deleted has tickets assigned to it (In this case the release can not be deleted)*/
			error: function(xhr, textStatus, errorThrown){
				$('#deleteModal').modal('hide');
				$("#opFailReleaseResponse" ).toggle();
				$("#opFailReleaseResponse").html("<b>Can't be deleted. This release has tickets assigned to it</b>").delay(2500).fadeOut()
			}
		});
		
		event.preventDefault();
	});
	
	/*Ajax call to insert a new registry in the release table */
	$('#createRelease').submit(function(event) {
	       
		var project = $('#project').val();
		var tag = $('#tag').val();
		var name = $('#name').val();
		var target_date = $('#datepicker').val();
  
		var json = { "project" : project, "tag" : tag, "name": name, "target_date": target_date};
		console.log(JSON.stringify(json));
	    
		$.ajax({
	    	url: $("#createRelease").attr( "action"),
	        data: JSON.stringify(json),
	        type: "POST",
	        beforeSend: function(xhr) {
	            xhr.setRequestHeader("Accept", "text/plain");
	            xhr.setRequestHeader("Content-Type", "application/json");
	        },
	        success: function(d) {
	            console.log('Response: ' + d.status);
	            
	            if(d.status == 1){
					console.log("Falha");
					$("#opFailReleaseResponse" ).toggle();
					$("#opFailReleaseResponse").html("<b>All filed must not be empty.</b>").delay(2500).fadeOut()
				}           
	            $('#addModal').modal('hide');
	            
	    		console.log('Table: ' + oTable);
	    		
	    		//oTable.ajax.reload( null, false );
	    		oTable.ajax.reload();
	        }
		    });
			event.preventDefault();
		});
	
	/*Ajax call to edit a registry*/
	$('#editRelease').submit(function(event) {
	       
		var id_release = $('#edit_id_release').val();
		var project = $('#edit_project').val();
		var tag = $('#edit_tag').val();
		var name = $('#edit_name').val();
		var target_date = $('#edit_target_date').val();
  
		var json = { "id_release": id_release,"project" : project, "tag" : tag, "name": name, "target_date": target_date};
		console.log('project: ' + project + ' tag: ' + tag + ' name: ' + name + ' target_date: ' + target_date)
	    
		$.ajax({
	    	url: $("#editRelease").attr( "action"),
	        data: JSON.stringify(json),
	        type: "POST",
	        beforeSend: function(xhr) {
	            xhr.setRequestHeader("Accept", "text/plain");
	            xhr.setRequestHeader("Content-Type", "application/json");
	        },
	        success: function(d) {
	        	
	        	 if(d.status == 1){
						console.log("Falha");
						$("#opFailReleaseResponse" ).toggle();
						$("#opFailReleaseResponse").html("<b>All fields must not be empty.</b>").delay(5000).fadeOut()
					}    
	          	           
	            $('#editModal').modal('hide');
	            
	    		//oTable.ajax.reload( null, false );
	    		oTable.ajax.reload();
	        }
	    });
		event.preventDefault();
	});
	
	$('#releases-NOT-BEING-USED tbody').not("a.edit a.delete tbody").on('click', 'tr', function () {
		$('#editModal').modal('show');

		console.log(oTable.row(this).data().id_release);
		var id_release = oTable.row(this).data().id_release;
		var project = oTable.row(this).data().project;
		var tag = oTable.row(this).data().tag;
		var name = oTable.row(this).data().name;
		var target_date = oTable.row(this).data().target_date;

		$(".modal-body #edit_id_release").val(id_release);
		$(".modal-body #edit_project").val(project);
		$(".modal-body #edit_tag").val(tag);
		$(".modal-body #edit_name").val(name);
		$(".modal-body #edit_target_date").val(target_date);
	});
	
	//oTable created as global
	oTable = $('#releases').DataTable({
		
		stateSave: true,
		"bProcessing": true,
		"sAjaxDataProp":"",
		ajax: {
			"url": "/AutomacaoTestes/getListAjax",
		    "beforeSend": function(xhr) {	
		    	xhr.setRequestHeader("Accept", "application/json");
		        xhr.setRequestHeader("Content-Type", "application/json");
		    },
		    dataType:"JSON"
	    },
		aoColumns: [
		    //mData : This property can be used to read data from any JSON data source property, including deeply nested objects / properties
			{"mData": "project"},
			{"mData": "tag"},
			{"mData": "name"},
			{"mData": "target_date"},
			//mRender: This property is the rendering partner to mData and it is suggested that when you want to manipulate data for display but not altering the underlying data for the table, use this property.
			{ sortable:false, mRender: function (data, type, full) {return '<a title="Click to edit the row data" href="#" class="edit" data-toggle="modal" data-id="'+full.id_release+'"><span class="glyphicon glyphicon-pencil"></span></a>'; }},
			{ sortable:false, mRender: function (data, type, full) {return '<a title="Click to options" href="refreshTicket?project='+full.project+'&tag='+full.tag+'&id_release='+full.id_release+'"><span class="glyphicon glyphicon-option-horizontal"></span> </a>'; }},
			{ sortable:false, mRender: function (data, type, full) {return '<a title="Click to delete" href="#" class="delete"	data-toggle="modal" data-id="'+full.id_release+'"> <span class="glyphicon glyphicon-remove"></span></a>'; }}
        ]
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
	
	$('#refresh').click(function() {
		console.log("Updating")
		oTable.ajax.reload();
	});
	
});

/* Function to open "Add Release" modal on click */
 $(document).on('click', 'a.add', function() {
	$('#addModal').modal('show');
});
 
$(document).on('click', 'a.edit', function(){
	$('#editModal').modal('show');
	
	var id_release = $(this).data('id');
	var project = $(this).closest('tr').find('td:eq(0)').html();
	var tag = $(this).closest('tr').find('td:eq(1)').html();
	var name = $(this).closest('tr').find('td:eq(2)').html();
	var target_date = $(this).closest('tr').find('td:eq(3)').html();
	
	$(".modal-body #edit_id_release").val(id_release);
	$(".modal-body #edit_project").val(project);
	$(".modal-body #edit_tag").val(tag);
	$(".modal-body #edit_name").val(name);
	$(".modal-body #edit_target_date").val(target_date);
});

/* Function to "Delete Release" modal on click */
$(document).on('click', 'a.delete', function(){  
	$('#deleteModal').modal('show');
	
	var id_release = $(this).data('id');
	
	$(".modal-footer #delete_id_release").val(id_release);	
});
