$(document).ready(function(){
	window.setInterval(resetAnimation, 9000 * $(".product").length);

	function resetAnimation(){
		$(".product").each(function(key,value){
        	var elm = $(".product.product-"+(key+1))[0];
			var newone = elm.cloneNode(true);
			elm.parentNode.replaceChild(newone, elm);
		})
	}
})