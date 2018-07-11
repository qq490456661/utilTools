/**
 * onway.com Inc.
 * Copyright (c) 2018-2018 All Rights Reserved.
 */
package DatabaseParse;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author junjie.lin
 * @version $Id: TableModel.java, v 0.1 2018/7/11 0011 15:57 junjie.lin Exp $
 */
@Data
@Accessors(chain = true)
public class TableModel {

    //����
    private String tableName;

    //�����ֶΣ����ŷָ�
    private String otherFields;

    //Pojo���� = ${tableName} + "Pojo"
    private String pojoName;

    //id,user_id,password
    private String commonFields;

    //user_id as userId
    private String as;

    //a.user_id as userId
    private String aDianAs;

    //userId
    private String firstUppercase;

    //#id#
    private String jing;

    //#{id}
    private String jingMybatis;

    //a.id = #id#
    private String fieldsEqualsJing;








}
