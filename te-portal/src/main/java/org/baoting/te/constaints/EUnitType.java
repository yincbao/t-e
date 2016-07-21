package org.baoting.te.constaints;

/**
 * ClassName: EUnitType
 * 
 * @description
 * @author zhu_l
 * @Date 2015-8-18
 * 
 */
public enum EUnitType {
	KM(0),
	Mile(1);

	private int type;

	private EUnitType(int type) {
		this.type = type;
	}

	public int value() {
		return type;
	}

	public static EUnitType getELocateType(int type) {
		EUnitType[] types = EUnitType.values();
		for(int i = 0; i < types.length; i++) {
			if (types[i].type == type) {
				return types[i];
			}
		}
		throw new java.lang.IllegalArgumentException(
				"No enum found for the passed code: " + type);
	}
}
