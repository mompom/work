$(document).ready(function() {
	$('input, textarea').placeholder();
	$('select').fakeselect();
	$('input[type="radio"], input[type="checkbox"]').fakecheck();

	//datepicker
	$('.datepicker').datepicker({
		  dateFormat: "yy-mm-dd"
	});

	
});