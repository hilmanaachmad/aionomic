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

import id.co.aio.procure_to_pay.aio_ptp.TblTBudgetDetails;
import id.co.aio.procure_to_pay.aio_ptp.TblTBudgetHeader;
import id.co.aio.procure_to_pay.aio_ptp.TblTPrLineItem;
import id.co.aio.procure_to_pay.aio_ptp.service.TblTBudgetHeaderService;


/**
 * Controller object for domain model class TblTBudgetHeader.
 * @see TblTBudgetHeader
 */
@RestController("aio_ptp.TblTBudgetHeaderController")
@Api(value = "TblTBudgetHeaderController", description = "Exposes APIs to work with TblTBudgetHeader resource.")
@RequestMapping("/aio_ptp/TblTBudgetHeader")
public class TblTBudgetHeaderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblTBudgetHeaderController.class);

    @Autowired
	@Qualifier("aio_ptp.TblTBudgetHeaderService")
	private TblTBudgetHeaderService tblTBudgetHeaderService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TblTBudgetHeader instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTBudgetHeader createTblTBudgetHeader(@RequestBody TblTBudgetHeader tblTbudgetHeader) {
		LOGGER.debug("Create TblTBudgetHeader with information: {}" , tblTbudgetHeader);

		tblTbudgetHeader = tblTBudgetHeaderService.create(tblTbudgetHeader);
		LOGGER.debug("Created TblTBudgetHeader with information: {}" , tblTbudgetHeader);

	    return tblTbudgetHeader;
	}

    @ApiOperation(value = "Returns the TblTBudgetHeader instance associated with the given id.")
    @RequestMapping(value = "/{bhId:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTBudgetHeader getTblTBudgetHeader(@PathVariable("bhId") Integer bhId) {
        LOGGER.debug("Getting TblTBudgetHeader with id: {}" , bhId);

        TblTBudgetHeader foundTblTBudgetHeader = tblTBudgetHeaderService.getById(bhId);
        LOGGER.debug("TblTBudgetHeader details with id: {}" , foundTblTBudgetHeader);

        return foundTblTBudgetHeader;
    }

    @ApiOperation(value = "Updates the TblTBudgetHeader instance associated with the given id.")
    @RequestMapping(value = "/{bhId:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTBudgetHeader editTblTBudgetHeader(@PathVariable("bhId") Integer bhId, @RequestBody TblTBudgetHeader tblTbudgetHeader) {
        LOGGER.debug("Editing TblTBudgetHeader with id: {}" , tblTbudgetHeader.getBhId());

        tblTbudgetHeader.setBhId(bhId);
        tblTbudgetHeader = tblTBudgetHeaderService.update(tblTbudgetHeader);
        LOGGER.debug("TblTBudgetHeader details with id: {}" , tblTbudgetHeader);

        return tblTbudgetHeader;
    }
    
    @ApiOperation(value = "Partially updates the TblTBudgetHeader instance associated with the given id.")
    @RequestMapping(value = "/{bhId:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTBudgetHeader patchTblTBudgetHeader(@PathVariable("bhId") Integer bhId, @RequestBody @MapTo(TblTBudgetHeader.class) Map<String, Object> tblTbudgetHeaderPatch) {
        LOGGER.debug("Partially updating TblTBudgetHeader with id: {}" , bhId);

        TblTBudgetHeader tblTbudgetHeader = tblTBudgetHeaderService.partialUpdate(bhId, tblTbudgetHeaderPatch);
        LOGGER.debug("TblTBudgetHeader details after partial update: {}" , tblTbudgetHeader);

        return tblTbudgetHeader;
    }

    @ApiOperation(value = "Deletes the TblTBudgetHeader instance associated with the given id.")
    @RequestMapping(value = "/{bhId:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTblTBudgetHeader(@PathVariable("bhId") Integer bhId) {
        LOGGER.debug("Deleting TblTBudgetHeader with id: {}" , bhId);

        TblTBudgetHeader deletedTblTBudgetHeader = tblTBudgetHeaderService.delete(bhId);

        return deletedTblTBudgetHeader != null;
    }

    /**
     * @deprecated Use {@link #findTblTBudgetHeaders(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TblTBudgetHeader instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTBudgetHeader> searchTblTBudgetHeadersByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TblTBudgetHeaders list by query filter:{}", (Object) queryFilters);
        return tblTBudgetHeaderService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblTBudgetHeader instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTBudgetHeader> findTblTBudgetHeaders(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblTBudgetHeaders list by filter:", query);
        return tblTBudgetHeaderService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblTBudgetHeader instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTBudgetHeader> filterTblTBudgetHeaders(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblTBudgetHeaders list by filter", query);
        return tblTBudgetHeaderService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTblTBudgetHeaders(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tblTBudgetHeaderService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTblTBudgetHeadersAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TblTBudgetHeader.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tblTBudgetHeaderService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TblTBudgetHeader instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTblTBudgetHeaders( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TblTBudgetHeaders");
		return tblTBudgetHeaderService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTblTBudgetHeaderAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tblTBudgetHeaderService.getAggregatedValues(aggregationInfo, pageable);
    }

    @RequestMapping(value="/{bhId:.+}/tblTbudgetDetailses", method=RequestMethod.GET)
    @ApiOperation(value = "Gets the tblTbudgetDetailses instance associated with the given id.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTBudgetDetails> findAssociatedTblTbudgetDetailses(@PathVariable("bhId") Integer bhId, Pageable pageable) {

        LOGGER.debug("Fetching all associated tblTbudgetDetailses");
        return tblTBudgetHeaderService.findAssociatedTblTbudgetDetailses(bhId, pageable);
    }

    @RequestMapping(value="/{bhId:.+}/tblTprLineItems", method=RequestMethod.GET)
    @ApiOperation(value = "Gets the tblTprLineItems instance associated with the given id.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTPrLineItem> findAssociatedTblTprLineItems(@PathVariable("bhId") Integer bhId, Pageable pageable) {

        LOGGER.debug("Fetching all associated tblTprLineItems");
        return tblTBudgetHeaderService.findAssociatedTblTprLineItems(bhId, pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TblTBudgetHeaderService instance
	 */
	protected void setTblTBudgetHeaderService(TblTBudgetHeaderService service) {
		this.tblTBudgetHeaderService = service;
	}

}