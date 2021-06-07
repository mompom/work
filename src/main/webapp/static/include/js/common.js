$(document).ready(function() {
	$('.datepicker').datepicker({
		  dateFormat: "yy-mm-dd"
	});

	$('.gnb_menu').click(function(event){
		var $body = $('body');
		if($body.hasClass('menu_open')){
			$body.removeClass('menu_open');
		}else{
			$body.addClass('menu_open');
		}
	});
	
	$('.gnb_mask').click(function(){
		$('.gnb_menu').trigger('click');
	});

	$('.drop').click(function(){
		var $this = $(this);
		$this.parent().next('.drop_cont').toggle();
	});

	//layer_close
	$('.layer_close').click(function(){
		var $this = $(this);
		$this.parent().parent().hide();
	});
	
	
});