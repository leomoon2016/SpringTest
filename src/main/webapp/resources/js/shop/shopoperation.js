/**
 * 1、获取表单list 2、提交数据
 */
$(function() {
	var shopId = getQueryString('shopId');
	var isEdit = shopId ? true : false;

	var initUrl = '/o2o/shopadmin/getshopinitinfo';
	var registerShopUrl = '/o2o/shopadmin/registershop';
	var shopInfoUrl = '/o2o/shopadmin/getshopbyid?shopId=' + shopId;
	var editShopUrl = '/o2o/shopadmin/modifyshop';

	if (!isEdit) {
		getShopInitShop();
	} else {
		getShopInfo();
	}

	function getShopInfo(shopId) {

		$.getJSON(shopInfoUrl, function(data) {
			if (data.success) {
				var shop = data.shop;
				$('#shop_name').val(shop.shopName);
				$('#shop_addr').val(shop.shopAddr);
				$('#shop_phone').val(shop.phone);
				$('#shop_desc').val(shop.shopDesc);

				var shopCategory = '<option data-id="'
						+ shop.shopCategory.shopCategoryId + '" selected>'
						+ shop.shopCategory.shopCategoryName + '</option>';

				var tempAreaHtml = '';
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});

				$('#shop_category').html(shopCategory);
				$('#shop_category').attr('disabled', 'disabled');
				$('#area').html(tempAreaHtml);
				$('#area option[data-id="' + shop.area.areaId + '"]').attr(
						'selected', 'selected');

				// $('#area').attr('data-id', shop.areaId);

			}
		});

	}

	function getShopInitShop() {

		$.getJSON(initUrl, function(data) {

			if (data.success) {
				var tempHtml = '';
				var tempAreaHtml = '';

				// list
				data.shopCategoryList.map(function(item, index) {
					tempHtml += '<option data-id="' + item.shopCategoryId
							+ '">' + item.shopCategoryName + '</option>';
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

	$('#submit').click(function() {

		var shop = {};
		if (isEdit) {
			shop.shopId = shopId;
		}
		shop.shopName = $('#shop_name').val();
		shop.shopAddr = $('#shop_addr').val();
		shop.phone = $('#shop_phone').val();
		shop.shopDesc = $('#shop_desc').val();

		shop.shopCategory = {
			shopCategoryId : $('#shop_category').find('option').not(function() {
				return !this.selected;
			}).data('id')
		}

		shop.area = {
			areaId : $('#area').find('option').not(function() {
				return !this.selected
			}).data('id')
		}

		var shopImg = $('#shop_img')[0].files[0];

		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码');
			return;
		}

		var formData = new FormData();
		formData.append('shopImg', shopImg);
		formData.append('shopStr', JSON.stringify(shop));
		formData.append("verifyCodeActual", verifyCodeActual);

		$.ajax({
			url : (isEdit ? editShopUrl : registerShopUrl),
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				// alert(data.success);
				if (data.success) {
					$.toast('提交成功');
				} else {
					// $.toast('提交失败');
					$.toast(data.errMsg);
				}
				$('#captcha_img').click();
			}
		})

	});

})