/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.controller;

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

import id.co.aio.procure_to_pay.aio_ptp.SystemConfig;
import id.co.aio.procure_to_pay.aio_ptp.service.SystemConfigService;


/**
 * Controller object for domain model class SystemConfig.
 * @see SystemConfig
 */
@RestController("aio_ptp.SystemConfigController")
@Api(value = "SystemConfigController", description = "Exposes APIs to work with SystemConfig resource.")
@RequestMapping("/aio_ptp/SystemConfig")
public class SystemConfigController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemConfigController.class);

    @Autowired
	@Qualifier("aio_ptp.SystemConfigService")
	private SystemConfigService systemConfigService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new SystemConfig instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public SystemConfig createSystemConfig(@RequestBody SystemConfig systemConfig) {
		LOGGER.debug("Create SystemConfig with information: {}" , systemConfig);

		systemConfig = systemConfigService.create(systemConfig);
		LOGGER.debug("Created SystemConfig with information: {}" , systemConfig);

	    return systemConfig;
	}

    @ApiOperation(value = "Returns the SystemConfig instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public SystemConfig getSystemConfig(@PathVariable("id") Integer id) {
        LOGGER.debug("Getting SystemConfig with id: {}" , id);

        SystemConfig foundSystemConfig = systemConfigService.getById(id);
        LOGGER.debug("SystemConfig details with id: {}" , foundSystemConfig);

        return foundSystemConfig;
    }

    @ApiOperation(value = "Updates the SystemConfig instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public SystemConfig editSystemConfig(@PathVariable("id") Integer id, @RequestBody SystemConfig systemConfig) {
        LOGGER.debug("Editing SystemConfig with id: {}" , systemConfig.getId());

        systemConfig.setId(id);
        systemConfig = systemConfigService.update(systemConfig);
        LOGGER.debug("SystemConfig details with id: {}" , systemConfig);

        return systemConfig;
    }
    
    @ApiOperation(value = "Partially updates the SystemConfig instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public SystemConfig patchSystemConfig(@PathVariable("id") Integer id, @RequestBody @MapTo(SystemConfig.class) Map<String, Object> systemConfigPatch) {
        LOGGER.debug("Partially updating SystemConfig with id: {}" , id);

        SystemConfig systemConfig = systemConfigService.partialUpdate(id, systemConfigPatch);
        LOGGER.debug("SystemConfig details after partial update: {}" , systemConfig);

        return systemConfig;
    }

    @ApiOperation(value = "Deletes the SystemConfig instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteSystemConfig(@PathVariable("id") Integer id) {
        LOGGER.debug("Deleting SystemConfig with id: {}" , id);

        SystemConfig deletedSystemConfig = systemConfigService.delete(id);

        return deletedSystemConfig != null;
    }

    /**
     * @deprecated Use {@link #findSystemConfigs(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of SystemConfig instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<SystemConfig> searchSystemConfigsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering SystemConfigs list by query filter:{}", (Object) queryFilters);
        return systemConfigService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of SystemConfig instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<SystemConfig> findSystemConfigs(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering SystemConfigs list by filter:", query);
        return systemConfigService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of SystemConfig instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<SystemConfig> filterSystemConfigs(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering SystemConfigs list by filter", query);
        return systemConfigService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportSystemConfigs(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return systemConfigService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportSystemConfigsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = SystemConfig.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> systemConfigService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of SystemConfig instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countSystemConfigs( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting SystemConfigs");
		return systemConfigService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getSystemConfigAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return systemConfigService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service SystemConfigService instance
	 */
	protected void setSystemConfigService(SystemConfigService service) {
		this.systemConfigService = service;
	}

}