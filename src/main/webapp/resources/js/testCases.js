/* Functions to be ready at page load */
$(document).ready(function() {

	//TODO: Set order by on query that populate testcase tables in the application to show the data always on the most recent order
	
	
	
	//TODO: Selector to add class "form-control input-sm" on search input
	//$('input[@type=search]').val('Test');
	
	/*For new API function must be .DataTable({}); with D on upCase 
	 *For old API function must be .dataTable({}); with d on downCase*/
	oTable = $('#testCases').dataTable({
		
		stateSave: true,
		//TODO: Set all column to not sortable
		/*"bProcessing": true,
		"sAjaxDataProp":"",
		ajax: {
			"url": "/AutomacaoTestes/testCasesAjax",
		    "beforeSend": function(xhr) {	
		    	xhr.setRequestHeader("Accept", "application/json");
		        xhr.setRequestHeader("Content-Type", "application/json");
		    },
		    dataType:"JSON"
	    },
		aoColumns: [
		    //mData : This property can be used to read data from any JSON data source property, including deeply nested objects / properties
			
		    {"mData": "id_task"},
			{"mData": "status"},
			{"mData": "tested_by"},
			{"mData": "tested_on"},
			{"mData": "pre_requisite"},
			{"mData": "testcase_description"},
			{"mData": "results"},
			{"mData": "comments"},
			{"mData": "id_testcase"},
			//mRender: This property is the rendering partner to mData and it is suggested that when you want to manipulate data for display but not altering the underlying data for the table, use this property.
			{ sortable:false, mRender: function (data, type, full) {return '<a title="Click to move up the row data" href="#" class="moveup" data-toggle="modal" data-id="'+full.id_testcase+'"> <span class="glyphicon glyphicon-chevron-up"></span> </a> <a title="Click to move down the row data" href="#" class="movedown" data-toggle="modal" data-id="'+full.testcase_id+'"> <span class="glyphicon glyphicon-chevron-down"></span> </a>'; }},
			{ sortable:false, mRender: function (data, type, full) {return '<a title="Click to edit the row data" href="#" class="edit" data-toggle="modal" data-id="'+full.id_task+'"> <span class="glyphicon glyphicon-pencil"></span> </a>'; }},
			{ sortable:false, mRender: function (data, type, full) {return '<a title="Click to Reset Tests field" href="#" class="reset" data-toggle="modal" data-id="'+full.id_testcase+'"> <span class="glyphicon glyphicon-refresh"></span> </a>'; }},
			{ sortable:false, mRender: function (data, type, full) {return '<a title="Click to Play Test" href="" onclick="window.open("startTestsSelected?id_testcase="'+full.id_testcase+'"&id_ticket="'+full.id_ticket+'"&status="'+full.status+'"&pre_requisite="'+full.pre_requisite+'"&testcase_description="'+full.testcase_description+'"&results="'+full.results+'"&comments="'+full.comments+'"&id_task="'+full.id_task+'", "newwindow", width=450, height=650"); return false;" class="play" data-id="'+full.testcase_id+'"> <span class="glyphicon glyphicon-play"></span> </a>'; }},
			{ sortable:false, mRender: function (data, type, full) {return '<a title="Click to delete" href="#" class="delete" data-toggle="modal" data-id="'+full.id_testcase+'"> <span class="glyphicon glyphicon-remove"></span> </a>'; }}
		]
	   */
		/*"aoColumnDefs": [
            { "sWidth": "1px", "aTargets": [0] },
 		    { "sWidth": "200px", "aTargets": [5] },
		    { "bSortable": false, "aTargets": [8] },
		    { "bSortable": false, "aTargets": [9] },
	        { "bSortable": false, "aTargets": [10]},
        	{ "bSortable": false, "aTargets": [11]},
        	{"bSortable": false, "aTargets": [12]}
	    ]*/
		/*"scrollY" : "750px",
		"scrollX" : false,
		"scrollCollapse" : true,
		"info" : false,
		"paging" : false*/
	});
	
	$('#editTestCase').submit(function(event) {
	       
		var testCaseTicketId = $('#ticket_id').val();
		var testCaseId = $('#testcase_id').val();
		var testCaseTaskId = $('#taskId').val();
		var testCaseStatus = $('#testCaseStatus').val();
		var testCaseTestedBy = $('#testCaseTestedBy').val();
		var testCaseTestedOn = $('#testCaseTestedOn').val();
		var testCasePreRequisite = $('#testCasePreRequisite').val();
		var testCaseDescription = $('#testCaseDescription').val();
		var testCaseResults = $('#testCaseResults').val();
		var testCaseComments = $('#testCaseComments').val();
		
		var json = {"task_id": testCaseTaskId, "testcase_id": testCaseId, "status": testCaseStatus, "tested_by": testCaseTestedBy, "tested_on": testCaseTestedOn, "pre_requisite": testCasePreRequisite, "testcase_description": testCaseDescription, "results": testCaseResults, "comments": testCaseComments, "comments": testCaseComments};
	    
		$.ajax({
	    	url: $("#editTestCase").attr( "action"),
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
	    		//oTable.ajax.reload();
	            window.location.reload(true);
	            //OLD API
	            //oTable.api().ajax.reload();
	        }
	    });
		event.preventDefault();
	});
	
	$('#createTestCase').submit(function(event) {
	       
		var taskId = $('#taskId').val();
		var status = $('#status').val();
		var testedBy = $('#tested_by').val();
		var testedOn = $('#tested_on').val();
		var preRequisite = $('#pre_requisite').val();
		var testcaseDescription = $('#testcase_description').val();
		var results = $('#results').val();
		var comments = $('#comments').val();
		
		console.log("TAKSID: " + taskId);
		var json = { "task_id" : taskId, "status" : status, "tested_by": testedBy, "tested_on": tested_on, "pre_requisite": preRequisite, "testcase_description": testcaseDescription, "results": results, "comments": comments};
		console.log(JSON.stringify(json));
	    
		$.ajax({
	    	url: $("#createTestCase").attr( "action"),
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
					$("#opFailReleaseResponse").html("<b>All filed must not be empty.</b>").delay(5000).fadeOut()
				}           
	          /*  $('#addModal').modal('hide');
	            
	    		console.log('Table: ' + oTable);
	    		
	    		//oTable.ajax.reload( null, false );
	    		oTable.ajax.reload();*/
	        }
		    });
			event.preventDefault();
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

	// *******TO BE DELETED**********Initialize table counter	
	var cont = 1;
	$("#testCases .testCaseTaskId").each(function(){
		$(this).html(cont++);
	});
    
$('#testCases tbody').on( 'click', 'tr', function(){
	var aPos = oTable.fnGetPosition(this);
	console.log('INDEX: ' +  aPos );	
	moverow(aPos);
});
	
	
	// Initialize your table
    //var oTable = $('#testCases').DataTable();
    
    // Get the length
	/*For new API: 
	 * var data = table.rows().data();
	 * var rows = data.length*/
 
    
    // Get value Task ID
	
});

