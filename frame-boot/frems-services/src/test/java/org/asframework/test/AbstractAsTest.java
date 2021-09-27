/**
 * 
 */
package org.asframework.test;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * 抽象测试工具类
 * 
 * @author wenzhaoming
 */
public abstract class AbstractAsTest {

	/** 默认的预执行方法 */
	@Before
	public abstract void doBefore();

	/** 默认的执行方法 */
	@Test
	public abstract void doTest();

	/**
	 * 打印对象
	 * 
	 * @param obj
	 */
	protected void print(Object obj) {
		StringBuffer printSb = new StringBuffer();
		this.createPrintString(printSb, obj);
		printSb.append("\n");
		System.out.print(printSb);
	}

	/**
	 * 构造打印字符
	 * 
	 * @param printSb
	 * @param obj
	 */
	protected void createPrintString(StringBuffer printSb, Object obj) {
		if (obj != null) {
			if (obj instanceof Collection) {
				// 集合循环打印
				printSb.append("Collection:[");
				for (Object o : (Collection<?>) obj) {
					createPrintString(printSb, o);
					printSb.append(',');
				}
				if (!((Collection<?>) obj).isEmpty()) {
					printSb.delete(printSb.length() - 1, printSb.length());
				}
				printSb.append(']');
			} else if (obj instanceof Map) {
				// map按照key:value循环打印
				printSb.append("Map:{");
				Map<?, ?> map = (Map<?, ?>) obj;
				for (Map.Entry<?, ?> entry : map.entrySet()) {
					Object key = entry.getKey();
					Object value = entry.getValue();
					createPrintString(printSb, key);
					printSb.append(':');
					createPrintString(printSb, value);
					printSb.append(',');
				}
				if (!((Map<?, ?>) obj).isEmpty()) {
					printSb.delete(printSb.length() - 1, printSb.length());
				}
				printSb.append('}');
			} else if (obj.getClass().isArray()) {
				// 数组循环打印
				printSb.append("Array:[");
				int len = Array.getLength(obj);
				for (int i = 0; i < len; i++) {
					createPrintString(printSb, Array.get(obj, i));
					printSb.append(',');
				}
				if (Array.getLength(obj) > 0) {
					printSb.delete(printSb.length() - 1, printSb.length());
				}
				printSb.append(']');
			} else if (obj instanceof java.util.Date) {
				// 日期打印
				printSb.append("Date:");
				printSb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(obj));
			} else if (obj instanceof Class) {
				// class
				printSb.append(((Class<?>) obj).getName());
			} else if (obj instanceof String) {
				printSb.append('"').append(obj).append('"');
			} else {
				// 其他
				printSb.append(obj);
			}
		} else {
			printSb.append("null");
		}
	}
}
