# as-data-mybatis-1.1.0.jar 使用说明

- MyBatis查询条件处理工具类：org.asframework.data.mybatis.util.MyQueryHelper
```
// 分页查询对象说明
@Data
public class XmglKphkPageQuery extends BasePageQuery<XmglKphk> {

    /** 省份 */
    private String sf;
    /** 开票名称：查询kpmc like '%kpmc%'的数据 */
    @QueryRule(value = QueryRuleEnum.LIKE)
    private String kpmc;
    /** 开票日期从：查询kprq>=kprqFrom的数据，entityFieldName是实体类的字段（非数据库字段） */
    @QueryRule(value = QueryRuleEnum.GE, entityFieldName = "kprq")
    private Date kprqFrom;
    /** 开票日期至：查询kprq<=kprqFrom的数据，entityFieldName是实体类的字段（非数据库字段） */
    @QueryRule(value = QueryRuleEnum.LE, entityFieldName = "kprq")
    Date kprqTo;
    /** 发票号码：@QueryIgnoreField忽略字段，表示该字段不作为查询条件 */
    @QueryIgnoreField
    private String fphm;

}
```