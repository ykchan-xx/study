# as-core-1.1.0.jar 使用说明
## 一、工具类
- 1.集合或数组处理工具类：org.asframework.core.util.CollectionUtil
```
// 集合转为数组
String[] strArr = CollectionUtil.list2Array(List<String>, String.class);
// 数组转为集合
List<Object> list1 = CollectionUtil.array2List(Object[]);
// 集合拼接为字符串
String list2String = CollectionUtil.list2String(List<String>, "-");
// 数组拼接为字符串
String array2String = CollectionUtil.array2String(String[], "::");
// 字符串分割为集合
List<String> listString = CollectionUtil.string2List("1list-2list-3list--end", "-");
// 字符串分割为集合
List<Object> listObject = CollectionUtil.string2ListObject("1list-2list-3list--end", "-");
// 字符串分割为数组
String[] string2Array = CollectionUtil.string2Array("1,,2,3,, ,,end", ",");
// 字符串分割为数组
Object[] string2ArrayObject = CollectionUtil.string2ArrayObject("1,,2,3,, ,,end", ",");
// 数组扩充
Object[] arrayOrigin = { "11", 22, "next" };
arrayOrigin = CollectionUtil.arrayExtend(arrayOrigin, 0, 10);

```
- 2.日期处理工具类：org.asframework.core.util.DateUtil
```
// 日期对象格式化为字符串
DateUtil.formatDate8(new Date()));
DateUtil.formatDate10(new Date()));
DateUtil.formatDate(new Date(), "yyyy年MM月dd日"));

// 字符串格式化为日期对象
DateUtil.parseDate("20190110"));
DateUtil.parseDate("2019-01-10 12:12:21"));
DateUtil.parseDate("2019年01月10日12点", "yyyy年MM月dd日HH点"));

// 获取之后的日期
Date curDate = new Date();
DateUtil.formatDate19(DateUtil.afterDay(curDate, 7));
DateUtil.formatDate19(DateUtil.afterMonth(curDate, -2));
DateUtil.formatDate19(DateUtil.afterYear(curDate, 1));
DateUtil.formatDate19(DateUtil.afterDate(curDate, 1, 1, 1));
DateUtil.formatDate19(DateUtil.afterDate(curDate, 0, 0, 1, 1, 1, 1, 0));

// 日期相减
Date nextDate = DateUtil.afterDate(curDate, 2, 0, 15, 12, 30, 0, 0);
DateUtil.minusDay(nextDate, curDate);
DateUtil.minusDayD(nextDate, curDate, 2);
DateUtil.minusMonth(nextDate, curDate);
DateUtil.minusMonthD(nextDate, curDate, 2);
DateUtil.minusYear(nextDate, curDate);
DateUtil.minusYearD(nextDate, curDate, 2);
```

- 3.加密解密工具类：org.asframework.core.util.EncryptUtil
```
// MD5加密
EncryptUtil.md5("wenzhaoming");
// DES加密
EncryptUtil.desEncode("wenzhaoming");
// DES解密
EncryptUtil.desDecode(EncryptUtil.desEncode("wenzhaoming");
```

- 4.文件操作工具类：org.asframework.core.util.FileUtil
```
//
```

- 5.集合各种类型等的工具类：org.asframework.core.util.LangUtil
```
// 判断对象是否为空
LangUtil.empty(null);
LangUtil.empty(" ");
LangUtil.isEmpty(new HashMap<>(1));

// 以下不为空
LangUtil.notEmpty(new String[1]);
LangUtil.isNotEmpty(new HashMap<>(1));

LangUtil.isList(Collections.emptyList());
LangUtil.isList(new ArrayList<>());
LangUtil.isMap(new HashMap<>(0));
LangUtil.isArray(new Object[] { 1 });
```
- 6.字符串处理工具类：org.asframework.core.util.StringUtil
```
// 字符为空判断
StringUtil.isEmpty(null);
StringUtil.isEmpty("");
StringUtil.isEmpty(" ");

// 字符为非空判断
StringUtil.notEmpty(" ");

// 字符为空白字符判断
StringUtil.isBlank(null);
StringUtil.isBlank("");
StringUtil.isBlank(" ");

// 字符为非空白字符判断
StringUtil.notBlank(" a ");

// 左填充字符
String lpad = StringUtil.padLeft("1", 10, '0');

// 右填充字符
String rpad = StringUtil.padRight("A", 10, '0');

// 本身字符超出长度后，不填充
String lpadMore = StringUtil.padLeft("123456789", 5, '0');

String a = "{}, what day it is today? It is {}!";
// 默认{}格式的内容替换
String b = StringUtil.replaceDefault(a, "Xiao Hua", "Monday");

a = "#{}, what day it is today? #{}";
// 指定正则表达式的内容替换
b = StringUtil.replace(a, "#\\{\\}", "Xiao Hong", "I dont known.");

Map<String, Object> map = new HashMap<>(2);
map.put("name1", "Xiao Ming");
map.put("date", "Friday");
a = "${name1}, what day it is today? It is ${date}!";
// 匹配${}的内容，并替换为指定内容
b = StringUtil.replaceByMap(a, map);
```


## 二、自定义异常类

- 1.一般异常类
```
org.asframework.core.exception.AsBaseException
org.asframework.core.exception.AsDBOperException
org.asframework.core.exception.AsRuleException
org.asframework.core.exception.AsRuntimeException
```

- 2.权限类异常类
```
org.asframework.core.exception.auth.ClientForbiddenException
org.asframework.core.exception.auth.UserInvalidException
org.asframework.core.exception.auth.UserTokenInvalidException
org.asframework.core.exception.auth.UserUnauthorizedException
```
