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
import id.co.aio.procure_to_pay.aio_ptp.service.TblTAdditionalAttachmentService;


/**
 * Controller object for domain model class TblTAdditionalAttachment.
 * @see TblTAdditionalAttachment
 */
@RestController("aio_ptp.TblTAdditionalAttachmentController")
@Api(value = "TblTAdditionalAttachmentController", description = "Exposes APIs to work with TblTAdditionalAttachment resource.")
@RequestMapping("/aio_ptp/TblTAdditionalAttachment")
public class TblTAdditionalAttachmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblTAdditionalAttachmentController.class);

    @Autowired
	@Qualifier("aio_ptp.TblTAdditionalAttachmentService")
	private TblTAdditionalAttachmentService tblTAdditionalAttachmentService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TblTAdditionalAttachment instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTAdditionalAttachment createTblTAdditionalAttachment(@RequestBody TblTAdditionalAttachment tblTadditionalAttachment) {
		LOGGER.debug("Create TblTAdditionalAttachment with information: {}" , tblTadditionalAttachment);

		tblTadditionalAttachment = tblTAdditionalAttachmentService.create(tblTadditionalAttachment);
		LOGGER.debug("Created TblTAdditionalAttachment with information: {}" , tblTadditionalAttachment);

	    return tblTadditionalAttachment;
	}

    @ApiOperation(value = "Returns the TblTAdditionalAttachment instance associated with the given id.")
    @RequestMapping(value = "/{aaId:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTAdditionalAttachment getTblTAdditionalAttachment(@PathVariable("aaId") Integer aaId) {
        LOGGER.debug("Getting TblTAdditionalAttachment with id: {}" , aaId);

        TblTAdditionalAttachment foundTblTAdditionalAttachment = tblTAdditionalAttachmentService.getById(aaId);
        LOGGER.debug("TblTAdditionalAttachment details with id: {}" , foundTblTAdditionalAttachment);

        return foundTblTAdditionalAttachment;
    }

    @ApiOperation(value = "Updates the TblTAdditionalAttachment instance associated with the given id.")
    @RequestMapping(value = "/{aaId:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTAdditionalAttachment editTblTAdditionalAttachment(@PathVariable("aaId") Integer aaId, @RequestBody TblTAdditionalAttachment tblTadditionalAttachment) {
        LOGGER.debug("Editing TblTAdditionalAttachment with id: {}" , tblTadditionalAttachment.getAaId());

        tblTadditionalAttachment.setAaId(aaId);
        tblTadditionalAttachment = tblTAdditionalAttachmentService.update(tblTadditionalAttachment);
        LOGGER.debug("TblTAdditionalAttachment details with id: {}" , tblTadditionalAttachment);

        return tblTadditionalAttachment;
    }
    
    @ApiOperation(value = "Partially updates the TblTAdditionalAttachment instance associated with the given id.")
    @RequestMapping(value = "/{aaId:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTAdditionalAttachment patchTblTAdditionalAttachment(@PathVariable("aaId") Integer aaId, @RequestBody @MapTo(TblTAdditionalAttachment.class) Map<String, Object> tblTadditionalAttachmentPatch) {
        LOGGER.debug("Partially updating TblTAdditionalAttachment with id: {}" , aaId);

        TblTAdditionalAttachment tblTadditionalAttachment = tblTAdditionalAttachmentService.partialUpdate(aaId, tblTadditionalAttachmentPatch);
        LOGGER.debug("TblTAdditionalAttachment details after partial update: {}" , tblTadditionalAttachment);

        return tblTadditionalAttachment;
    }

    @ApiOperation(value = "Deletes the TblTAdditionalAttachment instance associated with the given id.")
    @RequestMapping(value = "/{aaId:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTblTAdditionalAttachment(@PathVariable("aaId") Integer aaId) {
        LOGGER.debug("Deleting TblTAdditionalAttachment with id: {}" , aaId);

        TblTAdditionalAttachment deletedTblTAdditionalAttachment = tblTAdditionalAttachmentService.delete(aaId);

        return deletedTblTAdditionalAttachment != null;
    }

    /**
     * @deprecated Use {@link #findTblTAdditionalAttachments(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TblTAdditionalAttachment instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTAdditionalAttachment> searchTblTAdditionalAttachmentsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TblTAdditionalAttachments list by query filter:{}", (Object) queryFilters);
        return tblTAdditionalAttachmentService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblTAdditionalAttachment instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTAdditionalAttachment> findTblTAdditionalAttachments(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblTAdditionalAttachments list by filter:", query);
        return tblTAdditionalAttachmentService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblTAdditionalAttachment instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTAdditionalAttachment> filterTblTAdditionalAttachments(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblTAdditionalAttachments list by filter", query);
        return tblTAdditionalAttachmentService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTblTAdditionalAttachments(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tblTAdditionalAttachmentService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTblTAdditionalAttachmentsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TblTAdditionalAttachment.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tblTAdditionalAttachmentService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TblTAdditionalAttachment instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTblTAdditionalAttachments( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TblTAdditionalAttachments");
		return tblTAdditionalAttachmentService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTblTAdditionalAttachmentAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tblTAdditionalAttachmentService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TblTAdditionalAttachmentService instance
	 */
	protected void setTblTAdditionalAttachmentService(TblTAdditionalAttachmentService service) {
		this.tblTAdditionalAttachmentService = service;
	}

}