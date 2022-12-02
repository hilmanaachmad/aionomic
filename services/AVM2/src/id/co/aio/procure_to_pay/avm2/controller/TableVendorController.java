/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2.controller;

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

import id.co.aio.procure_to_pay.avm2.TableVendor;
import id.co.aio.procure_to_pay.avm2.service.TableVendorService;


/**
 * Controller object for domain model class TableVendor.
 * @see TableVendor
 */
@RestController("AVM2.TableVendorController")
@Api(value = "TableVendorController", description = "Exposes APIs to work with TableVendor resource.")
@RequestMapping("/AVM2/TableVendor")
public class TableVendorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TableVendorController.class);

    @Autowired
	@Qualifier("AVM2.TableVendorService")
	private TableVendorService tableVendorService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TableVendor instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TableVendor createTableVendor(@RequestBody TableVendor tableVendor) {
		LOGGER.debug("Create TableVendor with information: {}" , tableVendor);

		tableVendor = tableVendorService.create(tableVendor);
		LOGGER.debug("Created TableVendor with information: {}" , tableVendor);

	    return tableVendor;
	}

    @ApiOperation(value = "Returns the TableVendor instance associated with the given id.")
    @RequestMapping(value = "/{abIdor:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TableVendor getTableVendor(@PathVariable("abIdor") Integer abIdor) {
        LOGGER.debug("Getting TableVendor with id: {}" , abIdor);

        TableVendor foundTableVendor = tableVendorService.getById(abIdor);
        LOGGER.debug("TableVendor details with id: {}" , foundTableVendor);

        return foundTableVendor;
    }

    @ApiOperation(value = "Updates the TableVendor instance associated with the given id.")
    @RequestMapping(value = "/{abIdor:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TableVendor editTableVendor(@PathVariable("abIdor") Integer abIdor, @RequestBody TableVendor tableVendor) {
        LOGGER.debug("Editing TableVendor with id: {}" , tableVendor.getAbIdor());

        tableVendor.setAbIdor(abIdor);
        tableVendor = tableVendorService.update(tableVendor);
        LOGGER.debug("TableVendor details with id: {}" , tableVendor);

        return tableVendor;
    }
    
    @ApiOperation(value = "Partially updates the TableVendor instance associated with the given id.")
    @RequestMapping(value = "/{abIdor:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TableVendor patchTableVendor(@PathVariable("abIdor") Integer abIdor, @RequestBody @MapTo(TableVendor.class) Map<String, Object> tableVendorPatch) {
        LOGGER.debug("Partially updating TableVendor with id: {}" , abIdor);

        TableVendor tableVendor = tableVendorService.partialUpdate(abIdor, tableVendorPatch);
        LOGGER.debug("TableVendor details after partial update: {}" , tableVendor);

        return tableVendor;
    }

    @ApiOperation(value = "Deletes the TableVendor instance associated with the given id.")
    @RequestMapping(value = "/{abIdor:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTableVendor(@PathVariable("abIdor") Integer abIdor) {
        LOGGER.debug("Deleting TableVendor with id: {}" , abIdor);

        TableVendor deletedTableVendor = tableVendorService.delete(abIdor);

        return deletedTableVendor != null;
    }

    /**
     * @deprecated Use {@link #findTableVendors(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TableVendor instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TableVendor> searchTableVendorsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TableVendors list by query filter:{}", (Object) queryFilters);
        return tableVendorService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TableVendor instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TableVendor> findTableVendors(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TableVendors list by filter:", query);
        return tableVendorService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TableVendor instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TableVendor> filterTableVendors(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TableVendors list by filter", query);
        return tableVendorService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTableVendors(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tableVendorService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTableVendorsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TableVendor.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tableVendorService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TableVendor instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTableVendors( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TableVendors");
		return tableVendorService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTableVendorAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tableVendorService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TableVendorService instance
	 */
	protected void setTableVendorService(TableVendorService service) {
		this.tableVendorService = service;
	}

}