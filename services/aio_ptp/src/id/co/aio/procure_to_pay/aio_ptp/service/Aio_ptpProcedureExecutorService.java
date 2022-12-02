/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import id.co.aio.procure_to_pay.aio_ptp.models.procedure.*;

public interface Aio_ptpProcedureExecutorService {

    SpUsageBudgetDetailsResponse executeSp_usage_budgetDetails(String bhId, String typeData, String valueBeginning, String valueAdjustment, String valueReserve, String valueCommitment, String valueActual, String createdAtStart, String createdAtEnd, String createdBy, String coa, String remarks, String docNumber);

    SpBudgetDetail2Response executeSp_budgetDetail2(String bhId, String typeData, String valueBeginning, String valueAdjustment, String valueReclass, String valueAdditional, String createdAtStart, String createdAtEnd, String createdBy, String coa);

    SpBudgetDetail1Response executeSpBudgetDetail1(String accType, String comp, String brand, String dep, String emp, String year, String admin);

    SpBudgetDetailsResponse executeSp_budgetDetails(String bhId, String typeData, String valueBeginning, String valueAdjustment, String valueReclass, String valueAdditional, String createdAtStart, String createdAtEnd, String createdBy, String coa, String remarks, String docNumber);

}