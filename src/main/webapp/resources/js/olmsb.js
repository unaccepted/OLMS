toastr.options = {
  "closeButton": true,
  "debug": false,
  "newestOnTop": true,
  "progressBar": false,
  "positionClass": "toast-top-right",
  "preventDuplicates": true,
  "onclick": null,
  "timeOut": "3000"
};

var OLMSB = {
	"request" : {
		"post" : function(data, url, success, error) {
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : url,
				data : JSON.stringify(data),
				dataType : 'json',
				timeout : 10000,
				success : success,
				error : error
			});
		}
	}	
};
