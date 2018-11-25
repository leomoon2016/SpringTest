$(function() {
	var shopId = getQueryString('shopID');
	var shopInfoUrl = '/o2o/shopadmin/getshopmanagementinfo?shopId='
			+ shopId;
	$.getJSON(shopInfoUrl, function(data) {
		if (data.redirect) {
			window.location.href = data.url;
		} else {
			if (data.shopID != undefined && data.shopID != null) {
				shopId = data.shopId;
			}
			$('#shopInfo').attr('href',
					'/o2o/shopadmin/shopoperation?shopId=' + shopId);
		}

	});
});