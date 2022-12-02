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

import id.co.aio.procure_to_pay.aio_ptp.TblMEmpPriceLimit;
import id.co.aio.procure_to_pay.aio_ptp.service.TblMEmpPriceLimitService;


/**
 * Controller object for domain model class TblMEmpPriceLimit.
 * @see TblMEmpPriceLimit
 */
@RestController("aio_ptp.TblMEmpPriceLimitController")
@Api(value = "TblMEmpPriceLimitController", description = "Exposes APIs to work with TblMEmpPriceLimit resource.")
@RequestMapping("/aio_ptp/TblMEmpPriceLimit")
public class TblMEmpPriceLimitController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblMEmpPriceLimitController.class);

    @Autowired
	@Qualifier("aio_ptp.TblMEmpPriceLimitService")
	private TblMEmpPriceLimitService tblMEmpPriceLimitService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TblMEmpPriceLimit instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblMEmpPriceLimit createTblMEmpPriceLimit(@RequestBody TblMEmpPriceLimit tblMempPriceLimit) {
		LOGGER.debug("Create TblMEmpPriceLimit with information: {}" , tblMempPriceLimit);

		tblMempPriceLimit = tblMEmpPriceLimitService.create(tblMempPriceLimit);
		LOGGER.debug("Created TblMEmpPriceLimit with information: {}" , tblMempPriceLimit);

	    return tblMempPriceLimit;
	}

    @ApiOperation(value = "Returns the TblMEmpPriceLimit instance associated with the given id.")
    @RequestMapping(value = "/{eplId:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblMEmpPriceLimit getTblMEmpPriceLimit(@PathVariable("eplId") Integer eplId) {
        LOGGER.debug("Getting TblMEmpPriceLimit with id: {}" , eplId);

        TblMEmpPriceLimit foundTblMEmpPriceLimit = tblMEmpPriceLimitService.getById(eplId);
        LOGGER.debug("TblMEmpPriceLimit details with id: {}" , foundTblMEmpPriceLimit);

        return foundTblMEmpPriceLimit;
    }

    @ApiOperation(value = "Updates the TblMEmpPriceLimit instance associated with the given id.")
    @RequestMapping(value = "/{eplId:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblMEmpPriceLimit editTblMEmpPriceLimit(@PathVariable("eplId") Integer eplId, @RequestBody TblMEmpPriceLimit tblMempPriceLimit) {
        LOGGER.debug("Editing TblMEmpPriceLimit with id: {}" , tblMempPriceLimit.getEplId());

        tblMempPriceLimit.setEplId(eplId);
        tblMempPriceLimit = tblMEmpPriceLimitService.update(tblMempPriceLimit);
        LOGGER.debug("TblMEmpPriceLimit details with id: {}" , tblMempPriceLimit);

        return tblMempPriceLimit;
    }
    
    @ApiOperation(value = "Partially updates the TblMEmpPriceLimit instance associated with the given id.")
    @RequestMapping(value = "/{eplId:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblMEmpPriceLimit patchTblMEmpPriceLimit(@PathVariable("eplId") Integer eplId, @RequestBody @MapTo(TblMEmpPriceLimit.class) Map<String, Object> tblMempPriceLimitPatch) {
        LOGGER.debug("Partially updating TblMEmpPriceLimit with id: {}" , eplId);

        TblMEmpPriceLimit tblMempPriceLimit = tblMEmpPriceLimitService.partialUpdate(eplId, tblMempPriceLimitPatch);
        LOGGER.debug("TblMEmpPriceLimit details after partial update: {}" , tblMempPriceLimit);

        return tblMempPriceLimit;
    }

    @ApiOperation(value = "Deletes the TblMEmpPriceLimit instance associated with the given id.")
    @RequestMapping(value = "/{eplId:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTblMEmpPriceLimit(@PathVariable("eplId") Integer eplId) {
        LOGGER.debug("Deleting TblMEmpPriceLimit with id: {}" , eplId);

        TblMEmpPriceLimit deletedTblMEmpPriceLimit = tblMEmpPriceLimitService.delete(eplId);

        return deletedTblMEmpPriceLimit != null;
    }

    /**
     * @deprecated Use {@link #findTblMEmpPriceLimits(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TblMEmpPriceLimit instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblMEmpPriceLimit> searchTblMEmpPriceLimitsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TblMEmpPriceLimits list by query filter:{}", (Object) queryFilters);
        return tblMEmpPriceLimitService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblMEmpPriceLimit instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblMEmpPriceLimit> findTblMEmpPriceLimits(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblMEmpPriceLimits list by filter:", query);
        return tblMEmpPriceLimitService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblMEmpPriceLimit instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblMEmpPriceLimit> filterTblMEmpPriceLimits(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblMEmpPriceLimits list by filter", query);
        return tblMEmpPriceLimitService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTblMEmpPriceLimits(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tblMEmpPriceLimitService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTblMEmpPriceLimitsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TblMEmpPriceLimit.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tblMEmpPriceLimitService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TblMEmpPriceLimit instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTblMEmpPriceLimits( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TblMEmpPriceLimits");
		return tblMEmpPriceLimitService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTblMEmpPriceLimitAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tblMEmpPriceLimitService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TblMEmpPriceLimitService instance
	 */
	protected void setTblMEmpPriceLimitService(TblMEmpPriceLimitService service) {
		this.tblMEmpPriceLimitService = service;
	}

}