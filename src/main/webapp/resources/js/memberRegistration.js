var validateForm = function () {
			 var userId = $('#userId').val();
			 if(!userId) {
				 toastr.error('usrId cannot be empty'); 
				 return false;
			 } else {
				 return  true;
			 }
			 var password = $('#password').val();
			 if(!password) {
				 toastr.error('password cannot be empty'); 
				 return false;
			 } else {
				 return  true;
			 }
			 var confirmPassword = $('#confirmPassword').val();
			 if(!confirmPassword) {
				 toastr.error('confirmPassword cannot be empty'); 
				 return false;
			 } else {
				 return  true;
			 }
			 var firstName = $('#firstName').val();
			 if(!firstName) {
				 toastr.error('first name cannot be empty'); 
				 return false;
			 } else {
				 return  true;
			 }
			 	 
		};

$(document).ready(function() {
	  		$('#resetBtn').click(function(event){
	  			$('input[type="text"]').val('');
	  			$('input[type="password"]').val('');
	  		});
	  	});
		
$(document).ready(function() {
  	  $('#submitBtn').click(function(event) {
      if(validateForm()) {
		var data = {
			"userId" : $('#userId').val(),
			"password" : $('#password').val(),
			"confirmPassword" : $('#confirmPassword').val(),
			"firstName" : $('#firstName').val(),
			"lastName" : $('#lastName').val(),
			"contactNo" : $('#contactNo').val(),
			"email" : $('#email').val()
		};

		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "/olmsb-web/registration/validate",
			data : JSON.stringify(data),
			dataType : 'json',
			timeout : 10000,
			success : function(response) {
				if(response.success) {
					$('#memberRegistrationForm').submit();
				} else {
					var err = ''; 
					response.errors.forEach(function(item, index) {
						err += item + '<br>'
					});
					toastr.error(err);
				}
			},
			error : function(err) {
				toastr.error('Error validating Member Registration details');
				console.log("ERROR: ", err);
			}
		});
      }
  });
});