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

import id.co.aio.procure_to_pay.aio_ptp.TblPbAttachment;
import id.co.aio.procure_to_pay.aio_ptp.TblTPr;
import id.co.aio.procure_to_pay.aio_ptp.TblTProposalBudget;
import id.co.aio.procure_to_pay.aio_ptp.service.TblTProposalBudgetService;


/**
 * Controller object for domain model class TblTProposalBudget.
 * @see TblTProposalBudget
 */
@RestController("aio_ptp.TblTProposalBudgetController")
@Api(value = "TblTProposalBudgetController", description = "Exposes APIs to work with TblTProposalBudget resource.")
@RequestMapping("/aio_ptp/TblTProposalBudget")
public class TblTProposalBudgetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblTProposalBudgetController.class);

    @Autowired
	@Qualifier("aio_ptp.TblTProposalBudgetService")
	private TblTProposalBudgetService tblTProposalBudgetService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TblTProposalBudget instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTProposalBudget createTblTProposalBudget(@RequestBody TblTProposalBudget tblTproposalBudget) {
		LOGGER.debug("Create TblTProposalBudget with information: {}" , tblTproposalBudget);

		tblTproposalBudget = tblTProposalBudgetService.create(tblTproposalBudget);
		LOGGER.debug("Created TblTProposalBudget with information: {}" , tblTproposalBudget);

	    return tblTproposalBudget;
	}

    @ApiOperation(value = "Returns the TblTProposalBudget instance associated with the given id.")
    @RequestMapping(value = "/{pbId:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTProposalBudget getTblTProposalBudget(@PathVariable("pbId") Integer pbId) {
        LOGGER.debug("Getting TblTProposalBudget with id: {}" , pbId);

        TblTProposalBudget foundTblTProposalBudget = tblTProposalBudgetService.getById(pbId);
        LOGGER.debug("TblTProposalBudget details with id: {}" , foundTblTProposalBudget);

        return foundTblTProposalBudget;
    }

    @ApiOperation(value = "Updates the TblTProposalBudget instance associated with the given id.")
    @RequestMapping(value = "/{pbId:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTProposalBudget editTblTProposalBudget(@PathVariable("pbId") Integer pbId, @RequestBody TblTProposalBudget tblTproposalBudget) {
        LOGGER.debug("Editing TblTProposalBudget with id: {}" , tblTproposalBudget.getPbId());

        tblTproposalBudget.setPbId(pbId);
        tblTproposalBudget = tblTProposalBudgetService.update(tblTproposalBudget);
        LOGGER.debug("TblTProposalBudget details with id: {}" , tblTproposalBudget);

        return tblTproposalBudget;
    }
    
    @ApiOperation(value = "Partially updates the TblTProposalBudget instance associated with the given id.")
    @RequestMapping(value = "/{pbId:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTProposalBudget patchTblTProposalBudget(@PathVariable("pbId") Integer pbId, @RequestBody @MapTo(TblTProposalBudget.class) Map<String, Object> tblTproposalBudgetPatch) {
        LOGGER.debug("Partially updating TblTProposalBudget with id: {}" , pbId);

        TblTProposalBudget tblTproposalBudget = tblTProposalBudgetService.partialUpdate(pbId, tblTproposalBudgetPatch);
        LOGGER.debug("TblTProposalBudget details after partial update: {}" , tblTproposalBudget);

        return tblTproposalBudget;
    }

    @ApiOperation(value = "Deletes the TblTProposalBudget instance associated with the given id.")
    @RequestMapping(value = "/{pbId:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTblTProposalBudget(@PathVariable("pbId") Integer pbId) {
        LOGGER.debug("Deleting TblTProposalBudget with id: {}" , pbId);

        TblTProposalBudget deletedTblTProposalBudget = tblTProposalBudgetService.delete(pbId);

        return deletedTblTProposalBudget != null;
    }

    /**
     * @deprecated Use {@link #findTblTProposalBudgets(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TblTProposalBudget instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTProposalBudget> searchTblTProposalBudgetsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TblTProposalBudgets list by query filter:{}", (Object) queryFilters);
        return tblTProposalBudgetService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblTProposalBudget instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTProposalBudget> findTblTProposalBudgets(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblTProposalBudgets list by filter:", query);
        return tblTProposalBudgetService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblTProposalBudget instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTProposalBudget> filterTblTProposalBudgets(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblTProposalBudgets list by filter", query);
        return tblTProposalBudgetService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTblTProposalBudgets(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tblTProposalBudgetService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTblTProposalBudgetsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TblTProposalBudget.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tblTProposalBudgetService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TblTProposalBudget instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTblTProposalBudgets( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TblTProposalBudgets");
		return tblTProposalBudgetService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTblTProposalBudgetAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tblTProposalBudgetService.getAggregatedValues(aggregationInfo, pageable);
    }

    @RequestMapping(value="/{pbId:.+}/tblPbAttachments", method=RequestMethod.GET)
    @ApiOperation(value = "Gets the tblPbAttachments instance associated with the given id.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblPbAttachment> findAssociatedTblPbAttachments(@PathVariable("pbId") Integer pbId, Pageable pageable) {

        LOGGER.debug("Fetching all associated tblPbAttachments");
        return tblTProposalBudgetService.findAssociatedTblPbAttachments(pbId, pageable);
    }

    @RequestMapping(value="/{pbId:.+}/tblTprs", method=RequestMethod.GET)
    @ApiOperation(value = "Gets the tblTprs instance associated with the given id.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTPr> findAssociatedTblTprs(@PathVariable("pbId") Integer pbId, Pageable pageable) {

        LOGGER.debug("Fetching all associated tblTprs");
        return tblTProposalBudgetService.findAssociatedTblTprs(pbId, pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TblTProposalBudgetService instance
	 */
	protected void setTblTProposalBudgetService(TblTProposalBudgetService service) {
		this.tblTProposalBudgetService = service;
	}

}