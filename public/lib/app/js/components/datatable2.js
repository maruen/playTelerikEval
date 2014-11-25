
var crudServiceBaseUrl = "http://localhost:9000",
    dataSource = new kendo.data.DataSource({
	    transport: {
	        read:  {
	            url: crudServiceBaseUrl + "/computers",
	            dataType: "jsonp"
	        },
	        update: {
	            url: crudServiceBaseUrl + "/computers/update",
	            dataType: "jsonp"
	        },
	        destroy: {
	            url: crudServiceBaseUrl + "/computers/delete",
	            dataType: "jsonp"
	        },
	        create: {
	            url: crudServiceBaseUrl + "/computers/create",
	            dataType: "jsonp"
	        },
	        parameterMap: function(options, operation) {
	            if (operation !== "read" && options.models) {
	                return {models: kendo.stringify(options.models)};
	            }
	        }
	    },
	    batch: true,
	    pageSize: 10,
	    schema: {
	        model: {
	            id: "id",
	            fields: {
	                id: 		 	{ editable: false, nullable: true },
	                name: 		 	{ validation: { required: true } },
	                introduced: 	{ type: "string" },
	                discontinued: 	{ type: "string" },
	                company: 		{ type: "number"  }
	            }
	        }
	    }
    });


$("#grid").kendoGrid({
    dataSource: dataSource,
    pageable: true,
    sortable: true,
    height: 520,
    toolbar: [                          
              	  "create",
              	  {
                      text: "Novo Registro",
                      className: "newOrUpdateClass",
                  },
                  {
                      text : "Exportar Excel", 
                      name: "popup3", 
                      iconClass: "k-icon k-add"
                  },
                  {
                      text : "Exportar PDF", 
                      name: "popup4", 
                      iconClass: "k-icon k-add"
                  },
              ],
    columns: [
        		"name",
        		{ field: "discontinued"			, 	title: "Discontinued"	, width: "120px" },
        		{ field: "Company"				, 	title: "Company"		, width: "120px" },
        		{ field: "introduced"			, 	title: "Data"			, width: "120px" },
        		{ command: ["edit", "destroy"]	,   title: "&nbsp;"			, width: "200px" }
        ],
    
 	editable: "inline"
});

$(".newOrUpdateClass").click(function() {
   $("#datatable").hide();
   $("#edit").show();
});

$("#cancelButton").click(function() {
	$("#datatable").show();
    $("#edit").hide();
});

$("#saveButton").click(function() {
	$("#datatable").show();
    $("#edit").hide();
});



