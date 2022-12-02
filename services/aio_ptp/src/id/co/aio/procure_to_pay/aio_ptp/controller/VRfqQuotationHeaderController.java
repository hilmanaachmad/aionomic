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

import id.co.aio.procure_to_pay.aio_ptp.VRfqQuotationHeader;
import id.co.aio.procure_to_pay.aio_ptp.service.VRfqQuotationHeaderService;


/**
 * Controller object for domain model class VRfqQuotationHeader.
 * @see VRfqQuotationHeader
 */
@RestController("aio_ptp.VRfqQuotationHeaderController")
@Api(value = "VRfqQuotationHeaderController", description = "Exposes APIs to work with VRfqQuotationHeader resource.")
@RequestMapping("/aio_ptp/VRfqQuotationHeader")
public class VRfqQuotationHeaderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VRfqQuotationHeaderController.class);

    @Autowired
	@Qualifier("aio_ptp.VRfqQuotationHeaderService")
	private VRfqQuotationHeaderService vRfqQuotationHeaderService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new VRfqQuotationHeader instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VRfqQuotationHeader createVRfqQuotationHeader(@RequestBody VRfqQuotationHeader vrfqQuotationHeader) {
		LOGGER.debug("Create VRfqQuotationHeader with information: {}" , vrfqQuotationHeader);

		vrfqQuotationHeader = vRfqQuotationHeaderService.create(vrfqQuotationHeader);
		LOGGER.debug("Created VRfqQuotationHeader with information: {}" , vrfqQuotationHeader);

	    return vrfqQuotationHeader;
	}

    @ApiOperation(value = "Returns the VRfqQuotationHeader instance associated with the given id.")
    @RequestMapping(value = "/{rfqvId:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VRfqQuotationHeader getVRfqQuotationHeader(@PathVariable("rfqvId") Integer rfqvId) {
        LOGGER.debug("Getting VRfqQuotationHeader with id: {}" , rfqvId);

        VRfqQuotationHeader foundVRfqQuotationHeader = vRfqQuotationHeaderService.getById(rfqvId);
        LOGGER.debug("VRfqQuotationHeader details with id: {}" , foundVRfqQuotationHeader);

        return foundVRfqQuotationHeader;
    }

    @ApiOperation(value = "Updates the VRfqQuotationHeader instance associated with the given id.")
    @RequestMapping(value = "/{rfqvId:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VRfqQuotationHeader editVRfqQuotationHeader(@PathVariable("rfqvId") Integer rfqvId, @RequestBody VRfqQuotationHeader vrfqQuotationHeader) {
        LOGGER.debug("Editing VRfqQuotationHeader with id: {}" , vrfqQuotationHeader.getRfqvId());

        vrfqQuotationHeader.setRfqvId(rfqvId);
        vrfqQuotationHeader = vRfqQuotationHeaderService.update(vrfqQuotationHeader);
        LOGGER.debug("VRfqQuotationHeader details with id: {}" , vrfqQuotationHeader);

        return vrfqQuotationHeader;
    }
    
    @ApiOperation(value = "Partially updates the VRfqQuotationHeader instance associated with the given id.")
    @RequestMapping(value = "/{rfqvId:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VRfqQuotationHeader patchVRfqQuotationHeader(@PathVariable("rfqvId") Integer rfqvId, @RequestBody @MapTo(VRfqQuotationHeader.class) Map<String, Object> vrfqQuotationHeaderPatch) {
        LOGGER.debug("Partially updating VRfqQuotationHeader with id: {}" , rfqvId);

        VRfqQuotationHeader vrfqQuotationHeader = vRfqQuotationHeaderService.partialUpdate(rfqvId, vrfqQuotationHeaderPatch);
        LOGGER.debug("VRfqQuotationHeader details after partial update: {}" , vrfqQuotationHeader);

        return vrfqQuotationHeader;
    }

    @ApiOperation(value = "Deletes the VRfqQuotationHeader instance associated with the given id.")
    @RequestMapping(value = "/{rfqvId:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteVRfqQuotationHeader(@PathVariable("rfqvId") Integer rfqvId) {
        LOGGER.debug("Deleting VRfqQuotationHeader with id: {}" , rfqvId);

        VRfqQuotationHeader deletedVRfqQuotationHeader = vRfqQuotationHeaderService.delete(rfqvId);

        return deletedVRfqQuotationHeader != null;
    }

    /**
     * @deprecated Use {@link #findVRfqQuotationHeaders(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of VRfqQuotationHeader instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<VRfqQuotationHeader> searchVRfqQuotationHeadersByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering VRfqQuotationHeaders list by query filter:{}", (Object) queryFilters);
        return vRfqQuotationHeaderService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of VRfqQuotationHeader instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<VRfqQuotationHeader> findVRfqQuotationHeaders(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering VRfqQuotationHeaders list by filter:", query);
        return vRfqQuotationHeaderService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of VRfqQuotationHeader instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<VRfqQuotationHeader> filterVRfqQuotationHeaders(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering VRfqQuotationHeaders list by filter", query);
        return vRfqQuotationHeaderService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportVRfqQuotationHeaders(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return vRfqQuotationHeaderService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportVRfqQuotationHeadersAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = VRfqQuotationHeader.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> vRfqQuotationHeaderService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of VRfqQuotationHeader instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countVRfqQuotationHeaders( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting VRfqQuotationHeaders");
		return vRfqQuotationHeaderService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getVRfqQuotationHeaderAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return vRfqQuotationHeaderService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service VRfqQuotationHeaderService instance
	 */
	protected void setVRfqQuotationHeaderService(VRfqQuotationHeaderService service) {
		this.vRfqQuotationHeaderService = service;
	}

}