/*Click envent to increment id_taks conter, had to be implement here to make sure
 * that ajax call has already finish for us to know the length of the table and set it to the next insert*/
$(document).on('click', '.newTestCase', function(e){
	/*****Other way to get lenght of dataTable*****
	var oSettings = oTable.fnSettings();
	var iTotalRecords = oSettings.fnRecordsTotal();*/
	    
	var rows = oTable.fnSettings().fnRecordsTotal();
	var id = document.getElementById('task_id');
	
	console.log("Rows: " + rows);
	id.value = rows + 1;
});

/***********JQUERY SORTABLE******************/


/***********NEW API******************/
$(document).on('click', '.moveup', function(e){
	console.log("up");
	moveSelected("up");
});

$(document).on('click', '.movedown', function(e){
	console.log("down");
	moveSelected("down");
});

/*Button to save the new sort defined by the user, it get the data in the order that 
 * was organized by the user and send it rows data to the updateSort() function, which will persist
 * into the database the new order*/
$(document).on('click', '#saveSort', function(e){
	var json;
	var testCaseTaskId = 0;
	console.log('SaveSort');
	
	var rows = oTable.fnSettings().fnRecordsTotal();
	console.log("Rows: " + rows );
	
	for(var x=0; x<rows; x++){
		
		var testCaseStatus = oTable.fnGetData(x)[1]
		var testCaseTestedBy = oTable.fnGetData(x)[2]
		var testCaseTestedOn = oTable.fnGetData(x)[3];
		var testCasePreRequisite = oTable.fnGetData(x)[4];
		var testCaseDescription = oTable.fnGetData(x)[5];
		var testCaseResults = oTable.fnGetData(x)[6];
		var testCaseComments = oTable.fnGetData(x)[7];
		var testCaseid = oTable.fnGetData(x)[8];
		testCaseTaskId++;
		
		console.log("task_id - " + testCaseTaskId + " - testcase_id - " + testCaseid);
		json = {"task_id": testCaseTaskId, "testcase_id": testCaseid} 
	
		updateSort(json);
	}
});

//Function to save the new sort defined by the user, update the task_id column on by the testcase_id
function updateSort(json){
	$.ajax({
    	url: "/AutomacaoTestes/editTestCaseSort.json",
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
        }
    });
}

