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

import id.co.aio.procure_to_pay.aio_ptp.TblTAdditionalAttachment;
import id.co.aio.procure_to_pay.aio_ptp.TblTAdditionalHistory;
import id.co.aio.procure_to_pay.aio_ptp.TblTBudgetAdditional;
import id.co.aio.procure_to_pay.aio_ptp.service.TblTBudgetAdditionalService;


/**
 * Controller object for domain model class TblTBudgetAdditional.
 * @see TblTBudgetAdditional
 */
@RestController("aio_ptp.TblTBudgetAdditionalController")
@Api(value = "TblTBudgetAdditionalController", description = "Exposes APIs to work with TblTBudgetAdditional resource.")
@RequestMapping("/aio_ptp/TblTBudgetAdditional")
public class TblTBudgetAdditionalController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblTBudgetAdditionalController.class);

    @Autowired
	@Qualifier("aio_ptp.TblTBudgetAdditionalService")
	private TblTBudgetAdditionalService tblTBudgetAdditionalService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TblTBudgetAdditional instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTBudgetAdditional createTblTBudgetAdditional(@RequestBody TblTBudgetAdditional tblTbudgetAdditional) {
		LOGGER.debug("Create TblTBudgetAdditional with information: {}" , tblTbudgetAdditional);

		tblTbudgetAdditional = tblTBudgetAdditionalService.create(tblTbudgetAdditional);
		LOGGER.debug("Created TblTBudgetAdditional with information: {}" , tblTbudgetAdditional);

	    return tblTbudgetAdditional;
	}

    @ApiOperation(value = "Returns the TblTBudgetAdditional instance associated with the given id.")
    @RequestMapping(value = "/{baId:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTBudgetAdditional getTblTBudgetAdditional(@PathVariable("baId") Integer baId) {
        LOGGER.debug("Getting TblTBudgetAdditional with id: {}" , baId);

        TblTBudgetAdditional foundTblTBudgetAdditional = tblTBudgetAdditionalService.getById(baId);
        LOGGER.debug("TblTBudgetAdditional details with id: {}" , foundTblTBudgetAdditional);

        return foundTblTBudgetAdditional;
    }

    @ApiOperation(value = "Updates the TblTBudgetAdditional instance associated with the given id.")
    @RequestMapping(value = "/{baId:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTBudgetAdditional editTblTBudgetAdditional(@PathVariable("baId") Integer baId, @RequestBody TblTBudgetAdditional tblTbudgetAdditional) {
        LOGGER.debug("Editing TblTBudgetAdditional with id: {}" , tblTbudgetAdditional.getBaId());

        tblTbudgetAdditional.setBaId(baId);
        tblTbudgetAdditional = tblTBudgetAdditionalService.update(tblTbudgetAdditional);
        LOGGER.debug("TblTBudgetAdditional details with id: {}" , tblTbudgetAdditional);

        return tblTbudgetAdditional;
    }
    
    @ApiOperation(value = "Partially updates the TblTBudgetAdditional instance associated with the given id.")
    @RequestMapping(value = "/{baId:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTBudgetAdditional patchTblTBudgetAdditional(@PathVariable("baId") Integer baId, @RequestBody @MapTo(TblTBudgetAdditional.class) Map<String, Object> tblTbudgetAdditionalPatch) {
        LOGGER.debug("Partially updating TblTBudgetAdditional with id: {}" , baId);

        TblTBudgetAdditional tblTbudgetAdditional = tblTBudgetAdditionalService.partialUpdate(baId, tblTbudgetAdditionalPatch);
        LOGGER.debug("TblTBudgetAdditional details after partial update: {}" , tblTbudgetAdditional);

        return tblTbudgetAdditional;
    }

    @ApiOperation(value = "Deletes the TblTBudgetAdditional instance associated with the given id.")
    @RequestMapping(value = "/{baId:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTblTBudgetAdditional(@PathVariable("baId") Integer baId) {
        LOGGER.debug("Deleting TblTBudgetAdditional with id: {}" , baId);

        TblTBudgetAdditional deletedTblTBudgetAdditional = tblTBudgetAdditionalService.delete(baId);

        return deletedTblTBudgetAdditional != null;
    }

    /**
     * @deprecated Use {@link #findTblTBudgetAdditionals(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TblTBudgetAdditional instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTBudgetAdditional> searchTblTBudgetAdditionalsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TblTBudgetAdditionals list by query filter:{}", (Object) queryFilters);
        return tblTBudgetAdditionalService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblTBudgetAdditional instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTBudgetAdditional> findTblTBudgetAdditionals(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblTBudgetAdditionals list by filter:", query);
        return tblTBudgetAdditionalService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblTBudgetAdditional instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTBudgetAdditional> filterTblTBudgetAdditionals(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblTBudgetAdditionals list by filter", query);
        return tblTBudgetAdditionalService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTblTBudgetAdditionals(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tblTBudgetAdditionalService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTblTBudgetAdditionalsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TblTBudgetAdditional.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tblTBudgetAdditionalService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TblTBudgetAdditional instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTblTBudgetAdditionals( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TblTBudgetAdditionals");
		return tblTBudgetAdditionalService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTblTBudgetAdditionalAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tblTBudgetAdditionalService.getAggregatedValues(aggregationInfo, pageable);
    }

    @RequestMapping(value="/{baId:.+}/tblTadditionalAttachments", method=RequestMethod.GET)
    @ApiOperation(value = "Gets the tblTadditionalAttachments instance associated with the given id.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTAdditionalAttachment> findAssociatedTblTadditionalAttachments(@PathVariable("baId") Integer baId, Pageable pageable) {

        LOGGER.debug("Fetching all associated tblTadditionalAttachments");
        return tblTBudgetAdditionalService.findAssociatedTblTadditionalAttachments(baId, pageable);
    }

    @RequestMapping(value="/{baId:.+}/tblTadditionalHistories", method=RequestMethod.GET)
    @ApiOperation(value = "Gets the tblTadditionalHistories instance associated with the given id.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTAdditionalHistory> findAssociatedTblTadditionalHistories(@PathVariable("baId") Integer baId, Pageable pageable) {

        LOGGER.debug("Fetching all associated tblTadditionalHistories");
        return tblTBudgetAdditionalService.findAssociatedTblTadditionalHistories(baId, pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TblTBudgetAdditionalService instance
	 */
	protected void setTblTBudgetAdditionalService(TblTBudgetAdditionalService service) {
		this.tblTBudgetAdditionalService = service;
	}

}