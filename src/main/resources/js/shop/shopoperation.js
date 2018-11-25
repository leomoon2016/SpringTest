/**
 * 1、获取表单list 2、提交数据
 */
$(function() {
	var initUrl = 'o2o/shopadmin/getshopinitinfo';
	var registerShopUrl = 'o2o/shopadmin/registershop';
	var shopInfoUrl = 'o2o/shopadmin/getshopbyid?shopId='+shopId;

	getShopInitShop();
	
	function getShopInitShop() {

		$getJSON(initUrl, function(data) {
			if (data.success) {
				var tempHtml = '';
				var tempAreaHtml = '';

				// list
				data.shopCategoryList.map(function(item, index) {
					tempHtml += '<option data-id="' + item.shopCategoryId
							+ '">' + item.shopcategoryName + '</option>';
				})
				// list
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				})

				$('#shop_category').html(tempHtml);
				$('#area').html(tempAreaHtml);
			}
		})
	}
	
	$('#submit').click(function(){
		var shop={};
		shop.shopName=$('#shop_name').val();
		shop.shopAddr=$('#shop_addr').val();
		shop.shopPhone=$('#shop_phone').val();
		shop.shopDesc=$('#shop_desc').val();
		
		shop.shopCategory={
				shopCategoryId:$('#shop_category').find('option').not(function(){return !this.selected}).data('id');
		};
		
		shop.area={
				areaId:$('#shop_area').find('option').not(function(){return !this.selected}).data('id');
		};
		
		var shopImg=$('#shop_img')[0].files[0];
		var formData=new FormData();
		formData.append('shopImg',shopImg);
		formData.append('shopStr',JSON.stringify(shop));
		
		$ajax({
			url:registerShopUrl,
			type:'POST',
			data:formData,
			contentType:false,
			processData:false,
			cache:false,
			success:function(data){
				if(data.success){
					$.toast('提交成功');
				}
				else{
					$.toast('提交失败');
				}
			}
		});
	});
})