package com.arthur.cloud.activity.util;


import com.alibaba.druid.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 参数工具
 * @author czh
 * @2016年11月11日
 */
public class AppUtil {

	/**
	 * 封装接口返回数据
	 * @param result
	 * @return
	 */
	public static CommonResult returnObj(String result) {
		if (StringUtils.isEmpty(result)) {
			return new CommonResult();
		}
		return new CommonResult(result);
	}

	/**
	 * 封装带数据的返回
	 * @param result
	 * @param data
	 * @return
	 */
	public static CommonResult returnObj(String result, Object data) {
		if (StringUtils.isEmpty(result)) {
			return new CommonResult(data);
		}
		return new CommonResult(result);
	}

	/**
	 * 封装带集合的返回
	 * @param result
	 * @param
	 * @return
	 */
	public static <T> CommonResult returnList(String result, List<T> list) {
		if (StringUtils.isEmpty(result)) {
			return returnObj(result, list);
		}
		list = new ArrayList<T>();
		return new CommonResult(true, result, list);
	}

	/**
	 * 比较两个实体类属性值是否相等
	 * @param source
	 * @param target
	 * @return
	 * @throws Exception
	 */
	public static boolean entityIsEquals(Object source, Object target) throws Exception {
		if (source == null || target == null) {
			return false;
		}
		boolean ret = true;
		Class<?> srcClass = source.getClass();
		Field[] fields = srcClass.getDeclaredFields();
		String nameKey = null;
		String srcValue = null;
		String tarValue = null;
		for (Field field : fields) {
			nameKey = field.getName();
			srcValue = getClassValue(source, nameKey) == null ? "" : getClassValue(source, nameKey).toString();
			tarValue = getClassValue(target, nameKey) == null ? "" : getClassValue(target, nameKey).toString();
			if (!srcValue.equals(tarValue)) {
				ret = false;
				break;
			}
		}
		return ret;
	}

	/**
	 * 根据字段名称取值
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	private static Object getClassValue(Object obj, String fieldName) throws Exception {
		@SuppressWarnings("rawtypes")
		Class beanClass = obj.getClass();
		Method[] ms = beanClass.getMethods();
		for(Method method: ms){
			// 非get方法不取
			if(method.getName().startsWith("get")){
				Object objValue = method.invoke(obj, new Object[] {});
				if(null != objValue){
					if(method.getName().toUpperCase().equals(fieldName.toUpperCase()) || method.getName().substring(3).toUpperCase().equals(fieldName.toUpperCase())){
						return objValue;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 生成N位随机数
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; ++i) {
			int number = random.nextInt(3);
			long result = 0;

			switch (number) {
			case 0:
				result = Math.round(Math.random() * 25 + 65);
				sb.append(String.valueOf((char) result));
				break;
			case 1:
				result = Math.round(Math.random() * 25 + 97);
				sb.append(String.valueOf((char) result));
				break;
			case 2:
				sb.append(String.valueOf(new Random().nextInt(10)));
				break;
				default:
			}
		}
		return sb.toString();
	}

	/**
	 * 生成N位纯数字验证码
	 * @return
	 */
	public static String getVerificationCode(int n) {
		final Random random = new Random();
		String verificationCode = "";
		for (int i = 0; i < n; i++) {
			verificationCode += random.nextInt(10);
		}
		return verificationCode;
	}

	/**
	 * 生产注单号
	 * @return
	 */
	public static String getOrdercode() {
		String time = String.valueOf(System.currentTimeMillis());
		return time + time.subSequence(2, 6) + getVerificationCode(1);
	}

	/**
	 * 生成32位UUID
	 * @return
	 */
	public static String getUuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * 相除
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static String divide(Object num1, Object num2){
		Float num = Float.parseFloat(num1.toString());
		BigDecimal b1 = new BigDecimal(num);
		BigDecimal b2 = new BigDecimal(num2.toString());
		Double result = b1.divide(b2, 2, RoundingMode.HALF_UP).doubleValue();
		return new DecimalFormat("#,##0.00").format(result);
	}
	
	public static String formatValue(double source){
		return new DecimalFormat("###0.00").format(source);
	}
}
