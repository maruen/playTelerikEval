
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
	dataSource	: dataSource,
    pageable	: true,
    sortable	: true,
    height		: 520,
    toolbar		: [                          
		          	  "create",
		          	  {
		                  text: "Novo Registro",
		                  className: "newOrUpdateClass",
		              },
		              
		              {
		                  template: "<a download='Grid.xlsx' class='k-button ' id='exportExcel'>Exportar Excel</a>",
		                  overflowTemplate: "<span></span>"
		              },
		              {
		                  template: "<a download='Grid.pdf' class='k-button ' id='exportPDF'>Exportar PDF</a>",
		                  overflowTemplate: "<span></span>"
		              },
	              ],
	columns		: [
		    		"name",
		    		{ field: "discontinued"			, 	title: "Discontinued"	, width: "120px" },
		    		{ field: "Company"				, 	title: "Company"		, width: "120px" },
		    		{ field: "introduced"			, 	title: "Data"			, width: "120px" },
		    		{ command: ["edit", "destroy"]	,   title: "&nbsp;"			, width: "200px" }
		    	   ],
	
	editable	: "inline"
	 
});

$(document).on("click", "#exportExcel", excel);
$(document).on("click", "#exportPDF", pdf);

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


function b64ToUint6 (nChr) {
    return nChr > 64 && nChr < 91 ?
        nChr - 65
      : nChr > 96 && nChr < 123 ?
        nChr - 71
      : nChr > 47 && nChr < 58 ?
        nChr + 4
      : nChr === 43 ?
        62
      : nChr === 47 ?
        63
      :
        0;

  }

function base64DecToArr (sBase64, nBlocksSize) {

	var	sB64Enc = sBase64.replace(/[^A-Za-z0-9\+\/]/g, ""), nInLen = sB64Enc.length,
	nOutLen = nBlocksSize ? Math.ceil((nInLen * 3 + 1 >> 2) / nBlocksSize) * nBlocksSize : nInLen * 3 + 1 >> 2, taBytes = new Uint8Array(nOutLen);

	for (var nMod3, nMod4, nUint24 = 0, nOutIdx = 0, nInIdx = 0; nInIdx < nInLen; nInIdx++) {
		nMod4 = nInIdx & 3;
		nUint24 |= b64ToUint6(sB64Enc.charCodeAt(nInIdx)) << 18 - 6 * nMod4;
		if (nMod4 === 3 || nInLen - nInIdx === 1) {
			for (nMod3 = 0; nMod3 < 3 && nOutIdx < nOutLen; nMod3++, nOutIdx++) {
				taBytes[nOutIdx] = nUint24 >>> (16 >>> nMod3 & 24) & 255;
			}
			nUint24 = 0;

		}
	}

	return taBytes;
}

function pdf() {
	window.open('/computersPDF');
}


function excel() {
	var grid = $("#grid").data("kendoGrid");
	var data = grid.dataSource.view();

	var file = {
			worksheets: [{
				data: [] 
			}],
			creator: 'John Smith', 
			created: new Date('8/16/2012'),
			lastModifiedBy: 'Larry Jones', 
			modified: new Date(),
			activeWorksheet: 0
	};

	var worksheetData = file.worksheets[0].data;
	var worksheetDataHeader = [];

	worksheetData.push(worksheetDataHeader);

	for (var ci = 0; ci < grid.columns.length; ci++) {
		var title = grid.columns[ci].title || grid.columns[ci].field;
		worksheetDataHeader.push({
			value: title,
			autoWidth: true
		});
	}

	for (var di = 0; di < data.length; di++) {
		var dataItem = data[di];
		var worksheetDataItem = [];

		for (ci = 0; ci < grid.columns.length; ci++) {
			var column = grid.columns[ci];

			worksheetDataItem.push({
				value: dataItem.get(column.field)
			});
		}

		worksheetData.push(worksheetDataItem);
	}

	var result = xlsx(file);

	if (navigator.msSaveBlob) {
		var blob = new Blob([base64DecToArr(result.base64)]);
		navigator.msSaveBlob(blob, this.getAttribute("download"));
	} else {
		this.href = result.href();
	}
}






