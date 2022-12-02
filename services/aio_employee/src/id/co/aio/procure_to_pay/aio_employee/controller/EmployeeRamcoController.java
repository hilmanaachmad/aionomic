/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_employee.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.commons.wrapper.StringWrapper;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.manager.ExportedFileManager;
import com.wavemaker.runtime.file.model.Downloadable;
import com.wavemaker.tools.api.core.annotations.MapTo;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import id.co.aio.procure_to_pay.aio_employee.EmployeeRamco;
import id.co.aio.procure_to_pay.aio_employee.service.EmployeeRamcoService;


/**
 * Controller object for domain model class EmployeeRamco.
 * @see EmployeeRamco
 */
@RestController("aio_employee.EmployeeRamcoController")
@Api(value = "EmployeeRamcoController", description = "Exposes APIs to work with EmployeeRamco resource.")
@RequestMapping("/aio_employee/EmployeeRamco")
public class EmployeeRamcoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeRamcoController.class);

    @Autowired
	@Qualifier("aio_employee.EmployeeRamcoService")
	private EmployeeRamcoService employeeRamcoService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new EmployeeRamco instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public EmployeeRamco createEmployeeRamco(@RequestBody EmployeeRamco employeeRamco) {
		LOGGER.debug("Create EmployeeRamco with information: {}" , employeeRamco);

		employeeRamco = employeeRamcoService.create(employeeRamco);
		LOGGER.debug("Created EmployeeRamco with information: {}" , employeeRamco);

	    return employeeRamco;
	}

    @ApiOperation(value = "Returns the EmployeeRamco instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public EmployeeRamco getEmployeeRamco(@PathVariable("id") Integer id) {
        LOGGER.debug("Getting EmployeeRamco with id: {}" , id);

        EmployeeRamco foundEmployeeRamco = employeeRamcoService.getById(id);
        LOGGER.debug("EmployeeRamco details with id: {}" , foundEmployeeRamco);

        return foundEmployeeRamco;
    }

    @ApiOperation(value = "Updates the EmployeeRamco instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public EmployeeRamco editEmployeeRamco(@PathVariable("id") Integer id, @RequestBody EmployeeRamco employeeRamco) {
        LOGGER.debug("Editing EmployeeRamco with id: {}" , employeeRamco.getId());

        employeeRamco.setId(id);
        employeeRamco = employeeRamcoService.update(employeeRamco);
        LOGGER.debug("EmployeeRamco details with id: {}" , employeeRamco);

        return employeeRamco;
    }
    
    @ApiOperation(value = "Partially updates the EmployeeRamco instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public EmployeeRamco patchEmployeeRamco(@PathVariable("id") Integer id, @RequestBody @MapTo(EmployeeRamco.class) Map<String, Object> employeeRamcoPatch) {
        LOGGER.debug("Partially updating EmployeeRamco with id: {}" , id);

        EmployeeRamco employeeRamco = employeeRamcoService.partialUpdate(id, employeeRamcoPatch);
        LOGGER.debug("EmployeeRamco details after partial update: {}" , employeeRamco);

        return employeeRamco;
    }

    @ApiOperation(value = "Deletes the EmployeeRamco instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteEmployeeRamco(@PathVariable("id") Integer id) {
        LOGGER.debug("Deleting EmployeeRamco with id: {}" , id);

        EmployeeRamco deletedEmployeeRamco = employeeRamcoService.delete(id);

        return deletedEmployeeRamco != null;
    }

    /**
     * @deprecated Use {@link #findEmployeeRamcos(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of EmployeeRamco instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<EmployeeRamco> searchEmployeeRamcosByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering EmployeeRamcos list by query filter:{}", (Object) queryFilters);
        return employeeRamcoService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of EmployeeRamco instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<EmployeeRamco> findEmployeeRamcos(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering EmployeeRamcos list by filter:", query);
        return employeeRamcoService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of EmployeeRamco instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<EmployeeRamco> filterEmployeeRamcos(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering EmployeeRamcos list by filter", query);
        return employeeRamcoService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportEmployeeRamcos(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return employeeRamcoService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportEmployeeRamcosAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = EmployeeRamco.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> employeeRamcoService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of EmployeeRamco instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countEmployeeRamcos( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting EmployeeRamcos");
		return employeeRamcoService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getEmployeeRamcoAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return employeeRamcoService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service EmployeeRamcoService instance
	 */
	protected void setEmployeeRamcoService(EmployeeRamcoService service) {
		this.employeeRamcoService = service;
	}

}