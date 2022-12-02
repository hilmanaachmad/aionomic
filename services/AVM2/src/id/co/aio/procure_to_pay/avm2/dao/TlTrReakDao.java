/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2.dao;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wavemaker.runtime.data.dao.WMGenericDaoImpl;
import com.wavemaker.runtime.data.dao.query.types.wmql.WMQLTypeHelper;

import id.co.aio.procure_to_pay.avm2.TlTrReak;
import id.co.aio.procure_to_pay.avm2.TlTrReakId;

/**
 * Specifies methods used to obtain and modify TlTrReak related information
 * which is stored in the database.
 */
@Repository("AVM2.TlTrReakDao")
public class TlTrReakDao extends WMGenericDaoImpl<TlTrReak, TlTrReakId> {

    @Autowired
    @Qualifier("AVM2Template")
    private HibernateTemplate template;

    @Autowired
    @Qualifier("AVM2WMQLTypeHelper")
    private WMQLTypeHelper wmqlTypeHelper;


    @Override
    public HibernateTemplate getTemplate() {
        return this.template;
    }

    @Override
    public WMQLTypeHelper getWMQLTypeHelper() {
        return this.wmqlTypeHelper;
    }

}