/* Functions to be ready at page load */
$(document).ready(function() {
	
	//TODO: Add comments to the code.
	$("#opFailTicketResponse" ).toggle();
	$("#validationAlert" ).toggle();
	var idRelease = $("#idRelease").val();
	var ticketTag = $("#ticketTag").val();
	console.log("idRelease: " + idRelease);
	console.log("ticketTag: " + ticketTag);
	
	var alertStatus = $('#validationAlert').text();
	console.log("alertStatus: " + alertStatus);
	
	//Check if msg parameter sent by the spring controller has the value of "true", if so, it will show an alert to the screen
	if( alertStatus == "true"){
		$("#opFailTicketResponse" ).toggle();
		$("#opFailTicketResponse").html("<b>All filed must not be empty.</b>").delay(5000).fadeOut()
	}
	else
		console.log("ELSE TRIGGERED");
	
	
	/*Load the Ticket dataTable using the parameter mRender to customise the content of
	 * the columns*/
	oTable = $('#table_id').DataTable({
		stateSave: true,
		"bProcessing": true,
		"sAjaxDataProp":"",
		ajax: {
			"url": "/AutomacaoTestes/ticketsListAjax",
			"data": {"id_release": idRelease},
		    "beforeSend": function(xhr) {	
		    	xhr.setRequestHeader("Accept", "application/json");
		        xhr.setRequestHeader("Content-Type", "application/json");
		    },
		    dataType:"JSON"
	    },
		aoColumns: [
		    //mData : This property can be used to read data from any JSON data source property, including deeply nested objects / properties
		    {'mRender': function (data, type, full) {
				if(full.testcase_status == 'Failed'){
					return '<a href="http://dbatlas.db.com/jira02/browse/'+full.jira+'" target="_blank" class="failedLink">'+full.jira+'</a>';
				}
				else{
					return '<a href="http://dbatlas.db.com/jira02/browse/'+full.jira+'" target="_blank">'+full.jira+'</a>';
				}
			}},
			{'mRender': function (data, type, full) {return '<p align="justify">'+full.description+'</p>';}},
			{"mData": "environment"},
			{"mData": "developer"},
			{"mData": "tester"},
			{"mData": "status"},
			//mRender: This property is the rendering partner to mData and it is suggested that when you want to manipulate data for display but not altering the underlying data for the table, use this property.
			{ sortable:false, mRender: function (data, type, full) {return '<a title="Click to edit the row data" href="#" class="edit" data-toggle="modal" data-id="'+full.id_ticket+'"><span class="glyphicon glyphicon-pencil"></span></a>'; }},
			{ sortable:false, mRender: function (data, type, full) {return '<a title="Click to details"	href="testCases?id_ticket='+full.id_ticket+'&jira='+full.jira+'&tag='+ticketTag+'&description='+full.description+'&developer='+full.developer+'&tester='+full.tester+'&environment='+full.environment+'&run_time='+full.run_time+'&status='+full.status+'"> <span class="glyphicon glyphicon-option-horizontal"></span> </a>'; }},
			{ sortable:false, mRender: function (data, type, full) {return '<a title="Click start tests" href="#" onclick="window.open(\'startTests?id_ticket='+full.id_ticket+'\', \'newwindow\', \'width=450, height=650\'); return false;" data-id="'+full.id_ticket+'"> <span class="glyphicon glyphicon-play"></span>'; }},
			 {'mRender': function (data, type, full) {
					if(full.testcase_status == 'Failed'){
						return '<span class="glyphicon glyphicon-export"></span>';
					}
					else{
						return '<a title="Click to Excel" href="getExcel/'+full.jira+'_Test_Plan?id_ticket='+full.id_ticket+'&jira='+full.jira+'&tag='+ticketTag+'&environment='+full.environment+'&developer='+full.developer+'&tester='+full.tester+'&description='+full.description+'&run_time='+full.run_time+'"><span class="glyphicon glyphicon-export"></span></a>';
					}
				}},
			{ sortable:false, mRender: function (data, type, full) {return '<a title="Click to delete" href="#" class="delete"	data-toggle="modal" data-id="'+full.id_ticket+'"> <span class="glyphicon glyphicon-remove"></span></a>'; }}
		],
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
	
	/*Ajax call to insert a new registry in the release table */
	$('#createTicket').submit(function(event) {
		  
		var jira = $('#jira').val();
		var description = $('#description').val();
		var environment = $('#environment').val();
		var developer = $('#developer').val();
		var tester = $('#tester').val();
		var status = $('#ticketStatus').val();
		var run_time = "";
		var testcase_status = "";
		
		var json = {"jira" : jira, "description" : description, "environment": environment, "developer": developer, "tester": tester, "status": status, "run_time": run_time, "testcase_status": testcase_status, "id_release": idRelease};
		console.log(JSON.stringify(json));
	    
		$.ajax({
	    	url: $("#createTicket").attr("action"),
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
					$("#opFailTicketResponse" ).toggle();
					$("#opFailTicketResponse").html("<b>All filed must not be empty.</b>").delay(2500).fadeOut()
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
	$('#editTicket').submit(function(event) {
	       
		var jira = $('#ticketJira').val();
		var description = $('#ticketDescription').val();
		var environment = $('#ticketEnvironment').val();
		var developer = $('#ticketDeveloper').val();
		var tester = $('#ticketTester').val();
		var status = $('#ticketStatus').val();
		var idTicket = $('#id_ticket').val();
		
		var json = {"jira" : jira, "description" : description, "environment": environment, "developer": developer, "tester": tester, "status": status, "id_ticket": idTicket};
		console.log('jira: ' + jira + ' description: ' + description + ' environment: ' + environment + ' developer: ' + developer)
	    
		$.ajax({
	    	url: $("#editTicket").attr("action"),
	        data: JSON.stringify(json),
	        type: "POST",
	        beforeSend: function(xhr) {
	            xhr.setRequestHeader("Accept", "text/plain");
	            xhr.setRequestHeader("Content-Type", "application/json");
	        },
	        success: function(d) {
	        	
	        	 if(d.status == 1){
						console.log("Falha");
						$("#opFailTicketResponse" ).toggle();
						$("#opFailTicketResponse").html("<b>All fields must not be empty.</b>").delay(5000).fadeOut()
					}    
	          	           
	            $('#editModal').modal('hide');
	            
	    		//oTable.ajax.reload( null, false );
	    		oTable.ajax.reload();
	        }
	    });
		event.preventDefault();
	});
	
	$('#deleteTicket').submit(function(event){
		
		var idTicket = $('#delete_id_ticket').val();
		console.log("IdTicket - " + idTicket);
		
		$.ajax({
			url: $("#deleteTicket").attr("action"),
			type: "POST",
			data: idTicket,  
			dataType: "json",
			beforeSend: function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
			success: function(d) {
				$('#deleteModal').modal('hide');
				if(d.status == 1){
					console.log("Falha");
					$("#opFailTicketResponse" ).toggle();
					$("#opFailTicketResponse").html("<b>Can not be deleted. This ticket has test cases assigned to it.</b>").delay(2500).fadeOut()
				}
				oTable.ajax.reload();
			}
		});
		
		event.preventDefault();
	});
	
//END OF (DOCUMENTO).READY()	
});


/* Function to open "Add Ticket" modal on click */
$(document).on('click', 'a.add', function() {
	$('#addModal').modal('show');
});

/* Function to open "Edit Ticket" modal on click */
$(document).on('click',	'a.edit', function() {
	$('#editModal').modal('show');

	var ticketId = $(this).data('id');
	var ticketJira = $(this).closest('tr').find('td:eq(0)').children('a').html();
	var ticketDescription = $(this).closest('tr').find('td:eq(1)').children('p').html();
	var ticketEnvironment = $(this).closest('tr').find('td:eq(2)').html();
	var ticketDeveloper = $(this).closest('tr').find('td:eq(3)').html();
	var ticketTester = $(this).closest('tr').find('td:eq(4)').html();
	var ticketStatus = $(this).closest('tr').find('td:eq(5)').html();

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