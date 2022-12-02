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

import id.co.aio.procure_to_pay.aio_ptp.VPoHeaderToGr;
import id.co.aio.procure_to_pay.aio_ptp.service.VPoHeaderToGrService;


/**
 * Controller object for domain model class VPoHeaderToGr.
 * @see VPoHeaderToGr
 */
@RestController("aio_ptp.VPoHeaderToGrController")
@Api(value = "VPoHeaderToGrController", description = "Exposes APIs to work with VPoHeaderToGr resource.")
@RequestMapping("/aio_ptp/VPoHeaderToGr")
public class VPoHeaderToGrController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VPoHeaderToGrController.class);

    @Autowired
	@Qualifier("aio_ptp.VPoHeaderToGrService")
	private VPoHeaderToGrService vPoHeaderToGrService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new VPoHeaderToGr instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VPoHeaderToGr createVPoHeaderToGr(@RequestBody VPoHeaderToGr vpoHeaderToGr) {
		LOGGER.debug("Create VPoHeaderToGr with information: {}" , vpoHeaderToGr);

		vpoHeaderToGr = vPoHeaderToGrService.create(vpoHeaderToGr);
		LOGGER.debug("Created VPoHeaderToGr with information: {}" , vpoHeaderToGr);

	    return vpoHeaderToGr;
	}

    @ApiOperation(value = "Returns the VPoHeaderToGr instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VPoHeaderToGr getVPoHeaderToGr(@PathVariable("id") Integer id) {
        LOGGER.debug("Getting VPoHeaderToGr with id: {}" , id);

        VPoHeaderToGr foundVPoHeaderToGr = vPoHeaderToGrService.getById(id);
        LOGGER.debug("VPoHeaderToGr details with id: {}" , foundVPoHeaderToGr);

        return foundVPoHeaderToGr;
    }

    @ApiOperation(value = "Updates the VPoHeaderToGr instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VPoHeaderToGr editVPoHeaderToGr(@PathVariable("id") Integer id, @RequestBody VPoHeaderToGr vpoHeaderToGr) {
        LOGGER.debug("Editing VPoHeaderToGr with id: {}" , vpoHeaderToGr.getId());

        vpoHeaderToGr.setId(id);
        vpoHeaderToGr = vPoHeaderToGrService.update(vpoHeaderToGr);
        LOGGER.debug("VPoHeaderToGr details with id: {}" , vpoHeaderToGr);

        return vpoHeaderToGr;
    }
    
    @ApiOperation(value = "Partially updates the VPoHeaderToGr instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VPoHeaderToGr patchVPoHeaderToGr(@PathVariable("id") Integer id, @RequestBody @MapTo(VPoHeaderToGr.class) Map<String, Object> vpoHeaderToGrPatch) {
        LOGGER.debug("Partially updating VPoHeaderToGr with id: {}" , id);

        VPoHeaderToGr vpoHeaderToGr = vPoHeaderToGrService.partialUpdate(id, vpoHeaderToGrPatch);
        LOGGER.debug("VPoHeaderToGr details after partial update: {}" , vpoHeaderToGr);

        return vpoHeaderToGr;
    }

    @ApiOperation(value = "Deletes the VPoHeaderToGr instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteVPoHeaderToGr(@PathVariable("id") Integer id) {
        LOGGER.debug("Deleting VPoHeaderToGr with id: {}" , id);

        VPoHeaderToGr deletedVPoHeaderToGr = vPoHeaderToGrService.delete(id);

        return deletedVPoHeaderToGr != null;
    }

    /**
     * @deprecated Use {@link #findVPoHeaderToGrs(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of VPoHeaderToGr instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<VPoHeaderToGr> searchVPoHeaderToGrsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering VPoHeaderToGrs list by query filter:{}", (Object) queryFilters);
        return vPoHeaderToGrService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of VPoHeaderToGr instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<VPoHeaderToGr> findVPoHeaderToGrs(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering VPoHeaderToGrs list by filter:", query);
        return vPoHeaderToGrService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of VPoHeaderToGr instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<VPoHeaderToGr> filterVPoHeaderToGrs(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering VPoHeaderToGrs list by filter", query);
        return vPoHeaderToGrService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportVPoHeaderToGrs(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return vPoHeaderToGrService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportVPoHeaderToGrsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = VPoHeaderToGr.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> vPoHeaderToGrService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of VPoHeaderToGr instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countVPoHeaderToGrs( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting VPoHeaderToGrs");
		return vPoHeaderToGrService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getVPoHeaderToGrAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return vPoHeaderToGrService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service VPoHeaderToGrService instance
	 */
	protected void setVPoHeaderToGrService(VPoHeaderToGrService service) {
		this.vPoHeaderToGrService = service;
	}

}