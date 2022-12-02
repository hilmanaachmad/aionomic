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

import id.co.aio.procure_to_pay.aio_ptp.TblTPoLineItem;
import id.co.aio.procure_to_pay.aio_ptp.service.TblTPoLineItemService;


/**
 * Controller object for domain model class TblTPoLineItem.
 * @see TblTPoLineItem
 */
@RestController("aio_ptp.TblTPoLineItemController")
@Api(value = "TblTPoLineItemController", description = "Exposes APIs to work with TblTPoLineItem resource.")
@RequestMapping("/aio_ptp/TblTPoLineItem")
public class TblTPoLineItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblTPoLineItemController.class);

    @Autowired
	@Qualifier("aio_ptp.TblTPoLineItemService")
	private TblTPoLineItemService tblTPoLineItemService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TblTPoLineItem instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTPoLineItem createTblTPoLineItem(@RequestBody TblTPoLineItem tblTpoLineItem) {
		LOGGER.debug("Create TblTPoLineItem with information: {}" , tblTpoLineItem);

		tblTpoLineItem = tblTPoLineItemService.create(tblTpoLineItem);
		LOGGER.debug("Created TblTPoLineItem with information: {}" , tblTpoLineItem);

	    return tblTpoLineItem;
	}

    @ApiOperation(value = "Returns the TblTPoLineItem instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTPoLineItem getTblTPoLineItem(@PathVariable("id") Integer id) {
        LOGGER.debug("Getting TblTPoLineItem with id: {}" , id);

        TblTPoLineItem foundTblTPoLineItem = tblTPoLineItemService.getById(id);
        LOGGER.debug("TblTPoLineItem details with id: {}" , foundTblTPoLineItem);

        return foundTblTPoLineItem;
    }

    @ApiOperation(value = "Updates the TblTPoLineItem instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTPoLineItem editTblTPoLineItem(@PathVariable("id") Integer id, @RequestBody TblTPoLineItem tblTpoLineItem) {
        LOGGER.debug("Editing TblTPoLineItem with id: {}" , tblTpoLineItem.getId());

        tblTpoLineItem.setId(id);
        tblTpoLineItem = tblTPoLineItemService.update(tblTpoLineItem);
        LOGGER.debug("TblTPoLineItem details with id: {}" , tblTpoLineItem);

        return tblTpoLineItem;
    }
    
    @ApiOperation(value = "Partially updates the TblTPoLineItem instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTPoLineItem patchTblTPoLineItem(@PathVariable("id") Integer id, @RequestBody @MapTo(TblTPoLineItem.class) Map<String, Object> tblTpoLineItemPatch) {
        LOGGER.debug("Partially updating TblTPoLineItem with id: {}" , id);

        TblTPoLineItem tblTpoLineItem = tblTPoLineItemService.partialUpdate(id, tblTpoLineItemPatch);
        LOGGER.debug("TblTPoLineItem details after partial update: {}" , tblTpoLineItem);

        return tblTpoLineItem;
    }

    @ApiOperation(value = "Deletes the TblTPoLineItem instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTblTPoLineItem(@PathVariable("id") Integer id) {
        LOGGER.debug("Deleting TblTPoLineItem with id: {}" , id);

        TblTPoLineItem deletedTblTPoLineItem = tblTPoLineItemService.delete(id);

        return deletedTblTPoLineItem != null;
    }

    /**
     * @deprecated Use {@link #findTblTPoLineItems(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TblTPoLineItem instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTPoLineItem> searchTblTPoLineItemsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TblTPoLineItems list by query filter:{}", (Object) queryFilters);
        return tblTPoLineItemService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblTPoLineItem instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTPoLineItem> findTblTPoLineItems(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblTPoLineItems list by filter:", query);
        return tblTPoLineItemService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblTPoLineItem instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTPoLineItem> filterTblTPoLineItems(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblTPoLineItems list by filter", query);
        return tblTPoLineItemService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTblTPoLineItems(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tblTPoLineItemService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTblTPoLineItemsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TblTPoLineItem.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tblTPoLineItemService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TblTPoLineItem instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTblTPoLineItems( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TblTPoLineItems");
		return tblTPoLineItemService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTblTPoLineItemAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tblTPoLineItemService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TblTPoLineItemService instance
	 */
	protected void setTblTPoLineItemService(TblTPoLineItemService service) {
		this.tblTPoLineItemService = service;
	}

}