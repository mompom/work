//엑셀 파일로 다운받기
function excelExport(obj, target, containerId, dataType, worksheetName){
	
	var itoStr = function(num){
		num < 10? num = '0'+num : num;
		return num.toString();
	};
	
	var dt = new Date();
	var year =  itoStr( dt.getFullYear() );
	var month = itoStr( dt.getMonth() + 1 );
	var day =   itoStr( dt.getDate() );
	var hour =  itoStr( dt.getHours() );
	var mins =  itoStr( dt.getMinutes() );
	
	var postfix = year + month + day + "_" + hour + mins;
	//var postfix = year + month;
	//var fileName = "계좌인증-"+ postfix + "-사용내역.xls";
	var fileName = "계좌인증-사용내역.xls";
	
	var uri = $(target).excelexportjs({
		containerid: containerId,
		datatype: dataType,
		returnUri: true,
		worksheetName: worksheetName
	});
	
    $(obj).attr('download', fileName).attr('href', uri).attr('target', '_blank');

}

function remove_blank_str(toString){
	return toString.replace(/(\s*)/g, "");
}

function removeComma(n) {  // 콤마제거
    if ( typeof n == "undefined" || n == null || n == "" ) {
        return "";
    }
    var txtNumber = '' + n;
    return txtNumber.replace(/(,)/g, "");
}
function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function numberWithCommasInput($obj){
    var num = $obj.val().replace(/[,]/g, "");
 
    var parts = num.toString().split(".");
    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    $obj.val(parts.join("."));
}

$.fn.rowspan = function(colIdx, isStats) {    
	return this.each(function(){      
		var that;     
		$('tr', this).each(function(row) {      
			$('td:eq('+colIdx+')', this).filter(':visible').each(function(col) {
				
				if ($(this).html() == $(that).html()
					&& (!isStats 
							|| isStats && $(this).prev().html() == $(that).prev().html()
							)
					) {            
					rowspan = $(that).attr("rowspan") || 1;
					rowspan = Number(rowspan)+1;

					$(that).attr("rowspan",rowspan);
					
					// do your action for the colspan cell here            
					$(this).hide();
					
					//$(this).remove(); 
					// do your action for the old cell here
					
				} else {            
					that = this;         
				}          
				
				// set the that if not already set
				that = (that == null) ? this : that;      
			});     
		});    
	});  
}; 

$.fn.colspan = function(rowIdx) {
	return this.each(function(){
		
		var that;
		$('tr', this).filter(":eq("+rowIdx+")").each(function(row) {
			$(this).find('th').filter(':visible').each(function(col) {
				if ($(this).html() == $(that).html()) {
					colspan = $(that).attr("colSpan") || 1;
					colspan = Number(colspan)+1;
					
					$(that).attr("colSpan",colspan);
					$(this).hide(); // .remove();
				} else {
					that = this;
				}
				
				// set the that if not already set
				that = (that == null) ? this : that;
				
			});
		});
	});
}

$.fn.selectRange = function(start, end) {
	//커서 범위 선택
    return this.each(function() {
    	console.log(this);
         if(this.setSelectionRange) {
             this.focus();
             this.setSelectionRange(start, end);
         } else if(this.createTextRange) {
             var range = this.createTextRange();
             range.collapse(true);
             range.moveEnd('character', end);
             range.moveStart('character', start);
             range.select();
         }
     });
 }; 
 
 $.fn.focusEnd = function() {
	 //커서를 끝으로 div contenteditable
    $(this).focus();
    var tmp = $('<span />').appendTo($(this)),
        node = tmp.get(0),
        range = null,
        sel = null;

    if (document.selection) {
        range = document.body.createTextRange();
        range.moveToElementText(node);
        range.select();
    } else if (window.getSelection) {
        range = document.createRange();
        range.selectNode(node);
        sel = window.getSelection();
        sel.removeAllRanges();
        sel.addRange(range);
    }
    tmp.remove();
    return this;
}