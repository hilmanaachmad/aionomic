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

import id.co.aio.procure_to_pay.aio_ptp.TblMRfqParameter;
import id.co.aio.procure_to_pay.aio_ptp.service.TblMRfqParameterService;


/**
 * Controller object for domain model class TblMRfqParameter.
 * @see TblMRfqParameter
 */
@RestController("aio_ptp.TblMRfqParameterController")
@Api(value = "TblMRfqParameterController", description = "Exposes APIs to work with TblMRfqParameter resource.")
@RequestMapping("/aio_ptp/TblMRfqParameter")
public class TblMRfqParameterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblMRfqParameterController.class);

    @Autowired
	@Qualifier("aio_ptp.TblMRfqParameterService")
	private TblMRfqParameterService tblMRfqParameterService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TblMRfqParameter instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblMRfqParameter createTblMRfqParameter(@RequestBody TblMRfqParameter tblMrfqParameter) {
		LOGGER.debug("Create TblMRfqParameter with information: {}" , tblMrfqParameter);

		tblMrfqParameter = tblMRfqParameterService.create(tblMrfqParameter);
		LOGGER.debug("Created TblMRfqParameter with information: {}" , tblMrfqParameter);

	    return tblMrfqParameter;
	}

    @ApiOperation(value = "Returns the TblMRfqParameter instance associated with the given id.")
    @RequestMapping(value = "/{rfqId:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblMRfqParameter getTblMRfqParameter(@PathVariable("rfqId") Integer rfqId) {
        LOGGER.debug("Getting TblMRfqParameter with id: {}" , rfqId);

        TblMRfqParameter foundTblMRfqParameter = tblMRfqParameterService.getById(rfqId);
        LOGGER.debug("TblMRfqParameter details with id: {}" , foundTblMRfqParameter);

        return foundTblMRfqParameter;
    }

    @ApiOperation(value = "Updates the TblMRfqParameter instance associated with the given id.")
    @RequestMapping(value = "/{rfqId:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblMRfqParameter editTblMRfqParameter(@PathVariable("rfqId") Integer rfqId, @RequestBody TblMRfqParameter tblMrfqParameter) {
        LOGGER.debug("Editing TblMRfqParameter with id: {}" , tblMrfqParameter.getRfqId());

        tblMrfqParameter.setRfqId(rfqId);
        tblMrfqParameter = tblMRfqParameterService.update(tblMrfqParameter);
        LOGGER.debug("TblMRfqParameter details with id: {}" , tblMrfqParameter);

        return tblMrfqParameter;
    }
    
    @ApiOperation(value = "Partially updates the TblMRfqParameter instance associated with the given id.")
    @RequestMapping(value = "/{rfqId:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblMRfqParameter patchTblMRfqParameter(@PathVariable("rfqId") Integer rfqId, @RequestBody @MapTo(TblMRfqParameter.class) Map<String, Object> tblMrfqParameterPatch) {
        LOGGER.debug("Partially updating TblMRfqParameter with id: {}" , rfqId);

        TblMRfqParameter tblMrfqParameter = tblMRfqParameterService.partialUpdate(rfqId, tblMrfqParameterPatch);
        LOGGER.debug("TblMRfqParameter details after partial update: {}" , tblMrfqParameter);

        return tblMrfqParameter;
    }

    @ApiOperation(value = "Deletes the TblMRfqParameter instance associated with the given id.")
    @RequestMapping(value = "/{rfqId:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTblMRfqParameter(@PathVariable("rfqId") Integer rfqId) {
        LOGGER.debug("Deleting TblMRfqParameter with id: {}" , rfqId);

        TblMRfqParameter deletedTblMRfqParameter = tblMRfqParameterService.delete(rfqId);

        return deletedTblMRfqParameter != null;
    }

    /**
     * @deprecated Use {@link #findTblMRfqParameters(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TblMRfqParameter instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblMRfqParameter> searchTblMRfqParametersByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TblMRfqParameters list by query filter:{}", (Object) queryFilters);
        return tblMRfqParameterService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblMRfqParameter instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblMRfqParameter> findTblMRfqParameters(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblMRfqParameters list by filter:", query);
        return tblMRfqParameterService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblMRfqParameter instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblMRfqParameter> filterTblMRfqParameters(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblMRfqParameters list by filter", query);
        return tblMRfqParameterService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTblMRfqParameters(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tblMRfqParameterService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTblMRfqParametersAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TblMRfqParameter.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tblMRfqParameterService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TblMRfqParameter instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTblMRfqParameters( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TblMRfqParameters");
		return tblMRfqParameterService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTblMRfqParameterAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tblMRfqParameterService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TblMRfqParameterService instance
	 */
	protected void setTblMRfqParameterService(TblMRfqParameterService service) {
		this.tblMRfqParameterService = service;
	}

}