/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_xspeed_user.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.commons.wrapper.IntegerWrapper;
import com.wavemaker.commons.wrapper.StringWrapper;
import com.wavemaker.runtime.data.export.ExportOptions;
import com.wavemaker.runtime.file.manager.ExportedFileManager;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import id.co.aio.procure_to_pay.aio_xspeed_user.service.Aio_xspeed_userQueryExecutorService;
import id.co.aio.procure_to_pay.aio_xspeed_user.models.query.*;

@RestController(value = "Aio_xspeed_user.QueryExecutionController")
@RequestMapping("/aio_xspeed_user/queryExecutor")
@Api(value = "QueryExecutionController", description = "controller class for query execution")
public class QueryExecutionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryExecutionController.class);

    @Autowired
    private Aio_xspeed_userQueryExecutorService queryService;

    @Autowired
	private ExportedFileManager exportedFileManager;

    @RequestMapping(value = "/queries/qGetEmployeeSupervisor", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "qGetEmployeeSupervisor")
    public Page<QgetEmployeeSupervisorResponse> executeQGetEmployeeSupervisor(@RequestParam(value = "nik") String nik, Pageable pageable, HttpServletRequest _request) {
        LOGGER.debug("Executing named query: qGetEmployeeSupervisor");
        Page<QgetEmployeeSupervisorResponse> _result = queryService.executeQGetEmployeeSupervisor(nik, pageable);
        LOGGER.debug("got the result for named query: qGetEmployeeSupervisor, result:{}", _result);
        return _result;
    }

    @ApiOperation(value = "Returns downloadable file url for query qGetEmployeeSupervisor")
    @RequestMapping(value = "/queries/qGetEmployeeSupervisor/export", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportQGetEmployeeSupervisor(@RequestParam(value = "nik") String nik, @RequestBody ExportOptions exportOptions, Pageable pageable) {
        LOGGER.debug("Exporting named query: qGetEmployeeSupervisor");

        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = "qGetEmployeeSupervisor";
        }
        exportedFileName += exportOptions.getExportType().getExtension();

        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName,
                        outputStream -> queryService.exportQGetEmployeeSupervisor(nik,  exportOptions, pageable, outputStream));

        return new StringWrapper(exportedUrl);
    }

    @RequestMapping(value = "/queries/qGetAllUser", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "Get lg_nik dan lg_name")
    public Page<QgetAllUserResponse> executeQGetAllUser(Pageable pageable, HttpServletRequest _request) {
        LOGGER.debug("Executing named query: qGetAllUser");
        Page<QgetAllUserResponse> _result = queryService.executeQGetAllUser(pageable);
        LOGGER.debug("got the result for named query: qGetAllUser, result:{}", _result);
        return _result;
    }

    @ApiOperation(value = "Returns downloadable file url for query qGetAllUser")
    @RequestMapping(value = "/queries/qGetAllUser/export", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportQGetAllUser(@RequestBody ExportOptions exportOptions, Pageable pageable) {
        LOGGER.debug("Exporting named query: qGetAllUser");

        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = "qGetAllUser";
        }
        exportedFileName += exportOptions.getExportType().getExtension();

        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName,
                        outputStream -> queryService.exportQGetAllUser( exportOptions, pageable, outputStream));

        return new StringWrapper(exportedUrl);
    }

    @RequestMapping(value = "/queries/getEmployeeData", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "get employee data")
    public Page<GetEmployeeDataResponse> executeGetEmployeeData(@RequestParam(value = "nik") String nik, Pageable pageable, HttpServletRequest _request) {
        LOGGER.debug("Executing named query: getEmployeeData");
        Page<GetEmployeeDataResponse> _result = queryService.executeGetEmployeeData(nik, pageable);
        LOGGER.debug("got the result for named query: getEmployeeData, result:{}", _result);
        return _result;
    }

    @ApiOperation(value = "Returns downloadable file url for query getEmployeeData")
    @RequestMapping(value = "/queries/getEmployeeData/export", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportGetEmployeeData(@RequestParam(value = "nik") String nik, @RequestBody ExportOptions exportOptions, Pageable pageable) {
        LOGGER.debug("Exporting named query: getEmployeeData");

        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = "getEmployeeData";
        }
        exportedFileName += exportOptions.getExportType().getExtension();

        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName,
                        outputStream -> queryService.exportGetEmployeeData(nik,  exportOptions, pageable, outputStream));

        return new StringWrapper(exportedUrl);
    }

    @RequestMapping(value = "/queries/qChangePassEmpl", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "qChangePassEmpl")
    public IntegerWrapper executeQChangePassEmpl(@Valid @RequestBody QchangePassEmplRequest qchangePassEmplRequest, HttpServletRequest _request) {
        LOGGER.debug("Executing named query: qChangePassEmpl");
        Integer _result = queryService.executeQChangePassEmpl(qchangePassEmplRequest);
        LOGGER.debug("got the result for named query: qChangePassEmpl, result:{}", _result);
        return new IntegerWrapper(_result);
    }

    @RequestMapping(value = "/queries/qForgotPassEmp", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "qForgotPassEmp")
    public IntegerWrapper executeQForgotPassEmp(@Valid @RequestBody QforgotPassEmpRequest qforgotPassEmpRequest, HttpServletRequest _request) {
        LOGGER.debug("Executing named query: qForgotPassEmp");
        Integer _result = queryService.executeQForgotPassEmp(qforgotPassEmpRequest);
        LOGGER.debug("got the result for named query: qForgotPassEmp, result:{}", _result);
        return new IntegerWrapper(_result);
    }

    @RequestMapping(value = "/queries/qCheckUserNikPass", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "qCheckUserNikPass")
    public Page<QcheckUserNikPassResponse> executeQCheckUserNikPass(@RequestParam(value = "nik") String nik, @RequestParam(value = "password") String password, Pageable pageable, HttpServletRequest _request) {
        LOGGER.debug("Executing named query: qCheckUserNikPass");
        Page<QcheckUserNikPassResponse> _result = queryService.executeQCheckUserNikPass(nik, password, pageable);
        LOGGER.debug("got the result for named query: qCheckUserNikPass, result:{}", _result);
        return _result;
    }

    @ApiOperation(value = "Returns downloadable file url for query qCheckUserNikPass")
    @RequestMapping(value = "/queries/qCheckUserNikPass/export", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportQCheckUserNikPass(@RequestParam(value = "nik") String nik, @RequestParam(value = "password") String password, @RequestBody ExportOptions exportOptions, Pageable pageable) {
        LOGGER.debug("Exporting named query: qCheckUserNikPass");

        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = "qCheckUserNikPass";
        }
        exportedFileName += exportOptions.getExportType().getExtension();

        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName,
                        outputStream -> queryService.exportQCheckUserNikPass(nik, password,  exportOptions, pageable, outputStream));

        return new StringWrapper(exportedUrl);
    }

    @RequestMapping(value = "/queries/qCheckUserEmailNik", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "qCheckUserEmailNik")
    public Page<QcheckUserEmailNikResponse> executeQCheckUserEmailNik(@RequestParam(value = "email") String email, @RequestParam(value = "nik") String nik, Pageable pageable, HttpServletRequest _request) {
        LOGGER.debug("Executing named query: qCheckUserEmailNik");
        Page<QcheckUserEmailNikResponse> _result = queryService.executeQCheckUserEmailNik(email, nik, pageable);
        LOGGER.debug("got the result for named query: qCheckUserEmailNik, result:{}", _result);
        return _result;
    }

    @ApiOperation(value = "Returns downloadable file url for query qCheckUserEmailNik")
    @RequestMapping(value = "/queries/qCheckUserEmailNik/export", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportQCheckUserEmailNik(@RequestParam(value = "email") String email, @RequestParam(value = "nik") String nik, @RequestBody ExportOptions exportOptions, Pageable pageable) {
        LOGGER.debug("Exporting named query: qCheckUserEmailNik");

        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = "qCheckUserEmailNik";
        }
        exportedFileName += exportOptions.getExportType().getExtension();

        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName,
                        outputStream -> queryService.exportQCheckUserEmailNik(email, nik,  exportOptions, pageable, outputStream));

        return new StringWrapper(exportedUrl);
    }

}