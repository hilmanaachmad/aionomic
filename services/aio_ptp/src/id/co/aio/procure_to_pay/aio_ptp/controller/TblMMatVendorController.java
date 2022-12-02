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

import id.co.aio.procure_to_pay.aio_ptp.TblMMatVendor;
import id.co.aio.procure_to_pay.aio_ptp.service.TblMMatVendorService;


/**
 * Controller object for domain model class TblMMatVendor.
 * @see TblMMatVendor
 */
@RestController("aio_ptp.TblMMatVendorController")
@Api(value = "TblMMatVendorController", description = "Exposes APIs to work with TblMMatVendor resource.")
@RequestMapping("/aio_ptp/TblMMatVendor")
public class TblMMatVendorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblMMatVendorController.class);

    @Autowired
	@Qualifier("aio_ptp.TblMMatVendorService")
	private TblMMatVendorService tblMMatVendorService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TblMMatVendor instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblMMatVendor createTblMMatVendor(@RequestBody TblMMatVendor tblMmatVendor) {
		LOGGER.debug("Create TblMMatVendor with information: {}" , tblMmatVendor);

		tblMmatVendor = tblMMatVendorService.create(tblMmatVendor);
		LOGGER.debug("Created TblMMatVendor with information: {}" , tblMmatVendor);

	    return tblMmatVendor;
	}

    @ApiOperation(value = "Returns the TblMMatVendor instance associated with the given id.")
    @RequestMapping(value = "/{mvId:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblMMatVendor getTblMMatVendor(@PathVariable("mvId") Integer mvId) {
        LOGGER.debug("Getting TblMMatVendor with id: {}" , mvId);

        TblMMatVendor foundTblMMatVendor = tblMMatVendorService.getById(mvId);
        LOGGER.debug("TblMMatVendor details with id: {}" , foundTblMMatVendor);

        return foundTblMMatVendor;
    }

    @ApiOperation(value = "Updates the TblMMatVendor instance associated with the given id.")
    @RequestMapping(value = "/{mvId:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblMMatVendor editTblMMatVendor(@PathVariable("mvId") Integer mvId, @RequestBody TblMMatVendor tblMmatVendor) {
        LOGGER.debug("Editing TblMMatVendor with id: {}" , tblMmatVendor.getMvId());

        tblMmatVendor.setMvId(mvId);
        tblMmatVendor = tblMMatVendorService.update(tblMmatVendor);
        LOGGER.debug("TblMMatVendor details with id: {}" , tblMmatVendor);

        return tblMmatVendor;
    }
    
    @ApiOperation(value = "Partially updates the TblMMatVendor instance associated with the given id.")
    @RequestMapping(value = "/{mvId:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblMMatVendor patchTblMMatVendor(@PathVariable("mvId") Integer mvId, @RequestBody @MapTo(TblMMatVendor.class) Map<String, Object> tblMmatVendorPatch) {
        LOGGER.debug("Partially updating TblMMatVendor with id: {}" , mvId);

        TblMMatVendor tblMmatVendor = tblMMatVendorService.partialUpdate(mvId, tblMmatVendorPatch);
        LOGGER.debug("TblMMatVendor details after partial update: {}" , tblMmatVendor);

        return tblMmatVendor;
    }

    @ApiOperation(value = "Deletes the TblMMatVendor instance associated with the given id.")
    @RequestMapping(value = "/{mvId:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTblMMatVendor(@PathVariable("mvId") Integer mvId) {
        LOGGER.debug("Deleting TblMMatVendor with id: {}" , mvId);

        TblMMatVendor deletedTblMMatVendor = tblMMatVendorService.delete(mvId);

        return deletedTblMMatVendor != null;
    }

    /**
     * @deprecated Use {@link #findTblMMatVendors(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TblMMatVendor instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblMMatVendor> searchTblMMatVendorsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TblMMatVendors list by query filter:{}", (Object) queryFilters);
        return tblMMatVendorService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblMMatVendor instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblMMatVendor> findTblMMatVendors(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblMMatVendors list by filter:", query);
        return tblMMatVendorService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblMMatVendor instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblMMatVendor> filterTblMMatVendors(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblMMatVendors list by filter", query);
        return tblMMatVendorService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTblMMatVendors(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tblMMatVendorService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTblMMatVendorsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TblMMatVendor.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tblMMatVendorService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TblMMatVendor instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTblMMatVendors( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TblMMatVendors");
		return tblMMatVendorService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTblMMatVendorAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tblMMatVendorService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TblMMatVendorService instance
	 */
	protected void setTblMMatVendorService(TblMMatVendorService service) {
		this.tblMMatVendorService = service;
	}

}