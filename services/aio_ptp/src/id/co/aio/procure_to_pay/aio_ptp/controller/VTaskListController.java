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

import id.co.aio.procure_to_pay.aio_ptp.VTaskList;
import id.co.aio.procure_to_pay.aio_ptp.service.VTaskListService;


/**
 * Controller object for domain model class VTaskList.
 * @see VTaskList
 */
@RestController("aio_ptp.VTaskListController")
@Api(value = "VTaskListController", description = "Exposes APIs to work with VTaskList resource.")
@RequestMapping("/aio_ptp/VTaskList")
public class VTaskListController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VTaskListController.class);

    @Autowired
	@Qualifier("aio_ptp.VTaskListService")
	private VTaskListService vTaskListService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new VTaskList instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VTaskList createVTaskList(@RequestBody VTaskList vtaskList) {
		LOGGER.debug("Create VTaskList with information: {}" , vtaskList);

		vtaskList = vTaskListService.create(vtaskList);
		LOGGER.debug("Created VTaskList with information: {}" , vtaskList);

	    return vtaskList;
	}

    @ApiOperation(value = "Returns the VTaskList instance associated with the given id.")
    @RequestMapping(value = "/{tlId:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VTaskList getVTaskList(@PathVariable("tlId") Integer tlId) {
        LOGGER.debug("Getting VTaskList with id: {}" , tlId);

        VTaskList foundVTaskList = vTaskListService.getById(tlId);
        LOGGER.debug("VTaskList details with id: {}" , foundVTaskList);

        return foundVTaskList;
    }

    @ApiOperation(value = "Updates the VTaskList instance associated with the given id.")
    @RequestMapping(value = "/{tlId:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VTaskList editVTaskList(@PathVariable("tlId") Integer tlId, @RequestBody VTaskList vtaskList) {
        LOGGER.debug("Editing VTaskList with id: {}" , vtaskList.getTlId());

        vtaskList.setTlId(tlId);
        vtaskList = vTaskListService.update(vtaskList);
        LOGGER.debug("VTaskList details with id: {}" , vtaskList);

        return vtaskList;
    }
    
    @ApiOperation(value = "Partially updates the VTaskList instance associated with the given id.")
    @RequestMapping(value = "/{tlId:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VTaskList patchVTaskList(@PathVariable("tlId") Integer tlId, @RequestBody @MapTo(VTaskList.class) Map<String, Object> vtaskListPatch) {
        LOGGER.debug("Partially updating VTaskList with id: {}" , tlId);

        VTaskList vtaskList = vTaskListService.partialUpdate(tlId, vtaskListPatch);
        LOGGER.debug("VTaskList details after partial update: {}" , vtaskList);

        return vtaskList;
    }

    @ApiOperation(value = "Deletes the VTaskList instance associated with the given id.")
    @RequestMapping(value = "/{tlId:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteVTaskList(@PathVariable("tlId") Integer tlId) {
        LOGGER.debug("Deleting VTaskList with id: {}" , tlId);

        VTaskList deletedVTaskList = vTaskListService.delete(tlId);

        return deletedVTaskList != null;
    }

    /**
     * @deprecated Use {@link #findVTaskLists(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of VTaskList instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<VTaskList> searchVTaskListsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering VTaskLists list by query filter:{}", (Object) queryFilters);
        return vTaskListService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of VTaskList instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<VTaskList> findVTaskLists(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering VTaskLists list by filter:", query);
        return vTaskListService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of VTaskList instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<VTaskList> filterVTaskLists(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering VTaskLists list by filter", query);
        return vTaskListService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportVTaskLists(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return vTaskListService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportVTaskListsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = VTaskList.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> vTaskListService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of VTaskList instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countVTaskLists( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting VTaskLists");
		return vTaskListService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getVTaskListAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return vTaskListService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service VTaskListService instance
	 */
	protected void setVTaskListService(VTaskListService service) {
		this.vTaskListService = service;
	}

}