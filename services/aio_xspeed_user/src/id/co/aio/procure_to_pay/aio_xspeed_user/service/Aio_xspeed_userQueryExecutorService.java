/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_xspeed_user.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.OutputStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wavemaker.runtime.data.export.ExportOptions;

import id.co.aio.procure_to_pay.aio_xspeed_user.models.query.*;

public interface Aio_xspeed_userQueryExecutorService {

    Page<QgetEmployeeSupervisorResponse> executeQGetEmployeeSupervisor(String nik, Pageable pageable);

    void exportQGetEmployeeSupervisor(String nik, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Page<QgetAllUserResponse> executeQGetAllUser(Pageable pageable);

    void exportQGetAllUser(ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Page<GetEmployeeDataResponse> executeGetEmployeeData(String nik, Pageable pageable);

    void exportGetEmployeeData(String nik, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Integer executeQChangePassEmpl(QchangePassEmplRequest qchangePassEmplRequest);

    Integer executeQForgotPassEmp(QforgotPassEmpRequest qforgotPassEmpRequest);

    Page<QcheckUserNikPassResponse> executeQCheckUserNikPass(String nik, String password, Pageable pageable);

    void exportQCheckUserNikPass(String nik, String password, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Page<QcheckUserEmailNikResponse> executeQCheckUserEmailNik(String email, String nik, Pageable pageable);

    void exportQCheckUserEmailNik(String email, String nik, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

}