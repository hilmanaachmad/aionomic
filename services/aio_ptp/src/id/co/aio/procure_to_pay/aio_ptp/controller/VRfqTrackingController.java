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

import id.co.aio.procure_to_pay.aio_ptp.VRfqTracking;
import id.co.aio.procure_to_pay.aio_ptp.service.VRfqTrackingService;


/**
 * Controller object for domain model class VRfqTracking.
 * @see VRfqTracking
 */
@RestController("aio_ptp.VRfqTrackingController")
@Api(value = "VRfqTrackingController", description = "Exposes APIs to work with VRfqTracking resource.")
@RequestMapping("/aio_ptp/VRfqTracking")
public class VRfqTrackingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VRfqTrackingController.class);

    @Autowired
	@Qualifier("aio_ptp.VRfqTrackingService")
	private VRfqTrackingService vRfqTrackingService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new VRfqTracking instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VRfqTracking createVRfqTracking(@RequestBody VRfqTracking vrfqTracking) {
		LOGGER.debug("Create VRfqTracking with information: {}" , vrfqTracking);

		vrfqTracking = vRfqTrackingService.create(vrfqTracking);
		LOGGER.debug("Created VRfqTracking with information: {}" , vrfqTracking);

	    return vrfqTracking;
	}

    @ApiOperation(value = "Returns the VRfqTracking instance associated with the given id.")
    @RequestMapping(value = "/{rfqId:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VRfqTracking getVRfqTracking(@PathVariable("rfqId") Integer rfqId) {
        LOGGER.debug("Getting VRfqTracking with id: {}" , rfqId);

        VRfqTracking foundVRfqTracking = vRfqTrackingService.getById(rfqId);
        LOGGER.debug("VRfqTracking details with id: {}" , foundVRfqTracking);

        return foundVRfqTracking;
    }

    @ApiOperation(value = "Updates the VRfqTracking instance associated with the given id.")
    @RequestMapping(value = "/{rfqId:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VRfqTracking editVRfqTracking(@PathVariable("rfqId") Integer rfqId, @RequestBody VRfqTracking vrfqTracking) {
        LOGGER.debug("Editing VRfqTracking with id: {}" , vrfqTracking.getRfqId());

        vrfqTracking.setRfqId(rfqId);
        vrfqTracking = vRfqTrackingService.update(vrfqTracking);
        LOGGER.debug("VRfqTracking details with id: {}" , vrfqTracking);

        return vrfqTracking;
    }
    
    @ApiOperation(value = "Partially updates the VRfqTracking instance associated with the given id.")
    @RequestMapping(value = "/{rfqId:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VRfqTracking patchVRfqTracking(@PathVariable("rfqId") Integer rfqId, @RequestBody @MapTo(VRfqTracking.class) Map<String, Object> vrfqTrackingPatch) {
        LOGGER.debug("Partially updating VRfqTracking with id: {}" , rfqId);

        VRfqTracking vrfqTracking = vRfqTrackingService.partialUpdate(rfqId, vrfqTrackingPatch);
        LOGGER.debug("VRfqTracking details after partial update: {}" , vrfqTracking);

        return vrfqTracking;
    }

    @ApiOperation(value = "Deletes the VRfqTracking instance associated with the given id.")
    @RequestMapping(value = "/{rfqId:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteVRfqTracking(@PathVariable("rfqId") Integer rfqId) {
        LOGGER.debug("Deleting VRfqTracking with id: {}" , rfqId);

        VRfqTracking deletedVRfqTracking = vRfqTrackingService.delete(rfqId);

        return deletedVRfqTracking != null;
    }

    /**
     * @deprecated Use {@link #findVRfqTrackings(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of VRfqTracking instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<VRfqTracking> searchVRfqTrackingsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering VRfqTrackings list by query filter:{}", (Object) queryFilters);
        return vRfqTrackingService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of VRfqTracking instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<VRfqTracking> findVRfqTrackings(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering VRfqTrackings list by filter:", query);
        return vRfqTrackingService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of VRfqTracking instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<VRfqTracking> filterVRfqTrackings(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering VRfqTrackings list by filter", query);
        return vRfqTrackingService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportVRfqTrackings(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return vRfqTrackingService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportVRfqTrackingsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = VRfqTracking.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> vRfqTrackingService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of VRfqTracking instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countVRfqTrackings( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting VRfqTrackings");
		return vRfqTrackingService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getVRfqTrackingAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return vRfqTrackingService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service VRfqTrackingService instance
	 */
	protected void setVRfqTrackingService(VRfqTrackingService service) {
		this.vRfqTrackingService = service;
	}

}