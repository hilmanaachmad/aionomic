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

import id.co.aio.procure_to_pay.avm2.VPic;
import id.co.aio.procure_to_pay.avm2.service.VPicService;


/**
 * Controller object for domain model class VPic.
 * @see VPic
 */
@RestController("AVM2.VPicController")
@Api(value = "VPicController", description = "Exposes APIs to work with VPic resource.")
@RequestMapping("/AVM2/VPic")
public class VPicController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VPicController.class);

    @Autowired
	@Qualifier("AVM2.VPicService")
	private VPicService vPicService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new VPic instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VPic createVPic(@RequestBody VPic vpic) {
		LOGGER.debug("Create VPic with information: {}" , vpic);

		vpic = vPicService.create(vpic);
		LOGGER.debug("Created VPic with information: {}" , vpic);

	    return vpic;
	}

    @ApiOperation(value = "Returns the VPic instance associated with the given id.")
    @RequestMapping(value = "/{abIdil:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VPic getVPic(@PathVariable("abIdil") Integer abIdil) {
        LOGGER.debug("Getting VPic with id: {}" , abIdil);

        VPic foundVPic = vPicService.getById(abIdil);
        LOGGER.debug("VPic details with id: {}" , foundVPic);

        return foundVPic;
    }

    @ApiOperation(value = "Updates the VPic instance associated with the given id.")
    @RequestMapping(value = "/{abIdil:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VPic editVPic(@PathVariable("abIdil") Integer abIdil, @RequestBody VPic vpic) {
        LOGGER.debug("Editing VPic with id: {}" , vpic.getAbIdil());

        vpic.setAbIdil(abIdil);
        vpic = vPicService.update(vpic);
        LOGGER.debug("VPic details with id: {}" , vpic);

        return vpic;
    }
    
    @ApiOperation(value = "Partially updates the VPic instance associated with the given id.")
    @RequestMapping(value = "/{abIdil:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VPic patchVPic(@PathVariable("abIdil") Integer abIdil, @RequestBody @MapTo(VPic.class) Map<String, Object> vpicPatch) {
        LOGGER.debug("Partially updating VPic with id: {}" , abIdil);

        VPic vpic = vPicService.partialUpdate(abIdil, vpicPatch);
        LOGGER.debug("VPic details after partial update: {}" , vpic);

        return vpic;
    }

    @ApiOperation(value = "Deletes the VPic instance associated with the given id.")
    @RequestMapping(value = "/{abIdil:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteVPic(@PathVariable("abIdil") Integer abIdil) {
        LOGGER.debug("Deleting VPic with id: {}" , abIdil);

        VPic deletedVPic = vPicService.delete(abIdil);

        return deletedVPic != null;
    }

    /**
     * @deprecated Use {@link #findVPics(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of VPic instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<VPic> searchVPicsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering VPics list by query filter:{}", (Object) queryFilters);
        return vPicService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of VPic instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<VPic> findVPics(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering VPics list by filter:", query);
        return vPicService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of VPic instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<VPic> filterVPics(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering VPics list by filter", query);
        return vPicService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportVPics(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return vPicService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportVPicsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = VPic.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> vPicService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of VPic instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countVPics( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting VPics");
		return vPicService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getVPicAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return vPicService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service VPicService instance
	 */
	protected void setVPicService(VPicService service) {
		this.vPicService = service;
	}

}