package com.imooc.o2o.enums;

public enum ShopStateEnum {
	CHECK(0, "审核中"), OFFLINE(-1, "非法"), SUCCESS(1, "操作成功"), PASS(2, "通过"), INNER_ERROR(-1001, "内部错误"),
	NULL_SHOPID(-1002, "ShopId为空"), NULL_SHOP(-1003, "店铺空");

	private int state;
	private String stateInfo;

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	private ShopStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	/**
	 * 依据相应state传出值
	 */
	public static ShopStateEnum stateOf(int state) {
		for (ShopStateEnum stateEnum : values()) {
			if (stateEnum.getState() == state) {
				return stateEnum;
			}
		}
		return null;
	}

}
