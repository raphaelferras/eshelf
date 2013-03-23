$(document).ready(function(){
	window.setInterval(resetAnimation, 9000 * $(".product").length);

	function resetAnimation(){
		$(".product").each(function(key,value){
        	var elm = $(".product.product-"+(key+1))[0];
			var newone = elm.cloneNode(true);
			elm.parentNode.replaceChild(newone, elm);
		})
	}

	window.setInterval(showQrcode, 9000);
	var last = 1;

	function showQrcode(){
		var total = $(".product").length;
		
		$("footer .product-"+ ((last + total) % total + 1)).remove();
		var product = $(".product.product-"+last);
		var html = product.clone();

		html.removeClass("product");
		$("footer section").append(html);
		$("footer img").animate({opacity: 1},"slow");
		$("footer span").animate({opacity: 1},"slow");

		last = last % total + 1;
	}
})