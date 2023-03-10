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

import id.co.aio.procure_to_pay.aio_ptp.TblMDivisionBod;
import id.co.aio.procure_to_pay.aio_ptp.service.TblMDivisionBodService;


/**
 * Controller object for domain model class TblMDivisionBod.
 * @see TblMDivisionBod
 */
@RestController("aio_ptp.TblMDivisionBodController")
@Api(value = "TblMDivisionBodController", description = "Exposes APIs to work with TblMDivisionBod resource.")
@RequestMapping("/aio_ptp/TblMDivisionBod")
public class TblMDivisionBodController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblMDivisionBodController.class);

    @Autowired
	@Qualifier("aio_ptp.TblMDivisionBodService")
	private TblMDivisionBodService tblMDivisionBodService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TblMDivisionBod instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblMDivisionBod createTblMDivisionBod(@RequestBody TblMDivisionBod tblMdivisionBod) {
		LOGGER.debug("Create TblMDivisionBod with information: {}" , tblMdivisionBod);

		tblMdivisionBod = tblMDivisionBodService.create(tblMdivisionBod);
		LOGGER.debug("Created TblMDivisionBod with information: {}" , tblMdivisionBod);

	    return tblMdivisionBod;
	}

    @ApiOperation(value = "Returns the TblMDivisionBod instance associated with the given id.")
    @RequestMapping(value = "/{divBodId:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblMDivisionBod getTblMDivisionBod(@PathVariable("divBodId") Integer divBodId) {
        LOGGER.debug("Getting TblMDivisionBod with id: {}" , divBodId);

        TblMDivisionBod foundTblMDivisionBod = tblMDivisionBodService.getById(divBodId);
        LOGGER.debug("TblMDivisionBod details with id: {}" , foundTblMDivisionBod);

        return foundTblMDivisionBod;
    }

    @ApiOperation(value = "Updates the TblMDivisionBod instance associated with the given id.")
    @RequestMapping(value = "/{divBodId:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblMDivisionBod editTblMDivisionBod(@PathVariable("divBodId") Integer divBodId, @RequestBody TblMDivisionBod tblMdivisionBod) {
        LOGGER.debug("Editing TblMDivisionBod with id: {}" , tblMdivisionBod.getDivBodId());

        tblMdivisionBod.setDivBodId(divBodId);
        tblMdivisionBod = tblMDivisionBodService.update(tblMdivisionBod);
        LOGGER.debug("TblMDivisionBod details with id: {}" , tblMdivisionBod);

        return tblMdivisionBod;
    }
    
    @ApiOperation(value = "Partially updates the TblMDivisionBod instance associated with the given id.")
    @RequestMapping(value = "/{divBodId:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblMDivisionBod patchTblMDivisionBod(@PathVariable("divBodId") Integer divBodId, @RequestBody @MapTo(TblMDivisionBod.class) Map<String, Object> tblMdivisionBodPatch) {
        LOGGER.debug("Partially updating TblMDivisionBod with id: {}" , divBodId);

        TblMDivisionBod tblMdivisionBod = tblMDivisionBodService.partialUpdate(divBodId, tblMdivisionBodPatch);
        LOGGER.debug("TblMDivisionBod details after partial update: {}" , tblMdivisionBod);

        return tblMdivisionBod;
    }

    @ApiOperation(value = "Deletes the TblMDivisionBod instance associated with the given id.")
    @RequestMapping(value = "/{divBodId:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTblMDivisionBod(@PathVariable("divBodId") Integer divBodId) {
        LOGGER.debug("Deleting TblMDivisionBod with id: {}" , divBodId);

        TblMDivisionBod deletedTblMDivisionBod = tblMDivisionBodService.delete(divBodId);

        return deletedTblMDivisionBod != null;
    }

    /**
     * @deprecated Use {@link #findTblMDivisionBods(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TblMDivisionBod instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblMDivisionBod> searchTblMDivisionBodsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TblMDivisionBods list by query filter:{}", (Object) queryFilters);
        return tblMDivisionBodService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblMDivisionBod instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblMDivisionBod> findTblMDivisionBods(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblMDivisionBods list by filter:", query);
        return tblMDivisionBodService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblMDivisionBod instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblMDivisionBod> filterTblMDivisionBods(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblMDivisionBods list by filter", query);
        return tblMDivisionBodService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTblMDivisionBods(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tblMDivisionBodService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTblMDivisionBodsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TblMDivisionBod.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tblMDivisionBodService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TblMDivisionBod instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTblMDivisionBods( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TblMDivisionBods");
		return tblMDivisionBodService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTblMDivisionBodAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tblMDivisionBodService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TblMDivisionBodService instance
	 */
	protected void setTblMDivisionBodService(TblMDivisionBodService service) {
		this.tblMDivisionBodService = service;
	}

}