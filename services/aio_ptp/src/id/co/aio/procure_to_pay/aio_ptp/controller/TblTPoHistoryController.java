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

import id.co.aio.procure_to_pay.aio_ptp.TblTPoHistory;
import id.co.aio.procure_to_pay.aio_ptp.service.TblTPoHistoryService;


/**
 * Controller object for domain model class TblTPoHistory.
 * @see TblTPoHistory
 */
@RestController("aio_ptp.TblTPoHistoryController")
@Api(value = "TblTPoHistoryController", description = "Exposes APIs to work with TblTPoHistory resource.")
@RequestMapping("/aio_ptp/TblTPoHistory")
public class TblTPoHistoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblTPoHistoryController.class);

    @Autowired
	@Qualifier("aio_ptp.TblTPoHistoryService")
	private TblTPoHistoryService tblTPoHistoryService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TblTPoHistory instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTPoHistory createTblTPoHistory(@RequestBody TblTPoHistory tblTpoHistory) {
		LOGGER.debug("Create TblTPoHistory with information: {}" , tblTpoHistory);

		tblTpoHistory = tblTPoHistoryService.create(tblTpoHistory);
		LOGGER.debug("Created TblTPoHistory with information: {}" , tblTpoHistory);

	    return tblTpoHistory;
	}

    @ApiOperation(value = "Returns the TblTPoHistory instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTPoHistory getTblTPoHistory(@PathVariable("id") Integer id) {
        LOGGER.debug("Getting TblTPoHistory with id: {}" , id);

        TblTPoHistory foundTblTPoHistory = tblTPoHistoryService.getById(id);
        LOGGER.debug("TblTPoHistory details with id: {}" , foundTblTPoHistory);

        return foundTblTPoHistory;
    }

    @ApiOperation(value = "Updates the TblTPoHistory instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTPoHistory editTblTPoHistory(@PathVariable("id") Integer id, @RequestBody TblTPoHistory tblTpoHistory) {
        LOGGER.debug("Editing TblTPoHistory with id: {}" , tblTpoHistory.getId());

        tblTpoHistory.setId(id);
        tblTpoHistory = tblTPoHistoryService.update(tblTpoHistory);
        LOGGER.debug("TblTPoHistory details with id: {}" , tblTpoHistory);

        return tblTpoHistory;
    }
    
    @ApiOperation(value = "Partially updates the TblTPoHistory instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTPoHistory patchTblTPoHistory(@PathVariable("id") Integer id, @RequestBody @MapTo(TblTPoHistory.class) Map<String, Object> tblTpoHistoryPatch) {
        LOGGER.debug("Partially updating TblTPoHistory with id: {}" , id);

        TblTPoHistory tblTpoHistory = tblTPoHistoryService.partialUpdate(id, tblTpoHistoryPatch);
        LOGGER.debug("TblTPoHistory details after partial update: {}" , tblTpoHistory);

        return tblTpoHistory;
    }

    @ApiOperation(value = "Deletes the TblTPoHistory instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTblTPoHistory(@PathVariable("id") Integer id) {
        LOGGER.debug("Deleting TblTPoHistory with id: {}" , id);

        TblTPoHistory deletedTblTPoHistory = tblTPoHistoryService.delete(id);

        return deletedTblTPoHistory != null;
    }

    /**
     * @deprecated Use {@link #findTblTPoHistories(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TblTPoHistory instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTPoHistory> searchTblTPoHistoriesByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TblTPoHistories list by query filter:{}", (Object) queryFilters);
        return tblTPoHistoryService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblTPoHistory instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTPoHistory> findTblTPoHistories(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblTPoHistories list by filter:", query);
        return tblTPoHistoryService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblTPoHistory instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTPoHistory> filterTblTPoHistories(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblTPoHistories list by filter", query);
        return tblTPoHistoryService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTblTPoHistories(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tblTPoHistoryService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTblTPoHistoriesAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TblTPoHistory.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tblTPoHistoryService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TblTPoHistory instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTblTPoHistories( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TblTPoHistories");
		return tblTPoHistoryService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTblTPoHistoryAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tblTPoHistoryService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TblTPoHistoryService instance
	 */
	protected void setTblTPoHistoryService(TblTPoHistoryService service) {
		this.tblTPoHistoryService = service;
	}

}