$(document).ready(function(){
	window.setInterval(resetAnimation, 9000*numProd);
	window.setInterval(showQrcode, 9000);

	function resetAnimation(){
		$(".product").each(function(key,value){
        	var elm = $(".product.p"+(key+1))[0];
			var newone = elm.cloneNode(true);
			elm.parentNode.replaceChild(newone, elm);
		})
	}

	var last = 1;

	function showQrcode(){
		var total = $(".product").length;
		if(last > total){
			$("footer section").html(" ");
			last = 1;
		}

		var qrcodeSrc = $(".product.p"+last+" .qrcode").attr("src");
		var title = $(".product.p"+last+" h1.stroke").html();
		var price = $(".product.p"+last+" .price.stroke").html();

		var content = "<img style=\"opacity: 0;\" class=\"qrcode\" src=\""+qrcodeSrc+"\">";
			content += "<span style=\"opacity: 0;\">";
			content += "<span class=\"stroke\">"+title+"<br/>"+price+"</span>";
			content += "</span>";

		$("footer section").append(content);
		$("footer img").animate({opacity: 1},"slow");
		$("footer span").animate({opacity: 1},"slow");

		last ++;
	}
})