/*OLD API*/
function moverow(index){
	console.log('moveup');
	
    if ((index-1) >= 0){
    	
        var data = oTable.fnGetData();
        oTable.fnClearTable();
        data.splice((index-1), 0, data.splice(index,1)[0]);
        oTable.fnAddData(data);
        
        var oSettings = oTable.fnSettings();
        var clonedData = oSettings.aoData.slice();
       
        $('#testCases tbody tr').each(function(i)
        {
          var startPos = oTable.fnGetPosition(this);
          console.log( "i: " + i + " startPos: " + startPos );
           //console.log(oSettings);
          clonedData[i] = oSettings.aoData[startPos];
          clonedData[i]._iId = i;
          	
          // App specific - first table col contains an incremental count which needs to be refreshed after a move
          // Without cloning hack - do this with fnUpdate()
          clonedData[i].nTr.cells[0].innerHTML = i+1;
          clonedData[i].nTr.cells[0].innerText = i+1;
        })
   
        oSettings.aoData = clonedData;
        oTable.fnDraw();
    }
}

/*********ODL API******
$(document).on('click', '.moveup', function(e){
	console.log('moveup');
    var index = $(this).attr('index');
    console.log('This: ' + this);
    if ((index-1) >= 0){
        //var datatable = $('#datatable.table').dataTable()
        var data = oTable.fnGetData();
        console.log('Data: ' + data);
        oTable.fnClearTable();
        data.splice((index-1), 0, data.splice(index,1)[0]);
        oTable.fnAddData(data);
    }
    
});
$(document).on('click', '.movedown', function(e){
	console.log('movedown');
    var index = $(this).attr('index');
    if ((index+1) >= 0) {
        //var datatable = $('#datatable.table').dataTable()
        var data = oTable.fnGetData();
        oTable.fnClearTable();
        data.splice((index+1), 0, data.splice(index,1)[0]);
        oTable.fnAddData(data);
    }
});
*/

/* Function to open "Edit Test Case" modal on click */
$(document).on('click', 'a.edit', function() {
	$('#editModal').modal('show');

	var taskId = $(this).closest('tr').find('td:eq(0)').html();
	var testCaseStatus = $(this).closest('tr').find('td:eq(1)').html();
	var testCaseTestedBy = $(this).closest('tr').find('td:eq(2)').html();
	var testCaseTestedOn = $(this).closest('tr').find('td:eq(3)').html();
	var testCasePreRequisite = $(this).closest('tr').find('td:eq(4)').html();
	var testCaseDescription = $(this).closest('tr').find('td:eq(5)').html();
	var testCaseResults = $(this).closest('tr').find('td:eq(6)').html();
	var testCaseComments = $(this).closest('tr').find('td:eq(7)').html();
	var testCaseId = $(this).closest('tr').find('td:eq(8)').html();

	console.log("TAKSID: " + taskId);
	$(".modal-body #testcase_id").val(testCaseId);
	$(".modal-body #taskId").val(taskId);
	$(".modal-body #testCaseStatus").val(testCaseStatus);
	$(".modal-body #testCaseTestedBy").val(testCaseTestedBy);
	$(".modal-body #testCaseTestedOn").val(testCaseTestedOn);
	$(".modal-body #testCasePreRequisite").val(testCasePreRequisite);
	$(".modal-body #testCaseDescription").val(testCaseDescription);
	$(".modal-body #testCaseResults").val(testCaseResults);
	$(".modal-body #testCaseComments").val(testCaseComments);
});

/* Function to open "Edit Time to run all tests" modal on click */
$(document).on('click', 'a.editHeader', function() {
	$('#modalTime').modal('show');

	var id_ticket = $(this).data('id');
	var runTime = $(this).closest('tr').find('td.lastLine.time').html();
	//Uses hidden input in the modal as reference to preset status value on the edit modal
	var status = $('input#status').val();
	
	$(".modal-body #id_ticket").val(id_ticket);
	$(".modal-body #run_time").val(runTime);
	$(".modal-body #ticketStatus").val(status);
});

/* Function to open "Reset tests" modal on click */
$(document).on('click', 'a.reset', function() {
	$('#resetModal').modal('show');

	var id_ticket = $(this).data('id');

	$(".modal-footer #reset_testcase_id").val(id_ticket);
});

/* NOT BEING USED - Function to open "Play tests" modal on click */
/*$(document).on('click', 'a.play', function() {
	$('#playModal').modal('show');

	var id_ticket = $(this).data('id');

	$(".modal-footer #play_testcase_id").val(id_ticket);
});*/

/* Function to "Delete Ticket" modal on click */
$(document).on('click', 'a.delete', function() {
	$('#deleteModal').modal('show');

	var id_ticket = $(this).data('id');

	$(".modal-footer #delete_testcase_id").val(id_ticket);
});